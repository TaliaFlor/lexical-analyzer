package compiler.model;

public enum TokenType {

    // NON CONSUMABLE
    NON_CONSUMABLE("None"),

    // VARIABLE VALUES
    CHARACTERE("None"),
    INTEGER_NUMBER("None"),
    REAL_NUMBER("None"),

    // IDENTIFIER
    IDENTIFIER("None"),

    // RESERVED WORDS
    INT("int"),
    FLOAT("float"),
    CHAR("char"),
    MAIN("main"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    DO("do"),
    FOR("for"),

    // ARITHMETIC OPERATORS
    ATTRIBUTION("="),
    PLUS("+"),
    MINUS("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),

    // RELATIONAL OPERATORS
    EQUAL("=="),
    DIFFERENT("!="),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_THAN_OR_EQUAL_TO("<="),
    GREATER_THAN_OR_EQUAL_TO(">="),

    // SPECIAL CHARACTERES
    COMMA(","),
    SEMICOLON(";"),
    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    OPEN_CURLY_BRACKET("{"),
    CLOSE_CURLY_BRACKET("}");


    private final String value;

    TokenType(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

}
