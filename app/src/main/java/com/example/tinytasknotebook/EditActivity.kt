package com.example.tinytasknotebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {

    private var realm = Realm.getDefaultInstance()

    private var id = 0
    private var isAchieved = false
    private var achievedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //Intentにidがあれば取得
        id = intent.getIntExtra("id", 0)

        //既存Todo更新時
        if(id != 0){
            val todo = realm.where<Todo>()
                .equalTo("id", id)
                .findFirst()
            contentEdit.setText(todo?.content)
            isAchieved = todo?.isAchieved!!
            achievedDate = todo.achievedDate
            if (isAchieved) {
                achieveButton.setImageResource(R.drawable.ic_baseline_close_24)
            }
        }

        backButton.setOnClickListener {
            saveTodo()
            finish()
        }

        achieveButton.setOnClickListener {
            isAchieved = !isAchieved
            saveTodo()
            finish()
        }

        deleteButton.setOnClickListener {
            deleteTodo()
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveTodo()
    }

    private fun saveTodo() {
        if (id == 0 && contentEdit.text.isNotEmpty()){
            insertTodo()
        } else if (id != 0 && contentEdit.text.isNotEmpty()) {
            updateTodo()
        }
    }

    private fun insertTodo() {
        val maxId = realm.where<Todo>().max("id")?.toInt() ?: 0
        val newId = maxId + 1
        realm.executeTransaction {
            val todo = realm.createObject<Todo>(newId)
            todo.content = contentEdit.text.toString()
            todo.isAchieved = isAchieved
        }
    }

    private fun updateTodo() {
        val todo = realm.where<Todo>()
            .equalTo("id", id)
            .findFirst()
        realm.executeTransaction {
            todo?.content = contentEdit.text.toString()
            todo?.isAchieved = isAchieved
        }
    }

    private fun deleteTodo() {
        val todo = realm.where<Todo>()
            .equalTo("id", id)
            .findFirst()
        realm.executeTransaction {
            todo?.deleteFromRealm()
        }
    }

}