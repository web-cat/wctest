package wctest;

import wctest.translator.*;

/**
 * Handles conversion of the Abstract Syntax Tree into source code for the target
 * language test case.
 * <br>
 * Created on: Jul 15, 2015 at 9:35:58 PM
 * @author Matthew Edwards
 */
public class Translator
{
    private static final String header = "/* Auto-generated Java Test Case */\n" +
            "@Description(\"%s\")\n" +
            "public class WCTest extends me.edwards.wctest.test.TestSet\n" + 
            "{\n" + 
            "    private Object subject;\n" + 
            "\n" + 
            "    public void setUp()\n" + 
            "    {\n" + 
            "        subject = %s;\n" + 
            "    }";
    private static final String footer = "\n}";

    private static final String testMethod = "\n\n    @Description(\"%s\")\n" +
            "    public void test%d()\n" + 
            "    {\n%s%s%s    }";
    
    private static final String invoke           = "invoke(%s, \"%s\"%s)";
    
    private static final String nullMethod       = "        assertIs(%s, %s);\n";
    private static final String isMethod         = "        assertIs(%s, %s);\n";
    private static final String isNotMethod      = "        assertIsNot(%s, %s);\n";
    private static final String isEqMethod       = "        assertIsEq(%s, %s);\n";
    private static final String isNotEqMethod    = "        assertIsNotEq(%s, %s);\n";
    private static final String isLtMethod       = "        assertIsLt(%s, %s);\n";
    private static final String isLteMethod      = "        assertIsLte(%s, %s);\n";
    private static final String isGtMethod       = "        assertIsGt(%s, %s);\n";
    private static final String isGteMethod      = "        assertIsGte(%s, %s);\n";
    private static final String matchesMethod    = "        assertMatches(%s, %s);\n";
    private static final String instanceofMethod = "        assertIsInstanceOf(%s, %s);\n";
    private static final String throwsMethod     = "        assertThrows(%s, %s);\n";

    private static final String stdInMethod      = "        setSystemIn(%s);\n";
    private static final String stdOutMethod     = "        assertSystemOutIs(%s);\n";
    
    private static final String floatWrapper     = "new DoubleValue(%s%s)";
    private static final String rangeWrapper     = "new Range(%s, %s, %s)";
    
    private static int testNum;
    
    /**
     * Translates AST into source code for target language
     * @param root Abstract Syntax Tree to translate
     * @return 
     */
    public static String translate(TokenTree root)
    {
        StringBuffer sb = new StringBuffer();
        testNum = 0;
        
        String testName = "";
        if (root.getChild(0).getToken() != null
                && root.getChild(0).getToken().getType() == TokenType.STRING)
        {
            testName = root.getChild(0).getToken().getText();
            testName = testName.substring(1, testName.length() - 1);
            root.removeChild(0);
        }
        
        {
            TokenTree body = root.getChild(0);
            
            if (body.getChild(0).getToken().getType() == TokenType.SUBJECT)
            {
                String subject = body.getChild(0).getChild(0).getToken().getText();
                sb.append(String.format(header, testName, subject));
                body.removeChild(0);
            }
            else
            {
                return error(body.getChild(0).getToken());
            }
            
            sb.append(body(testName, body));
        }
        
        sb.append(footer);
        return sb.toString();
    }
    
    private static String error(Token t)
    {
        throw new InvalidSyntaxException(t);
    }
    
    private static String body(String description, TokenTree body)
    {
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; body.getChildren() > i; i++)
        {
            if (body.getChild(i).getToken() == null)
            {
                sb.append(getFormattedInsert(body.getChild(i), nullMethod, description, null));
            }
            else if (body.getChild(i).getToken().getType() == TokenType.IS
                    || body.getChild(i).getToken().getType() == TokenType.IS_NOT
                    || body.getChild(i).getToken().getType() == TokenType.IS_EQ
                    || body.getChild(i).getToken().getType() == TokenType.IS_NOT_EQ
                    || body.getChild(i).getToken().getType() == TokenType.IS_LT
                    || body.getChild(i).getToken().getType() == TokenType.IS_LTE
                    || body.getChild(i).getToken().getType() == TokenType.IS_GT
                    || body.getChild(i).getToken().getType() == TokenType.IS_GTE)
            {
                String result = "";
                
                if (body.getChild(i).getChild(1).getToken() == null)
                {
                    result = String.format(rangeWrapper, body.getChild(i).getChild(1).getChild(0).getToken().getText(),
                            body.getChild(i).getChild(1).getChild(1).getToken().getText(),
                            body.getChild(i).getChild(1).getChild(2).getToken().getText());
                }
                else
                {
                    result = body.getChild(i).getChild(1).getToken().getText();

                    if (body.getChild(i).getChildren() == 3)
                    {
                        result = String.format(floatWrapper, "\"" + result + "\"", ", " + body.getChild(i).getChild(2).getToken().getText());
                    }
                    else if (body.getChild(i).getChild(1).getToken().getType() == TokenType.FLOAT)
                    {
                        result = String.format(floatWrapper, "\"" + result + "\"", "");
                    }
                }
                
                String method = isMethod;
                
                if (body.getChild(i).getToken().getType() == TokenType.IS_NOT)
                {
                    method = isNotMethod;
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_EQ)
                {
                    method = isEqMethod;
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_NOT_EQ)
                {
                    method = isNotEqMethod;
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_LT)
                {
                    method = isLtMethod;
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_LTE)
                {
                    method = isLteMethod;
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_GT)
                {
                    method = isGtMethod;
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_GTE)
                {
                    method = isGteMethod;
                }
                
                sb.append(getFormattedInsert(body.getChild(i), method, description, result));
            }
            else if (body.getChild(i).getToken().getType() == TokenType.MATCHES)
            {
                String result = body.getChild(i).getChild(1).getToken().getText();
                sb.append(getFormattedInsert(body.getChild(i), matchesMethod, description,
                        "\"" + result.substring(1, result.length() - 1).replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\"") + "\""));
            }
            else if (body.getChild(i).getToken().getType() == TokenType.INSTANCEOF)
            {
                sb.append(getFormattedInsert(body.getChild(i), instanceofMethod, description, body.getChild(i).getChild(1).getToken().getText())); // TODO
            }
            else if (body.getChild(i).getToken().getType() == TokenType.THROWS)
            {
                sb.append(getFormattedInsert(body.getChild(i), throwsMethod, description, body.getChild(i).getChild(1).getToken().getText())); // TODO
            }
            else if (body.getChild(i).getToken().getType() == TokenType.TEST)
            {
//                String modifier = body.getChild(i).getChild(0).getToken() != null ? body.getChild(i).getChild(0).getToken().getText() : null;
                String name = body.getChild(i).getChild(1).getToken() != null ? body.getChild(i).getChild(1).getToken().getText() : null;
                TokenTree testBody = body.getChild(i).getChild(body.getChild(i).getChildren() - 1);
                sb.append(body(description + (name == null ? "" : " " + name.substring(1, name.length() - 1)), testBody));
            }
            else
            {
                return error(body.getChild(i).getToken());
            }
        }
        
        return sb.toString();
    }
    
    private static String getFormattedInsert(TokenTree body, String method, String description, String result)
    {
        String name = body.getChild(0).getToken().getText();
        String subject = "subject";
        String[] nameSplit = name.split("\\.");
        if (nameSplit.length > 1)
        {
            subject = name.substring(0, name.length() - nameSplit[nameSplit.length - 1].length() - 1);
            name = nameSplit[nameSplit.length - 1];
        }
        String args = body.getChild(0).getChild(0).getToken() != null ? ", " + body.getChild(0).getChild(0).getToken().getText() : "";
        String stdIn = "";
        String stdOut = "";
        if (body.getChild(0).getChildren() > 1)
        {
            if (body.getChild(0).getChild(1).getToken() != null)
            {
                String inv = String.format(invoke, subject, name, args);
                TokenTree newMethod = body.getChild(0).getChild(1);
                while (newMethod.getChild(1).getToken() != null)
                {
                    String newName = newMethod.getToken().getText();
                    String[] newNameSplit = newName.split("\\.");
                    if (newNameSplit.length > 1)
                    {
                        inv = inv + "." + newName.substring(0, newName.length() - newNameSplit[newNameSplit.length - 1].length() - 1);
                        newName = newNameSplit[nameSplit.length - 1];
                    }
                    String newArgs = newMethod.getChild(0).getToken() != null ? ", " + newMethod.getChild(0).getToken().getText() : "";
                    inv = String.format(invoke, inv, newName, newArgs);
                    newMethod = newMethod.getChild(1);
                }
                
                name = newMethod.getToken().getText();
                nameSplit = name.split("\\.");
                if (nameSplit.length > 1)
                {
                    inv = inv + "." + name.substring(0, name.length() - nameSplit[nameSplit.length - 1].length() - 1);
                    name = nameSplit[nameSplit.length - 1];
                }
                args = newMethod.getChild(0).getToken() != null ? ", " + newMethod.getChild(0).getToken().getText() : "";
                subject = inv;
                
                body.getChild(0).removeChild(1);
                body.getChild(0).addChild(newMethod.getChild(1));
                body.getChild(0).addChild(newMethod.getChild(2));
            }
            if (body.getChild(0).getChildren() > 1 && body.getChild(0).getChild(1).getChildren() > 0)
            {
                StringBuffer inBuffer = new StringBuffer();
                for (int i = 0; body.getChild(0).getChild(1).getChildren() > i; i++)
                {
                    inBuffer.append("\"" + body.getChild(0).getChild(1).getChild(i).getToken().getText() + "\", ");
                }
                inBuffer.delete(inBuffer.length() - 2, inBuffer.length());
                stdIn = String.format(stdInMethod, inBuffer.toString());
            }
            if (body.getChild(0).getChildren() > 1 && body.getChild(0).getChild(2).getChildren() > 0)
            {
                StringBuffer outBuffer = new StringBuffer();
                for (int i = 0; body.getChild(0).getChild(2).getChildren() > i; i++)
                {
                    outBuffer.append("\"" + body.getChild(0).getChild(2).getChild(i).getToken().getText() + "\", ");
                }
                outBuffer.delete(outBuffer.length() - 2, outBuffer.length());
                stdOut = String.format(stdOutMethod, outBuffer.toString());
            }
        }
        
        if (result == null)
        {
            String r = String.format(invoke, subject, name, args);
            return String.format(testMethod, description, testNum++,
                    stdIn,
                    String.format(method, r, r),
                    stdOut);
        }
        else
        {
            return String.format(testMethod, description, testNum++,
                    stdIn,
                    String.format(method, String.format(invoke, subject, name, args), result),
                    stdOut);
        }
    }
}
