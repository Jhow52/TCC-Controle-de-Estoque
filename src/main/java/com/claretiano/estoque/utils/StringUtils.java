package com.claretiano.estoque.utils;

import java.text.Normalizer;

public class StringUtils {
    public static String normalizar(String texto) {
        if (texto == null) return null;

        String textoSemAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return textoSemAcento.toLowerCase().trim();
    }
}
