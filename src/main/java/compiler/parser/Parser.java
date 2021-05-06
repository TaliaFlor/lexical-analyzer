package compiler.parser;

import compiler.component.ExceptionHandler;
import compiler.interfaces.Analyser;
import compiler.parser.exception.TokenExpectedException;
import compiler.parser.model.ActualToken;
import compiler.parser.validation.ComposedValidation;
import compiler.parser.validation.ConjuntoFirstValidation;
import compiler.parser.validation.TokenValidation;
import compiler.scanner.Scanner;

public class Parser implements Analyser {
    private final TokenValidation validate;
    private final ConjuntoFirstValidation validateConjFirst;
    private final ComposedValidation validateComposed;
    private final ExceptionHandler exceptionHandler;
    private final ActualToken actualToken;


    public Parser(Scanner scanner) {
        actualToken = ActualToken.getInstance(scanner);
        validate = new TokenValidation();
        validateConjFirst = new ConjuntoFirstValidation();
        validateComposed = new ComposedValidation();
        exceptionHandler = new ExceptionHandler();
    }


    @Override
    public void parse() {
        main();
    }


    // ======= MAIN =======

    private void main() {
        validate._main();
        validate.openParentesis();
        validate.closeParentesis();
        bloco();
    }

    // ======= BLOCO =======

    private void bloco() {
        validate.openCurlyBracket();
        while (validateConjFirst.comando())
            comando(ActualToken.NEXT_TOKEN_FLAG_FALSE);
        if (!actualToken.isTokenFound())
            validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
    }

    // ======= COMANDO =======

    private void comando() {
        comando(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void comando(boolean nextToken) {
        try {
            boolean firstFound = false;
            while (validateConjFirst.declaracao(nextToken)) {   //TODO Remover esses while e transformar em try/catch aninhados
                if (!firstFound)
                    firstFound = true;
                declaracao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            }
            while (validateConjFirst.iteracao(ActualToken.NEXT_TOKEN_FLAG_FALSE)) {
                if (!firstFound)
                    firstFound = true;
                iteracao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            }
            while (validateConjFirst.decisao(ActualToken.NEXT_TOKEN_FLAG_FALSE)) {
                if (!firstFound)
                    firstFound = true;
                decisao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            }

            actualToken.markTokenNotFound();
            if (!firstFound) {
                validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            }
        } catch (TokenExpectedException e) {
            exceptionHandler.throwTokenExpectedException("Command of type 'declaracao', 'iteracao' or 'decisao', or token '}' expected!");
        }
    }

    // ======= TIPO =======

    private void tipo() {
        tipo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void tipo(boolean nextToken) {
        validateComposed.tipo(nextToken);
    }

    // ======= DECLARAÇÃO =======

    private void declaracao() {
        declaracao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void declaracao(boolean nextToken) {
        declaracaoAux(nextToken);
        if (!actualToken.isTokenFound()) {
            validate.identifier();
            actualToken.resetTokenFoundMark();
        }
        atribuicao();
        if (!actualToken.isTokenFound()) {
            validate.semicolon();
            actualToken.resetTokenFoundMark();
        }
    }

    private void declaracaoAux() {
        declaracaoAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void declaracaoAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            tipo(nextToken);
        } catch (TokenExpectedException e) {
            try {
                validate.identifier(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Reserved word 'int', 'float' or 'char', or identifier expected!");
            }
        }
    }

    // ======= ATRIBUIÇÃO =======

    private void atribuicao() {
        actualToken.markTokenNotFound();
        try {
            validate.attribution();
            expressaoRelacional();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Token '=' or ';' expected!");
            }
        }
    }

    // ======= FATOR =======

    private void fator() {
        fator(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void fator(boolean nextToken) {
        try {
            validate.identifier(nextToken);
        } catch (TokenExpectedException e) {
            try {
                validate.variableValues(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Identifier or variable value of type 'int', 'float' or 'char' expected!");
            }
        }
    }

    // ======= DECISÃO =======

    private void decisao() {
        decisao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void decisao(boolean nextToken) {
        validate._if(nextToken);
        condicao();
        bloco();
        _else();
    }

    private void _else() {
        actualToken.markTokenNotFound();
        try {
            validate._else();
            elseAux();
        } catch (TokenExpectedException e) {
            try {
                validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Reserved word 'else' or special character '}' expected!");
            }
        }
    }

    private void elseAux() {
        try {
            decisao();
        } catch (TokenExpectedException e) {
            try {
                bloco();
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Command of type 'decisão' or 'bloco' expected!");
            }
        }
    }

    // ======= CONDIÇÃO =======

    private void condicao() {
        validate.openParentesis();
        condicaoAux();
        validate.closeParentesis();
    }

    private void condicaoAux() {
        fator();
        validate.relationalOperators();
        fator();
    }

    // ======= ITERAÇÃO =======

    private void iteracao() {
        iteracao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void iteracao(boolean nextToken) {
        try {
            _while(nextToken);
        } catch (TokenExpectedException e) {
            try {
                doWhile(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                try {
                    _for(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                } catch (Exception e2) {
                    exceptionHandler.throwTokenExpectedException("Command of type 'while', 'do-while' or 'for' expected!");
                }
            }
        }
    }

    private void _while() {
        _while(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void _while(boolean nextToken) {
        validate._while(nextToken);
        condicao();
        bloco();
    }

    private void doWhile() {
        doWhile(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void doWhile(boolean nextToken) {
        validate._do(nextToken);
        bloco();
        validate._while();
        condicao();
        validate.semicolon();
    }

    private void _for() {
        _for(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void _for(boolean nextToken) {
        validate._for(nextToken);
        validate.openParentesis();
        declaracao();
        condicaoAux();
        if (!actualToken.isTokenFound()) {
            validate.semicolon();
            actualToken.resetTokenFoundMark();
        }
        counter();
        validate.closeParentesis();
        bloco();
    }

    private void counter() {
        validate.identifier();
        counterAux();
        validate.semicolon();
    }

    private void counterAux() {
        try {
            validateComposed.plusOrMinus();
            counterAux2();
        } catch (TokenExpectedException e) {
            try {
                validate.attribution(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                validate.identifier();
                validate.arithmeticOperators();
                validate.integerNumber();
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Arithmetic operator '+', '-' or '=' expected!");
            }
        }
    }

    private void counterAux2() {
        try {
            validateComposed.plusOrMinus();
        } catch (TokenExpectedException e) {
            try {
                validate.attribution(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                validate.integerNumber();
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Arithmetic operator '+', '-' or '=' expected!");
            }
        }
    }

    // ======= EXPRESSÃO ARITMÉTICA =======

    private void expressaoAritmetica() {
        expressaoAritmetica(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void expressaoAritmetica(boolean nextToken) {
        termo(nextToken);
        expressaoAritmeticaAux();
    }

    private void expressaoAritmeticaAux() {
        expressaoAritmeticaAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void expressaoAritmeticaAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            validateComposed.plusOrMinus(nextToken);
            expressaoAritmetica();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                try {
                    validate.closeParentesis(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    actualToken.markTokenFound();
                } catch (TokenExpectedException e2) {
                    exceptionHandler.throwTokenExpectedException("Arithmetic operator '+' or '-'; or special character ';' or ')' expected!");
                }
            }
        }
    }

    private void termo() {
        termo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void termo(boolean nextToken) {
        fator(nextToken);
        termoAux();
    }

    private void termoAux() {
        try {
            validateComposed.multOrDiv();
            termo();
        } catch (TokenExpectedException e) {
            try {
                expressaoAritmeticaAux(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Arithmetic operator '*', '/', '+' or '-' expected!");
            }
        }
    }

    // ======= EXPRESSÃO RELACIONAL =======

    private void expressaoRelacional() {
        expressaoRelacional(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void expressaoRelacional(boolean nextToken) {
        try {
            expressaoAritmetica(nextToken);
            expressaoRelacionalAux();
        } catch (TokenExpectedException e) {
            try {
                expressaoRelacionalAux(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                try {
                    validate.relationalOperators(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    expressaoRelacional();
                } catch (TokenExpectedException e2) {
                    exceptionHandler.throwTokenExpectedException("Identifier; or variable value of type 'int', 'float' or 'char'; or relational operator expected!");
                }
            }
        }
    }

    private void expressaoRelacionalAux() {
        expressaoRelacionalAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void expressaoRelacionalAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            validate.relationalOperators(nextToken);
            expressaoAritmetica();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Relational operation or ';' expected!");
            }
        }
    }

}
