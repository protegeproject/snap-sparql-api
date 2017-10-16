package org.semanticweb.owlapi.sparql.builtin.eval;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.sparql.api.EvaluationResult;
import org.semanticweb.owlapi.sparql.api.Literal;
import org.semanticweb.owlapi.sparql.api.SolutionMapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 16 Oct 2017
 */
public class CEIL_Evaluator_TestCase {

    private CEIL_Evaluator evaluator;

    @Before
    public void setUp() throws Exception {
        evaluator = new CEIL_Evaluator();
    }

    @Test
    public void shouldGetPositiveCeiling() {
        EvaluationResult result = evaluator.evaluate(Literal.createDecimal(10.5), SolutionMapping.emptyMapping());
        assertThat(result.getResult(), is(Literal.createDecimal(11)));
    }

    @Test
    public void shouldGetNegativeCeiling() {
        EvaluationResult result = evaluator.evaluate(Literal.createDecimal(-10.5), SolutionMapping.emptyMapping());
        assertThat(result.getResult(), is(Literal.createDecimal(-10)));
    }

    @Test
    public void shouldProduceError() {
        EvaluationResult result = evaluator.evaluate(Literal.createString("Hello"), SolutionMapping.emptyMapping());
        assertThat(result.isError(), is(true));
    }
}
