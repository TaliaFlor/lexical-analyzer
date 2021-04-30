package compiler.component;


import compiler.model.Token;
import compiler.parser.exception.TokenExpectedException;
import compiler.util.Util;

public class ExceptionHandler {

    private final Util util = new Util();


    // === TokenExpectedException

    public void throwIdentifierExpectedException(Token token) {   // Identifier expected!
        throw tokenExpectedException("Identifier expected!", token);
    }

    public void throwIntegerExpectedException(Token token) {
        throw tokenExpectedException("Integer expected!", token);
    }

    public void throwReservedWordExpectedException(String reservedWord, Token token) {   // Reserved word 'main' expected!
        throw tokenExpectedException("Reserved word '" + reservedWord + "' expected!", token);
    }

    public void throwReservedWordExpectedException(String[] reservedWords, Token token) {   // Reserved word 'int', 'float' or 'char' expected!
        throw tokenExpectedException("Reserved words " + util.stringfy(reservedWords) + " expected!", token);
    }

    public void throwVariableValueExpectedException(String[] variableValues, Token token) {   // Reserved word 'int', 'float' or 'char' expected!
        throw tokenExpectedException("Variable values types " + util.stringfy(variableValues) + " expected!", token);
    }

    public void throwSpecialCharacterExpectedException(char specialCharacter, Token token) {
        throw tokenExpectedException("Special character '" + specialCharacter + "' expected!", token);
    }

    public void throwArithmeticOperatorExpectedException(char arithmeticOperator, Token token) {
        throw tokenExpectedException("Arithmetic operator '" + arithmeticOperator + "' expected!", token);
    }

    public void throwArithmeticOperatorExpectedException(String[] arithmeticOperators, Token token) {
        throw tokenExpectedException("Arithmetic operators " + util.stringfy(arithmeticOperators) + " expected!", token);
    }

    public void throwRelationalOperatorExpectedException(String relationalOperator, Token token) {
        throw tokenExpectedException("Relational operator '" + relationalOperator + "' expected!", token);
    }

    public void throwRelationalOperatorExpectedException(String[] relationalOperators, Token token) {   // Reserved word 'int', 'float' or 'char' expected!
        throw tokenExpectedException("Relational operators " + util.stringfy(relationalOperators) + " expected!", token);
    }


    // ======= HELPER METHODS =======

    private TokenExpectedException tokenExpectedException(String msg, Token token) {
        return new TokenExpectedException(errorMsg(token, msg));
    }

    private String errorMsg(Token token, String msg) {  // msg + Found ARITHMETIC_OPERATOR_DIVISION --> /. Line 3 and column 4 (3:4).
        return msg + " Found " + token.toString() + ". " + token.lineByColumn();
    }

}
