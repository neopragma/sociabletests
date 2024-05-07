package com.neopragma.sociable.v2;

public class Helpers {
    public static Integer extractNumericField(String text, FieldPosition field) {
        return Integer.valueOf(text
                .substring(field.startPosition(), field.endPosition())
                .replaceAll("[^\\d-]", ""));
    }
}
