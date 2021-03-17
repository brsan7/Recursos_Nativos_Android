package com.brsan7.recursosnativos.contatos

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.brsan7.recursosnativos.R
import com.brsan7.recursosnativos.ToolBarActivitys
import com.brsan7.recursosnativos.contatos.adapter.ContatosAdapter
import kotlinx.android.synthetic.main.activity_contatos.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolBar

/*Permição utilizada no Android Manifest
* <uses-permission android:name="android.permission.READ_CONTACTS" />*/
class ContatosActivity : ToolBarActivitys() {

    val REQUEST_CONTACT = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos)
        setupToolBar(toolBar, getString(R.string.toolBarTitleContatos), true)

        if(checkSelfPermission(this,Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            REQUEST_CONTACT)
        }
        else{
            setContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        if(requestCode == REQUEST_CONTACT) setContacts()

    }

    private fun setContacts() {
        try {
            val cursor: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            rcvContatos.apply {
                layoutManager = LinearLayoutManager(this@ContatosActivity)
                adapter = ContatosAdapter(cursor as Cursor)
            }
            //cursor?.close()
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}