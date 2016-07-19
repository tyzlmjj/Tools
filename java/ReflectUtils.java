package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射的基础功能封装
 */
public class ReflectUtils
{
    private ReflectUtils(){}

    /**
     * 反射执行方法
     * @param obj           执行方法的实例
     * @param c             定义方法的类，要调用父类方法就填父类
     * @param methodName    方法名
     * @param params        方法的参数
     * @param <T>           方法的返回类型
     * @return  返回调用方法的返回值，如果调用方法异常就返回null
     */
    public static <T> T invokeMethod(Object obj,Class<?> c,String methodName,Object... params)
    {
        Class<?>[] parameterTypes = new Class[0];

        //获取方法参数的类类型
        if(params.length > 0)
        {
            parameterTypes = new Class[params.length];

            for (int i = 0; i < params.length; i++)
            {
                parameterTypes[i] = TransformationWrapperClasses(params[i].getClass());
            }
        }

        try
        {
            Method m = c.getDeclaredMethod(methodName,parameterTypes);
            m.setAccessible(true);
            return (T)m.invoke(obj,params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 简单实例转换成 {@code Map<String,String>}
     * <p>需要有标准的get方法</p>
     */
    public static Map<String,String> entityToMap(Object object)
    {
        if(object == null)
        {
            return null;
        }
        Map<String, String> map = new HashMap<>();

        Class<?> classType = object.getClass();
        Field[] fields = classType.getDeclaredFields();

        for (Field field : fields)
        {
            String name = field.getName();
            try
            {
                Method method = classType.getMethod("get" + upFirstABC(name));
                map.put(name, String.valueOf(method.invoke(object)));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                //nothing
            }
        }

        return map;
    }


    /**
     * 基础包装类型转换
     */
    private static Class TransformationWrapperClasses(Class<?> c)
    {
        //整形
        if(c == Integer.class) {return int.class;}
        if(c == Short.class) {return short.class;}
        if(c == Byte.class) {return byte.class;}
        if(c == Long.class) {return long.class;}
        //浮点型
        if(c == Float.class) {return float.class;}
        if(c == Double.class) {return double.class;}
        //逻辑型
        if(c == Boolean.class) {return boolean.class;}
        //字符型
        if(c == Character.class) {return char.class;}

        return c;
    }

    /**
     * 首字母变大写
     */
    private static String upFirstABC(String fildeName)
    {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}
