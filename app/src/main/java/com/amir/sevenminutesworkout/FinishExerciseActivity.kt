package com.amir.sevenminutesworkout
import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.amir.sevenminutesworkout.databinding.ActivityFinishExerciseBinding
import com.amir.sevenminutesworkout.room.HistoryDAO
import com.amir.sevenminutesworkout.room.HistoryEntity
import com.amir.sevenminutesworkout.room.WorkOutApp
import kotlinx.coroutines.launch
import java.util.*

class FinishExerciseActivity : AppCompatActivity() {
    var binding: ActivityFinishExerciseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinish)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //or finish
            //finish will close all activities and jump to main oen
        }

        val historyDAO = (application as WorkOutApp).db.historyDao()
        addDateToDataBase(historyDAO)
    }


    @SuppressLint("NewApi")
    private fun addDateToDataBase(historyDAO: HistoryDAO) {
        val c = Calendar.getInstance()
        val dateTime = c.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        lifecycleScope.launch { historyDAO.insert(HistoryEntity(date = date)) }
        Log.e("time", date)
    }
}