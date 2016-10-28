package com.tao.permissionstools_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tao.permissionslibrary.PermissionsChecker;
import com.tao.permissionslibrary.PermissonsTools;

public class MainActivity extends AppCompatActivity {

    private PermissonsTools permissonsTools;
    private PermissionsChecker permissionsChecker;// 权限检测器
    private boolean isRequireCheck; // 是否需要系统权限检测, 防止和系统提示框重叠
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermissons();
    }

    private void initPermissons(){
        permissonsTools = new PermissonsTools(this);
        permissionsChecker = new PermissionsChecker(this);
        isRequireCheck = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = permissonsTools.PERMISSIONS;
            if (permissionsChecker.lacksPermissions(permissions)) {
                permissonsTools.requestPermissions(MainActivity.this,permissions); // 请求权限
            } else {
                permissonsTools.allPermissionsGranted(); // 全部权限都已获取
            }
        }else {
            isRequireCheck = true;
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == permissonsTools.PERMISSION_REQUEST_CODE && permissonsTools.hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            permissonsTools.allPermissionsGranted(); // 全部权限都已获取;
        } else {
            isRequireCheck = false;
            permissonsTools.showMissingPermissionDialog();
        }
    }

}
