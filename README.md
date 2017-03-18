# CommonPrintProvider
# grade
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
  dependencies {
	        compile 'com.github.GrassQing:CommonPrintProvider:1.0.0'
	}
  # 接口说明
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
    
   # 使用
      ~~~//初始化
        InitPrint(ESC_SYTLE.MODE_PRINT.WIFI_PRINT, "192.168.0.1","12345");
        //打印数据
        initdata();
        //打印并且切纸
        startPrint(true);
        
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
~~~
    
