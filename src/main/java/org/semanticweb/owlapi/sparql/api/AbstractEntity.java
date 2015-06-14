package org.semanticweb.owlapi.sparql.api;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.PrefixManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 26/07/2012
 */
public abstract class AbstractEntity implements HasIRI, Term, Entity {

    private IRI iri;

    public AbstractEntity(IRI iri) {
        this.iri = checkNotNull(iri);
    }

    public Set<Variable> getVariables() {
        return Collections.emptySet();
    }

    public IRI getIRI() {
        return iri;
    }

    public String getPrefixedName(PrefixManager pm) {
        String prefixIRI = pm.getPrefixIRI(iri);
        return prefixIRI != null ? prefixIRI : iri.toQuotedString();
    }

    @Override
    public boolean isSameRDFTermAs(Term term) {
        if(term == this) {
            return true;
        }
        if(!(term instanceof HasIRI)) {
            return false;
        }
        HasIRI other = (HasIRI) term;
        return this.iri.equals(other.getIRI());
    }

    @Override
    public RDFTerm asRDFTerm() {
        return new AtomicIRI(iri);
    }

    @Override
    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return null;
    }

    public String getIdentifier() {
        return iri.toString();
    }

    public boolean isLiteral() {
        return false;
    }

    public boolean isEntityIRI() {
        return true;
    }

    public boolean isUntypedIRI() {
        return false;
    }

    public EvaluationResult evaluate(SolutionMapping sm) {
        return EvaluationResult.getResult(this.asRDFTerm());
    }

    public EvaluationResult evaluateAsEffectiveBooleanValue(SolutionMapping sm) {
        return EvaluationResult.getError();
    }

    public EvaluationResult evaluateAsSimpleLiteral(SolutionMapping sm) {
        return EvaluationResult.getSimpleLiteral(getIRI().toString());
    }

    public EvaluationResult evaluateAsStringLiteral(SolutionMapping sm) {
        return EvaluationResult.getSimpleLiteral(getIRI().toString());
    }

    public EvaluationResult evaluateAsNumeric(SolutionMapping sm) {
        return EvaluationResult.getError();
    }

    public EvaluationResult evaluateAsDateTime(SolutionMapping sm) {
        return EvaluationResult.getError();
    }

    @Override
    public EvaluationResult evaluateAsLiteral(SolutionMapping sm) {
        return EvaluationResult.getSimpleLiteral(getIRI().toString());
    }

    @Override
    public EvaluationResult evaluateAsIRI(SolutionMapping sm) {
        return EvaluationResult.getResult(new AtomicIRI(iri));
    }

    @Override
    public void collectVariables(Collection<Variable> variables) {}


    @Override
    public final AnnotationSubject toAnnotationSubject() {
        return new AtomicIRI(getIRI());
    }
}
