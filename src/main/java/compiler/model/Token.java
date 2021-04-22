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

}
