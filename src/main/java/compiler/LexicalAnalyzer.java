package compiler;

import compiler.exception.MalformedTokenException;
import compiler.exception.UnrecognizedTokenException;
import compiler.model.Token;
import compiler.scanner.LexicalScanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LexicalAnalyzer {

    public static void main(String[] args) {
        try {
//            LexicalScanner lexicalScanner = new LexicalScanner("somatorio.c");
//            LexicalScanner lexicalScanner = new LexicalScanner("somatorio_erro.c");        // Exemplo de arquivo com erro
            LexicalScanner lexicalScanner = new LexicalScanner("somatorio_testes.c");

            log.info("Start of reading");

            Token token;
            do {
                token = lexicalScanner.nextToken();
                if (token != null)
                    System.out.println(token);
            } while (token != null);
        } catch (MalformedTokenException e) {
            log.error("MalformedTokenException: " + e.getMessage());
        } catch (UnrecognizedTokenException e) {
            log.error("UnrecognizedTokenException: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            log.info("End of reading");
        } catch (Exception e) {
            log.error("Error occurred during file lexical analysis");
            e.printStackTrace();
        }
    }

}
