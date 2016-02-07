package com.app.progresswheel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.progresviews.ProgressLine;
import com.app.progresviews.ProgressWheel;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private ProgressWheel mProgressWheel;
    private int per = 72;
    private int step = 20000;
    private DecimalFormat mformatter;

    private ProgressLine mProgressLine;
    private int mValue = 90000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Steps cleared", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                clearSteps();
            }
        });

        mProgressWheel = (ProgressWheel) findViewById(R.id.progress);
        mProgressLine = (ProgressLine) findViewById(R.id.progress_line);

        mformatter = new DecimalFormat("#,###,###");

        mProgressWheel.setPercentage(per);
        mProgressWheel.setStepCountText(mformatter.format(step));
    }

    private void clearSteps() {
        mProgressWheel.setPercentage(per = 0);
        mProgressWheel.setStepCountText(mformatter.format(step = 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void change(View view) {
        mProgressWheel.setPercentage(per = per + 72);
        mProgressWheel.setStepCountText(mformatter.format(step = step + 20000));
    }

    public void changeLine(View view) {
        mValue += 10000;
        mProgressLine.setmValueText(String.valueOf(mValue));
    }

    public void clearAllData(View view) {
        mValue = 0;
        per = 0;
        step = 0;
        mProgressLine.setmValueText(String.valueOf(mValue));
    }
}
