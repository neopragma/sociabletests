package com.neopragma.withmocks.v3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpersTest {
    @ParameterizedTest
    @MethodSource("provideValuesForExtractNumericFieldTests")
    public void it_extracts_numeric_values_from_text(
            int expected, String text, FieldPosition field
    ) {
        assertEquals(expected, Helpers.extractNumericField(text, field));
    }

    private static Stream<Arguments> provideValuesForExtractNumericFieldTests() {
        return Stream.of(
                Arguments.of(42, "   42xxx", new FieldPosition(2,5)),
                Arguments.of(-12, "-12345xxx", new FieldPosition(0,3)),
                Arguments.of(5336, "xxxx   5336xxx", new FieldPosition(5,12))

        );
    }


}
