package com.cgrass.printprovider;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cgrass.print.printprovider.attr.ESC_SYTLE;
import cgrass.print.printprovider.attr.PrintFormat;
import cgrass.print.printprovider.inter.PrintProviderInterface;
import cgrass.print.printprovider.printer.PrinterProvider;

public class MainActivity extends AppCompatActivity {
    PrinterProvider print;
    PrintFormat PrintFormat;
    PrintProviderInterface printerdevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        InitPrint(ESC_SYTLE.MODE_PRINT.WIFI_PRINT, "192.168.0.1","12345");
        //打印数据
        initdata();
        //打印并且切纸
        startPrint(true);
    }

    /**
     * 初始化 设置 ip，端口，wifi，打印。。socket通讯
     *
     * @param mode
     * @param ip
     * @param port
     */
    public void InitPrint(ESC_SYTLE.MODE_PRINT mode, String ip, String port) {
        print = null;
        print = new PrinterProvider(this);
        print.setPrinterConfig(ip, port, "GBK");
        printerdevice = print.CreatePrint(mode);
        printerdevice.InitPrint();
        printerdevice.preparePrint();
        PrintFormat = new PrintFormat();
    }

    /**
     * 打印文本
     *
     * @param text
     * @param
     */
    private void printText(String text) {
        print.setFormat(PrintFormat);
        printerdevice.printText(text, print.getParameterFormat());

    }

    /**
     * 打印图片
     *
     * @param bitmap
     */
    private void printBitmap(Bitmap bitmap, int width, int height) {
        print.setFormat(PrintFormat);
        printerdevice.printBitmap(bitmap, print.getParameterFormat(), width, height);
    }

    private void printBrandcode(String content, int width, int height) {
        print.setFormat(PrintFormat);
        printerdevice.pinrtBrandCode(content, print.getParameterFormat(), width, height);
    }

    private void printQrcode(String content, Bitmap bitmap, int width, int height) {
        print.setFormat(PrintFormat);
        printerdevice.printQrCode(content, print.getParameterFormat(), bitmap, width, height);
    }

    private void startPrint(final boolean cutpaper) {
        if (cutpaper) {
            printerdevice.feedPaper(1, 0);
            //printerdevice.cutPaper();
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    printerdevice.startPrint(cutpaper);
                    printerdevice.releasePrint();
                    print.removeParamererFormat();
                }

            }.start();
        }
    }

    private void printEnter(int num) {
        for (int i = 0; i < num; i++) {
            printText("\n");
        }
    }

    private void initdata() {
        PrintFormat = new PrintFormat();
        PrintFormat.clear();
        //打印标题花纹
        PrintFormat.setParameter(PrintFormat.ALIGN, PrintFormat.ALIGN_CENTER);
        PrintFormat.setParameter(PrintFormat.BOLD, PrintFormat.BOLD_ENABLE);
        PrintFormat.setParameter(PrintFormat.WIDTH_HEIGHT, PrintFormat.DOUBLE_HEIGHT);
        PrintFormat.setParameter(PrintFormat.FONT, PrintFormat.FONT_SIZE_MEDIUM);
        printText("******************微信订餐订单******************");
        printEnter(1);
        PrintFormat.setParameter(PrintFormat.BOLD, PrintFormat.BOLD_ENABLE);
        PrintFormat.setParameter(PrintFormat.WIDTH_HEIGHT, PrintFormat.DOUBLE_WIDTH_HEIGHT);
        printText("XX店");
        printEnter(1);
        PrintFormat.setParameter(PrintFormat.ALIGN, PrintFormat.ALIGN_CENTER);
        PrintFormat.setParameter(PrintFormat.BOLD, PrintFormat.BOLD_DISABLE);
        PrintFormat.setParameter(PrintFormat.WIDTH_HEIGHT, PrintFormat.NORMAL_WIDTH_HEIGHT);
        printText("--------------------------------------------------------------");
        printEnter(1);
        PrintFormat.setParameter(PrintFormat.ALIGN, PrintFormat.ALIGN_LEFT);
        PrintFormat.setParameter(PrintFormat.BOLD, PrintFormat.BOLD_DISABLE);
        PrintFormat.setParameter(PrintFormat.WIDTH_HEIGHT, PrintFormat.NORMAL_WIDTH_HEIGHT);
        printText("订单号:32432423");
        printEnter(1);
        printText("桌台号:43");
        printEnter(1);

        PrintFormat.setParameter(PrintFormat.ALIGN, PrintFormat.ALIGN_CENTER);
        PrintFormat.setParameter(PrintFormat.BOLD, PrintFormat.BOLD_DISABLE);
        PrintFormat.setParameter(PrintFormat.WIDTH_HEIGHT, PrintFormat.NORMAL_WIDTH_HEIGHT);
        printText("--------------------------------------------------------------");
        printEnter(2);
        //开始打印 报文详细内容
    }
}
