package com.yt.demo_view.DrawColor.bitmap;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Stack;


class PixelsManager {
    public static final int INT_BORDER_ALPHA = 127;
    private static final String TAG = PixelsManager.class.getSimpleName();
    int[] alphas = new int[256];
    private int width;
    private int height;
    private int[] pixels;
    private Stack<Point> searchStack = new Stack<>();
    private boolean isFilling;
    private Bitmap.Config config;
    private SparseArray<ArrayList<LineRange>> outBorderSparseArray = new SparseArray<>();
    private int outBorderColor = Color.WHITE;

    PixelsManager(final int[] pixels, final int width, final int height, boolean hasEdited, Bitmap.Config config) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.config = config;

        if (!hasEdited) {
            for (int i = 0; i < pixels.length; i++) {
                int pix = pixels[i];
                pixels[i] = Color.argb(255 - Color.alpha(pix), 0, 0, 0);
            }
        }
        scanOutBoarderColor();
        if (!hasEdited) {
            for (int i = 0; i < pixels.length; i++) {
                int pix = pixels[i];
                pixels[i] = Color.argb(Color.alpha(pix), 255, 255, 255);
            }
        }
    }

    private boolean isFinished = false;

    public void finish() {
        isFinished = true;
        pixels = null;
        outBorderSparseArray = null;
        searchStack = null;
    }

    private void scanOutBoarderColor() {
        final int[] pixels = new int[this.pixels.length];
        System.arraycopy(this.pixels, 0, pixels, 0, this.pixels.length);
        new AsyncTask<Void, Void, SparseArray<ArrayList<LineRange>>>() {

            @Override
            protected void onPostExecute(SparseArray<ArrayList<LineRange>> arrayListSparseArray) {
                super.onPostExecute(arrayListSparseArray);
                if (isFinished) {
                    return;
                }
                outBorderSparseArray = arrayListSparseArray;
                Log.e(TAG, "scan out boarder Color . onPostExecute.");
            }

            @Override
            protected SparseArray<ArrayList<LineRange>> doInBackground(Void... params) {
                Stack<Point> searchStack = new Stack<>();
                searchStack.push(new Point(0, 0));
                int selectColor = pixels[0];
                SparseArray<ArrayList<LineRange>> outBorderSparseArray = new SparseArray<>();
                while (!searchStack.isEmpty() && !isFinished) {
                    Point seed = searchStack.pop();
                    LineRange range = fillLine(pixels, seed.x, seed.y, width, selectColor, Color.RED);
                    if (seed.y - 1 >= 0) {
                        pushNewLineSeedsToStack(pixels, seed.y - 1, width, height, selectColor, range, searchStack);
                    }
                    if (seed.y + 1 < height) {
                        pushNewLineSeedsToStack(pixels, seed.y + 1, width, height, selectColor, range, searchStack);
                    }
                    ArrayList<LineRange> lineRanges = outBorderSparseArray.get(seed.y);
                    if (lineRanges == null) {
                        lineRanges = new ArrayList<>();
                        outBorderSparseArray.put(seed.y, lineRanges);
                    }
                    lineRanges.add(range);
                }
                return outBorderSparseArray;
            }
        }.execute();

    }

    void fillBitmap(Bitmap coverBmp) {
        coverBmp.setPixels(pixels, 0, width, 0, 0, width, height);
    }

    void fillArea(int x, int y, int width, int height, int selectPixel, int color) {
        if (isFilling) {
            return;
        }

        if (testAndFillOutBorder(x, y, selectPixel, color)) {
            return;
        }
        isFilling = true;
        searchStack.push(new Point(x, y));
        while (!searchStack.isEmpty()) {
            Point seed = searchStack.pop();
            LineRange range = fillLine(pixels, seed.x, seed.y, width, selectPixel, color);
            if (seed.y - 1 >= 0) {
                pushNewLineSeedsToStack(pixels, seed.y - 1, width, height, selectPixel, range, searchStack);
            }
            if (seed.y + 1 < height) {
                pushNewLineSeedsToStack(pixels, seed.y + 1, width, height, selectPixel, range, searchStack);
            }
        }
//        for (int i = 0; i < alphas.length; i++) {
//            Log.e(TAG, "" + i + ": " + alphas[i]);
//            alphas[i] = 0;
//        }
        isFilling = false;
    }

    private boolean testAndFillOutBorder(int x, int y, int selectPixel, int color) {
        if (y > height) {
            fillOutBorder(color);
            return true;
        }
        ArrayList<LineRange> lineRanges = outBorderSparseArray.get(y);
        if (lineRanges == null) {
            return false;
        }
        for (LineRange lineRange : lineRanges) {
            if (x <= lineRange.right && x >= lineRange.left) {
                fillOutBorder(color);
                return true;
            }
        }
        return false;
    }

    public void fillOutBorder(int targetColor) {
        outBorderColor = targetColor;
        int afterStart = width * height;
        for (int i = afterStart; i < pixels.length; i++) {
            pixels[i] = targetColor;
        }
        int r = Color.red(targetColor) << 16;
        int g = Color.green(targetColor) << 8;
        int b = Color.blue(targetColor);
        int rgb = r | g | b;
        for (int i = 0; i < outBorderSparseArray.size(); i++) {
            ArrayList<LineRange> lineRanges = outBorderSparseArray.get(i);
            if (lineRanges == null) {
                continue;
            }
            int startX = i * width;
            for (LineRange lineRange : lineRanges) {
                for (int i1 = startX + lineRange.left; i1 <= startX + lineRange.right; i1++) {
                    pixels[i1] = Color.alpha(pixels[i1]) << 24 | rgb;
                }
            }
        }
    }

    private boolean needFillPixel(int pixel, int selectPixel) {
//        int a = Color.alpha(pixel);
//        alphas[a]++;
        return Color.alpha(pixel) > INT_BORDER_ALPHA && pixel << 8 == selectPixel;
    }

    boolean isRgbEqual(int selectPixel, int pixel) {
        return pixel << 8 == selectPixel << 8;
    }

    private void pushNewLineSeedsToStack(int[] pixels, int y, int width, int height, int selectPixel, LineRange range, Stack<Point> searchStack) {
        int lineStartX = y * width;
        int beginIndex = lineStartX + range.left;
        int endIndex = lineStartX + range.right;
        boolean hasSeed = false;
        int rx;
        int selectRgb = selectPixel << 8;
        while (endIndex >= beginIndex) {
            if (needFillPixel(pixels[endIndex], selectRgb)) {
                if (!hasSeed) {
                    rx = endIndex % width;
                    searchStack.push(obtainPoint(rx, y));
                    hasSeed = true;
                }
            } else {
                hasSeed = false;
            }
            endIndex--;
        }
    }

    private Point obtainPoint(int rx, int y) {
        return new Point(rx, y);
    }

    private LineRange fillLine(int[] pixels, int x, int y, int width, int selectPixel, int targetColor) {
        LineRange range = new LineRange(x - 1, x + 1);
        int lineStartX = y * width;
        int r = Color.red(targetColor) << 16;
        int g = Color.green(targetColor) << 8;
        int b = Color.blue(targetColor);
        int rgb = r | g | b;
        // 填充当前选中点
        pixels[lineStartX + x] = pixels[lineStartX + x] & 0xFF000000 | r | g | b;
        // 向左
        while (range.left >= 0) {
            int index = lineStartX + range.left;
            if (Color.alpha(pixels[index]) > INT_BORDER_ALPHA) {
                pixels[index] = pixels[index] & 0xFF000000 | rgb;
                range.left--;
            } else {
                break;
            }
        }
        range.left++;
        //向右
        while (range.right < width) {
            int index = lineStartX + range.right;
            if (Color.alpha(pixels[index]) > INT_BORDER_ALPHA) {
                pixels[index] = pixels[index] & 0xFF000000 | rgb;
                range.right++;
            } else {
                break;
            }
        }
        range.right--;
        return range;
    }

    public int getPixel(int x, int y, int w) {
        int index = w * y + x;
        if (index < 0) {
            index = 0;
        }
        if (index > pixels.length) {
            index = pixels.length - 1;
        }
        return pixels[index];
    }

    public void restore() {
        for (int i = 0; i < pixels.length; i++) {
            int pix = pixels[i];
            pixels[i] = Color.argb(Color.alpha(pix), 255, 255, 255);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap.Config getConfig() {
        return config;
    }

    public int[] getPixels() {
        return pixels;
    }

    private class LineRange {
        int left;
        int right;

        LineRange(int x, int x1) {
            left = x;
            right = x1;
        }

        @Override
        public String toString() {
            return "Range(" + left + ", " + right + ")";
        }
    }
}
