package wctest.rt;

import java.io.File;

/**
 * Test Case superclass from which all generated classes will extend. Provides
 * the necessary functionality defined in WC Test
 * Created on: Jul 25, 2015 at 11:34:14 PM
 * @author Matthew Edwards
 */
public class TestSet
{
    /**
     * TestSet initializer
     */
    public TestSet()
    {
        //
    }
    
    /**
     * Called before each test in order to prepare it to run
     */
    protected void setUp()
    {
        //
    }
    
    /**
     * Called after each test completes to reset the test
     */
    protected void tearDown()
    {
        //
    }

    /**
     * Data structure representing a double (floating-point) value and an optional
     * margin of error
     * Created on: Aug 3, 2015 at 8:22:33 PM
     * @author Matthew Edwards
     */
    public static class DoubleValue
    {
        /**
         * Creates new DoubleValue from a String
         * @param value Floating-Point value as a String
         */
        public DoubleValue(String value)
        {
            //
        }
        
        /**
         * Creates new DoubleValue from a String with a margin of error
         * @param value Floating-Point value as a String
         * @param delta Margin of error
         */
        public DoubleValue(String value, double delta)
        {
            //
        }
    }
    
    /**
     * Represents a single method call defined in the test source code
     * Created on: Aug 3, 2015 at 8:25:53 PM
     * @author Matthew Edwards
     */
    public static class MethodCall
    {
        //
    }

    /**
     * Sets the system input for the test
     * @param lines Strings to set as input
     */
    public void setSystemIn(String... lines)
    {
        //
    }
    
    /**
     * Sets the system input for the test using the contents of the specified file
     * @param content File to read as input
     */
    public void setSystemIn(File content)
    {
        //
    }
    
    /**
     * Asserts that the system output will be the specified strings
     * @param lines Strings to expect for output
     */
    public void assertSystemOutIs(String... lines)
    {
        //
    }
    
    /**
     * Asserts that the system output will be the contents of the specified file
     * @param content File to read as expected output
     */
    public void assertSystemOutIs(File content)
    {
        //
    }

    /**
     * Invokes a method on a receiver object
     * @param receiver Object to invoke the method on
     * @param method Name of the method to invoke
     * @param args Method arguments
     * @return MethodCall representing this method invocation
     */
    public static MethodCall invoke(
        Object receiver, String method, Object... args) { return null; }
    
    /**
     * Invokes a method on a receiver object
     * @param receiver Class to invoke the method on
     * @param method Name of the method to invoke
     * @param args Method arguments
     * @return MethodCall representing this method invocation
     */
    public static MethodCall invoke(
        Class<?> receiver, String method, Object... args) { return null; }
    
    /**
     * Invokes a method on a receiver object
     * @param receiver Name of the class to invoke the method on
     * @param method Name of the method to invoke
     * @param args Method arguments
     * @return MethodCall representing this method invocation
     */
    public static MethodCall invoke(
        String receiver, String method, Object... args) { return null; }
    
    /**
     * Invokes a method on a receiver object
     * @param receiver MethodCall to invoke the method on
     * @param method Name of the method to invoke
     * @param args Method arguments
     * @return MethodCall representing this method invocation
     */
    public static MethodCall invoke(
        MethodCall receiver, String method, Object... args) { return null; }

    /**
     * Data Structure representing a range bounded by a lower and upper limit.
     * (Configurably inclusive or exclusively limited)
     * Created on: Aug 3, 2015 at 8:36:06 PM
     * @author Matthew Edwards
     */
    public static class Range
    {
        /**
         * Creates new Range representation
         * @param lower Lower limit of this range
         * @param upper Upper limit of this range
         * @param inclusive True if this range is inclusive of the lower and upper limits
         */
        public Range(Object lower, Object upper, boolean inclusive)
    {
        //
    }
    }
    
    /**
     * Asserts that the test result is equivalent to the specified result object
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIs(Object actual, Object expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is within the specified Range
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIs(Object actual, Range expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equivalent to the specified result object
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNot(Object actual, Object expected)
    {
        //
    }

    /**
     * Asserts that the test result is an instance of the specified class
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsInstanceof(Object actual, Class<?> expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is an instance of the specified class (as a String literal)
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsInstanceof(Object actual, String expected)
    {
        //
    }

    /**
     * Asserts that the test result is equal to the specified byte
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsEq(Object actual, byte expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is equal to the specified short
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsEq(Object actual, short expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is equal to the specified int
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsEq(Object actual, int expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is equal to the specified long
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsEq(Object actual, long expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is equal to the specified float
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsEq(Object actual, float expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is equal to the specified double
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsEq(Object actual, double expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is equal to the specified char
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsEq(Object actual, char expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is equal to the specified object
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsEq(Object actual, Object expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equal to the specified byte
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNotEq(Object actual, byte expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equal to the specified short
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNotEq(Object actual, short expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equal to the specified int
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNotEq(Object actual, int expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equal to the specified long
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNotEq(Object actual, long expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equal to the specified float
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNotEq(Object actual, float expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equal to the specified double
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNotEq(Object actual, double expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equal to the specified char
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNotEq(Object actual, char expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is not equal to the specified object
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsNotEq(Object actual, Object expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than the specified byte
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLt(Object actual, byte expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than the specified short
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLt(Object actual, short expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than the specified int
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLt(Object actual, int expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than the specified long
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLt(Object actual, long expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than the specified float
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLt(Object actual, float expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than the specified double
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLt(Object actual, double expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than the specified char
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLt(Object actual, char expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than the specified object
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLt(Object actual, Object expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than or equal to the specified byte
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLte(Object actual, byte expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than or equal to the specified short
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLte(Object actual, short expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than or equal to the specified int
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLte(Object actual, int expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than or equal to the specified long
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLte(Object actual, long expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than or equal to the specified float
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLte(Object actual, float expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than or equal to the specified double
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLte(Object actual, double expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than or equal to the specified char
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLte(Object actual, char expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is less than or equal to the specified object
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsLte(Object actual, Object expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than the specified byte
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGt(Object actual, byte expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than the specified short
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGt(Object actual, short expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than the specified int
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGt(Object actual, int expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than the specified long
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGt(Object actual, long expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than the specified float
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGt(Object actual, float expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than the specified double
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGt(Object actual, double expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than the specified char
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGt(Object actual, char expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than the specified object
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGt(Object actual, Object expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than or equal to the specified byte
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGte(Object actual, byte expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than or equal to the specified short
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGte(Object actual, short expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than or equal to the specified int
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGte(Object actual, int expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than or equal to the specified long
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGte(Object actual, long expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than or equal to the specified float
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGte(Object actual, float expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than or equal to the specified double
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGte(Object actual, double expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than or equal to the specified char
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGte(Object actual, char expected)
    {
        //
    }
    
    /**
     * Asserts that the test result is greater than or equal to the specified object
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertIsGte(Object actual, Object expected)
    {
        //
    }

    /**
     * Asserts that the test result matches the specified regex
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertMatches(Object actual, String expected)
    {
        //
    }
    
    /**
     * Asserts that the test result throws the specified throwable
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertThrows(MethodCall actual, Throwable expected)
    {
        //
    }
    
    /**
     * Asserts that the test result throws the specified throwable (as a String literal)
     * @param actual Test result value
     * @param expected Expected result value
     */
    public void assertThrows(MethodCall actual, String expected)
    {
        
    }
}
