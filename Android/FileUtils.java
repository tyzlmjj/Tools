
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

public class FileUtils {

    /**
     * 获取文件大小的语义化字符串
     */
    public static String getFileSizeString(File file){
        if (file.exists() && file.isFile()){
            return getFileSizeString(file.length());
        }
        return "0b";
    }

    /**
     * 获取文件大小的语义字符串
     */
    public static String getFileSizeString(long b){
        if (b < 1024){
            return String.format(Locale.CHINA,"%dB",b);
        } else if (b / 1024 < 1024){
            return String.format(Locale.CHINA,"%.2fKB",b / 1024f);
        } else {
            return String.format(Locale.CHINA,"%.2fMB",b / 1024f / 1024f);
        }
    }

    /**
     * 获取文件类型(用了三种API去获取，再取不到就算了吧)
     */
    public static String getFileType(File file){

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());

        if (TextUtils.isEmpty(mimeType)){
            try {
                InputStream is = new BufferedInputStream(new FileInputStream(file));
                mimeType = URLConnection.guessContentTypeFromStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(mimeType)){
            MimeTypeMap myMime = MimeTypeMap.getSingleton();
            String name = file.getName();
            int index = name.lastIndexOf(".");
            if (index > 0 && name.length() > index) {
                mimeType = myMime.getMimeTypeFromExtension(name.substring(index + 1));
            }
        }

        return mimeType;
    }
}
