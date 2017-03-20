package com.example.think.accessibilitytest;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by HuangMei on 2017/3/8.
 */

public class MyAccessibilityService extends AccessibilityService{

    public static int INVOKE_TYPE = 0;
    public static final int KILL_TYPE = 1;
    public static final int INSTALL_TYPE = 2;
    public static final int  UNINSTALL_TYPE = 3;

    public static void reset(){
        INVOKE_TYPE = 0;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("连接服务成功","连接服务成功" );
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        processAccessibilityEvent(event);
    }

    private void processAccessibilityEvent(AccessibilityEvent event){
        if (event.getSource() == null)
            Log.d("test", "event source = null");
        else {
            Log.d("test", "event source = " + event.getSource());
            switch (INVOKE_TYPE){
                case KILL_TYPE:
                    processKillApp(event);
                    break;
                case INSTALL_TYPE:
                    processInstallApp(event);
                    break;
                case UNINSTALL_TYPE:
                    processUnInstallApp(event);
                    break;
            }
        }
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return true;
    }

    @Override
    public void onInterrupt() {

    }

    private void processKillApp(AccessibilityEvent event){

        if (event.getSource() != null){
            if (event.getPackageName().equals("com.android.packageinstaller")){
                List<AccessibilityNodeInfo> ok_node = event.getSource().findAccessibilityNodeInfosByText("确定");
                if (ok_node != null && !ok_node.isEmpty()){
                    AccessibilityNodeInfo nodeInfo;
                    for (int i = 0; i < ok_node.size(); i ++){
                        nodeInfo = ok_node.get(i);
                        if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.isEnabled()){
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }
        }

    }

    private void processInstallApp(AccessibilityEvent event){
        if (event.getSource() != null){
            if (event.getPackageName().equals("com.android.packageinstaller")){
                List<AccessibilityNodeInfo> install_node = event.getSource().findAccessibilityNodeInfosByText("安装");
                if(install_node != null && !install_node.isEmpty()){
                    AccessibilityNodeInfo nodeInfo;
                    for (int i = 0; i < install_node.size(); i ++){
                        nodeInfo = install_node.get(i);
                        if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.isEnabled()){
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }

                List<AccessibilityNodeInfo> next_step_info = event.getSource().findAccessibilityNodeInfosByText("下一步");
                if (next_step_info != null && !next_step_info.isEmpty()){
                    AccessibilityNodeInfo nodeInfo;
                    for (int i = 0; i < next_step_info.size(); i ++){
                        nodeInfo = next_step_info.get(i);
                        if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.isEnabled()){
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }

                List<AccessibilityNodeInfo> ok_node = event.getSource().findAccessibilityNodeInfosByText("打开");
                if (ok_node != null && !ok_node.isEmpty()){
                    AccessibilityNodeInfo nodeInfo;
                    for (int i = 0; i < ok_node.size(); i ++){
                        nodeInfo = ok_node.get(i);
                        if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.isEnabled()){
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }
        }
    }

    private void processUnInstallApp(AccessibilityEvent event){

        if (event.getSource() != null){
            if (event.getPackageName().equals("com.android.settings")){
            List<AccessibilityNodeInfo> stop_node = event.getSource().findAccessibilityNodeInfosByText("强行停止");
            if (stop_node != null && !stop_node.isEmpty()){
                AccessibilityNodeInfo nodeInfo;
                for (int i = 0; i < stop_node.size(); i ++){
                    nodeInfo = stop_node.get(i);
                    if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.isEnabled()){
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }

                List<AccessibilityNodeInfo> ok_node = event.getSource().findAccessibilityNodeInfosByText("确定");
                if (ok_node != null && !ok_node.isEmpty()){
                    AccessibilityNodeInfo nodeInfo;
                    for (int i = 0; i < ok_node.size(); i ++){
                        nodeInfo = ok_node.get(i);
                        if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.isEnabled()){
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }
        }
    }
}
