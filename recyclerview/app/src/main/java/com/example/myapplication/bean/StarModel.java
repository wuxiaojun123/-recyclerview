package com.example.myapplication.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by wuxiaojun on 2019/1/15.
 */

public class StarModel {

	private Bitmap	mIcon;		// icon

	private double	mStarAngle;	// 开始角度

	private float	mStartX;	// 起始位置

	private float	mStartY;	// 起始位置

	private Matrix	mMatrix;	// 矩阵

	private float	mInitScale;	// 初始化缩放值

	private float	mSpeed;		// 加速度

	public StarModel(Bitmap mIcon, double mStarAngle, float mStartX, float mStartY, Matrix matrix, float initScale, float speed) {
		this.mIcon = mIcon;
		this.mStarAngle = mStarAngle;
		this.mStartX = mStartX;
		this.mStartY = mStartY;
		this.mMatrix = matrix;
		this.mInitScale = initScale;
		this.mSpeed = speed;
	}

	public void draw(Canvas canvas, Paint paint, float currentProgress, int centerX, int centerY) {
		// 路径问题
		float left = (float) (centerX + mStartX + Math.cos(mStarAngle) * (Math.abs(centerX - Math.abs(mStartX))) * currentProgress * mSpeed); // 初始角度
		float top = (float) (centerY + mStartY + Math.sin(mStarAngle) * (Math.abs(centerY - Math.abs(mStartY))) * currentProgress * mSpeed);

		mMatrix.setTranslate(left, top);
		float scale = mInitScale - currentProgress;
		if (scale < 0) {
			scale = 0;
		}
		mMatrix.preScale(scale, scale);
		canvas.drawBitmap(mIcon, mMatrix, paint);
	}

	public Bitmap getmIcon() {
		return mIcon;
	}

	public void setmIcon(Bitmap mIcon) {
		this.mIcon = mIcon;
	}

	public double getmStarAngle() {
		return mStarAngle;
	}

	public void setmStarAngle(double mStarAngle) {
		this.mStarAngle = mStarAngle;
	}

	public float getmStartX() {
		return mStartX;
	}

	public void setmStartX(float mStartX) {
		this.mStartX = mStartX;
	}

	public float getmStartY() {
		return mStartY;
	}

	public void setmStartY(float mStartY) {
		this.mStartY = mStartY;
	}

	public Matrix getmMatrix() {
		return mMatrix;
	}

	public void setmMatrix(Matrix mMatrix) {
		this.mMatrix = mMatrix;
	}

}
