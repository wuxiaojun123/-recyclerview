package com.example.myapplication.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;

import com.example.myapplication.App;


/**
 * Created by wuxiaojun on 2018/9/14.
 */

public class ScreenUtils {

	public static float SCALE = 0.92f;

	private ScreenUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * Return the width of screen, in pixel.
	 *
	 * @return the width of screen, in pixel
	 */
	public static int getScreenWidth() {
		WindowManager wm = (WindowManager) App.getApplication().getSystemService(Context.WINDOW_SERVICE);
		Point point = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			// noinspection ConstantConditions
			wm.getDefaultDisplay().getRealSize(point);
		} else {
			// noinspection ConstantConditions
			wm.getDefaultDisplay().getSize(point);
		}
		return point.x;
	}

	/**
	 * Return the height of screen, in pixel.
	 *
	 * @return the height of screen, in pixel
	 */
	public static int getScreenHeight() {
		WindowManager wm = (WindowManager) App.getApplication().getSystemService(Context.WINDOW_SERVICE);
		Point point = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			// noinspection ConstantConditions
			wm.getDefaultDisplay().getRealSize(point);
		} else {
			// noinspection ConstantConditions
			wm.getDefaultDisplay().getSize(point);
		}
		return point.y;
	}

	/**
	 * Return the density of screen.
	 *
	 * @return the density of screen
	 */
	public static float getScreenDensity() {
		return Resources.getSystem().getDisplayMetrics().density;
	}

	/**
	 * Return the screen density expressed as dots-per-inch.
	 *
	 * @return the screen density expressed as dots-per-inch
	 */
	public static int getScreenDensityDpi() {
		return Resources.getSystem().getDisplayMetrics().densityDpi;
	}

	/**
	 * Set full screen.
	 *
	 * @param activity
	 *            The activity.
	 */
	public static void setFullScreen(@NonNull final Activity activity) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * Set non full screen.
	 *
	 * @param activity
	 *            The activity.
	 */
	public static void setNonFullScreen(@NonNull final Activity activity) {
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * Toggle full screen.
	 *
	 * @param activity
	 *            The activity.
	 */
	public static void toggleFullScreen(@NonNull final Activity activity) {
		int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window window = activity.getWindow();
		if ((window.getAttributes().flags & fullScreenFlag) == fullScreenFlag) {
			window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}

	/**
	 * Return whether screen is full.
	 *
	 * @param activity
	 *            The activity.
	 * @return {@code true}: yes<br>
	 * 		{@code false}: no
	 */
	public static boolean isFullScreen(@NonNull final Activity activity) {
		int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
	}

	/**
	 * Set the screen to landscape.
	 *
	 * @param activity
	 *            The activity.
	 */
	public static void setLandscape(@NonNull final Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * Set the screen to portrait.
	 *
	 * @param activity
	 *            The activity.
	 */
	public static void setPortrait(@NonNull final Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * Return whether screen is landscape.
	 *
	 * @return {@code true}: yes<br>
	 * 		{@code false}: no
	 */
	public static boolean isLandscape() {
		return App.getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	/**
	 * Return whether screen is portrait.
	 *
	 * @return {@code true}: yes<br>
	 * 		{@code false}: no
	 */
	public static boolean isPortrait() {
		return App.getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	/**
	 * Return the rotation of screen.
	 *
	 * @param activity
	 *            The activity.
	 * @return the rotation of screen
	 */
	public static int getScreenRotation(@NonNull final Activity activity) {
		switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
			case Surface.ROTATION_0:
				return 0;
			case Surface.ROTATION_90:
				return 90;
			case Surface.ROTATION_180:
				return 180;
			case Surface.ROTATION_270:
				return 270;
			default:
				return 0;
		}
	}

	/**
	 * Return the bitmap of screen.
	 *
	 * @param activity
	 *            The activity.
	 * @return the bitmap of screen
	 */
	public static Bitmap screenShot(@NonNull final Activity activity) {
		return screenShot(activity, false);
	}

	/**
	 * Return the bitmap of screen.
	 *
	 * @param activity
	 *            The activity.
	 * @param isDeleteStatusBar
	 *            True to delete status bar, false otherwise.
	 * @return the bitmap of screen
	 */
	public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
		View decorView = activity.getWindow().getDecorView();
		decorView.setDrawingCacheEnabled(true);
		decorView.setWillNotCacheDrawing(false);
		Bitmap bmp = decorView.getDrawingCache();
		if (bmp == null) return null;
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		Bitmap ret;
		if (isDeleteStatusBar) {
			Resources resources = activity.getResources();
			int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
			int statusBarHeight = resources.getDimensionPixelSize(resourceId);
			ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
		} else {
			ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
		}
		decorView.destroyDrawingCache();
		return ret;
	}

	/**
	 * Return whether screen is locked.
	 *
	 * @return {@code true}: yes<br>
	 * 		{@code false}: no
	 */
	public static boolean isScreenLock() {
		KeyguardManager km = (KeyguardManager) App.getApplication().getSystemService(Context.KEYGUARD_SERVICE);
		// noinspection ConstantConditions
		return km.inKeyguardRestrictedInputMode();
	}

	public static int dp2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 宽高比例
	 *
	 * @return
	 */
	public static float wrradioH() {
		int height = ScreenUtils.getScreenHeight();
		int width = ScreenUtils.getScreenWidth();
		return (float) width / height;
	}



	public static int getDimens(Context mContext, @DimenRes int id) {
		return mContext.getResources().getDimensionPixelOffset(id);
	}
}
