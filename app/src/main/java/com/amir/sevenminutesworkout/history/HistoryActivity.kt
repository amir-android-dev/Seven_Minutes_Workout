package com.amir.sevenminutesworkout.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amir.sevenminutesworkout.databinding.ActivityHistoryBinding
import com.amir.sevenminutesworkout.room.WorkOutApp
import com.amir.sevenminutesworkout.room.HistoryDAO
import com.amir.sevenminutesworkout.room.HistoryEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }

        val historyDAO = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(historyDAO)
    }

    private fun getAllCompletedDates(historyDAO: HistoryDAO) {


        lifecycleScope.launch {

            historyDAO.fetchAllHistories().collect { allCompletedDatesList ->
                var i = allCompletedDatesList
                for (i in allCompletedDatesList) {
                    Log.e("Date", "" + i.date)
                    val historyAdapter = HistoryAdapter(allCompletedDatesList as ArrayList<HistoryEntity>)
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    binding?.rvHistory?.adapter = historyAdapter
                }
                if (i.isEmpty()) {
                    binding?.tvNoData?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.INVISIBLE
                }


            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}