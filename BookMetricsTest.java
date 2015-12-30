
import java.util.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class BookMetricsTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BookMetricsTest
{
    private BookMetrics testBookMetrics, joyceMetrics;
    private Map<Character, Double> testMap;
    private Map<String, Double> testMap2;

    /**
     * Default constructor for test class BookMetricsTest
     */
    public BookMetricsTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        //testBookMetrics = new BookMetrics("");
        //joyceMetrics = new BookMetrics("uly.txt", true);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Test sample of values in Map returned by getSymbolFrequencyMap. 
     */
    @Test
    public void getSymbolFreqMapTest()
    {
        testBookMetrics = new BookMetrics("Get the frequency of each letter of this sentence", false);
        testMap = testBookMetrics.getSymbolFrequencyMap();
        assertEquals(Double.valueOf(.0204), testMap.get('a'), .001);
        assertNull(testMap.get('b'));
        assertEquals(Double.valueOf(.20408), testMap.get('e'), .001);
        assertEquals(Double.valueOf(.12244), testMap.get('t'), .001);
        System.out.println(testBookMetrics.getMostFrequentLetter() + " : " + testBookMetrics.getMostFrequentLetterFrequency()); 
    }
    /**
     * Test sample of values in Map returned by getSymbolPairFrequencyMap 
     */
    @Test
    public void getSymbolPairFreqTest()
    {
        testBookMetrics = new BookMetrics("This sentence will be split into bigrams and then is entered in a Map", false);
        testMap2 = testBookMetrics.getSymbolPairFrequencyMap();
        assertEquals(Double.valueOf(.014492), testMap2.get("Th"), .001);
        assertEquals(Double.valueOf(.0289), testMap2.get("is"), .001);
        assertEquals(Double.valueOf(.05797), testMap2.get("en"), .001);
        //assertEquals(Double.valueOf(.014492), testMap2.get("th"), .001);
        System.out.println(testBookMetrics.getMostFrequentBiGram() + " : " + testBookMetrics.getMostFrequentBiGramFrequency()); 
        
    //    testBookMetrics = new BookMetrics("aaa b c i ", false);
      //  testMap2 =  testBookMetrics.getSymbolPairFrequencyMap();
    }
    /**
     * Test values returned by avgWordLength 
     */
    @Test
    public void avgWordLengthTest()
    {
        
        testBookMetrics = new BookMetrics(" The average word length of this string is", false);
        double tester = testBookMetrics.avgWordLength();
        assertEquals(4.25, tester, 0.1);
        testBookMetrics = new BookMetrics(" ", false);
        tester = testBookMetrics.avgWordLength();
        assertEquals(0, tester, 0.0);
        testBookMetrics = new BookMetrics(" kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk ", false);
        tester = testBookMetrics.avgWordLength();
        assertEquals(60, tester, 0.0);
        testBookMetrics = new BookMetrics("a kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk ", false);
        assertEquals(30.5, testBookMetrics.avgWordLength(), 0.1);
    }
    /**
     * Test values returned by avgSentenceLength 
     */
    @Test
    public void getAverageSentenceLengthTest()
    {
        testBookMetrics = new BookMetrics("this. sentence. is very! choppy? and is going. to yield. a. small! number.", false);
        double tester = testBookMetrics.avgSentenceLength();
        assertEquals(1.4444, tester, 0.1); 
        testBookMetrics = new BookMetrics(" .  !  ? ? ..    !!!???", false);
        //testBookMetrics = new BookMetrics("uly.txt", true);
        tester = testBookMetrics.avgSentenceLength();
        assertEquals(0, tester, 0.0);
        
        testBookMetrics = new BookMetrics("?a.b!.c.d.f.g.a a??", false);
        tester = testBookMetrics.avgSentenceLength();
        //assertEquals(.875, tester, 0.1);
        
        testBookMetrics = new BookMetrics("these are conventional sentences. And they should be easy to analyze. Unless something goes wrong. Nothing can go wrogn! right?", false);
        assertEquals(4, testBookMetrics.avgSentenceLength(), 0.1);
    }
    /**
     * Test values returned by wordToSentenceRatio 
     */
    @Test
    public void wordToSentenceRatioTest()
    {
        testBookMetrics = new BookMetrics("this will measure word to sentence ratio. Will this go well?  Should be straight forward on conventional sentences. Hopefully nobody inputs Ulysses!", false);
        double tester = testBookMetrics.wordToSentenceRatio();
        assertEquals(1.04132, tester, 0.01);
        
        testBookMetrics = new BookMetrics(" ", false);
        assertEquals(0, testBookMetrics.wordToSentenceRatio(), 0.0);
        
        testBookMetrics = new BookMetrics(" one fairly long run -on  sentence to be analyzed and then tested to see if the correct values are returned by the  method under test when this is run by the user of the test that is being run right now and is almost done", false);//45
        assertEquals(.08592, testBookMetrics.wordToSentenceRatio(), 0.001);
        
        testBookMetrics = new BookMetrics("a. series! of! short? quasi -sentences! using. a variety. of! punctuation. to. test. word. to! sentence? length.", false);
        assertEquals(4.98, testBookMetrics.wordToSentenceRatio(), 0.01);
    }
    /**
     * Test values returned by vocabularyWidth 
     */
    @Test
    public void vocabularyWidthTest()
    {
        testBookMetrics = new BookMetrics("the the the the the the one the the the the one the", false);
        assertEquals(.1538, testBookMetrics.vocabularyWidth(), 0.001);
        
        testBookMetrics = new BookMetrics("each word in this sentence is unique so there should be a high variance for the result of testing it", false);
        assertEquals(1, testBookMetrics.vocabularyWidth(), 0.0);
        
        testBookMetrics = new BookMetrics(" !", false);
        assertEquals(0, testBookMetrics.vocabularyWidth(), 0.0);
        
        testBookMetrics = new BookMetrics(" ", false);
        assertEquals(0, testBookMetrics.vocabularyWidth(), 0);
        
        testBookMetrics = new BookMetrics("one.", false);
        assertEquals(1, testBookMetrics.vocabularyWidth(), 0);
    }
}
