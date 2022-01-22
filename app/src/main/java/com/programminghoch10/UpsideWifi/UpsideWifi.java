package com.programminghoch10.UpsideWifi;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class UpsideWifi implements IXposedHookInitPackageResources {
    ImageView volteIndicator;

    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.systemui")) {
            return;
        }
        resparam.res.hookLayout("com.android.systemui", "layout", "status_bar_mobile_signal_group", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                volteIndicator = liparam.view.findViewById(liparam.res.getIdentifier("volte", "id", "com.android.systemui"));
                volteIndicator.setRotation(180);
                volteIndicator.setImageResource(android.R.color.transparent);
                ViewGroup.LayoutParams params = volteIndicator.getLayoutParams();
                params.height = 0;
                params.width = 0;
                volteIndicator.setLayoutParams(params);
            }
        });
        resparam.res.hookLayout("com.android.systemui", "layout", "status_bar_mobile_signal_group", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                volteIndicator = liparam.view.findViewById(liparam.res.getIdentifier("volte", "id", "com.android.systemui"));
                volteIndicator.setRotation(180);
                volteIndicator.setImageResource(android.R.color.transparent);
                ViewGroup.LayoutParams params = volteIndicator.getLayoutParams();
                params.height = 0;
                params.width = 0;
                volteIndicator.setLayoutParams(params);
            }
        });
    }

    // Won't work for unknown reason
    /*@Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.android.systemui")) {
            return;
        }
        Class MobileIconState;
        try {
            MobileIconState = Class.forName("com.android.systemui.statusbar.phone.StatusBarSignalPolicy$MobileIconState", false, lpparam.classLoader);
        } catch (ClassNotFoundException ignored) {
            XposedBridge.log("StatusBarSignalPolicy$MobileIconState not found!!!");
            return;
        }
        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.StatusBarMobileView", lpparam.classLoader, "updateState",
                MobileIconState,
                new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("StatusBarMobileView.updateState reset");
                if (volteIndicator == null) {
                    XposedBridge.log("StatusBarMobileView.updateState volteIndicator is null???");
                }
                volteIndicator.setVisibility(View.GONE);
                volteIndicator.setImageResource(android.R.color.transparent);
            }
        });
    }*/
}
