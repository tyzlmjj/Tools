
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具
 */
public class MD5Utils {

    private static final String TAG = "MD5";

    /**
     * 检查文件一致性
     * @param md5           原文件MD5值
     * @param updateFile    文件
     * @return true 文件未修改
     */
    public static boolean checkMD5(String md5, File updateFile) {
        if (TextUtils.isEmpty(md5) || updateFile == null) {
            // MD5 string empty or updateFile null
            return false;
        }
        String calculatedDigest = calculateMD5(updateFile);
        if (calculatedDigest == null) {
            return false;
        }
        return calculatedDigest.equalsIgnoreCase(md5);
    }

    /**
     * 生成文件的MD5
     */
    public static String calculateMD5(File file) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // Exception while getting digest
            return null;
        }

        InputStream is;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // Exception while getting FileInputStream
            return null;
        }

        byte[] buffer = new byte[8192];
        int read;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            // Fill to 32 chars
            output = String.format("%32s", output).replace(' ', '0');
            return output;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // Exception on closing MD5 input stream
            }
        }
    }
}
