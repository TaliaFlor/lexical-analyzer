package compiler.component;


import compiler.exception.SintaticalException;
import compiler.model.Token;
import compiler.parser.exception.TokenExpectedException;

public class ExceptionHandler {

    // === TokenExpectedException

    public void throwReservedWordExpectedException(String reservedWord, Token token) {   // Reserved word 'main' expected!
        throw tokenExpectedException("Reserved word '" + reservedWord + "' expected!", token);
    }

    public void throwSpecialCharacterExpectedException(char specialCharacter, Token token) {   // Reserved word 'main' expected!
        throw tokenExpectedException("Special character '" + specialCharacter + "' expected!", token);
    }

    private TokenExpectedException tokenExpectedException(String msg, Token token) {
        return new TokenExpectedException(errorMsg(token, msg));
    }

    private String errorMsg(Token token, String msg) {  // msg + Found ARITHMETIC_OPERATOR_DIVISION --> /. Line 3 and column 4 (3:4).
        return msg + " Found " + token.toString() + ". " + token.lineByColumn();
    }


//    // === UnrecognizedTokenException
//
//    public void throwUnrecognizedSymbolException(char c) {
//        throw unrecognizedTokenException("symbol", c);
//    }
//
//    public void throwUnrecognizedOperatorException(char c) {
//        throw unrecognizedTokenException("operator", c);
//    }
//
//    public void throwUnrecognizedTokenException(char c) {
//        throw unrecognizedTokenException("token", c);
//    }
//
//    private UnrecognizedTokenException unrecognizedTokenException(String type, char c) {
//        return (UnrecognizedTokenException) lexicalException("Unrecognized " + type, c);
//    }
//
//    // === MalformedTokenException
//
//    public void throwMalformedNumberException(char c) {
//        throw malformedTokenException("number", c);
//    }
//
//    public void throwMalformedCharException(char c) {
//        throw malformedTokenException("char", c);
//    }
//
//    public void throwMalformedIdentifierException(char c) {
//        throw malformedTokenException("identifier", c);
//    }
//
//    private MalformedTokenException malformedTokenException(String type, char c) {
//        return (MalformedTokenException) lexicalException("Malformed " + type, c);
//    }
//
//    // === InvalidStateException
//
//    public void throwInvalidStateException(State state) {
//        throw (InvalidStateException) lexicalException("Invalid state", state);
//    }
//
//    // === LexicalException
//
//    private LexicalException lexicalException(String message, char c) {
//        return new LexicalException(message + " - '" + (scanned + c) + "'" + lineAndColumn());
//    }
//
//    private LexicalException lexicalException(String message, State state) {
//        return new LexicalException(message + " - '" + state + "'" + lineAndColumn());
//    }
//
//    // === Helpers
//
//    private String lineAndColumn() {    // Line 3 and column 4 (3:4)
//        String coluna = column - scanned.length();
//        return ". Line " + line + " and column " + coluna + " (" + line + ":" + coluna + ")";
//    }

}
