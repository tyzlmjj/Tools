
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * 字节流工具
 * 对文件操作还可以使用{@link java.nio.file.Files}
 */
public class StreamByteUtil
{
    /**
     * 读入
     */
    public static String read_toString(File file) throws IOException
    {
        return readToString(new FileInputStream(file),null);
    }

    /**
     * 读入
     * @param charset	编码
     */
    public static String read_toString(File file,String charset) throws IOException
    {
        return readToString(new FileInputStream(file),charset);
    }

    /**
     * 读入
     */
    public static String read_toString(InputStream inputStream) throws IOException
    {
        return readToString(inputStream,null);
    }

    /**
     * 读入
     * @param charset	编码
     */
    public static String read_toString(InputStream inputStream,String charset) throws IOException
    {
        return readToString(inputStream,charset);
    }

    /**
     * 读入
     * 注意：读取存在限制，文件最大可以为Integer.MAX_VALUE字节,约2G
     */
    public static byte[] read(InputStream inputStream) throws IOException
    {
        try(BufferedInputStream in =new BufferedInputStream(inputStream, 8192);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream())
        {
            byte[] b = new byte[4096];
            int i = -1;
            while((i=in.read(b)) != -1)
            {
                byteOut.write(b,0,i);
            }
            return byteOut.toByteArray();
        }
        catch (IOException e)
        {
            throw e;
        }
    }

    /**
     * 序列化
     */
    public static <T> boolean writeObject(T object,File file)
    {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(object);
            out.flush();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 反序列化
     * @return 返回序列化对象，如果反序列化失败则返回null
     */
    public static <T> T readObject(File file)
    {
        T object;
        try(ObjectInputStream out = new ObjectInputStream(new FileInputStream(file)))
        {
            object = (T) out.readObject();
            return object;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 序列化,List
     */
    public static <E> boolean writeObjectForList(List<E> list, File file)
    {
        E[] array = (E[]) list.toArray();
        return StreamByteUtil.<E[]>writeObject(array,file);
    }

    /**
     * 反序列化,List
     * @return 返回序列化对象，如果反序列化失败则返回null
     */
    public static <E> List<E> readObjectForList(File file)
    {
        E[] object = StreamByteUtil.<E[]>readObject(file);
        return Arrays.asList(object);
    }

    /**
     * 获取文件大小,不能是目录
     * @return 文件大小，单位(B)字节。如果文件不存在或者不是文件，返回-1
     */
    public static long getFileSize(File file)
    {
        if (file.exists() && file.isFile())
        {
            return file.length();
        }
        else
        {
            return -1;
        }
    }
/**********************私有方法，用作一些处理*********************************/

    /**
     * 防止空指针
     */
    private static String readToString(InputStream inputStream,String charset) throws IOException
    {
        byte[] b = read(inputStream);
        if(b!=null)
        {
            if(charset == null)
            {
                return new String(b);
            }
            else
            {
                return new String(b,charset);
            }
        }
        else
        {
            return "";
        }
    }
}
