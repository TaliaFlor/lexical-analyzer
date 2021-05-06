package compiler.component;


import compiler.model.TokenType;
import compiler.model.Type;
import compiler.parser.exception.TokenExpectedException;
import compiler.parser.model.ActualToken;

import java.util.List;

public class ExceptionLauncher {

    private final ActualToken actualToken;


    public ExceptionLauncher() {
        this.actualToken = ActualToken.getInstance();
    }


    // ======= IDENTIFIER =======

    public void identifierExpectedException() {
        throw unique(Type.IDENTIFIER);
    }

    // ======= RESERVED WORDS =======

    public void reservedWordExpectedException() {
        throw anyOption(Type.RESERVED_WORD);
    }

    public void reservedWordExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    public void reservedWordExpectedException(List<TokenType> tokenTypes) {
        throw anyOption(Type.RESERVED_WORD, tokenTypes);
    }

    // ======= VARIABLE VALUES =======

    public void variableValueExpectedException() {
        throw anyOption(Type.VARIABLE_VALUE);
    }

    public void variableValueExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    // ======= SPECIAL CHARACTERS =======

    public void specialCharacterExpectedException() {
        throw anyOption(Type.SPECIAL_CHARACTER);
    }

    public void specialCharacterExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    // ======= ARITHMETIC OPERATORS =======

    public void arithmeticOperatorExpectedException() {
        throw anyOption(Type.ARITHMETIC_OPERATOR);
    }

    public void arithmeticOperatorExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    public void arithmeticOperatorExpectedException(List<TokenType> tokenTypes) {
        throw anyOption(Type.ARITHMETIC_OPERATOR, tokenTypes);
    }

    // ======= RELATIONAL OPERATORS =======

    public void relationalOperatorExpectedException() {
        throw anyOption(Type.RELATIONAL_OPERATOR);
    }

    public void relationalOperatorExpectedException(TokenType tokenType) {
        throw oneOption(tokenType);
    }

    // ======= PERSONALIZED =======

    public TokenExpectedException tokenExpectedException(String msg) {
        return createTokenExpectedException(msg);
    }

    // ======= HELPER METHODS =======

    private TokenExpectedException unique(Type type) {     // Identifier expected!
        return createTokenExpectedException(type.get() + " expected!");
    }

    private TokenExpectedException oneOption(TokenType tokenType) {   // Reserved word 'main' expected!
        return createTokenExpectedException(tokenType.type().get() + " '" + tokenType.get() + "' expected!");
    }

    private TokenExpectedException anyOption(Type type) {   // Reserved word 'int', 'float' or 'char' expected!
        return createTokenExpectedException(type.get() + " " + TokenType.options(type) + " expected!");
    }

    private TokenExpectedException anyOption(Type type, List<TokenType> tokenTypes) {   // Reserved word 'int', 'float' or 'char' expected!
        return createTokenExpectedException(type.get() + " " + TokenType.options(tokenTypes) + " expected!");
    }

    private TokenExpectedException createTokenExpectedException(String msg) {
        return new TokenExpectedException(errorMsg(msg));
    }

    private String errorMsg(String msg) {  // msg + Found ARITHMETIC_OPERATOR_DIVISION --> /. Line 3 and column 4 (3:4).
        return msg + " Found " + actualToken.getToken().toString() + ". " + actualToken.getToken().lineByColumn();
    }

}
