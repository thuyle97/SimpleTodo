package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.writeLines
//import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.FileUtils;
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdaper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdaper.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // remove item from the list
                listOfTasks.removeAt(position)
                // notify the adapter something changes
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }
        //let's detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            //code in here is going to be executed when the user clicks on a button
//            Log.i("Thuy", "User clicked on button")
//        }

        loadItems()

        //look up recycle view in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdaper(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //set up the button and input field, so that the user can enter a task
        findViewById<Button>(R.id.button).setOnClickListener {
            //grab the text the user input
            val userInputtedTask = inputTextField.text.toString()

            //add string to list of tasks
            listOfTasks.add(userInputtedTask)

            //notify the adapter that the list has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            //reset the text field
            inputTextField.setText("")
            //save items
            saveItems()
        }


    }
    //Save the data that users has inputted

    //save data by writing and reading from a file

    //get the file we need
    fun getDataFile() : File {

        //every line is a task
        return File(filesDir, "data.txt")
    }

    //create a method to get the file needed

    //load the items by reading every line in the data file
    fun loadItems() {
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    //save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}