# CommonPrintProvider
wifi实测过，指令内容通用，蓝牙类的自要能连连接上打印机，其他打印步奏基本一致。
我的博客地址：http://www.jianshu.com/p/c8a1c7803cfd
# grade
~~~
 allprojects {
 repositories {
	...
       maven { url 'https://jitpack.io' }
   }
}
  

dependencies {
    compile 'com.github.GrassQing:CommonPrintProvider:1.0.0'
}
 ~~~ 
# 接口说明

    void InitPrint();//初始化打印    
    boolean preparePrint();//准备打印操作,
    void endPrint();//打印结束释放内存等.
    void releasePrint();//释放打印资源
    void printText(String content, PrintFormat format);//打印文本内容
    void printBitmap(Bitmap bitmap, PrintFormat format, int widthPix, int heightPix);//打印图片
    void pinrtBrandCode(String content, PrintFormat format, int widthPix, int heightPix);//打印条形码
    void printQrCode(String content, PrintFormat format, Bitmap logo, int widthPix, int heightPix);//打印二维码
    void feedPaper(int rownum, int num);//走纸,第一个是走纸n行,第二个是走纸的向前走纸的 数目,
    void cutPaper();//裁剪纸张，部分打印机没有这个功能
    void printEnter();// 打印换行
    boolean startPrint(boolean cutpaper);//开始打印

# PrintFormat
~~~
//打印格式
 public static final String BARCODE = "barcode-type";
    //文本 对齐方式，字体大小等等
    public static final String ALIGN = "align";
    public static final String FONT = "size";
    public static final String BOLD = "bold";
    public static final String WIDTH_HEIGHT = "double-wh";
    //条形码格式
    public static final int BARCODE_UPC_A = 0;
    public static final int BARCODE_UPC_E = 1;
    public static final int BARCODE_JAN13 = 2;
    public static final int BARCODE_JAN8 = 3;
    public static final int BARCODE_CODE39 = 4;
    public static final int BARCODE_ITF = 5;
    public static final int BARCODE_CODABAR = 6;
    public static final int BARCODE_CODE93 = 7;
    public static final int BARCODE_CODE128 = 8;
    //文字 对齐方式
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;
    //文字 字体大小
    public static final int FONT_SIZE_SMALL = 0;
    public static final int FONT_SIZE_MEDIUM = 1;
    public static final int FONT_SIZE_LARGE = 2;
    public static final int NORMAL_WIDTH_HEIGHT = 0;
    //字体 长宽
    public static final int DOUBLE_WIDTH = 1;
    public static final int DOUBLE_HEIGHT = 3;
    public static final int DOUBLE_WIDTH_HEIGHT = 4;
    public static final int BOLD_ENABLE = 0;
    public static final int BOLD_DISABLE = 1;
    
~~~    
# ESC_SYTLE

    /**
     * 打印对齐方式
     */
    public enum MODE_ALIGN {
        ALIGN_CENTER, ALIGN_LEFT, ALIGN_RIGHT
    }

    /**
     * 打印规格
     */
    public enum MODE_ENLARGE {
        HEIGHT_DOUBLE, HEIGHT_WIDTH_DOUBLE, WIDTH_DOUBLE, NORMAL
    }


    /**
     * 打印模式
     */
    public enum MODE_PRINT {
        LOCAL_PRINT, WIFI_PRINT, NORMAL_PRINT
    }

    /**
     * 打印配置
     */
    public enum CONFIG_PRINT {
        IP_PRINT, PORT_PRINT, ENCODING_PRINT
    }


# 使用
~~~
        
        InitPrint(ESC_SYTLE.MODE_PRINT.WIFI_PRINT, "192.168.0.1","12345");//初始化，传入打印模式,ip,port
        initdata();//打印数据  
        startPrint(true);     //打印并且切纸
	releasePrint();//释放资源
	endPrint();//结束打印
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
    //打印数据示例：
    private void initdata() {
        PrintFormat = new PrintFormat();
        PrintFormat.clear();
        //打印标题花纹
        PrintFormat.setParameter(PrintFormat.ALIGN, PrintFormat.ALIGN_CENTER);
        PrintFormat.setParameter(PrintFormat.BOLD, PrintFormat.BOLD_ENABLE);
        PrintFormat.setParameter(PrintFormat.WIDTH_HEIGHT, PrintFormat.DOUBLE_HEIGHT);
        PrintFormat.setParameter(PrintFormat.FONT, PrintFormat.FONT_SIZE_MEDIUM);
        printText("******************订餐订单******************");
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
~~~
    
