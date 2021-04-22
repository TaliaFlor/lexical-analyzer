package compiler.exception;

public class MalformedTokenException extends LexicalException {

    private static final long serialVersionUID = -4198301914889723104L;

    public MalformedTokenException(String message) {
        super(message);
    }

}
