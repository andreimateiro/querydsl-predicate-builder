package ro.andreimatei.querydsl;

/**
 * @author <a href="mailto:andrei@andreimatei.ro">Andrei-Vlad Matei</a>
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
