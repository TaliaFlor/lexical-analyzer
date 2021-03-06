package compiler.scanner.util;

import compiler.scanner.exception.MalformedTokenException;
import compiler.scanner.model.State;

import static compiler.scanner.component.ScannerValidation.*;

public class ScannerUtil {

    private char[] content;
    private State state;
    private int posicao;
    private String scanned;
    private int line;           //TODO consertar count de linhas (off by +2)
    private int column;         //TODO consertar count de colunas (off by -1)


    public ScannerUtil(char[] content) {
        this.line = 1;
        this.column = 0;
        this.scanned = "";
        this.state = State.ZERO;
        this.content = content;
    }


    private void back() {
        posicao--;
    }

    private void next() {
        posicao++;
    }

    private char previousChar() {
        back();
        back();
        return content[posicao];
    }

    private char nextChar() {
        if (isEOF()) {
            return '\0';
        }
        return content[posicao++];
    }

    private boolean isEOF() {
        return posicao >= content.length;
    }

    private boolean isEOF(char c) {
        return c == '\0';
    }

    private void append(char currentChar) {
        scanned += currentChar;
    }

    private void setState(State state) {
        this.state = state;
    }

    private void setStateAndAppendChar(State state, char c) {
        append(c);
        setState(state);
    }

    private void setStateAndNotMove(State state) {
        back();
        setState(state);
    }

    private void throwMalformedNumberException(char c) {
        throw new MalformedTokenException("Malformed number - '" + (scanned + c) + "'. Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
    }

    private void resetState() {
        scanned = "";
        state = State.ZERO;
    }

    // Caracteres não consumíveis
    public boolean isNonConsumable(char c) {
        if (c == '\n' || c == '\r') {
            line++;
            column = 0;
        }
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    public boolean isOther(char c) {
        return isNonConsumable(c) || isSpecialChar(c) || isAritmeticOperator(c) || isRelationalOperator(c);
    }

}
