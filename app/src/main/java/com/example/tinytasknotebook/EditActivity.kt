package com.example.tinytasknotebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.time.ZoneId
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
                achievedDatetimeContainer.visibility = View.VISIBLE
                setAchievedDateTimeText()
            }
        }

        backButton.setOnClickListener {
            saveTodo()
            finish()
        }

        achieveButton.setOnClickListener {
            isAchieved = !isAchieved
            if (isAchieved) {
                achievedDate = Date()
            }
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
            todo.achievedDate = achievedDate
        }
    }

    private fun updateTodo() {
        val todo = realm.where<Todo>()
            .equalTo("id", id)
            .findFirst()
        realm.executeTransaction {
            todo?.content = contentEdit.text.toString()
            todo?.isAchieved = isAchieved
            todo?.achievedDate = achievedDate
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

    private fun setAchievedDateTimeText(){
        //達成年日の文字列を生成
        val dateFormatter = SimpleDateFormat("yyyy年 M月 d日")
        val achievedDateString: String = dateFormatter.format(achievedDate!!)

        //達成曜日の文字列を生成
        val achievedLocalDate = achievedDate!!.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val achievedDayOfWeek: Int =  achievedLocalDate.dayOfWeek.value
        var achievedDayOfWeekString: String = ""
        when(achievedDayOfWeek){
            1 -> achievedDayOfWeekString = " (日)"
            2 -> achievedDayOfWeekString = " (月)"
            3 -> achievedDayOfWeekString = " (火)"
            4 -> achievedDayOfWeekString = " (水)"
            5 -> achievedDayOfWeekString = " (木)"
            6 -> achievedDayOfWeekString = " (金)"
            7 -> achievedDayOfWeekString = " (土)"
        }

        //達成日をTextViewへセット
        achievedDateText.text = (achievedDateString + achievedDayOfWeekString)

        //達成時刻をTextViewへセット
        val timeFormatter = SimpleDateFormat("HH:mm")
        achievedTimeText.text = timeFormatter.format(achievedDate!!)
    }

}