package com.claretiano.estoque.utils;

import java.text.Normalizer;

public class StringUtils {
    public static String normalize(String text) {
        if (text == null) return null;

        String textWithoutAccent = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return textWithoutAccent.toLowerCase().trim();
    }
}
