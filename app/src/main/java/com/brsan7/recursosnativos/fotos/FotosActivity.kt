package com.brsan7.recursosnativos.fotos

import android.os.Bundle
import com.brsan7.recursosnativos.R
import com.brsan7.recursosnativos.ToolBarActivitys
import kotlinx.android.synthetic.main.activity_main.*

class FotosActivity : ToolBarActivitys() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos)
        setupToolBar(toolBar, "Fotos", true)
    }
}