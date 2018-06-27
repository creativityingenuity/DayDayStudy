package com.example.demo_oneactivity_multifragment;

import java.io.Serializable;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/6/27.
 */

public class User implements Serializable {
    public String name;
    public String pwd;
    public String phone;

    public User(String name, String pwd, String phone) {
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
