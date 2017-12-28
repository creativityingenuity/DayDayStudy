package com.yt.daydaystudy.demo_greendao.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ${zhangyuanchao} on 2017/12/28.
 */
@Entity
public class User {
    /*定义主键*/
    @Id(autoincrement = true)
    private long id;

    private String name;
    private int age;
    private String sex;

    /*@Generated(hash = 664611791)中的hash参数 是build->Make Project之后生成的*/
    @Generated(hash = 664611791)
    public User(long id, String name, int age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
