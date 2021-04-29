package compiler.parser;

import compiler.parser.component.ParserValidation;
import compiler.parser.exception.TokenExpectedException;
import compiler.scanner.Scanner;

public class Parser {

    private static final boolean NEXT_TOKEN_FLAG_TRUE = true;
    private static final boolean NEXT_TOKEN_FLAG_FALSE = false;

    private final ParserValidation validate;


    public Parser(Scanner scanner) {
        validate = new ParserValidation(scanner);
    }


    public void start() {
        main();
    }


    public void main() {
        validate.main();
        validate.openParentesis();
        validate.closeParentesis();
        bloco();
    }

    public void bloco() {
        validate.openCurlyBracket();
        while (validate.conjFirstForComando()) {
            comando();
        }
        validate.closeCurlyBracket(NEXT_TOKEN_FLAG_FALSE);
    }

    public void comando() {
        while (validate.conjFirstForDeclaracao()) {
            declaracao();
        }
        while (validate.conjFirstForIteracao(NEXT_TOKEN_FLAG_FALSE)) {
            iteracao();
        }
        while (validate.conjFirstForDecisao(NEXT_TOKEN_FLAG_FALSE)) {
            decisao();
        }
    }

    public void tipo() {
        tipo(NEXT_TOKEN_FLAG_TRUE);
    }

    public void tipo(boolean nextToken) {
        validate.tipo(nextToken);
    }

    public void declaracao() {
        declaracaoAux();    // + identifier
        atribuicao();       // + ;
    }

    public void declaracaoAux() {
        try {
            tipo();
        } catch (TokenExpectedException e) {
            validate.identifier(NEXT_TOKEN_FLAG_FALSE);
        }
    }

    public void atribuicao() {
        try {
            validate.attribution();
        } catch (TokenExpectedException e) {
            validate.semicolon(NEXT_TOKEN_FLAG_FALSE);
        }
    }

    public void atribuicaoAux() {
        try {
            fator();
        } catch (TokenExpectedException e) {
            try {
                expressaoAritmetica(NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                try {
                    expressaoRelacional(NEXT_TOKEN_FLAG_FALSE);
                } catch (TokenExpectedException e2) {
                    expressaoLogica(NEXT_TOKEN_FLAG_FALSE);
                }
            }
        }
    }

    public void fator() {
        fator(NEXT_TOKEN_FLAG_TRUE);
    }

    public void fator(boolean nextToken) {
        try {
            tipo(nextToken);
        } catch (TokenExpectedException e) {
            validate.identifier(NEXT_TOKEN_FLAG_FALSE);
        }
    }

    public void expressaoAritmetica() {
        expressaoAritmetica(NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoAritmetica(boolean nextToken) {
        termo(nextToken);
        expressaoAritmeticaAux();
    }

    public void termo() {
        termo(NEXT_TOKEN_FLAG_TRUE);
    }

    public void termo(boolean nextToken) {
        fator(nextToken);
        termoAux();
    }

    public void termoAux() {
        try {
            validate.termoAux();
            termo();
        } catch (TokenExpectedException e) {
            expressaoAritmeticaAux(NEXT_TOKEN_FLAG_FALSE);
        }
    }

    public void expressaoAritmeticaAux() {
        expressaoAritmeticaAux(NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoAritmeticaAux(boolean nextToken) {
        try {
            validate.expressaoAritmeticaAux(nextToken);
            expressaoAritmetica();
        } catch (TokenExpectedException e) {
            try {
                validate.semicolon(NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                validate.closeParentesis(NEXT_TOKEN_FLAG_FALSE);
            }
        }
    }

    public void expressaoRelacional() {
        expressaoRelacional(NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoRelacional(boolean nextToken) {
        try {
            fatorOuExpressaoAritmetica(nextToken);
            expressaoRelacionalAux();
        } catch (TokenExpectedException e) {
            try {
                expressaoRelacionalAux(NEXT_TOKEN_FLAG_FALSE);
            } catch (TokenExpectedException e1) {
                validate.relationalOperator(NEXT_TOKEN_FLAG_FALSE);
                expressaoRelacional();
            }
        }
    }

    private void fatorOuExpressaoAritmetica() {
        fatorOuExpressaoAritmetica(NEXT_TOKEN_FLAG_TRUE);
    }

    private void fatorOuExpressaoAritmetica(boolean nextToken) {    //TODO
    }

    public void expressaoRelacionalAux() {
        expressaoRelacionalAux(NEXT_TOKEN_FLAG_TRUE);
    }

    private void expressaoRelacionalAux(boolean nextToken) {
        validate.relationalOperator(nextToken);
        fatorOuExpressaoAritmetica();
    }

    public void expressaoLogica() {
        expressaoLogica(NEXT_TOKEN_FLAG_TRUE);
    }

    public void expressaoLogica(boolean nextToken) {    // TODO
    }

    public void iteracao() {
        try {
            validate._while();
            condicao();
            bloco();
        } catch (TokenExpectedException e) {
            try {
                validate._do(NEXT_TOKEN_FLAG_FALSE);
                bloco();
                validate._while();
                condicao();
                validate.semicolon();
            } catch (TokenExpectedException e1) {
                validate._for(NEXT_TOKEN_FLAG_FALSE);
                validate.openParentesis();
                declaracao();
                validate.semicolon();
                expressaoRelacional();
                validate.semicolon();
                counter();
                validate.closeParentesis();
                bloco();
            }
        }
    }

    private void condicao() {
        validate.openParentesis();
        condicaoAux();
        validate.closeParentesis();
    }

    private void condicaoAux() {    //TODO

    }

    private void counter() {
        validate.identifier();
        counterAux();
        validate.semicolon();
    }

    private void counterAux() {
        try {
            sumOrMinus();
            sumOrMinus(NEXT_TOKEN_FLAG_FALSE);
            condicaoAux2();
        } catch (TokenExpectedException e) {
            validate.attribution(NEXT_TOKEN_FLAG_FALSE);
            validate.identifier();
            validate.arithmeticOperator();
            validate.integer();
        }
    }

    public void sumOrMinus() {
        sumOrMinus(NEXT_TOKEN_FLAG_TRUE);
    }

    private void sumOrMinus(boolean nextToken) {
        validate.sumOrMinus();
    }

    private void condicaoAux2() {
        try {
            sumOrMinus();
        } catch (TokenExpectedException e) {
            validate.attribution(NEXT_TOKEN_FLAG_FALSE);
            validate.integer();
        }
    }

    public void decisao() {
        validate._if();
        condicao();
        bloco();
        _else();
    }

    private void _else() {
        try {
            validate._else();
            elseAux();
        } catch (TokenExpectedException e) {
            validate.closeCurlyBracket(NEXT_TOKEN_FLAG_FALSE);
        }
    }

    private void elseAux() {
        try {
            decisao();
        } catch (TokenExpectedException e) {
            bloco();
        }
    }

}
