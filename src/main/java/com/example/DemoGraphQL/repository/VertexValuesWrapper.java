package com.example.DemoGraphQL.repository;

import org.apache.tinkerpop.gremlin.structure.T;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VertexValuesWrapper {
    private final Map<String, Object> valueMap;

    private VertexValuesWrapper(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }

    /**
     * Static constructor for fluent like querying
     *
     * @param valueMap The value map of vertex properties
     * @return An instance of {@link VertexValuesWrapper}
     */
    public static VertexValuesWrapper fromValueMap(Map<String, Object> valueMap) {
        return new VertexValuesWrapper(valueMap);
    }

    /**
     * Returns the id of the vertex if tokens were included in the value map
     */
    public Optional<Long> getVertexId() {
        return Optional.ofNullable((Long) valueMap.get(T.id));
    }

    /**
     * Retrieve a value of a particular type from the value map
     *
     * @param propertyKey The key of the property to retrieve
     * @param valueType The type of value being retrieved
     * @param <T> The type of value being retrieved
     * @return The value if the property is present or null
     */
    @SuppressWarnings("unchecked'")
    public <T> T getValue(String propertyKey, Class<T> valueType) {
        Object valueList = valueMap.get(propertyKey);
        if(valueList instanceof List) {
            return valueType.cast(((List) valueList).get(0));
        }
        return null;
    }

    /**
     * Retrieve a string value from the value map for the specified property key
     *
     * @param propertyKey The property to retrieve the string value of
     * @return The string value or null
     */
    public String getStringValue(String propertyKey) {
        return getValue(propertyKey, String.class);
    }

    /**
     * Retrieve an integer value from the value map for the specified property key
     *
     * @param propertyKey The property to retrieve the integer value of
     * @return The integer value or null
     */
    public Integer getIntValue(String propertyKey) {
        return getValue(propertyKey, Integer.class);
    }

    /**
     * Retrieve a long value from the value map for the specified property key
     *
     * @param propertyKey The property to retrieve the long value of
     * @return The long value or null
     */
    public Long getLongValue(String propertyKey) {
        return getValue(propertyKey, Long.class);
    }
    
    /**
     * Retrieve an integer value from the value map for the specified property key
     *
     * @param propertyKey The property to retrieve the integer value of
     * @return The integer value or null
     */
    public Double getDoubleValue(String propertyKey) {
        return getValue(propertyKey, Double.class);
    }
}
