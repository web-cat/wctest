package wctest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

import wctest.translator.InvalidSyntaxException;
import wctest.translator.Lexer;
import wctest.translator.Parser;
import wctest.translator.Token;
import wctest.translator.TokenTree;
import wctest.translator.TokenType;

/**
 * Handles conversion of the Abstract Syntax Tree into source code for the target
 * language test case.
 * <br>
 * Created on: Jul 15, 2015 at 9:35:58 PM
 * @author Matthew Edwards
 */
public class Translator
{
    private static final String header = "header";
    private static final String footer = "footer";

    private static final String testMethod = "testMethod";
    
    private static final String invoke           = "invoke";
    
    private static final String nullMethod       = "assertIs";
    private static final String isMethod         = "assertIs";
    private static final String isNotMethod      = "assertIsNot";
    private static final String isEqMethod       = "assertIsEq";
    private static final String isNotEqMethod    = "assertIsNotEq";
    private static final String isLtMethod       = "assertIsLt";
    private static final String isLteMethod      = "assertIsLte";
    private static final String isGtMethod       = "assertIsGt";
    private static final String isGteMethod      = "assertIsGte";
    private static final String matchesMethod    = "assertMatches";
    private static final String instanceofMethod = "assertInstanceOf";
    private static final String throwsMethod     = "assertThrows";

    private static final String stdInMethod      = "stdInMethod";
    private static final String stdOutMethod     = "stdOutMethod";
    
    private static final String floatWrapper     = "floatWrapper";
    private static final String rangeWrapper     = "rangeWrapper";
    
    /**
     * Called to start Translator
     * @param args -o Set Output
     * <br>        -i Set Input
     * <br>        --java Generate Java Test
     */
    public static void main(String[] args)
    {
        int index = 0;
        String fileName = null;
        try
        {
            Properties java = new Properties();
            java.load(Translator.class.getClassLoader().getResourceAsStream("java.properties"));

            Properties cpp = new Properties();
            cpp.load(Translator.class.getClassLoader().getResourceAsStream("cpp.properties"));
            
            Properties python = new Properties();
            python.load(Translator.class.getClassLoader().getResourceAsStream("python.properties"));
            
            Properties ruby = new Properties();
            ruby.load(Translator.class.getClassLoader().getResourceAsStream("ruby.properties"));
            
            while (args.length > index)
            {
                if (args[index].equalsIgnoreCase("-o"))
                {
                    index++;
                    System.out.println("Setting output to " + args[index++]);
                }
                else if (args[index].equalsIgnoreCase("-i"))
                {
                    index++;
                    System.out.println("Setting input to " + args[index++]);
                }
                else if (args[index].equalsIgnoreCase("--java"))
                {
                    index++;
                    System.out.println("Generating Java Test...\n");
                    System.out.println(translate(fileName, java));
                }
                else if (args[index].equalsIgnoreCase("--cpp"))
                {
                    index++;
                    System.out.println("Generating C++ Test...\n");
                    System.out.println(translate(fileName, cpp));
                }
                else if (args[index].equalsIgnoreCase("--python"))
                {
                    index++;
                    System.out.println("Generating Python Test...\n");
                    System.out.println(translate(fileName, python));
                }
                else if (args[index].equalsIgnoreCase("--ruby"))
                {
                    index++;
                    System.out.println("Generating Ruby Test...\n");
                    System.out.println(translate(fileName, ruby));
                }
                else
                {
                    fileName = args[index++];
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.err.println("Invalid Arguments! Syntax error on token \"" + args[index--] + "\"\nProper usage: fileName [-o outputFile] [-i inputFile] [--java] [--cpp] [--ruby] [--python]");
        }
        catch (IOException e)
        {
            System.err.println("Could not read file \"" + fileName + "\"");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static Properties lang;
    private static int testNum;
    
    /**
     * Translates WC Test Source into source code for target language
     * @param fileName Name of file to translate
     * @param lang Target language translation properties file
     * @return Translated source code for target language
     * @throws IOException 
     * @throws URISyntaxException 
     */
    public static String translate(String fileName, Properties lang) throws IOException
    {
        File sourceFile = new File(fileName);
        
        String source = new String(Files.readAllBytes(sourceFile.toPath()));    // Read file into string
        
        ArrayList<Token> tokens = Lexer.tokenize(source);                       // Tokenize source file
        
        TokenTree root = Parser.parse(tokens);                                  // Parse tokens into AST
        
        StringBuffer sb = new StringBuffer();
        Translator.lang = lang;
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
                sb.append(String.format(lang.getProperty(header), testName, subject));
                body.removeChild(0);
            }
            else
            {
                return error(body.getChild(0).getToken());
            }
            
            sb.append(body(testName, body));
        }
        
        sb.append(lang.getProperty(footer));
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
                sb.append(getFormattedInsert(body.getChild(i), lang.getProperty(nullMethod), description, null));
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
                    result = String.format(lang.getProperty(rangeWrapper), body.getChild(i).getChild(1).getChild(0).getToken().getText(),
                            body.getChild(i).getChild(1).getChild(1).getToken().getText(),
                            body.getChild(i).getChild(1).getChild(2).getToken().getText());
                }
                else
                {
                    result = body.getChild(i).getChild(1).getToken().getText();

                    if (body.getChild(i).getChildren() == 3)
                    {
                        result = String.format(lang.getProperty(floatWrapper), "\"" + result + "\"", ", " + body.getChild(i).getChild(2).getToken().getText());
                    }
                    else if (body.getChild(i).getChild(1).getToken().getType() == TokenType.FLOAT)
                    {
                        result = String.format(lang.getProperty(floatWrapper), "\"" + result + "\"", "");
                    }
                }
                
                String method = lang.getProperty(isMethod);
                
                if (body.getChild(i).getToken().getType() == TokenType.IS_NOT)
                {
                    method = lang.getProperty(isNotMethod);
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_EQ)
                {
                    method = lang.getProperty(isEqMethod);
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_NOT_EQ)
                {
                    method = lang.getProperty(isNotEqMethod);
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_LT)
                {
                    method = lang.getProperty(isLtMethod);
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_LTE)
                {
                    method = lang.getProperty(isLteMethod);
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_GT)
                {
                    method = lang.getProperty(isGtMethod);
                }
                else if (body.getChild(i).getToken().getType() == TokenType.IS_GTE)
                {
                    method = lang.getProperty(isGteMethod);
                }
                
                sb.append(getFormattedInsert(body.getChild(i), method, description, result));
            }
            else if (body.getChild(i).getToken().getType() == TokenType.MATCHES)
            {
                String result = body.getChild(i).getChild(1).getToken().getText();
                sb.append(getFormattedInsert(body.getChild(i), lang.getProperty(matchesMethod), description,
                        "\"" + result.substring(1, result.length() - 1).replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\"") + "\""));
            }
            else if (body.getChild(i).getToken().getType() == TokenType.INSTANCEOF)
            {
                sb.append(getFormattedInsert(body.getChild(i), lang.getProperty(instanceofMethod), description, body.getChild(i).getChild(1).getToken().getText())); // TODO
            }
            else if (body.getChild(i).getToken().getType() == TokenType.THROWS)
            {
                sb.append(getFormattedInsert(body.getChild(i), lang.getProperty(throwsMethod), description, body.getChild(i).getChild(1).getToken().getText())); // TODO
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
                String inv = String.format(lang.getProperty(invoke), subject, name, args);
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
                    inv = String.format(lang.getProperty(invoke), inv, newName, newArgs);
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
                stdIn = String.format(lang.getProperty(stdInMethod), inBuffer.toString());
            }
            if (body.getChild(0).getChildren() > 1 && body.getChild(0).getChild(2).getChildren() > 0)
            {
                StringBuffer outBuffer = new StringBuffer();
                for (int i = 0; body.getChild(0).getChild(2).getChildren() > i; i++)
                {
                    outBuffer.append("\"" + body.getChild(0).getChild(2).getChild(i).getToken().getText() + "\", ");
                }
                outBuffer.delete(outBuffer.length() - 2, outBuffer.length());
                stdOut = String.format(lang.getProperty(stdOutMethod), outBuffer.toString());
            }
        }
        
        if (result == null)
        {
            String r = String.format(lang.getProperty(invoke), subject, name, args);
            return String.format(lang.getProperty(testMethod), description, testNum++,
                    stdIn,
                    String.format(method, r, r),
                    stdOut);
        }
        else
        {
            return String.format(lang.getProperty(testMethod), description, testNum++,
                    stdIn,
                    String.format(method, String.format(lang.getProperty(invoke), subject, name, args), result),
                    stdOut);
        }
    }
}
