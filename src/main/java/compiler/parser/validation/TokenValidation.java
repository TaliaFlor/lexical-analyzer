package compiler.parser.validation;

import compiler.model.TokenType;
import compiler.parser.model.ActualToken;

public class TokenValidation extends ParserValidation {


    // ======= IDENTIFIER =======

    public void identifier() {
        identifier(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void identifier(boolean nextToken) {
        super.identifier(nextToken);
    }

    // ======= RESERVED WORDS =======

    public void _main() {
        _main(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _main(boolean nextToken) {
        super.reservedWord(TokenType.MAIN, nextToken);
    }

    public void _int() {
        _int(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _int(boolean nextToken) {
        super.reservedWord(TokenType.INT, nextToken);
    }

    public void _float() {
        _float(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _float(boolean nextToken) {
        super.reservedWord(TokenType.FLOAT, nextToken);
    }

    public void _char() {
        _char(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _char(boolean nextToken) {
        super.reservedWord(TokenType.CHAR, nextToken);
    }

    public void _if() {
        _if(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _if(boolean nextToken) {
        super.reservedWord(TokenType.IF, nextToken);
    }

    public void _else() {
        _else(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _else(boolean nextToken) {
        super.reservedWord(TokenType.ELSE, nextToken);
    }

    public void _while() {
        _while(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _while(boolean nextToken) {
        super.reservedWord(TokenType.WHILE, nextToken);
    }

    public void _do() {
        _do(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _do(boolean nextToken) {
        super.reservedWord(TokenType.DO, nextToken);
    }

    public void _for() {
        _for(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _for(boolean nextToken) {
        super.reservedWord(TokenType.FOR, nextToken);
    }

    // ======= VARIABLE VALUES =======

    public void variableValues() {
        variableValues(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void variableValues(boolean nextToken) {
        super.variableValues(nextToken);
    }

    public void integerNumber() {
        integerNumber(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void integerNumber(boolean nextToken) {
        super.variableValue(TokenType.INTEGER_NUMBER, nextToken);
    }

    public void realNumber() {
        realNumber(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void realNumber(boolean nextToken) {
        super.variableValue(TokenType.REAL_NUMBER, nextToken);
    }

    public void charactere() {
        charactere(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void charactere(boolean nextToken) {
        super.variableValue(TokenType.CHARACTERE, nextToken);
    }

    // ======= SPECIAL CHARACTERS =======

    public void comma() {
        comma(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void comma(boolean nextToken) {
        super.specialCharacter(TokenType.COMMA, nextToken);
    }

    public void semicolon() {
        semicolon(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void semicolon(boolean nextToken) {
        super.specialCharacter(TokenType.SEMICOLON, nextToken);
    }

    public void openParentesis() {
        openParentesis(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void openParentesis(boolean nextToken) {
        super.specialCharacter(TokenType.OPEN_PARENTHESIS, nextToken);
    }

    public void closeParentesis() {
        closeParentesis(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void closeParentesis(boolean nextToken) {
        super.specialCharacter(TokenType.CLOSE_PARENTHESIS, nextToken);
    }

    public void openCurlyBracket() {
        openCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void openCurlyBracket(boolean nextToken) {
        super.specialCharacter(TokenType.OPEN_CURLY_BRACKET, nextToken);
    }

    public void closeCurlyBracket() {
        closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void closeCurlyBracket(boolean nextToken) {
        super.specialCharacter(TokenType.CLOSE_CURLY_BRACKET, nextToken);
    }

    // ======= ARITHMETIC OPERATORS =======

    public void arithmeticOperators() {
        arithmeticOperators(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void arithmeticOperators(boolean nextToken) {
        super.arithmeticOperators(nextToken);
    }

    public void attribution() {
        attribution(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void attribution(boolean nextToken) {
        super.arithmeticOperator(TokenType.ATTRIBUTION, nextToken);
    }

    public void plus() {
        plus(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void plus(boolean nextToken) {
        super.arithmeticOperator(TokenType.PLUS, nextToken);
    }

    public void minus() {
        minus(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void minus(boolean nextToken) {
        super.arithmeticOperator(TokenType.MINUS, nextToken);
    }

    public void multiplication() {
        multiplication(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void multiplication(boolean nextToken) {
        super.arithmeticOperator(TokenType.MULTIPLICATION, nextToken);
    }

    public void division() {
        division(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void division(boolean nextToken) {
        super.arithmeticOperator(TokenType.DIVISION, nextToken);
    }

    // ======= RELATIONAL OPERATORS =======

    public void equal() {
        equal(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void equal(boolean nextToken) {
        super.relationalOperator(TokenType.EQUAL, nextToken);
    }

    public void different() {
        different(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void different(boolean nextToken) {
        super.relationalOperator(TokenType.DIFFERENT, nextToken);
    }

    public void lessThan() {
        lessThan(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void lessThan(boolean nextToken) {
        super.relationalOperator(TokenType.LESS_THAN, nextToken);
    }

    public void greaterThan() {
        greaterThan(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void greaterThan(boolean nextToken) {
        super.relationalOperator(TokenType.GREATER_THAN, nextToken);
    }

    public void lessThanOrEqualTo() {
        lessThanOrEqualTo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void lessThanOrEqualTo(boolean nextToken) {
        super.relationalOperator(TokenType.LESS_THAN_OR_EQUAL_TO, nextToken);
    }

    public void greaterThanOrEqualTo() {
        greaterThanOrEqualTo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void greaterThanOrEqualTo(boolean nextToken) {
        super.relationalOperator(TokenType.GREATER_THAN_OR_EQUAL_TO, nextToken);
    }

}
