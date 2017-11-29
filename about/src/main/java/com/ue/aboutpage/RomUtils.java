package com.ue.aboutpage;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hawk on 2017/11/29.
 */

public class RomUtils {
    public static final String MIUI = "miui";
    public static final String EMUI_0 = "emotionui";
    public static final String EMUI_1 = "emui";
    public static final String FLYME = "flyme";
    public static final String COLOROS = "coloros";
    public static final String FUNTOUCH = "funtouch";
    private static final String ROM_ALL = "ro.build.display.id";
    private static final String ROM_MIUI = "ro.miui.ui.version.name";
    private static final String ROM_EMUI = "ro.build.version.emui";
    private static final String ROM_OPPO = "ro.build.version.opporom";
    private static final String ROM_VIVO = "ro.vivo.os.version";

    public static boolean isOppo() {
        return isTargetRom(ROM_OPPO, COLOROS);
    }

    public static boolean isVivo() {
        return isTargetRom(ROM_VIVO, FUNTOUCH);
    }

    public static boolean isFlyme() {
        return isTargetRom(null, FLYME);
    }

    public static boolean isMiui() {
        return isTargetRom(ROM_MIUI, MIUI);
    }

    public static boolean isEmui() {
        boolean isEmui = !TextUtils.isEmpty(getRomProperty(ROM_EMUI));
        if (isEmui) {
            return true;
        }
        String romProperty = getRomProperty(ROM_ALL);
        return (romProperty.contains(EMUI_0) || romProperty.contains(EMUI_1));
    }

    private static boolean isTargetRom(String targetRom, String targetRomName) {
        if (TextUtils.isEmpty(targetRom)) {
            return getRomProperty(ROM_ALL).contains(targetRomName);
        }
        if (!TextUtils.isEmpty(getRomProperty(targetRom))) {
            return true;
        }
        return getRomProperty(ROM_ALL).contains(targetRomName);
    }

    private static String getRomProperty(String prop) {
        String line = "";
        BufferedReader reader = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("getprop " + prop);
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (p != null) {
                p.destroy();
            }
        }
        return line.toLowerCase();
    }
}
