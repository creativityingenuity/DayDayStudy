# Gradle笔记

## 1.依赖认识

> 依赖配置 implementation、api、compileOnly、runtimeOnly、annotationProcessor

* implementation：添加依赖到编译路径，并且会将依赖打包到输出（aar或apk），但是在编译时不会将依赖的实现暴露给其他module，也就是只有在运行时其他module才能访问这个依赖中的实现。

* api：添加依赖到编译路径，并且会将依赖打包到输出（aar 或a pk）。与 implementation 不同，这个依赖可以传递，其他 module 无论在编译时和运行时都可以访问这个依赖的实现。举个例子，A 依赖 B，B 依赖 C，如果都是使用 api 配置的话，A 可以直接使用 C 中的类（编译时和运行时）。而如果是使用 implementation 配置的话，在编译时，A 无法访问 C 中的类。

* compileOnly：编译时使用，不会打包到输出（aar 或 apk）。这可以减少输出的体积，在只在编译时需要，在运行时可选的情况，很有用。

* runtimeOnly：只在生成apk的时候参与打包，编译时不会参与，很少用。

* annotationProcessor：用于注解处理器的依赖配置。

> 依赖类型

1. 本地源码依赖：implementation project(":mylibrary")
2. 本地libs目录jar包等依赖：implementation fileTree(dir: 'libs', include: ['*.jar'])
3. 远程Maven仓库等依赖：implementation 'com.example.android:app-magic:12.3'

> 依赖加@aar和不加aar的区别

如果指定了@aar，那么下载后默认看不到它下面的依赖树，当没有指定@aar的话，能看到此模块下面的依赖树

```
compile ('com.android.support:appcompat-v7:22.1.1') 能看到此模块下面的依赖树
compile ('com.android.support:appcompat-v7:22.1.1@aar') {默认看不到
    transitive = true//这样设置后才能看到
}
```

## 2.依赖配置

###2.1排除依赖传递

> 1.dependencies中单个依赖排除: 

```
compile('com.hongri.android:accs-huawei:1.1.2@aar') {
        exclude group: 'com.android.support', module:'design'
}
```

> 2.全局配置排除：

```
configurations {
    compile.exclude module: 'cglib'
    //全局排除原有的tnet jar包与so包分离的配置，统一使用aar包中的内容
    all*.exclude group: 'com.taobao.android', module: 'tnet-jni'
    all*.exclude group: 'com.taobao.android', module: 'tnet-so'
}
```

> 3.禁止依赖传递：

```
compile('com.hongri.android:foundation:1.0') {
    transitive = false
}
//全局配置
configurations.all {
    transitive = false
}
```

> 4.还可以在单个依赖项中使用 @jar 标识符忽略传递依赖：

```
compile 'com.hongri.android:foundation:1.0.0@jar'
```

###2.2依赖库版本指定

一般情况下高的版本会覆盖旧的低的版本,如果我们想一直使用某个固定版本，那么可以使用如下的强制依赖实现：

```
compile('com.hongri.android:foundation:1.0.0') {
    force = true
}
//或者进行全局配置
configurations.all {
    resolutionStrategy {
        force 'com.hongri.android:foundation:1.0.0'
        //或这种写法
        forcedModules = ['com.hongri.android:foundation:1.0.0']
    }
}
```

## 3.依赖树查询

查询项目的全部依赖：

```
./gradlew app:dependencies >dep.txt
```

查询特定配置的依赖：

```
./gradlew app:dependencies --configuration compileOnly //输出模块中compileOnly相关的依赖
```

> 打印的依赖树中的符号

```
+- - - 是依赖分支库的开始
| 标识还是之前的依赖库中的依赖，显示它依赖的库
\- - - 是依赖库的末尾
* 意味着该库的进一步依赖关系不会显示
 -> 如果多个依赖库都依赖到同一个库但是不同版本，默认选择该库的最新版本
```

##4.使用循环优化`Gradle`依赖管理

有时候我们使用ext或者新建一个gradle来管理依赖，还有一种是循环遍历依赖来进行优化

```
1.config.gradle：
ext{
	dependencies = [
            // base
            "appcompat-v7": "com.android.support:appcompat-v7:${version["supportLibraryVersion"]}"，
    ]
    
    annotationProcessor = [
            "glide_compiler": "com.github.bumptech.glide:compiler:${version["glideVersion"]}",
    ]
    
    apiFileDependencies = [
            "launchstarter":"libs/launchstarter-release-1.0.0.aar"
    ]
    
    debugImplementationDependencies = [
            "MethodTraceMan": "com.github.zhengcx:MethodTraceMan:1.0.7"
    ]
    
    implementationExcludes = [
            "com.android.support.test.espresso:espresso-idling-resource:3.0.2" : [
                    'com.android.support' : 'support-annotations'
            ]
    ]
}
2.build.gradle中配置：
apply from config.gradle

def implementationDependencies = project.ext.dependencies
def processors = project.ext.annotationProcesso
def implementationExcludes = project.ext.implementationExcludes
dependencies{
    // 处理所有的 xxximplementation 依赖
    implementationDependencies.each { k, v -> implementation v }   
    // 处理 annotationProcessor 依赖
    processors.each { k, v -> annotationProcessor v }
    // 处理所有包含 exclude 的依赖
    implementationExcludes.each { entry ->
        implementation(entry.key) {
            entry.value.each { childEntry ->
                exclude(group: childEntry)
            }
        }
    }
}
```

## 5.`Library`模块`Gradle`代码复用

对多Lib中build.gradle重复的部分封装起来复用。写`basic` 抽取，同样将共有参数/信息提取到 `basic.gradle` 中，每个 `module` `apply`

```
apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'

android {
    // 指定用于编译项目的 API 级别
    compileSdkVersion Versions.compileSDK
    // 指定在生成项目时要使用的 SDK 工具的版本，Android Studio 3.0 后不需要手动配置。
    buildToolsVersion Versions.buildTools

    // 指定 Android 插件适用于所有构建版本的版本属性的默认值
    defaultConfig {
        minSdkVersion Versions.minSDK
        targetSdkVersion Versions.targetSDK
        versionCode 1
        versionName "1.0"
    }

    // 配置 Java 编译(编码格式、编译级别、生成字节码版本)
    compileOptions {
        encoding = 'utf-8'
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lintOptions {
        // lint 异常后继续执行
        abortOnError false
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    ...
}
```

然后在相应的模块的`build.gradle`中引入即可

```
apply from:"../basic.gradle"

dependencies {
    api Deps.constraintLayout
    api Deps.retrofit
}
```

## 6.资源文件分包-对`layout`与`drawable`文件夹进行分包

1. 在 `main` 目录下新建 `res_core`, `res_feed`（根据业务模块命名）等目录，在`res_core`中新建`res`目录中相同的文件夹如：`layout`、`drawable-xxhdpi`、`values`等。
2. 在`gradle`中配置`res_xx`目录

```
android {
    sourceSets {
        main {
            res.srcDirs(
                    'src/main/res',
                    'src/main/res_core',
                    'src/main/res_feed',
            )
        }
    }
}
```

## 7.`AAR`依赖与源码依赖快速切换

1.首先下载`retrofit`，可以放到和项目同级的目录,并修改目录名为`retrofit-source`,以便区分
2.在`settings.gradle`文件中添加需要修改的`aar`库的源码`project`

```
include ':retrofit-source'
project(':retrofit-source').projectDir = new File("../retrofit-source")
```

3.替换`aar`为源码
`build.gradle(android)` 脚本中添加替换策略

```
allprojects {
    repositories {
...
    }
 
    configurations.all {
        resolutionStrategy {
            dependencySubstitution {
                substitute module( "com.squareup.retrofit2:retrofit") with project(':retofit-source')
            }
        }
    }
```

##8.定义解析策略

> 1.dependencySubstitution：接收一系列替换规则，允许你通过substitute函数为项目中的依赖替换为你希望的依赖项：

```
dependencySubstitution {
   //将该module所有的远程依赖替换成源码依赖
   substitute module('org.gradle:api') with project(':api')
   //将该module所有源码依赖替换成远程依赖
   substitute project(':util') with module('org.gradle:util:3.0')
}
```

> 2.eachDependency:

允许你在gradle解析配置时为每个依赖项添加一个替换规则，DependencyResolveDetails类型的参数可以让你获取一个requested和使用useVersion()、useTarget()两个函数指定依赖版本和目标依赖。request中存放了依赖项的groupid、module name以及version，你可以通过这些值来筛选你想要替换的依赖项，再通过useVersion或useTarget指定你想要的依赖。

将group是com.android.support且name不等于multidex的所有module版本指定为28.0.2：

```
configurations.all {
    resolutionStrategy.eachDependency { DependencyResolveDetails details ->
        def requested = details.requested
        if (requested.group == 'com.android.support') {
            if (!requested.name.startsWith("multidex")) {
                details.useVersion '28.0.2'
            }
        }
    }
}
将所有module为rxjava的依赖，全都指定使用如下版本：‘io.reactivex.rxjava3:rxjava:3.0.0-RC1’:
configurations.all {
    resolutionStrategy.eachDependency { DependencyResolveDetails details ->
        if (details.requested.name == 'rxjava') {
            //由于useVersion只能指定版本号，不适用于group不同的情况
            details.useTarget group: 'io.reactivex.rxjava3', name: 'rxjava', version: '3.0.0-RC1'
        }
    }
}
```

