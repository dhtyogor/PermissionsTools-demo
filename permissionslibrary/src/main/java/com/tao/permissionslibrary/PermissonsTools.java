package com.tao.permissionslibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by Tao on 2016/4/25.
 * android 6.0权限工具类
 */
public  class PermissonsTools {

    private static Context context;

    public PermissonsTools(Context context) {
        this.context = context;
    }

    public static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数


    // 所需的全部权限
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,//摄像头
            Manifest.permission.RECORD_AUDIO,//麦克风 音频
            Manifest.permission.READ_CALENDAR,//日历
            Manifest.permission.READ_CONTACTS,//通讯录 联系人
            Manifest.permission.ACCESS_FINE_LOCATION,//地理位置
            Manifest.permission.CALL_PHONE,//电话
            Manifest.permission.SEND_SMS,//短信
            Manifest.permission.READ_EXTERNAL_STORAGE,//存储空间
    };

    // 请求权限兼容低版本
    public static void requestPermissions(Activity activity,String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, PermissonsTools.PERMISSION_REQUEST_CODE);
    }

    // 全部权限均已获取
    public static void allPermissionsGranted() {
//        setResult(PERMISSIONS_GRANTED);
//        finish();
    }

    // 含有全部的权限
    public static boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }



    // 显示缺失权限提示
    public static void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_help_text);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
//                setResult(PERMISSIONS_DENIED);
//                finish();

            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.setCancelable(false);

        builder.show();
    }

    // 启动应用的设置
    public static void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }


}
