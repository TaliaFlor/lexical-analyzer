package compiler.parser;

import compiler.parser.component.ParserValidation;
import compiler.scanner.Scanner;

public class Parser {

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
//        bloco();
    }

    public void bloco() {
        validate.openCurlyBracket();
        while (validate.conjuntoFirstComando()) {
            comando();
        }
        validate.closeCurlyBracket();
    }

    public void comando() {
    }


}
