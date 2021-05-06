package compiler.parser;

import compiler.component.ExceptionHandler;
import compiler.interfaces.Analyser;
import compiler.parser.exception.TokenExpectedException;
import compiler.parser.model.ActualToken;
import compiler.parser.validation.ComposedValidation;
import compiler.parser.validation.ConjuntoFirstValidation;
import compiler.parser.validation.TokenValidation;
import compiler.scanner.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Parser implements Analyser {
    public final TokenValidation validate;
    public final ConjuntoFirstValidation validateConjFirst;
    public final ComposedValidation validateComposed;
    public final ExceptionHandler exceptionHandler;
    public final ActualToken actualToken;


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

    public void main() {
        try {
            validate._main();
            validate.openParentesis();
            validate.closeParentesis();
            bloco();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    // ======= BLOCO =======

    public void bloco() {
        try {
            validate.openCurlyBracket();
            while (validateConjFirst.comando())
                comando(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            if (!actualToken.isTokenFound()) {
                validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.resetTokenFoundMark();
            }
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    // ======= COMANDO =======

    public void comando() {
        comando(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void comando(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            declaracao(nextToken);
        } catch (TokenExpectedException e) {
            try {
                iteracao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                try {
                    decisao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                } catch (TokenExpectedException e2) {
                    try {
                        validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                        actualToken.markTokenFound();
                    } catch (TokenExpectedException e3) {
                        log.error(exceptionHandler.throwTokenExpectedException("Command of type 'declaracao', 'iteracao' or 'decisao'; or token '}' expected!").getMessage());
                    }
                }
            }
        }
    }

    // ======= TIPO =======

    public void tipo() {
        tipo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void tipo(boolean nextToken) {
        try {
            validateComposed.tipo(nextToken);
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    // ======= DECLARAÇÃO =======

    public void declaracao() {
        declaracao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void declaracao(boolean nextToken) {
        try {
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
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void declaracaoAux() {
        declaracaoAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void declaracaoAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            tipo(nextToken);
        } catch (TokenExpectedException e) {
            try {
                validate.identifier(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                log.error(exceptionHandler.throwTokenExpectedException("Reserved word 'int', 'float' or 'char', or identifier expected!").getMessage());
            }
        }
    }

    // ======= ATRIBUIÇÃO =======

    public void atribuicao() {
        actualToken.markTokenNotFound();
        try {
            validate.attribution();
            expressaoRelacional();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                log.error(exceptionHandler.throwTokenExpectedException("Token '=' or ';' expected!").getMessage());
            }
        }
    }

    // ======= FATOR =======

    public void fator() {
        fator(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void fator(boolean nextToken) {
        try {
            validate.identifier(nextToken);
        } catch (TokenExpectedException e) {
            try {
                validate.variableValues(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                log.error(exceptionHandler.throwTokenExpectedException("Identifier or variable value of type 'int', 'float' or 'char' expected!").getMessage());
            }
        }
    }

    // ======= DECISÃO =======

    public void decisao() {
        decisao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void decisao(boolean nextToken) {
        try {
            validate._if(nextToken);
            condicao();
            bloco();
            _else();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void _else() {
        actualToken.markTokenNotFound();
        try {
            validate._else();
            elseAux();
        } catch (TokenExpectedException e) {
            try {
                validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                log.error(exceptionHandler.throwTokenExpectedException("Reserved word 'else' or special character '}' expected!").getMessage());
            }
        }
    }

    public void elseAux() {
        try {
            decisao();
        } catch (TokenExpectedException e) {
            try {
                bloco();
            } catch (TokenExpectedException e1) {
                log.error(exceptionHandler.throwTokenExpectedException("Command of type 'decisão' or 'bloco' expected!").getMessage());
            }
        }
    }

    // ======= CONDIÇÃO =======

    public void condicao() {
        try {
            validate.openParentesis();
            condicaoAux();
            validate.closeParentesis();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void condicaoAux() {
        try {
            fator();
            validate.relationalOperators();
            fator();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    // ======= ITERAÇÃO =======

    public void iteracao() {
        iteracao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void iteracao(boolean nextToken) {
        try {
            _while(nextToken);
        } catch (TokenExpectedException e) {
            try {
                doWhile(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                try {
                    _for(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                } catch (Exception e2) {
                    log.error(exceptionHandler.throwTokenExpectedException("Command of type 'while', 'do-while' or 'for' expected!").getMessage());
                }
            }
        }
    }

    public void _while() {
        _while(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _while(boolean nextToken) {
        try {
            validate._while(nextToken);
            condicao();
            bloco();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void doWhile() {
        doWhile(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void doWhile(boolean nextToken) {
        try {
            validate._do(nextToken);
            bloco();
            validate._while();
            condicao();
            validate.semicolon();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void _for() {
        _for(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _for(boolean nextToken) {
        try {
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
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void counter() {
        try {
            validate.identifier();
            counterAux();
            validate.semicolon();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void counterAux() {
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
                log.error(exceptionHandler.throwTokenExpectedException("Arithmetic operator '+', '-' or '=' expected!").getMessage());
            }
        }
    }

    public void counterAux2() {
        try {
            validateComposed.plusOrMinus();
        } catch (TokenExpectedException e) {
            try {
                validate.attribution(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                validate.integerNumber();
            } catch (TokenExpectedException e1) {
                log.error(exceptionHandler.throwTokenExpectedException("Arithmetic operator '+', '-' or '=' expected!").getMessage());
            }
        }
    }

    // ======= EXPRESSÃO ARITMÉTICA =======

    public void expressaoAritmetica() {
        expressaoAritmetica(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoAritmetica(boolean nextToken) {
        try {
            termo(nextToken);
            if (validateConjFirst.plusOrMinus(ActualToken.NEXT_TOKEN_FLAG_FALSE))
                expressaoAritmeticaAux();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void expressaoAritmeticaAux() {
        expressaoAritmeticaAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoAritmeticaAux(boolean nextToken) {
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
                    log.error(exceptionHandler.throwTokenExpectedException("Arithmetic operator '+' or '-'; or special character ';' or ')' expected!").getMessage());
                }
            }
        }
    }

    public void termo() {
        termo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void termo(boolean nextToken) {
        try {
            fator(nextToken);
            termoAux();
        } catch (TokenExpectedException e) {
            log.error(e.getMessage());
        }
    }

    public void termoAux() {
        try {
            validateComposed.multOrDiv();
            termo();
        } catch (TokenExpectedException e) {
            try {
                expressaoAritmeticaAux(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                log.error(exceptionHandler.throwTokenExpectedException("Arithmetic operator '*', '/', '+' or '-' expected!").getMessage());
            }
        }
    }

    // ======= EXPRESSÃO RELACIONAL =======

    public void expressaoRelacional() {
        expressaoRelacional(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoRelacional(boolean nextToken) {
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
                    log.error(exceptionHandler.throwTokenExpectedException("Identifier; or variable value of type 'int', 'float' or 'char'; or relational operator expected!").getMessage());
                }
            }
        }
    }

    public void expressaoRelacionalAux() {
        expressaoRelacionalAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoRelacionalAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            validate.relationalOperators(nextToken);
            expressaoAritmetica();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                log.error(exceptionHandler.throwTokenExpectedException("Relational operation or ';' expected!").getMessage());
            }
        }
    }

}
