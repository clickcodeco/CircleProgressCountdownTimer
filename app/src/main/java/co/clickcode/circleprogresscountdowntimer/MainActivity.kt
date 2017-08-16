package co.clickcode.circleprogresscountdowntimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit private var progressBar: ProgressBar
    lateinit private var editTextCount: EditText
    lateinit private var textViewCount: TextView
    lateinit private var imageViewSwitch: ImageView
    lateinit private var imageViewReset: ImageView
    lateinit private var countDownTimer: CountDownTimer
    internal var isRunning = false
    internal var timeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        editTextCount = findViewById<EditText>(R.id.editTextCount)
        textViewCount = findViewById<TextView>(R.id.textViewCount)
        imageViewSwitch = findViewById<ImageView>(R.id.imageViewSwitch)
        imageViewReset = findViewById<ImageView>(R.id.imageViewReset)

        imageViewSwitch.setOnClickListener {
            if (!isRunning) {
                if (editTextCount.text.toString().isEmpty()) {
                    Toast.makeText(this@MainActivity, "Enter number of seconds", Toast.LENGTH_LONG).show()
                } else {
                    start()
                }
            } else {
                stop()
            }
        }

        imageViewReset.setOnClickListener {
            stop()
            isRunning = false
            imageViewSwitch.setImageResource(R.drawable.ic_start)
            textViewCount.text = "" + timeInMillis / 1000
            progressBar.progress = timeInMillis.toInt() / 1000
            progressBar.max = timeInMillis.toInt() / 1000
        }
    }

    private fun start() {
        val textInput = editTextCount.text.toString()
        val timeInput = textInput.toLong() * 1000
        timeInMillis = timeInput

        progressBar.max = timeInMillis.toInt() / 1000
        imageViewSwitch.setImageResource(R.drawable.ic_stop)
        isRunning = true

        countDownTimer = object : CountDownTimer(timeInMillis, 100) {
            override fun onTick(millisUntilFinished: Long) {
                textViewCount.text = Math.round(millisUntilFinished * 0.001f).toString()
                progressBar.progress = Math.round(millisUntilFinished * 0.001f)
            }

            override fun onFinish() {

            }
        }.start()
        countDownTimer.start()
    }

    private fun stop() {
        imageViewSwitch.setImageResource(R.drawable.ic_start)
        isRunning = false
        countDownTimer.cancel()
    }
}