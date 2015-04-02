
public class MultiplicationExpressionNode extends SequenceExpressionNode {

    public MultiplicationExpressionNode() {
    }

    public MultiplicationExpressionNode(ExpressionNode a, boolean positive) {
        super(a, positive);
    }

    @Override
    public int getType() {
        return ExpressionNode.MULTIPLICATION_NODE;
    }

    @Override
    public double getValue() {
        double prod = 1.0;
        for (Term t : terms) {
            if (t.positive) {
                prod *= t.expression.getValue();
            } else {
                prod /= t.expression.getValue();
            }
        }
        return prod;
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        for (Term t : terms) {
            t.expression.accept(visitor);
        }
    }
}
