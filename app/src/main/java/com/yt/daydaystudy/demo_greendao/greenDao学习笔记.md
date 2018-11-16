#greenDao学习笔记
greenDao是一个将对象映射到SQLite数据库中的轻量且快速的ORM解决方案
>优点

* 性能高，号称Android最快的关系型数据库
* 内存占用小
* 库文件比较小，小于100K，编译时间低，而且可以避免65K方法限制
* 支持数据库加密
###1.使用
1. greenDao框架的初始化(准备工作与接入)

		*首先实在project的build中配置
		buildscript {
		    repositories {
		        jcenter()
		        mavenCentral()
		    }
		    dependencies {
		        //greenDao的配置
		        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
		    }
		}

		*在moudle的build中配置
		//greenDao依赖
		apply plugin: 'org.greenrobot.greendao'
		
		android {
		    ........
			//自定义路径
		    greendao {
				//数据库版本号
		        schemaVersion 1
				//设置DaoMaster 、DaoSession、Dao所在包
		        daoPackage 'com.yt.daydaystudy.greendao'
				//设置生成的代码所在的路径
		        targetGenDir 'src/main/java'
		    }
		}
		
		dependencies {
		   
		    //greenDao的依赖
		    compile 'org.greenrobot:greendao:3.2.2'
		}
2. 创建实体类 接着点击 Build -> Make Project

		@Entity
		public class User {
		    /*定义主键*/
		    @Id(autoincrement = true)
		    private long id;
		
		    private String name;
		    private int age;
		    private String sex;
		    
		}
		Make Project 
			会自动生成构造与get/set方法
			然后在greendao目录下生成DaoMaster+DaoSeeeion+UserDao三个类
3. 数据库操作类生成 开始用greenDao操作数据库（通常greenDao的初始化代码写在我们的application中）

		/**
		 * 数据库管理
		 */
		
		public class DBManager {
		    private DaoSession mDaoSession;
		    private String dbName = "test_db";
		    private DaoMaster.DevOpenHelper mHelper;
		
		    private DBManager() {
		        initGreenDao();
		    }
		
		    private static DBManager mInstance;
		
		    public static DBManager getInstance() {
		        if (mInstance == null) {
		            synchronized (DBManager.class) {
		                if (mInstance == null) {
		                    mInstance = new DBManager();
		                }
		            }
		        }
		        return mInstance;
		    }
		
		    private void initGreenDao() {
		        mHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance().getApplicationContext(), dbName, null);
		        SQLiteDatabase db = mHelper.getWritableDatabase();
		        DaoMaster mDaoMaster = new DaoMaster(db);
		        mDaoSession = mDaoMaster.newSession();
		    }
		
		    public DaoSession getDaoSession() {
		        return mDaoSession;
		    }
		
		    /**
		     * 插入一条数据
		     *
		     * @param user
		     */
		    public void insertUser(User user) {
		        mDaoSession.getUserDao().insert(user);
		    }
		
		    /**
		     * 插入用户集合
		     *
		     * @param userList
		     */
		    public void insertUserList(List<User> userList) {
		        if (userList == null || userList.isEmpty()) return;
		        mDaoSession.getUserDao().insertInTx(userList);
		    }
		
		    /**
		     * 删除一条用户记录
		     *
		     * @param user
		     */
		    public void deleteUser(User user) {
		        mDaoSession.getUserDao().delete(user);
				----------------------
							userInfoDao.deleteByKey(id);
		    }
		
		    /**
		     * '
		     * 更新用户数据
		     *
		     * @param user
		     */
		    public void updataUser(User user) {
		        mDaoSession.update(user);
		    }
		
		    /**
		     * 查询用户列表
		     *
		     * @return
		     */
		    public List<User> queryUserList() {
		        QueryBuilder<User> userQueryBuilder = mDaoSession.getUserDao().queryBuilder();
		        return userQueryBuilder.list();
		    }
		
		    /**
		     * 查询用户列表
		     */
		    public List<User> queryUserList(int age) {
		        QueryBuilder<User> userQueryBuilder = mDaoSession.getUserDao().queryBuilder().where(UserDao.Properties.Age.gt(age)).orderAsc(UserDao.Properties.Age);
		        return userQueryBuilder.list();
		    }
		}
		
		-----------
		使用
		public void add(View v) {
	        DBManager.getInstance().insertUser(new User(1, "杨拓", 23, "男"));
	        DBManager.getInstance().insertUser(new User(2, "杨拓111", 23, "男"));
	    }
	
	    public void del(View v) {
	        DBManager.getInstance().deleteUser(new User(1, "杨拓", 23, "男"));
	    }
	
	    public void update(View v) {
	        DBManager.getInstance().updataUser(new User(2, "xxxxx", 23, "男"));
	    }
	
	    public void find(View v) {
	        list.clear();
	        List<User> users = DBManager.getInstance().queryUserList();
	        for (User user : users) {
	            list.add(user.getName());
	        }
	        arrayAdapter.notifyDataSetChanged();
	    }
###2.核心类介绍
>DaoMaster (master控制)

GreenDao的入口。**负责管理数据库对象（sqlitedatabase）和dao类**。

保存数据库对象（SQLiteDatabase）并管理特定模式的 DAO 类（而不是对象）。它有静态方法来创建表或删除它们。它的内部类 OpenHelper 和DevOpenHelper 是 SQLiteOpenHelper 实现，它们在 SQLite 数据库中创建模式。

通过它内部类 `OpenHelper` 和 `DevOpenHelper SQLiteOpenHelper` 创建不同模式的 SQLite 数据库
>DaoSession (session 会议)

**管理指定模式下的所有 DAO 对象，DaoSession提供了一些通用的持久性方法比如插入、负载、更新、更新和删除实体。**
>XxxDao

每个实体类，greenDAO都会生成一个与之对应DAO对象，如：User 实体，则会生成一个一个UserDao 类

![](https://i.imgur.com/p1kdC2j.png)
###3.常用注解
	@Entity 表明这个实体类会在数据库中生成一个与之相对应的表
		schema：告知GreenDao当前实体属于哪个schema
		active：标记一个实体处于活动状态，活动实体有更新、删除和刷新方法
		nameInDb：在数据中使用的别名，默认使用的是实体的类名
		indexes：定义索引，可以跨越多个列
		createInDb：标记创建数据库表

	基础属性
		@Id 对应数据表中的Id字段 可以通过@Id(autoincrement = true)设置自增长
		@Property：设置一个非默认关系映射所对应的列名，默认是的使用字段名 举例：@Property (nameInDb="name")
		@Index 作为一个属性来创建一个索引，默认是使用字段名
	@NotNul：设置数据库表当前列不能为空
	@Unique：表名该属性在数据库中只能有唯一值
	@OrderBy 某一字段排序 ，例如：@OrderBy("date ASC")
	@Transient ：添加次标记之后不会生成数据库表的列

	@ToOne：表示一对一关系
	@ToMany：定义与多个实体对象的关系
