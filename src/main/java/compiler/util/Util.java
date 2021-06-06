package compiler.util;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public final class Util {

    public String stringfy(String[] array) {
        return Arrays.stream(array)
                .limit(array.length - 1)
                .collect(joining("'", "'", ", "))
                .concat(" or '" + array[array.length - 1] + "'");
    }

}
