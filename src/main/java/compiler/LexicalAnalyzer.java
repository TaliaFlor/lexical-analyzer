package compiler;

import compiler.exception.MalformedTokenException;
import compiler.exception.UnrecognizedTokenException;
import compiler.model.Token;
import compiler.scanner.LexicalScanner;
import lombok.extern.java.Log;

@Log
public class LexicalAnalyzer {

    public static void main(String[] args) {
        try {
            LexicalScanner lexicalScanner = new LexicalScanner("somatorio2.c");

            System.out.println("\n========= Start of reading =========\n");

            Token token;
            do {
                token = lexicalScanner.nextToken();
                if (token != null)
                    System.out.println(token);
            } while (token != null);
        } catch (MalformedTokenException e) {
            log.severe("MalformedTokenException: " + e.getMessage());
        } catch (UnrecognizedTokenException e) {
            log.severe("UnrecognizedTokenException: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n========= End of reading =========\n");
        } catch (Exception e) {
            log.severe("Error occurred during file lexical analysis");
            e.printStackTrace();
        }
    }

}
