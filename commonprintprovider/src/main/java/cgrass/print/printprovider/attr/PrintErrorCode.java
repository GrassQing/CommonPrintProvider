package cgrass.print.printprovider.attr;

/**
 * Created by chen on 2016/8/22.
 */
public class PrintErrorCode {
    public static String LOG_TAG = "PrinterDevice";
  //  public static int status;//打印设备状态码,错误码
    public static int IMAGE_WIDTH_ERROR = 100;//图片宽度没设置,或者设置错误
    public static int IMAGE_HEIGHT_ERROR = 101; //Height图片高度没设置,或者设置错误
    //public static int IMAGE_HEIGHT_ERROR = 101; //Height图片高度没设置,或者设置错误
    public static int NET_ERROR = 200; //终端未与打印设备连接,即wifi模式无网络
    public static int IP_ERROR = 201; //ip不合法


}
