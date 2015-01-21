package com.eldridge.simplecalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * A simple calculation that will validate inputs and perform a calculation if
 * inputs are valid; supported operations include addition, subtraction,
 * multiplication and division.
 *
 * @author Brian
 */
public final class SimpleCalculation {

    private boolean validInputs = true;
    private BigDecimal operand1 = null;
    private BigDecimal operand2 = null;
    private Operator operator = null;
    private ArrayList<String> validationErrors = null;
    public static final String ERROR_OPERAND1_NOT_PROVIDED = "Operand1 must be specified.";
    public static final String ERROR_OPERAND2_NOT_PROVIDED = "Operand2 must be specified.";
    public static final String ERROR_OPERATOR_NOT_PROVIDED = "Operator must be specified.";
    public static final String ERROR_INCORRECT_FORMAT = "All operands must be in the proper format (#.## or -#.##).";
    public static final String ERROR_OPERATION_NOT_SUPPORTED = "Operation not supported, please specify one of the following operands +, -, *, or /.";
    public static final String ERROR_DIVIDE_BY_ZERO = "Cannot divide by zero.";

    private SimpleCalculation() {
    }

    public SimpleCalculation(String operand1, String operand2, String symbol) {
        if (operand1 == null || operand1.isEmpty()) {
            this.setValidInputs(false);
            this.addValidationError(ERROR_OPERAND1_NOT_PROVIDED);
        } else {
            try {
                this.operand1 = new BigDecimal(operand1);
            } catch (NumberFormatException e) {
                this.setValidInputs(false);
                this.addValidationError(ERROR_INCORRECT_FORMAT);
            }
        }

        if (operand2 == null || operand2.isEmpty()) {
            this.setValidInputs(false);
            this.addValidationError(ERROR_OPERAND2_NOT_PROVIDED);
        } else {
            try {
                this.operand2 = new BigDecimal(operand2);
            } catch (NumberFormatException e) {
                this.setValidInputs(false);
                this.addValidationError(ERROR_INCORRECT_FORMAT);
            }
        }

        if (symbol == null || symbol.isEmpty()) {
            this.setValidInputs(false);
            this.addValidationError(ERROR_OPERATOR_NOT_PROVIDED);
        } else {
            Operator op = Operator.getOperatorBySymbol(symbol);
            if (op != null) {
                this.operator = op;
            } else {
                this.setValidInputs(false);
                this.addValidationError(ERROR_OPERATION_NOT_SUPPORTED);
            }
        }
        this.validateInputs();
    }

    /**
     * Validate any special cases, assumes all inputs have been previously
     * validated for null, empty, and proper format. Iff a combination of inputs
     * is found to be invalid, the calculation is marked as having invalid input
     * messages and an appropriate error message is set.
     */
    private void validateInputs() {
        if (this.isValidInputs()) {
            switch (this.getOperator()) {
                case DIVIDE:
                    if (this.getOperand2().equals(BigDecimal.ZERO)) {
                        this.setValidInputs(false);
                        this.addValidationError(ERROR_DIVIDE_BY_ZERO);
                    }
                    break;
            }
        }
    }

    /**
     * Performs the specified operation on the two operands.
     *
     * @return
     */
    public BigDecimal calculate() {
        BigDecimal result = null;
        switch (this.getOperator()) {
            case ADD:
                result = this.getOperand1().add(this.getOperand2());
                break;
            case SUBTRACT:
                result = this.getOperand1().subtract(this.getOperand2());
                break;
            case MULTIPLY:
                result = this.getOperand1().multiply(this.getOperand2());
                break;
            case DIVIDE:
                result = this.getOperand1().divide(this.getOperand2(), 20, RoundingMode.HALF_EVEN);
                break;
        }
        return result;
    }

    /**
     * @return the operand1
     */
    private BigDecimal getOperand1() {
        return operand1;
    }

    /**
     * @return the operand2
     */
    private BigDecimal getOperand2() {
        return operand2;
    }

    /**
     * @return the operator
     */
    private Operator getOperator() {
        return operator;
    }

    /**
     * @return the validInputs
     */
    public boolean isValidInputs() {
        return validInputs;
    }

    /**
     * @param validInputs the validInputs to set
     */
    private void setValidInputs(boolean validInputs) {
        this.validInputs = validInputs;
    }

    /**
     * @return the validationErrors
     */
    public ArrayList<String> getValidationErrors() {
        if (this.validationErrors == null) {
            validationErrors = new ArrayList<String>();
        }
        return validationErrors;
    }

    private void addValidationError(String error) {
        if (!this.getValidationErrors().contains(error)) {
            this.getValidationErrors().add(error);
        }
    }
}
