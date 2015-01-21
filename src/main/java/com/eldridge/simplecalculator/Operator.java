package com.eldridge.simplecalculator;

/**
 * An enumerated list of supported operators and their corresponding symbol.
 *
 * @author Brian
 */
public enum Operator {

    ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/");
    private final String symbol;

    private Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Fetches an operator with the specified symbol.
     *
     * @param symbol
     * @return
     */
    public static Operator getOperatorBySymbol(String symbol) {
        Operator operator = null;
        for (Operator op : Operator.values()) {
            if (op.getSymbol().equals(symbol)) {
                operator = op;
                break;
            }
        }
        return operator;
    }
}
