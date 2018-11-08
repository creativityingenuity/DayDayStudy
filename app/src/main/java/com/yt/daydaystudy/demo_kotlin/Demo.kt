package com.yt.daydaystudy.demo_kotlin

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/4/2.
 */
class Demo {
    //D定义
    var a = 1;
    val b: String? = "";
    val arrs = Array(3) {}

    fun main(args:Array<String>){
        var name = "xxxx"
        println(name.length)

    }
    //静态方法
    companion object {
        //null安全处理
        var age: String? = "23"
        //抛出null异常
        var age2 = age!!.toInt()
        //不做处理 返回null
        val age3 = age?.toInt()
        //做null判断 ?:空判断处理
        val age4 = age?.toInt()?:-1
        //拼接字符串

//        val age5 =
        var x = 10;

    }
//    test(i:int){}
    //条件
}