package com.brsan7.recursosnativos.agenda

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*
import com.brsan7.recursosnativos.R
import com.brsan7.recursosnativos.ToolBarActivitys
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_main.toolBar

class AgendaActivity : ToolBarActivitys() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)
        setupToolBar(toolBar, getString(R.string.toolBarTitleAgenda), true)
        btnSetEvent.setOnClickListener{
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CONTENT_URI)
                .putExtra(TITLE,"BootCamp Everis")
                .putExtra(EVENT_LOCATION,"On Line")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + (60*60*1000))

            startActivity(intent)
        }
    }
}