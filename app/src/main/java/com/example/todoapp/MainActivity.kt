package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClick(position: Int) {
                listTasks.removeAt(position)
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

//        listTasks.add("Do Laundry")
//        listTasks.add("Go for walk")

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recylcerView)
        adapter = TaskItemAdapter(listTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //code for user interaction with button
        val inputText = findViewById<EditText>(R.id.addtask)

        findViewById<Button>(R.id.button).setOnClickListener{
            val userInputTask = inputText.text.toString()
            listTasks.add(userInputTask)
            adapter.notifyItemInserted(listTasks.size - 1)
            inputText.setText("")

            saveItems()
        }
    }

    fun getDataFile() : File{
        return File(filesDir, "data.txt")
    }

    fun loadItems(){
        try {
            listTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }

    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listTasks)
        }catch(ioException: IOException){
            ioException.printStackTrace()
        }
    }
}