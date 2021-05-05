package compiler.parser.validation;

import compiler.model.TokenType;
import compiler.parser.model.ActualToken;

public class ConjuntoFirstValidation extends ParserValidation {


    // ======= COMANDO =======

    public boolean comando() {
        return comando(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean comando(boolean nextToken) {
        updateInfo(nextToken);
        return tokenType == TokenType.IDENTIFIER
                || tokenType == TokenType.IF
                || tokenType == TokenType.INT || tokenType == TokenType.FLOAT || tokenType == TokenType.CHAR
                || tokenType == TokenType.WHILE || tokenType == TokenType.DO || tokenType == TokenType.FOR
                || tokenType == TokenType.CLOSE_CURLY_BRACKET;
    }

    // ======= DECISÃO =======

    public boolean decisao() {
        return decisao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean decisao(boolean nextToken) {
        updateInfo(nextToken);
        return tokenType == TokenType.IF;
    }

    // ======= DECLARAÇÃO =======

    public boolean declaracao() {
        return declaracao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean declaracao(boolean nextToken) {
        updateInfo(nextToken);
        return tokenType == TokenType.IDENTIFIER
                || tokenType == TokenType.INT || tokenType == TokenType.FLOAT || tokenType == TokenType.CHAR;
    }

    // ======= ITERAÇÃO =======

    public boolean iteracao() {
        return iteracao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean iteracao(boolean nextToken) {
        updateInfo(nextToken);
        return tokenType == TokenType.WHILE || tokenType == TokenType.DO || tokenType == TokenType.FOR;
    }

}
