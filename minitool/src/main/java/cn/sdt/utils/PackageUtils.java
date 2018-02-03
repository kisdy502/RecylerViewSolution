package cn.sdt.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @author ljf++
 * @date 14:49 2017/11/9
 * @description android应用管理工具类
 */

public class PackageUtils {
    private static final String TAG = PackageUtils.class.getSimpleName();

    public final static int INSTALL_SUCCEEDED = 1;
    public static final int INSTALL_FAILED_DUPLICATE_PERMISSION = -112;
    public static final int INSTALL_REPLACE_EXISTING = 0x00000002;

    public static String getFingerprintMd5(X509Certificate cf, Context context, String packageName) {
        if (null == cf) {
            return null;
        }
        String hex = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] publicKey = md.digest(cf.getEncoded());
            hex = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hex;
    }

    public static String getFingerprintMd5(Context context, String packageName) {
        X509Certificate cf = getPackageX509Certificate(context, packageName);
        return getFingerprintMd5(cf, context, packageName);
    }

    public static String getFingerprintSha1(X509Certificate cf, Context context, String packageName) {
        if (null == cf) {
            return null;
        }
        String hex = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cf.getEncoded());
            hex = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hex;
    }

    public static String getFingerprintSha1(Context context, String packageName) {
        X509Certificate cf = getPackageX509Certificate(context, packageName);
        return getFingerprintSha1(cf, context, packageName);
    }


    public static X509Certificate getPackageX509Certificate(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        Signature[] signatures = packageInfo.signatures;
        if (null == signatures || signatures.length < 1) {
            return null;
        }
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        }
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        }
        try {
            input.close();
            input = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return c;
    }

    private static String byte2HexFormatted(byte[] bytes) {
        final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        char[] hexChars = new char[bytes.length * 3 - 1];
        int v = 0x00;
        for (int i = 0; i < bytes.length; i++) {
            v = bytes[i] & 0xff; // 保留最后两位，即两个16进制位
            // high 4bit
            hexChars[i * 3] = HEX[v >>> 4]; // 忽略符号右移，空出补0
            // low 4bit
            hexChars[i * 3 + 1] = HEX[v & 0x0f];
            if (i < bytes.length - 1) {
                hexChars[i * 3 + 2] = ':';
            }
        }
        return String.valueOf(hexChars);
    }


    public static PackageInfo getPackageInfo(Context context, String packageName) {
        if (null == packageName || packageName.isEmpty()) {
            return null;
        }
        PackageInfo info = null;
        try {
            PackageManager manager = context.getPackageManager();
            info = manager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return info;
    }

    public static int getVersionCode(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return -1;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    public static String getVersionName(Context context, String packageName) {
        if (null == packageName || packageName.isEmpty()) {
            return "";
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static boolean installPackage(Context context, String filePath) {
        final int level = Build.VERSION.SDK_INT;
        if (level <= 19) {
            return installPackageIce(context, filePath);
        }

        return installPackageLolli(context, filePath);
    }

    /**
     * android 4.0.1 ~ android 4.4.2的系统函数安装
     */
    public static boolean installPackageIce(Context context, String filePath) {
        int installFlags = 0;
        installFlags |= INSTALL_REPLACE_EXISTING;

        final Uri apkURI = Uri.fromFile(new File(filePath));
        PackageInstallObserver obs = new PackageInstallObserver();
        PackageManager pm = context.getPackageManager();
        try {
            Method method = PackageManager.class.getDeclaredMethod("installPackage", Uri.class,
                    IPackageInstallObserver.class, int.class, String.class);
            method.setAccessible(true);
            method.invoke(pm, apkURI, obs, installFlags, null);
            synchronized (obs) {
                while (!obs.finished) {
                    try {
                        obs.wait();
                    } catch (InterruptedException e) {
                    }
                }
                final boolean ret = (obs.result == INSTALL_SUCCEEDED);
                if (!ret) {
                    Log.e(TAG, "installPackageIce install failed. file: " + filePath
                            + " resultCode: " + obs.result);
                }
                return ret;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void uninstallNormal(Context context, String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class PackageInstallObserver extends IPackageInstallObserver.Stub {
        boolean finished;
        int result;

        public void packageInstalled(String name, int status) {
            synchronized (this) {
                finished = true;
                result = status;
                notifyAll();
            }
        }
    }

    /**
     * FUNTODO ljf++ 20170605后续修改
     * android 5.0 ~ android 6.0系统函数安装
     */
    public static boolean installPackageLolli(Context context, String filePath) {
        return installPackageIce(context, filePath);
    }

    static class PackageInstallObserver2 extends IPackageInstallObserver2.Stub {
        final String EXTRA_FAILURE_EXISTING_PERMISSION
                = "android.content.pm.extra.FAILURE_EXISTING_PERMISSION";
        final String EXTRA_FAILURE_EXISTING_PACKAGE
                = "android.content.pm.extra.FAILURE_EXISTING_PACKAGE";

        boolean finished;
        int result;
        String extraPermission;
        String extraPackage;

        @Override
        public void onUserActionRequired(Intent intent) throws RemoteException {
        }

        @Override
        public void onPackageInstalled(String basePackageName, int returnCode, String msg,
                                       Bundle extras) throws RemoteException {
            synchronized (this) {
                finished = true;
                result = returnCode;
                if (returnCode == INSTALL_FAILED_DUPLICATE_PERMISSION) {
                    extraPermission = extras.getString(
                            EXTRA_FAILURE_EXISTING_PERMISSION);
                    extraPackage = extras.getString(
                            EXTRA_FAILURE_EXISTING_PACKAGE);
                }
                notifyAll();
            }
        }
    }
}
