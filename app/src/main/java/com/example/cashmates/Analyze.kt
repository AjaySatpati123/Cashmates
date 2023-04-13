package com.example.cashmates

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class Analyze : AppCompatActivity() {
    private val db = Firebase.firestore
    private val myCollectionRef = db.collection("posts")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        readData(object: MyCallback {
            override fun onCallback(value: HashMap<String,Int>, value2: HashMap<String,Int>) {
                Log.d("Problem","${value2.size}")

                val barGraph: BarChart

                val pieChart: PieChart = findViewById(R.id.pieChart)
                val list: ArrayList<PieEntry> = ArrayList()
                value["Food"]?.let { PieEntry(it.toFloat(),"Food") }?.let { list.add(it) }
                value["Grocery"]?.let { PieEntry(it.toFloat(),"Grocery") }?.let { list.add(it) }
                value["Travel"]?.let { PieEntry(it.toFloat(),"Travel") }?.let { list.add(it) }
                value["One Time Cost"]?.let { PieEntry(it.toFloat(),"One Time Cost") }?.let { list.add(it) }
                value["Entertainment"]?.let { PieEntry(it.toFloat(),"Entertainment") }?.let { list.add(it) }
                value["Other Cost"]?.let { PieEntry(it.toFloat(),"Other Cost") }?.let { list.add(it) }
                val pieDataSet = PieDataSet(list,"Spends")
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS, 255)
                pieDataSet.valueTextSize = 15f
                pieDataSet.valueTextColor = Color.BLACK
                val pieData = PieData(pieDataSet)
                pieChart.data = pieData
                pieChart.description.text= "Pie Chart"
                pieChart.centerText = "Spends"
                pieChart.animateY(2000)
            }
        })
    }
    private fun readData(myCallback : MyCallback) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        myCollectionRef.whereEqualTo("createdBy.uid", userId).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val hashMap : HashMap<String, Int> = HashMap()
                val hash : HashMap<String, Int> = HashMap()
                for (document in task.result) {
                    val time = document.getLong("createdAt")
                    val parsedTime = time?.let { convertLongToTime(it) }
                    val amount = document.getString("amount")?.let { Integer.parseInt(it) }
                    val locate = document.getString("locate").toString()
                    if (hashMap.containsKey(locate)) {
                        val count = hashMap[locate] ?: 0
                        hashMap[locate] = count + amount!!
                    } else {
                        hashMap[locate] = amount!!
                    }
                    if(hash.containsKey(parsedTime)){
                        val count = hash[parsedTime] ?: 0
                        hash[parsedTime!!]=count+amount
                    } else {
                        hash[parsedTime!!]=amount
                    }
                }

                myCallback.onCallback(hashMap,hash)
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }
    interface MyCallback {
        fun onCallback(value: HashMap<String,Int>, value2: HashMap<String,Int>)
    }
}