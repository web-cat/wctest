package wctest.translator;

import java.util.ArrayList;

/**
 * A node class for the nested Abstract Syntax Tree representation created by
 * the parser.
 * <br>
 * Created on: Jul 15, 2015 at 8:04:35 PM
 * @author Matthew Edwards
 */
public class TokenTree
{
    private ArrayList<TokenTree> children;
    private Token token;
    
    /**
     * Creates new TokenTree node
     * @param token Token contained by this node
     */
    public TokenTree(Token token)
    {
        this.children = new ArrayList<TokenTree>();
        this.token = token;
    }
    
    /**
     * Returns this node's token
     * @return Token
     */
    public Token getToken()
    {
        return token;
    }
    
    /**
     * Adds a child to this node
     * @param child Child to add
     */
    public void addChild(TokenTree child)
    {
        children.add(child);
    }
    
    /**
     * Adds a child to this node at the specified index
     * @param index Index at which to insert
     * @param child Child to add
     */
    public void addChild(int index, TokenTree child)
    {
        children.add(index, child);
    }
    
    /**
     * Removes a child from this node
     * @param index Index of child to remove
     */
    public void removeChild(int index)
    {
        children.remove(index);
    }
    
    /**
     * Returns the child at the specified index
     * @param index Index of child
     * @return Child
     */
    public TokenTree getChild(int index)
    {
        return children.get(index);
    }
    
    /**
     * Returns number of children
     * @return Number of children
     */
    public int getChildren()
    {
        return children.size();
    }
    
    @Override
    public String toString()
    {
        return toFormattedString(0);
    }
    
    private String toFormattedString(int indent)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; indent > i; i++)
        {
            sb.append("    ");
        }
        sb.append(token);
        
        for (TokenTree t : children)
        {
            sb.append("\n" + t.toFormattedString(indent + 1));
        }
        
        return sb.toString();
    }
}
