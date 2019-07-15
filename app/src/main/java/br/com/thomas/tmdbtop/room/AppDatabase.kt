package br.com.thomas.tmdbtop.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.utils.IntegerListConverter
import br.com.thomas.tmdbtop.utils.KeywordListConverter
import br.com.thomas.tmdbtop.utils.StringListConverter

@Database(entities = [(Movie::class)],
    version = 1, exportSchema = false)
@TypeConverters(value = [(StringListConverter::class), (IntegerListConverter::class),
    (KeywordListConverter::class)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}