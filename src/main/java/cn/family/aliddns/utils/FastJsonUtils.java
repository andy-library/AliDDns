package cn.family.aliddns.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJsonUtils {

    private static final SerializerFeature[] features = {
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullListAsEmpty,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullBooleanAsFalse,
        SerializerFeature.WriteDateUseDateFormat,
        SerializerFeature.WriteNullStringAsEmpty
    };

    /**
     * 对象转JSON字符串
     * @param object
     * @return
     */
    public static String convertObjectToJSON(Object object) {
        return JSONObject.toJSONString(object,features);
    }

    /**
     * 转换JSON字符串为对象
     * @param jsonData
     * @param clazz
     * @return
     */
    public static <T> T convertJsonToObject(String jsonData, Class<T> clazz) {
        return JSONObject.parseObject(jsonData, clazz);
    }

    /**
     * 转换JSON字符串为复杂对象
     * @param jsonData
     * @param typeReference
     * @return
     */
    public static <T> T convertJsonToObject(String jsonData, TypeReference typeReference) {
        return JSONObject.parseObject(jsonData, typeReference.getType());
    }

}
