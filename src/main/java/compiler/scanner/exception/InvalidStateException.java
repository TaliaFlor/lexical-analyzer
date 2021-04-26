package compiler.scanner.exception;

import compiler.exception.LexicalException;

public class InvalidStateException extends LexicalException {

    private static final long serialVersionUID = -7368938454901268056L;

    public InvalidStateException(String message) {
        super(message);
    }

}
