import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by mjj
 * <p>
 *     Json解析工具，使用GSON, 依赖 compile 'com.google.code.gson:gson:2.8.0'
 * </p>
 */
public class JsonUtils {

    private JsonUtils(){}

    /**
     * 转换成JSON字符串
     */
    public static String toJson(Object src){
         return new Gson().toJson(src);
    }

    /**
     * 将JSON字符串解析为对象
     *
     * @param json      JSON格式字符串
     * @param classOfT  对象类类型
     * @param <T>       对象
     * @return          解析后的对象实例
     * @throws JsonSyntaxException  json格式不正确时可能抛出这个异常
     */
    public static <T> T fromJson(String json,Class<T> classOfT) throws JsonSyntaxException {
        return new Gson().fromJson(json,classOfT);
    }

    /**
     * 将JSON字符串解析为对象
     *
     * @param json      JSON格式字符串
     * @param type      用于包装一些存在泛型的对象类型，比如List:
     *                  {@code new TypeToken<List<String>(){}.getType()}
     * @param <T>       解析后的对象
     * @return          解析后的对象实例
     * @throws JsonSyntaxException  json格式不正确时可能抛出这个异常
     */
    public static <T> T fromJson(String json, Type type) throws JsonSyntaxException {
        return new Gson().fromJson(json,type);
    }

    /**
     * 将JSON字符串解析为{@link List List&lt;Map&lt;String,Object>>}
     * <p>并且整型不会被转换为浮点型</p>
     *
     * @param json  JSON格式字符串
     * @return  {@link List List&lt;Map&lt;String,Object>>}
     */
    public static List<Map<String,Object>> jsonToListMap(String json){
        Type type = new TypeToken<List<TreeMap<String, Object>>>(){}.getType();
        return getMapParser().fromJson(json,type);
    }

    /**
     * 将JSON字符串解析为{@link Map Map&lt;String,Object>}
     * <p>并且整型不会被转换为浮点型</p>
     *
     * @param json  JSON格式字符串
     * @return  {@link Map Map&lt;String,Object>}
     */
    public static Map<String,Object> jsonToMap(String json){
        Type type = new TypeToken<TreeMap<String, Object>>(){}.getType();
        return getMapParser().fromJson(json,type);
    }

    private static Gson MapParser;

    private static Gson getMapParser(){

        if(MapParser == null){
            MapParser = new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<TreeMap<String, Object>>(){}.getType(), new JsonDeserializer<TreeMap<String, Object>>() {
                        @Override
                        public TreeMap<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                            TreeMap<String, Object> treeMap = new TreeMap<>();
                            JsonObject jsonObject = json.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                            for (Map.Entry<String, JsonElement> entry : entrySet) {
                                treeMap.put(entry.getKey(), entry.getValue());
                            }

                            return treeMap;
                        }
                    })
                    .create();
        }

        return MapParser;
    }
}
