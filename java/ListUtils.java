
import java.util.HashSet;
import java.util.List;

/**
 * List工具类
 */
public class ListUtils
{
    /**
     * 移除重复项<p>
     * 需要确保equals()方法正确性
     * @param list {@link List}
     */
    public void removeRepeat(List list)
    {
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            for (int j = i+1; j < size; j++)
            {
                if(list.get(i).equals(list.get(j)))
                {
                    list.remove(j);
                    j--;
                    size = list.size();
                }
            }
            size = list.size();
        }
    }

    /**
     * 移除重复项,并且是按Hash排序，所以输出乱序<p>
     * 需要确保equals()方法正确性
     * @param list  {@link List}
     */
    public void removeRepeatByHash(List<Object> list)
    {
        HashSet<Object> hashSet = new HashSet<>(list);
        list.clear();
        list.addAll(hashSet);
    }


}
