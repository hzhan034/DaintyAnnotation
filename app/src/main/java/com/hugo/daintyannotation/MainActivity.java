package com.hugo.daintyannotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.TestAnnotation;


public class MainActivity extends AppCompatActivity {

    @TestAnnotation("onCreate")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cat mCat = new Cat();
        mCat.catName = "mmm";
//        Dog mDog = (Dog) BeanUtils.transCat2Dog(mCat);
//        Log.d("MainActivity" , "MainActivity : "+ mDog.dongName);
//        Log.d("MainActivity" , "MainActivity2 : "+ "tttt");
    }
}
