package ro.andreimatei.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import ro.andreimatei.querydsl.exceptions.QueryDSLPredicateBuildException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:andrei@andreimatei.ro">Andrei-Vlad Matei</a>
 */
public final class QueryDSLPredicatesBuilder<T> {
    private final List<SearchCriteria> params;
    private final Class<T> entityClass;

    public QueryDSLPredicatesBuilder(Class<T> entityClass) {
        this.params = new ArrayList<>();
        this.entityClass = entityClass;
    }

    public QueryDSLPredicatesBuilder with(final String key, final String operation, final Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public QueryDSLPredicatesBuilder with(final String search) {
        Pattern pattern = Pattern.compile("([^,]+?)(:|!|<|>)([^,]+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            this.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return this;
    }

    public BooleanExpression build() throws QueryDSLPredicateBuildException {
        if (params.size() == 0) {
            return null;
        }

        final List<BooleanExpression> predicates = new ArrayList<>();
        QueryDSLPredicate<T> predicate;
        for (final SearchCriteria param : params) {
            predicate = new QueryDSLPredicate<>(param);
            final BooleanExpression exp = predicate.getPredicate(entityClass);
            if (exp != null) {
                predicates.add(exp);
            }
        }

        BooleanExpression result = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            result = result.and(predicates.get(i));
        }
        return result;
    }
}