package com.aliex.commonlib.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

public class SDCardUtils {
    static class SDCardInfo {
        /**
         * 名称
         */
        private String label;

        /**
         * 挂载点
         */
        private String mountPoint;

        /**
         * 是否已挂载
         */
        private boolean mounted;

        /**
         * @Description:获取SD卡名称
         * @return SD卡名称
         */
        public String getLabel() {
            return label;
        }

        /**
         * @Description:设置SD卡名称
         * @param label
         *            SD卡名称
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * @Description:获取挂载路径
         * @return 挂载路径
         */
        public String getMountPoint() {
            return mountPoint;
        }

        /**
         * @Description:设置挂载路径
         * @param mountPoint
         *            挂载路径
         */
        public void setMountPoint(String mountPoint) {
            this.mountPoint = mountPoint;
        }

        /**
         * @Description:是否已经挂载
         * @return true:已经挂载，false:未挂载
         */
        public boolean isMounted() {
            return mounted;
        }

        /**
         * @Description:设置是否已经挂载
         * @param mounted
         *            true:已经挂载，false:未挂载
         */
        public void setMounted(boolean mounted) {
            this.mounted = mounted;
        }

        @Override
        public String toString() {
            return "SDCardInfo [label=" + label + ", mountPoint=" + mountPoint + ", mounted=" + mounted + "]";
        }

    }

    /**
     * 内置
     */
    static String SDCARD_INTERNAL = "internal";

    /**
     * 外置
     */
    static String SDCARD_EXTERNAL = "external";

    /**
     * API14以下通过读取Linux的vold.fstab文件来获取SDCard信息
     * 
     * @return
     */
    public static HashMap<String, SDCardInfo> getSDCardInfoBelow14() {
        HashMap<String, SDCardInfo> sdCardInfos = new HashMap<String, SDCardInfo>();
        BufferedReader bufferedReader = null;
        List<String> dev_mountStrs = null;
        try {
            // API14以下通过读取Linux的vold.fstab文件来获取SDCard信息
            bufferedReader = new BufferedReader(new FileReader(Environment.getRootDirectory().getAbsoluteFile()
                    + File.separator + "etc" + File.separator + "vold.fstab"));
            dev_mountStrs = new ArrayList<String>();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("dev_mount")) {
                    dev_mountStrs.add(line);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; dev_mountStrs != null && i < dev_mountStrs.size(); i++) {
            SDCardInfo sdCardInfo = new SDCardInfo();
            String[] infoStr = dev_mountStrs.get(i).split(" ");
            sdCardInfo.setLabel(infoStr[1]);
            sdCardInfo.setMountPoint(infoStr[2]);
            if (sdCardInfo.getMountPoint().equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                sdCardInfo.setMounted(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
                sdCardInfos.put(SDCARD_INTERNAL, sdCardInfo);
            } else if (sdCardInfo.getMountPoint().startsWith("/mnt") && !sdCardInfo.getMountPoint()
                    .equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                File file = new File(sdCardInfo.getMountPoint() + File.separator + "temp");
                if (file.exists()) {
                    sdCardInfo.setMounted(true);
                } else {
                    if (file.mkdir()) {
                        file.delete();
                        sdCardInfo.setMounted(true);
                    } else {
                        sdCardInfo.setMounted(false);
                    }
                }
                sdCardInfos.put(SDCARD_EXTERNAL, sdCardInfo);
            }
        }
        return sdCardInfos;
    }

    // API14以上包括14通过改方法获取
    public static HashMap<String, SDCardInfo> getSDCardInfo(Context context) {
        HashMap<String, SDCardInfo> sdCardInfos = new HashMap<String, SDCardInfo>();
        String[] storagePathList = null;
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Method getVolumePaths = storageManager.getClass().getMethod("getVolumePaths");
            storagePathList = (String[]) getVolumePaths.invoke(storageManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (storagePathList != null && storagePathList.length > 0) {
            String mSDCardPath = storagePathList[0];
            SDCardInfo internalDevInfo = new SDCardInfo();
            internalDevInfo.setMountPoint(mSDCardPath);
            internalDevInfo.setMounted(checkSDCardMount14(context, mSDCardPath));
            sdCardInfos.put(SDCARD_INTERNAL, internalDevInfo);
            if (storagePathList.length >= 2) {
                String externalDevPath = storagePathList[1];
                SDCardInfo externalDevInfo = new SDCardInfo();
                externalDevInfo.setMountPoint(storagePathList[1]);
                externalDevInfo.setMounted(checkSDCardMount14(context, externalDevPath));
                sdCardInfos.put(SDCARD_EXTERNAL, externalDevInfo);
            }
        }
        return sdCardInfos;
    }

    /**
     * @Description:判断SDCard是否挂载上,返回值为true证明挂载上了，否则未挂载
     * @param context
     *            上下文
     * @param mountPoint
     *            挂载点
     */
    protected static boolean checkSDCardMount14(Context context, String mountPoint) {
        if (mountPoint == null) {
            return false;
        }
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Method getVolumeState = storageManager.getClass().getMethod("getVolumeState", String.class);
            String state = (String) getVolumeState.invoke(storageManager, mountPoint);
            return Environment.MEDIA_MOUNTED.equals(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
