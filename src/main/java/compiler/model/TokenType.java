package compiler.model;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public enum TokenType {
    // IDENTIFIER
    IDENTIFIER(Type.IDENTIFIER, "None"),

    // RESERVED WORDS
    MAIN(Type.RESERVED_WORD, "main"),
    INT(Type.RESERVED_WORD, "int"),
    FLOAT(Type.RESERVED_WORD, "float"),
    CHAR(Type.RESERVED_WORD, "char"),
    IF(Type.RESERVED_WORD, "if"),
    ELSE(Type.RESERVED_WORD, "else"),
    WHILE(Type.RESERVED_WORD, "while"),
    DO(Type.RESERVED_WORD, "do"),
    FOR(Type.RESERVED_WORD, "for"),

    // VARIABLE VALUES
    INTEGER_NUMBER(Type.VARIABLE_VALUE, "None"),
    REAL_NUMBER(Type.VARIABLE_VALUE, "None"),
    CHARACTERE(Type.VARIABLE_VALUE, "None"),

    // NON CONSUMABLE
    NON_CONSUMABLE(Type.NON_CONSUMABLE, "None"),

    // SPECIAL CHARACTERES
    COMMA(Type.SPECIAL_CHARACTER, ","),
    SEMICOLON(Type.SPECIAL_CHARACTER, ";"),
    OPEN_PARENTHESIS(Type.SPECIAL_CHARACTER, "("),
    CLOSE_PARENTHESIS(Type.SPECIAL_CHARACTER, ")"),
    OPEN_CURLY_BRACKET(Type.SPECIAL_CHARACTER, "{"),
    CLOSE_CURLY_BRACKET(Type.SPECIAL_CHARACTER, "}"),

    // ARITHMETIC OPERATORS
    ATTRIBUTION(Type.ARITHMETIC_OPERATOR, "="),
    PLUS(Type.ARITHMETIC_OPERATOR, "+"),
    MINUS(Type.ARITHMETIC_OPERATOR, "-"),
    MULTIPLICATION(Type.ARITHMETIC_OPERATOR, "*"),
    DIVISION(Type.ARITHMETIC_OPERATOR, "/"),

    // RELATIONAL OPERATORS
    EQUAL(Type.RELATIONAL_OPERATOR, "=="),
    DIFFERENT(Type.RELATIONAL_OPERATOR, "!="),
    LESS_THAN(Type.RELATIONAL_OPERATOR, "<"),
    GREATER_THAN(Type.RELATIONAL_OPERATOR, ">"),
    LESS_THAN_OR_EQUAL_TO(Type.RELATIONAL_OPERATOR, "<="),
    GREATER_THAN_OR_EQUAL_TO(Type.RELATIONAL_OPERATOR, ">=");


    private final Type type;
    private final String value;

    TokenType(Type type, String value) {
        this.value = value;
        this.type = type;
    }

    public static List<String> list(Type type) {
        return Arrays.stream(TokenType.values())
                .filter(tokenType -> tokenType.type == type)
                .map(TokenType::get)
                .collect(toList());
    }

    public static String options(Type type) {
        List<String> list = list(type);
        return list.stream()
                .limit(list.size() - 1)
                .map(item -> "'" + item + "'")
                .collect(joining(", "))
                .concat(" or '" + list.get(list.size() - 1) + "'");
    }

    public static String options(List<TokenType> tokenTypes) {
        return tokenTypes.stream()
                .limit(tokenTypes.size() - 1)
                .map(TokenType::get)
                .map(item -> "'" + item + "'")
                .collect(joining(", "))
                .concat(" or '" + tokenTypes.get(tokenTypes.size() - 1) + "'");
    }

    public String get() {
        return value;
    }

    public Type type() {
        return type;
    }

}
