package compiler.model;

public enum TokenType {
    TK_NAO_CONSUMIVEL(0),
    TK_NUMBER_INT(1),
    TK_NUMBER_REAL(2),
    TK_IDENTIFICADOR(3),
    TK_OP_RELACIONAL(4),
    TK_OP_ARITMETICO(5),
    TK_CARACTER_ESPECIAL(6),
    TK_PALAVRA_RESERVADA(7);


    private final int value;


    TokenType(int value) {
        this.value = value;
    }


    public int get() {
        return value;
    }

}
