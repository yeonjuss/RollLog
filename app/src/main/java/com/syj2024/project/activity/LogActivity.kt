package com.syj2024.project.activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.syj2024.project.databinding.ActivityLogBinding


class LogActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLogBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val selectedDate = intent.getStringExtra("selectedDate")
        binding.selectedDateTv.text = "$selectedDate"

        // data db 파일 만들기
        val db: SQLiteDatabase= openOrCreateDatabase("data", MODE_PRIVATE,null)
        db.execSQL("CREATE TABLE IF NOT EXISTS log(id PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT(80), event TEXT(1000) )")


        binding.logChange.setOnClickListener {

            finish()
        }


        binding.logSubmit.setOnClickListener {

            var selectedDate:String= binding.selectedDateTv.text.toString()
            var title:String= binding.logEt.text.toString()
            var event:String= binding.logEt2.text.toString()

            db.execSQL("INSERT INTO log(date, title, event) VALUES('$selectedDate','$title','$event')")

            Toast.makeText(this, "등록 완료", Toast.LENGTH_SHORT).show()

        }

        val date = intent.getStringExtra("date")
        val title = intent.getStringExtra("title")
        val event = intent.getStringExtra("event")

        binding.selectedDateTv.text = date
        binding.logEt.setText(title)
        binding.logEt2.setText(event)



    } // onCreate

    
}

