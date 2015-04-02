
public class Main {

    public static void main(String[] args) {
        String exprstr = "2^2^3,15,20^2";
        if (args.length > 0) {
            exprstr = args[0];
        }
        String[] split = exprstr.split(",");
        String solutions = "";
        for (String e : split) {
            Parser parser = new Parser();
            ExpressionNode expr = parser.parse(e);
            solutions = solutions + " " + Math.round(expr.getValue());
        }
        System.out.println(solutions);
    }
}
