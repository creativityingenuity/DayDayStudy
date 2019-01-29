package com.yt.demo_view.changeskin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yt.demo_view.MainActivity;
import com.yt.demo_view.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class ChangeSkinActivity extends AppCompatActivity {

    private static final String TAG = "changeskin";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeskin);
        iv = (ImageView) findViewById(R.id.imageView);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSkin();
            }
        });
    }

    /**
     * 换肤操作
     */
    private void changeSkin() {
        copyFileFromAssets();
        //获取APK包信息   APK文件绝对路径
        PackageInfo pkgInfo = getPackageManager().getPackageArchiveInfo(pluginApkPath + File.separator + APKNAME, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            //得到插件apk的包名，用于dexClassLoader去加载相应的类
            String packageName = pkgInfo.applicationInfo.packageName;
            //data/data/包名/ 下创建文件
            File optimizedDirectoryFile = getDir("dex", MODE_PRIVATE);
            DexClassLoader classLoader = new DexClassLoader(
                    pluginApkPath + File.separator + APKNAME,//dexPath:需被解压的apk路径，不能为空。
                    optimizedDirectoryFile.getAbsolutePath(),//optimizedDirectory：解压后的.dex文件的存储路径，不能为空。这个路径强烈建议使用应用程序的私有路径，不要放到sdcard上，否则代码容易被注入攻击。
                    null,//libraryPath：c/c++库的路径，可以为null，若有相关库，须填写。
                    ClassLoader.getSystemClassLoader()//父亲加载器，一般为context.getClassLoader(),使用当前上下文的类加载器。
            );
            try {
                Class<?> clazz = classLoader.loadClass(packageName + ".R$mipmap");
                //得到名为guide_3的这张图片在R文件中对应的域值
                Field skin = clazz.getDeclaredField("guide_3");
                //得到图片id
                int resId = skin.getInt(R.id.class);
                //得到插件apk中的Resource
                Resources mResources = getPluginResources(APKNAME);
                if (mResources != null) {
                    //通过插件apk中的Resource得到resId对应的资源
                    Log.i(TAG, "开始换肤");
                    iv.setImageDrawable(mResources.getDrawable(resId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Resources getPluginResources(String apkName) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            //通过反射调用方法addAssetPath(String path)，将插件Apk文件的添加到AssetManager中，
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(assetManager, pluginApkPath + File.separator + apkName);
            Resources mResources = new Resources(assetManager, this.getResources().getDisplayMetrics(), this.getResources().getConfiguration());
            return mResources;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String APKNAME = "skinplugin.apk";
    private static final String pluginApkPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "plugin";

    /**
     * 将插件APK从assets拷贝到apkpluginPath路径
     */
    private void copyFileFromAssets() {
        String APKNAME = "skinplugin.apk";
        File file = new File(pluginApkPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File apkFile = new File(pluginApkPath + File.separator + APKNAME);
        if (apkFile.exists()) {
            Log.i(TAG, "插件已存在");
            return;
        }
        try {
            InputStream is = getAssets().open(APKNAME);
            FileOutputStream fos = new FileOutputStream(apkFile);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
            fos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startAction(MainActivity activity) {
        activity.startActivity(new Intent(activity,ChangeSkinActivity.class));
    }
}
