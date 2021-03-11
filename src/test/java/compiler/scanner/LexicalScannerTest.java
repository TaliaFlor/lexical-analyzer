package compiler.scanner;

import compiler.model.Token;
import compiler.model.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LexicalScannerTest {

    private static final String PATH = "src/test/resources/";

    LexicalScanner lexicalScanner;


    @Test
    void testTokenIsAnIntegerNumber() {
        lexicalScanner = new LexicalScanner(PATH + "integer_numbers.txt");
        Token token = lexicalScanner.nextToken();
        Assertions.assertEquals(TokenType.INTEGER_NUMBER, token.getType());
    }

    @Test
    void testTokenIsARealNumber() {
        lexicalScanner = new LexicalScanner(PATH + "real_numbers.txt");
        Token token = lexicalScanner.nextToken();
        Assertions.assertEquals(TokenType.REAL_NUMBER, token.getType());
    }

}
