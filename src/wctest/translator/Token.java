package wctest.translator;

/**
 * Data structure representing a token created by the Lexer. Contains token type
 * information in addition to raw String from which this token was created and 
 * data for the original parsed text in its source file.
 * <br>
 * Created on: Jul 15, 2015 at 7:46:59 PM
 * @author Matthew Edwards
 */
public class Token
{
    private TokenType type;
    private String contents;
    private int line;
    private int col;
    
    /**
     * Creates new Token
     * @param type Type of token
     * @param contents Raw string from which this token was created
     * @param line Line in source file of this token
     * @param col Column in source file of this token
     */
    public Token(TokenType type, String contents, int line, int col)
    {
        this.type = type;
        this.contents = contents;
        this.line = line;
        this.col = col;
    }
    
    /**
     * Returns the type of this token
     * @return
     */
    public TokenType getType()
    {
        return type;
    }
    
    /**
     * Returns the text of this token
     * @return
     */
    public String getText()
    {
        return contents;
    }
    
    /**
     * Returns the line of this token
     * @return
     */
    public int getLine()
    {
        return line;
    }
    
    /**
     * Returns the column of this token
     * @return
     */
    public int getCol()
    {
        return col;
    }
    
    @Override
    public String toString()
    {
        return type.getName() + "<" + contents + ">(" + line + ":" + col + ")";
    }
}
