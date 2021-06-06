package compiler.scanner.exception;

import compiler.exception.LexicalException;

public class UnrecognizedTokenException extends LexicalException {

    private static final long serialVersionUID = 7870081046679011667L;

    public UnrecognizedTokenException(String message) {
        super(message);
    }

}
