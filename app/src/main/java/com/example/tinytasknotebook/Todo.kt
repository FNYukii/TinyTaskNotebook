package com.example.tinytasknotebook

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Todo (
    @PrimaryKey
    var id: Int = 0,
    var content: String = "",
    var isPinned: Boolean = false,
    var isAchieved: Boolean = false,
    var achievedDate: Date? = null
): RealmObject()