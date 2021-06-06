package compiler.model;

public enum Type {
    IDENTIFIER("Identifier"),
    RESERVED_WORD("Reserved word"),
    VARIABLE_VALUE("Variable value of type"),
    NON_CONSUMABLE("Non consumable"),
    SPECIAL_CHARACTER("Special character"),
    ARITHMETIC_OPERATOR("Arithmetic operator"),
    RELATIONAL_OPERATOR("Relational operator");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public String get() {
        return type;
    }

}
