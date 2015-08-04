package wctest.translator;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Tokenizes a string (created by reading from a source code file) and stores the
 * prepared tokens in a list to be parsed.
 * <br>
 * Created on: Jul 15, 2015 at 7:44:35 PM
 * @author Matthew Edwards
 */
public class Lexer
{
    private static ArrayList<Token> tokens;
    
    /**
     * Tokenizes a string
     * @param str Source file string to tokenize
     * @return Prepared list of tokens created from source file
     */
    public static ArrayList<Token> tokenize(String str)
    {
        tokens = new ArrayList<Token>();
        int line = 1;
        int col = 0;
        while (str.length() > 0)
        {
            boolean match = false;
            
            for (TokenType t : TokenType.values())
            {
                Matcher m = t.getPattern().matcher(str);
                if (m.find())
                {
                    match = true;
                    
                    String token = m.group();
                    if (!t.isIgnored())
                    {
                        tokens.add(new Token(t, t != TokenType.WHITESPACE ? (t != TokenType.STRING ? token.trim().replaceAll("[ \n\r\t]+", "") : token.trim()) : token, line, col));
                    }
                    col += token.length();
                    String[] temp = token.replaceAll("[\n\r]", "\n ").split("\n");
                    line += temp.length - 1;
                    if (temp.length - 1 > 0)
                    {
                        col = temp[temp.length - 1].length() - 1;
                    }

                    str = m.replaceFirst("");
                    break;
                }
            }
            
            if (!match)
            {
                Token t = tokens.get(tokens.size() - 1);
                throw new InvalidSyntaxException(t);
            }
        }
        tokens.add(new Token(TokenType.EOF, "", line, col));
        
        return tokens;
    }
}
