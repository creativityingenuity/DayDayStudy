package cn.yt.demo_export_excel;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.yt.demo_export_excel.jxl.JXLExcelUtils;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ArrayList<String>> recordList;
    private List<Student> students;
    private static String[] title = {"编号", "姓名", "性别", "年龄", "班级", "数学", "英语", "语文"};
    private File file;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //模拟数据集合
        students = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            students.add(new Student("小红" + i, "女", "12", "1" + i, "一班", "85", "77", "98"));
            students.add(new Student("小明" + i, "男", "14", "2" + i, "二班", "65", "57", "100"));
        }
    }

    /**
     * 导出excel
     *
     * @param view
     */
    public void exportExcel(View view) {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
        JXLExcelUtils.initExcel(file.toString() + "/成绩表.xls", title);
        fileName = getSDPath() + "/Record/成绩表.xls";
        JXLExcelUtils.writeObjListToExcel(getRecordData(), fileName, this);

        openAssignFolder(file.getPath());
    }

    private void openAssignFolder(String path) {
        File file = new File(path);
        if (null == file || !file.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file), "file/*");

//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.ms-excel");
        try {
            startActivity(intent);
            //            startActivity(Intent.createChooser(intent,"选择浏览工具"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            ArrayList<String> beanList = new ArrayList<String>();
            beanList.add(student.id);
            beanList.add(student.name);
            beanList.add(student.sex);
            beanList.add(student.age);
            beanList.add(student.classNo);
            beanList.add(student.math);
            beanList.add(student.english);
            beanList.add(student.chinese);
            recordList.add(beanList);
        }
        return recordList;
    }

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    public void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }
}
