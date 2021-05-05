package compiler.parser;

class ParserTest {
//
//    Parser parser;
//    Scanner scanner;
//
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//        scanner = null;
//        parser = null;
//    }
//
//
//    // ========== MAIN ==========
//
//    @ParameterizedTest
//    @DisplayName("Flow 'main' sem erros")
//    @ValueSource(strings = {"main()", "main() {}"})
//    void testMain_Sucess(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertDoesNotThrow(() -> parser.main());
//    }
//
//    @ParameterizedTest
//    @DisplayName("Recebe tokens diferentes dos esperados")
//    @ValueSource(strings = {"if() {}", "main)) {}", "main(( {}"})
//    void testMain_UnexpectedTokens(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertThrows(TokenExpectedException.class, () -> parser.main());
//    }
//
//    // ==========================
//
//    // ========== BLOCO ==========
//
//    @ParameterizedTest
//    @DisplayName("Flow 'bloco' sem erros")
//    @ValueSource(strings = {"{}", "{ int a = 3; }", "{ while (true) {} }", "{ if (true) {} else {} }"})
//    void testBloco_Sucess(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertDoesNotThrow(() -> parser.bloco());
//    }
//
//    // ===========================
//
//    // ========== COMANDO ==========
//
//    @ParameterizedTest
//    @DisplayName("Flow 'declVar' sem erros")
//    @ValueSource(strings = {"{ int a = 3; }", "{ while (true) {} }", "{ if (true) {} else {} }"})
//    void testComando_Sucess(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertDoesNotThrow(() -> parser.comando());
//    }
//
//    // ===========================
//
//    // ========== TIPO ==========
//
//    @ParameterizedTest
//    @DisplayName("Flow 'tipo' sem erros")
//    @ValueSource(strings = {"int  ", "float  ", "char  "})
//    void testTipo_Sucess(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertDoesNotThrow(() -> parser.tipo());
//    }
//
//    @ParameterizedTest
//    @DisplayName("Flow 'tipo' com erros")
//    @ValueSource(strings = {"main  ", "=  ", "for  "})
//    void testTipo_Error(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertThrows(TokenExpectedException.class, () -> parser.tipo());
//    }
//
//    // ===========================
//
//    // ========== DECLARACAO ==========
//
//    @ParameterizedTest
//    @DisplayName("Flow 'declaracao' sem erros")
//    @ValueSource(strings = {"int a = 3;"})
//    void testDeclaracao_Sucess(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertDoesNotThrow(() -> parser.declaracao());
//    }
//
//    // ===========================
//
//    // ========== DECLARACAO_AUX ==========
//
//    @ParameterizedTest
//    @DisplayName("Flow 'declaracaoAux' sem erros")
//    @ValueSource(strings = {"int   ", "float   ", "char   ", "x  ", "num  ", "qtyApples  "})
//    void testDeclaracaoAux_Sucess(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertDoesNotThrow(() -> parser.declaracaoAux());
//    }
//
//    @ParameterizedTest
//    @DisplayName("Flow 'declaracaoAux' com erros")
//    @ValueSource(strings = {"main  ", "=  ", "54  "})
//    void testDeclaracaoAux_Error(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertThrows(TokenExpectedException.class, () -> parser.declaracaoAux());
//    }
//
//    // ===========================
//
//    // ========== ATRIBUIÇÃO ==========
//
//    @ParameterizedTest
//    @DisplayName("Flow 'atribuição' sem erros")
//    @ValueSource(strings = {"=   ", ";   "})
//    void testAtribuicao_Sucess(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertDoesNotThrow(() -> parser.atribuicao());
//    }
//
//    @ParameterizedTest
//    @DisplayName("Flow 'atribuição' com erros")
//    @ValueSource(strings = {"main  ", "a  ", "54  "})
//    void testAtribuicao_Error(String input) {
//        // given
//        scanner = new Scanner(input, true);
//        parser = new Parser(scanner);
//
//        // when + then
//        Assertions.assertThrows(TokenExpectedException.class, () -> parser.atribuicao());
//    }
//
//    // ===========================

}