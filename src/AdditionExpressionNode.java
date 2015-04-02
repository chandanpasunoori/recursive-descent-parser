
public class AdditionExpressionNode extends SequenceExpressionNode {

    public AdditionExpressionNode() {
    }

    public AdditionExpressionNode(ExpressionNode node, boolean positive) {
        super(node, positive);
    }

    @Override
    public int getType() {
        return ExpressionNode.ADDITION_NODE;
    }

    @Override
    public double getValue() {
        double sum = 0.0;
        for (Term t : terms) {
            if (t.positive) {
                sum += t.expression.getValue();
            } else {
                sum -= t.expression.getValue();
            }
        }
        return sum;
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        for (Term t : terms) {
            t.expression.accept(visitor);
        }
    }

}
