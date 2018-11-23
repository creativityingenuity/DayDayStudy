package com.yt.daydaystudy.demo_greendao.dao;

import com.yt.daydaystudy.demo_greendao.greendao.DaoSession;
import com.yt.daydaystudy.demo_greendao.greendao.NanDao;
import com.yt.daydaystudy.demo_greendao.greendao.NvDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/11/23.
 */
@Entity
public class Nan {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String age;
    private String sex;
    private String salary;
    private Long nvId;
    @ToOne(joinProperty = "nvId")
    private Nv nv;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1432478600)
    private transient NanDao myDao;
    @Generated(hash = 1085571147)
    public Nan(Long id, String name, String age, String sex, String salary,
            Long nvId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
        this.nvId = nvId;
    }
    @Generated(hash = 2128352814)
    public Nan() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSalary() {
        return this.salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
    public Long getNvId() {
        return this.nvId;
    }
    public void setNvId(Long nvId) {
        this.nvId = nvId;
    }
    @Generated(hash = 2074203955)
    private transient Long nv__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1925020120)
    public Nv getNv() {
        Long __key = this.nvId;
        if (nv__resolvedKey == null || !nv__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NvDao targetDao = daoSession.getNvDao();
            Nv nvNew = targetDao.load(__key);
            synchronized (this) {
                nv = nvNew;
                nv__resolvedKey = __key;
            }
        }
        return nv;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 142582350)
    public void setNv(Nv nv) {
        synchronized (this) {
            this.nv = nv;
            nvId = nv == null ? null : nv.getId();
            nv__resolvedKey = nvId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442809554)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNanDao() : null;
    }
}
