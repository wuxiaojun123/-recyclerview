package com.example.myapplication.customLayoutManager;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.bean.ItemViewInfo;

import java.util.ArrayList;


/****
 * 自定义卡片的layoutmanager
 *
 * 1.getViewForPosition()这个方法返回的view可能是回收的view也有可能是new出来的view
 * 2.
 * 3.math.floor()向下取整
 */
public class CardLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "cardLayoutManager";

    private float mScale = 0.9f;
    private final float widthPercent = 0.87f;
    private final float heightPercent = 1.46f;

    private int mItemCount;
    private int mScrollOffset;
    private int mItemViewWidth;
    private int mItemViewHeight;

    public CardLayoutManager(Context context) {
        mScrollOffset = Integer.MAX_VALUE;
        mItemViewWidth = (int) (getHorizontalSpace() * widthPercent);
        mItemViewHeight = (int) (mItemViewWidth * heightPercent);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,RecyclerView.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {

            Log.e(TAG,"onLayoutChildren ");

            // 需要摆放子view
            if(state.getItemCount() == 0) {
                return;
            }
            if(state.isPreLayout()) {
                return;
            }
            removeAndRecycleAllViews(recycler);

            // 获取到单个item的宽度和高度
            mItemViewWidth = (int) (getHorizontalSpace() * widthPercent);
            mItemViewHeight = (int) (mItemViewWidth * heightPercent);
            mItemCount = getItemCount();
            mScrollOffset = Math.min(Math.max(mItemViewHeight,mScrollOffset),mItemCount*mItemViewHeight);

            layoutChild(recycler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void layoutChild(RecyclerView.Recycler recycler) {
        if(getItemCount() == 0) {
            return;
        }
        Log.e(TAG,"getVerticalSpace=" + getVerticalSpace() + "mScrollOffset=" + mScrollOffset + "mItemViewHeight=" + mItemViewHeight);
        // 获取最后一个item的位置
        int lastItemPosition = (int) Math.floor(mScrollOffset / mItemViewHeight);
        Log.e(TAG,"lastItemPosition="+lastItemPosition);
        // 获取到出去一个完整的item的高度，还剩余多少空间
        int remainSpace = getVerticalSpace() - mItemViewHeight;
        // 滑动的时候获取最后一个item在屏幕上还显示的高度
        int lastItemVisibleHeight = mScrollOffset % mItemViewHeight;
        // 最后一个item显示高度相对于本身的比例
        final float offsetPercentRelativeToItemView = lastItemVisibleHeight * 1.0f / lastItemVisibleHeight;



        // 把我们需要的item添加到这个集合中
        ArrayList<ItemViewInfo> layoutInfos = new ArrayList<>();
        for (int i = lastItemPosition-1,j=1; i >= 0; i--,j++) {
            // 计算偏移量
            double maxOffset = (getVerticalSpace() - mItemViewHeight) / 2 * Math.pow(0.8,j);
            // 这个item的top值
            int start = (int)(remainSpace - offsetPercentRelativeToItemView * maxOffset);

            //这个Item需要缩放的比例
            float scaleXY = (float) (Math.pow(mScale, j - 1) * (1 - offsetPercentRelativeToItemView * (1 - mScale)));
            float positonOffset = offsetPercentRelativeToItemView;
            //Item上面的距离占RecyclerView可用高度的比例
            float layoutPercent = start * 1.0f / getVerticalSpace();
            ItemViewInfo info = new ItemViewInfo(start, scaleXY, positonOffset, layoutPercent);
            layoutInfos.add(0, info);
            remainSpace = (int) (remainSpace - maxOffset);
            //在添加Item的同时，计算剩余空间是否可以容下下一个Item，如果不能的话，就不再添加了
            if (remainSpace <= 0) {
                info.setTop((int) (remainSpace + maxOffset));
                info.setPositionOffset(0);
                info.setLayoutPercent(info.getTop() / getVerticalSpace());
                info.setScaleXY((float) Math.pow(mScale, j - 1)); ;
                break;
            }

        }

        if (lastItemPosition < mItemCount) {
            final int start = getVerticalSpace() - lastItemVisibleHeight;
            layoutInfos.add(new ItemViewInfo(start, 1.0f, lastItemVisibleHeight * 1.0f / mItemViewHeight, start * 1.0f / getVerticalSpace())
                    .setIsBottom());
        } else {
            lastItemPosition = lastItemPosition - 1; // 99
        }

        int layoutCount = layoutInfos.size();
        final int startPos = lastItemPosition - (layoutCount - 1);
        final int endPos = lastItemPosition;
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            int pos = getPosition(childView);
            if (pos > endPos || pos < startPos) {
                removeAndRecycleView(childView, recycler);
            }
        }

        detachAndScrapAttachedViews(recycler);

        for (int i = 0; i < layoutCount; i++) {
            View view = recycler.getViewForPosition(startPos + i);
            ItemViewInfo layoutInfo = layoutInfos.get(i);
            addView(view);
            measureChildWithExactlySize(view);

            int left = (getHorizontalSpace() - mItemViewWidth) / 2;
            layoutDecoratedWithMargins(view, left, layoutInfo.getTop(), left + mItemViewWidth, layoutInfo.getTop() + mItemViewHeight);
            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(0);
            view.setScaleX(layoutInfo.getScaleXY());
            view.setScaleY(layoutInfo.getScaleXY());
        }

    }

    private void measureChildWithExactlySize(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mItemViewWidth,View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mItemViewHeight,View.MeasureSpec.EXACTLY);
        view.measure(widthMeasureSpec,heightMeasureSpec);
    }

//    @Override
//    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        int pendingScrollOffset = mScrollOffset + dy;
//        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset + dy), mItemCount * mItemViewHeight);
//        layoutChild(recycler);
//        return mScrollOffset - pendingScrollOffset + dy;
//    }
//
//
//    @Override
//    public boolean canScrollVertically() {
//        return true;
//    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

}
