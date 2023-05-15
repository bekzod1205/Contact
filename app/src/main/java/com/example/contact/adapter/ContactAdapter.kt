package com.example.contact.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.database.Contact
import com.example.contact.databinding.ContactItemBinding

class ContactAdapter(
    var list: MutableList<Contact>,
    var mycontact: myContact,
    var context: Context,
    var activity: Activity
) :
    RecyclerView.Adapter<ContactAdapter.ContactHolder>() {
    inner class ContactHolder(binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.contactName
        val phone = binding.contactNumber
        val card = binding.contactCard
        val call = binding.call
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(
            ContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.phone.text = item.phone_number
        holder.card.setOnClickListener {
            mycontact.onClick(item)
        }
        holder.call.setOnClickListener {
            openCall(item.phone_number)
        }
    }

    interface myContact {
        fun onClick(contact: Contact)
    }

    fun openCall(number: String) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(android.Manifest.permission.CALL_PHONE),
                1
            )
        } else {
            if (number.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$number")
                activity.startActivity(callIntent)
            }
        }
    }
}