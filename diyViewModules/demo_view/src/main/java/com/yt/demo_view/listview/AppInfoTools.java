package com.yt.demo_view.listview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取应用程序信息
 */

public class AppInfoTools {
    public static List<AppInfo> getAppInfo(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            //包名
            String packageName = packageInfo.packageName;
            //applica
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //应用名称 Label
            String name = applicationInfo.loadLabel(pm).toString();
            //图标
            Drawable drawable = applicationInfo.loadIcon(pm);
            //占用空间大小
            long size = new File(applicationInfo.sourceDir).length();
            //程序标记
            boolean isSystem = false;
            boolean isSD = false;
            //判断是用户还是系统程序
            int flags = applicationInfo.flags;
            //系统程序
            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                isSystem = true;
            } else {
                isSystem = false;
            }
            //是否在SD中
            if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                isSD = true;
            } else {
                isSD = false;
            }
            list.add(new AppInfo(packageName, name, drawable, size, isSystem, isSD));
        }
        return list;
    }
}
