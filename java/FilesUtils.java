
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

/**
 * {@link java.nio.file.Files Files}进一步封装
 */
public class FilesUtils
{
    private FilesUtils(){}

    /**
     * 获取文件基本信息
     */
    public static BasicFileAttributes getFileInfo(Path path) throws IOException
    {
        return Files.readAttributes(path,BasicFileAttributes.class);
    }

    /**
     * 获取文件基本信息
     */
    public static BasicFileAttributes getFileInfo(String path) throws IOException
    {
        return getFileInfo(Paths.get(path));
    }

    /**
     * 需要系统支持POSIX文件系统
     * 一般只在类Unix系统下有效
     */
    public static PosixFileAttributes getPosixFileInfo(Path path) throws IOException
    {
        return Files.readAttributes(path,PosixFileAttributes.class);
    }


    //---------------中等大小文件save、delete、move、copy操作--------------------------------------------------------------//

    public static Save save(InputStream in)
    {
        return save(readInputStream(in));
    }

    public static Save save(String str)
    {
        return save(str.getBytes());
    }

    public static Save save(byte[] bytes)
    {
        return new Save(bytes);
    }

    public static Append append(String str)
    {
        return append(str.getBytes());
    }

    public static Append append(byte[] bytes)
    {
        return new Append(bytes);
    }

    public static Move move(File file)
    {
        return move(file.toPath());
    }

    public static Move move(String path)
    {
        return move(Paths.get(path));
    }

    public static Move move(Path path)
    {
        return new Move(path);
    }

    public static Copy copy(Path path)
    {
        return new Copy(path);
    }

    public static Copy copy(File file)
    {
        return copy(file.toPath());
    }

    public static Copy copy(String path)
    {
        return copy(Paths.get(path));
    }

    public static boolean delete(File file)
    {
        return delete(file.toPath());
    }

    public static boolean delete(String path)
    {
        Path p = Paths.get(path);
        return delete(p);
    }

    public static boolean delete(Path path)
    {
        try
        {
            return Files.deleteIfExists(path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public static class Append
    {
        byte[] bytes;

        Append(byte[] bytes)
        {
            this.bytes = bytes;
        }

        public boolean to(String path) {
            return to(Paths.get(path));
        }

        public boolean to(File file) {
            return to(file.getPath());
        }

        public boolean to(Path path)
        {
            if(path == null || bytes == null){return false;}

            try
            {
                Files.write(path,bytes,StandardOpenOption.APPEND,StandardOpenOption.CREATE);
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static class Save
    {
        byte[] bytes;

        Save(byte[] bytes)
        {
            this.bytes = bytes;
        }

        public boolean replaceTo(String path) {
            return replaceTo(Paths.get(path));
        }

        public boolean replaceTo(File file) {
            return replaceTo(file.toPath());
        }

        public boolean replaceTo(Path path)
        {

            return saveTo(path,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        }

        public boolean to(String path)
        {
            return to(Paths.get(path));
        }

        public boolean to(File file)
        {
            return to(file.getPath());
        }

        public boolean to(Path path)
        {
            return saveTo(path,StandardOpenOption.WRITE,StandardOpenOption.CREATE_NEW);
        }

        public boolean saveTo(Path path,OpenOption... openOptions)
        {
            if(bytes == null || path == null){return false;}

            try
            {
                Files.write(path,bytes,openOptions);
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }

    }

    public static class Copy extends I
    {

        public Copy(Path from) {
            super(from);
        }

        @Override
        public boolean replaceTo(Path path)
        {
            return copyTo(path,REPLACE);
        }

        @Override
        public boolean to(Path path)
        {
            return copyTo(path,NO_REPLACE);
        }

        public boolean to(OutputStream outputStream)
        {
            if(from == null || outputStream == null){return false;}

            try
            {
                Files.copy(from,outputStream);
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }

        private boolean copyTo(Path path,CopyOption[] copyOptions)
        {
            if(path == null || from == null){return false;}

            try
            {
                Files.copy(from,path,copyOptions);
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static class Move extends I
    {
        public Move(Path from) {
            super(from);
        }

        @Override
        public boolean replaceTo(Path path)
        {
            return moveTo(path,REPLACE);
        }

        @Override
        public boolean to(Path path)
        {
            return moveTo(path,NO_REPLACE);
        }

        protected boolean moveTo(Path path, CopyOption[] copyOptions)
        {
            if(path == null || from == null){return false;}

            try
            {
                Files.move(from,path,copyOptions);
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }

    static abstract class I
    {
        Path from;

        final CopyOption[] NO_REPLACE =
                {StandardCopyOption.COPY_ATTRIBUTES};

        final CopyOption[] REPLACE =
                {StandardCopyOption.COPY_ATTRIBUTES,StandardCopyOption.REPLACE_EXISTING};

        I(Path from){
            this.from = from;
        }

        public boolean replaceTo(String path){
            return replaceTo(Paths.get(path));
        }

        public boolean replaceTo(File file){
            return replaceTo(file.toPath());
        }

        abstract boolean replaceTo(Path path);

        public boolean to(String path){
            return to(Paths.get(path));
        }

        public boolean to(File file){
            return to(file.toPath());
        }

        abstract boolean to(Path path);


    }

    private static byte[] readInputStream(InputStream inputStream)
    {
        try(BufferedInputStream in =new BufferedInputStream(inputStream, 8192);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream())
        {
            byte[] b = new byte[4096];
            int i;
            while((i=in.read(b)) != -1)
            {
                byteOut.write(b,0,i);
            }
            return byteOut.toByteArray();
        }
        catch (IOException e)
        {
            return null;
        }
    }

}
