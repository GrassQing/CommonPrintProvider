package cgrass.print.printprovider.printer;


import android.content.Context;

import cgrass.print.printprovider.attr.ESC_SYTLE;
import cgrass.print.printprovider.attr.PrintFormat;
import cgrass.print.printprovider.attr.PrintConfig;
import cgrass.print.printprovider.inter.PrintProviderInterface;

/**
 * PrintProviderInterface
 *
 * @author cq
 *         通过工厂类调用 打印功能,在这边调用
 *         create by chen in 2016-8-22 10:13:08
 *         modify,modifytime
 */
public class PrinterProvider {
    PrintConfig config;
    Context context;
    PrintFormat format;

    public PrinterProvider(Context context) {
        this.context = context;
    }

    /**
     * 通过 设定模式调用不同的类来实现接口回调,使用者不用在意内部是如何调用功能的,只需要知道接口的方法实现功能即可
     *
     * @param Mode
     * @return
     */
    public PrintProviderInterface CreatePrint(ESC_SYTLE.MODE_PRINT Mode) {
        PrintProviderInterface print = null;
        if (ESC_SYTLE.MODE_PRINT.LOCAL_PRINT == Mode) {
            format=null;
          //  print = new PosPrinter(context);
          //  print.InitPrint();
          //  print.preparePrint();
        } else if (ESC_SYTLE.MODE_PRINT.WIFI_PRINT == Mode) {
            format=null;
            print = new WifiPrint(context);
        } else if (ESC_SYTLE.MODE_PRINT.NORMAL_PRINT == Mode) {
            format=null;
            print = new WifiPrint(context);

        }
        return print;
    }

    public void setPrinterConfig(String ip, String port, String endcoding) {
        if (config == null) {
            config = new PrintConfig(context);
        }
        config.setParameter(ESC_SYTLE.CONFIG_PRINT.IP_PRINT, ip);
        config.setParameter(ESC_SYTLE.CONFIG_PRINT.PORT_PRINT, port);
        config.setParameter(ESC_SYTLE.CONFIG_PRINT.ENCODING_PRINT, endcoding);

    }

    public String getPrinterConfig(ESC_SYTLE.CONFIG_PRINT key) {
        if (config == null) {
            config = new PrintConfig(context);
        }
        return config.getParameter(key);

    }

    /**
     * 设置打印属性
     *
     * @param key
     * @param value
     */
    public void setParameterFormat(String key, int value) {
        if (format == null) {
            format = new PrintFormat();
        }
        format.setParameter(key, value);
    }

    /**
     * 设置打印属性
     *
     * @param format
     */
    public void setFormat(PrintFormat format) {
        this.format = format;
    }

    public PrintFormat getParameterFormat() {
        if (format == null) {
            return null;
        }
        return format;
    }

    /**
     * 获取打印属性
     *
     * @param key
     * @param
     */
    public int getParameterFormat(String key) {
        if (format == null) {
            format = new PrintFormat();
        }
        return format.getParameter(key);

    }
    public void removeParamererFormat(){
        if (format == null) {
            format = new PrintFormat();
        }
        format.clear();
    }
}
