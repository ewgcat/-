package com.kymjs.themvp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kymjs.themvp.utils.AppManager;
import com.kymjs.themvp.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;



public class DialogUtils {
    /**
     * 应用市场
     * 腾讯应用宝 com.tencent.android.qqdownloader
     * 小米应用商店 com.xiaomi.market
     * VIVO应用商店 com.bbk.appstore
     * OPPO应用商店 com.oppo.market
     * 魅族应用市场 com.meizu.mstore
     * 华为应用商店 com.huawei.appmarket
     * 阿里应用可以直接挑战到UC浏览器的下载 com.UCMobile
     */
    public final static String TENCENTSHOP = "com.tencent.android.qqdownloader";//应用宝
    public final static String XIAOMISHOP = "com.xiaomi.market";//小米
    public final static String VIVOSHOP = "com.bbk.appstore";
    public final static String OPPOSHOP = "com.oppo.market";
    public final static String MEIZUSHOP = "com.meizu.mstore";
    public final static String HWSHOP = "com.huawei.appmarket";
    public final static String UCSHOP = "com.UCMobile";

    public static PopupWindow popupWindow;

    public interface IkoneOnClick {
        void onClick();
    }

    public static void showPopupDialog(final Activity context, String msg, String button, final String cancle, String title, final IkoneOnClick ikone) {
        View view = context.getLayoutInflater().inflate(R.layout.dialog_popup, null);
        TextView tv1 = (TextView) view.findViewById(R.id.tv1);
        TextView btn_i_ok = (TextView) view.findViewById(R.id.btn_i_ok);
        TextView btn_i_cancle = (TextView) view.findViewById(R.id.btn_i_cancle);
        tv1.setText(msg);
        view.findViewById(R.id.tv).setVisibility(View.GONE);
        btn_i_ok.setText(button);
        btn_i_cancle.setVisibility(View.GONE);
        view.findViewById(R.id.view1).setVisibility(View.GONE);
        if (cancle != null) {
            if (!cancle.equals("")) {
                btn_i_cancle.setVisibility(View.VISIBLE);
                btn_i_cancle.setText(cancle);
                view.findViewById(R.id.view1).setVisibility(View.VISIBLE);
            }
        } else {
            btn_i_cancle.setVisibility(View.GONE);
            view.findViewById(R.id.view1).setVisibility(View.GONE);
        }
        if (button != null) {
            btn_i_ok.setVisibility(View.VISIBLE);
            btn_i_ok.setText(button);
            view.findViewById(R.id.view1).setVisibility(View.VISIBLE);
        } else {
            btn_i_ok.setVisibility(View.GONE);
            view.findViewById(R.id.view1).setVisibility(View.GONE);
        }
        if (title != null) {
            if (!title.equals("")) {
                view.findViewById(R.id.tv).setVisibility(View.VISIBLE);
                TextView textView = (TextView) view.findViewById(R.id.tv);
                textView.setText(title);
            }
        }
        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(view);
        popupWindow.setWindowLayoutMode(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(false);// 设置PopupWindow可获得焦点
        btn_i_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                if (cancle != null) {
                    if (!cancle.equals("")) {
                        ikone.onClick();
                    }
                }
            }
        });
        btn_i_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                if (cancle == null) {
                    ikone.onClick();
                } else {
                    if (cancle.equals("")) {
                        ikone.onClick();
                    }
                }
            }
        });
        popupWindow.showAtLocation(context.findViewById(R.id.li_all), Gravity.CENTER, 0, 0);
    }

    /**
     * 更新
     *
     * @param must true 必须更新
     */
    public static void updateDialog(final Activity context, final boolean must, String msg, final String url, final String appPkg) {
        DialogUtils.showPopupDialog(context, msg, must ? null : "下次再说", "立即更新", "发现新版本", new DialogUtils.IkoneOnClick() {
            @Override
            public void onClick() {
                if (SystemUtils.SYS_MIUI.equals(SystemUtils.getSystem())) {//小米
                    if (isAvilible(context, XIAOMISHOP)) {//小米应用市场
                        toMarket(context, appPkg, XIAOMISHOP, url);
                    } else {
                        commonMarket(context, appPkg, url);
                    }
                } else if (SystemUtils.SYS_FLYME.equals(SystemUtils.getSystem())) {//魅族
                    if (isAvilible(context, MEIZUSHOP)) {//魅族应用市场
                        toMarket(context, appPkg, MEIZUSHOP, url);
                    } else {
                        commonMarket(context, appPkg, url);
                    }
                } else if (SystemUtils.SYS_EMUI.equals(SystemUtils.getSystem())) {//华为
                    if (isAvilible(context, HWSHOP)) {//华为应用市场
                        toMarket(context, appPkg, HWSHOP, url);
                    } else {
                        commonMarket(context, appPkg, url);
                    }
                } else if (SystemUtils.SYS_VIVO.equals(SystemUtils.getSystem())) {//VOIVO
                    if (isAvilible(context, VIVOSHOP)) {//VIVO应用市场
                        toMarket(context, appPkg, VIVOSHOP, url);
                    } else {
                        commonMarket(context, appPkg, url);
                    }
                } else if (SystemUtils.SYS_OPPO.equals(SystemUtils.getSystem())) {//OPPO
                    if (isAvilible(context, OPPOSHOP)) {//OPPO应用市场
                        toMarket(context, appPkg, OPPOSHOP, url);
                    } else {
                        commonMarket(context, appPkg, url);
                    }
                } else {//跳转到浏览器
                    commonMarket(context, appPkg, url);
                }
                if (must) {
                    AppManager.getAppManager().AppForceExit(context);
                }
            }
        });
    }

    public static void downLoadDialog(final Activity context, final String url, final String appPkg, IkoneOnClick ikoneOnClick) {
        if (SystemUtils.SYS_MIUI.equals(SystemUtils.getSystem())) {//小米
            if (isAvilible(context, XIAOMISHOP)) {//小米应用市场
                toMarket(context, appPkg, XIAOMISHOP, url);
            } else {
                commonMarket(context, appPkg, url);
            }
        } else if (SystemUtils.SYS_FLYME.equals(SystemUtils.getSystem())) {//魅族
            if (isAvilible(context, MEIZUSHOP)) {//魅族应用市场
                toMarket(context, appPkg, MEIZUSHOP, url);
            } else {
                commonMarket(context, appPkg, url);
            }
        } else if (SystemUtils.SYS_EMUI.equals(SystemUtils.getSystem())) {//华为
            if (isAvilible(context, HWSHOP)) {//华为应用市场
                toMarket(context, appPkg, HWSHOP, url);
            } else {
                commonMarket(context, appPkg, url);
            }
        } else if (SystemUtils.SYS_VIVO.equals(SystemUtils.getSystem())) {//VOIVO
            if (isAvilible(context, VIVOSHOP)) {//VIVO应用市场
                toMarket(context, appPkg, VIVOSHOP, url);
            } else {
                commonMarket(context, appPkg, url);
            }
        } else if (SystemUtils.SYS_OPPO.equals(SystemUtils.getSystem())) {//OPPO
            if (isAvilible(context, OPPOSHOP)) {//OPPO应用市场
                toMarket(context, appPkg, OPPOSHOP, url);
            } else {
                commonMarket(context, appPkg, url);
            }
        } else {//跳转到浏览器
            commonMarket(context, appPkg, url);
        }
    }

    /**
     * 跳到公用的市场
     *
     * @param context
     * @param appPkg
     * @param url
     */
    public static void commonMarket(Context context, String appPkg, String url) {
        if (isAvilible(context, TENCENTSHOP)) {//应用宝
            toMarket(context, appPkg, TENCENTSHOP, url);
        } else if (isAvilible(context, UCSHOP)) {//UC浏览器
            toMarket(context, appPkg, UCSHOP, url);
        } else {//跳转到浏览器
            Intent intent1 = new Intent();
            Uri content_url = Uri.parse(url);
            intent1.setAction(Intent.ACTION_VIEW);
            intent1.setData(content_url);
            context.startActivity(intent1);
        }
    }

    /**
     * 跳转应用商店.
     *
     * @param context   {@link Context}
     * @param appPkg    包名
     * @param marketPkg 应用商店包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean toMarket(Context context, String appPkg, String marketPkg, String url) {
        Uri uri = Uri.parse("market://details?id=" + appPkg);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (marketPkg != null) {// 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
            intent.setPackage(marketPkg);
            try {
                context.startActivity(intent);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            Intent intent1 = new Intent();
            Uri content_url = Uri.parse(url);
            intent1.setAction(Intent.ACTION_VIEW);
            intent1.setData(content_url);
            context.startActivity(intent1);
            return true;
        }
    }

    // 判断市场是否存在的方法
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }


}
