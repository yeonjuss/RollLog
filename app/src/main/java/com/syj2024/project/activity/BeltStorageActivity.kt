package com.syj2024.project.activity

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syj2024.project.R
import com.syj2024.project.adapter.BeltStorageAdapter
import com.syj2024.project.databinding.RecyclerItemListBeltActivityBinding
import com.syj2024.project.databinding.ToolbarBeltBinding
import com.syj2024.project.fragment.Item2
import java.util.Calendar


class BeltStorageActivity : AppCompatActivity() {

    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    val beltList: MutableList<BeltStorageItme> = mutableListOf()
    private val binding by lazy { ToolbarBeltBinding.inflate(layoutInflater) }
//    val recyclerView:RecyclerView by lazy { findViewById(R.id.recycler_view1) }
//    val recyclerView2:RecyclerView by lazy { findViewById(R.id.recycler_view2) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.iv.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, {_, year, month, day ->
                binding.tvDate.text = year.toString() + "/" + (month+1).toString() + "/" + day.toString()
            },year,month,day)
            datePickerDialog.show()

        }




//        beltList.add( BeltStorageItme("0 grau"," ",R.drawable.ic_action_calender2))
//        beltList.add( BeltStorageItme("1 grau"," ",R.drawable.ic_action_calender2))
//        beltList.add( BeltStorageItme("2 grau"," ",R.drawable.ic_action_calender2))
//        beltList.add( BeltStorageItme("3 grau"," ",R.drawable.ic_action_calender2))
//        beltList.add( BeltStorageItme("4 grau"," ",R.drawable.ic_action_calender2))


//        recyclerView.adapter= BeltStorageAdapter(this,beltList)
//        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//
//
//        recyclerView2.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)





    }
}
