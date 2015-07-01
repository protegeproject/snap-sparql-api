package org.semanticweb.owlapi.sparql.builtin.eval;

import org.semanticweb.owlapi.sparql.algebra.SolutionSequence;
import org.semanticweb.owlapi.sparql.api.Datatype;
import org.semanticweb.owlapi.sparql.api.EvaluationResult;
import org.semanticweb.owlapi.sparql.api.Expression;
import org.semanticweb.owlapi.sparql.api.SolutionMapping;
import org.semanticweb.owlapi.sparql.builtin.BasicNumericType;

import java.util.List;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 21/06/15
 */
public class SUM_Evaluator implements BuiltInCallEvaluator, BuiltInAggregateCallEvaluator {

    @Override
    public EvaluationResult evaluate(List<Expression> args, SolutionMapping sm) {
        return EvaluationResult.getError();
    }

    @Override
    public EvaluationResult evaluateAsAggregate(List<Expression> args, SolutionSequence solutionSequence) {
        if(args.size() != 1) {
            return EvaluationResult.getError();
        }
        Expression arg = args.get(0);
        double sum = 0;
        Datatype mostSpecificBasicType = Datatype.getXSDInteger();
        for(SolutionMapping sm : solutionSequence.getSolutionMappings()) {
            EvaluationResult argEval = arg.evaluateAsNumeric(sm);
            if(!argEval.isError()) {
                mostSpecificBasicType = BasicNumericType.getMostSpecificBasicNumericType(mostSpecificBasicType, argEval.asLiteral().getDatatype());
                sum += argEval.asNumeric();
            }
        }
        return EvaluationResult.getResult(BasicNumericType.getLiteralOfBasicNumericType(sum, mostSpecificBasicType));
    }
}
