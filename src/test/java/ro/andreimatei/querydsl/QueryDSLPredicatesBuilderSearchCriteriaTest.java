package ro.andreimatei.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Before;
import org.junit.Test;
import ro.andreimatei.querydsl.exceptions.QueryDSLPredicateBuildException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * @author Andrei-Vlad.Matei <andrei@andreimatei.ro>
 */
public class QueryDSLPredicatesBuilderSearchCriteriaTest {

    private List<SearchCriteria> expectedCriteriaList;

    @Before
    public void setUp() {
        expectedCriteriaList = new ArrayList<>();
        expectedCriteriaList.add(new SearchCriteria("stringField1", ":", "value"));
        expectedCriteriaList.add(new SearchCriteria("stringField1", ";", "value"));
        expectedCriteriaList.add(new SearchCriteria("stringField1", "!", "value"));
        expectedCriteriaList.add(new SearchCriteria("stringField1", ":", "null"));
        expectedCriteriaList.add(new SearchCriteria("longField1", ":", "0"));
        expectedCriteriaList.add(new SearchCriteria("longField1", "!", "0"));
        expectedCriteriaList.add(new SearchCriteria("longField1", ">", "0"));
        expectedCriteriaList.add(new SearchCriteria("longField1", "<", "0"));
    }

    @Test
    public void testBuildMultipleClausesSearchCriteria() throws Exception {
        QueryDSLPredicatesBuilder<DummyClass> builder = new QueryDSLPredicatesBuilder<>(DummyClass.class);

        builder.with("stringField1", ":", "value");
        builder.with("stringField1", ";", "value");
        builder.with("stringField1", "!", "value");
        builder.with("stringField1", ":", "null");
        builder.with("longField1", ":", "0");
        builder.with("longField1", "!", "0");
        builder.with("longField1", ">", "0");
        builder.with("longField1", "<", "0");

        builder.build();

        List<SearchCriteria> actualCriteriaList = builder.getSearchCriteriaList();

        assertThat(actualCriteriaList, is(expectedCriteriaList));
    }

    @Test
    public void testBuildMultipleClausesSearchCriteriaFromRegex() throws Exception {
        QueryDSLPredicatesBuilder<DummyClass> builder = new QueryDSLPredicatesBuilder<>(DummyClass.class);

        builder.with("stringField1:value");
        builder.with("stringField1;value");
        builder.with("stringField1!value");
        builder.with("stringField1:null");
        builder.with("longField1:0");
        builder.with("longField1!0");
        builder.with("longField1>0");
        builder.with("longField1<0");

        builder.build();

        List<SearchCriteria> actualCriteriaList = builder.getSearchCriteriaList();

        assertThat(actualCriteriaList, is(expectedCriteriaList));
    }
}
