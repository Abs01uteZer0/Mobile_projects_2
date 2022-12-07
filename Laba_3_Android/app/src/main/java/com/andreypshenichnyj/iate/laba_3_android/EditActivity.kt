package com.andreypshenichnyj.iate.laba_3_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.andreypshenichnyj.iate.laba_3_android.db.MyDbManager
import com.andreypshenichnyj.iate.laba_3_android.db.MyIntentConstants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    var id = 0
    var isEditState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        myDbManager.openDb()
        getMyIntents()
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
                    myDbManager.updateItem(edTitle.text.toString(), edDesc.text.toString(), id)
                } else {
                    myDbManager.insertToDb(edTitle.text.toString(), edDesc.text.toString())
                }
                finish()
            }
        }

    }

    fun onEditEnable(view: View) {
        val edTitle = findViewById<EditText>(R.id.edTitle)
        val edDesc = findViewById<EditText>(R.id.edDesc)
        val fbEdit = findViewById<FloatingActionButton>(R.id.fbEdit)

        edTitle.isEnabled = true
        edDesc.isEnabled = true
        fbEdit.visibility = View.GONE
    }

    fun getMyIntents() {
        val i = intent
        val edTitle = findViewById<EditText>(R.id.edTitle)
        val edDesc = findViewById<EditText>(R.id.edDesc)
        val fbEdit = findViewById<FloatingActionButton>(R.id.fbEdit)
        fbEdit.visibility = View.GONE


        if (i != null) {
            if (i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null) {
                isEditState = true
                edTitle.isEnabled = false
                edDesc.isEnabled = false
                fbEdit.visibility = View.VISIBLE
                edTitle.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))
                edDesc.setText(i.getStringExtra(MyIntentConstants.I_DESC_KEY))
                id = i.getIntExtra(MyIntentConstants.I_ID_KEY, 0)
            }
        }
    }

    fun exitActivity(view: View) {
        finish()
    }
}