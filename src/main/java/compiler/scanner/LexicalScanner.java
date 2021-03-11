package compiler.scanner;

import compiler.exception.MalformedTokenException;
import compiler.exception.UnrecognizedTokenException;
import compiler.model.State;
import compiler.model.Token;
import compiler.model.TokenType;
import compiler.util.Utils;
import lombok.extern.java.Log;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static compiler.util.Utils.isDigit;
import static compiler.util.Utils.isDot;

@Log
public class LexicalScanner {

    private char[] content;
    private State state;
    private int posicao;
    private String scanned;


    public LexicalScanner(String filename) {
        this.scanned = "";
        this.state = State.ZERO;
        try {
            String text = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            log.info("\n-------- File received --------\n" + text + "\n--------------------------------");
            this.content = text.toCharArray();
        } catch (Exception e) {
            log.severe("Erro ao ler arquivo - " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void back() {
        posicao--;
    }

    private void next() {
        posicao++;
    }

    private char nextChar() {
        return content[posicao++];
    }

    private boolean isEOF() {
        return posicao == content.length;
    }

    public Token nextToken() {
        if (isEOF())
            return null;

        char currentChar;
        while (true) {
            currentChar = nextChar();
            switch (state) {
                case ZERO:
                    if (isDigit(currentChar))
                        state = State.ONE;
                    else if (isDot(currentChar))
                        state = State.THREE;
                    else {
                        append(currentChar);
                        throw new UnrecognizedTokenException("Unrecognized symbol - '" + scanned + "'");
                    }
                    append(currentChar);
                    break;
                case ONE:
                    if (!Utils.isDot(currentChar) && !Utils.isLetter(currentChar))
                        state = State.TWO;
                    else {
                        append(currentChar);
                        throw new MalformedTokenException("Malformed number - '" + scanned + "'");
                    }
                    append(currentChar);
                    break;
                case TWO:
                    back();
                    return new Token(TokenType.INTEGER_NUMBER, scanned.trim());
                case THREE:
                    break;
                case FOUR:
                    break;
                case FIVE:
                    break;
                case SIX:
                    break;
                case SEVEN:
                    break;
                case EIGHT:
                    break;
                case NINE:
                    break;
                case TEN:
                    break;
                case ELEVEN:
                    break;
                case TWELVE:
                    break;
                case THIRTEEN:
                    break;
                case FOURTEEN:
                    break;
                case FIFTHTEEN:
                    break;
                case SIXTEEN:
                    break;
                case SEVENTEEN:
                    break;
                case EIGHTEEN:
                    break;
                case NINETEEN:
                    break;
                case TWENTY:
                    break;
                default:
                    throw new IllegalStateException("Estado inválido: " + state);
            }

        }
    }

    private void append(char currentChar) {
        scanned += currentChar;
    }

}
