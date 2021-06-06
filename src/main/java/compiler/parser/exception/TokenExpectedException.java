package compiler.parser.exception;

import compiler.exception.SintaticalException;

public class TokenExpectedException extends SintaticalException {

    private static final long serialVersionUID = -7693767281860255407L;

    public TokenExpectedException(String message) {
        super(message);
    }

}
