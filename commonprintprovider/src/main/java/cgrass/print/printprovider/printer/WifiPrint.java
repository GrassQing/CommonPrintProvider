package cgrass.print.printprovider.printer;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import cgrass.print.printprovider.attr.ESC_SYTLE;
import cgrass.print.printprovider.attr.PrintErrorCode;
import cgrass.print.printprovider.attr.PrintFormat;
import cgrass.print.printprovider.inter.PrintProviderInterface;
import cgrass.print.printprovider.utils.NetWorkUtils;
import cgrass.print.printprovider.utils.SharePreferenceUtils;

/**
 * WifiPrint
 * wifi调用模式
 *
 * @author cq
 * @create by chen in 2016年8月19日11:43:59
 * @modify,
 * @modifytime
 */
class WifiPrint implements PrintProviderInterface {
    private static int status;
    private String encoding = "GBK";//默认编码
    private SharePreferenceUtils mSharePreference;
    private String configName = "PrintConfig";
    private String formatName = "PrintFormat";
    private String ip;
    private int port;
    private ESC_CMD eCmd;//打印执行命令
    private Context context;

    /**
     * 构造函数
     */
    public WifiPrint(Context context) {
        this.context = context;
    }


    @Override
    public void InitPrint() {
        mSharePreference = new SharePreferenceUtils(context,configName);
        ip = mSharePreference.getString("" + ESC_SYTLE.CONFIG_PRINT.IP_PRINT, "").trim();
        port = Integer.valueOf(mSharePreference.getString("" + ESC_SYTLE.CONFIG_PRINT.PORT_PRINT, "0").trim());
        encoding = mSharePreference.getString("" + ESC_SYTLE.CONFIG_PRINT.ENCODING_PRINT, "GBK").trim();
        // System.out.println(ip + "---" + port + "----" + encoding);
        if (!NetWorkUtils.isIPAddress(ip)) {
            setStatus(PrintErrorCode.IP_ERROR);
            return;
        }
        mSharePreference = null;
        eCmd = new ESC_CMD();//初始化打印命令 cmd
        eCmd.esc_init_print();//初始化打印机
    }

    /**
     * 检测网络状态,wifi模式打印
     *
     * @param
     * @return
     */
    @Override
    public boolean preparePrint() {
        boolean state = NetWorkUtils.isNetworkAvailable(context);
        if (!state) {
            setStatus(PrintErrorCode.NET_ERROR);
        }
        releasePrint();

        return state;
    }

    @Override
    public void endPrint() {
        try {
            if (outputStream != null)
                outputStream.close();
            if (socket != null)
                socket.close();
            socket = null;
        } catch (IOException e) {

        }

    }

    @Override
    public void releasePrint() {
        eCmd.esc_data_clear();
        if (mSharePreference == null) {
            mSharePreference = new SharePreferenceUtils(context,formatName);
        }
        mSharePreference.clear();
        mSharePreference = null;
    }


    public void setPrintTextMode(boolean bold, boolean both, boolean doubleWidth, boolean doubleHeight, boolean underLine) {
        eCmd.esc_print_mode(bold, both, doubleWidth, doubleHeight, underLine);
        //eCmd.esc_char_enlarge(mEnlarge);
    }

    @Override
    public void printText(String content, PrintFormat format) {
        //  System.out.println(format.getKeyList());

        if (format.getKeyList().contains(PrintFormat.FONT)) {
            switch (format.getParameter(PrintFormat.FONT)) {
                case PrintFormat.FONT_SIZE_SMALL:
                    eCmd.esc_font_a();
                    break;
                case PrintFormat.FONT_SIZE_MEDIUM:
                    eCmd.esc_font_b();
                    break;
                case PrintFormat.FONT_SIZE_LARGE:
                    eCmd.esc_font_c();
                    break;
                default:
                  //  eCmd.esc_font_b();
                    break;
            }
        }
        if (format.getKeyList().contains(PrintFormat.WIDTH_HEIGHT)) {
            switch (format.getParameter(PrintFormat.WIDTH_HEIGHT)) {
                case PrintFormat.DOUBLE_WIDTH:
                    eCmd.esc_char_enlarge(ESC_SYTLE.MODE_ENLARGE.WIDTH_DOUBLE);
                    break;
                case PrintFormat.DOUBLE_HEIGHT:
                    eCmd.esc_char_enlarge(ESC_SYTLE.MODE_ENLARGE.HEIGHT_DOUBLE);
                    break;
                case PrintFormat.DOUBLE_WIDTH_HEIGHT:
                    eCmd.esc_char_enlarge(ESC_SYTLE.MODE_ENLARGE.HEIGHT_WIDTH_DOUBLE);
                    break;
                case PrintFormat.NORMAL_WIDTH_HEIGHT:
                    eCmd.esc_char_enlarge(ESC_SYTLE.MODE_ENLARGE.NORMAL);
                    break;
                default:
                    eCmd.esc_char_enlarge(ESC_SYTLE.MODE_ENLARGE.NORMAL);
                    break;
            }
        }
        if (format.getKeyList().contains(PrintFormat.BOLD)) {
            switch (format.getParameter(PrintFormat.BOLD)) {
                case PrintFormat.BOLD_DISABLE:
                    eCmd.esc_char_bold(false);
                    break;
                case PrintFormat.BOLD_ENABLE:
                    eCmd.esc_char_bold(true);
                    break;
                default:
                    eCmd.esc_char_bold(false);
                    break;
            }
        }
        setPrintAlign(format);
        eCmd.esc_text_print(content, encoding);
        // startPrintForPrint(false);
    }


    public void setPrintAlign(PrintFormat format) {
        if (format.getKeyList().contains(PrintFormat.ALIGN)) {
            switch (format.getParameter(PrintFormat.ALIGN)) {
                case PrintFormat.ALIGN_CENTER:
                    eCmd.esc_align_set(ESC_SYTLE.MODE_ALIGN.ALIGN_CENTER);
                    break;
                case PrintFormat.ALIGN_LEFT:
                    eCmd.esc_align_set(ESC_SYTLE.MODE_ALIGN.ALIGN_LEFT);
                    break;
                case PrintFormat.ALIGN_RIGHT:
                    eCmd.esc_align_set(ESC_SYTLE.MODE_ALIGN.ALIGN_RIGHT);
                    break;
            }
        }
    }

    @Override
    public void printBitmap(Bitmap bitmap, PrintFormat format, int widthPix, int heightPix) {
        setPrintAlign(format);
        eCmd.esc_image_print(bitmap, widthPix, heightPix);
        //startPrint(false);
    }

    @Override
    public void pinrtBrandCode(String content, PrintFormat format, int widthPix, int heightPix) {
        setPrintAlign(format);
        
        switch (format.getParameter(PrintFormat.BARCODE)) {
            case PrintFormat.BARCODE_UPC_A:
                eCmd.esc_brandcode_print(content, BarcodeFormat.UPC_A, widthPix, heightPix);
                break;
            case PrintFormat.BARCODE_UPC_E:
                eCmd.esc_brandcode_print(content, BarcodeFormat.UPC_E, widthPix, heightPix);
                break;
            case PrintFormat.BARCODE_JAN13:
                eCmd.esc_brandcode_print(content, BarcodeFormat.EAN_13, widthPix, heightPix);
                break;
            case PrintFormat.BARCODE_JAN8:
                eCmd.esc_brandcode_print(content, BarcodeFormat.EAN_8, widthPix, heightPix);
                break;
            case PrintFormat.BARCODE_CODE39:
                eCmd.esc_brandcode_print(content, BarcodeFormat.CODE_39, widthPix, heightPix);
                break;
            case PrintFormat.BARCODE_ITF:
                eCmd.esc_brandcode_print(content, BarcodeFormat.ITF, widthPix, heightPix);
                break;
            case PrintFormat.BARCODE_CODABAR:
                eCmd.esc_brandcode_print(content, BarcodeFormat.CODABAR, widthPix, heightPix);
                break;
            case PrintFormat.BARCODE_CODE93:
                eCmd.esc_brandcode_print(content, BarcodeFormat.CODE_93, widthPix, heightPix);
                break;

            case PrintFormat.BARCODE_CODE128:
                eCmd.esc_brandcode_print(content, BarcodeFormat.CODE_128, widthPix, heightPix);
                break;

            default:
                eCmd.esc_brandcode_print(content, BarcodeFormat.CODE_128, widthPix, heightPix);
                break;
        }
        //startPrint(false);
    }

    @Override
    public void printQrCode(String content, PrintFormat format, Bitmap logo, int widthPix, int heightPix) {
        setPrintAlign(format);
        eCmd.esc_qrcode_print(context,widthPix, heightPix, content, logo);
        //startPrint(false);
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void feedPaper(int rownum, int num) {
        eCmd.esc_feed_paper_row(rownum);
        eCmd.esc_feed_paper(num);
        // startPrint(false);
    }

    @Override
    public void cutPaper() {
        eCmd.esc_cut_paper();
    }



    private void setStatus(int status) {
        this.status = status;
    }


    @Override
    public void printEnter() {
        eCmd.esc_enter();
    }

    @Override
    public boolean startPrint(boolean cutpaper) {

        try {
            boolean flag = SendMsgCommand(ip, port, cutpaper);
            return flag;
        } catch (IOException e) {
            return false;
        }

    }

    /**
     * 向打印机发送打印指令
     *
     * @param ip
     * @param port
     * @throws UnknownHostException
     * @throws IOException
     */
    Socket socket = null;
    OutputStream outputStream = null;

    private boolean SendMsgCommand(final String ip, final int port, boolean cutpaper)
            throws UnknownHostException, IOException {


        try {

            socket = new Socket(ip, port);
            outputStream = socket.getOutputStream();

            for (int i = 0; i < eCmd.getbList().size(); i++) {
                outputStream.write(eCmd.getbList().get(i));
            }
            if (cutpaper) {
                outputStream.flush();
                outputStream.write(Command.LF);
                outputStream.write(Command.ESC_CUT_PAPER);
            }
            outputStream.flush();
            outputStream.close();
            socket.close();
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            if (socket != null)
                socket.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            if (socket != null)
                socket.close();
            return false;
        }
    }


}
