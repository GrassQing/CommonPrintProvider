package cgrass.print.printprovider.attr;

public class ESC_SYTLE {

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

    /**
     * 打印属性
     */
    public enum FORMAT_PRINT {
        SIZE_PRINT, ALIGN_PRINT, BOLD_PRINT, WIDTH_HEIGHT_PRINT
    }

}
