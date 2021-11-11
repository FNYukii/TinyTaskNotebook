package com.example.tinytodo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Floating action button
        fab.setOnClickListener {
            Log.d("hello", "hello")
            val intent = Intent(this.context, EditActivity::class.java)
            startActivity(intent)
        }

        //Todosを取得
        val realm: Realm = Realm.getDefaultInstance()
        val todos: RealmResults<Todo> = realm.where<Todo>()
            .findAll()
            .sort("id", Sort.DESCENDING)

        Log.d("hello", "todos.count: ${todos.size}")

        //RecyclerView設定
        todoRecyclerView.layoutManager = GridLayoutManager(this.context, 1)
        todoRecyclerView.adapter = TodoRecyclerViewAdapter(todos)


    }


}