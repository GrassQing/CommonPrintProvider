package cgrass.print.printprovider.inter;

import android.graphics.Bitmap;
import cgrass.print.printprovider.attr.PrintFormat;

/**
 * PrintProviderInterface
 *
 * @author cq
 *         打印接口,使用工厂模式,通过接口调用
 *         create by chen in 2016年8月19日11:43:59
 *         modify,modifytime
 */

public interface PrintProviderInterface {
    void InitPrint();//初始化打印
    boolean preparePrint();//准备打印操作,
    void endPrint();//打印结束释放内存等.
    void releasePrint();//释放打印资源
    void printText(String content, PrintFormat format);//打印文本内容

    void printBitmap(Bitmap bitmap, PrintFormat format, int widthPix, int heightPix);//打印图片

    void pinrtBrandCode(String content, PrintFormat format, int widthPix, int heightPix);//打印条形码

    void printQrCode(String content, PrintFormat format, Bitmap logo, int widthPix, int heightPix);//打印二维码

    int getStatus();//获取当前状态码0 - 正常,1 - 打印机忙,2 - 打印机缺纸,3 - 打印机过热,4 - 其他异常

    void feedPaper(int rownum, int num);//走纸,第一个是走纸n行,第二个是走纸的向前走纸的 数目,

    void cutPaper();//裁剪纸张，部分打印机没有这个功能

  //  void checkPrint();//快速打印自检,相关设置信息

    void printEnter();// 打印换行

    boolean startPrint(boolean cutpaper);//开始打印

}
