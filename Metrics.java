import java.util.*;
/**
 * Abstract class Metrics - generates metrics for a text file
 * 
 * @author Jason Isaacs
 * @version v1.2
 */
public abstract class Metrics
{
    //private static int wordCount, sentenceCount, symbolCount, uniqueWordCount;
   
    public Metrics() {
        
    }
    
    /** 
     * Calculate number of sentences in a String deliminated by puncutation.
     * 
     * @param text - String to count number of sentences of
     *///return int?-----------------------------------------------------------
    public static int getSentenceCount(String text) {
        String[] sentences;
        sentences = text.trim().split("[(?!.)+]");//////////////////////test reg exp with +------------------------------------------------------------
        return sentences.length;
        
    }
    
    /** 
     * Calculate number of words in a String deliminated by spaces.
     * 
     * @param text - String to count number of words of
     */
    public static int getWordCount(String text) {
        String[] words;
        StringBuilder textWithoutPunctuation = getRidOfPunctuation(text);
        words = textWithoutPunctuation.toString().trim().split("\\s+");
        return words.length - countEmptyStrings(words);
    } 
    
    private static int countEmptyStrings(String[] words) {
        int emptyStringCount = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("") || words[i].equals(" "))
                emptyStringCount++;
        }    
        return emptyStringCount;
    }
    
    private static StringBuilder getRidOfPunctuation(String text) {
        StringBuilder textWithoutPunctuation = new StringBuilder();
        for (int i = 0; i < text.length(); i ++) {
            if (Character.isLetter(text.charAt(i)) || Character.isWhitespace(text.charAt(i)) || text.charAt(i) == ('-') )
                textWithoutPunctuation.append(text.charAt(i));
           // else
            //    textWithoutPunctuation.append(" ");
        } 
        return textWithoutPunctuation;
    }
    
    /** 
     * Calculate number of unique words in a String.
     * 
     * @param text - String to count number of words of
     */
    public static int getUniqueWordCount(String text) {
        String[] words;
        Map<String, Integer> uniqueWords = new TreeMap<String, Integer>();
        Integer freq = null;
        
        StringBuilder letterOnlyText = getRidOfPunctuation(text);
        words = letterOnlyText.toString().split("\\s+");        
        
        for (int i = 0; i < words.length; i++) {
            freq = (uniqueWords.get(words[i]));
            if (freq == null) {
                uniqueWords.put(words[i], 1);
            }    
        }    
        return uniqueWords.size();
    } 
    
    /** 
     * Calculate number of letters in a String.
     * 
     * @param text - String to count number of symbols of
     */
    public static int getSymbolCount(String text) {
        int length = text.length();
        int symbolCount = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) != ' ')
                symbolCount++;
        }        
        return symbolCount;
    } 
    
    /** 
     * Create a Map with the frequency of each symbol that appears in the string.
     * 
     * @param text - string from which to generate the map
     */
    abstract Map<Character, Double> getSymbolFrequencyMap();
    
    /** 
     * Create a Map with the frequency of symbol-pairs (bi-grams) that appears in the string.
     * 
     * @param text - string from which to generate the map
     */
    abstract Map<String, Double> getSymbolPairFrequencyMap();
    
    /** 
     * Generate average word Length of supplied string.
     * 
     * @param text - string from which to generate average word length
     */
    abstract double avgWordLength();
    
    /** 
     * Generate average sentence Length of supplied string.
     * 
     * @param text - string from which to generate average sentence length
     */
    abstract double avgSentenceLength();
    
    /** 
     * Generate word to sentence ratio of supplied string.
     * 
     * @param text - string from which to generate ratio
     */
    abstract double wordToSentenceRatio();
    
    /** 
     * Generate metric for word variety of supplied string.
     * 
     * @param text - string from which to generate metric
     */
    abstract double vocabularyWidth();
    
}
