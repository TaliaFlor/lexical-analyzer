package compiler.parser.component;

import compiler.component.ExceptionHandler;
import compiler.model.Token;
import compiler.model.TokenType;
import compiler.scanner.Scanner;

public class ParserValidation {

    private static final boolean NEXT_TOKEN_FLAG = true;

    private final ExceptionHandler exceptionHandler;
    private final Scanner scanner;                          // Análisador léxico

    private Token token;                                    // Token atual
    private TokenType tokenType;                            // Tipo do token atual. Atualizado pontualmente.


    public ParserValidation(Scanner scanner) {
        this.scanner = scanner;
        exceptionHandler = new ExceptionHandler();
    }


    // ======= VALIDATIONS =======

    public void main() {
        token = scanner.nextToken();
        if (token.getType() != TokenType.RESERVED_WORD_MAIN)
            exceptionHandler.throwReservedWordExpectedException("main", token);
    }

    public void identifier() {
        identifier(NEXT_TOKEN_FLAG);
    }

    public void identifier(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.IDENTIFIER)
            exceptionHandler.throwIdentifierExpectedException(token);
    }

    public void equals() {
        equals(NEXT_TOKEN_FLAG);
    }

    public void equals(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.RELATIONAL_OPERATOR_EQUAL)
            exceptionHandler.throwRelationalOperatorExpectedException("==", token);
    }

    public void attribution() {
        attribution(NEXT_TOKEN_FLAG);
    }

    public void attribution(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.ARITHMETIC_OPERATOR_ATTRIBUTION)
            exceptionHandler.throwArithmeticOperatorExpectedException('=', token);
    }

    public void semicolon() {
        semicolon(NEXT_TOKEN_FLAG);
    }

    public void semicolon(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.SPECIAL_CHARACTER_SEMICOLON)
            exceptionHandler.throwSpecialCharacterExpectedException(';', token);
    }

    public void openParentesis() {
        openParentesis(NEXT_TOKEN_FLAG);
    }

    public void openParentesis(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.SPECIAL_CHARACTER_OPEN_PARENTHESIS)
            exceptionHandler.throwSpecialCharacterExpectedException('(', token);
    }

    public void closeParentesis() {
        closeParentesis(NEXT_TOKEN_FLAG);
    }

    public void closeParentesis(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.SPECIAL_CHARACTER_CLOSE_PARENTHESIS)
            exceptionHandler.throwSpecialCharacterExpectedException(')', token);
    }

    public void openCurlyBracket() {
        openCurlyBracket(NEXT_TOKEN_FLAG);
    }

    public void openCurlyBracket(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.SPECIAL_CHARACTER_OPEN_CURLY_BRACKET)
            exceptionHandler.throwSpecialCharacterExpectedException('{', token);
    }

    public void closeCurlyBracket() {
        closeCurlyBracket(NEXT_TOKEN_FLAG);
    }

    public void closeCurlyBracket(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.SPECIAL_CHARACTER_CLOSE_CURLY_BRACKET)
            exceptionHandler.throwSpecialCharacterExpectedException('}', token);
    }

    public void relationalOperator() {
        relationalOperator(NEXT_TOKEN_FLAG);
    }

    public void relationalOperator(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.RELATIONAL_OPERATOR_EQUAL && tokenType != TokenType.RELATIONAL_OPERATOR_DIFFERENT
                && tokenType != TokenType.RELATIONAL_OPERATOR_LESS_THAN && tokenType != TokenType.RELATIONAL_OPERATOR_GREATER_THAN
                && tokenType != TokenType.RELATIONAL_OPERATOR_LESS_THAN_OR_EQUAL_TO && tokenType != TokenType.RELATIONAL_OPERATOR_GREATER_THAN_OR_EQUAL_TO)
            exceptionHandler.throwRelationalOperatorExpectedException(new String[]{"==", "!=", "<", ">", "<=", ">="}, token);
    }

    // ======= AUTOMÂTOS =======

    public void tipo() {
        tipo(NEXT_TOKEN_FLAG);
    }

    public void tipo(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.RESERVED_WORD_INT && tokenType != TokenType.RESERVED_WORD_FLOAT
                && tokenType != TokenType.RESERVED_WORD_CHAR)
            exceptionHandler.throwReservedWordExpectedException(new String[]{"int", "float", "char"}, token);
    }

    public void fator() {
        fator(NEXT_TOKEN_FLAG);
    }

    public void fator(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.RESERVED_WORD_INT && tokenType != TokenType.RESERVED_WORD_FLOAT
                && tokenType != TokenType.RESERVED_WORD_CHAR)
            exceptionHandler.throwReservedWordExpectedException(new String[]{"int", "float", "char"}, token);
    }

    public void termoAux() {
        termoAux(NEXT_TOKEN_FLAG);
    }

    public void termoAux(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.ARITHMETIC_OPERATOR_MULTIPLICATION && tokenType != TokenType.ARITHMETIC_OPERATOR_DIVISION)
            exceptionHandler.throwArithmeticOperatorExpectedException(new char[]{'*', '/'}, token);
    }

    public void expressaoAritmeticaAux() {
        expressaoAritmeticaAux(NEXT_TOKEN_FLAG);
    }

    public void expressaoAritmeticaAux(boolean nextToken) {
        tokenType(nextToken);
        if (tokenType != TokenType.ARITHMETIC_OPERATOR_SUM && tokenType != TokenType.ARITHMETIC_OPERATOR_SUBTRACTION)
            exceptionHandler.throwArithmeticOperatorExpectedException(new char[]{'+', '-'}, token);
    }

    // ======= CONJUNTOS FIRST =======

    public boolean conjFirstForComando() {
        return conjFirstForComando(NEXT_TOKEN_FLAG);
    }

    public boolean conjFirstForComando(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.IDENTIFIER || tokenType == TokenType.RESERVED_WORD_INT ||
                tokenType == TokenType.RESERVED_WORD_FLOAT || tokenType == TokenType.RESERVED_WORD_CHAR
                || tokenType == TokenType.RESERVED_WORD_WHILE || tokenType == TokenType.RESERVED_WORD_DO
                || tokenType == TokenType.RESERVED_WORD_FOR || tokenType == TokenType.RESERVED_WORD_IF;
    }

    public boolean conjFirstForDeclaracao() {
        return conjFirstForDeclaracao(NEXT_TOKEN_FLAG);
    }

    public boolean conjFirstForDeclaracao(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.RESERVED_WORD_INT || tokenType == TokenType.RESERVED_WORD_FLOAT
                || tokenType == TokenType.RESERVED_WORD_CHAR;
    }

    public boolean conjFirstForAtribuicao() {
        return conjFirstForAtribuicao(NEXT_TOKEN_FLAG);
    }

    public boolean conjFirstForAtribuicao(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.IDENTIFIER;
    }

    public boolean conjFirstForIteracao() {
        return conjFirstForIteracao(NEXT_TOKEN_FLAG);
    }

    public boolean conjFirstForIteracao(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.RESERVED_WORD_WHILE || tokenType == TokenType.RESERVED_WORD_DO
                || tokenType == TokenType.RESERVED_WORD_FOR;
    }

    public boolean conjFirstForDecisao() {
        return conjFirstForDecisao(NEXT_TOKEN_FLAG);
    }

    public boolean conjFirstForDecisao(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.RESERVED_WORD_IF;
    }

    // ======= HELPER METHODS =======

    public void tokenType(boolean nextToken) {
        verifyToken(nextToken);
        tokenType = token.getType();
    }

    private void verifyToken(boolean nextToken) {
        if (nextToken)
            token = scanner.nextToken();
    }

}
