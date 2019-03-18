package com.cxz.aspect.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "cxz----->>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method1();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method2();
                testMethod();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMethod2();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMethod3();
            }
        });
    }

    @DebugTree
    public void method1() {
        Log.e(TAG, "注解方法1执行了...");
    }

    public void method2() {
        int result = 0;
        for (int i = 1; i <= 100; i++) {
            result += i;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "计算结果：" + result);

    }

    public void testMethod() {
        Log.e(TAG, "testMethod: 方法执行了...");
    }

    public void testMethod2() {
        Student student = new Student("张三", 20);
        Log.e(TAG, "testMethod2: " + student.getAge());
        student.setAge(30);
        Log.e(TAG, "testMethod2: " + student.getAge());
    }

    public void testMethod3() {
        try {
            int a = 10 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
