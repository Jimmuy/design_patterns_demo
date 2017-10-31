package com.jimmy.designpatterns.activity.observer;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jimmy.designpatterns.R;
import com.jimmy.designpatterns.databinding.ObserverBinding;

import java.util.Observable;

/**
 * @author: jimmy
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2017/10/12     jimmy       v1.0.0        create
 * 观察者模式的简单使用
 **/

public class ObserverPatternActivity extends Activity implements View.OnClickListener {

    private ObserverBinding binding;
    private StringBuilder sb;
    private ObservableImpl impl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_observer);
        binding.btnPublish.setOnClickListener(this);
        sb = new StringBuilder("");

        ObserverImpl observer1 = new ObserverImpl("test 1");
        ObserverImpl observer2 = new ObserverImpl("test 2");
        ObserverImpl observer3 = new ObserverImpl("test 3");
        ObserverImpl observer4 = new ObserverImpl("test 4");
        ObserverImpl observer5 = new ObserverImpl("test 5");


        impl = new ObservableImpl();
        impl.addObserver(observer1);
        impl.addObserver(observer2);
        impl.addObserver(observer3);
        impl.addObserver(observer4);
        impl.addObserver(observer5);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
                sb.delete(0, sb.length());
                impl.notifyAllObserver("通知所有观察者");
                break;
        }
    }

    public class ObserverImpl implements java.util.Observer {
        private String name;

        public ObserverImpl(String observerName) {
            this.name = observerName;
        }


        @Override
        public void update(Observable o, Object arg) {
            sb.append("name: " + name + " observable :" + o + " object :" + arg.toString() + "\n");
            binding.tvReceiver.setText(sb.toString());
            System.out.println(sb.toString());
        }
    }

    public class ObservableImpl extends Observable {
        public void notifyAllObserver(String content) {
            setChanged();
            notifyObservers(content);
        }
    }
}
