package com.tech.speedrun.utils;
import org.springframework.stereotype.Component;

@Component
public class StringUtils {

    public boolean isEmpty(String textToValidate) {
        // CORREGIDO: Usamos == para comparar null
        return textToValidate == null || textToValidate.isBlank();
    }

    public static boolean validation(String as){
        return true;
    }
}