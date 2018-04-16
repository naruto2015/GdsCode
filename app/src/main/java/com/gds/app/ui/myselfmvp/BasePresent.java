package com.gds.app.ui.myselfmvp;

/**
 * Created by gaodesong on 18/1/29.
 */

public abstract class BasePresent<T> {

    public T view;

    public void attach(T view){
        this.view=view;
    }

    public void detach(){
        this.view=null;
    }






}
