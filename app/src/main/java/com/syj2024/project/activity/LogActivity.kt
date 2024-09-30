package com.syj2024.project.activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.syj2024.project.adapter.LogImageAdapter
import com.syj2024.project.databinding.ActivityLogBinding


class LogActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLogBinding.inflate(layoutInflater) }
    val photoList: MutableList<Uri?> = mutableListOf()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: LogImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val selectedDate = intent.getStringExtra("selectedDate") ?: ""
        val date = intent.getStringExtra("date")
//        val img = intent.getStringExtra("img")
        val title = intent.getStringExtra("title")
        val event = intent.getStringExtra("event")


        // UI 초기화
        binding.selectedDateTv.text = date ?: selectedDate
        binding.logEt.setText(title)
        binding.logEt2.setText(event)

        val db: SQLiteDatabase = openOrCreateDatabase("data", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS log(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, img INT,title TEXT(80), event TEXT(1000) )")

        binding.logChange.setOnClickListener {
            finish()
        }

        binding.logSubmit.setOnClickListener {
            val dateText = binding.selectedDateTv.text.toString()
            val titleText = binding.logEt.text.toString()
            val eventText = binding.logEt2.text.toString()


            val values = ContentValues().apply {
                put("date", dateText)
                put("title", titleText)
                put("event", eventText)
            }

            db.insert("log", null, values)
            Toast.makeText(this, "등록 완료", Toast.LENGTH_SHORT).show()


        }
        binding.logPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES).putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 10)
            resultLauncher.launch(intent)

        }


        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->


                if (result.resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    photoList.clear()

                    if (result.data?.data !== null) { // 1개 선택
                        photoList.add(result.data?.data)

                    } else { // 2개 이상을 선택
                        var cnt: Int = result.data?.clipData?.itemCount!!
                        for (i in 0 until cnt) photoList.add(result.data?.clipData?.getItemAt(i)?.uri)
                    }

                    binding.recyclerViewLog.adapter = LogImageAdapter(this, photoList)

                }
            }
    } //onCreate


}

