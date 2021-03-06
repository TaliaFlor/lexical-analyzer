package compiler;

import compiler.model.Token;
import compiler.parser.Parser;
import compiler.scanner.Scanner;
import compiler.scanner.exception.MalformedTokenException;
import compiler.scanner.exception.UnrecognizedTokenException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Compiler {

    public static void main(String[] args) {
        Scanner scanner = new Scanner("src/main/resources/examples/testes.c");
        Parser parser = new Parser(scanner);
        try {
            parser.main();
            log.info("Compiling finished with success! No errors on input file");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
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
