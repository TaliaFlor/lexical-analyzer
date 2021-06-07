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
    public final TokenValidation validate;
    public final ConjuntoFirstValidation validateConjFirst;
    public final ComposedValidation validateComposed;
    public final ExceptionHandler handler;
    public final ActualToken actualToken;


    public Parser(Scanner scanner) {
        actualToken = ActualToken.getInstance(scanner);
        validate = new TokenValidation();
        validateConjFirst = new ConjuntoFirstValidation();
        validateComposed = new ComposedValidation();
        handler = new ExceptionHandler();
    }


    @Override
    public void parse() {
        main();
    }


    // ======= MAIN =======

    public void main() {
        validate._main();
        validate.openParentesis();
        validate.closeParentesis();
        bloco();
    }

    // ======= BLOCO =======

    public void bloco() {
        validate.openCurlyBracket();
        while (validateConjFirst.comando())
            comando(ActualToken.NEXT_TOKEN_FLAG_FALSE);
        if (!actualToken.isTokenFound()) {
            validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            actualToken.resetTokenFoundMark();
        }
    }

    // ======= COMANDO =======

    public void comando() {
        comando(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void comando(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            declVar(nextToken);
        } catch (TokenExpectedException e) {
            try {
                acessoVar(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (Exception e1) {
                try {
                    iteracao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                } catch (TokenExpectedException e2) {
                    try {
                        decisao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    } catch (TokenExpectedException e3) {
                        try {
                            validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                            actualToken.markTokenFound();
                        } catch (TokenExpectedException e4) {
                            handler.handle("Command of type 'declaracao', 'iteracao' or 'decisao'; or token '}' expected!");
                        }
                    }
                }
            }
        }
    }

    // ======= TIPO =======

    public void tipoVar() {
        tipoVar(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void tipoVar(boolean nextToken) {
        validateComposed.tipo(nextToken);
    }

    // ======= DECLARAÇÃO DE VARIÁVEL =======

    public void declVar() {
        declVar(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void declVar(boolean nextToken) {
        tipoVar(nextToken);
        acessoVar();
    }

    public void declVarAux() {
        declVarAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void declVarAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            initVar(nextToken);
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                handler.handle("Reserved word 'int', 'float' or 'char', identifier or token ';' expected!");
            }
        }
    }

    // ======= ACESSO DE VARIÁVEL =======

    public void acessoVar() {
        acessoVar(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void acessoVar(boolean nextToken) {
        validate.identifier(nextToken);
        declVarAux();
        if (!actualToken.isTokenFound()) {
            validate.semicolon();
            actualToken.resetTokenFoundMark();
        }
    }

    // ======= INICIALIZAÇÃO DE VARIÁVEL =======

    public void initVar() {
        initVar(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void initVar(boolean nextToken) {
        validate.attribution(nextToken);
        initVarAux();
    }

    public void initVarAux() {
        initVarAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void initVarAux(boolean nextToken) {
        try {
            expArit(nextToken);
        } catch (TokenExpectedException e) {
            try {
                validate.charactere(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                handler.handle("Variable value or aritmetic expression expected!");
            }
        }
    }

    // ======= VALOR NUMÉRICO =======

    public void valorNum() {
        valorNum(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void valorNum(boolean nextToken) {
        try {
            validate.integerNumber(nextToken);
        } catch (TokenExpectedException e) {
            try {
                validate.realNumber(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                handler.handle("Number expected!");
            }
        }
    }

    // ======= DECISÃO =======

    public void decisao() {
        decisao(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void decisao(boolean nextToken) {
        validate._if(nextToken);
        condicao();
        bloco();
        _else();
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
                handler.handle("Reserved word 'else' or special character '}' expected!");
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
                handler.handle("Command of type 'decisão' or 'bloco' expected!");
            }
        }
    }

    // ======= CONDIÇÃO =======

    public void condicao() {
        validate.openParentesis();
        expRel();
        if (!actualToken.isTokenFound()) {
            validate.closeParentesis();
            actualToken.resetTokenFoundMark();
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
                    handler.handle("Command of type 'while', 'do-while' or 'for' expected!");
                }
            }
        }
    }

    public void _while() {
        _while(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _while(boolean nextToken) {
        validate._while(nextToken);
        condicao();
        bloco();
    }

    public void doWhile() {
        doWhile(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void doWhile(boolean nextToken) {
        validate._do(nextToken);
        bloco();
        validate._while();
        condicao();
        validate.semicolon();
    }

    public void _for() {
        _for(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void _for(boolean nextToken) {
        validate._for(nextToken);
        validate.openParentesis();
        declVar();
        validate.semicolon();
        expRel();
        validate.semicolon();
        expArit();
        validate.closeParentesis();
        bloco();
    }


    // ======= EXPRESSÃO RELACIONAL =======

    public void expRel() {
        expRel(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expRel(boolean nextToken) {
        valorVar(nextToken);
        expRelAux();
    }

    public void valorVar() {
        valorVar(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    private void valorVar(boolean nextToken) {
        try {
            validate.identifier(nextToken);
        } catch (TokenExpectedException e) {
            try {
                valorNum(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                try {
                    validate.charactere(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                } catch (TokenExpectedException e2) {
                    handler.handle("Variable value or identifier expected!");
                }
            }
        }
    }

    public void expRelAux() {
        expRelAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expRelAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            validate.relationalOperators(nextToken);
            expRel();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                try {
                    validate.closeParentesis(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    actualToken.markTokenFound();
                } catch (TokenExpectedException e2) {
                    handler.handle("Relational operator, ';' or ')' expected!");
                }
            }
        }
    }


    // ======= EXPRESSÃO ARITMÉTICA =======

    public void expArit() {
        expArit(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expArit(boolean nextToken) {
        termo(nextToken);
        if (!actualToken.isTokenFound()) {
            expAritAux();
            actualToken.resetTokenFoundMark();
        }
    }

    public void expAritAux() {
        expAritAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expAritAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            validateComposed.plusOrMinus(nextToken);
            expArit();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
            } catch (TokenExpectedException e1) {
                try {
                    validate.closeParentesis(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    actualToken.markTokenFound();
                } catch (TokenExpectedException e2) {
                    handler.handle("Arithmetic operator '+' or '-'; or special character ';' or ')' expected!");
                }
            }
        }
    }

    public void termo() {
        termo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void termo(boolean nextToken) {
        fator(nextToken);
        termoAux();
    }

    public void termoAux() {
        try {
            validateComposed.multOrDiv();
            termo();
        } catch (TokenExpectedException e) {
            try {
                expAritAux(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                handler.handle("Arithmetic operator '*', '/', '+' or '-'; or token ';' or ')' expected!");
            }
        }
    }

    // ======= FATOR =======

    public void fator() {
        fator(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void fator(boolean nextToken) {
        try {
            validate.openParentesis(nextToken);
            expArit();
            validate.closeParentesis();
        } catch (TokenExpectedException e) {
            try {
                validate.identifier(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                try {
                    valorNum(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                } catch (TokenExpectedException e2) {
                    handler.handle("Identifier or variable value of type 'int', 'float' or 'char' expected!");
                }
            }
        }
    }

}
