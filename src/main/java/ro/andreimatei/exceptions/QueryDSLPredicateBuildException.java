package ro.andreimatei.exceptions;

/**
 * @author Andrei-Vlad Matei <andrei@andreimatei.ro>
 */
public class QueryDSLPredicateBuildException extends Exception {

    private String parameterName;
    private String actualValue;
    private String expectedValue;

    /**
     * @param parameterName the parameter name which had the invalid value
     * @param actualValue the value that was passed in for this parameter
     * @param expectedValue the list of proper expected valid values for this parameter
     */
    public QueryDSLPredicateBuildException(String parameterName, String actualValue, String expectedValue) {
        super("Bad Request. Parameter " + parameterName + " has value: " + actualValue + ". But expected value(s): "
                + expectedValue);
        this.parameterName = parameterName;
        this.actualValue = actualValue;
        this.expectedValue = expectedValue;
    }

    public String getErrorMessage() {
         return "Bad Request. Parameter " + parameterName + " has value: " + actualValue + ". But expected value(s): " + expectedValue;
    }
}
