package compiler.interfaces;

import compiler.model.TokenType;
import compiler.model.Type;

public interface Validation {

    void identifier(boolean nextToken);

    void reservedWord(TokenType tokenType, boolean nextToken);

    void variableValue(TokenType tokenType, boolean nextToken);

    void specialCharacter(TokenType tokenType, boolean nextToken);

    void arithmeticOperator(TokenType tokenType, boolean nextToken);

    void relationalOperator(TokenType tokenType, boolean nextToken);

}
