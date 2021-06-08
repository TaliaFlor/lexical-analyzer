package compiler.semanticanalyzer.datastructures;

import lombok.Data;

@Data
public abstract class Symbol {
    protected final int scope;
    protected final String name;
}
