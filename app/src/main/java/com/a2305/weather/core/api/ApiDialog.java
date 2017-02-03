package com.a2305.weather.core.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

public class ApiDialog implements ApiCallback {

	private Activity mActivity;
	private Dialog mDialog;
	private boolean mCancelable;

	private boolean mCompleted = true;
	private String mErrorMessage = null;
	private String mDescription = "";

	public ApiDialog(Activity activity, boolean cancelable) {
		mActivity = activity;
		mCancelable = cancelable;
	}

	@Override
	public void onApiStart(String description) {
		mDescription = description;
		mCompleted = false;
		displayWaitDialog();
	}

	@Override
	public void onApiEnd() {
		mCompleted = true;
		closeUIDialog();
	}

	@Override
	public void onApiError(String error) {
		mCompleted = true;
		displayErrorDialog(error);
	}

	private void closeUIDialog() {
		Activity activity = mActivity;
		if (activity != null) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					closeDialog();
				}
			});
		}
	}

	private void closeDialog() {
		Dialog dialog = mDialog;
		if (dialog != null) {
			try {
				dialog.cancel();
			} catch (Exception e) {
			}
		}
	}

	public void setActivity(Activity activity) {
		mActivity = activity;
		if (activity != null) {
			if (!mCompleted) {
				displayWaitDialog();
			} else if (mErrorMessage != null) {
				displayErrorDialog(mErrorMessage);
			}
		} else {
			mDialog = null;
		}
	}

	private boolean displayWaitDialog() {
		Activity activity = mActivity;
		if (activity != null) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					closeDialog();
					ProgressDialog dialog;
					dialog = new ProgressDialog(mActivity);
					dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					dialog.setMessage(mDescription);
					dialog.setCancelable(mCancelable);
					dialog.setCanceledOnTouchOutside(mCancelable);
					dialog.show();
					mDialog = dialog;
				}
			});
			return true;
		}
		return false;
	}

	private boolean displayErrorDialog(final String error) {
		final Activity activity = mActivity;
		if (activity != null) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					closeDialog();
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setMessage(error).setPositiveButton("OK", null);
					mDialog = builder.create();
					mDialog.show();
				}
			});
			mErrorMessage = null;
			return true;
		}
		mErrorMessage = error;
		return false;
	}

}
