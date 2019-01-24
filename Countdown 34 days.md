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
>Android中的内存泄漏和内存溢出有什么区别？

* 内存溢出是指程序在申请内存的时候，没有足够的内存可以分配，导致Out Of Memory错误，也就是OOM。
* 内存泄漏：在内存应该被释放的时候，却得不到及时的释放，就会一直占用内存，造成内存泄漏。随着内存泄漏的堆积，可用的内存空间越来越少，最后会导致内存溢出。

导致内存泄漏有很多原因，最常见的有内部类的使用，因为内部类持有外部引用。还有就是对Activity Context的使用，以及对bitnmap,io,cursor等的资源释放

>Activity有几种启动模式？有什么区别？

Activity启动有4种模式，区别如下：

1. standard 标准启动模式
	* 默认的启动模式，每次启动activity都会创建新的实例。
2. singletop 栈顶复用模式  =适合接收通知启动的内容显示页面
	* 当栈顶有将要开启的Activity时，会复用这个Activity，同时这个activity的onNewIntent方法会被回调。这个activity的onCreate,OnStart方法不会被调用，因为它没有发生改变。若是栈顶没有，那么就会重新创建
	* 应用场景：**适用于接收到消息后显示的界面**。例如：QQ接收到消息后会弹出activity，但如果一次来10条消息，总不能一次弹出10个activity。	
			
3. **singetask** 栈内复用模式，在当前任务栈里面只能有一个实例存在 =适合作为程序入口点

	* 当一个启动模式为singleTask的activityA请求启动后，系统**会先寻找是否存在A想要的任务栈**（在系统中查找属性值affinity等于它的属性值taskAffinity的Task存在），如果不存在，就重新创建一个任务栈，然后创建A的实例并将A放到栈中。如果存在A所需的任务栈，这时要看栈中有是否有要开启的activityA，如果有则直接复用并删除A上面的activity，将A移动到栈顶，同singletop一样，也会回调这个activity的onNewIntent方法。如果没有实例，则创建A实例并压入栈中

	* 现在有两个任务栈，前台任务栈中有BA，后台任务栈中有DC，假设DC启动启动模式都为singleTask。现在请求启动D，那么整个后台任务栈都会被切到前台，这时候前台任务栈为DCBA，当按back键时，前台栈中activity会一一出栈；如果请求启动C，那么情况就不一样了，会把D删除,C切换到前台。（具体看图——singleTask启动模式特例.pptx）
			
	* **所以这种启动模式通常可以用来退出整个应用程序。**。将主activity设为singleTask,然后在要退出的activity中转到主Activity，从而将主Activity上的其他activity全部清除，然后在主Activity中的onNewIntent()中加上finish(),将最后一个activity结束。
	
4. singleInstance 单实例模式，可以看作加强版singleTask模式

	* activity会开启一个新的任务栈，并且这个任务栈里面只有一个实例存在。
	* 这种启动模式和浏览器的工作原理类似。当多个程序访问浏览器时，如果浏览器没有打开，则打开浏览器，否则会在当前打开的浏览器中访问。举个例子来说，当应用A的任务栈创建了ActivityA实例，并且其启动模式为sinleInstance，如果应用B也要激活ActivityA，则不需要创建，两个应用共享即可。
	* 如果你要保证一个activity在整个手机操作系统里面只有一个实例存在，使用singleInstance
	* 关于singleInstance这种启动模式还有一点需要特殊说明：
		* 如果在一个singleInstance的activityA中通过startActivityForResult()去启动另一个activityB，那么在A中拿不到数据。因为android不允许task间互相传递数据。

onNewIntent()

	protected void onNewIntent(Intent intent)通过这个方法可以取出当前请求的信息
* 第一次创建Activity A时，执行的逻辑顺序是：
	* onCreate() ­­­>onStart()­­­>onResume()
* 而如果使用singleTask模式第二次启动Activity A，且A处于任务栈的顶端，则执行的逻辑顺序是：
    * onNewIntent() ­­­>onRestart> onStart>onResume()。 
注意，getIntent()仍返回原来的意图。你可以使用setIntent来设置新的意图。

>RxJava 中  flatMap 和 concatMap 有什么区别？

concatMap和flatMap的功能是一样的， 将一个发射数据的Observable变换为多个Observables，然后将它们发射的数据放进一个单独的Observable。只不过最后合并Observables flatMap采用的merge，而concatMap采用的是连接(concat)。总之一句一话,他们的区别在于：concatMap是有序的，flatMap是无序的，concatMap最终输出的顺序与原序列保持一致，而flatMap则不一定，有可能出现交错。

>在Activity中如何保存/恢复状态？

分别调用onSaveInstanceState和onRestoreInstanceState 2个方法保存和恢复状态。

>Android 中序列化有哪些方式？区别？

1. Serializable（Java自带）：

Serializable是序列化的意思，表示将一个对象转换成可存储或可传输的状态。序列化后的对象可以在网络上进行传输，也可以存储到本地。

2. Parcelable（android 专用）：

除了Serializable之外，使用Parcelable也可以实现相同的效果，
不过不同于将对象进行序列化，Parcelable方式的实现原理是将一个完整的对象进行分解，
而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了。
区别：Parcelable比Serializable性能高，所以应用内传递数据推荐使用Parcelable,但是Parcelable不能使用在要将数据存储在磁盘上的情况，因为Parcelable不能很好的保证数据的持续性在外界有变化的情况下。尽管Serializable效率低点，但此时还是建议使用Serializable 。

>Android中的显式Intent 和 隐式Intent 有什么区别？

* 显式Intent：即直接指定需要打开的Activity类，可以唯一确定一个Activity，意图特别明确，所以是显式的。
* 隐式Intent:，隐式不明确指定启动哪个Activity，而是设置Action、Data、Category，让系统来筛选出合适的Activity。


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