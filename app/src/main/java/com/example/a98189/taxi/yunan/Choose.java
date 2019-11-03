package com.example.a98189.taxi.yunan;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.a98189.taxi.R;

public class Choose extends Activity {

    private Button driver;
    private Button student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        driver = findViewById(R.id.button_driver);
        driver = findViewById(R.id.button_driver);


        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent=new Intent(Choose.this, DriverActivity.class);
                //启动EnterActivity
                startActivity(intent);
                finish();
            }
        });
        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent=new Intent(Choose.this, StudnetActivity.class);
                //启动EnterActivity
                startActivity(intent);
                finish();
            }
        });



    }


}
