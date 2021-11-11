package com.example.tinytasknotebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onStart() {
        super.onStart()

        //Todosを取得
        val realm: Realm = Realm.getDefaultInstance()
        val achievedTodos: RealmResults<Todo> = realm.where<Todo>()
            .equalTo("isAchieved", true)
            .findAll()
            .sort("id", Sort.DESCENDING)

        //NoTodoText
        if (achievedTodos.size != 0) {
            noAchievedTodoText.visibility = View.INVISIBLE
        } else {
            noAchievedTodoText.visibility = View.VISIBLE
        }

        //RecyclerView設定
        achievedTodoRecyclerView.layoutManager = LinearLayoutManager(this.context)
        achievedTodoRecyclerView.adapter = TodoRecyclerViewAdapter(achievedTodos)
    }

}