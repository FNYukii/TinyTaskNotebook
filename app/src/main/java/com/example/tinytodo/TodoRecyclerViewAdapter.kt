package com.example.tinytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.one_todo.view.*

class TodoRecyclerViewAdapter(
    private val realmResults: RealmResults<Todo>
): RecyclerView.Adapter<TodoRecyclerViewAdapter.CustomViewHolder>(){

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val todoContentText: TextView = itemView.todoContentText
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.one_todo, viewGroup, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return realmResults.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val todo = realmResults[position]
        holder.todoContentText.text = todo?.content.toString()
    }

}