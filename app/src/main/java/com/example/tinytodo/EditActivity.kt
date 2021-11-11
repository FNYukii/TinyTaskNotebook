package com.example.tinytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
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
            contentEdit.setText(todo?.content)
            saveButton.text = "更新"
            deleteButton.visibility = View.VISIBLE
        }

        saveButton.setOnClickListener {
            if(id == 0){
                //新規レコード用の値を用意する
                val maxId = realm.where<Todo>().max("id")?.toInt() ?: 0
                val newId = maxId + 1

                //新規レコード追加
                realm.executeTransaction {
                    val todo = realm.createObject<Todo>(newId)
                    todo.content = contentEdit.text.toString()
                }
            }
            if(id != 0){
                val todo = realm.where<Todo>()
                    .equalTo("id", id)
                    .findFirst()
                realm.executeTransaction {
                    todo?.content = contentEdit.text.toString()
                }
            }
            finish()
        }

        deleteButton.setOnClickListener {
            val todo = realm.where<Todo>()
                .equalTo("id", id)
                .findFirst()
            realm.executeTransaction {
                todo?.deleteFromRealm()
            }
            finish()
        }

    }
}