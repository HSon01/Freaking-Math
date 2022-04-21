package com.ssn.freakingmath

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ssn.freakingmath.databinding.ActivityPlayBinding
import kotlinx.coroutines.*
import kotlin.random.Random

private var second = 1000
private var point = 0
private var resultTrue = 0
private var currentProgr = 0
private var nameCurrent = ""


class play : AppCompatActivity() {
    lateinit var binding: ActivityPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        point = 0
        nameCurrent = intent.getStringExtra("yourName").toString()
        binding.HiPlayname.text = "Hi, " + nameCurrent
        runGame()
    }

    fun showDiaLog() {
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.game_over)
        var play = dialog.findViewById(R.id.play) as ImageButton
        var back = dialog.findViewById(R.id.back) as ImageButton
        var name = dialog.findViewById(R.id.Name) as TextView
        var poit = dialog.findViewById(R.id.pointT) as TextView

        name.text = binding.HiPlayname.text.toString()
        poit.text = binding.point.text.toString()

        play.setOnClickListener {
            val intent: Intent = Intent(this, com.ssn.freakingmath.play::class.java)
            intent.putExtra("yourName", nameCurrent)
            startActivity(intent)
        }

        back.setOnClickListener {
            val intent: Intent = Intent(this, com.ssn.freakingmath.MainActivity::class.java)
            startActivity(intent)
        }

        dialog.setCancelable(false)
        dialog.show()

    }

    fun mathO(): List<Int> {
        var coutn = Random.nextInt(0, 5)
        var result = 0
        var first = Random.nextInt(0, 10)
        var second = Random.nextInt(0, 10)
        var resultfasle = Random.nextInt(0, 20)
        resultTrue = first + second
        if (coutn % 2 == 0) {
            result = resultTrue
        } else {
            result = resultfasle
        }
        return arrayListOf(first, second, result, resultTrue)
    }

    fun newGame() {
        var lits = mathO()
        binding.progressBar.max = second
        binding.progressBar.secondaryProgress = second
        binding.arg1.text = lits[0].toString()
        binding.arg2.text = lits[1].toString()
        binding.result.text = lits[2].toString()
        binding.point.text = point.toString()
        currentProgr = binding.progressBar.max
        var job = lifecycleScope.launch(Dispatchers.IO) {

            while (currentProgr > 0) {
                currentProgr--
                withContext(Dispatchers.Main) {
                    binding.progressBar.secondaryProgress = currentProgr
                }
            }
            if (currentProgr <= 0) {
                withContext(Dispatchers.Main) {
                    showDiaLog()
                }
            }

        }
        job.start()
    }

    fun runGame() {
        newGame()
        binding.resultTrue.setOnClickListener {
            if (binding.result.text.toString() == resultTrue.toString()) {
                second = second + 1000
                point++
                runGame()
            } else {
                showDiaLog()
                Toast.makeText(applicationContext, "Thua Roi", Toast.LENGTH_LONG).show()
            }
        }
        binding.resultFalse.setOnClickListener {
            if (binding.result.text.toString() != resultTrue.toString()) {
                second = second + 1000
                point++
                runGame()
            } else {
                showDiaLog()
                Toast.makeText(applicationContext, "Thua Roi", Toast.LENGTH_LONG).show()
            }
        }
    }
}

