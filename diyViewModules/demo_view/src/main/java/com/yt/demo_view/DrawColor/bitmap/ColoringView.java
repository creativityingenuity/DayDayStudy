package com.yt.demo_view.DrawColor.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.io.File;
import java.util.LinkedList;

public class ColoringView extends View {
    private static final String TAG = ColoringView.class.getSimpleName();
    private static final int ACTION_SAVING = 3;
    private static final int ACTION_EDITING = 1;
    private final Matrix imageMatrix = new Matrix();
    private final GestureDetector gestureDetector;
    private Bitmap coverBmp;
    private ScaleGestureDetector scaleGestureDetector;
    private LinkedList<Action> backStack = new LinkedList<>();
    private LinkedList<Action> stepStack = new LinkedList<>();
    private int targetColor = Color.RED;
    private PixelsManager pixelsManager;
    private boolean multiPointerAction;
    private boolean translated;
    private PointF touchingPoint = new PointF();
    private float minScaleFactor = 1.f;
    private float mScaleFactor;
    private float focusingX;
    private float focusingY;
    private float[] tempImageMatrixFloat = new float[9];
    private RectF tempImageRect = new RectF();
    private Paint borderPaint = new Paint();
    private OnTapChangeColor onTapChangeColor;
    private PointF actionDownPoint = new PointF();
    private BitmapDrawable mDrawable;
    private int mDrawableWidth;
    private int mDrawableHeight;
    private Paint imagePaint = new Paint();
    private int actionState;
    private boolean isLongPress = false;
    private float colorPickerRadius = 400;
    private float colorPickerStrokeWidth = 100;
    private RectF tempImageBorderRect = new RectF();
    private int bottomScalePadding = 200;
    private int topScalePadding = 50;

    public void destroy() {
        if (pixelsManager != null) {
            pixelsManager.finish();
        }
        onTapChangeColor = null;
        try {
            coverBmp.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ColoringView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bottomScalePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, context.getResources().getDisplayMetrics());
        topScalePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        colorPickerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics());
        colorPickerStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                isLongPress = true;
                pickColor(e);
                invalidate();
            }
        });
    }

    public void setTargetColor(int color) {
        targetColor = color;
    }

    public int backwardStepCounts() {
        return backStack.size();
    }

    public int forwardStepCounts() {
        return stepStack.size();
    }

    public void trySaveCurrentState(String url) {
        actionState = ACTION_SAVING;
        new SaveBitmapTask(getContext(), coverBmp, url).execute();
    }

    public void trySaveCurrentState(File file) {
        actionState = ACTION_SAVING;
        new SaveBitmapTask(getContext(), coverBmp, file).execute();
    }

    public void setImageByBitmap(Bitmap originalBmp, boolean haveEdited) {
        int[] pixels = new int[originalBmp.getWidth() * originalBmp.getHeight()];
        originalBmp.getPixels(pixels, 0, originalBmp.getWidth(), 0, 0, originalBmp.getWidth(), originalBmp.getHeight());
        pixelsManager = new PixelsManager(pixels, originalBmp.getWidth(), originalBmp.getHeight(), haveEdited, originalBmp.getConfig());
        if (coverBmp != null) {
            coverBmp.recycle();
        }
        coverBmp = Bitmap.createBitmap(pixelsManager.getWidth(), pixelsManager.getHeight(), pixelsManager.getConfig());
        pixelsManager.fillBitmap(coverBmp);
        BitmapDrawable coverDrawable = new BitmapDrawable(getResources(), coverBmp);
        setImageDrawable(coverDrawable);
        setMinScaleFactor(getWidth(), getHeight());
        correctScroll();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setMinScaleFactor(w, h);
    }

    private void setMinScaleFactor(int w, int h) {
        if (pixelsManager != null && w > 0 && h > 0) {
            minScaleFactor = Math.min(w, h) * .93f / pixelsManager.getWidth();
        }
        Point point = getPointForBmp(0, 0);
        if (point != null) {
            focusingX = point.x;
            focusingY = point.y;
        }
        mScaleFactor = minScaleFactor;
        scaleBitmap();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_POINTER_DOWN == event.getActionMasked()) {
            multiPointerAction = event.getActionIndex() >= 1;
        }
        gestureDetector.onTouchEvent(event);
        if (multiPointerAction) {
            scaleGestureDetector.onTouchEvent(event);
            if (MotionEvent.ACTION_UP == event.getAction()) {
                correctScroll();
                scrollBy(0, 0);
//                Log.e(TAG, "scale end.");
                multiPointerAction = false;
            }
            return true;
        }
        if (isLongPress) {
            return pickColorTouchEvent(event);
        }
        return fillColorTouchEvent(event);
    }

    private boolean fillColorTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDownPoint.set(event.getX(), event.getY());
                touchingPoint.set(event.getX(), event.getY());
//                Log.e(TAG, "action down :" + actionDownPoint);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (touchingPoint.x - event.getX());
                int dy = (int) (touchingPoint.y - event.getY());
//                Log.e(TAG, "move: " + dx + ", " + dy);
                if (Math.abs(event.getX() - actionDownPoint.x) > 20 || Math.abs(event.getY() - actionDownPoint.y) > 20) {
                    translated = true;
                }
                if (translated) {
                    touchingPoint.x = event.getX();
                    touchingPoint.y = event.getY();
                    scrollBy(dx, dy);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!translated) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    fillColor(x, y);
//                    Log.e(TAG, "fill color up");
                } else {
//                    Log.e(TAG, "translate up.");
                    correctScroll();
                }
                translated = false;
                break;
        }
        return true;
    }

    private PointF colorPickerPoint = new PointF();

    private boolean pickColorTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                pickColor(event);
                break;
            case MotionEvent.ACTION_UP:
                isLongPress = false;
                break;
        }
        invalidate();
        return true;
    }

    private void pickColor(MotionEvent event) {
        colorPickerPoint.set(event.getX(), event.getY());
        Point point = getPointForBmp(event.getX(), event.getY());
        if (point == null) {
            return;
        }
        if (point.x < 0 || point.x >= coverBmp.getWidth()) {
            return;
        }
        if (point.y < 0 || point.y >= coverBmp.getHeight()) {
            return;
        }
        targetColor = coverBmp.getPixel(point.x, point.y);
    }

    public boolean isEditing() {
        return actionState == ACTION_EDITING;
    }

    private void correctScroll() {
        if (getDrawable() == null) {
            return;
        }
        RectF rectF = getImageBorder();
//        Log.e(TAG, "matrix:" + (int) left + ", " + (int) top + " - " + (int) right + ", " + (int) bottom);
//        Log.e(TAG, "view  :" + getLeft() + ", " + getTop() + " - " + getRight() + ", " + getBottom() + ",   \t scrollX:" + getScrollX() + ", scrollY:" + getScrollY());
        int x = getScrollX();
        int y = getScrollY();
        if (getWidth() >= rectF.right - rectF.left) {
            x = (int) (rectF.left - ((getWidth() - rectF.right + rectF.left) / 2));
        }
        if (getHeight() >= rectF.bottom - rectF.top) {
            y = (int) (rectF.top /*- ((getHeight() - height) / 2)*/);
        }
        scrollTo(x, y);
//        Log.e(TAG, "view  :" + getLeft() + ", " + getTop() + " - " + getRight() + ", " + getBottom() + ",   \t scrollX:" + x + ", scrollY:" + y);
    }


    @Override
    public void scrollBy(@Px int x, @Px int y) {
        if (getDrawable() == null) {
            super.scrollBy(x, y);
            return;
        }
        RectF rectF = getImageBorder();
//        Log.e(TAG, "matrix:" + (int) left + ", " + (int) top + " - " + (int) right + ", " + (int) bottom);
//        Log.e(TAG, "view  :" + getLeft() + ", " + getTop() + " - " + getRight() + ", " + getBottom() + ",   \t scrollX:" + getScrollX() + ", scrollY:" + getScrollY());
        if (rectF.right - rectF.left >= getRight() - getLeft()) {
            if (getScrollX() + getLeft() + x < rectF.left - topScalePadding) {
                x = (int) (rectF.left - getLeft() - getScrollX()) - topScalePadding;
            }
            if (getScrollX() + getRight() + x > rectF.right + topScalePadding) {
                x = (int) (rectF.right - getScrollX() - getRight()) + topScalePadding;
            }
        }
        if (rectF.bottom - rectF.top >= getBottom() - getTop()) {
            if (getScrollY() + y < rectF.top - topScalePadding) {
                y = (int) (rectF.top - getScrollY()) - topScalePadding;
            } else if (getScrollY() - getTop() + getBottom() + y > rectF.bottom + bottomScalePadding) {
                y = (int) (rectF.bottom - getScrollY() - getBottom() + getTop()) + bottomScalePadding;
            }
        }
//        Log.e(TAG, "scroll by x:" + x + ", y:" + y);
        super.scrollBy(x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDrawable == null) {
            return; // couldn't resolve the URI
        }

        if (mDrawableWidth == 0 || mDrawableHeight == 0) {
            return;     // nothing to draw (empty bounds)
        }

        RectF rectF = getImageBorder();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectF, borderPaint);
        drawImageBackground(canvas, rectF);
        canvas.drawBitmap(coverBmp, imageMatrix, imagePaint);
        drawLongPressCircle(canvas);
    }

    private void drawLongPressCircle(Canvas canvas) {
        if (isLongPress) {
            borderPaint.setColor(targetColor);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(colorPickerStrokeWidth);
            canvas.drawCircle(colorPickerPoint.x + getScrollX(), colorPickerPoint.y + getScrollY(), colorPickerRadius, borderPaint);
        }
    }

    private void drawImageBackground(Canvas canvas, RectF rectF) {
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStyle(Paint.Style.FILL);
        tempImageRect.set(getScrollX(), getScrollY(), rectF.left, getBottom() + getScrollY());
        canvas.drawRect(tempImageRect, borderPaint);
        tempImageRect.set(getScrollX(), getScrollY(), getRight() + getScrollX(), rectF.top);
        canvas.drawRect(tempImageRect, borderPaint);
        tempImageRect.set(rectF.right, getScrollY(), getRight() + getScrollX(), getBottom() + getScrollY());
        canvas.drawRect(tempImageRect, borderPaint);
        tempImageRect.set(getScrollX(), rectF.bottom, getRight() + getScrollX(), getBottom() + getScrollY());
        canvas.drawRect(tempImageRect, borderPaint);
    }

    @NonNull
    private RectF getImageBorder() {
        Matrix matrix = getImageMatrix();
        Rect rect = getDrawable().getBounds();
        matrix.getValues(tempImageMatrixFloat);
        RectF rectF = tempImageBorderRect;
        rectF.left = tempImageMatrixFloat[2];
        rectF.top = tempImageMatrixFloat[5];
        rectF.right = rectF.left + rect.width() * tempImageMatrixFloat[0];
        rectF.bottom = rectF.top + rect.height() * tempImageMatrixFloat[0];
        return rectF;
    }

    public void backForward() {
        if (backStack.isEmpty()) {
            return;
        }
        Action action = backStack.pop();
        if (action != null) {
            fillArea(action.x, action.y, action.toPixel, action.fromPixel);
            stepStack.push(action);
        }
    }

    public void stepForward() {
        if (stepStack.isEmpty()) {
            return;
        }
        Action action = stepStack.pop();
        if (action != null) {
            fillArea(action.x, action.y, action.fromPixel, action.toPixel);
            backStack.push(action);
        }
    }

    private void fillColor(int x, int y) {
        Point point = getPointForBmp(x, y);
        if (point == null) return;
        int w = coverBmp.getWidth();
//        int h = coverBmp.getHeight();
        int targetColor = this.targetColor;
        int selectPixel = pixelsManager.getPixel(point.x, point.y, w);
        if (Color.alpha(selectPixel) == 0) {
            return;
        }
        if (stepStack.size() > 0) {
            stepStack.clear();
        }
        MethodTimePrinter.start();
        if (pixelsManager.isRgbEqual(selectPixel, targetColor)) {
            if (pixelsManager.isRgbEqual(selectPixel, Color.WHITE)) {
                return;
            }
            targetColor = Color.WHITE;
        }
        fillArea(point.x, point.y, selectPixel, targetColor);
        MethodTimePrinter.end("fillArea");
        Action action = new Action(point.x, point.y);
        action.fromPixel = selectPixel;
        action.toPixel = targetColor;
        backStack.push(action);
        if (onTapChangeColor != null) {
            onTapChangeColor.paint(targetColor);
        }
    }

    @Nullable
    private Point getPointForBmp(double x, double y) {
        if (getDrawable() == null) {
            return null;
        }
        RectF rectF = getImageBorder();
        x += getScrollX();
        y += getScrollY();
        if (x < rectF.left) {
            x = rectF.left;
        }
        if (x > rectF.right) {
            x = rectF.right;
        }
        if (y < rectF.top) {
            y = rectF.top;
        }
        if (y > rectF.bottom) {
            y = rectF.bottom;
        }
//        if (x < left || x > right || y < top || y > bottom) {
//            return null;
//        }
        Point point = new Point((int) ((x - rectF.left) / tempImageMatrixFloat[0]), (int) ((y - rectF.top) / tempImageMatrixFloat[0]));
        if (point.x >= pixelsManager.getWidth()) {
            point.x = pixelsManager.getWidth() - 1;
        }
        int i = point.y * pixelsManager.getWidth() + point.x;
        if (i < 0 || i >= pixelsManager.getPixels().length) {
            return null;
        }
        return point;
    }

    private void fillArea(int x, int y, int selectPixel, int targetColor) {
        pixelsManager.fillArea(x, y, coverBmp.getWidth(), coverBmp.getHeight(), selectPixel, targetColor);
        pixelsManager.fillBitmap(coverBmp);
        invalidate();
        actionState = ACTION_EDITING;
//        scrollBy(0,0);
    }

    private void scaleBitmap() {
//        Log.e(TAG, "focusingX:" + focusingX + " , focusingY:" + focusingY + ", mScaleFactor" + mScaleFactor);
        getImageMatrix().setScale(mScaleFactor, mScaleFactor, focusingX, focusingY);
        invalidate();
    }

    public void restore() {
        Log.e(TAG, "restore");
        if (pixelsManager == null) {
            return;
        }
        pixelsManager.restore();
        pixelsManager.fillBitmap(coverBmp);
        backStack.clear();
        stepStack.clear();
        invalidate();
    }

    public void setOnTapChangeColor(OnTapChangeColor onTapChangeColor) {
        this.onTapChangeColor = onTapChangeColor;
    }

    public void setImageDrawable(BitmapDrawable imageDrawable) {
        this.mDrawable = imageDrawable;
        mDrawableHeight = imageDrawable.getIntrinsicHeight();
        mDrawableWidth = imageDrawable.getIntrinsicWidth();
        this.mDrawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
    }

    public Matrix getImageMatrix() {
        return imageMatrix;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public interface OnTapChangeColor {
        void paint(int color);
    }

    private class Action {
        int x, y;
        int fromPixel, toPixel;

        Action(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class ScaleListener
            implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            focusingX = detector.getFocusX();
            focusingY = detector.getFocusY();
            Point point = getPointForBmp(focusingX, focusingY);
            if (point != null) {
                focusingX = point.x;
                focusingY = point.y;
            }
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(minScaleFactor, Math.min(mScaleFactor, 10.0f));
            if (detector.isInProgress()) {
                scaleBitmap();
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }
}
