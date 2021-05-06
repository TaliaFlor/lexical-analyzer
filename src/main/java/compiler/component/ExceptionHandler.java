package compiler.component;


import compiler.model.TokenType;
import compiler.model.Type;
import compiler.parser.exception.TokenExpectedException;
import compiler.parser.model.ActualToken;

import java.util.List;

public class ExceptionHandler {

    private final ActualToken actualToken;


    public ExceptionHandler() {
        this.actualToken = ActualToken.getInstance();
    }


    // ======= IDENTIFIER =======

    public void throwIdentifierExpectedException() {
        throw unique(Type.IDENTIFIER);
    }

    // ======= RESERVED WORDS =======

    public void throwReservedWordExpectedException() {
        throw anyOption(Type.RESERVED_WORD);
    }

    public void throwReservedWordExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    public void throwReservedWordExpectedException(List<TokenType> tokenTypes) {
        throw anyOption(Type.RESERVED_WORD, tokenTypes);
    }

    // ======= VARIABLE VALUES =======

    public void throwVariableValueExpectedException() {
        throw anyOption(Type.VARIABLE_VALUE);
    }

    public void throwVariableValueExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    // ======= SPECIAL CHARACTERS =======

    public void throwSpecialCharacterExpectedException() {
        throw anyOption(Type.SPECIAL_CHARACTER);
    }

    public void throwSpecialCharacterExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    // ======= ARITHMETIC OPERATORS =======

    public void throwArithmeticOperatorExpectedException() {
        throw anyOption(Type.ARITHMETIC_OPERATOR);
    }

    public void throwArithmeticOperatorExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    public void throwArithmeticOperatorExpectedException(List<TokenType> tokenTypes) {
        throw anyOption(Type.ARITHMETIC_OPERATOR, tokenTypes);
    }

    // ======= RELATIONAL OPERATORS =======

    public void throwRelationalOperatorExpectedException() {
        throw anyOption(Type.RELATIONAL_OPERATOR);
    }

    public void throwRelationalOperatorExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    // ======= PERSONALIZED =======

    public TokenExpectedException throwTokenExpectedException(String msg) {
        return tokenExpectedException(msg);
    }

    // ======= HELPER METHODS =======

    private TokenExpectedException unique(Type type) {     // Identifier expected!
        return tokenExpectedException(type.get() + " expected!");
    }

    private TokenExpectedException oneOption(TokenType tokenType) {   // Reserved word 'main' expected!
        return tokenExpectedException(tokenType.type().get() + " '" + tokenType.get() + "' expected!");
    }

    private TokenExpectedException anyOption(Type type) {   // Reserved word 'int', 'float' or 'char' expected!
        return tokenExpectedException(type.get() + " " + TokenType.options(type) + " expected!");
    }

    private TokenExpectedException anyOption(Type type, List<TokenType> tokenTypes) {   // Reserved word 'int', 'float' or 'char' expected!
        return tokenExpectedException(type.get() + " " + TokenType.options(tokenTypes) + " expected!");
    }

    private TokenExpectedException tokenExpectedException(String msg) {
        return new TokenExpectedException(errorMsg(msg));
    }

    private String errorMsg(String msg) {  // msg + Found ARITHMETIC_OPERATOR_DIVISION --> /. Line 3 and column 4 (3:4).
        return msg + " Found " + actualToken.getToken().toString() + ". " + actualToken.getToken().lineByColumn();
    }

}
