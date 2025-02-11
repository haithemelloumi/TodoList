package com.helloumi.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "completed")
    var completed: Boolean,
    @ColumnInfo(name = "todo")
    var todo: String,
    @ColumnInfo(name = "userId")
    var userId: Int
)
