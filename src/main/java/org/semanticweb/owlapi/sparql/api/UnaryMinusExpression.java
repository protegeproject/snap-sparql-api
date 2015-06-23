package org.semanticweb.owlapi.sparql.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 30/07/2012
 */
public class UnaryMinusExpression implements Expression {

    private Expression expression;

    public UnaryMinusExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Set<Variable> getVariables() {
        return expression.getVariables();
    }

    @Override
    public String toString() {
        return "Expression(- " + expression + ")";
    }

    public EvaluationResult evaluate(SolutionMapping sm) {
        return evaluateAsNumeric(sm);
    }

    public boolean canEvaluateAsBoolean(SolutionMapping sm) {
        return false;
    }

    public EvaluationResult evaluateAsEffectiveBooleanValue(SolutionMapping sm) {
        return EvaluationResult.getError();
    }

    public boolean canEvaluateAsStringLiteral(SolutionMapping sm) {
        return false;
    }

    public EvaluationResult evaluateAsStringLiteral(SolutionMapping sm) {
        return EvaluationResult.getError();
    }

    public boolean canEvaluateAsSimpleLiteral(SolutionMapping sm) {
        return false;
    }

    public EvaluationResult evaluateAsSimpleLiteral(SolutionMapping sm) {
        return EvaluationResult.getError();
    }

    public boolean canEvaluateAsNumeric(SolutionMapping sm) {
        return true;
    }

    public EvaluationResult evaluateAsNumeric(SolutionMapping sm) {
        EvaluationResult eval = expression.evaluateAsNumeric(sm);
        if(eval.isError()) {
            return eval;
        }
        return EvaluationResult.getDouble(-eval.asNumeric());
    }

    public boolean canEvaluateAsDateTime(SolutionMapping sm) {
        return false;
    }

    public EvaluationResult evaluateAsDateTime(SolutionMapping sm) {
        return EvaluationResult.getError();
    }

    public boolean canEvaluateAsIRI(SolutionMapping sm) {
        return false;
    }

    @Override
    public EvaluationResult evaluateAsLiteral(SolutionMapping sm) {
        return EvaluationResult.getError();
    }


    @Override
    public EvaluationResult evaluateAsIRI(SolutionMapping sm) {
        return EvaluationResult.getError();
    }

//    @Override
//    public Expression replaceSubExpressionWith(Expression subExpression, Expression replaceWith) {
//        if(subExpression.equals(this)) {
//            return replaceWith;
//        }
//        return new UnaryMinusExpression(expression.replaceSubExpressionWith(subExpression, replaceWith));
//    }

    @Override
    public List<Expression> getSubExpressions() {
        ArrayList<Expression> result = new ArrayList<>();
        result.add(this);
        result.addAll(expression.getSubExpressions());
        return result;
    }

    @Override
    public <R, E extends Throwable, C> R accept(ExpressionVisitor<R, E, C> visitor, C context) throws E {
        return visitor.visit(this, context);
    }
}
