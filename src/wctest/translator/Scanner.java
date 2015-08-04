package wctest.translator;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import wctest.*;

/**
 * Reads a specified source code file into a string for parsing and generates
 * an Abstract Syntax Tree.
 * <br>
 * Created on: Jul 15, 2015 at 7:56:01 PM
 * @author Matthew Edwards
 */
public class Scanner
{
    /**
     * Reads a specified source code file into a string for parsing and generates
     * an Abstract Syntax Tree.
     * @param args Method Arguments
     * @throws Exception Exception thrown
     */
    public static void main(String[] args) throws Exception
    {
        File sourceFile = new File(Scanner.class.getClassLoader()
                .getResource("me/edwards/wctest/files/samples/wctest_sample.txt").toURI());
        
        String source = new String(Files.readAllBytes(sourceFile.toPath()));    // Read file into string
        
        ArrayList<Token> tokens = Lexer.tokenize(source);                       // Tokenize source file
        
        TokenTree root = Parser.parse(tokens);                                  // Parse tokens into AST
        
        String translatedSource = Translator.translate(root);                   // Translate AST into source
        
        System.out.println(translatedSource);
    }
}
