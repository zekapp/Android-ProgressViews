package com.app.progresswheel

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.app.progresviews.ProgressLine
import com.app.progresviews.ProgressWheel

import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private var mProgressWheel: ProgressWheel? = null
    private var per = 72
    private var step = 20000

    private var perL = 10
    private var mValue = 1000

    private var mformatter: DecimalFormat? = null

    private var mProgressLine: ProgressLine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Steps cleared", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            clearSteps()
        }

        mProgressWheel = findViewById(R.id.progress)
        mProgressLine = findViewById(R.id.progress_line)

        mformatter = DecimalFormat("#,###,###")

        mProgressWheel!!.setPercentage(per)
        mProgressWheel!!.setStepCountText(mformatter!!.format(step.toLong()))

        mProgressLine!!.setmPercentage(perL)
        mProgressLine!!.setmValueText(mValue.toString())
    }

    private fun clearSteps() {
        per = 0
        step = 0
        mProgressWheel!!.setPercentage(per)
        mProgressWheel!!.setStepCountText(mformatter!!.format((step).toLong()))

        mValue = 0
        mProgressLine!!.setmPercentage(mValue)
        mProgressLine!!.setmValueText(mValue.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    fun change(view: View) {
        per += 72
        step += 20000
        mProgressWheel!!.setPercentage(per)
        mProgressWheel!!.setStepCountText(mformatter!!.format((step).toLong()))
    }

    fun changeLine(view: View) {
        mValue += 1000
        perL += 10
        mProgressLine!!.setmValueText(mValue.toString())
        mProgressLine!!.setmPercentage(perL)
    }

    fun clearAllData(view: View) {
        step = 0
        per = 0

        perL = 0
        mValue = 0

        mProgressLine!!.setmPercentage(perL)
        mProgressLine!!.setmValueText(mValue.toString())

        mProgressWheel!!.setPercentage(per)
        mProgressWheel!!.setStepCountText(mformatter!!.format(step.toLong()))
    }
}
