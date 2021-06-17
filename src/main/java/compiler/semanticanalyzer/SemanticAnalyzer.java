package compiler.semanticanalyzer;

import compiler.exception.SemanticException;
import compiler.model.TokenType;
import compiler.parser.model.ActualToken;
import compiler.semanticanalyzer.datastructures.Symbol;
import compiler.semanticanalyzer.datastructures.SymbolTable;
import compiler.semanticanalyzer.datastructures.Variable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SemanticAnalyzer {
    private static SemanticAnalyzer instance = null;
    private final SymbolTable symbolTable = new SymbolTable();
    private final ActualToken actualToken;
    private int scope;
    private TokenType type;
    private String name;
    private boolean createNewVariable;


    private SemanticAnalyzer() {
        scope = 0;
        createNewVariable = false;

        actualToken = ActualToken.getInstance();
    }

    public static SemanticAnalyzer getInstance() {
        if (instance == null)
            instance = new SemanticAnalyzer();
        return instance;
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

    public void verifyVariable() {
        if (createNewVariable)
            createNewVariable();
        else
            verifyVariableIsDeclared();
    }

    public void createNewVariable() {
        if (symbolTable.existsInTheSameOrBiggerScope(scope, name))
            throw new SemanticException("Variable '" + name + "' alredy declared in the same or bigger scope.");
        Symbol symbol = new Variable(scope, type, name);
        symbolTable.add(symbol);
    }

    public void setName(String name) {
        this.name = name;
        verifyVariable();
    }

    public double getValue(String name) {
        verifyVariableIsDeclared();
        return (double) getVariable(name).getValue();
    }

    public void setValue(Object value) {
        verifyVariableIsDeclared();
        if (getVariable(name).getValue() != null)
            return;

        getVariable(name).setValue(value);
        log.trace(String.valueOf(getVariable(name)));
    }

    public Variable getVariable(String name) {
        return (Variable) symbolTable.get(name);
    }

    public void verifyVariableIsDeclared() {
        if (!symbolTable.existsInTheSameOrBiggerScope(scope, name))
            throw new SemanticException("Variable '" + name + "' not declared. " + actualToken.getToken().lineByColumn());
    }

}
