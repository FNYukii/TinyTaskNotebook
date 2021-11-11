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
        }

        backButton.setOnClickListener {
            saveRecord()
            finish()
        }

        deleteButton.setOnClickListener {
            deleteRecord()
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveRecord()
    }

    private fun saveRecord() {
        if (id == 0 && contentEdit.text.isNotEmpty()){
            insertRecord()
        } else if (id != 0 && contentEdit.text.isNotEmpty()) {
            updateRecord()
        }
    }

    private fun insertRecord() {
        val maxId = realm.where<Todo>().max("id")?.toInt() ?: 0
        val newId = maxId + 1
        realm.executeTransaction {
            val todo = realm.createObject<Todo>(newId)
            todo.content = contentEdit.text.toString()
        }
    }

    private fun updateRecord() {
        val todo = realm.where<Todo>()
            .equalTo("id", id)
            .findFirst()
        realm.executeTransaction {
            todo?.content = contentEdit.text.toString()
        }
    }

    private fun deleteRecord() {
        val todo = realm.where<Todo>()
            .equalTo("id", id)
            .findFirst()
        realm.executeTransaction {
            todo?.deleteFromRealm()
        }
    }

}