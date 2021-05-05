package compiler.parser.validation;

import compiler.component.ExceptionHandler;
import compiler.interfaces.Validation;
import compiler.model.Token;
import compiler.model.TokenType;
import compiler.model.Type;
import compiler.parser.model.ActualToken;

public class ParserValidation implements Validation {
    protected final ActualToken actualToken;

    protected final ExceptionHandler exceptionHandler;

    protected Type type;
    protected TokenType tokenType;
    protected Token token;


    public ParserValidation() {
        exceptionHandler = new ExceptionHandler();
        actualToken = ActualToken.getInstance();
    }


    @Override
    public void identifier(boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != TokenType.IDENTIFIER)
            exceptionHandler.throwIdentifierExpectedException();
    }

    @Override
    public void reservedWord(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler.throwReservedWordExpectedException(tokenType);
    }

    public void variableValues(boolean nextToken) {
        updateInfo(nextToken);
        if (type != Type.VARIABLE_VALUE)
            exceptionHandler.throwVariableValueExpectedException();
    }

    @Override
    public void variableValue(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler.throwVariableValueExpectedException(tokenType);
    }

    @Override
    public void specialCharacter(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler.throwSpecialCharacterExpectedException(tokenType);
    }

    public void arithmeticOperators(boolean nextToken) {
        updateInfo(nextToken);
        if (type != Type.ARITHMETIC_OPERATOR)
            exceptionHandler.throwArithmeticOperatorExpectedException();
    }

    public void relationalOperators(boolean nextToken) {
        updateInfo(nextToken);
        if (type != Type.RELATIONAL_OPERATOR)
            exceptionHandler.throwRelationalOperatorExpectedException();
    }

    @Override
    public void arithmeticOperator(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler.throwArithmeticOperatorExpectedException(tokenType);
    }

    @Override
    public void relationalOperator(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            exceptionHandler.throwRelationalOperatorExpectedException(tokenType);
    }


    // ======= HELPER METHODS =======

    protected void updateInfo(boolean nextToken) {
        actualToken.updateToken(nextToken);
        type = actualToken.getType();
        tokenType = token.getTokenType();
    }

}
