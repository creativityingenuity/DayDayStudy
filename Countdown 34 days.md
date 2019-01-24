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

hashcode和equals是object的方法，equals用来判断对象是否相等，hashcode返回对象的哈希值

若重写equals方法，则必须重写hashcode方法确保eqauls为true的两个对象有着相同的hashcode;

如果不重写，可能就有问题，比如HashMap你存2个key,你在定义equals方法的时候，可能2个对象是相等的，但是在HashMap里面，会对key进行hash计算，所以尽管你对象相等，但是hashcode不一致，程序是有问题的。

若equals发那会false,则有可能hashcode相同。这个取决于hashcode的算法。

>4.new String创建了几个对象

1个。new 在堆中创建一个对象，字符串常量池中没有
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

##数据结构和算法
>1.ArrayList和LinkedList的区别，优缺点
>2.hashmap实现，扩容是怎么做的，怎么处理hash冲突，hashcode算法等
>3.链表需要知道。LinkedHashMap一般再问LRU的时候会问到
>4.二分搜索树的特性和原理。前中后序遍历写出其中一种，当问到二分搜索树的缺点的时候，你需要提出基于二分搜索树的红黑树，说出他的特性。
>5.堆的实现，最大堆，最小堆，优先队列原理


>6.手写快速排序，插入排序，冒泡排序
>7.翻转一个数字
>8.翻转一个链表
>9.O(n)复杂度找出数组中和是9的两个数的索引
>10.写出二分搜索树前中后序遍历中的其中一个
>11.实现一个队列，并能记录队列中最大的数。
##Android
>1.四大组件有哪些，说出你对他们在Android系统中的作用和理解。
>2.Activity生命周期，A启动B两个页面生命周期怎么运行的，为什么会这样，生命周期为什么这么设计，你有了解过吗。
>3.四种启动模式，内部堆栈是怎么回事，你工作中怎么使用的。
>4.Activity的启动过程，这个我强烈建议每个Android开发人员都要清楚的知道，并且跟一下源码，几个核心类的作用。你会对Android有一个更好的认识。
>5.事件分发流程，怎么处理滑动冲突。举例：长按ListView的一个Item它变灰了。这个时候在滑动。item恢复原来的样子，这个时候他们内部的事件传递是什么样子。有很多种问法，所以你一定要搞清楚。
>6.自定义View,View的绘制流程。onMeasure,onLayout,onDraw都是什么作用。ViewGroup是怎么分发绘制的。onDraw里面怎么去做绘制，Canvas,Path,Paint你都需要了解。并且配合ValueAnimtor或者Scroller去实现动画。有时候面试的会突发奇想问你ViewGroup是树形结构，我想知道树的深度，你怎么计算，突然就变成了一个数据结构和算法的题。
>7.Bitmap和Drawable
>8.Animation和Animator
>9.LinearLayout、RelativeLayout、FrameLayout三种常用布局的特性，他在布局的时候是怎么计算的。效率如何。CoordinatorLayout配合AppbarLayout的使用，以及自定义Behavior。ConstraintLayout的使用。用来减少层级。
>10.Handler消息机制，推荐看一下Looper的源码
>11.进程间通信，Binder机制
>12.图片的压缩处理，三级缓存，Lru算法
>13.分辨率和屏幕密度，以及计算一个图片大小。mdpi,hdpi的关系和比例。
>14.优化，内存优化，布局优化，启动优化，性能优化。内存泄露，内存溢出。怎么优化，用了什么工具，具体怎么做的。
>15.listView和RecycleView对比，以及缓存策略。
>16.MVC,MVP,MVVM
>17.开源框架Okhttp,Glide,EventBus,Rxjava等,以及JetPack下的开源库，要会用，还说说出一些东西，推荐 Retrofit，Okhttp,Glide,EventBus这些看一下源码。
>18.RecyclerView四大块，能实现什么效果，大致怎么实现的，心里要有数
>19.DecorView,Window,WindowManager,PhoneWindow关系，以及个子的职责。