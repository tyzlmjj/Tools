
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import java.lang.ref.WeakReference;

/**
 * 权限工具
 */
public class PermissionUtils {

    /**
     * 检查权限
     */
    public static boolean checkPermissions(Activity activity,String[] permissions){
        boolean result = true;
        for (String permission:permissions) {
            int rc = ActivityCompat.checkSelfPermission(activity,permission);
            if (rc != PackageManager.PERMISSION_GRANTED){
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 请求权限
     */
    public static void requestPermissions(Activity activity, final String[] permissions,
                                          final int requestCode, String hintTitle, String hintMessage) {
        final WeakReference<Activity> ac = new WeakReference<>(activity);

        boolean showRationale = false;
        for (String permission:permissions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                showRationale = true;
                break;
            }
        }
        if (!showRationale) {
            ActivityCompat.requestPermissions(activity,permissions, requestCode);
            return;
        }

        Dialog.OnClickListener listener = new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Activity a = ac.get();
                if (a != null) {
                    ActivityCompat.requestPermissions(a, permissions, requestCode);
                }
            }
        };

        new AlertDialog.Builder(activity)
                .setTitle(hintTitle)
                .setMessage(hintMessage)
                .setPositiveButton(android.R.string.ok, listener)
                .setCancelable(false)
                .show();
    }
}
