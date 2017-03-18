package cgrass.print.printprovider.printer;

class Command {

    public static final byte HT = 0x9;
    public static final byte LF = 0x0A;
    public static final byte CR = 0x0D;
    public static final byte ESC = 0x1B;
    public static final byte DLE = 0x10;
    public static final byte GS = 0x1D;
    public static final byte FS = 0x1C;
    public static final byte STX = 0x02;
    public static final byte US = 0x1F;
    public static final byte CAN = 0x18;
    public static final byte CLR = 0x0C;

    /**
     * n = 1: Transmit printer status n = 2: Transmit offline status n = 3:
     * Transmit error status n = 4: Transmit paper roll sensor status
     */
    public static final byte[] DLE_EOT_n = new byte[]{DLE, 0x04, 0x01};

    /**
     * n = 1: Recover from an error and restart printing from the line where the
     * error occurred n = 2: Recover from an error aft clearing the receive and
     * print buffers
     */
    public static final byte[] DLE_ENQ_n = new byte[]{DLE, 0x05, 0x01};

    /**
     * n = 1 m = 0, 1 1 ≤ t ≤ 8 m Connector pin m = 0: Drawer kick-out connector
     * pin 2. m = 1: Drawer kick-out connector pin 5. pulse ON time is [t × 100
     * ms] and the OFF time is [t × 100 ms].
     */
    public static final byte[] DLE_DC4_n_m_t = new byte[]{DLE, 0x14, 0x01,
            0x00, 0x01};

    public static final byte[] ESC_SELECT_DEF_CHAR = new byte[]{ESC, '%',
            0x00};
    public static final byte[] ESC_CANCEL_DEF_CHAR = new byte[]{ESC, '%',
            0x01};
    // Define user-defined characters
    // ESC & y c1 c2 [x1 d1...d(y ×××× x1)]...[xk d1...d(y ×××× xk)]
    // Select bit-image mode
    // ESC * m nL nH d1...dk

    public static final byte[] ESC_UNDER_LINE_OFF = new byte[]{ESC, '-', 0x00};
    public static final byte[] ESC_UNDER_LINE_ON = new byte[]{ESC, '-', 0x01};

    public static final byte[] ESC_DEFAULT_LINE_SP = new byte[]{ESC, '2'};

    public static final byte[] ESC_ENABLE_PRINTER = new byte[]{ESC, '=', 0x01};
    //初始化
    public static final byte[] ESC_INIT = new byte[]{ESC, '@'};
    //水平制表符
    public static final byte[] ESC_HT_RESET = new byte[]{ESC, 'D',};

    public static final byte[] ESC_EM_OFF = new byte[]{ESC, 'E', 0x00};
    public static final byte[] ESC_EM_ON = new byte[]{ESC, 'E', 0x01};

    public static final byte[] ESC_BLOD_OFF = new byte[]{ESC, 'G', 0x00};
    public static final byte[] ESC_BLOD_ON = new byte[]{ESC, 'G', 0x01};

    public static final byte[] ESC_CHARSET_CHINESS = new byte[]{ESC, 'R', 15};

    public static final byte[] ESC_ALIGN_LEFT = new byte[]{ESC, 'a', 0x00};
    public static final byte[] ESC_ALIGN_CENTER = new byte[]{ESC, 'a', 0x01};
    public static final byte[] ESC_ALIGN_RIGHT = new byte[]{ESC, 'a', 0x02};

    public static final byte[] ESC_PAPER_END_SENSOR_DISABLE_ALL = new byte[]{
            ESC, 'c', '3', 0x00};
    public static final byte[] ESC_PAPER_END_SENSOR_ENABLE_ALL = new byte[]{
            ESC, 'c', '3', 0x0F};
    public static final byte[] ESC_PAPER_END_SENSOR_ENABLE_NEAR = new byte[]{
            ESC, 'c', '3', 0x01};
    public static final byte[] ESC_PAPER_END_SENSOR_ENABLE_ROLL = new byte[]{
            ESC, 'c', '3', 0x04};

    public static final byte[] ESC_STOP_PRINT_SENSOR_DISABLE = new byte[]{
            ESC, 'c', '4', 0x00};
    public static final byte[] ESC_STOP_PRINT_SENSOR_ANABLE = new byte[]{ESC,
            'c', '4', 0x01};

    public static final byte[] ESC_PANEL_BUTTON_DISABLE = new byte[]{ESC,
            'c', '5', 0x00};
    public static final byte[] ESC_PANEL_BUTTON_ENABLE = new byte[]{ESC, 'c',
            '5', 0x01};

    public static final byte[] ESC_UPSIDE_OFF = new byte[]{ESC, '{', 0x00};
    public static final byte[] ESC_UPSIDE_ON = new byte[]{ESC, '{', 0x01};

    public static final byte[] ESC_CUT_PAPER = new byte[]{GS, 'V', 0x00};
    public static final byte[] ESC_CUT_MODE = new byte[]{GS, 'V', 0x00};
    public static final byte[] ESC_GS_NORMAL = new byte[]{GS, '!', 0x00};
    public static final byte[] ESC_GS_HEIGHT_DOUBLE = new byte[]{GS, '!',
            0x01};
    public static final byte[] ESC_GS_WIDTH_DOUBLE = new byte[]{GS, '!', 0x10};
    public static final byte[] ESC_GS_HEIGHT_WIDTH_DOUBLE = new byte[]{GS,
            '!', 0x11};
    // GS IIII n
    // GS a n
    // FS 2 c1 c2 [d1...dk]

    public static final byte[] ESC_TRANSMIT_PAPER_STATUS = new byte[]{GS,
            'r', 0x01};
    public static final byte[] ESC_TRANSMIT_DRAWER_STATUS = new byte[]{GS,
            'r', 0x02};

    public static final byte[] ESC_UNDERLINE_OFF = new byte[]{FS, '-', 0x00};
    public static final byte[] ESC_UNDERLINE_ON = new byte[]{FS, '-', 0x01};
    //进入英文模式
    public static final byte[] ESC_CN_MODE_OFF = new byte[]{FS, '.'};
    //进入汉字模式
    public static final byte[] ESC_CN_MODE = new byte[]{FS, '&'};

    public static final byte[] ESC_CN_SIZE_QUADRUPLE_OFF = new byte[]{FS,
            'W', 0x00};
    public static final byte[] ESC_CN_SIZE_QUADRUPLE_ON = new byte[]{FS, 'W',
            0x01};

    public static final byte[] ESC_OPEN_DRAWER = new byte[]{STX, 'M'};
    public static final byte[] ESC_OPEN_DRAWER_US = new byte[]{US, 'M'};

    public static final byte[] ESC_DRAWER_RATE_9600 = new byte[]{STX, 'B',
            0x00};
    public static final byte[] ESC_DRAWER_RATE_2400 = new byte[]{STX, 'B',
            0x02};

    public static byte[] setPrintMode(boolean fontB, boolean both,
                                      boolean doubleWidth, boolean doubleHeight, boolean underLine) {
        int n = 0;
        if (fontB) {
            n |= 1;
        }
        if (both) {
            n |= 1 << 3;
        }
        if (doubleHeight) {
            n |= 1 << 4;
        }
        if (doubleWidth) {
            n |= 1 << 5;
        }
        if (underLine) {
            n |= 1 << 7;
        }
        return new byte[]{ESC, '!', (byte) n};
    }

    public static byte[] setCharSpacing(int n) {
        n = (n > -1 || n < 256 ? n : 0);
        /**
         * Set right-side character spacing 0 ≤ n ≤ 255
         */
        return new byte[]{ESC, ' ', (byte) n};
    }

    public static byte[] setLineSpacing(int n) {
        n = (n > -1 || n < 256 ? n : 24);
        return new byte[]{ESC, '3', (byte) n};
    }

    public static byte[] cancelUserDefineCharacters(int offset) {
        if (offset < 0 || (offset + 31) > 126) {
            return new byte[0];
        }
        return new byte[]{ESC, '?', (byte) (31 + offset)};
    }

    public static byte[] setHT() {
        // TODO
        return new byte[]{ESC, 'D'};
    }

    public static byte[] printAndFeedPaper(int n) {
        n = (n > 255 ? 255 : n);
        n = (n < 0 ? 0 : n);
        return new byte[]{ESC, 'J', (byte) n};
    }

    public static byte[] printAndFeedLines(int n) {
        n = (n > 255 ? 255 : n);
        n = (n < 0 ? 0 : n);
        return new byte[]{ESC, 'd', (byte) n};
    }

    public static byte[] generatePulse(int onTime, int offTime) {
        int t2 = 255 * 2;
        int t5 = 255 * 5;
        offTime = (offTime < onTime ? onTime : offTime);
        offTime = (offTime > t5 ? t5 : offTime);
        int m = (offTime > t2 ? 1 : 0);
        int ot1 = (m == 1 ? onTime / 5 : onTime / 2);
        int ot2 = (m == 1 ? offTime / 5 : offTime / 2);
        return new byte[]{ESC, 'p', (byte) m, (byte) ot1, (byte) ot2};
    }

    public static byte[] selectCharacterCodeTable(int n) {
        return new byte[]{ESC, 't', (byte) n};
    }

    public static byte[] printNvBitImage(int n, int m) {
        return new byte[]{ESC, 'p', (byte) n, (byte) m};
    }

	/*
     * public static byte[] setNvBitImage(BufferedImage[] images) { return null;
	 * }
	 */

    public static byte[] testPrint(int paper, int pattern) {
        paper = (paper == 0 || paper == 1 || paper == 2 || paper == 48
                || paper == 49 || paper == 50) ? paper : 0;
        pattern = (pattern == 1 || pattern == 2 || pattern == 3
                || pattern == 49 || pattern == 50 || pattern == 51) ? pattern
                : 1;
        return new byte[]{ESC, '(', 'A', 0x02, 0x00, (byte) paper,
                (byte) pattern};
    }

    public static byte[] setCutMode(int n) {
        n = n % 256;
        int m = 66;
        return new byte[]{GS, 'V', (byte) m, (byte) n};
    }

    public static byte[] setMultiByteCharMode(boolean doubleWidth,
                                              boolean doubleHeight, boolean underLine) {
        int n = 0;
        if (doubleWidth) {
            n |= 1 << 2;
        }
        if (doubleHeight) {
            n |= 1 << 3;
        }
        if (underLine) {
            n |= 1 << 7;
        }
        return new byte[]{FS, '!', (byte) n};
    }

    public static final byte[] ESC_FONT_A = new byte[]{ESC, 'M', 0x00};
    public static final byte[] ESC_FONT_B = new byte[]{ESC, 'M', 0x01};
    public static final byte[] ESC_FONT_C = new byte[]{ESC, 'M', 0x02};

    public static byte[] getFontA() {
        return new byte[]{ESC, 'M', 0x00};
    }

    public static byte[] getFontB() {
        return new byte[]{ESC, 'M', 0x01};
    }

    public static byte[] getColorDefault() {
        return new byte[]{ESC, 'r', 0x00};
    }

    public static byte[] getColorRed() {
        return new byte[]{ESC, 'r', 0x01};
    }

    public static byte[] setDisplayRate(char n) {
        return new byte[]{STX, 'B', (byte) n};
    }

    public static byte[] sendDisplayData(String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }
        byte[] bytes = data.getBytes();
        int len = bytes.length + 4;
        byte[] bs = new byte[len];
        bs[0] = ESC;
        bs[1] = 'Q';
        bs[2] = 'A';
        bs[len - 1] = CR;
        for (int i = 0; i < bytes.length; i++) {
            bs[i + 3] = bytes[i];
        }
        return bs;
    }

    public static byte[] setDisplayState(char n) {
        return new byte[]{ESC, 's', (byte) n};
    }

    public static byte[] testPrinterDevice() {
        return new byte[]{16, 4, 4};
    }
}