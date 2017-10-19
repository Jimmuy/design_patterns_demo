package com.jimmy.designpatterns.activity.prototype;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jimmy.designpatterns.R;
import com.jimmy.designpatterns.databinding.PrototypeBinding;

/**
 * @author: jimmy
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2017/10/12     jimmy       v1.0.0        create
 * <p>
 * <p>
 * 原型设计模式：通过拷贝原型创建新的对象，分为深拷贝和浅拷贝，在使用cloneable实现拷贝的时候，构造函数并不会执行。
 * <p>
 * 使用场景：
 * 类的初始化消耗资源，包括数据资源，硬件资源，通过原型拷贝避免资源的消耗
 * 通过new产生一个对象需要繁琐的数据准备和权限
 * 一个对象需要提供给其他调用者使用，而且各个调用者可能都需要修改其值得时候，可以用原型模式拷贝多个对象提供给其他对象访问
 **/

public class PrototypePatternActivity extends Activity {

    private PrototypeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prototype);
        login();
    }

    /**
     * 模拟登陆
     */
    private void login() {
        //异步请求后存储用户信息到内存
        User user = new User();
        user.name = "小明";
        user.tel = "110";
        user.address = new Address();

        //拷贝对象
        User user1 = user.clone();
        user1.name = "小红";


        String log = "user: " + user.toString() + user.log() + "\n" + "user1: " + user1.toString() + user1.log();
        binding.tvLog.setText(log);
    }

    class User implements Cloneable {
        public String name;
        public String tel;
        public Address address;

        public User clone() {
            try {
                final User clone = (User) super.clone();
                clone.address = address.clone();
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }


        public String log() {
            return "name:" + name + "tel:" + tel;
        }
    }

    class Address implements Cloneable {
        public String city;
        public String number;

        @Override
        protected Address clone() {
            try {
                return (Address) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
