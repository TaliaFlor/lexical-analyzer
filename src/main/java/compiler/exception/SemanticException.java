package compiler.exception;

public class SemanticException extends RuntimeException {
    private static final long serialVersionUID = -3046059574247925659L;

    public SemanticException(String message) {
        super(message);
    }

}
