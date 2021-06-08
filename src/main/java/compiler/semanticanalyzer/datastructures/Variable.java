package compiler.semanticanalyzer.datastructures;

import compiler.model.TokenType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Variable extends Symbol {
    private TokenType type;
    private Object value;

    public Variable(int scope, TokenType type, String name, Object value) {
        super(scope, name);
        this.type = type;
        this.value = value;
    }
}
