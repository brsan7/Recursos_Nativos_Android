package com.brsan7.recursosnativos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.brsan7.recursosnativos.agenda.AgendaActivity
import com.brsan7.recursosnativos.contatos.ContatosActivity
import com.brsan7.recursosnativos.fotos.FotosActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ToolBarActivitys() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolBar(toolBar, getString(R.string.app_name), false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menuAgenda ->{
                val intent = Intent(this, AgendaActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menuContatos ->{
                val intent = Intent(this, ContatosActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menuFotos ->{
                val intent = Intent(this, FotosActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}