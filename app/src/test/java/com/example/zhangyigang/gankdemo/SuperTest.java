package com.example.zhangyigang.gankdemo;

import org.junit.Test;

/**
 * @author yigang zhang
 * @date 19-8-6
 */
public class SuperTest {
    public SuperTest(){

    }

void print(){
        System.out.print("1");
}
    @Test
    public void sup(){

        SuperTest test1 = new test1();
        test1.print();
    }
}
class test1 extends SuperTest{
    public test1(){
        super();
    }
    void print(){
        System.out.print("2");
    }
}