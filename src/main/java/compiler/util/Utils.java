package compiler.util;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Map<Integer, String> RESERVED_WORDS = new HashMap<Integer, String>() {{
        put(1, "main");
        put(2, "if");
        put(3, "else");
        put(4, "while");
        put(5, "do");
        put(6, "for");
        put(7, "int");
        put(8, "float");
        put(9, "char");
    }};


    // digito ::= [0-9]
    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    // letra ::= [a-z]
    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z');
    }

    // char ::= 'letra' | 'dígito'
    public static boolean isChar(char c) {
        return isLetter(c) || isDigit(c);
    }

    // operador_relacional ::= <  |  >  |  <=  |  >=  |  ==  |  !=
    public static boolean isRelationalOperator(char c) {
        return c == '>' || c == '<' || c == '=' || c == '!';
    }

    // operador_aritmético ::= "+"  |  "-"  |  "*"  |  "/"  |  "="
    public static boolean isAritmeticOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '=';
    }

    // caracter_especial ::= ")"  |  "("  |  "{"  |  "}"  |  ","  |  ";"
    public static boolean isSpecialChar(char c) {
        return c == ')' || c == '(' || c == '}' || c == '{'
                || c == ',' || c == ';';
    }

    // Caracteres não consumíveis
    public static boolean isNonConsumable(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    //  palavra_reservada ::= main  |  if  |  else  |  while  |  do  |  for  |  int  |  float  |  char
    public static boolean isReservedWord(String cadeia) {
        return RESERVED_WORDS.containsValue(cadeia);
    }

    // .
    public static boolean isDot(char c) {
        return c == '.';
    }

}
