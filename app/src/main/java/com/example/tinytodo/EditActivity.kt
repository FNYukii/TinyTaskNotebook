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
            if(id == 0){
                //新規レコード用の値を用意する
                val maxId = realm.where<Todo>().max("id")?.toInt() ?: 0
                val newId = maxId + 1

                //新規レコード用意
                val newTodo = Todo(
                    id = newId,
                    content = contentEdit.text.toString(),
                    isPinned = false,
                    isAchieved = false,
                    achievedDate = Date()
                )

                //新規レコード追加
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(newTodo)
                realm.commitTransaction()
            }
            finish()
        }


    }
}