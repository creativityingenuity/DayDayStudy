##java
>1.java基本数据类型有哪些，int， long占几个字节

* 整型
	* byte（1）
	* short（2）
	* int（4）
	* long（8）
* 浮点型
	* float（4）
	* double（8）
* boolean（布尔型）
* char（2）
>2.== 和 equals有什么区别

一般来说：==常用于比较原生类型，equals比较对象是否相等
但在比较对象时：==比较的是对象在内存中的地址，equals默认实现是==，但是可以自由实现去比较对象是否相等

>3.hashcode 和 equals作用


>4.new String创建了几个对象
>5.位运算符的一些计算
>6.java的拆装箱
>7.compareable 和 compartor的区别

基本数据类型，int大小，一个字节占几位， int的取值区间。
Integer a = 123456
Integer b = 123456
return a==b 如果a = a b = 1 结果呢

int a = 2;
int rusult = a++ + 4<<2
求 result的值

public static String fun(String s) {
        return s.length() < 0 ? (fun(s.substring(1) + s.charAt(0))) : "";
    }
System.out.println("result = " + fun("Smart"));
它的打印结果是什么。

###数据结构和算法

##Android