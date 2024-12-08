package com.ncorti.kotlin.template.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.ncorti.kotlin.template.app.data.Test

class ItemTestsAdapter(private val dataSet: Array<Test>, private val navController: NavController) :
    RecyclerView.Adapter<ItemTestsAdapter.ViewHolder>() {


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Define click listener for the ViewHolder's View
        val description: TextView = view.findViewById(R.id.tvDescription)
        val duration: TextView = view.findViewById(R.id.tvDuration)
        val name: TextView = view.findViewById(R.id.tvTestName)
        val icon: ImageView = view.findViewById(R.id.vwIcon)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_test, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.icon.setImageResource(dataSet[position].testPhoto)
        viewHolder.name.text = dataSet[position].testName
        viewHolder.description.text = dataSet[position].testDescription
        viewHolder.duration.text = dataSet[position].testDuration

        val bundle = Bundle().apply {
            putSerializable("questions", dataSet[position].questions)
        }
        viewHolder.itemView.setOnClickListener {
            Log.i("AICI BA", dataSet[position].questions.toString())
            navController.navigate(
                R.id.action_mainFragment2_to_questionaireFragment, bundle
            )
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}