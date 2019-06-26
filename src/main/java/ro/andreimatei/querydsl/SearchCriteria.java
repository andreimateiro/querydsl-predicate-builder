package ro.andreimatei.querydsl;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCriteria that = (SearchCriteria) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(operation, that.operation) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, operation, value);
    }
}
