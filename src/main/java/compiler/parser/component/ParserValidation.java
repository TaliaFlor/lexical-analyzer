package compiler.parser.component;

import compiler.component.ExceptionHandler;
import compiler.model.Token;
import compiler.model.TokenType;
import compiler.scanner.Scanner;

public class ParserValidation {

    private final ExceptionHandler exceptionHandler;
    private final Scanner scanner;                          // Análisador léxico

    private Token token;                                    // Token atual


    public ParserValidation(Scanner scanner) {
        this.scanner = scanner;
        exceptionHandler = new ExceptionHandler();
    }


    public void main() {
        token = scanner.nextToken();
        if (token.getType() != TokenType.RESERVED_WORD_MAIN)
            exceptionHandler.throwReservedWordExpectedException("main", token);
    }

    public void openParentesis() {
        token = scanner.nextToken();
        if (token.getType() != TokenType.SPECIAL_CHARACTER_OPEN_PARENTHESIS)
            exceptionHandler.throwSpecialCharacterExpectedException('(', token);
    }

    public void closeParentesis() {
        token = scanner.nextToken();
        if (token.getType() != TokenType.SPECIAL_CHARACTER_CLOSE_PARENTHESIS)
            exceptionHandler.throwSpecialCharacterExpectedException(')', token);
    }

    public void openCurlyBracket() {
        token = scanner.nextToken();
        if (token.getType() != TokenType.SPECIAL_CHARACTER_OPEN_CURLY_BRACKET)
            exceptionHandler.throwSpecialCharacterExpectedException('{', token);
    }

    public void closeCurlyBracket() {
        token = scanner.nextToken();
        if (token.getType() != TokenType.SPECIAL_CHARACTER_CLOSE_CURLY_BRACKET)
            exceptionHandler.throwSpecialCharacterExpectedException('}', token);
    }

    public boolean conjuntoFirstComando() {
        token = scanner.nextToken();
        TokenType tokenType = token.getType();
        return tokenType == TokenType.IDENTIFIER || tokenType == TokenType.RESERVED_WORD_INT ||
                tokenType == TokenType.RESERVED_WORD_FLOAT || tokenType == TokenType.RESERVED_WORD_CHAR
                || tokenType == TokenType.RESERVED_WORD_WHILE || tokenType == TokenType.RESERVED_WORD_DO
                || tokenType == TokenType.RESERVED_WORD_FOR || tokenType == TokenType.RESERVED_WORD_IF;
    }


}
