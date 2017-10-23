package com.jimmy.designpatterns.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.jimmy.designpatterns.R;
import com.jimmy.designpatterns.activity.factory.FactoryPatternActivity;
import com.jimmy.designpatterns.activity.observer.ObserverPatternActivity;
import com.jimmy.designpatterns.activity.prototype.PrototypePatternActivity;
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
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_factory:
//                jump to factory pattern
                intent = new Intent(this, FactoryPatternActivity.class);
                startActivity(intent);
                break;
            case R.id.two:
                intent = new Intent(this, PrototypePatternActivity.class);
                startActivity(intent);
                break;
            case R.id.three:
                intent = new Intent(this, ObserverPatternActivity.class);
                startActivity(intent);
                break;
        }
    }
}
