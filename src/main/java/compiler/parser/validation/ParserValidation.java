package compiler.parser.validation;

import compiler.component.ExceptionLauncher;
import compiler.interfaces.Validation;
import compiler.model.Token;
import compiler.model.TokenType;
import compiler.model.Type;
import compiler.parser.model.ActualToken;

public class ParserValidation implements Validation {
    protected final ActualToken actualToken;

    protected final ExceptionLauncher _throw;

    protected Type type;
    protected TokenType tokenType;
    protected Token token;


    public ParserValidation() {
        _throw = new ExceptionLauncher();
        actualToken = ActualToken.getInstance();
    }


    @Override
    public void identifier(boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != TokenType.IDENTIFIER)
            _throw.identifierExpectedException();
    }

    @Override
    public void reservedWord(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            _throw.reservedWordExpectedException(tokenType);
    }

    public void variableValues(boolean nextToken) {
        updateInfo(nextToken);
        if (type != Type.VARIABLE_VALUE)
            _throw.variableValueExpectedException();
    }

    @Override
    public void variableValue(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            _throw.variableValueExpectedException(tokenType);
    }

    @Override
    public void specialCharacter(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            _throw.specialCharacterExpectedException(tokenType);
    }

    public void arithmeticOperators(boolean nextToken) {
        updateInfo(nextToken);
        if (type != Type.ARITHMETIC_OPERATOR)
            _throw.arithmeticOperatorExpectedException();
    }

    public void relationalOperators(boolean nextToken) {
        updateInfo(nextToken);
        if (type != Type.RELATIONAL_OPERATOR)
            _throw.relationalOperatorExpectedException();
    }

    @Override
    public void arithmeticOperator(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            _throw.arithmeticOperatorExpectedException(tokenType);
    }

    @Override
    public void relationalOperator(TokenType tokenType, boolean nextToken) {
        updateInfo(nextToken);
        if (this.tokenType != tokenType)
            _throw.relationalOperatorExpectedException(tokenType);
    }


    // ======= HELPER METHODS =======

    protected void updateInfo(boolean nextToken) {
        actualToken.updateToken(nextToken);
        token = actualToken.getToken();
        type = actualToken.getType();
        tokenType = actualToken.getTokenType();
    }

}
