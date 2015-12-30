import java.util.*;
import java.io.*;
/**
 * Generates metrics for a text 
 * Caveats:
 * words are white-space delineated strings
 * sentences are delineated by punctuation(.!?)
 * 
 * 
 * @author Jason Isaacs 
 * @version v1.2
 */
public class BookMetrics extends Metrics
{
    private int wordCount, symbolCount, sentenceCount, uniqueWordCount;
    private double avgWordLength, avgSentenceLength, wordToSentenceRatio, vocabWidth, 
                    mostFrequentLetterFrequency, mostFrequentBiGramFrequency, mostFrequentTriGramFrequency, mostFrequentFourGramFrequency;
    String text, mostFrequentBiGram, mostFrequentLetter, mostFrequentTriGram, mostFrequentFourGram;
   
    /** 
     * Takes a string representing a file path if isFile is true, otherwise string represents text.
     * 
     * @param sequence - string containing file path to file containing text or simply a string of text
     * @param isFile - if true sequence is the filename to a text-file
     */
    public BookMetrics(String sequence, boolean isFile) {
        if (isFile) {
            text = getTextFromFile(sequence);
        }  
        else {
            text = sequence;
        }    
        sentenceCount = super.getSentenceCount(text);
        wordCount = super.getWordCount(text);
        symbolCount  = super.getSymbolCount(text);
        uniqueWordCount = super.getUniqueWordCount(text);
    }
    
    /**
     * Extracts text from a file.
     * 
     * @param file - name of file containing text
     * 
     * @return string representing text from file
     */
    private String getTextFromFile(String file) {
        StringBuilder textFromFile = new StringBuilder();
        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(new FileReader(file));
            String line;
            while ((line = inputStream.readLine()) != null) {
                   textFromFile.append(line + " ");
            }
        } 
        catch (IOException e) {
            System.out.println("An error has occurred, the inputStream will be closed, please make sure you have a file to read from: \n" + e);  
        } 
        finally {
            if (inputStream != null) {
                try {
                     inputStream.close();
                }
                catch (IOException e){
                    System.out.println("An error has occurred, the inputStream did not close \n" + e);
                }
            }
        }
        return textFromFile.toString();
    }
    
    /** 
     * Create a Map with the frequency of each symbol that appears in the string.
     * 
     * @param text - string from which to generate the map
     */
    public Map<Character, Double> getSymbolFrequencyMap() {
        Map<Character, Double> letterFrequency = new TreeMap<Character, Double>();
        Double freq = new Double(0);
        Character textCharacter;
        for(int i = 0; i < text.length(); i++) {
                textCharacter = new Character(text.charAt(i));
                if (!Character.isWhitespace(textCharacter)) {
                    freq = (letterFrequency.get(textCharacter));
                    letterFrequency.put(textCharacter, (freq == null) ? 1 : freq + 1);
                }           
        }
        normalizeSymbolMap(letterFrequency);    
        
        return letterFrequency;
    }    
    
    private Map<Character, Double> normalizeSymbolMap(Map<Character, Double> letterFrequency) {
        Double freq; 
        Double mostFrequent = new Double(0);
        Character textCharacter;
        Set symbolsInMap = letterFrequency.entrySet();
        Iterator iterateSymbols = symbolsInMap.iterator();
        while (iterateSymbols.hasNext()) {
            Map.Entry symbolEntries = (Map.Entry) iterateSymbols.next();
            textCharacter = (Character) symbolEntries.getKey();
            freq = letterFrequency.get(textCharacter);
            letterFrequency.put(textCharacter, (freq/text.length()));
            if (freq != null && freq > mostFrequent) {
                mostFrequent = freq;
                mostFrequentLetter = textCharacter.toString();                        
            }
        }
        mostFrequentLetterFrequency = mostFrequent / text.length();
        return letterFrequency;
    }
    
    /** 
     * Create a Map with the frequency of tri-grams that appears in the string.
     * 
     * @param text - string from which to generate the map
     */
    public Map<String, Double> getTriGramFrequencyMap() {
        Map<String, Double> trigramMap = new TreeMap<String, Double>();
        Double freq = new Double(0);
        Double mostFrequent = new Double(0);
        for (int i = 0; i < text.length() - 2; i++) {
            String tgram = text.substring(i, i + 3);
            if (!tgram.equals(" th") && !tgram.equals("the") && !tgram.equals("he ")) {
                freq = (trigramMap.get(tgram));
                trigramMap.put(tgram, (freq == null) ? 1 : freq + 1);
				
            }
			if  (freq != null && freq > mostFrequent){
                mostFrequent = freq;
                mostFrequentTriGram = tgram;
            }   
        }   
        mostFrequentTriGramFrequency = mostFrequent / text.length();
        return trigramMap;
    }    
    
    /** 
     * Create a Map with the frequency of 4-grams that appears in the string.
     * 
     * @param text - string from which to generate the map
     */
    public Map<String, Double> getFourGramFrequencyMap() {
        Map<String, Double> fourgramMap = new TreeMap<String, Double>();
        Double freq = new Double(0);
        Double mostFrequent = new Double(0);
        for (int i = 0; i < text.length() - 3; i++) {
            String fourgram = text.substring(i, i + 4);
            if (!fourgram.equals(" the") && !fourgram.equals("the ")) {
                freq = (fourgramMap.get(fourgram));
                fourgramMap.put(fourgram, (freq == null) ? 1 : freq + 1);
            }
            if (freq != null && freq > mostFrequent){
                        mostFrequent = freq;
                        mostFrequentFourGram = fourgram;
            } 
        } 
        mostFrequentFourGramFrequency = mostFrequent / text.length();
        return normalizeGramMap(fourgramMap);
    }    
    
    /** 
     * Create a Map with the frequency of symbol-pairs (bi-grams) that appears in the string.
     * 
     * @param text - string from which to generate the map
     */
    public Map<String, Double> getSymbolPairFrequencyMap() {
        Map<String, Double> letterPairMap = new TreeMap<String, Double>();
        Double freq = new Double(0);
        Double mostFrequent = new Double(0);
        for (int i = 0; i < text.length() - 1; i++) {
            String letterPair = text.substring(i, i + 2);
            if (!letterPair.equals("th") && !letterPair.equals("th") && !letterPair.equals("he") && !letterPair.equals("e ") && !letterPair.equals(" t"))  {
                freq = (letterPairMap.get(letterPair));
                letterPairMap.put(letterPair, (freq == null) ? 1 : freq + 1);
            }
            if (freq != null && freq > mostFrequent){
                        mostFrequent = freq;
                        mostFrequentBiGram = letterPair;
            }
        } 
        
        mostFrequentBiGramFrequency = mostFrequent / text.length();
        return normalizeGramMap(letterPairMap);
    }    
    
    private Map<String, Double> normalizeGramMap(Map<String, Double> gramFrequencyMap) {
        Double freq; 
        String nGram;
        Set nGramsInMap = gramFrequencyMap.entrySet();
        Iterator iterateSequence = nGramsInMap.iterator();
        while (iterateSequence.hasNext()) {
            Map.Entry symbolEntries = (Map.Entry) iterateSequence.next();
            nGram = (String) symbolEntries.getKey();
            freq = gramFrequencyMap.get(nGram);
            gramFrequencyMap.put(nGram, (freq/text.length()));
        }
        return gramFrequencyMap;
    }
    
    /** 
     * Generate average word Length of supplied string.
     * 
     * @return wordLength - numeric average length of words used in text
     */
    public double avgWordLength() {
        if (wordCount == 0)
            return 0;
        else    
            return (double) symbolCount / wordCount;
    }    
    
    /** 
     * Generate average sentence Length of supplied string.
     * 
     * @param sentenceLength - numeric average length of sentences used in text
     */
    public double avgSentenceLength() {
        if (sentenceCount == 0)
            return 0;
        else
            return (double) wordCount/sentenceCount;
    }    
    
    /** 
     * Generate word to sentence ratio of supplied string.
     * 
     * @return ratio -  ratio of words to sentences
     */
    public double wordToSentenceRatio() {
        double avgSentenceLength = avgSentenceLength(); 
        double avgWord = avgWordLength();
        if (avgSentenceLength == 0)
            return 0;
        else
            return avgWordLength() / avgSentenceLength;
    }    
    
    /** 
     * Generate metric for word variety of supplied string.
     * 
     * @return width - ratio of unique words to total words used in text
     */
    public double vocabularyWidth() {
        if (wordCount == 0)
            return 0;
        else
            return (double) uniqueWordCount / wordCount;
    }  
    
    /** 
     * Provides string with most frequent letter used in text.
     * 
     * @return mostFrequentLetter - the most frequent letter used in text.
     */
    public String getMostFrequentLetter() {
        return mostFrequentLetter;
    }    
    /** 
     * Provides frequency of the most frequent letter used in text.
     * 
     * @return mostFrequentLetterFrequency - frequency of the most frequent letter used in text.
     */
    public double getMostFrequentLetterFrequency() {
        return mostFrequentLetterFrequency;
    }
    
    /** 
     * Provides string with most frequent bi-gram used in text.
     * 
     * @return mostFrequentBiGram - the most frequent bi-gram used in text.
     */
    public String getMostFrequentBiGram() {
        return mostFrequentBiGram;
    }    
    /** 
     * Provides frequency of the most frequent bi-gram used in text.
     * 
     * @return mostFrequentBiGramFrequency - frequency of the most frequent bi-gram used in text.
     */
    public double getMostFrequentBiGramFrequency() {
        return mostFrequentBiGramFrequency;
    }
    
    /** 
     * Provides string with most frequent tri-gram used in text.
     * 
     * @return mostFrequentTriGram - the most frequent tri-gram used in text.
     */
    public String getMostFrequentTriGram() {
        return mostFrequentTriGram;
    }    
    /** 
     * Provides frequency of the most frequent tri-gram used in text.
     * 
     * @return mostFrequentTriGramFrequency - frequency of the most frequent tri-gram used in text.
     */
    public double getMostFrequentTriGramFrequency() {
        return mostFrequentBiGramFrequency;
    }
}
