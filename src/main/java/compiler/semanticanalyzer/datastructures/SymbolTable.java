package compiler.semanticanalyzer.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Symbol> map = new HashMap<>();


    public void add(Symbol symbol) {
        map.put(symbol.getName(), symbol);
    }

    public boolean existsInTheSameOrBiggerScope(int scope, String symbolName) {
        return exists(symbolName) && scopeIsTheSameOrBigger(scope, symbolName);
    }

    private boolean scopeIsTheSameOrBigger(int scope, String symbolName) {
        return get(symbolName).getScope() >= scope;
    }

    public boolean exists(String symbolName) {
        return map.get(symbolName) != null;
    }

    public Symbol get(String symbolName) {
        return map.get(symbolName);
    }

    public List<Symbol> getAll() {
        return new ArrayList<>(map.values());
    }

}
