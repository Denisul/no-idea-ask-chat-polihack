import org.json.JSONArray
import org.json.JSONObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

// Data Classes
data class PersonalityTraits(
    val neuroticism: Int,
    val extroversion: Int,
    val openness: Int,
    val agreeableness: Int,
    val conscientiousness: Int
)

data class PersonalityAnalysisResponse(
    val fullAnalysis: String,
    val personalityInsights: PersonalityInsights,
    val developmentStrategies: List<String>,
    val recommendedResources: List<String>
)

data class PersonalityInsights(
    val strengths: List<String>,
    val challenges: List<String>,
    val primaryTraitInterpretations: Map<String, String>
)

class PersonalityAnalysisService(private val apiKey: String) {
    private val client = OkHttpClient()
    private val openAiApiUrl = "https://api.openai.com/v1/chat/completions"

    suspend fun analyzePersonality(traits: PersonalityTraits): Result<PersonalityAnalysisResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val systemPrompt = """
                You are an empathetic and insightful personality analysis coach whose primary mission is to help individuals deeply understand themselves by analyzing their Big Five personality trait scores. Your role is to provide nuanced, scientifically-grounded, and constructive insights that illuminate personal strengths, potential growth areas, and actionable strategies for self-improvement, always with the goal of empowering individuals to recognize their unique potential, develop emotional intelligence, and foster personal growth through compassionate, evidence-based guidance.
                
                Output Format:
                ## Personality Insights
                - [Detailed insights]

                ## Strengths
                - [List of strengths]

                ## Potential Challenges
                - [List of challenges]

                ## Personal Development Strategies
                1. [First strategy]
                2. [Second strategy]
                3. [Third strategy]

                ## Recommended Resources
                1. [First resource]
                2. [Second resource]
                3. [Third resource]
                """.trimIndent()

                val userPrompt = """
                Analyze my personality based on these trait scores:
                - Neuroticism: ${traits.neuroticism}
                - Extroversion: ${traits.extroversion}
                - Openness: ${traits.openness}
                - Agreeableness: ${traits.agreeableness}
                - Conscientiousness: ${traits.conscientiousness}

                Please provide a comprehensive analysis using the specified output format.
                """.trimIndent()

                // Explicitly create JSONArray for messages
                val messagesArray = JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", systemPrompt)
                    })
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", userPrompt)
                    })
                }

                val requestBodyJson = JSONObject().apply {
                    put("model", "gpt-4o-mini")
                    put("messages", messagesArray)
                    put("max_tokens", 1500)
                    put("temperature", 0.7)
                }

                val requestBody = requestBodyJson.toString()
                    .toRequestBody("application/json".toMediaTypeOrNull())

                val request = Request.Builder()
                    .url(openAiApiUrl)
                    .addHeader("Authorization", "Bearer $apiKey")
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    val errorBody = response.body?.string() ?: "Unknown error"
                    return@withContext Result.failure(
                        IOException("API call failed with code: ${response.code}. Error: $errorBody")
                    )
                }

                val responseBody = response.body?.string()
                    ?: throw IOException("Empty response body")

                val jsonResponse = JSONObject(responseBody)
                val analysisText = jsonResponse
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")

                // Parse the response
                val parsedResponse = parsePersonalityAnalysis(analysisText)

                Result.success(parsedResponse)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun parsePersonalityAnalysis(text: String): PersonalityAnalysisResponse {
        return PersonalityAnalysisResponse(
            fullAnalysis = text.substringBefore("## Strengths"),
            personalityInsights = extractPersonalityInsights(text),
            developmentStrategies = extractDevelopmentStrategies(text),
            recommendedResources = extractRecommendedResources(text)
        )
    }

    private fun extractPersonalityInsights(text: String): PersonalityInsights {
        return PersonalityInsights(
            strengths = extractSection(text, "Strengths"),
            challenges = extractSection(text, "Potential Challenges"),
            primaryTraitInterpretations = extractTraitInterpretations(text)
        )
    }

    private fun extractSection(text: String, sectionTitle: String): List<String> {
        val sectionStartIndex = text.indexOf("## $sectionTitle")
        if (sectionStartIndex == -1) return emptyList()

        // Find the next section or end of text
        val nextSectionMatch = listOf(
            "## Personal Development Strategies",
            "## Recommended Resources",
            "## "
        ).mapNotNull {
            val index = text.indexOf(it, sectionStartIndex + sectionTitle.length + 3)
            if (index != -1) index else null
        }.minOrNull() ?: text.length

        // Extract the section content
        val sectionContent = text.substring(
            sectionStartIndex + sectionTitle.length + 3,
            nextSectionMatch
        ).trim()

        // Parse items
        return sectionContent
            .split("\n")
            .filter { it.startsWith("- ") }
            .map { it.removePrefix("- ").trim() }
    }

    private fun extractDevelopmentStrategies(text: String): List<String> {
        return extractNumberedSection(text, "Personal Development Strategies")
    }

    private fun extractRecommendedResources(text: String): List<String> {
        return extractNumberedSection(text, "Recommended Resources")
    }

    private fun extractNumberedSection(text: String, sectionTitle: String): List<String> {
        val sectionStartIndex = text.indexOf("## $sectionTitle")
        if (sectionStartIndex == -1) return emptyList()

        // Find the next section or end of text
        val nextSectionMatch = listOf(
            "## Recommended Resources",
            "## "
        ).mapNotNull {
            val index = text.indexOf(it, sectionStartIndex + sectionTitle.length + 3)
            if (index != -1) index else null
        }.minOrNull() ?: text.length

        // Extract the section content
        val sectionContent = text.substring(
            sectionStartIndex + sectionTitle.length + 3,
            nextSectionMatch
        ).trim()

        // Parse numbered items
        return sectionContent
            .split("\n")
            .filter { it.matches(Regex("^\\d+\\.\\s.*")) }
            .map { it.replaceFirst(Regex("^\\d+\\.\\s*"), "").trim() }
    }

    private fun extractTraitInterpretations(text: String): Map<String, String> {
        val traits = listOf(
            "Neuroticism", "Extroversion", "Openness",
            "Agreeableness", "Conscientiousness"
        )

        return traits.mapNotNull { trait ->
            // Look for the trait in the Personality Insights section
            val insightsSection = text.indexOf("## Personality Insights")
            if (insightsSection == -1) return@mapNotNull null

            val traitIndex = text.indexOf(trait, insightsSection)
            if (traitIndex == -1) return@mapNotNull null

            // Find the end of this trait's description (next line or next trait)
            val lineEnd = text.indexOf("\n", traitIndex)
            val nextTraitIndex = traits.map { text.indexOf(it, traitIndex + 1) }
                .filter { it != -1 }
                .minOrNull() ?: text.length

            val description = text.substring(
                traitIndex + trait.length,
                minOf(lineEnd, nextTraitIndex)
            ).trim()

            trait to description
        }.toMap()
    }
}

// Example usage
suspend fun main() {
    val apiService = PersonalityAnalysisService("your-openai-api-key")

    val traits = PersonalityTraits(
        neuroticism = 7,
        extroversion = 4,
        openness = 6,
        agreeableness = 8,
        conscientiousness = 5
    )

    val result = apiService.analyzePersonality(traits)

    result.onSuccess { response ->
        println("Full Analysis:\n${response.fullAnalysis}")

        println("\nPersonality Insights:")
        println("Strengths: ${response.personalityInsights.strengths}")
        println("Challenges: ${response.personalityInsights.challenges}")
        println("Trait Interpretations: ${response.personalityInsights.primaryTraitInterpretations}")

        println("\nDevelopment Strategies:")
        response.developmentStrategies.forEach { println(it) }

        println("\nRecommended Resources:")
        response.recommendedResources.forEach { println(it) }
    }.onFailure {
        println("Error: ${it.message}")
    }
}