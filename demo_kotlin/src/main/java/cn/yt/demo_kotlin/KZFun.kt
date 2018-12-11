package cn.yt.demo_kotlin

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/7.
 * 扩展函数
 */
class KZFun {
    //必须声明为public（Kotlin默认是public）
    //否则扩展属性无法访问该变量
    var mValue = 0

    /**
     * 以被扩展的类型  作为前缀
     * 对MutableList进行扩展，为其添加swap函数
     */
    fun MutableList<Int>.swap(index: Int, num: Int) {
        val tem = this[index]
        this[index] = this[num]
        this[num] = tem
    }


    fun main() {
        var mutableListOf = mutableListOf(1, 2, 3)
        //调用swap
        mutableListOf.swap(1, 2)
        print(mutableListOf)
        //1,3,2
    }

    //定义一个接口base
    interface Base {
        fun print()
    }

    //实现base
    class BaseImpl(var i: Int) : Base {
        override fun print() {
            print(i)
        }
    }

    //实现base
//    class Drived(b: Base) : Base {
//        override fun print() {
//
//        }
//
//    }

    //委托模式
    class Drived1(var b: Base) : Base by b

    fun test(){
        var d = Drived1(BaseImpl(10))
        d.print()
    }
}