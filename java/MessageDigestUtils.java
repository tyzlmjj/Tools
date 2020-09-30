
import java.security.MessageDigest;

public class MessageDigestUtils {

    private static final char[] HEXDIGITS = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };

    private MessageDigestUtils() {}

    public static String getMD5(byte[] inputByteArray){
        return get(inputByteArray,"MD5");
    }

    public static String getSHA1(byte[] inputByteArray){
        return get(inputByteArray,"SHA-1");
    }

    public static String getSHA256(byte[] inputByteArray){
        return get(inputByteArray,"SHA-256");
    }

    private static String get(byte[] inputByteArray,String algorithm){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        }catch (Exception e){
            return null;
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = HEXDIGITS[b>>> 4 & 0xf];
            resultCharArray[index++] = HEXDIGITS[b& 0xf];
        }
        return new String(resultCharArray);
    }
}
