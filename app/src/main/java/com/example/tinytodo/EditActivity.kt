package com.example.tinytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.one_todo.*
import java.util.*

class EditActivity : AppCompatActivity() {

    private var realm = Realm.getDefaultInstance()

    private var id = 0
    private var isAchieved = false
    private var isPinned = false
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
            todoContentText.text = todo?.content
        }

        Log.d("hello", "id: $id")

        saveButton.setOnClickListener {

            Log.d("hello", "hello")

            if(id == 0){
                //新規レコード用のidを生成する
                val maxId = realm.where<Todo>().max("id")?.toInt() ?: 0
                val newId = maxId + 1

                //新規レコード追加
                realm.executeTransaction {
                    val todo = realm.createObject<Todo>(newId)
                    todo.content = todoContentText.text.toString()
                    todo.isPinned = false
                    todo.isAchieved = false
                    todo.achievedDate = Date()
                }
            }

        }


    }
}