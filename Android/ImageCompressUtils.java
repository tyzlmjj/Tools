package me.majiajie.photoalbum.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片压缩工具
 */
public class ImageCompressUtils {

    /**
     * 图片压缩
     */
    public static CompressResult imageCompress(String filePath,String newPath,Bitmap.CompressFormat format){
        CompressResult compressResult = new CompressResult();

        int orientation = readPictureDegree(filePath);//获取旋转角度
        Bitmap bm = getSmallBitmap(filePath);
        if(Math.abs(orientation) > 0){
            bm =  rotaingImageView(orientation, bm);//旋转图片
        }

        compressResult.setWidth(bm.getWidth());
        compressResult.setHeight(bm.getHeight());
        compressResult.setFilePath(newPath);

        try {
            FileOutputStream out = new FileOutputStream(new File(newPath));
            compressResult.setSucceed(bm.compress(format, 40, out));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return compressResult;
    }

    /**
     * 根据路径获得图片并压缩返回bitmap
     */
    private static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, 1080, 1920);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final float height = options.outHeight;
        final float width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            inSampleSize = (int) Math.ceil(Math.max(height / reqHeight, width / reqWidth));
        }

        return inSampleSize;
    }

    /**
     * 获取图片角度
     */
    private static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     */
    private static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        // 旋转图片
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 压缩图片的返回结果
     */
    public static class CompressResult{

        private int width;

        private int height;

        private String filePath;

        private boolean succeed = false;

        public boolean isSucceed() {
            return succeed;
        }

        public void setSucceed(boolean succeed) {
            this.succeed = succeed;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }
}
