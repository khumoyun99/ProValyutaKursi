package com.example.provalyutakursi.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if("android.net.conn.CONNECTIVITY_CHANGE" == intent.action){
            val noConnectivity:Boolean = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false)
            if (noConnectivity){
                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
            }
        }

    }
}