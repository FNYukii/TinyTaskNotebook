package com.example.tinytodo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.one_todo.view.*

class TodoRecyclerViewAdapter(
    private val todos: OrderedRealmCollection<Todo>
): RealmRecyclerViewAdapter<Todo, TodoRecyclerViewAdapter.CustomViewHolder>(todos, true){

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val todoContentText: TextView = itemView.todoContentText
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.one_todo, viewGroup, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val todo = todos[position]
        holder.todoContentText.text = todo?.content.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("id", todo?.id)
            it.context.startActivity(intent)
        }
    }

}