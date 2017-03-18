package cgrass.print.printprovider.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * ImageUtils
 * 图片处理功能,主要功能是进行黑白化
 *
 * @author cq
 * @create by chen in 2016年8月19日11:43:59
 * @modify,
 * @modifytime
 */
public class ImageUtils {
    /**
     * 对图片进行压缩（去除透明度）
     *
     * @param
     */
    private static int errorcode;

    public static Bitmap compressPic(Bitmap bitmap, int newWidth, int newHeight) {
        // 获取这个图片的宽和高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 指定调整后的宽度和高度
        // int newWidth = 240;
        //  int newHeight = 240;
        if (newWidth <= 0) {
            newWidth = 240;
            //setStatus(PrintErrorCode.IMAGE_WIDTH_ERROR);
        }
        if (newHeight <= 0) {
            newHeight = 240;
            //setStatus(PrintErrorCode.IMAGE_HEIGHT_ERROR);
        }
        Bitmap targetBmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(targetBmp);
        targetCanvas.drawColor(0xffffffff);
        targetCanvas.drawBitmap(bitmap, new Rect(0, 0, width, height), new Rect(0, 0, newWidth, newHeight), null);
        return targetBmp;
    }

    /**
     * 灰度图片黑白化，黑色是1，白色是0
     *
     * @param x   横坐标
     * @param y   纵坐标
     * @param bit 位图
     * @return
     */
    public static byte px2Byte(int x, int y, Bitmap bit) {
        if (x < bit.getWidth() && y < bit.getHeight()) {
            byte b;
            int pixel = bit.getPixel(x, y);
            int red = (pixel & 0x00ff0000) >> 16; // 取高两位
            int green = (pixel & 0x0000ff00) >> 8; // 取中两位
            int blue = pixel & 0x000000ff; // 取低两位
            int gray = RGB2Gray(red, green, blue);
            if (gray < 128) {
                b = 1;
            } else {
                b = 0;
            }
            return b;
        }
        return 0;
    }

    /**
     * 图片灰度的转化
     */
    private static int RGB2Gray(int r, int g, int b) {
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b);  //灰度转化公式
        return gray;
    }

   /* *************************************************************************
            * 假设一个240*240的图片，分辨率设为24, 共分10行打印
    * 每一行,是一个 240*24 的点阵, 每一列有24个点,存储在3个byte里面。
            * 每个byte存储8个像素点信息。因为只有黑白两色，所以对应为1的位是黑色，对应为0的位是白色
    **************************************************************************/

    /**
     * 把一张Bitmap图片转化为打印机可以打印的字节流
     *
     * @param bmp
     * @return
     */
    public static byte[] draw2PxPoint(Bitmap bmp) {
        //用来存储转换后的 bitmap 数据。为什么要再加1000，这是为了应对当图片高度无法
        //整除24时的情况。比如bitmap 分辨率为 240 * 250，占用 7500 byte，5:5455,3,5447,4,5427
        //但是实际上要存储11行数据，每一行需要 24 * 240 / 8 =720byte 的空间。再加上一些指令存储的开销，
        //所以多申请 1000byte 的空间是稳妥的，不然运行时会抛出数组访问越界的异常。
        int size = bmp.getWidth() * bmp.getHeight() / 8 + 1000;
        byte[] data = new byte[size];
        int k = 0;
        //设置行距为0的指令
        data[k++] = 0x1B;
        data[k++] = 0x33;
        data[k++] = 0x00;
        // 逐行打印
        for (int j = 0; j < bmp.getHeight() / 24f; j++) {
            //打印图片的指令
            data[k++] = 0x1B;
            data[k++] = 0x2A;
            data[k++] = 33;
            data[k++] = (byte) (bmp.getWidth() % 256); //nL
            data[k++] = (byte) (bmp.getWidth() / 256); //nH
            //对于每一行，逐列打印
            for (int i = 0; i < bmp.getWidth(); i++) {
                //每一列24个像素点，分为3个字节存储
                for (int m = 0; m < 3; m++) {
                    //每个字节表示8个像素点，0表示白色，1表示黑色
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(i, j * 24 + m * 8 + n, bmp);
                        data[k] += data[k] + b;
                    }

                    k++;
                }
            }
            data[k++] = 10;//换行
        }
        //   long a=System.currentTimeMillis();
        byte[] data1 = new byte[k];
        System.arraycopy(data, 0, data1, 0, k);
        // long b=System.currentTimeMillis();
        //  System.out.println("结束字节:"+k+"---"+data.length+"耗时:"+(b-a));
        return data1;
    }

    public int getStatus() {
        return errorcode;
    }

    private void setStatus(int errorcode) {
        this.errorcode = errorcode;
    }
}
