
public class ConstantExpressionNode implements ExpressionNode {

    private double value;

    public ConstantExpressionNode(double value) {
        this.value = value;
    }

    public ConstantExpressionNode(String value) {
        this.value = Double.valueOf(value);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public int getType() {
        return ExpressionNode.CONSTANT_NODE;
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}
