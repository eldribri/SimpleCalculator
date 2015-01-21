package com.eldridge.simplecalculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * Servlet to handle a calculate action
 *
 * @author Brian
 */
public class CalculatorServlet extends HttpServlet {

    public static final String PARAM_OPERATOR = "operator";
    public static final String PARAM_OPERAND1 = "operand1";
    public static final String PARAM_OPERAND2 = "operand2";
    private static final String DECIMAL_FORMAT = "#.########";
    private static final String SERVLET_PATH = "/action/calculate";

    /**
     * Performs a calculation for both HTTP/GET and HTTP/Post and returns the
     * result if there are no problems with the inputs or error messages if an
     * input is in some way invalid.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operator = request.getParameter(PARAM_OPERATOR);
        String operand1 = request.getParameter(PARAM_OPERAND1);
        String operand2 = request.getParameter(PARAM_OPERAND2);

        SimpleCalculation calculation = new SimpleCalculation(operand1, operand2, operator);
        if (calculation.isValidInputs()) {
            BigDecimal result = calculation.calculate();
            DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
            JSONObject data = new JSONObject();
            data.put("result", df.format(result));
            data.write(response.getWriter());
        } else {
            JSONObject data = new JSONObject();
            data.put("errors", calculation.getValidationErrors());
            data.write(response.getWriter());
        }
    }

    /**
     * Processes HTTP GET requests
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Processes HTTP Post requests
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Returns the context specific servlet mapping
     *
     * @param request
     * @return
     */
    public static String getServletPath(HttpServletRequest request) {
        return request.getContextPath() + SERVLET_PATH;
    }
}
