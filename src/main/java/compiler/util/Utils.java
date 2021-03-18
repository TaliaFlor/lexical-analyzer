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
    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    // letra ::= [a-z]
    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z');
    }

    // char ::= 'letra' | 'dígito'
    public static boolean isChar(char c) {
        return isLetter(c) || isNumber(c);
    }

    // operador_relacional ::= <  |  >  |  <=  |  >=  |  ==  |  !=
    public static boolean isRelationalOperator(char c) {
        return c == '>' || c == '<'  || c == '!';
    }

    // operador_aritmético ::= "+"  |  "-"  |  "*"  |  "/"  |  "="
    public static boolean isAritmeticOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
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
        return RESERVED_WORDS.containsValue(cadeia.trim());
    }

    // .
    public static boolean isDot(char c) {
        return c == '.';
    }

    // _
    public static boolean isUnderline(char c) {
        return c == '_';
    }

    // =
    public static boolean isEquals(char c){
        return c == '=';
    }

    // -
    public static boolean isMinus(char c){
        return c == '-';
    }

    // +
    public static boolean isPlus(char c){
        return c == '+';
    }

    // *
    public static boolean isMult(char c){
        return c == '*';
    }

    // /
    public static boolean isDiv(char c) {
        return c == '/';
    }

    // ,
    public static boolean isComma(char c) {
        return c == ',';
    }

    // ;
    public static boolean isSemicolon(char c) {
        return c == ';';
    }

    // (
    public static boolean isOpenParentesis(char c) {
        return c == '(';
    }

    // )
    public static boolean isCloseParentesis(char c) {
        return c == ')';
    }

    // {
    public static boolean isOpenCurlyBracket(char c) {
        return c == '{';
    }

    // }
    public static boolean isCloseCurlyBracket(char c) {
        return c == '}';
    }

    // <
    public static boolean isLessThan(char c) {
        return c == '<';
    }

    // >
    public static boolean isGreaterThan(char c) {
        return c == '>';
    }

    // >
    public static boolean isDiff(char c) {
        return c == '!';
    }

    public static boolean isOther(char c) {
        return isNonConsumable(c) || isSpecialChar(c) || isAritmeticOperator(c) || isRelationalOperator(c);
    }

}
