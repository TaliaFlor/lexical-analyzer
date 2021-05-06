package compiler;

import compiler.model.Token;
import compiler.parser.Parser;
import compiler.parser.exception.TokenExpectedException;
import compiler.scanner.Scanner;
import compiler.scanner.exception.MalformedTokenException;
import compiler.scanner.exception.UnrecognizedTokenException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Compiler {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner("src/main/resources/examples/somatorio.c");
//            Scanner scanner = new Scanner("src/main/resources/examples/somatorio_erro.c");        // Exemplo de arquivo com erro
//            Scanner scanner = new Scanner("src/main/resources/examples/somatorio_testes.c");

//        Scanner scanner = new Scanner("src/main/resources/examples/empty/for.c");

//        executeScannerOnly(scanner);

        Parser parser = new Parser(scanner);
//        parser.parse();

        try {
//            parser._for();  //TODO bug na declaração
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }

    }

    private static void executeScannerOnly(Scanner scanner) {
        try {
            log.info("Start of reading");

            Token token;
            do {
                token = scanner.nextToken();
                if (token != null)
                    System.out.println(token);
            } while (token != null);
        } catch (MalformedTokenException e) {
            log.error("MalformedTokenException: " + e.getMessage());
        } catch (UnrecognizedTokenException e) {
            log.error("UnrecognizedTokenException: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred during file lexical analysis");
            e.printStackTrace();
        }
    }

}
