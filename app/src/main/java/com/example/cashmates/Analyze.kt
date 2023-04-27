package com.example.cashmates

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_analyze.*
import kotlinx.android.synthetic.main.activity_analyze.view.*
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
            override fun onCallback(value: HashMap<String,Int>, value2: MutableMap<String,Int>) {

//                BarGraph
                val sortedMap: MutableMap<String, Int> = TreeMap(value2)
                val chart = findViewById<BarChart>(R.id.barChart)
                chart.setDrawBarShadow(false)
                chart.setDrawValueAboveBar(true)
                chart.description.isEnabled = false
                chart.setPinchZoom(false)
                chart.setDrawGridBackground(false)
                val labels = ArrayList<String>()
                for ((keys, _) in sortedMap.entries) {
                    labels.add(keys)
                }
                val list2 = ArrayList<BarEntry>()
                var i=0
                for ((_, values) in sortedMap.entries) {
                    list2.add(BarEntry(i.toFloat(),values.toFloat()))
                    i++
                }
                val maxValue = list2.size.toFloat()
                val dataset = BarDataSet(list2, "Legend")
                dataset.colors = ColorTemplate.MATERIAL_COLORS.toList()
                val barData = BarData(dataset)
                barData.barWidth = 0.8f;
                chart.data = barData
                chart.xAxis.valueFormatter= IndexAxisValueFormatter(labels)
                chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                chart.axisLeft.axisMinimum = 0f
                chart.axisRight.axisMinimum = 0f
                chart.axisRight.setDrawLabels(false)
                chart.legend.isEnabled = false
                chart.animateY(500)
                chart.setVisibleXRangeMaximum(3F)
                chart.moveViewToX(maxValue)
                chart.invalidate()

//                PieChart
                val pieChart: PieChart = findViewById(R.id.pieChart)
                val list: ArrayList<PieEntry> = ArrayList()
                value["Food"]?.let { PieEntry(it.toFloat(),"Food") }?.let { list.add(it) }
                value["Grocery"]?.let { PieEntry(it.toFloat(),"Grocery") }?.let { list.add(it) }
                value["Travel"]?.let { PieEntry(it.toFloat(),"Travel") }?.let { list.add(it) }
                value["One Time Cost"]?.let { PieEntry(it.toFloat(),"One Time Cost") }?.let { list.add(it) }
                value["Entertainment"]?.let { PieEntry(it.toFloat(),"Entertainment") }?.let { list.add(it) }
                value["Other Cost"]?.let { PieEntry(it.toFloat(),"Other Cost") }?.let { list.add(it) }
                val pieDataSet = PieDataSet(list,"Legend")
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS, 255)
                pieDataSet.valueTextSize = 10f
                pieDataSet.valueTextColor = Color.BLACK
                val pieData = PieData(pieDataSet)
                pieChart.data = pieData
                pieChart.description.text= ""
                pieChart.centerText = "Spends"
                pieChart.animateY(500)
                pieChart.holeRadius = 30f
                pieChart.transparentCircleRadius = 35f
                pieChart.setEntryLabelTextSize(8f)
                pieChart.legend.isEnabled = false
            }
        })
    }
    private fun readData(myCallback : MyCallback) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        myCollectionRef.whereEqualTo("createdBy.uid", userId).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val hashMap : HashMap<String, Int> = HashMap()
                val hash: MutableMap<String, Int>  = HashMap()
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
        val format = SimpleDateFormat("yyyy.MM")
        return format.format(date)
    }
    interface MyCallback {
        fun onCallback(value: HashMap<String,Int>, value2: MutableMap<String,Int>)
    }
}