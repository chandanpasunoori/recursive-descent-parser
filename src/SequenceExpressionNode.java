
import java.util.ArrayList;

public abstract class SequenceExpressionNode implements ExpressionNode {

    public class Term {

        public boolean positive;
        public ExpressionNode expression;

        public Term(boolean positive, ExpressionNode expression) {
            super();
            this.positive = positive;
            this.expression = expression;
        }
    }
    protected ArrayList<Term> terms;

    public SequenceExpressionNode() {
        this.terms = new ArrayList<>();
    }

    public SequenceExpressionNode(ExpressionNode a, boolean positive) {
        this.terms = new ArrayList<>();
        this.terms.add(new Term(positive, a));
    }

    public void add(ExpressionNode node, boolean positive) {
        this.terms.add(new Term(positive, node));
    }
}
