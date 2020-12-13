package com.example.annotationinvoke;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.annotationinvoke.annotation.QAnnotation;
import com.example.annotationinvoke.bean.Test;
import com.example.annotationinvoke.utils.AnnotationUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.annotation.Annotation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "JDK 7 ? " + AnnotationUtil.isJDK7OrLower());

                final QAnnotation oldAnnotation = Test.class.getAnnotation(QAnnotation.class);
                Log.d("test", "old = " + oldAnnotation.args());

                AnnotationUtil.alterAnnotationOn(Test.class, QAnnotation.class, new QAnnotation() {
                    @Override
                    public String args() {
                        return "222";
                    }

                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return oldAnnotation.annotationType();
                    }
                });

                QAnnotation newAnnotation = Test.class.getAnnotation(QAnnotation.class);
                Log.d("test", "new = " + newAnnotation.args());

                Snackbar.make(view, "old=" + oldAnnotation.args() + ", new=" + newAnnotation.args(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}