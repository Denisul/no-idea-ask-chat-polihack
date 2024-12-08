package com.ncorti.kotlin.template.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class AnalysisFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup trait progress bars
        setupTraitProgress(view, R.id.leadership, "Leadership", 85)
        setupTraitProgress(view, R.id.emotional_intelligence, "Emotional Intelligence", 92)
        setupTraitProgress(view, R.id.strategic_thinking, "Strategic Thinking", 78)
        setupTraitProgress(view, R.id.adaptability, "Adaptability", 88)

        // Setup career compatibility items
        /*setupCareerItem(view, R.id.team_leader, "Team Leader", "95%")
        setupCareerItem(view, R.id.project_manager, "Project Manager", "92%")
        setupCareerItem(view, R.id.business_consultant, "Business Consultant", "88%")
        setupCareerItem(view, R.id.hr_manager, "HR Manager", "85%")*/
    }

    @SuppressLint("SetTextI18n")
    private fun setupTraitProgress(view: View, traitId: Int, traitName: String, progress: Int) {
        val traitLayout = view.findViewById<View>(traitId)
        traitLayout?.apply {
            findViewById<TextView>(R.id.traitName).text = traitName
            findViewById<TextView>(R.id.traitScore).text = "$progress%"
            findViewById<ProgressBar>(R.id.progressBar).progress = progress
        }
    }

    private fun setupCareerItem(view: View, itemId: Int, role: String, match: String) {
        val careerLayout = view.findViewById<View>(itemId)
        careerLayout?.apply {
            findViewById<TextView>(R.id.roleTitle).text = role
            findViewById<TextView>(R.id.matchPercentage).text = match
        }
    }
}
