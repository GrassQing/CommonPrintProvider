
package cgrass.print.printprovider.attr;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class PrintFormat implements Parcelable {
    public static final String BARCODE = "barcode-type";
    public static final String ALIGN = "align";
    public static final String FONT = "size";
    public static final String BOLD = "bold";
    public static final String WIDTH_HEIGHT = "double-wh";

    public static final int BARCODE_UPC_A = 0;
    public static final int BARCODE_UPC_E = 1;
    public static final int BARCODE_JAN13 = 2;
    public static final int BARCODE_JAN8 = 3;
    public static final int BARCODE_CODE39 = 4;
    public static final int BARCODE_ITF = 5;
    public static final int BARCODE_CODABAR = 6;
    public static final int BARCODE_CODE93 = 7;
    public static final int BARCODE_CODE128 = 8;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int FONT_SIZE_SMALL = 0;
    public static final int FONT_SIZE_MEDIUM = 1;
    public static final int FONT_SIZE_LARGE = 2;
    public static final int NORMAL_WIDTH_HEIGHT = 0;
    public static final int DOUBLE_WIDTH = 1;
    public static final int DOUBLE_HEIGHT = 3;
    public static final int DOUBLE_WIDTH_HEIGHT = 4;
    public static final int BOLD_ENABLE = 0;
    public static final int BOLD_DISABLE = 1;
    private HashMap<String, Integer> mMap = new HashMap();
    public static final Creator<PrintFormat> CREATOR = new Creator() {
        public PrintFormat createFromParcel(Parcel source) {
            PrintFormat format = new PrintFormat();
            format.mMap = source.readHashMap(HashMap.class.getClassLoader());
            return format;
        }

        public PrintFormat[] newArray(int size) {
            return new PrintFormat[size];
        }
    };

    public PrintFormat() {
    }

    public void setParameter(String key, int value) {
        if (key.indexOf(61) == -1 && key.indexOf(59) == -1) {
            this.mMap.put(key, Integer.valueOf(value));
        }
    }

    public int getParameter(String key) {
        return ((Integer) this.mMap.get(key)).intValue();
    }

    public List<String> getKeyList() {
        ArrayList keys = new ArrayList();
        Iterator iter = this.mMap.entrySet().iterator();

        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            keys.add(entry.getKey().toString());
        }

        return keys;
    }

    public void remove(String key) {
        this.mMap.remove(key);
    }

    public void clear() {
        this.mMap.clear();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.mMap);
    }
}
