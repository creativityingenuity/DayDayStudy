package cn.yt.demo_kotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //fbc 控件设置值
        tv.text = "阿尼尼"
        tv.setOnClickListener {
//           Toast.makeText(this,"sdasd",Toast.LENGTH_SHORT).show()
//            toast("dasdad")  错误
        }
    }


    /**
     * 函数扩展
     *  fun 类型.函数(参数)
     */
    fun Context.toast(message:String, length:Int = Toast.LENGTH_LONG){
        Toast.makeText(this,message,length)
    }
}
