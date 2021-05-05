package compiler.parser;

import compiler.component.ExceptionHandler2;
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
    private final ExceptionHandler2 exceptionHandler;
    private final ActualToken actualToken;


    public Parser(Scanner scanner) {
        actualToken = ActualToken.getInstance(scanner);
        validate = new TokenValidation(scanner);
        validateConjFirst = new ConjuntoFirstValidation(scanner);
        validateComposed = new ComposedValidation(scanner);
        exceptionHandler = new ExceptionHandler2();
    }


    @Override
    public void start() {
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
            comando();
        if (!actualToken.isTokenFound())
            validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
    }

    // ======= COMANDO =======

    private void comando() {
        try {
            boolean firstFound = false;
            while (validateConjFirst.declaracao()) {
                if (!firstFound)
                    firstFound = true;
                declaracao();
            }
            while (validateConjFirst.iteracao(ActualToken.NEXT_TOKEN_FLAG_FALSE)) {
                if (!firstFound)
                    firstFound = true;
                iteracao();
            }
            while (validateConjFirst.decisao(ActualToken.NEXT_TOKEN_FLAG_FALSE)) {
                if (!firstFound)
                    firstFound = true;
                decisao();
            }

            actualToken.markTokenNotFound();
            if (!firstFound) {
                validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            }
        } catch (TokenExpectedException e) {
            exceptionHandler.throwTokenExpectedException("Command of type 'declaração', 'iteração' or 'decisão', or token '}' expected!");
        }
    }

    // ======= TIPO =======

    private void tipo() {
        validateComposed.tipo();
    }

    // ======= DECLARAÇÃO =======

    private void declaracao() {
        declaracaoAux();
        if (!actualToken.isTokenFound()) {
            validate.identifier();
            actualToken.resetTokenFoundMark();
        }
        atribuicao();
        if(!actualToken.isTokenFound()) {
            validate.semicolon();
            actualToken.resetTokenFoundMark();
        }
    }

    private void declaracaoAux() {
        actualToken.markTokenNotFound();
        try {
            tipo();
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
            atribuicaoAux();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                exceptionHandler.throwTokenExpectedException("Token '=' or ';' expected!");
            }
        }
    }

    private void atribuicaoAux() {
        try {
            fator();
        } catch (TokenExpectedException e) {
            try {
                expressaoAritmetica(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                try {
                    expressaoRelacional(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                } catch (TokenExpectedException e2) {
                    exceptionHandler.throwTokenExpectedException("Identifier; or variable value of type 'int', 'float' or 'char'; or command of type 'expressão aritmética' or 'expressão relacional' expected!");
                }
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
        validate._if();
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
        expressaoRelacional();
        if(!actualToken.isTokenFound()) {
            validate.closeParentesis();
            actualToken.resetTokenFoundMark();
        }
    }

    // ======= ITERAÇÃO =======

    private void iteracao() {
        try {
            validate._while();
            condicao();
            bloco();
        } catch (TokenExpectedException e) {
            try {
                validate._do(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                bloco();
                validate._while();
                condicao();
                validate.semicolon();
            } catch (TokenExpectedException e1) {
                try {
                    validate._for(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    validate.openParentesis();
                    declaracao();
                    expressaoRelacional();
                    if(!actualToken.isTokenFound()) {
                        validate.semicolon();
                        actualToken.resetTokenFoundMark();
                    }
                    counter();
                    validate.closeParentesis();
                    bloco();
                } catch (Exception e2) {
                    exceptionHandler.throwTokenExpectedException("Command of type 'while', 'do-while' or 'for' expected!");
                }
            }
        }
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
                    validate.relationalOperator(ActualToken.NEXT_TOKEN_FLAG_FALSE);
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
        validate.relationalOperator(nextToken);
        expressaoAritmetica(nextToken);
    }

}
