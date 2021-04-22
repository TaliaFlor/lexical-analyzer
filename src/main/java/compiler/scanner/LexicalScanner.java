package compiler.scanner;

import compiler.exception.LexicalException;
import compiler.exception.MalformedTokenException;
import compiler.exception.UnrecognizedTokenException;
import compiler.model.State;
import compiler.model.Token;
import compiler.model.TokenType;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static compiler.util.Validation.*;

@Slf4j
public class LexicalScanner {   //TODO adicionar linha (/r) e coluna (cada caracter lido por linha) as mensagens de erro

    private char[] content;
    private State state;
    private int posicao;
    private String scanned;
    private int line;           //TODO consertar count de linhas (off by +2)
    private int column;         //TODO consertar count de colunas (off by -1)


    public LexicalScanner() {
        this.line = 1;
        this.column = 0;
        this.scanned = "";
        this.state = State.ZERO;
        this.content = new char[0];
        log.warn("No file provided as input");
    }

    public LexicalScanner(String filename) {
        this.line = 1;
        this.column = 0;
        this.scanned = "";
        this.state = State.ZERO;
        try {
            String text = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            log.trace("File received\n" + text);
            this.content = text.toCharArray();
        } catch (Exception e) {
            log.error("Erro ao ler arquivo - " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void back() {
        posicao--;
    }

    private void next() {
        posicao++;
    }

    private char previousChar() {
        back();
        back();
        return content[posicao];
    }

    private char nextChar() {
        if (isEOF()) {
            return '\0';
        }
        return content[posicao++];
    }

    private boolean isEOF() {
        return posicao >= content.length;
    }

    private boolean isEOF(char c) {
        return c == '\0';
    }

    private void append(char currentChar) {
        scanned += currentChar;
    }

    private void setState(State state) {
        this.state = state;
    }

    private void setStateAndAppendChar(State state, char c) {
        append(c);
        setState(state);
    }

    private void setStateAndNotMove(State state) {
        back();
        setState(state);
    }

    public Token nextToken() {
        if (isEOF())
            return null;

        char currentChar;
        while (true) {
            currentChar = nextChar();
            if (!isEOF(currentChar))
                column++;
            Token token = automaton(currentChar);
            if (token != null)
                return token;
        }
    }

    public Token automaton(char c) {
        switch (state) {
            case ZERO:
                if (isNonConsumable(c))
                    return null;

                append(c);
                if (isNumber(c))
                    state = State.ONE;
                else if (isUnderline(c) || isLetter(c))
                    state = State.SIX;
                else if (isEquals(c))
                    state = State.EIGHT;
                else if (isAritmeticOperator(c))
                    state = State.ELEVEN;
                else if (isSpecialChar(c)) {
                    back();
                    state = State.TWELVE;
                } else if (isRelationalOperator(c))
                    state = State.THIRTEEN;
                else if (isSingleQuotes(c))
                    state = State.TWENTY_TWO;
                else if (isEOF(c))
                    return null;
                else
                    throw new UnrecognizedTokenException("Unrecognized symbol - '" + (scanned + c) + "'. Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
                break;
            case ONE:
                stateOne(c);
                break;
            case TWO:
                return returnToken(TokenType.INTEGER_NUMBER, c);
            case THREE:
                stateThree(c);
                break;
            case FOUR:
                stateFour(c);
                break;
            case FIVE:
                return returnToken(TokenType.REAL_NUMBER, c);
            case SIX:
                stateSix(c);
                break;
            case SEVEN:
                return stateSeven();
            case EIGHT:
                stateEight(c);
                break;
            case NINE:
                return returnToken(TokenType.ARITHMETIC_OPERATOR_ATTRIBUTION, c);
            case TEN:
                return returnToken(TokenType.RELATIONAL_OPERATOR_EQUAL, c);
            case ELEVEN:
                return stateEleven(c);
            case TWELVE:
                return stateTwelve(c);
            case THIRTEEN:
                stateThirteen(c);
                break;
            case FOURTEEN:
                stateFourteen();
                break;
            case FIFTHTEEN:
                return returnToken(TokenType.RELATIONAL_OPERATOR_LESS_THAN_OR_EQUAL_TO, c);
            case SIXTEEN:
                return returnToken(TokenType.RELATIONAL_OPERATOR_LESS_THAN, c);
            case SEVENTEEN:
                stateSeventeen();
                break;
            case EIGHTEEN:
                return returnToken(TokenType.RELATIONAL_OPERATOR_GREATER_THAN_OR_EQUAL_TO, c);
            case NINETEEN:
                return returnToken(TokenType.RELATIONAL_OPERATOR_GREATER_THAN, c);
            case TWENTY:
                stateTwenty(c);
                break;
            case TWENTY_ONE:
                return returnToken(TokenType.RELATIONAL_OPERATOR_DIFFERENT, c);
            case TWENTY_TWO:
                stateTwentyTwo(c);
                break;
            case TWENTY_THREE:
                stateTwentyThree(c);
                break;
            case TWENTY_FOUR:
                return returnToken(TokenType.CHAR);
            default:
                throw new LexicalException("Estado inválido: " + state + ". Na linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
        }
        return null;
    }

    private void stateOne(char c) {
        if (isNumber(c)) {
            append(c);
        } else if (isDot(c)) {
            append(c);
            state = State.THREE;
        } else if (isNonConsumable(c) || !isLetter(c)) {
            back();
            state = State.TWO;
        } else
            throwMalformedNumberException(c);
    }

    private void stateThree(char c) {
        if (isNumber(c))
            state = State.FOUR;
        else
            throwMalformedNumberException(c);
        append(c);
    }

    private void stateFour(char c) {
        if (isNumber(c)) {
            append(c);
        } else if (!isLetter(c)) {
            back();
            state = State.FIVE;
        } else {
            throwMalformedNumberException(c);
        }
    }

    private void stateSix(char c) {
        if (isUnderline(c) || isLetter(c) || isNumber(c)) {
            append(c);
        } else if (isOther(c)) {
            back();
            state = State.SEVEN;
        } else {
            back();
            throw new MalformedTokenException("Malformed identifier - '" + (scanned + c) + "'. Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
        }
    }

    private Token stateSeven() {
        if (isReservedWord(scanned))
            return returnToken(TokenType.RESERVED_WORD);
        else
            return returnToken(TokenType.IDENTIFIER);
    }

    private void stateEight(char c) {
        append(c);
        if (isEquals(c))
            state = State.TEN;
        else
            state = State.NINE;
    }

    private Token stateEleven(char c) {
        TokenType type;
        if (isNonConsumable(c) || isChar(c) || isUnderline(c) || isOpenParentesis(c))   //TODO adicionar o check para char aqui
            c = previousChar();

        if (isMinus(c))
            type = TokenType.ARITHMETIC_OPERATOR_SUBTRACTION;
        else if (isPlus(c))
            type = TokenType.ARITHMETIC_OPERATOR_SUM;
        else if (isMult(c))
            type = TokenType.ARITHMETIC_OPERATOR_MULTIPLICATION;
        else if (isDiv(c))
            type = TokenType.ARITHMETIC_OPERATOR_DIVISION;
        else
            throw new UnrecognizedTokenException("Unrecognized token - '" + (scanned + c) + "'. Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");

        next();
        next();
        return returnToken(type, c);
    }

    private Token stateTwelve(char c) {
        TokenType type;
        if (isComma(c))
            type = TokenType.SPECIAL_CHARACTER_COMMA;
        else if (isSemicolon(c))
            type = TokenType.SPECIAL_CHARACTER_SEMICOLON;
        else if (isOpenParentesis(c))
            type = TokenType.SPECIAL_CHARACTER_OPEN_PARENTHESIS;
        else if (isCloseParentesis(c))
            type = TokenType.SPECIAL_CHARACTER_CLOSE_PARENTHESIS;
        else if (isOpenCurlyBracket(c))
            type = TokenType.SPECIAL_CHARACTER_OPEN_CURLY_BRACKET;
        else if (isCloseCurlyBracket(c))
            type = TokenType.SPECIAL_CHARACTER_CLOSE_CURLY_BRACKET;
        else
            throw new UnrecognizedTokenException("Unrecognized token - '" + (scanned + c) + "'. Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");

        next();
        return returnToken(type, c);
    }

    private void stateThirteen(char c) {
        append(c);

        if (!isRelationalOperator(c))
            c = previousChar();

        if (isLessThan(c))
            state = State.FOURTEEN;
        else if (isGreaterThan(c))
            state = State.SEVENTEEN;
        else if (isDiff(c))
            state = State.TWENTY;
        else
            throw new UnrecognizedTokenException("Unrecognized token - '" + (scanned + c) + "'. Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
    }

    private void stateFourteen() {
        char c;
        c = nextChar();
        if (isEquals(c))
            state = State.FIFTHTEEN;
        else
            state = State.SIXTEEN;
    }

    private void stateSeventeen() {
        char c;
        c = nextChar();
        if (isEquals(c))
            state = State.EIGHTEEN;
        else
            state = State.NINETEEN;
    }

    private void stateTwenty(char c) {
        append(c);
        c = nextChar();
        if (isEquals(c))
            state = State.TWENTY_ONE;
        else
            throw new UnrecognizedTokenException("Unrecognized operator - '" + (scanned + c) + "'. Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
    }

    private void stateTwentyTwo(char c) {
        scanned = String.valueOf(c);    //Para remover o (') que foi appendido
        if (isChar(c))
            state = State.TWENTY_THREE;
        else
            throw new MalformedTokenException("Malformed char - " + scanned + ". Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
    }

    private void stateTwentyThree(char c) {
        if (isSingleQuotes(c))
            state = State.TWENTY_FOUR;
        else
            throw new MalformedTokenException("Malformed char - " + (scanned + c) + ". Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
    }

    private void throwMalformedNumberException(char c) {
        throw new MalformedTokenException("Malformed number - '" + (scanned + c) + "'. Linha " + line + " e coluna " + (column - scanned.length()) + " (" + line + ":" + (column - scanned.length()) + ")");
    }

    private Token returnToken(TokenType type) {
        String text = scanned.trim();
        back();
        resetState();
        return new Token(type, text, line, (column - text.length()));
    }

    private Token returnToken(TokenType type, char c) {
        String text = scanned.trim();
        back();
        append(c);
        resetState();
        return new Token(type, text, line, (column - text.length()));
    }

    private void resetState() {
        scanned = "";
        state = State.ZERO;
    }

    // Caracteres não consumíveis
    public boolean isNonConsumable(char c) {
        if (c == '\n' || c == '\r') {
            line++;
            column = 0;
        }
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    public boolean isOther(char c) {
        return isNonConsumable(c) || isSpecialChar(c) || isAritmeticOperator(c) || isRelationalOperator(c);
    }

}
