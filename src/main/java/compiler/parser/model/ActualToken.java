package compiler.parser.model;

import compiler.model.Token;
import compiler.model.TokenType;
import compiler.model.Type;
import compiler.scanner.Scanner;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ActualToken {
    public static final boolean NEXT_TOKEN_FLAG_TRUE = true;
    public static final boolean NEXT_TOKEN_FLAG_FALSE = false;
    @Getter
    private static ActualToken instance = null;
    private final Scanner scanner;
    private boolean tokenFound;
    private Token token;                                    // Token atual
    private Type type;
    private TokenType tokenType;


    private ActualToken(Scanner scanner) {
        this.scanner = scanner;
    }


    public static ActualToken getInstance(Scanner scanner) {
        if (instance == null)
            instance = new ActualToken(scanner);
        return instance;
    }


    public void updateToken(boolean nextToken) {
        getToken(nextToken);
        type = token.getType();
        tokenType = token.getTokenType();
    }

    private void getToken(boolean nextToken) {
        try {
            if (nextToken) {
                token = scanner.nextToken();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            log.info("End of file");
            System.exit(0);
        }
    }

    public void markTokenFound() {
        tokenFound = true;
    }

    public void markTokenNotFound() {
        tokenFound = false;
    }

    public void resetTokenFoundMark() {
        tokenFound = false;
    }

}
