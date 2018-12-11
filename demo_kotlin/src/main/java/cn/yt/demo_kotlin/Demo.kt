package cn.yt.demo_kotlin

/**
 * 类
 */
class Demo(name: String = "yt") { //主构造函数，在类名后。主构造函数不能包含任何代码，初始化的代码放到init代码块 yt默认参数

    //属性
    lateinit var name: String
    var sex : String = "男"
    //属性可不设置get set 方法，但也可以设置
    var address : String
        get() = "北京"
        set(value){
            address = value
        }

    init {
        //主构造函数 中的初始化代码
        //可以有多个init代码块，顺序执行
        print(name)
    }

    //次构造函数 可通过this调用主构造
    constructor() : this("爱你")

    constructor(sex: String, age: Int) : this("") {
        print("$sex $age")
    }

    /**
     * 嵌套类
     */
    inner class DemoInner{

    }
}