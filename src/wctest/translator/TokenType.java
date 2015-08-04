package wctest.translator;

import java.util.regex.Pattern;

/**
 * Each TokenType stores the type name, whether the token should be ignored by
 * the lexer, and string regex (for lexing its token from the source code).
 * <br>
 * Created on: Jul 15, 2015 at 7:37:29 PM
 * @author Matthew Edwards
 */
@SuppressWarnings("javadoc")
public enum TokenType
{
    COMMENT("Comment", "(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", true),
    
    TEST("Test", "test[s]?", false),
    MODIFIER("Modifier", "example|hidden", false),
    SUBJECT("Subject", "subject", false),
    
    IS_NOT("Is Not", "(is)[ \n\r]+(not)", false),
    IS_EQ("Is ==", "(is)[ \n\r]*(==)", false),
    IS_NOT_EQ("Is !=", "(is)[ \n\r]*(!=)", false),
    IS_GTE("Is >=", "(is)[ \n\r]*(>=)", false),
    IS_GT("Is >", "(is)[ \n\r]*(>)", false),
    IS_LTE("Is <=", "(is)[ \n\r]*(<=)", false),
    IS_LT("Is <", "(is)[ \n\r]*(<)", false),
    IS("Is", "is", false),
    INSTANCEOF("Instance Of", "instanceof", false),
    THROWS("Throws", "throws", false),
    MATCHES("Matches", "matches", false),
    WITHIN("Within", "within", false),
    BETWEEN("Between", "between", false),
    EXCL("Exclusivity", "inclusive|exclusive", false),
    
    COMMA("Comma", "\\,", false),
    LBRACE("LBrace", "\\{", false),
    RBRACE("RBrace", "\\}", false),
    LPAREN("LParen", "\\(", false),
    RPAREN("RParen", "\\)", false),

    REGEX("Regex", "\\/([^\\\\/]+|\\\\([\\/]))*\\/", false),
    
    STRING("String", "\"([^\\\\\"]+|\\\\([btnfr\"'\\\\]|[0-3]?[0-7]{1,2}|u[0-9a-fA-F]{4}))*\"", false),
    FLOAT("Float", "([0]|([\\-]?[1-9][0-9]*))[\\.][0-9]*", false),
    INTEGER("Integer", "([0]|([\\-]?[1-9][0-9]*))", false),

    IO("IO", "<<|>>", false),
    ADD("Add", "[+\\-]", false),
    POW("Power", "\\*\\*", false),
    MULT("Multiply", "[\\/\\*%]", false),
    
    IDENTIFIER("Identifier", "[a-zA-Z_][a-zA-Z0-9_]*([\n\r\t ]*[\\.][\n\r\t ]*[a-zA-Z][a-zA-Z0-9_]*)*", false),
    DOT("Dot", "[\\.]", false),
    WHITESPACE("Whitespace", "[\n\r\t ]", false),
    INVALID("Invalid", ".*", false),
    EOF("EOF", ".*", false);
    
    private String name;
    private Pattern p;
    private boolean ignore;
    
    private TokenType(String name, String regex, boolean ignore)
    {
        this.name = name;
        this.p = Pattern.compile("^(" + regex + ")");
        this.ignore = ignore;
    }
    
    /**
     * Returns token type name
     * @return
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns token type regex as Pattern
     * @return
     */
    public Pattern getPattern()
    {
        return p;
    }
    
    /**
     * Returns true if this token type should be ignored by the lexer
     * @return
     */
    public boolean isIgnored()
    {
        return ignore;
    }
}
