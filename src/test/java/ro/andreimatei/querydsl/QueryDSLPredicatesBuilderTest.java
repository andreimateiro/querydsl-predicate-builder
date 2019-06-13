package ro.andreimatei.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Test;
import ro.andreimatei.querydsl.exceptions.QueryDSLPredicateBuildException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


/**
 * @author Andrei-Vlad.Matei <andrei@andreimatei.ro>
 */
public class QueryDSLPredicatesBuilderTest {

    private static String INVALID_OPERATOR = "^";

    class DummyClass {
        public String stringField1;
        public LocalDateTime localDateTimeField1;
        public long longField1;
        public UUID uuidField1;
        public Boolean booleanField1;
    }

    // Positive tests

    @Test
    public void testStringFieldEquals() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("stringField1", ":", "value").build();
        assertEquals(expression.toString(), "eqIc(DummyClass.stringField1,value)");
    }

    @Test
    public void testStringFieldEqualsNull() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("stringField1", ":", "null").build();
        assertEquals(expression.toString(), "DummyClass.stringField1 is null");
    }

    @Test
    public void testStringFieldNotEquals() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("stringField1", "!", "value").build();
        assertEquals(expression.toString(), "!(eqIc(DummyClass.stringField1,value))");
    }

    @Test
    public void testIntFieldEquals() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("longField1", ":", 0).build();
        assertEquals(expression.toString(), "DummyClass.longField1 = 0");
    }

    @Test
    public void testIntFieldNotEquals() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("longField1", "!", 0).build();
        assertEquals(expression.toString(), "DummyClass.longField1 != 0");
    }

    @Test
    public void testIntFieldGoe() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("longField1", ">", 0).build();
        assertEquals(expression.toString(), "DummyClass.longField1 >= 0");
    }

    @Test
    public void testIntFieldLoe() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("longField1", "<", 0).build();
        assertEquals(expression.toString(), "DummyClass.longField1 <= 0");
    }

    @Test
    public void testLocalDateTimeFieldEquals() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("localDateTimeField1", ":", now).build();
        assertEquals(expression.toString(), "DummyClass.localDateTimeField1 = " + now);
    }

    @Test
    public void testLocalDateTimeFieldNotEquals() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("localDateTimeField1", "!", now).build();
        assertEquals(expression.toString(), "DummyClass.localDateTimeField1 != " + now);
    }

    @Test
    public void testLocalDateTimeFieldGoe() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("localDateTimeField1", ">", now).build();
        assertEquals(expression.toString(), "DummyClass.localDateTimeField1 > " + now);
    }

    @Test
    public void testLocalDateTimeFieldLoe() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("localDateTimeField1", "<", now).build();
        assertEquals(expression.toString(), "DummyClass.localDateTimeField1 < " + now);
    }

    @Test
    public void testUUIDFieldEquals() throws Exception {
        UUID uuid = UUID.randomUUID();
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("uuidField1", ":", uuid).build();
        assertEquals(expression.toString(), "DummyClass.uuidField1 = " + uuid);
    }

    @Test
    public void testUUIDFieldNotEquals() throws Exception {
        UUID uuid = UUID.randomUUID();
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("uuidField1", "!", uuid).build();
        assertEquals(expression.toString(), "DummyClass.uuidField1 != " + uuid);
    }

    @Test
    public void testBooleanFieldEquals() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("booleanField1", ":", "true").build();
        assertEquals(expression.toString(), "DummyClass.booleanField1 = true");
    }

    @Test
    public void testBooleanFieldNotEquals() throws Exception {
        BooleanExpression expression = new QueryDSLPredicatesBuilder<>(DummyClass.class).with("booleanField1", "!", "true").build();
        assertEquals(expression.toString(), "DummyClass.booleanField1 != true");
    }

    // Other tests

    @Test(expected = QueryDSLPredicateBuildException.class)
    public void testStringFieldInvalidOperator() throws Exception {
        new QueryDSLPredicatesBuilder<>(DummyClass.class).with("stringField1", INVALID_OPERATOR, "value").build();
    }

    @Test(expected = QueryDSLPredicateBuildException.class)
    public void testIntegerFieldInvalidOperator() throws Exception {
        new QueryDSLPredicatesBuilder<>(DummyClass.class).with("longField1", INVALID_OPERATOR, 0).build();
    }

    @Test(expected = QueryDSLPredicateBuildException.class)
    public void testLocalDateTimeFieldInvalidOperator() throws Exception {
        new QueryDSLPredicatesBuilder<>(DummyClass.class).with("localDateTimeField1", INVALID_OPERATOR, LocalDateTime.now()).build();
    }
}
