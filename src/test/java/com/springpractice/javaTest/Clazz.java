package com.springpractice.javaTest;

import com.springpractice.api.HomeController;
import com.springpractice.dtos.BearerTokenDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Clazz {

    private final BearerTokenDto bearerTokenDto = new BearerTokenDto();
    private final HomeController homeController = new HomeController();

    @Test
    void test() throws NoSuchMethodException {
        Class aClass = bearerTokenDto.getClass();
        Class<? extends HomeController> homeClass = homeController.getClass();

//        aClass.getDeclaredConstructor().newInstance();

        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("methodName = " + method.getName());
        }

        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("fieldName = " + field.getName());
        }

        Annotation[] annotations = aClass.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("annotation = " + annotation);
        }

        Annotation[] declaredAnnotations = homeClass.getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            System.out.println("declaredAnnotation = " + declaredAnnotation);
        }
    }
}
