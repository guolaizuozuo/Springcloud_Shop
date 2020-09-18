package com.abc.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class JsonUtil {

    @Autowired
    private static ObjectMapper objectMapper=new ObjectMapper();

    /*private JsonUtil() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }*/

        /**
         * 对象转json
         * @param object
         * @return
         */
    public static String toJSON(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    /**
     * json转对象
     * @param string
     * @param classType
     * @return
     */
    public static Object fromObject(String string, Class classType){
        try{
            return objectMapper.readValue(string,classType);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> fromList(String string, Class<T> classType){
        try{
            List<T> readValue = objectMapper.readValue(string,new TypeReference<List<T>>(){});


            return readValue;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static  <T> List<T> fromJson(Object value, Class cls, Class<T> t) {
        List<T> result = null;
        try {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(cls, t);
            result = objectMapper.readValue(value.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    //
}
