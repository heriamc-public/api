package fr.heriamc.api.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FieldUtils {

    public static <A extends Annotation> List<String> getAnnotatedFields(Class<?> clazz, Class<? extends A> annotation){
        List<String> strings = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            A fieldParameters = declaredField.getAnnotation(annotation);
            if(fieldParameters == null){
                continue;
            }

            strings.add(declaredField.getName());
        }
        return strings;
    }

}
