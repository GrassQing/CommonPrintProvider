package cgrass.print.printprovider.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by chen on 2016/8/23.
 */
public class FileUtils {

    //文件存储根目录
    public  String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }
}
