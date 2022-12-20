package com.andreypshenichnyj.iate.laba_3_android

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.andreypshenichnyj.iate.laba_3_android.db.MyDbManager
import com.andreypshenichnyj.iate.laba_3_android.db.MyIntentConstants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    val imageRequestCode = 10
    var tempImageUri = "empty"
    val myDbManager = MyDbManager(this)
    var id = 0
    var isEditState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        myDbManager.openDb()
        getMyIntents()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode){
            val imMyImage = findViewById<ImageView>(R.id.imMyImage)
            imMyImage.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickSave(view: View) {
        val edTitle = findViewById<EditText>(R.id.edTitle)
        val edDesc = findViewById<EditText>(R.id.edDesc)

        CoroutineScope(Dispatchers.Main).launch {
            if (edTitle.text.toString().isEmpty() || edDesc.text.toString().isEmpty()) {
            } else {
                if (isEditState) {
                    myDbManager.updateItem(edTitle.text.toString(), edDesc.text.toString(),tempImageUri, id, getCurrentTime())
                } else {
                    myDbManager.insertToDb(edTitle.text.toString(), edDesc.text.toString(),tempImageUri, getCurrentTime())
                }
                finish()
            }
        }

    }

    fun onEditEnable(view: View) {
        val edTitle = findViewById<EditText>(R.id.edTitle)
        val edDesc = findViewById<EditText>(R.id.edDesc)
        val fbEdit = findViewById<FloatingActionButton>(R.id.fbEdit)
        val imButtonEdit = findViewById<ImageButton>(R.id.imButtonEdit)
        val imButtonDelete = findViewById<ImageButton>(R.id.imButtonDelete)
        val fbAddImage = findViewById<FloatingActionButton>(R.id.fbAddImage)

        edTitle.isEnabled = true
        edDesc.isEnabled = true
        fbEdit.visibility = View.GONE
        if (tempImageUri == "empty"){
            fbAddImage.visibility = View.VISIBLE
            return
        } else {
            imButtonEdit.visibility = View.VISIBLE
            imButtonDelete.visibility = View.VISIBLE
        }
    }

    fun getMyIntents() {
        val i = intent
        val edTitle = findViewById<EditText>(R.id.edTitle)
        val edDesc = findViewById<EditText>(R.id.edDesc)
        val fbEdit = findViewById<FloatingActionButton>(R.id.fbEdit)
        val myImageLayout = findViewById<ConstraintLayout>(R.id.myImageLayout)
        val imMyImage = findViewById<ImageView>(R.id.imMyImage)
        val fbAddImage = findViewById<FloatingActionButton>(R.id.fbAddImage)
        val imButtonDelete = findViewById<ImageButton>(R.id.imButtonDelete)
        val imButtonEdit = findViewById<ImageButton>(R.id.imButtonEdit)

        fbEdit.visibility = View.GONE


        if (i != null) {
            if (i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null) {
                isEditState = true
                edTitle.isEnabled = false
                edDesc.isEnabled = false
                fbEdit.visibility = View.VISIBLE
                fbAddImage.visibility = View.GONE
                edTitle.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))
                edDesc.setText(i.getStringExtra(MyIntentConstants.I_DESC_KEY))
                id = i.getIntExtra(MyIntentConstants.I_ID_KEY, 0)
                if (i.getStringExtra(MyIntentConstants.I_URI_KEY) != "empty"){
                    myImageLayout.visibility = View.VISIBLE
                    tempImageUri = i.getStringExtra(MyIntentConstants.I_URI_KEY)!!
                    imMyImage.setImageURI(Uri.parse(tempImageUri))
                    imButtonDelete.visibility = View.GONE
                    imButtonEdit.visibility = View.GONE
                }
            }
        }
    }

    fun exitActivity(view: View) {
        finish()
    }

    fun onClickAddImage(view: View) {
        val imLayout = findViewById<ConstraintLayout>(R.id.myImageLayout)
        val fbAddImage = findViewById<FloatingActionButton>(R.id.fbAddImage)
        fbAddImage.visibility = View.GONE
        imLayout.visibility = View.VISIBLE
    }

    fun onClickDelete(view: View) {
        val imLayout = findViewById<ConstraintLayout>(R.id.myImageLayout)
        val fbAddImage = findViewById<FloatingActionButton>(R.id.fbAddImage)
        fbAddImage.visibility = View.VISIBLE
        imLayout.visibility = View.GONE
        tempImageUri = "empty"
    }

    fun onClickChooseImage(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"

        startActivityForResult(intent, imageRequestCode)
    }

    private fun getCurrentTime():String{
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yy kk:mm", Locale.getDefault())
        return formatter.format(time)
    }
}