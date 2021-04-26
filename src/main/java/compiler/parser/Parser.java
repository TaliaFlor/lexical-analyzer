package compiler.parser;

import compiler.component.ExceptionHandler;
import compiler.model.Token;
import compiler.model.TokenType;
import compiler.scanner.Scanner;

public class Parser {

    private final Scanner scanner;      // Análisador léxico
    private Token token;                // Token atual

    private final ExceptionHandler exceptionHandler;


    public Parser(Scanner scanner) {
        this.scanner = scanner;
        exceptionHandler = new ExceptionHandler();
    }


    public void start() {
        main();
    }

    private void main() {
        token = scanner.nextToken();
        if (token.getType() != TokenType.RESERVED_WORD && !token.getText().equals("main"))
            exceptionHandler.throwReservedWordExpectedException("main", token);
    }


}
