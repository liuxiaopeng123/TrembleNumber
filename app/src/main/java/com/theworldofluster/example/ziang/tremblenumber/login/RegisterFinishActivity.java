package com.theworldofluster.example.ziang.tremblenumber.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.theworldofluster.example.ziang.tremblenumber.HomeActivity;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

public class RegisterFinishActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_finish);

        TextView textView = findViewById(R.id.activity_register_finish_go);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                PreferenceUtil.putString("finishRegister","yes");
                startActivity(new Intent(RegisterFinishActivity.this,HomeActivity.class));
            }
        });
    }
}
