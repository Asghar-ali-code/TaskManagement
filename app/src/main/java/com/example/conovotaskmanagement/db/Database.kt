package com.example.conovotaskmanagement.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [TaskEntity::class],
    version = 2,
    autoMigrations = [ AutoMigration(from=1, to=2, spec = com.example.conovotaskmanagement.db.Database.Migration1To2::class)]
)
abstract class Database: RoomDatabase() {

    abstract val dao: Dao

    @RenameColumn(tableName = "task", fromColumnName = "isDone", toColumnName = "isTaskCompleted")
    class Migration1To2:AutoMigrationSpec
}