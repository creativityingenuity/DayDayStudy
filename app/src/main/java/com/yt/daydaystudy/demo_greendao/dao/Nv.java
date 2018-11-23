package com.yt.daydaystudy.demo_greendao.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/11/23.
 */
@Entity
public class Nv {
    @Id
    Long id;
    int age;
    String name;
    @Generated(hash = 1357819058)
    public Nv(Long id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }
    @Generated(hash = 1947086182)
    public Nv() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
