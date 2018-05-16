import android.app.Activity;
import android.app.ProgressDialog;

import java.lang.ref.WeakReference;

/**
 * 显示加载提示框的帮助类
 */
public class LoadingDialogHelper {

    private WeakReference<Activity> mActivity;

    private ProgressDialog mLoadingDialog;

    LoadingDialogHelper(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    /**
     * 显示加载提示框
     * @param msg   提示信息
     */
    public void showLoading(String msg) {
        Activity activity = mActivity.get();
        if (activity == null || activity.isFinishing()){return;}
        if (mLoadingDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            mLoadingDialog = progressDialog;
        }
        mLoadingDialog.setMessage(msg);
        mLoadingDialog.show();
    }

    /**
     * 隐藏加载提示框
     */
    public void hideLoading() {
        Activity activity = mActivity.get();
        if (activity == null || activity.isFinishing()){return;}

        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.hide();
        }
    }
}
