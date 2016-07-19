package reflect;


import java.util.Map;

public class TestUtils
{
    private TestUtils(){}

    public static void printMap(Map<Object,String> map)
    {
        for (Object key : map.keySet())
        {
            println("key: "+key + " value: " +map.get(key));
        }
    }

    public static void println(String str)
    {
        System.out.println(str);
    }

}
