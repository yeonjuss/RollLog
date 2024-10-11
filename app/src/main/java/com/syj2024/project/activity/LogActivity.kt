package com.syj2024.project.activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.syj2024.project.R
import com.syj2024.project.adapter.LogImageAdapter
import com.syj2024.project.databinding.ActivityLogBinding
import com.syj2024.project.decorate.EventDecorator
import java.util.UUID


class LogActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLogBinding.inflate(layoutInflater) }
    private val photoList: MutableList<Uri?> = mutableListOf()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: LogImageAdapter
    private val recordedDates = HashSet<CalendarDay>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val calendarView = findViewById<MaterialCalendarView>(R.id.mcv)

        val selectedDate = intent.getStringExtra("selectedDate") ?: ""
        val date = intent.getStringExtra("date")
        val title = intent.getStringExtra("title")
        val event = intent.getStringExtra("event")
        val receivedPhotoList: ArrayList<Uri>? =
            intent.getParcelableArrayListExtra("photoList")  // Uri 리스트 받기


//        if (!photoPathsString.isNullOrEmpty()) {
//            // 쉼표로 구분된 경로 문자열을 리스트로 변환
//            val photoList: List<Uri> = photoPathsString.split(",")
//                .filter { it.isNotBlank() }
//                .map { Uri.parse(it.trim()) }
//
//        }

        // UI 초기화
        binding.selectedDateTv.text = date ?: selectedDate
        binding.logEt.setText(title)
        binding.logEt2.setText(event)

        // 받은 photoList가 비어있지 않으면 photoList에 추가
        if (!receivedPhotoList.isNullOrEmpty()) {
            photoList.addAll(receivedPhotoList) // photoList에 데이터 추가
        }
        // 사진이 있는 경우 ImageView 표시
//        } else {
//            // 사진이 없는 경우, 빈 RecyclerView 또는 기본 이미지 처리 (옵션)
//            binding.recyclerViewLog.visibility = View.GONE
//        }

        // RecyclerView에 어댑터 설정
        adapter = LogImageAdapter(this, photoList)
        binding.recyclerViewLog.adapter = adapter
        binding.recyclerViewLog.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        // sqlite 데이터베이스 생성
        val db: SQLiteDatabase = openOrCreateDatabase("data", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS log(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT(80), event TEXT(1000), photo TEXT)")


        binding.logSubmit.setOnClickListener {
            finish()
        }


        // 등록 버튼 눌렀을때 데이터 전송
        binding.logSubmit.setOnClickListener {
            val dateText = binding.selectedDateTv.text.toString()
            val titleText = binding.logEt.text.toString()
            val eventText = binding.logEt2.text.toString()
            val photoPaths = photoList?.map { it.toString() }?.joinToString(",")


            val values = ContentValues().apply {
                put("date", dateText)
                put("title", titleText)
                put("event", eventText)
                put("photo", photoPaths)
            }


                db.insert("log", null, values)
                Toast.makeText(this, "등록 완료", Toast.LENGTH_SHORT).show()

                finish()


            }

            // 사진 등록하기
            binding.logPhoto.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_PICK_IMAGES).
                putExtra(
                    MediaStore.EXTRA_PICK_IMAGES_MAX,
                    3
                )
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
                        adapter.notifyDataSetChanged()


                    }

                }

    }   //onCreate
}
