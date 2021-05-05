package compiler.parser.validation;

import compiler.model.TokenType;
import compiler.parser.model.ActualToken;

import java.util.Arrays;

public class ComposedValidation extends ParserValidation {


    // ======= TIPO =======

    public void tipo() {
        tipo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void tipo(boolean nextToken) {
        updateInfo(nextToken);
        if (tokenType != TokenType.INT && tokenType != TokenType.FLOAT && tokenType != TokenType.CHAR)
            exceptionHandler.throwReservedWordExpectedException(Arrays.asList(TokenType.INT, TokenType.FLOAT, TokenType.CHAR));
    }

    // ======= PLUS OR MINUS =======

    public void plusOrMinus() {
        plusOrMinus(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void plusOrMinus(boolean nextToken) {
        updateInfo(nextToken);
        if (tokenType != TokenType.PLUS && tokenType != TokenType.MINUS)
            exceptionHandler.throwArithmeticOperatorExpectedException(Arrays.asList(TokenType.PLUS, TokenType.MINUS));
    }

    // ======= MULT OR DIV =======

    public void multOrDiv() {
        multOrDiv(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void multOrDiv(boolean nextToken) {
        updateInfo(nextToken);
        if (tokenType != TokenType.MULTIPLICATION && tokenType != TokenType.DIVISION)
            exceptionHandler.throwArithmeticOperatorExpectedException(Arrays.asList(TokenType.MULTIPLICATION, TokenType.DIVISION));
    }

}
