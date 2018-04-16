package com.gds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by gaodesong on 18/1/12.
 */

@Entity
public class UserBean {


    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "id")
    private int id;

    @Transient
    private String temp;

    private String sex;

    @Unique
    private String testUni;

    @Generated(hash = 2043559232)
    public UserBean(String name, int id, String sex, String testUni) {
        this.name = name;
        this.id = id;
        this.sex = sex;
        this.testUni = testUni;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTestUni() {
        return this.testUni;
    }

    public void setTestUni(String testUni) {
        this.testUni = testUni;
    }


}
