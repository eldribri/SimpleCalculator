package com.eldridge.simplecalculator;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases to ensure proper validation and calculations of the
 * SimpleCalculation.class
 *
 * @author Brian
 */
public class SimpleCalculationTest {

    @Test
    public void testValidationForNullOperator() {
        SimpleCalculation calculation = new SimpleCalculation("2", "1", null);
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating a null operator should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating a null operator returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating a null operator returned an incorrect error message", SimpleCalculation.ERROR_OPERATOR_NOT_PROVIDED, error);
        }
    }

    @Test
    public void testValidationForEmptyOperator() {
        SimpleCalculation calculation = new SimpleCalculation("2", "1", "");
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating an empty operator should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating an empty operator returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating an empty operator returned an incorrect error message", SimpleCalculation.ERROR_OPERATOR_NOT_PROVIDED, error);
        }
    }

    @Test
    public void testValidationForUnsupportedOperator() {
        SimpleCalculation calculation = new SimpleCalculation("2", "1", "%");
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating an unsupported operator should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating an unsupported operator returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating an unsupported operator returned an incorrect error message", SimpleCalculation.ERROR_OPERATION_NOT_SUPPORTED, error);
        }
    }

    @Test
    public void testValidationForNullOperand1() {
        SimpleCalculation calculation = new SimpleCalculation(null, "1", Operator.ADD.getSymbol());
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating a null operand1 should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating a null operand1 returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating a null operand1 returned an incorrect error message", SimpleCalculation.ERROR_OPERAND1_NOT_PROVIDED, error);
        }
    }

    @Test
    public void testValidationForEmptyOperand1() {
        SimpleCalculation calculation = new SimpleCalculation("", "1", Operator.ADD.getSymbol());
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating an empty operand1 should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating an empty operand1 returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating an empty operand1 returned an incorrect error message", SimpleCalculation.ERROR_OPERAND1_NOT_PROVIDED, error);
        }
    }

    @Test
    public void testIncorrectFormattedOperand1() {
        SimpleCalculation calculation = new SimpleCalculation("1.1.1", "2", Operator.ADD.getSymbol());
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating an incorrectly formatted operand1 should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating an incorrectly formatted operand1 returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating an incorrectly formatted operand1 returned an incorrect error message", SimpleCalculation.ERROR_INCORRECT_FORMAT, error);
        }
    }

    @Test
    public void testValidationForNullOperand2() {
        SimpleCalculation calculation = new SimpleCalculation("2", null, Operator.ADD.getSymbol());
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating a null operand2 should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating a null operand2 returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating a null operand2 returned an incorrect error message", SimpleCalculation.ERROR_OPERAND2_NOT_PROVIDED, error);
        }
    }

    @Test
    public void testValidationForEmptyOperand2() {
        SimpleCalculation calculation = new SimpleCalculation("2", "", Operator.ADD.getSymbol());
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating an empty operand2 should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating an empty operand2 returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating an empty operand2 returned an incorrect error message", SimpleCalculation.ERROR_OPERAND2_NOT_PROVIDED, error);
        }
    }

    @Test
    public void testIncorrectFormattedOperand2() {
        SimpleCalculation calculation = new SimpleCalculation("2", "1.1.1", Operator.ADD.getSymbol());
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Validating an incorrectly formatted operand2 should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Validating an incorrectly formatted operand2 returned too many errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating an incorrectly formatted operand2 returned an incorrect error message", SimpleCalculation.ERROR_INCORRECT_FORMAT, error);
        }
    }

    @Test
    public void testMultipleNullInputs() {
        SimpleCalculation calculation = new SimpleCalculation(null, null, null);
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Multiple null inputs should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Multiple null inputs yielded an incorrect number of validation errors", 3, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            if (!error.equals(SimpleCalculation.ERROR_OPERAND1_NOT_PROVIDED)
                    && !error.equals(SimpleCalculation.ERROR_OPERAND2_NOT_PROVIDED)
                    && !error.equals(SimpleCalculation.ERROR_OPERATOR_NOT_PROVIDED)) {
                Assert.fail("Multiple null inputs yeilds incorrect error message");
            }
        }
    }

    @Test
    public void testMultipleEmptyInputs() {
        SimpleCalculation calculation = new SimpleCalculation("", "", "");
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Multiple empty inputs should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Multiple empty inputs yielded an incorrect number of validation errors", 3, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            if (!error.equals(SimpleCalculation.ERROR_OPERAND1_NOT_PROVIDED)
                    && !error.equals(SimpleCalculation.ERROR_OPERAND2_NOT_PROVIDED)
                    && !error.equals(SimpleCalculation.ERROR_OPERATOR_NOT_PROVIDED)) {
                Assert.fail("Multiple empty inputs yeilds incorrect error message");
            }
        }
    }

    @Test
    public void testDivideByZero() {
        SimpleCalculation calculation = new SimpleCalculation("1", "0", Operator.DIVIDE.getSymbol());
        Assert.assertFalse("Incorrect validation result", calculation.isValidInputs());
        Assert.assertNotNull("Division by zero should yield validation errors", calculation.getValidationErrors());
        Assert.assertEquals("Division by zero yields an incorrect number of validation errors", 1, calculation.getValidationErrors().size());
        for (String error : calculation.getValidationErrors()) {
            Assert.assertEquals("Validating a divide by zero scenario yields an incorrect error message", SimpleCalculation.ERROR_DIVIDE_BY_ZERO, error);
        }
    }

    @Test
    public void testAdditionWithTwoPositiveInputs() {
        SimpleCalculation calculation = new SimpleCalculation("1", "2", Operator.ADD.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Addition of two positive inputs yields incorrect result", new BigDecimal(3), calculation.calculate());
    }

    @Test
    public void testAdditionWithTwoNegativeInputs() {
        SimpleCalculation calculation = new SimpleCalculation("-1", "-2", Operator.ADD.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Addition of two negative inputs yields incorrect result", new BigDecimal(-3), calculation.calculate());
    }

    @Test
    public void testSubtractionWithTwoPositiveInputs() {
        SimpleCalculation calculation = new SimpleCalculation("1", "2", Operator.SUBTRACT.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Subtration of two positive inputs yields incorrect result", new BigDecimal(-1), calculation.calculate());
    }

    @Test
    public void testSubtractionWithTwoNegativeInputs() {
        SimpleCalculation calculation = new SimpleCalculation("-1", "-2", Operator.SUBTRACT.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Subtraction of two negative inputs yields incorrect result", new BigDecimal(1), calculation.calculate());
    }

    @Test
    public void testMultiplicationWithTwoPositiveInputs() {
        SimpleCalculation calculation = new SimpleCalculation("3", "2", Operator.MULTIPLY.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Multiplication of two positive inputs yields incorrect result", new BigDecimal(6), calculation.calculate());
    }

    @Test
    public void testMultiplicationWithTwoNegativeInputs() {
        SimpleCalculation calculation = new SimpleCalculation("-1", "-2", Operator.MULTIPLY.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Multiplication of two negative inputs yields incorrect result", new BigDecimal(2), calculation.calculate());
    }

    @Test
    public void testDivisionWithTwoPositiveInputs() {
        SimpleCalculation calculation = new SimpleCalculation("6", "3", Operator.DIVIDE.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Division of two positive inputs yields incorrect result", 2.0, calculation.calculate().doubleValue(), 0);
    }

    @Test
    public void testDivisionWithTwoNegativeInputs() {
        SimpleCalculation calculation = new SimpleCalculation("-4", "-2", Operator.DIVIDE.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Division of two negative inputs yields incorrect result", 2.0, calculation.calculate().doubleValue(), 0);
    }

    @Test
    public void testDivisonWithNonTerminatingResult() {
        SimpleCalculation calculation = new SimpleCalculation("1", "3", Operator.DIVIDE.getSymbol());
        Assert.assertTrue("Incorrect validation result", calculation.isValidInputs());
        Assert.assertEquals("Division with non terminating result yields incorrect result", 0.33, calculation.calculate().doubleValue(), 0.01);
    }
}
