package compiler.parser;

import compiler.parser.component.ParserValidation;
import compiler.scanner.Scanner;

public class Parser {

    private static final boolean NEXT_TOKEN_FLAG = false;

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
        validate.closeCurlyBracket(NEXT_TOKEN_FLAG);
    }

    public void comando() {
        while (validate.conjFirstForDeclVar()) {
            declVar();
        }
        while (validate.conjFirstForAtribuicao(NEXT_TOKEN_FLAG)) {
            atribuicao();
        }
        while (validate.conjFirstForIteracao(NEXT_TOKEN_FLAG)) {
            iteracao();
        }
        while (validate.conjFirstForDecisao(NEXT_TOKEN_FLAG)) {
            decisao();
        }
    }

    public void declVar() {
        tipo();
        declVarAux();
        validate.semicolon();
    }

    public void tipo() {
        validate.tipo();
    }

    public void declVarAux() {
        validate.declVarAux();
    }

    public void atribuicao() {
    }

    public void iteracao() {
    }

    public void decisao() {
    }

}
