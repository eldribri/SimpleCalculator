<%@page import="com.eldridge.simplecalculator.CalculatorServlet"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="//code.jquery.com/jquery-1.11.2.min.js"></script>
        <script type="text/javascript" src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
        <script type="text/javascript">
            var operator = '';
            var operand1 = '';
            var operand2 = '';
            var result = '';
            $(document).ready(function () {
                updateDisplay();
                $('.calculatorBtn').click(function (e) {
                    updateInputs($(this).text());
                });
                $(window).keydown(function (e) {
                    alert(e.charCode);
                    updateInputs(String.fromCharCode(e.keyCode || e.which));
                });
            });
            function updateInputs(keyValue) {
                switch (keyValue) {
                    case '0':
                        if (operator.length === 0) {
                            if (operand1.length > 0) {
                                operand1 += keyValue;
                            }
                        } else {
                            if (operand2.length > 0) {
                                operand2 += keyValue;
                            }
                        }
                        break;
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        if (operator.length === 0) {
                            operand1 += keyValue;
                        } else {
                            operand2 += keyValue;
                        }
                        break;
                    case '.':
                        if (operator.length === 0) {
                            if (operand1.indexOf('.') === -1) {
                                operand1 += keyValue;
                            }
                        } else {
                            if (operand2.indexOf('.') === -1) {
                                operand2 += keyValue;
                            }
                        }
                        break;
                    case '-' :
                        if (operand1.length === 0) {
                            operand1 = '-';
                        } else if (operator.length === 0 && operand1 !== '-') {
                            operator = '-';
                        } else if (operator.length !== 0 && operand2.length === 0) {
                            operand2 = '-';
                        }
                        break;
                    case '*' :
                    case '/' :
                    case '+' :
                        if (operand1.length > 0 && operand1 !== '-') {
                            if (operator.length === 0) {
                                operator = keyValue;
                            }
                        }
                        break;
                    case 'Clear':
                        operator = '';
                        operand1 = '';
                        operand2 = '';
                        result = '';
                        break;
                    case "=":
                        if (operand1.length > 0 && operand1 !== '-' && operand2.length > 0 && operand1 !== '-' && operator.length > 0) {
                            $.ajax({
                                url: "<%=CalculatorServlet.getServletPath(request)%>",
                                datatype: "json",
                                data: {
                                    '<%=CalculatorServlet.PARAM_OPERAND1%>': operand1,
                                    '<%=CalculatorServlet.PARAM_OPERAND2%>': operand2,
                                            '<%=CalculatorServlet.PARAM_OPERATOR%>': operator
                                }
                            }).success(function (response) {
                                var json = $.parseJSON(response);
                                if (typeof json.result !== 'undefined') {
                                    result = json.result;
                                } else {
                                    if (typeof json.errors !== 'undefined') {
                                        var errorMessage = '';
                                        $.each(json.errors, function () {
                                            errorMessage += '\n';
                                            errorMessage += this;
                                        });
                                        alert(errorMessage);
                                    }
                                }
                                updateDisplay();
                            });
                        }
                        break;
                }
                updateDisplay();
            }
            function updateDisplay() {
                $('#operand1').html(operand1);
                $('#operator').html(operator);
                $('#operand2').html(operand2);
                $('#result').html(result);
            }
        </script>
        <style type="text/css">
            /*CSS RESET*/
            html, body, div, span, applet, object, iframe,
            h1, h2, h3, h4, h5, h6, p, blockquote, pre,
            a, abbr, acronym, address, big, cite, code,
            del, dfn, em, img, ins, kbd, q, s, samp,
            small, strike, strong, sub, sup, tt, var,
            b, u, i, center,
            dl, dt, dd, ol, ul, li,
            fieldset, form, label, legend,
            table, caption, tbody, tfoot, thead, tr, th, td,
            article, aside, canvas, details, embed, 
            figure, figcaption, footer, header, hgroup, 
            menu, nav, output, ruby, section, summary,
            time, mark, audio, video {
                margin: 0;
                padding: 0;
                border: 0;
                font-size: 100%;
                font: inherit;
                vertical-align: baseline;
            }
            /* HTML5 display-role reset for older browsers */
            article, aside, details, figcaption, figure, 
            footer, header, hgroup, menu, nav, section {
                display: block;
            }
            body {
                line-height: 1;
            }
            ol, ul {
                list-style: none;
            }
            blockquote, q {
                quotes: none;
            }
            blockquote:before, blockquote:after,
            q:before, q:after {
                content: '';
                content: none;
            }
            table {
                border-collapse: collapse;
                border-spacing: 0;
            }
            /*CSS RESET*/
            div#display>div.displayComponent,div#display>span.displayComponent{
                min-height:20px;
                text-align:right;
            }
            div#display>div#operand2{
                border-bottom:1px solid #EFEFEF;
            }
            div#display>div#operator{
                float:left;
            }
            div#display>div#result{
                font-weight:bold;
            }
            table.calculator{
                margin:50px;
                display:inline-block;
                padding:20px;
                -webkit-box-shadow: 2px 2px 7px 0px rgba(50, 50, 50, 0.51);
                -moz-box-shadow:    2px 2px 7px 0px rgba(50, 50, 50, 0.51);
                box-shadow:         2px 2px 7px 0px rgba(50, 50, 50, 0.51);
                border-collapse:separate;
                border-spacing: 8px;
                table-layout: fixed;
            }
            table.calculator td{
                vertical-align:middle;
            }
            table.calculator td>textarea#display{
                margin:0 0 3px 0;
                width:100%;
                text-align: right;
            }
            table.calculator td.calculatorBtn{
                cursor: pointer;
                border:1px solid #EFEFEF;
                color:#B52025;
                font-size:1.5em;
                padding:3px 10px;
                text-align:center;
                background: #fcfff4; /* Old browsers */
                background: -moz-linear-gradient(top,  #fcfff4 0%, #e9e9ce 100%); /* FF3.6+ */
                background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#fcfff4), color-stop(100%,#e9e9ce)); /* Chrome,Safari4+ */
                background: -webkit-linear-gradient(top,  #fcfff4 0%,#e9e9ce 100%); /* Chrome10+,Safari5.1+ */
                background: -o-linear-gradient(top,  #fcfff4 0%,#e9e9ce 100%); /* Opera 11.10+ */
                background: -ms-linear-gradient(top,  #fcfff4 0%,#e9e9ce 100%); /* IE10+ */
                background: linear-gradient(to bottom,  #fcfff4 0%,#e9e9ce 100%); /* W3C */
                filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#fcfff4', endColorstr='#e9e9ce',GradientType=0 ); /* IE6-9 */
            }
            table.calculator td.calculatorBtn:active{
                border:1px solid black;
            }
            table.calculator td.calculatorBtn:active, table.calculator td.calculatorBtn:hover{
                background: #e6e8de; /* Old browsers */
                background: -moz-linear-gradient(top,  #e6e8de 0%, #cfd1ba 100%); /* FF3.6+ */
                background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#e6e8de), color-stop(100%,#cfd1ba)); /* Chrome,Safari4+ */
                background: -webkit-linear-gradient(top,  #e6e8de 0%,#cfd1ba 100%); /* Chrome10+,Safari5.1+ */
                background: -o-linear-gradient(top,  #e6e8de 0%,#cfd1ba 100%); /* Opera 11.10+ */
                background: -ms-linear-gradient(top,  #e6e8de 0%,#cfd1ba 100%); /* IE10+ */
                background: linear-gradient(to bottom,  #e6e8de 0%,#cfd1ba 100%); /* W3C */
                filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#e6e8de', endColorstr='#cfd1ba',GradientType=0 ); /* IE6-9 */
            }
        </style>
        <title>Simple Calculator</title>
    </head>
    <body>
        <table class="calculator">
            <thead></thead>
            <tbody>
                <tr>
                    <td colspan="5">
                        <div id="display">
                            <div class="displayComponent" id="operand1"></div>
                            <div class="displayComponent" id="operator"></div>
                            <div class="displayComponent" id="operand2"></div>
                            <div class="displayComponent" id="result"></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="calculatorBtn">7</td>
                    <td class="calculatorBtn">8</td>
                    <td class="calculatorBtn">9</td>
                    <td class="calculatorBtn">/</td>
                    <td rowspan="2" class="calculatorBtn">Clear</td>
                </tr>
                <tr>
                    <td class="calculatorBtn">4</td>
                    <td class="calculatorBtn">5</td>
                    <td class="calculatorBtn">6</td>
                    <td class="calculatorBtn">*</td>
                </tr>
                <tr>
                    <td class="calculatorBtn">1</td>
                    <td class="calculatorBtn">2</td>
                    <td class="calculatorBtn">3</td>
                    <td class="calculatorBtn">-</td>
                    <td rowspan="2" class="calculatorBtn">=</td>
                </tr>
                <tr>
                    <td colspan="2" class="calculatorBtn">0</td>
                    <td class="calculatorBtn">.</td>
                    <td class="calculatorBtn">+</td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
