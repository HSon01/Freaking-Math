package com.ssn.freakingmath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ssn.freakingmath.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playgame.setOnClickListener {
            playGame()
        }

    }
    fun playGame(){
        var name = binding.yourname.text.toString()
        if (name == ""){
            Toast.makeText(applicationContext,"Vui Lòng Nhập Tên",Toast.LENGTH_LONG).show()
            return
        }else{
            val intent: Intent = Intent (this, play::class.java)
            intent.putExtra("yourName",name)
            startActivity(intent)
        }
    }
}

