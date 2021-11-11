package com.example.tinytodo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        fab.setOnClickListener {
            val intent = Intent(this.context, EditActivity::class.java)
            startActivity(intent)
        }

        val realm: Realm = Realm.getDefaultInstance()
        val todos: RealmResults<Todo> = realm.where<Todo>()
            .equalTo("isAchieved", false)
            .findAll()
            .sort("id", Sort.DESCENDING)

        todoRecyclerView.adapter = TodoRecyclerViewAdapter(todos)


    }

}