package com.brsan7.recursosnativos.fotos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.brsan7.recursosnativos.R
import com.brsan7.recursosnativos.ToolBarActivitys
import kotlinx.android.synthetic.main.activity_fotos.*
import kotlinx.android.synthetic.main.activity_main.toolBar

class FotosActivity : ToolBarActivitys() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos)
        setupToolBar(toolBar, "Fotos", true)

        btnSelectImage.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{pickImageFromGalery()}
            }
            else{pickImageFromGalery()}
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode){
            PERMISSION_CODE ->{
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGalery()
                }
                else{Toast.makeText(this,"Permissão Negada", Toast.LENGTH_SHORT).show()}
            }
        }

    }

    private fun pickImageFromGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            ivImage.setImageURI(data?.data)
        }
    }

    companion object{
        private const val PERMISSION_CODE = 7
        private const val IMAGE_PICK_CODE = 1
    }
}