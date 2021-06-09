# Android 混淆高深笔记

https://juejin.cn/post/6966526844552085512#heading-8

## 1. 混淆规则

> 混淆参数

```
-optimizationpasses 5                       # 代码混淆的压缩比例，值介于0-7，默认5
-verbose                                    # 混淆时记录日志
-dontoptimize                               # 不优化输入的类文件
-dontshrink                                 # 关闭压缩
-dontpreverify                              # 关闭预校验(作用于Java平台，Android不需要，去掉可加快混淆)
-dontoptimize                               # 关闭代码优化
-dontobfuscate                              # 关闭混淆
-ignorewarnings                             # 忽略警告
-dontwarn com.squareup.okhttp.**            # 指定类不输出警告信息
-dontusemixedcaseclassnames                 # 混淆后类型都为小写
-dontskipnonpubliclibraryclasses            # 不跳过非公共的库的类
-printmapping mapping.txt                   # 生成原类名与混淆后类名的映射文件mapping.txt
-useuniqueclassmembernames                  # 把混淆类中的方法名也混淆
-allowaccessmodification                    # 优化时允许访问并修改有修饰符的类及类的成员
-renamesourcefileattribute SourceFile       # 将源码中有意义的类名转换成SourceFile，用于混淆具体崩溃代码
-keepattributes SourceFile,LineNumberTable  # 保留行号
-keepattributes *Annotation*,InnerClasses,Signature,EnclosingMethod # 避免混淆注解、内部类、泛型、匿名类
-optimizations !code/simplification/cast,!field/ ,!class/merging/   # 指定混淆时采用的算法
-repackageclasses #如果你已经在混淆你的代码并且使用适当的 keep 规则解决了任何问题，那么你可以添加这个选项以进一步减小 DEX 的大小。它的工作原理是将所有类移至默认（根）包，从而实质上释放了被像com.example.myapp.somepackage这样的字符串所占用的空间
```

> 混淆设置

语法组成：

```
[保持命令] [类] {
    [成员] 
}
-keep                           # 防止类和类成员被移除或被混淆；
-keepnames                      # 防止类和类成员被混淆；
-keepclassmembers	            # 防止类成员被移除或被混淆；
-keepclassmembernames           # 防止类成员被混淆；
-keepclasseswithmembers         # 防止拥有该成员的类和类成员被移除或被混淆；
-keepclasseswithmembernames     # 防止拥有该成员的类和类成员被混淆；

# 不混淆某个类的类名，及类中的内容
-keep class cn.coderpig.myapp.example.Test { *; }

# 不混淆指定包名下的类名，不包括子包下的类名
-keep class cn.coderpig.myapp*

# 不混淆指定包名下的类名，及类里的内容
-keep class cn.coderpig.myapp* {*;}

# 不混淆指定包名下的类名，包括子包下的类名
-keep class cn.coderpig.myapp**

# 不混淆某个类的子类
-keep public class * extends cn.coderpig.myapp.base.BaseFragment

# 不混淆实现了某个接口的类
-keep class * implements cn.coderpig.myapp.dao.DaoImp

# 不混淆类名中包含了"entity"的类，及类中内容
-keep class **.*entity*.** {*;}

# 不混淆内部类中的所有public内容
-keep class cn.coderpig.myapp.widget.CustomView$OnClickInterface {
    public *;
}

# 不混淆指定类的所有方法
-keep cn.coderpig.myapp.example.Test {
    public <methods>;
}

# 不混淆指定类的所有字段
-keep cn.coderpig.myapp.example.Test {
    public <fields>;
}

# 不混淆指定类的所有构造方法
-keep cn.coderpig.myapp.example.Test {
    public <init>;
}

# 不混淆指定参数作为形参的方法
-keep cn.coderpig.myapp.example.Test {
    public <methods>(java.lang.String);
}

# 不混淆类的特定方法
-keep cn.coderpig.myapp.example.Test {
    public test(java.lang.String);
}

# 不混淆native方法
-keepclasseswithmembernames class * {
    native <methods>;
}

# 不混淆枚举类
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 不混淆自定义控件
-keep public class * entends android.view.View {
    *** get*();
    void set*(***);
    public <init>;
}

# 不混淆实现了Serializable接口的类成员，此处只是演示，也可以直接 *;
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 不混淆实现了parcelable接口的类成员
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 注意事项：
# ① jni方法不可混淆，方法名需与native方法保持一致；
# ② 反射用到的类不混淆，否则反射可能出问题；
# ③ 四大组件、Application子类、Framework层下的类、自定义的View默认不会被混淆，无需另外配置；
# ④ WebView的JS调用接口方法不可混淆；
# ⑤ 注解相关的类不混淆；
# ⑥ GSON、Fastjson等解析的Bean数据类不可混淆；
# ⑦ 枚举enum类中的values和valuesof这两个方法不可混淆(反射调用)；
# ⑧ 继承Parceable和Serializable等可序列化的类不可混淆；
# ⑨ 第三方库或SDK，请参考第三方提供的混淆规则，没提供的话，建议第三方包全部不混淆；


通配符(*) → 匹配任意长度字符，但不包含包名分隔符(.)
通配符(**) → 匹配任意长度字符，且包含包名分隔符(.)
$ → 内部类
匹配所有构造器 → <init>
匹配所有域 → <field>
匹配所有方法 → <methods>
除了 * 和 ** 通配符外，还支持 *** 通配符，匹配任意参数类型
... → 匹配任意长度的任意类型参数，如void test(...)可以匹配不同参数个数的test方法
```

> 资源混淆

分为两步：**资源合并** 与 **资源移除**，合并不管是否配置 shrinkResources，AGP构建APK时都会执行资源合并，当存在两个或更多名称相同的资源才会进行资源合并，AGP会从重复项中选择 **优先级更高** 的文件，并只将此资源传递给AAPT2，以供在APK中分发。(Android Gradle Plugin AGP)

将aapt编译后的flat产物和合并后的清单文件进行链接处理生成 **._ap** 文件(包含资源文件、清单文件、资源关系映射表文件resources.arsc) 及 **R.java** 文件(保存了资源类型、资源名称及地址的映射关系)。

说完资源合并，接着说下资源移除，开启资源压缩后，所有未被使用的资源默认会被移除，如果你想定义那些资源需要保留，可以在 `res/raw/` 路径下创建一个xml文件，如 keep.xml，配置示例如下

```
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools"
    <!-- 定义哪些资源要被保留 -->
    tools:keep="@layout/l_used*_c,@layout/l_used_a,@layout/l_used_b*"
    <!-- 定义哪些资源需要被移除 -->
    tools:discard="@layout/unused2"
    <!-- 开启严苟模式，可选值strict,safe，前者严格按照keep和discard指定的资源保留 -->
    <!-- 后者保守删除未引用资源，如代码中使用Resources.getIdentifier()引用的资源会保留 -->
    tools:shrinkMode="strict"/>
```

另外，还可以在build.gradle中添加 **resConfigs** 来移除不需要的备用资源文件，如只保留中文：

```
android {
    defaultConfig {
        resConfigs "zh-rCN" // 不用支持国际化只需打包中文资源
    }
}   
```

## 2. 模块化混淆

主模块和子模块的混淆规则是叠加的

如果想查看所有规则叠加后的混淆规则，可在主目录的 **proguard-rules.pro** 添加下述配置：

```
# 输出所有规则叠加后的混淆规则
-printconfiguration ./build/outputs/mapping/full-config.txt
```

混淆的开启由app模块控制，与子模块无关。

建议 在app模块设置公共混淆规则，子模块设置专属混淆规则，子模块区分project和aar：

```
# Project类型，配置方法同app模块
buildTypes {
    release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
}

# AAR类型
android {
    defaultConfig {
        consumerProguardFiles 'lib-proguard-rules.txt'
    }
    ...
}
```

## 3. 编译器

Java代码可以在Windows、Linux、Mac上运行，背后依赖于不同平台/版本的 **JVM**（Java虚拟机），Java代码编译后生成 **.class`** 字节码文件，再由JVM翻译成特定平台的 **机器码`**，然后运行

JVM组成：

- **类加载器**：加载编译后的.class，链接、检测损坏的字节码，定位并初始化静态变量及静态代码；
- **运行时数据**：栈、堆、方法变量等；
- **执行引擎**：执行已经加载的代码、清理生成的所有垃圾(gc)；

与JVM字节码基于栈不同，**Dalvik基于寄存器**(变量都存储在寄存器中)，后者更加高效且需要更少的空间。从4.4开始引入了ART，为了优化每次APP运行时都需要编译代码。

.java和.kt代码文件被Java、Kotlin编译器协作编译为.class，而后编译为.dex文件，最后打包到.apk文件中。

把APK安装到设备上，当点击应用图标时，系统会启动一个新的Dalvik进程，并将应用包含的dex代码加载进来，在运行时交由Interpreter或JIT编译，然后就可以看到应用的界面了。

Dalvik每次运行都会编译代码，安装时间短，但是增加了运行时间。

应用在首次安装时用**dex2oat**将 **dex`** 编译为 **.oat`** 二进制文件。

点击应用图标启动时，ART直接加载.oat文件并运行，启动速度明显提升，避免了重复编译，减少了CPU的使用频率，也降低了功耗，当然缺点也是有的：**更长的应用安装时间** 和 **更大的存储空间占用**。

上面说过Android虚拟机采用 **基于寄存器的指令集(opcodes)**，这样会存在一个问题，更高版本Java新引入的语法特性不能在上面直接使用。

为了让我们能使用上Java 8的特性，Google使用 **Transformation`** 来增加了一步编译过程 → **脱糖(desugaring)`**。

**当使用当前Android版本不支持的高版本jdk语法时，在编译期转换为其支持的低版本jdk语法。**

## 4. ProGuard、D8、R8

![proguard_D8_R8.png](https://github.com/creativityingenuity/DayDayStudy/blob/master/%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/proguard_D8_R8.png?raw=true)

* ProGuard → 压缩、优化和混淆Java字节码文件的免费工具
* D8 → 脱糖 + 将.class字节码转换成dex(D8在R8包里)

- R8 → ProGuard的替代工具，支持现有ProGuard规则，**更快更强**，**AGP 3.4.0**或更高版本，默认使用R8混淆编译器。

> Proguard包括四个功能

- shrink压缩： 检测并移除没有用到的类，变量，方法和属性；
- optimize优化: 优化代码，非入口节点类会加上`private`/`static`/`final`, 没有用到的参数会被删除，一些方法可能会变成内联代码。
- obfuscate混淆: 使用短又没有语义的名字重命名非入口类的类名，变量名，方法名。入口类的名字保持不变。
- preverify预校验: 预校验代码是否符合Java1.6或者更高的规范(唯一一个与入口类不相关的步骤)

> 使用ProGuard或R8构建项目会在 build\outputs\mapping\release输出下述文件：

- **mapping.txt** → 原始与混淆过的类、方法、字段名称间的转换；
- **seeds.txt** → 未进行混淆的类与成员；
- **usage.txt** → APK中移除的代码；
- **resources.txt** → 资源优化记录文件，哪些资源引用了其他资源，哪些资源在使用，哪些资源被移除；

## 其他

> 1.如何分析混淆后的Crash日志

可以使用android-sdk/tools/proguard/bin/proguardgui.bat工具

![proguard使用.png](https://github.com/creativityingenuity/DayDayStudy/blob/master/%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/proguard%E4%BD%BF%E7%94%A8.png?raw=true)

> 2.自定义混淆字典

1. 在app的proguard-rules的同级目录创建一个文件,字典网上随便搜就行
2. 接着在 proguard-rules 添加下述配置：

```
-obfuscationdictionary ./dictionary
-classobfuscationdictionary ./dictionary
-packageobfuscationdictionary ./dictionary
```

