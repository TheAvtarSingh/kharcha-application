package org.kharcha.kharcha.common.mappers;
import java.util.function.Function;

public class Mappers {
    public static <S, T> T map(S source, Function<S, T> converter) {
        return converter.apply(source);
    }
}
