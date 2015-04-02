
public class ExponentiationExpressionNode implements ExpressionNode {

    private ExpressionNode base;
    private ExpressionNode exponent;

    public ExponentiationExpressionNode(ExpressionNode base, ExpressionNode exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public int getType() {
        return ExpressionNode.EXPONENTIATION_NODE;
    }

    @Override
    public double getValue() {
        return Math.pow(base.getValue(), exponent.getValue());
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        base.accept(visitor);
        exponent.accept(visitor);
    }
}
