package com.yt.daydaystudy.demo_takephoto;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.yt.daydaystudy.BuildConfig;
import com.yt.daydaystudy.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

import yt.myutils.LogUtils;
import yt.myutils.PermissionUtils;

/**
 * 内容知识点：
 * URI 分为两种： content 类型的 和 file 类型的。
 * File Uri 对应的是文件本身的存储路径 - Content Uri 对应的是文件在Content Provider的路径 所以在android 7.0 以上，我们就需要将File Uri转换为 Content Uri。
 * 2、进行裁剪
 * 3、上传服务器前编码转换，用Base64 编码法，将字节数据转化为String类型，上传。服务器端，再通过Base64解码，获得图片
 * <p>
 * 有两个问题：
 * 1.多图选择 https://github.com/zhihu/Matisse
 * 2.裁剪机型适配  https://github.com/Yalantis/uCrop
 */
public class PhotoUploadActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = PhotoUploadActivity.class.getName();
    /*拍照*/
    private static final int REQUEST_CODE_TAKEPHOTO = 0x003;
    /*相册*/
    private static final int REQUEST_CODE_CHOOSE_ALBUM = 0x004;
    /*裁剪*/
    private static final int REQUEST_CODE_CROP_CODE = 0x001;
    //裁剪图片地址
    private File cropfile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + "crop.png");
    //拍照图片地址
    private File photo_file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
    /*需要授权的权限*/
    private String[] permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private ImageView mIv;

    public static void startAction(Activity activity) {
        activity.startActivity(new Intent(activity, PhotoUploadActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
        findViewById(R.id.btn_takephoto).setOnClickListener(this);
        findViewById(R.id.btn_album).setOnClickListener(this);
        mIv = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKEPHOTO && resultCode == RESULT_OK) {
            doCrop(photo_file.getAbsolutePath(), this);
            LogUtils.i(TAG, "拍照完成");
        } else if (requestCode == REQUEST_CODE_CHOOSE_ALBUM) {
            if (data == null) return;
            String absolutePath = getAbsolutePath(PhotoUploadActivity.this, data.getData());
            LogUtils.i(TAG, "相册选取成功");
            doCrop(absolutePath, this);
        } else if (requestCode == REQUEST_CODE_CROP_CODE) {
            if (cropfile != null) {
                //获取裁剪后的图片，可以压缩上传 也可用base64将字节数据转化为String
                //进行上传
                Bitmap bitmap = BitmapFactory.decodeFile(cropfile.getAbsolutePath());
                mIv.setImageBitmap(bitmap);
                LogUtils.i(TAG, "裁剪完成");
            }
        }
    }

    public String getAbsolutePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String[] deniedPermissions = PermissionUtils.getDeniedPermissions(this, permissions);
            if (deniedPermissions.length > 0) {
                PermissionUtils.requestPermissions(this, 0x0002, permissions, new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //成功
                        LogUtils.i(TAG, "成功");
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {

                    }
                });
            }
        }
        switch (v.getId()) {
            case R.id.btn_takephoto://拍照
                // 设置图片的输出路径
                /*#Android系统为了防止传送原图出现OOM，拍照和裁剪都默认返回的是缩略图，为了解决这种问题，我们在启动相机的时候要先设置照片的存储路径，即传递MediaStore.EXTRA_OUTPUT参数，这样拍照完成后我们就可以根据之前设置的路径读取原图了。*/
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri;
                if (!photo_file.getParentFile().exists()) photo_file.getParentFile().mkdirs();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photo_file);
                } else {
                    imageUri = Uri.fromFile(photo_file);
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CODE_TAKEPHOTO);
                break;
            case R.id.btn_album://相册
                Intent albumIntent = new Intent(Intent.ACTION_PICK);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, REQUEST_CODE_CHOOSE_ALBUM);
                break;
        }
    }

    public void doCrop(String s, Activity activity) {
        File file = new File(s);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            imageUri = Uri.fromFile(file);
        }
        Uri outputUri = Uri.fromFile(cropfile);
        intent.setDataAndType(imageUri, "image/*");
        //发送裁剪信号
        intent.putExtra("crop", "true");
        //x方向上的比例
        intent.putExtra("aspectX", 9);
        //y方向上的比例
        intent.putExtra("aspectY", 8);
        //裁剪区的宽
        intent.putExtra("outputX", 900);
        //裁剪区的高
        intent.putExtra("outputY", 800);
        //是否保留比例
        intent.putExtra("scale", true);
        /*是否将数据保存在bitmap中返回
         *设为 true 的时候，在 onActivityResult() 中可以直接通过 data.getParcelableExtra("data") 得到裁剪后的 Bitmap 对象。但是当 Bitmap 过大时，就不能使用这种方法了，容易出现OOM现象
         * 因此可以将图片保存到本地，然后传递路径即可 */
        intent.putExtra("return-data", false);
        //是否需要人脸识别
        intent.putExtra("noFaceDetection", true);
        //图片输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        startActivityForResult(intent, REQUEST_CODE_CROP_CODE);
    }

    /**
     * 保存经过Base64编码的图片到服务器
     *
     * @param bitmap
     */
    private String sentImage(Bitmap bitmap) {
        // 先创建 字节数组流： ByteArrayOutputStream: 可以捕获内存缓冲区的数据，转换成字节数组。
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // 写入一个压缩版的位图到指定的输出流 （这里是字节数组流）
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, stream);
        // 把 字节数组流作为字节数组返回
        byte[] bytes = stream.toByteArray();
        // 用base64 编码方式把一个字节数组编码后以一个新的字节数组返回
        return new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        //接下来就可以 通过http 上传 img 上去服务器了
    }
}
