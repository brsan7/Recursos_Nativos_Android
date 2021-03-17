package com.brsan7.recursosnativos.contatos.adapter

import android.database.Cursor
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brsan7.recursosnativos.R
import kotlinx.android.synthetic.main.item_contatos.view.*

class ContatosAdapter(private val mCursor: Cursor): RecyclerView.Adapter<ClienteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder =
        ClienteViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contatos, parent, false))

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        mCursor.moveToPosition(position)
        with(holder.itemView) {
            tvNome.text = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            tvTelefone.text = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            //ivContatoImage.setImageURI(
            //        mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)).toUri())
        }
    }

    override fun getItemCount(): Int = mCursor.count

}

class ClienteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)