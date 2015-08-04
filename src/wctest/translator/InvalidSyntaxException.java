package wctest.translator;


/**
 * Exception thrown by the Lexer and Parser when an unexpected token or string
 * of tokens is encountered. It signals a syntax error or grammatical error in
 * the source file.
 * <br>
 * Created on: Jul 15, 2015 at 7:50:22 PM
 * @author Matthew Edwards
 */
public class InvalidSyntaxException extends RuntimeException
{
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 6736000842217174098L;
    
    /**
     * Creates new InvalidSyntaxException on the given token
     * @param t Token that caused the exception to be thrown
     */
    public InvalidSyntaxException(Token t)
    {
        super("Syntax Error: Unexpected token \"" + t.getText() + "\" <" + t.getType().getName() + "> at (" + t.getLine() + ":" + t.getCol() + ")");
    }

}
