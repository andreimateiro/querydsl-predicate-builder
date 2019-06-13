package ro.andreimatei.querydsl;

import com.querydsl.core.types.dsl.*;
import ro.andreimatei.exceptions.QueryDSLPredicateBuildException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

/**
 * @author Andrei-Vlad Matei <andrei@andreimatei.ro>
 */
public class QueryDSLPredicate<T> {

    private SearchCriteria criteria;

    public QueryDSLPredicate(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public BooleanExpression getPredicate(Class<T> entityClass) throws QueryDSLPredicateBuildException {

        final Class<?> typeByPath = getLastTypeByPath(entityClass, criteria.getKey());
        final PathBuilder<T> entityPath = new PathBuilder<>(entityClass, entityClass.getSimpleName());

        if (typeByPath.equals(String.class)) {
            final StringPath path = entityPath.getString(criteria.getKey());
            switch (criteria.getOperation()) {
                case ":":
                    if (criteria.getValue().toString().equalsIgnoreCase("null")) {
                        return path.isNull();
                    } else {
                        return path.equalsIgnoreCase(criteria.getValue().toString());
                    }
                case "!":
                    return path.notEqualsIgnoreCase(criteria.getValue().toString());
                default:
                    throw new QueryDSLPredicateBuildException(criteria.getKey() + "'s search operator", criteria.getOperation(), "Valid Search Operator");
            }
        } else if (typeByPath.equals(long.class)) {
            if (!isLong(criteria.getValue().toString())) {
                throw new QueryDSLPredicateBuildException(criteria.getKey(), criteria.getValue().toString(), "Valid long value");
            } else {
                final NumberPath<Long> path = entityPath.getNumber(criteria.getKey(), Long.class);
                final long value = Long.parseLong(criteria.getValue().toString());
                switch (criteria.getOperation()) {
                    case ":":
                        return path.eq(value);
                    case "!":
                        return path.ne(value);
                    case ">":
                        return path.goe(value);
                    case "<":
                        return path.loe(value);
                    default:
                        throw new QueryDSLPredicateBuildException(criteria.getKey() + "'s search operator", criteria.getOperation(), "Valid Search Operator");
                }
            }
        } else if (typeByPath.equals(LocalDateTime.class)) {
            if (!isLocalDateTime(criteria.getValue().toString())) {
                throw new QueryDSLPredicateBuildException(criteria.getKey(), criteria.getValue().toString(), "Valid LocalDateTime value");
            } else {
                final DateTimePath<LocalDateTime> path = entityPath.getDateTime(criteria.getKey(), LocalDateTime.class);
                final LocalDateTime value = LocalDateTime.parse(criteria.getValue().toString());
                switch (criteria.getOperation()) {
                    case ":":
                        return path.eq(value);
                    case "!":
                        return path.ne(value);
                    case ">":
                        return path.after(value);
                    case "<":
                        return path.before(value);
                    default:
                        throw new QueryDSLPredicateBuildException(criteria.getKey() + "'s search operator", criteria.getOperation(), "Valid Search Operator");
                }
            }
        } else if (typeByPath.equals(UUID.class)) {
            if (!isUUID(criteria.getValue().toString())) {
                throw new QueryDSLPredicateBuildException(criteria.getKey(), criteria.getValue().toString(), "Valid UUID");
            } else {
                final ComparablePath<UUID> path = entityPath.getComparable(criteria.getKey(), UUID.class);
                switch (criteria.getOperation()) {
                    case ":":
                        return path.eq(UUID.fromString(criteria.getValue().toString()));
                    case "!":
                        return path.ne(UUID.fromString(criteria.getValue().toString()));
                    default:
                        throw new QueryDSLPredicateBuildException(criteria.getKey() + "'s search operator", criteria.getOperation(), "Valid Search Operator");
                }
            }
        } else if (typeByPath.isEnum()) {
            final EnumPath path = entityPath.getEnum(criteria.getKey(), (Class) typeByPath);
            switch (criteria.getOperation()) {
                case ":":
                    return path.eq(Enum.valueOf((Class) typeByPath, (String)criteria.getValue()));
                case "!":
                    return path.ne(Enum.valueOf((Class) typeByPath, (String)criteria.getValue()));
                default:
                    throw new QueryDSLPredicateBuildException(criteria.getKey() + "'s search operator", criteria.getOperation(), "Valid Search Operator");
            }
        } else if (typeByPath.equals(boolean.class) || typeByPath.equals(Boolean.class)) {
            final BooleanPath path = entityPath.getBoolean(criteria.getKey());
            switch (criteria.getOperation()) {
                case ":":
                    return path.eq(Boolean.valueOf(criteria.getValue().toString()));
                case "!":
                    return path.ne(Boolean.valueOf(criteria.getValue().toString()));
                default:
                    throw new QueryDSLPredicateBuildException(criteria.getKey() + "'s search operator", criteria.getOperation(), "Valid Search Operator");
            }
        }  else if (typeByPath.getPackage().getName().equals("ro.smarty.data")) {
            // the search is made on a entity
            switch (criteria.getOperation()) {
                case ":":
                    if (criteria.getValue().toString().equalsIgnoreCase("null")) {
                        return entityPath.get(criteria.getKey(), typeByPath).isNull();
                    }
                default:
                    // TODO: enhance this error
                    throw new QueryDSLPredicateBuildException(criteria.getKey() + "'s search operator", criteria.getOperation(), "Valid Search Operator");
            }
        }

        throw new QueryDSLPredicateBuildException("search field", criteria.getKey(), "Valid Search Field");
    }

    private Class getLastTypeByPath(Class<T> entityClass, String path) throws QueryDSLPredicateBuildException {
        String[] types = path.split("\\.");
        Class returnType = entityClass;

        for (String type : types) {
            try {
                returnType = returnType.getDeclaredField(type).getType();
            } catch (NoSuchFieldException e) {
                throw new QueryDSLPredicateBuildException("search field", criteria.getKey(), "Valid Search Field");
            }
        }

        return returnType;
    }

    private static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isLong(final String str) {
        try {
            Long.parseLong(str);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isLocalDateTime(final String str) {
        try {
            LocalDateTime.parse(str);
        } catch (final DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private static boolean isUUID(final String str) {
        try {
            UUID.fromString(str);
        } catch (final Exception e) {
            return false;
        }
        return true;
    }
}