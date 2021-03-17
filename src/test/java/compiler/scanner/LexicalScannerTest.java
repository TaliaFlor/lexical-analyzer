package compiler.scanner;

import compiler.exception.MalformedTokenException;
import compiler.model.Token;
import compiler.model.TokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LexicalScannerTest {

    private static final String PATH = "src/test/resources/";

    LexicalScanner lexicalScanner;


    @Test
    void testTokenIsAnIntegerNumber() {
        lexicalScanner = new LexicalScanner(PATH + "integer_numbers.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.INTEGER_NUMBER, token.getType());
    }

    @Test
    void testTokenIsARealNumber() {
        lexicalScanner = new LexicalScanner(PATH + "real_numbers.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.REAL_NUMBER, token.getType());
    }

    @Test
    void testTokenIsAMalformedInteger() {
        lexicalScanner = new LexicalScanner(PATH + "malformed_integer_numbers.txt");
        assertThrows(MalformedTokenException.class, () -> lexicalScanner.nextToken(), "Malformed number");
    }

    @Test
    void testTokenIsAMalformedRealNumber() {
        lexicalScanner = new LexicalScanner(PATH + "malformed_real_numbers.txt");
        assertThrows(MalformedTokenException.class, () -> lexicalScanner.nextToken(), "Malformed number");
    }

    @Test
    void testTokenIsAMalformedRealNumber2() {
        lexicalScanner = new LexicalScanner(PATH + "malformed_real_numbers_2.txt");
        assertThrows(MalformedTokenException.class, () -> lexicalScanner.nextToken(), "Malformed number");
    }

    @Test
    void testTokenIsAnIdentifierStartedWithLetter() {
        lexicalScanner = new LexicalScanner(PATH + "identifiers_started_with_letters.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.IDENTIFIER, token.getType());
    }

    @Test
    void testTokenIsAnIdentifierStartedWithUnderline() {
        lexicalScanner = new LexicalScanner(PATH + "identifiers_started_with_underline.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.IDENTIFIER, token.getType());
    }

    @Test
    void testTokenIsAReservedWord() {
        lexicalScanner = new LexicalScanner(PATH + "reserved_words.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.RESERVED_WORD, token.getType());
    }

    @Test
    void testTokenIsAMalformedIdentifier() {
        lexicalScanner = new LexicalScanner(PATH + "malformed_identifiers.txt");
        assertThrows(MalformedTokenException.class, () -> lexicalScanner.nextToken(), "Malformed identifier");
    }

    @Test
    void testTokenIsAnAttributionOperator() {
        lexicalScanner = new LexicalScanner(PATH + "attribution_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.ARITHMETIC_OPERATOR_ATTRIBUTION, token.getType());
    }

    @Test
    void testTokenIsAnEqualsOperator() {
        lexicalScanner = new LexicalScanner(PATH + "equals_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.RELATIONAL_OPERATOR_EQUAL, token.getType());
    }

    @Test
    void testTokenIsMinusOperator() {
        lexicalScanner = new LexicalScanner(PATH + "minus_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.ARITHMETIC_OPERATOR_SUBTRACTION, token.getType());
    }

    @Test
    void testTokenIsPlusOperator() {
        lexicalScanner = new LexicalScanner(PATH + "plus_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.ARITHMETIC_OPERATOR_SUM, token.getType());
    }

    @Test
    void testTokenIsMultOperator() {
        lexicalScanner = new LexicalScanner(PATH + "multiplication_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.ARITHMETIC_OPERATOR_MULTIPLICATION, token.getType());
    }

    @Test
    void testTokenIsDivOperator() {
        lexicalScanner = new LexicalScanner(PATH + "division_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.ARITHMETIC_OPERATOR_DIVISION, token.getType());
    }

}
