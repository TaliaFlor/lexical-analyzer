package compiler.parser;

import compiler.component.ExceptionHandler;
import compiler.interfaces.Analyser;
import compiler.intermediatecode.IntermediateCodeGenerator;
import compiler.model.TokenType;
import compiler.parser.exception.TokenExpectedException;
import compiler.parser.model.ActualToken;
import compiler.parser.validation.ComposedValidation;
import compiler.parser.validation.ConjuntoFirstValidation;
import compiler.parser.validation.TokenValidation;
import compiler.scanner.Scanner;
import compiler.semanticanalyzer.SemanticAnalyzer;

public class Parser implements Analyser {
    private final TokenValidation validate;
    private final ConjuntoFirstValidation validateConjFirst;
    private final ComposedValidation validateComposed;
    private final ExceptionHandler handler;
    private final ActualToken actualToken;
    private final SemanticAnalyzer semanticAnalyzer;
    private final IntermediateCodeGenerator codeGenerator;

    private String identifier;


    public Parser(Scanner scanner) {
        actualToken = ActualToken.getInstance(scanner);
        validate = new TokenValidation();
        validateConjFirst = new ConjuntoFirstValidation();
        validateComposed = new ComposedValidation();
        handler = new ExceptionHandler();
        semanticAnalyzer = SemanticAnalyzer.getInstance();
        codeGenerator = new IntermediateCodeGenerator();
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

        codeGenerator.printCommands();
    }

    // ======= BLOCO =======

    public void bloco() {
        validate.openCurlyBracket();

        semanticAnalyzer.incrementScope();

        while (validateConjFirst.comando())
            comando(ActualToken.NEXT_TOKEN_FLAG_FALSE);
        if (!actualToken.isTokenFound()) {
            validate.closeCurlyBracket(ActualToken.NEXT_TOKEN_FLAG_FALSE);
            actualToken.resetTokenFoundMark();
        }

        semanticAnalyzer.decrementScope();
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
            } catch (TokenExpectedException e1) {
                try {
                    decisao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                } catch (TokenExpectedException e2) {
                    try {
                        iteracao(ActualToken.NEXT_TOKEN_FLAG_FALSE);
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
        codeGenerator.reset();
    }

    // ======= TIPO =======

    public void tipoVar() {
        tipoVar(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void tipoVar(boolean nextToken) {
        try {
            validateComposed.tipo(nextToken);
            semanticAnalyzer.setType(actualToken.getTokenType());
        } catch (TokenExpectedException e) {
            throw new TokenExpectedException("Reserved word 'int', 'float' or 'char' expected.");
        }
    }

    // ======= DECLARAÇÃO DE VARIÁVEL =======

    public void declVar() {
        declVar(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void declVar(boolean nextToken) {
        tipoVar(nextToken);

        semanticAnalyzer.selectCreateNewVariable();

        acessoVar();

        semanticAnalyzer.deselectCreateNewVariable();
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

        semanticAnalyzer.setName(actualToken.getToken().getValue());
        codeGenerator.setIdentifier(actualToken.getToken().getValue());

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
            double value = expArit(nextToken);
            semanticAnalyzer.setValue(value);
        } catch (TokenExpectedException e) {
            try {
                validate.charactere(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                semanticAnalyzer.setValue(actualToken.getToken().getValue());
            } catch (TokenExpectedException e1) {
                handler.handle("Variable value or aritmetic expression expected!");
            }
        }
    }

    // ======= VALOR NUMÉRICO =======

    public double valorNum() {
        return valorNum(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public double valorNum(boolean nextToken) {
        double value = 0;
        try {
            validate.integerNumber(nextToken);
            value = Integer.parseInt(actualToken.getToken().getValue());
        } catch (TokenExpectedException e) {
            try {
                validate.realNumber(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                value = Double.parseDouble(actualToken.getToken().getValue());
            } catch (TokenExpectedException e1) {
                handler.handle("Number expected!");
            }
        }
        return value;
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
            bloco();
        } catch (TokenExpectedException e) {
            try {
                decisao();
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
                } catch (TokenExpectedException e2) {
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
        codeGenerator.generateWhile();
        bloco();
        codeGenerator.addGotoWhile();
        codeGenerator.endWhile();
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

        double value = expArit();
        semanticAnalyzer.setValue(value);

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
            semanticAnalyzer.setValue(actualToken.getToken().getValue());
        } catch (TokenExpectedException e) {
            try {
                valorNum(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                semanticAnalyzer.setValue(actualToken.getToken().getValue());
            } catch (TokenExpectedException e1) {
                try {
                    validate.charactere(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    semanticAnalyzer.setValue(actualToken.getToken().getValue());
                } catch (TokenExpectedException e2) {
                    handler.handle("Variable value or identifier expected!");
                }
            }
        }
        codeGenerator.addTokenCondicao(actualToken.getToken().getValue());
    }

    public void expRelAux() {
        expRelAux(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public void expRelAux(boolean nextToken) {
        actualToken.markTokenNotFound();
        try {
            validate.relationalOperators(nextToken);
            codeGenerator.addTokenCondicao(actualToken.getToken().getValue(), true);
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

    public double expArit() {
        return expArit(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public double expArit(boolean nextToken) {
        Double result = null;
        double num1 = termo(nextToken);
        if (!actualToken.isTokenFound()) {
            result = expAritAux(num1);
            actualToken.resetTokenFoundMark();
        }
        return result != null ? result : num1;
    }

    public double expAritAux() {
        return expAritAux(ActualToken.NEXT_TOKEN_FLAG_TRUE, null);
    }

    public double expAritAux(double num1) {
        return expAritAux(ActualToken.NEXT_TOKEN_FLAG_TRUE, num1);
    }

    public double expAritAux(boolean nextToken, Double num1) {
        double result = 0;
        actualToken.markTokenNotFound();
        try {
            validateComposed.plusOrMinus(nextToken);
            boolean isSum = actualToken.getTokenType() == TokenType.PLUS;

            TokenType operator;
            double num2 = expArit();
            if (isSum) {
                result = num1 + num2;
                operator = TokenType.PLUS;
            } else {
                result = num1 - num2;
                operator = TokenType.MINUS;
            }

            codeGenerator.generateAritmeticOperation(operator);
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                actualToken.markTokenFound();
                result = num1;
            } catch (TokenExpectedException e1) {
                try {
                    validate.closeParentesis(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    actualToken.markTokenFound();
                    result = num1;
                } catch (TokenExpectedException e2) {
                    handler.handle("Arithmetic operator '+' or '-'; or special character ';' or ')' expected!");
                }
            }
        }
        return result;
    }

    public double termo() {
        return termo(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public double termo(boolean nextToken) {
        double num1 = fator(nextToken);
        return termoAux(num1);
    }

    public double termoAux(double num1) {
        double result = 0;
        try {
            validateComposed.multOrDiv();
            boolean isMult = actualToken.getTokenType() == TokenType.MULTIPLICATION;

            TokenType operator;
            double num2 = termo();
            if (isMult) {
                result = num1 * num2;
                operator = TokenType.MULTIPLICATION;
            } else {
                result = num1 / num2;
                operator = TokenType.DIVISION;
            }

            codeGenerator.generateAritmeticOperation(operator);
        } catch (TokenExpectedException e) {
            try {
                result = expAritAux(ActualToken.NEXT_TOKEN_FLAG_FALSE, num1);
            } catch (TokenExpectedException e1) {
                handler.handle("Arithmetic operator '*', '/', '+' or '-'; or token ';' or ')' expected!");
            }
        }
        return result;
    }

    // ======= FATOR =======

    public double fator() {
        return fator(ActualToken.NEXT_TOKEN_FLAG_TRUE);
    }

    public double fator(boolean nextToken) {
        double num1 = 0;
        String identifier = "";
        try {
            validate.openParentesis(nextToken);
            num1 = expArit();
            validate.closeParentesis();
        } catch (TokenExpectedException e) {
            try {
                validate.identifier(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                num1 = semanticAnalyzer.getValue(actualToken.getToken().getValue());
                identifier = actualToken.getToken().getValue();
            } catch (TokenExpectedException e1) {
                try {
                    num1 = valorNum(ActualToken.NEXT_TOKEN_FLAG_FALSE);
                    identifier = String.valueOf(num1);
                } catch (TokenExpectedException e2) {
                    handler.handle("Identifier or variable value of type 'int', 'float' or 'char' expected!");
                }
            }
        }

        codeGenerator.addTermo(identifier);
        return num1;
    }

}
