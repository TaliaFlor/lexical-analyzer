package compiler.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Token {
    @NonNull
    private Type type;
    @NonNull
    private TokenType tokenType;
    @NonNull
    private String text;
    private int line;
    private int column;

    @Override
    public String toString() {
        return type + " [" + tokenType + "] -> " + text;
    }


    public String lineByColumn() {      // Line 3 and column 4 (3:4).
        return "Line " + line + " and column " + column + " (" + line + ":" + column + ").";
    }

}
