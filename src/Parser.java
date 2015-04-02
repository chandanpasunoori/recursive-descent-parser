
import java.util.LinkedList;

public class Parser {

    LinkedList<Token> tokens;
    Token lookahead;

    public ExpressionNode parse(String expression) {
        Tokenizer tokenizer = Tokenizer.getExpressionTokenizer();
        tokenizer.tokenize(expression);
        LinkedList<Token> tokens = tokenizer.getTokens();
        return this.parse(tokens);
    }

    public ExpressionNode parse(LinkedList<Token> tokens) {
        this.tokens = (LinkedList<Token>) tokens.clone();
        lookahead = this.tokens.getFirst();
        ExpressionNode expr = expression();

        if (lookahead.token != Token.EPSILON) {
            throw new ParserException("Unexpected symbol %s found", lookahead);
        }

        return expr;
    }

    private ExpressionNode expression() {
        ExpressionNode expr = signedTerm();
        expr = sumOp(expr);
        return expr;
    }

    private ExpressionNode sumOp(ExpressionNode expr) {
        if (lookahead.token == Token.PLUSMINUS) {
            AdditionExpressionNode sum;
            if (expr.getType() == ExpressionNode.ADDITION_NODE) {
                sum = (AdditionExpressionNode) expr;
            } else {
                sum = new AdditionExpressionNode(expr, true);
            }
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode t = term();
            sum.add(t, positive);
            return sumOp(sum);
        }
        return expr;
    }

    private ExpressionNode signedTerm() {
        if (lookahead.token == Token.PLUSMINUS) {
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode t = term();
            if (positive) {
                return t;
            } else {
                return new AdditionExpressionNode(t, false);
            }
        }
        return term();
    }

    private ExpressionNode term() {
        ExpressionNode f = factor();
        return termOp(f);
    }

    private ExpressionNode termOp(ExpressionNode expression) {
        if (lookahead.token == Token.MULTDIV) {
            MultiplicationExpressionNode prod;
            if (expression.getType() == ExpressionNode.MULTIPLICATION_NODE) {
                prod = (MultiplicationExpressionNode) expression;
            } else {
                prod = new MultiplicationExpressionNode(expression, true);
            }
            boolean positive = lookahead.sequence.equals("*");
            nextToken();
            ExpressionNode f = signedFactor();
            prod.add(f, positive);

            return termOp(prod);
        }
        return expression;
    }

    private ExpressionNode signedFactor() {
        if (lookahead.token == Token.PLUSMINUS) {
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode t = factor();
            if (positive) {
                return t;
            } else {
                return new AdditionExpressionNode(t, false);
            }
        }
        return factor();
    }

    private ExpressionNode factor() {
        ExpressionNode a = argument();
        return factorOp(a);
    }

    private ExpressionNode factorOp(ExpressionNode expr) {
        if (lookahead.token == Token.RAISED) {
            nextToken();
            ExpressionNode exponent = signedFactor();

            return new ExponentiationExpressionNode(expr, exponent);
        }
        return expr;
    }

    private ExpressionNode argument() {
        if (lookahead.token == Token.FUNCTION) {
            int function = FunctionExpressionNode.stringToFunction(lookahead.sequence);
            nextToken();
            ExpressionNode expr = argument();
            return new FunctionExpressionNode(function, expr);
        } else if (lookahead.token == Token.OPEN_BRACKET) {
            nextToken();
            ExpressionNode expr = expression();
            if (lookahead.token != Token.CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected", lookahead);
            }
            nextToken();
            return expr;
        }
        return value();
    }

    private ExpressionNode value() {
        if (lookahead.token == Token.NUMBER) {
            ExpressionNode expr = new ConstantExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }
        if (lookahead.token == Token.VARIABLE) {
            ExpressionNode expr = new VariableExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        if (lookahead.token == Token.EPSILON) {
            throw new ParserException("Unexpected end of input");
        } else {
            throw new ParserException("Unexpected symbol %s found", lookahead);
        }
    }

    private void nextToken() {
        tokens.pop();
        if (tokens.isEmpty()) {
            lookahead = new Token(Token.EPSILON, "", -1);
        } else {
            lookahead = tokens.getFirst();
        }
    }
}
