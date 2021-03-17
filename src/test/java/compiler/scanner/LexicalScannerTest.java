package compiler.scanner;

import compiler.exception.MalformedTokenException;
import compiler.model.Token;
import compiler.model.TokenType;
import org.junit.jupiter.api.Disabled;
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

    @Test
    void testTokenIsComma(){
        lexicalScanner = new LexicalScanner(PATH + "comma_character.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.SPECIAL_CHARACTER_COMMA, token.getType());
    }

    @Test
    void testTokenIsSemiColon(){
        lexicalScanner = new LexicalScanner(PATH + "semicolon_character.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.SPECIAL_CHARACTER_SEMICOLON, token.getType());
    }

    @Test
    void testTokenIsOpenParentesis(){
        lexicalScanner = new LexicalScanner(PATH + "open_parentesis_character.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.SPECIAL_CHARACTER_OPEN_PARENTHESIS, token.getType());
    }

    @Test
    void testTokenIsCloseParentesis(){
        lexicalScanner = new LexicalScanner(PATH + "close_parentesis_character.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.SPECIAL_CHARACTER_CLOSE_PARENTHESIS, token.getType());
    }

    @Test
    void testTokenIsOpenCurlyBracketCharacter(){
        lexicalScanner = new LexicalScanner(PATH + "open_curly_bracket_character.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.SPECIAL_CHARACTER_OPEN_CURLY_BRACKET, token.getType());
    }

    @Test
    void testTokenIsCloseCurlyBracketCharacter(){
        lexicalScanner = new LexicalScanner(PATH + "close_curly_bracket_character.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.SPECIAL_CHARACTER_CLOSE_CURLY_BRACKET, token.getType());
    }

    @Test
    void testTokenIsALessThanOperator(){
        lexicalScanner = new LexicalScanner(PATH + "less_than_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.RELATIONAL_OPERATOR_LESS_THAN, token.getType());
    }

    @Test
    void testTokenIsALessThanOrEqualToOperator(){
        lexicalScanner = new LexicalScanner(PATH + "less_than_or_equal_to_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.RELATIONAL_OPERATOR_LESS_THAN_OR_EQUAL_TO, token.getType());
    }

    @Test
    void testTokenIsAGreaterThanOperator(){
        lexicalScanner = new LexicalScanner(PATH + "greater_than_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.RELATIONAL_OPERATOR_GREATER_THAN, token.getType());
    }

    @Test
    void testTokenIsAGreaterThanOrEqualToOperator(){
        lexicalScanner = new LexicalScanner(PATH + "greater_than_or_equal_to_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.RELATIONAL_OPERATOR_GREATER_THAN_OR_EQUAL_TO, token.getType());
    }

    @Test
    void testTokenIsADifferentOperator(){
        lexicalScanner = new LexicalScanner(PATH + "different_operator.txt");
        Token token = lexicalScanner.nextToken();
        assertEquals(TokenType.RELATIONAL_OPERATOR_DIFFERENT, token.getType());
    }
}
