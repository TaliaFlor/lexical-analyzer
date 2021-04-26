package compiler.scanner;

public class LexicalScannerTest {

//    private static final String PATH = "src/test/resources/";
//
//    Scanner lexicalScanner;
//
//    @BeforeEach
//    void init() {
//        lexicalScanner = new Scanner();
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"0       ", "3453    ", "34423        ", "3  ", "28  ", "23  "})
//    void testTokenIsAnIntegerNumber(String num) {
//        Token token = new Token();
//        for (char c : num.toCharArray()) {
//            token = lexicalScanner.automaton(c);
//        }
//
//        assertEquals(TokenType.INTEGER_NUMBER, token.getType());
//        assertEquals(num.trim(), token.getText());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"232aaa       ", "0absvd    ", "34ssf434        ", "67a7  ", "1q  "})
//    void testTokenIsAMalformedInteger(String num) {
//        assertThrows(MalformedTokenException.class, () -> {
//            for (char c : num.toCharArray()) {
//                lexicalScanner.automaton(c);
//            }
//        });
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"3.45       ", "34.0    ", "32.54545        ", "23.1  ", "0.0000001  "})
//    void testTokenIsARealNumber(String num) {
//        Token token = new Token();
//        for (char c : num.toCharArray()) {
//            token = lexicalScanner.automaton(c);
//        }
//
//        assertEquals(TokenType.REAL_NUMBER, token.getType());
//        assertEquals(num.trim(), token.getText());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"32.abc552       ", "1.z09    ", "0.a1223        ", "322.23adas", "1.32das", "0.111111a"})
//    void testTokenIsAMalformedRealNumber(String num) {
//        assertThrows(MalformedTokenException.class, () -> {
//            for (char c : num.toCharArray()) {
//                lexicalScanner.automaton(c);
//            }
//        });
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"aaaaa       ", "_ad2ds    ", "as3_aad        ", "_____  ", "a_a_a  ", "_a_a_   "})
//    void testTokenIsAnIdentifier(String identifier) {
//        Token token = new Token();
//        for (char c : identifier.toCharArray()) {
//            token = lexicalScanner.automaton(c);
//        }
//
//        assertEquals(TokenType.IDENTIFIER, token.getType());
//        assertEquals(identifier.trim(), token.getText());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"dsfsad@       ", "_$55dsdfs    ", "sdsaf#dasdf__        ", "___090898$  "})
//    void testTokenIsAMalformedIdentifier(String identifier) {
//        assertThrows(MalformedTokenException.class, () -> {
//            for (char c : identifier.toCharArray()) {
//                lexicalScanner.automaton(c);
//            }
//        });
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "main       ", "if    ", "else        ", "while     ", "do      ",
//            "for   ", "int       ", "float       ", "char        "
//    })
//    void testTokenIsAReservedWord(String reservedWord) {
//        Token token = new Token();
//        for (char c : reservedWord.toCharArray()) {
//            token = lexicalScanner.automaton(c);
//        }
//
//        assertEquals(TokenType.RESERVED_WORD, token.getType());
//        assertEquals(reservedWord.trim(), token.getText());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"=        "})
//    void testTokenIsAnAttributionOperator(String operator) {
//        Token token = new Token();
//        for (char c : operator.toCharArray()) {
//            token = lexicalScanner.automaton(c);
//        }
//
//        assertEquals(TokenType.ARITHMETIC_OPERATOR_ATTRIBUTION, token.getType());
//        assertEquals(operator.trim(), token.getText());
//    }
//
//
//    // TODO desacoplar operação da leitura do arquivo
//
//    @Test
//    void testTokenIsAnEqualsOperator() {
//        lexicalScanner = new Scanner(PATH + "equals_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.RELATIONAL_OPERATOR_EQUAL, token.getType());
//    }
//
////    @ParameterizedTest
////    @ValueSource(strings = {"-        "})
////    void testTokenIsMinusOperator(String operator) {
////        Token token = new Token();
////        for (char c : operator.toCharArray()) {
////            token = lexicalScanner.automaton(c);
////        }
////
////        assertEquals(TokenType.ARITHMETIC_OPERATOR_SUBTRACTION, token.getType());
////        assertEquals(operator.trim(), token.getText());
////    }
//
//    @Test
//    void testTokenIsMinusOperator() {
//        lexicalScanner = new Scanner(PATH + "minus_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.ARITHMETIC_OPERATOR_SUBTRACTION, token.getType());
//    }
//
//    @Test
//    void testTokenIsPlusOperator() {
//        lexicalScanner = new Scanner(PATH + "plus_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.ARITHMETIC_OPERATOR_SUM, token.getType());
//    }
//
//    @Test
//    void testTokenIsMultOperator() {
//        lexicalScanner = new Scanner(PATH + "multiplication_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.ARITHMETIC_OPERATOR_MULTIPLICATION, token.getType());
//    }
//
//    @Test
//    void testTokenIsDivOperator() {
//        lexicalScanner = new Scanner(PATH + "division_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.ARITHMETIC_OPERATOR_DIVISION, token.getType());
//    }
//
//    @Test
//    void testTokenIsComma() {
//        lexicalScanner = new Scanner(PATH + "comma_character.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.SPECIAL_CHARACTER_COMMA, token.getType());
//    }
//
//    @Test
//    void testTokenIsSemiColon() {
//        lexicalScanner = new Scanner(PATH + "semicolon_character.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.SPECIAL_CHARACTER_SEMICOLON, token.getType());
//    }
//
//    @Test
//    void testTokenIsOpenParentesis() {
//        lexicalScanner = new Scanner(PATH + "open_parentesis_character.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.SPECIAL_CHARACTER_OPEN_PARENTHESIS, token.getType());
//    }
//
//    @Test
//    void testTokenIsCloseParentesis() {
//        lexicalScanner = new Scanner(PATH + "close_parentesis_character.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.SPECIAL_CHARACTER_CLOSE_PARENTHESIS, token.getType());
//    }
//
//    @Test
//    void testTokenIsOpenCurlyBracketCharacter() {
//        lexicalScanner = new Scanner(PATH + "open_curly_bracket_character.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.SPECIAL_CHARACTER_OPEN_CURLY_BRACKET, token.getType());
//    }
//
//    @Test
//    void testTokenIsCloseCurlyBracketCharacter() {
//        lexicalScanner = new Scanner(PATH + "close_curly_bracket_character.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.SPECIAL_CHARACTER_CLOSE_CURLY_BRACKET, token.getType());
//    }
//
//    @Test
//    void testTokenIsALessThanOperator() {
//        lexicalScanner = new Scanner(PATH + "less_than_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.RELATIONAL_OPERATOR_LESS_THAN, token.getType());
//    }
//
//    @Test
//    void testTokenIsALessThanOrEqualToOperator() {
//        lexicalScanner = new Scanner(PATH + "less_than_or_equal_to_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.RELATIONAL_OPERATOR_LESS_THAN_OR_EQUAL_TO, token.getType());
//    }
//
//    @Test
//    void testTokenIsAGreaterThanOperator() {
//        lexicalScanner = new Scanner(PATH + "greater_than_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.RELATIONAL_OPERATOR_GREATER_THAN, token.getType());
//    }
//
//    @Test
//    void testTokenIsAGreaterThanOrEqualToOperator() {
//        lexicalScanner = new Scanner(PATH + "greater_than_or_equal_to_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.RELATIONAL_OPERATOR_GREATER_THAN_OR_EQUAL_TO, token.getType());
//    }
//
//    @Test
//    void testTokenIsADifferentOperator() {
//        lexicalScanner = new Scanner(PATH + "different_operator.txt");
//        Token token = lexicalScanner.nextToken();
//        assertEquals(TokenType.RELATIONAL_OPERATOR_DIFFERENT, token.getType());
//    }
}
