package cn.yt.demo_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        //
        printUserName(object : Login() {

        })
        //匿名内部类 object是一个对象 实现View.OnClickListener
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
            }
        })
    }

    fun foo() {
        //匿名内部类 object是一个对象 实现View.OnClickListener
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
            }
        })
        //创建一个匿名对象
        val abc = object {
            var a = 1
            var b = 2
        }
        Toast.makeText(this, "${abc.a}${abc.b}", Toast.LENGTH_SHORT).show()
    }


    fun printUserName(login: Login) {
        login.printName()
    }


//    fun method(){}
//    //带参数
//    fun method1(str : String, int : Int){}
//    //带返回值
//    fun method2(str : String, int : Int) : String{ return ""}
//    //默认参数 当调用方法时没有给参数传值的时候 使用默认值
//    fun method3(str: String = "daf", i : Int = 1){}
//    //命名参数

}

