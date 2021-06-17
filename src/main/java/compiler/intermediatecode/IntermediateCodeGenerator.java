package compiler.intermediatecode;

import compiler.model.TokenType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Slf4j
public class IntermediateCodeGenerator {
    private final Stack<String> termos = new Stack<>();
    private final List<String> commands = new ArrayList<>();

    @Setter
    private String identifier;
    private int counter = 0;
    private String condicao;


    public void generateAritmeticOperation(TokenType operator) {
        String lastTerm = termos.pop();
        String penultimateTerm = termos.pop();

        commands.add(String.format("t%s = %s %s %s", counter, penultimateTerm, operator.get(), lastTerm));

        addTermo("t" + counter);
        incrementCounter();
    }

    public void printCommands() {
        if (!commands.isEmpty()) {
            System.out.println("\n----------------------------");
            System.out.println(String.join("\n", commands));
            System.out.println("----------------------------\n");
        }
    }

    public void addTermo(String termo) {
        if (termo == null || termo.isEmpty())
            return;
        termos.push(termo);
    }

    public void addTokenCondicao(String token) {
        condicao += " " + token;
    }

    public void incrementCounter() {
        counter++;
    }

    public void reset() {
        generateAttribution();
        termos.clear();
        counter = 0;
        identifier = "";
        condicao = "";
    }

    private void generateAttribution() {
        if (!termos.isEmpty() && identifier != null && !identifier.isEmpty())
            commands.add(String.format("%s = %s", identifier, termos.get(termos.size() - 1)));
    }

    public void generateWhile() {
        commands.add(String.format("while%s do", condicao));
    }

    public void endWhile() {
        commands.add("endwhile");
    }

}
