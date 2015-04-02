
public class SetVariable implements ExpressionNodeVisitor {

    private String name;
    private double value;

    public SetVariable(String name, double value) {
        super();
        this.name = name;
        this.value = value;
    }

    @Override
    public void visit(VariableExpressionNode node) {
        if (node.getName().equals(name)) {
            node.setValue(value);
        }
    }

    @Override
    public void visit(ConstantExpressionNode node) {
    }

    @Override
    public void visit(AdditionExpressionNode node) {
    }

    @Override
    public void visit(MultiplicationExpressionNode node) {
    }

    @Override
    public void visit(ExponentiationExpressionNode node) {
    }

    @Override
    public void visit(FunctionExpressionNode node) {
    }

}
