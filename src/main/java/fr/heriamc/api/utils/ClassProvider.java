package fr.heriamc.api.utils;

import java.lang.reflect.ParameterizedType;

public interface ClassProvider<V> {

    default Class<V> getClazz(int number) {
        return (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[number];
    }

}
