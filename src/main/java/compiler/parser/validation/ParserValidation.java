package compiler.parser.validation;

import compiler.component.ExceptionHandler;
import compiler.component.ExceptionHandler2;
import compiler.interfaces.Validation;
import compiler.model.Token;
import compiler.model.TokenType;
import compiler.model.Type;
import compiler.parser.model.ActualToken;
import compiler.scanner.Scanner;

public class ParserValidation implements Validation {

    private static final boolean NEXT_TOKEN_FLAG_TRUE = true;

    private final ExceptionHandler exceptionHandler;
    protected final ExceptionHandler2 exceptionHandler2;
    private final Scanner scanner;                          // Análisador léxico
    protected final ActualToken actualToken;

    protected Token token;                                    // Token atual
    protected Type type;
    protected TokenType tokenType;


    public ParserValidation(Scanner scanner) {
        this.scanner = scanner;
        exceptionHandler = new ExceptionHandler();
        exceptionHandler2 = new ExceptionHandler2();
        actualToken = ActualToken.getInstance();
    }


    // ======= NOVOS =======

    @Override
    public void identifier(boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != TokenType.IDENTIFIER)
            exceptionHandler2.throwIdentifierExpectedException();
    }

    @Override
    public void reservedWord(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler2.throwReservedWordExpectedException(tokenType);
    }

    public void variableValues(boolean nextToken) {
        updateInfo(nextToken);
        if (type != Type.VARIABLE_VALUE)
            exceptionHandler2.throwVariableValueExpectedException();
    }

    @Override
    public void variableValue(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler2.throwVariableValueExpectedException(tokenType);
    }

    @Override
    public void specialCharacter(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler2.throwSpecialCharacterExpectedException(tokenType);
    }

    public void arithmeticOperators(boolean nextToken) {
        updateInfo(nextToken);
        if (type != Type.ARITHMETIC_OPERATOR)
            exceptionHandler2.throwArithmeticOperatorExpectedException();
    }

    @Override
    public void arithmeticOperator(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler2.throwArithmeticOperatorExpectedException(tokenType);
    }

    @Override
    public void relationalOperator(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler2.throwRelationalOperatorExpectedException(tokenType);
    }

    // ======= VALIDATIONS =======

    public void main() {
        token = scanner.nextToken();
        if (token.getTokenType() != TokenType.MAIN)
            exceptionHandler.throwReservedWordExpectedException("main", token);
    }


    public void _while() {
        _while(NEXT_TOKEN_FLAG_TRUE);
    }

    public void _while(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.WHILE)
            exceptionHandler.throwReservedWordExpectedException("while", token);
    }

    public void _do() {
        _do(NEXT_TOKEN_FLAG_TRUE);
    }

    public void _do(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.DO)
            exceptionHandler.throwReservedWordExpectedException("do", token);
    }

    public void _for() {
        _for(NEXT_TOKEN_FLAG_TRUE);
    }

    public void _for(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.FOR)
            exceptionHandler.throwReservedWordExpectedException("for", token);
    }

    public void _if() {
        _if(NEXT_TOKEN_FLAG_TRUE);
    }

    public void _if(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.IF)
            exceptionHandler.throwReservedWordExpectedException("if", token);
    }

    public void _else() {
        _else(NEXT_TOKEN_FLAG_TRUE);
    }

    public void _else(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.ELSE)
            exceptionHandler.throwReservedWordExpectedException("else", token);
    }

    public void equals() {
        equals(NEXT_TOKEN_FLAG_TRUE);
    }

    public void equals(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.EQUAL)
            exceptionHandler.throwRelationalOperatorExpectedException("==", token);
    }

    public void attribution() {
        attribution(NEXT_TOKEN_FLAG_TRUE);
    }

    public void attribution(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.ATTRIBUTION)
            exceptionHandler.throwArithmeticOperatorExpectedException('=', token);
    }

    public void semicolon() {
        semicolon(NEXT_TOKEN_FLAG_TRUE);
    }

    public void semicolon(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.SEMICOLON)
            exceptionHandler.throwSpecialCharacterExpectedException(';', token);
    }

    public void openParentesis() {
        openParentesis(NEXT_TOKEN_FLAG_TRUE);
    }

    public void openParentesis(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.OPEN_PARENTHESIS)
            exceptionHandler.throwSpecialCharacterExpectedException('(', token);
    }

    public void closeParentesis() {
        closeParentesis(NEXT_TOKEN_FLAG_TRUE);
    }

    public void closeParentesis(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.CLOSE_PARENTHESIS)
            exceptionHandler.throwSpecialCharacterExpectedException(')', token);
    }

    public void openCurlyBracket() {
        openCurlyBracket(NEXT_TOKEN_FLAG_TRUE);
    }

    public void openCurlyBracket(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.OPEN_CURLY_BRACKET)
            exceptionHandler.throwSpecialCharacterExpectedException('{', token);
    }

    public void closeCurlyBracket() {
        closeCurlyBracket(NEXT_TOKEN_FLAG_TRUE);
    }

    public void closeCurlyBracket(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.CLOSE_CURLY_BRACKET)
            exceptionHandler.throwSpecialCharacterExpectedException('}', token);
    }

    public void integerNumber() {
        integerNumber(NEXT_TOKEN_FLAG_TRUE);
    }

    public void integerNumber(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.INTEGER_NUMBER)
            exceptionHandler.throwIntegerExpectedException(token);
    }

    public void relationalOperator() {
        relationalOperator(NEXT_TOKEN_FLAG_TRUE);
    }

    public void relationalOperator(boolean nextToken) {
        tokenType(nextToken);
        if (type != Type.RELATIONAL_OPERATOR)
            exceptionHandler.throwRelationalOperatorExpectedException(new String[]{"==", "!=", "<", ">", "<=", ">="}, token);
    }

    public void arithmeticOperator() {
        arithmeticOperator(NEXT_TOKEN_FLAG_TRUE);
    }

    public void arithmeticOperator(boolean nextToken) {
        tokenType(nextToken);
        if (type != Type.ARITHMETIC_OPERATOR)
            exceptionHandler.throwArithmeticOperatorExpectedException(new String[]{"+", "-", "*", "/", "="}, token);
    }

    public void sumOrMinus() {
        sumOrMinus(NEXT_TOKEN_FLAG_TRUE);
    }

    public void sumOrMinus(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.PLUS && tokenType != TokenType.MINUS)
            exceptionHandler.throwArithmeticOperatorExpectedException(new String[]{"+", "-"}, token);
    }

    // ======= AUTOMÂTOS =======

    public void tipo() {
        tipo(NEXT_TOKEN_FLAG_TRUE);
    }

    public void tipo(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.INT && tokenType != TokenType.FLOAT && tokenType != TokenType.CHAR)
            exceptionHandler.throwReservedWordExpectedException(new String[]{"int", "float", "char"}, token);
    }

    public void termoAux() {
        termoAux(NEXT_TOKEN_FLAG_TRUE);
    }

    public void termoAux(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.MULTIPLICATION && tokenType != TokenType.DIVISION)
            exceptionHandler.throwArithmeticOperatorExpectedException(new String[]{"*", "/"}, token);
    }

    public void expressaoAritmeticaAux() {
        expressaoAritmeticaAux(NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoAritmeticaAux(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.PLUS && tokenType != TokenType.MINUS)
            exceptionHandler.throwArithmeticOperatorExpectedException(new String[]{"+", "-"}, token);
    }

    // ======= CHECKS =======

    public boolean isWhile() {
        return isWhile(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean isWhile(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.WHILE;
    }

    public boolean isDo() {
        return isDo(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean isDo(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.DO;
    }

    public boolean isFor() {
        return isFor(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean isFor(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.FOR;
    }

    public boolean isSumOrMinus() {
        return isSumOrMinus(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean isSumOrMinus(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.PLUS || tokenType == TokenType.MINUS;
    }

    public boolean isAttribution() {
        return isAttribution(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean isAttribution(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.ATTRIBUTION;
    }

    // ======= CONJUNTOS FIRST =======

    public boolean conjFirstForComando() {
        return conjFirstForComando(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean conjFirstForComando(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.IDENTIFIER || tokenType == TokenType.INT ||
                tokenType == TokenType.FLOAT || tokenType == TokenType.CHAR
                || tokenType == TokenType.WHILE || tokenType == TokenType.DO
                || tokenType == TokenType.FOR || tokenType == TokenType.IF;
    }

    public boolean conjFirstForDeclaracao() {
        return conjFirstForDeclaracao(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean conjFirstForDeclaracao(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.INT || tokenType == TokenType.FLOAT
                || tokenType == TokenType.CHAR;
    }

    public boolean conjFirstForAtribuicao() {
        return conjFirstForAtribuicao(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean conjFirstForAtribuicao(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.IDENTIFIER;
    }

    public boolean conjFirstForIteracao() {
        return conjFirstForIteracao(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean conjFirstForIteracao(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.WHILE || tokenType == TokenType.DO
                || tokenType == TokenType.FOR;
    }

    public boolean conjFirstForDecisao() {
        return conjFirstForDecisao(NEXT_TOKEN_FLAG_TRUE);
    }

    public boolean conjFirstForDecisao(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.IF;
    }

    // ======= HELPER METHODS =======

    public void tokenType(boolean nextToken) {
        getToken(nextToken);
        tokenType = token.getTokenType();
    }

    private void getToken(boolean nextToken) {
        if (nextToken)
            token = scanner.nextToken();
    }

    protected void updateInfo() {
        actualToken.updateToken();
        type = actualToken.getType();
        tokenType = token.getTokenType();
    }

    protected void updateInfo(boolean nextToken) {
        actualToken.updateToken(nextToken);
        type = actualToken.getType();
        tokenType = token.getTokenType();
    }

}
