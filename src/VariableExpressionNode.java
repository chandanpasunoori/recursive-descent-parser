
public class VariableExpressionNode implements ExpressionNode {

    private String name;
    private double value;
    private boolean valueSet;

    public VariableExpressionNode(String name) {
        this.name = name;
        valueSet = false;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getType() {
        return ExpressionNode.VARIABLE_NODE;
    }

    public void setValue(double value) {
        this.value = value;
        this.valueSet = true;
    }

    @Override
    public double getValue() {
        if (valueSet) {
            return value;
        } else {
            throw new EvaluationException("Variable '" + name + "' was not initialized.");
        }
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }

}
