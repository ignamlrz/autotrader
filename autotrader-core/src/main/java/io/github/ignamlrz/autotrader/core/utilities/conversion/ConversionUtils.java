package io.github.ignamlrz.autotrader.core.utilities.conversion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public final class ConversionUtils {

    /**
     * Basic Object Mapper
     */
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // ...register JDK8 module (for load Optional)
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        // ...not include null fields
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // ...allow to deserialize a JSON array as a Java array
        OBJECT_MAPPER.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    }

    /**
     * Method for write an object to a JSON
     *
     * @param o Object to write as a JSON
     * @return a JSON string if a null is no passed as reference or any error happen
     */
    public static String toJson(Object o) {
        if (o == null) return null;

        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * Method for instantiate a JSON
     *
     * @param json    JSON to instantiate
     * @param toClass Target class
     * @param <T>     Parameterized type
     * @return an instance of target class
     * @throws JsonProcessingException JSON Processing exception
     */
    public static <T> T fromJson(String json, Class<T> toClass) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, toClass);
    }
}
