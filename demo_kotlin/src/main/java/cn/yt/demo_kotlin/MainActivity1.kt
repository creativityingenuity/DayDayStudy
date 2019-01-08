package cn.yt.demo_kotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity1 : AppCompatActivity() {

    var sum: Int = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //实例化demo 类 去掉new
        var demo = Demo()
        //调用属性 可直接调用
        demo.name = "yt" //print(demo.name)
        demo.sex = "女"


        //调用嵌套类
//        var demoInner : Demo.DemoInner = Demo().DemoInner()

















       


        //延迟加载
//        val mModel : Ext by lazy {
//            toast("")
//        }

        //实现一个加法运算
        var num = add(3, 2)
        println(num)
//        var arr : Array<String> = {}
//        for (s in arr.indices) {
//
//        }
    }

    //简写
    fun add1(a: Int, b: Int) = a + b;

    //无返回值Unit
//    fun add2(a : Int,b:Int){ print("kaskda ")}
    private fun add(a: Int, b: Int): Int {
        return a + b
    }

    //简单
    fun normal(){
        for (i in 1..100){
            println(i)
        }

        whenTest(6)
    }

    private fun whenTest(age : Int) {
//        when(age){
//            1->"sadasda"
//            2->"sfasfa"
//            else->{
//                "这个是default"
//            }
//        }
    }

    /**
     * 函数扩展
     *  fun 类型.函数(参数)
     */
    fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, message, length)
    }
}
