package compiler.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Token {
    @NonNull
    private TokenType type;
    @NonNull
    private String text;
    private int line;
    private int column;

    @Override
    public String toString() {
        return type + " -> " + text;
    }


    public String lineByColumn() {      // Line 3 and column 4 (3:4).
        return "Line " + line + " and column " + column + " (" + line + ":" + column + ").";
    }

}
