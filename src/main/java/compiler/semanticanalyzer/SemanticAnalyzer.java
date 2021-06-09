package compiler.semanticanalyzer;

import compiler.exception.SemanticException;
import compiler.model.TokenType;
import compiler.parser.model.ActualToken;
import compiler.semanticanalyzer.datastructures.Symbol;
import compiler.semanticanalyzer.datastructures.SymbolTable;
import compiler.semanticanalyzer.datastructures.Variable;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SemanticAnalyzer {
    private final SymbolTable symbolTable = new SymbolTable();

    
    private final ActualToken actualToken;
    private int scope;
    private TokenType type;
    @Setter
    private String name;
    private Object value;
    private Symbol symbol;
    private boolean createNewVariable;


    public SemanticAnalyzer() {
        scope = 0;
        createNewVariable = false;

        actualToken = ActualToken.getInstance();
    }


    public void incrementScope() {
        scope++;
    }

    public void decrementScope() {
        scope--;
    }

    public void selectCreateNewVariable() {
        createNewVariable = true;
    }

    public void deselectCreateNewVariable() {
        createNewVariable = false;
    }

    public void setType(TokenType type) {
        if (type != TokenType.INT && type != TokenType.FLOAT && type != TokenType.CHAR)
            throw new IllegalArgumentException("Type must be of an integer or real number; or a charactere");
        this.type = type;
    }

    public void setValue(Object value) {
        setValue(value, false);
    }

    public void setValue(Object value, boolean isFromVariable) {
        if (isFromVariable) {
            verifyVariableIsDeclared();
            this.value = ((Variable) symbolTable.get(name)).getValue();
        } else {
            this.value = value;
        }
    }

    public void verifyVariable() {
        if (createNewVariable)
            createNewVariable();
        else
            verifyVariableIsDeclared();
    }

    public void createNewVariable() {
        if (symbolTable.existsInTheSameOrBiggerScope(scope, name))
            throw new SemanticException("Variable '" + name + "' alredy declared in the same or bigger scope.");
        Symbol symbol = new Variable(scope, type, name, value);
        symbolTable.add(symbol);
        log.trace(symbol.toString());
    }

    public void verifyVariableIsDeclared() {
        if (!symbolTable.existsInTheSameOrBiggerScope(scope, name))
            throw new SemanticException("Variable '" + name + "' not declared. " + actualToken.getToken().lineByColumn());
    }

}
