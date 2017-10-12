package com.jimmy.designpatterns.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.jimmy.designpatterns.R;
import com.jimmy.designpatterns.activity.FactoryPatternActivity;
import com.jimmy.designpatterns.databinding.MainBinding;

public class MainActivity extends Activity implements View.OnClickListener {

    private MainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_factory:
//                jump to factory pattern
                Intent intent = new Intent(this, FactoryPatternActivity.class);
                startActivity(intent);
                break;
        }
    }
}
