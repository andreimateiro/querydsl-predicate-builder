package ro.andreimatei.querydsl;

/**
 * @author Andrei-Vlad Matei <andrei@andreimatei.ro>
 */
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(final String key, final String operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }
}
