package com.codingShuttle.intro;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
//@Scope("prototype")
public class Apple {
//    int val;
//
//    public int getVal() {
//        return val;
//    }
//
//    public void setVal(int val) {
//        this.val = val;
//    }


    void eatApple (){
        System.out.println("Running eatApple");
    }
}
