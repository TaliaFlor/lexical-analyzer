package compiler.parser;

import compiler.parser.exception.TokenExpectedException;
import compiler.scanner.Scanner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    Parser parser;
    Scanner scanner;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        scanner = null;
        parser = null;
    }


    // ========== MAIN ==========

    @ParameterizedTest
    @DisplayName("Flow 'main' sem erros")
    @ValueSource(strings = {"main()", "main() {}"})
    void testMain_Sucess(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertDoesNotThrow(() -> parser.main());
    }

    @ParameterizedTest
    @DisplayName("Recebe tokens diferentes dos esperados")
    @ValueSource(strings = {"if() {}", "main)) {}", "main(( {}"})
    void testMain_UnexpectedTokens(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertThrows(TokenExpectedException.class, () -> parser.main());
    }

    // ==========================

    // ========== BLOCO ==========

    @ParameterizedTest
    @DisplayName("Flow 'bloco' sem erros")
    @ValueSource(strings = {"{}", "{ int a = 3; }", "{ while (true) {} }", "{ if (true) {} else {} }"})
    void testBloco_Sucess(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertDoesNotThrow(() -> parser.bloco());
    }

    // ===========================

    // ========== COMANDO ==========

    @ParameterizedTest
    @DisplayName("Flow 'declVar' sem erros")
    @ValueSource(strings = {"{ int a = 3; }", "{ while (true) {} }", "{ if (true) {} else {} }"})
    void testComando_Sucess(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertDoesNotThrow(() -> parser.comando());
    }

    // ===========================

    // ========== DECL_VAR ==========

    @ParameterizedTest
    @DisplayName("Flow 'declVar' sem erros")
    @ValueSource(strings = {"int a = 3;"})
    void testDeclVar_Sucess(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertDoesNotThrow(() -> parser.declVar());
    }

    // ===========================

    // ========== TIPO ==========

    @ParameterizedTest
    @DisplayName("Flow 'tipo' sem erros")
    @ValueSource(strings = {"int  ", "float  ", "char  "})
    void testTipo_Sucess(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertDoesNotThrow(() -> parser.tipo());
    }

    @ParameterizedTest
    @DisplayName("Flow 'tipo' com erros")
    @ValueSource(strings = {"main  ", "=  ", "for  "})
    void testTipo_Error(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertThrows(TokenExpectedException.class, () -> parser.tipo());
    }

    // ===========================

    // ========== DECL_VAR_AUX ==========

    @ParameterizedTest
    @DisplayName("Flow 'declVarAux' sem erros")
    @ValueSource(strings = {"x  ", "num  ", "qtyApples  "})
    void testDeclVarAux_Sucess(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertDoesNotThrow(() -> parser.declVarAux());
    }

    @ParameterizedTest
    @DisplayName("Flow 'declVarAux' com erros")
    @ValueSource(strings = {"main  ", "if  ", "int  "})
    void testDeclVarAux_Error(String input) {
        // given
        scanner = new Scanner(input, true);
        parser = new Parser(scanner);

        // when + then
        Assertions.assertThrows(TokenExpectedException.class, () -> parser.declVarAux());
    }

    // ===========================

}