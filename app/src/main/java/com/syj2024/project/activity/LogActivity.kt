package com.syj2024.project.activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.syj2024.project.R
import com.syj2024.project.adapter.LogImageAdapter
import com.syj2024.project.adapter.LogListAdapter
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
        val title = intent.getStringExtra("title")
        val event = intent.getStringExtra("event")


        // UI 초기화
        binding.selectedDateTv.text = date ?: selectedDate
        binding.logEt.setText(title)
        binding.logEt2.setText(event)

        val db: SQLiteDatabase = openOrCreateDatabase("data", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS log(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT(80), event TEXT(1000) )")

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
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES).putExtra(
                MediaStore.EXTRA_PICK_IMAGES_MAX, 10
            )
            resultLauncher.launch(intent)

        }


        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                // 일단 , 선택을 안하고 돌아올 수 있기에..
                if (result.resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "선택하지 않았습니다.", Toast.LENGTH_SHORT).show()

                } else {
                    // 기존 리스트를 모두 제거
                    photoList.clear()

                    //1개를 선택했을때는 uri로 선택사진의 정보가 전달되어 옴..2개 이상이면 uri data는 null 값이 되고. ClipData로 전달 되어 옴
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
