package cgrass.print.printprovider.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cgrass.print.printprovider.attr.ESC_SYTLE;
import cgrass.print.printprovider.utils.FileUtils;
import cgrass.print.printprovider.utils.ImageUtils;
import cgrass.print.printprovider.utils.QRCodeUtils;


class ESC_CMD {
    ArrayList<byte[]> byteList = new ArrayList<byte[]>();

    public ESC_CMD() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 清空所有数据,初始化数据
     */
    public void esc_data_clear() {
        byteList.clear();
    }

    /**
     * 对齐方式
     *
     * @param modeAlign
     */
    public void esc_align_set(ESC_SYTLE.MODE_ALIGN modeAlign) {

        if (modeAlign == ESC_SYTLE.MODE_ALIGN.ALIGN_CENTER) {
            byteList.add(Command.ESC_ALIGN_CENTER);
        }
        if (modeAlign == ESC_SYTLE.MODE_ALIGN.ALIGN_LEFT) {
            byteList.add(Command.ESC_ALIGN_LEFT);
        }
        if (modeAlign == ESC_SYTLE.MODE_ALIGN.ALIGN_RIGHT) {
            byteList.add(Command.ESC_ALIGN_RIGHT);
        }

    }

    /**
     * 初始化打印机
     */
    public void esc_init_print() {
        byteList.add(Command.ESC_INIT);
    }

    /**
     * 中文模式 打印GBK模式的文本
     *
     * @param text
     */
    public void esc_text_print(String text, String encoding) {

        byteList.add(Command.ESC_CN_MODE);
        try {
            byteList.add(text.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("port " + text + " is invalid, ", e);
        }
        byteList.add(Command.ESC_CN_MODE_OFF);
    }

    /**
     * @param fontB
     * @param both
     * @param doubleWidth
     * @param doubleHeight
     * @param underLine
     */
    public void esc_print_mode(boolean fontB, boolean both,
                               boolean doubleWidth, boolean doubleHeight, boolean underLine) {
        byteList.add(Command.setPrintMode(fontB, both, doubleWidth,
                doubleHeight, underLine));
    }

    public void esc_setMultiByteCharMode(boolean doubleWidth,
                                         boolean doubleHeight, boolean underLine) {
        byteList.add(Command.setMultiByteCharMode(doubleWidth, doubleHeight,
                underLine));
    }

    /**
     * 字体加粗
     *
     * @param bold
     */
    public void esc_char_bold(boolean bold) {
        if (bold) {
            byteList.add(Command.ESC_BLOD_ON);
        } else {
            byteList.add(Command.ESC_BLOD_OFF);
        }

    }

    /**
     * 放大字符， 该命令对除条码识读字符外的所有字符(英文、数字、字符和汉字)有效。
     *
     * @param mEnlarge
     */
    public void esc_char_enlarge(ESC_SYTLE.MODE_ENLARGE mEnlarge) {
        if (mEnlarge == ESC_SYTLE.MODE_ENLARGE.NORMAL) {
            byteList.add(Command.ESC_GS_NORMAL);
        }
        if (mEnlarge == ESC_SYTLE.MODE_ENLARGE.HEIGHT_DOUBLE) {
            byteList.add(Command.ESC_GS_HEIGHT_DOUBLE);
        }
        if (mEnlarge == ESC_SYTLE.MODE_ENLARGE.WIDTH_DOUBLE) {
            byteList.add(Command.ESC_GS_WIDTH_DOUBLE);
        }
        if (mEnlarge == ESC_SYTLE.MODE_ENLARGE.HEIGHT_WIDTH_DOUBLE) {
            byteList.add(Command.ESC_GS_HEIGHT_WIDTH_DOUBLE);
        }
    }

    /**
     * 打印并换行
     */

    public void esc_enter() {
        byteList.add(new byte[]{Command.CR, Command.LF});

    }

    /**
     * 打印并切纸
     */
    public void esc_cut_paper() {
        byteList.add(new byte[]{Command.LF});
        byteList.add(Command.ESC_CUT_PAPER);

    }

    /**
     * 打印并走纸
     *
     * @param n
     */
    public void esc_feed_paper(int n) {
        byteList.add(Command.printAndFeedPaper(n));
    }

    /**
     * 打印并走纸 n行
     *
     * @param
     */
    public void esc_feed_paper_row(int rownum) {
        byteList.add(Command.printAndFeedLines(rownum));
    }

    public ArrayList<byte[]> getbList() {
        return byteList;
    }

    /**
     * 图片打印
     */
    public void esc_image_print(Bitmap bitmap, int newWidth, int newHeight) {
        byteList.add(ImageUtils.draw2PxPoint(ImageUtils.compressPic(bitmap, newWidth, newHeight)));
        bitmap = null;
    }

    /**
     * 二维码打印,原理生成二维码图片,通过图片打印函数进行打印
     */
    public void esc_qrcode_print(Context context, int widthPix, int heightPix, String text, Bitmap logo) {
        FileUtils fileUtils = new FileUtils();
        String path = fileUtils.getFileRoot(context) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";
        boolean flag = QRCodeUtils.createQRImage(text, widthPix, heightPix, logo, path);
        if (flag) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            esc_image_print(bitmap, bitmap.getWidth(), bitmap.getHeight());
            //bitmap = null;
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
        fileUtils = null;
    }

    public void esc_brandcode_print(String content, BarcodeFormat format, int widthPix, int heightPix) {

        try {
            Bitmap bitmap = QRCodeUtils.CreateBrandCode(content, format, widthPix, heightPix);
            esc_image_print(bitmap, bitmap.getWidth(), bitmap.getHeight());
        } catch (WriterException e) {

        }
    }

    public void esc_font_a() {
        byteList.add(Command.ESC_FONT_A);
    }

    public void esc_font_b() {
        byteList.add(Command.ESC_FONT_B);
    }

    public void esc_font_c() {
        byteList.add(Command.ESC_FONT_C);
    }

    public void esc_test() {
        byteList.add(Command.testPrinterDevice());
    }
}
