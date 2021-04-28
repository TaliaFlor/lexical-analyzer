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

    public void declVarAux() {
        declVarAux(NEXT_TOKEN_FLAG);
    }

    public void declVarAux(boolean nextToken) {
        verifyToken(nextToken);
        if (!conjFirstForDeclVarAux(false))
            exceptionHandler.throwIdentifierExpectedException(token);
    }

    public void semicolon() {
        semicolon(NEXT_TOKEN_FLAG);
    }

    public void semicolon(boolean nextToken) {
        verifyToken(nextToken);
        if (token.getType() != TokenType.SPECIAL_CHARACTER_SEMICOLON)
            exceptionHandler.throwSpecialCharacterExpectedException(';', token);
    }

    public void openParentesis() {
        openParentesis(NEXT_TOKEN_FLAG);
    }

    public void openParentesis(boolean nextToken) {
        verifyToken(nextToken);
        if (token.getType() != TokenType.SPECIAL_CHARACTER_OPEN_PARENTHESIS)
            exceptionHandler.throwSpecialCharacterExpectedException('(', token);
    }

    public void closeParentesis() {
        closeParentesis(NEXT_TOKEN_FLAG);
    }

    public void closeParentesis(boolean nextToken) {
        verifyToken(nextToken);
        if (token.getType() != TokenType.SPECIAL_CHARACTER_CLOSE_PARENTHESIS)
            exceptionHandler.throwSpecialCharacterExpectedException(')', token);
    }

    public void openCurlyBracket() {
        openCurlyBracket(NEXT_TOKEN_FLAG);
    }

    public void openCurlyBracket(boolean nextToken) {
        verifyToken(nextToken);
        if (token.getType() != TokenType.SPECIAL_CHARACTER_OPEN_CURLY_BRACKET)
            exceptionHandler.throwSpecialCharacterExpectedException('{', token);
    }

    public void closeCurlyBracket() {
        closeCurlyBracket(NEXT_TOKEN_FLAG);
    }

    public void closeCurlyBracket(boolean nextToken) {
        verifyToken(nextToken);
        if (token.getType() != TokenType.SPECIAL_CHARACTER_CLOSE_CURLY_BRACKET)
            exceptionHandler.throwSpecialCharacterExpectedException('}', token);
    }

    public void tipo() {
        tipo(NEXT_TOKEN_FLAG);
    }

    public void tipo(boolean nextToken) {
        verifyToken(nextToken);
        if (token.getType() != TokenType.RESERVED_WORD_INT && token.getType() != TokenType.RESERVED_WORD_FLOAT
                && token.getType() != TokenType.RESERVED_WORD_CHAR)
            exceptionHandler.throwReservedWordExpectedException(new String[]{"int", "float", "char"}, token);
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

    public boolean conjFirstForDeclVar() {
        return conjFirstForDeclVar(NEXT_TOKEN_FLAG);
    }

    public boolean conjFirstForDeclVar(boolean nextToken) {
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

    public boolean conjFirstForDeclVarAux() {
        return conjFirstForDeclVarAux(NEXT_TOKEN_FLAG);
    }

    public boolean conjFirstForDeclVarAux(boolean nextToken) {
        tokenType(nextToken);
        return tokenType == TokenType.IDENTIFIER;
    }

    // ======= HELPER METHODS =======

    private void verifyToken(boolean nextToken) {
        if (nextToken)
            token = scanner.nextToken();
    }

    public void tokenType(boolean nextToken) {
        verifyToken(nextToken);
        tokenType = token.getType();
    }

}
