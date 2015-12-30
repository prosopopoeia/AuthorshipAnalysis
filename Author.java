import java.util.*;
/**
 * Class to hold information pertaining to an author
 * 
 * @author Jason Isaacs 
 * @version v1.2
 */
public class Author
{
    private String name;
    private double avgWordLength, avgSentenceLength, wordToSentenceRatio, vocabWidth;
    private Map<String, Double> bigramMap, trigramMap, fourgramMap;
    private Map<Character, Double> symbolMap;
    // private BookMetrics metrics;
    
    /**
     * Creates an unknown author
     */
    public Author() {
    }
    
    /**
     * Create author by supplying name and book metrics
     * @param name - name of author
     * @param metrics - BookMetrics object
     */
    public Author(String name, BookMetrics metrics) {
        this.name = name;
        //this.metrics = metrics;
        //extractMetrics(metrics);
    }    
    
    /**
     * Creates author containing the author's name, a filePath to text authored by author, and the following metrics: <br /> 
     * - average word length <br/>
     * - average sentence length<br/>
     * - average word length to average sentence length ratio<br/>
     * - richness of the texts vocabulary (or vocabulary width) <br/>
     * - a mapping of symbol frequencies<br/>
     * - a mapping of bi-gram frequencies<br/>
     * 
     * @param name - name of author
     * @param avgWordLength - average word length
     * @param avgSentenceLength - average sentence length
     * @param wordToSentenceRatio - ratio of average word length to average sentence length
     * @param vocabWidth - ratio of unique words to total words
     * @param bigramMap - map of bi-grams of text
     * @param symbolMap - map of symbols in text
     */
    public Author(String name, double avgWordLength, double avgSentenceLength, double wordToSentenceRatio, 
                        double vocabWidth, Map<String, Double> bigramMap, Map<Character, Double> symbolMap) {
        this.name = name;
        this.avgWordLength = avgWordLength;
        this.avgSentenceLength = avgSentenceLength;
        this.wordToSentenceRatio = wordToSentenceRatio;
        this.vocabWidth = vocabWidth;
        this.bigramMap = bigramMap;
        this.symbolMap = symbolMap;
    }
    
    /**
     * Compares two authors and provides an array of possible authors with a degree of match 
     * @param comparedAuthor - author to be compared to this author
     * @return degreeOfMatch - metric indicating degree of match
     */
    public double compares(Author comparedAuthor) {
        double wordLengthDifference, sentenceLengthDifference, wordSentenceRatio, vocabWidth,
            bigram, symbol, trigram, fourgram, degreeOfMatch;
        
        wordLengthDifference = compareAvgWordLength(comparedAuthor);
        sentenceLengthDifference = compareSentenceLength(comparedAuthor);
        wordSentenceRatio = compareSentenceWordRatios(comparedAuthor);
        vocabWidth = compareVocabularyWidth(comparedAuthor);
        bigram = compareBigramMaps(comparedAuthor); 
        symbol = compareSymbolMaps(comparedAuthor); 
        trigram = compareTriGramMaps(comparedAuthor);
        fourgram = compareFourGramMaps(comparedAuthor);
        degreeOfMatch = (wordLengthDifference + sentenceLengthDifference + (2 * wordSentenceRatio) + (3 * vocabWidth) + 
                            (20 * trigram) + (30 * fourgram))/67;
        return degreeOfMatch;
    }
     
    public double compareAvgWordLength (Author comparedAuthor) {
        double wordLengthDifference = 0.0;
        if (getAvgWordLength() > comparedAuthor.getAvgWordLength())
            wordLengthDifference = getAvgWordLength() - comparedAuthor.getAvgWordLength();
        else if (getAvgWordLength() == comparedAuthor.getAvgWordLength())
            wordLengthDifference = 0;
        else
            wordLengthDifference = comparedAuthor.getAvgWordLength() - getAvgWordLength();
        
        return wordLengthDifference;
    }    
     
    public double compareSentenceLength(Author comparedAuthor) {
        double sentenceLengthDifference = 0.0;
        
        if (getAvgSentenceLength() > comparedAuthor.getAvgSentenceLength())
            sentenceLengthDifference = getAvgSentenceLength() - comparedAuthor.getAvgSentenceLength();
        else if (getAvgSentenceLength() == comparedAuthor.getAvgSentenceLength())
            sentenceLengthDifference = 0;
        else
            sentenceLengthDifference = comparedAuthor.getAvgSentenceLength() - getAvgSentenceLength();
            
        return sentenceLengthDifference;    
    }    
      
    public double compareSentenceWordRatios(Author comparedAuthor) {
        double wordSentenceRatio = 0.0;
        if (getWordToSentenceRatio() > comparedAuthor.getWordToSentenceRatio())
            wordSentenceRatio = getWordToSentenceRatio() - comparedAuthor.getWordToSentenceRatio();
        else if (getWordToSentenceRatio() == comparedAuthor.getWordToSentenceRatio())
            wordSentenceRatio = 0;
        else
            wordSentenceRatio = comparedAuthor.getWordToSentenceRatio() -  getWordToSentenceRatio();
        
        return wordSentenceRatio;    
    }    
    
    public double compareVocabularyWidth(Author comparedAuthor) {
        double vocabWidth = 0.0;
    
        if (getVocabWidth() > comparedAuthor.getVocabWidth())
                vocabWidth = getVocabWidth() - comparedAuthor.getVocabWidth();
            else if (getVocabWidth() == comparedAuthor.getVocabWidth())
                vocabWidth = 0;
            else
                vocabWidth = comparedAuthor.getVocabWidth() - getVocabWidth();
        
        return vocabWidth;
    }
    
    /**
     * Creates named author and file location of text
     * @param name - name of author
     */
    public Author(String name)
    {
        this.name = name;
    }
    
    /**
     * Compares mappings of metrics of two authors
     * @param comparedAuthor - author to be compared to this author
     */
    
    public double compareSymbolMaps(Author comparedAuthor){
        String symbol;
        Double authorsSymbolFrequency, unknownAuthorSymbolFrequency, cumulativeComparison = 0.0;
        Set currentAuthorKeys = this.getSymbolFrequencyMap().entrySet();
        Iterator iterateSymbols = currentAuthorKeys.iterator();
        
        while (iterateSymbols.hasNext()) {
            Map.Entry symbolEntries = (Map.Entry) iterateSymbols.next();
            symbol = (String) symbolEntries.getKey();
            if (comparedAuthor.getSymbolFrequencyMap().containsKey(symbol)) {
                authorsSymbolFrequency = (Double) symbolEntries.getValue();
                unknownAuthorSymbolFrequency = comparedAuthor.getSymbolFrequencyMap().get(symbol);
                cumulativeComparison = (calculateComparisonValue(authorsSymbolFrequency, unknownAuthorSymbolFrequency)) 
                                           + cumulativeComparison / 2;  
            }
            else {
                cumulativeComparison = (cumulativeComparison + 1) / 2;
            }    
       }
       return cumulativeComparison;
    }
    
    /**
     * Compares mappings of metrics of two authors: bigrams
     * @param comparedAuthor - author to be compared to this author
     */
     public double compareBigramMaps(Author comparedAuthor){
        String bigram;
        Double authorsBigramFrequency, unknownAuthorBigramFrequency, cumulativeComparison = 0.0;
        Set currentAuthorKeys = this.getBiGramFrequencyMap().entrySet();
        Iterator iterateSymbols = currentAuthorKeys.iterator();
        
        while (iterateSymbols.hasNext()) {
            Map.Entry bigramEntries = (Map.Entry) iterateSymbols.next();
            bigram = (String) bigramEntries.getKey();
            if (comparedAuthor.getBiGramFrequencyMap().containsKey(bigram)) {
                authorsBigramFrequency = (Double) bigramEntries.getValue();
                unknownAuthorBigramFrequency = comparedAuthor.getBiGramFrequencyMap().get(bigram);
                cumulativeComparison = (calculateComparisonValue(authorsBigramFrequency, unknownAuthorBigramFrequency)) 
                                           + cumulativeComparison / 2;  
            }
            else {
                cumulativeComparison = (cumulativeComparison + 1) / 2;
            }    
       }
       return cumulativeComparison;
    }
    
     /**
     * Compares mappings of metrics of two authors: tri-grams
     * 
     * @param comparedAuthor - author to be compared to this author
     */
    public double compareTriGramMaps(Author comparedAuthor){
        String trigram;
        Double authorsTrigramFrequency, unknownAuthorTrigramFrequency, cumulativeComparison = 0.0;
        Set currentAuthorKeys = this.getTriGramFrequencyMap().entrySet();
        Iterator iterateSymbols = currentAuthorKeys.iterator();
        
        while (iterateSymbols.hasNext()) {
            Map.Entry trigramEntries = (Map.Entry) iterateSymbols.next();
            trigram = (String) trigramEntries.getKey();
            if (comparedAuthor.getTriGramFrequencyMap().containsKey(trigram)) {
                authorsTrigramFrequency = (Double) trigramEntries.getValue();
                unknownAuthorTrigramFrequency = comparedAuthor.getTriGramFrequencyMap().get(trigram);
                cumulativeComparison = (calculateComparisonValue(authorsTrigramFrequency, unknownAuthorTrigramFrequency)) 
                                           + cumulativeComparison / 2;  
            }
            else {
                cumulativeComparison = (cumulativeComparison + 1) / 2;
            }    
       }
       return cumulativeComparison;
    }
    
    /**
     * Compares mappings of metrics of two authors: four-grams
     * 
     * @param comparedAuthor - author to be compared to this author
     */
    public double compareFourGramMaps(Author comparedAuthor){
        String fourgram;
        Double authorsFourgramFrequency, unknownAuthorFourgramFrequency, cumulativeComparison = 0.0;
        Set currentAuthorKeys = this.getFourGramFrequencyMap().entrySet();
        Iterator iterateSymbols = currentAuthorKeys.iterator();
        
        while (iterateSymbols.hasNext()) {
            Map.Entry fourgramEntries = (Map.Entry) iterateSymbols.next();
            fourgram = (String) fourgramEntries.getKey();
            if (comparedAuthor.getFourGramFrequencyMap().containsKey(fourgram)) {
                authorsFourgramFrequency = (Double) fourgramEntries.getValue();
                unknownAuthorFourgramFrequency = comparedAuthor.getFourGramFrequencyMap().get(fourgram);
                cumulativeComparison = (calculateComparisonValue(authorsFourgramFrequency, unknownAuthorFourgramFrequency)) 
                                           + cumulativeComparison / 2;  
            }
            else {
                cumulativeComparison = (cumulativeComparison + 1) / 2;
            }    
       }
       return cumulativeComparison;
    }
    
    /**
     * calculate a comparison value for n-gram frequency of an author
     */
    private double calculateComparisonValue(double authorsNgramFrequency, double unknownAuthorNgramFrequency) {
       final double BOTH_HAVE_SAME_FREQUENCY = 0;
       if (authorsNgramFrequency > unknownAuthorNgramFrequency)
           return (authorsNgramFrequency - unknownAuthorNgramFrequency);
       else if (authorsNgramFrequency < unknownAuthorNgramFrequency)
           return (unknownAuthorNgramFrequency - authorsNgramFrequency); 
       return BOTH_HAVE_SAME_FREQUENCY;
    }    
    
    /**
     * returns author's name
     * @return name - author's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * returns average word length of supplied texts for author
     * @return avgWordLength - average word length of supplied texts for author
     */
    public double getAvgWordLength() {
        return avgWordLength;
    }
    
    /**
     * sets average word length of supplied texts for author
     * @param avgWordLength - average word length of supplied texts for author
     */
    public void setAvgWordLength(double avgLength) {
        avgWordLength = (avgWordLength + avgLength) / 2;
    }
    
    /**
     * returns average sentence length of supplied texts for author
     * @return avgSentenceLength - average sentence length of supplied texts for author
     */
    public double getAvgSentenceLength() {
        return avgSentenceLength;
    }
    
    /**
     * sets average sentence length of supplied texts for author
     * @param avgSentenceLength - average sentence length of supplied texts for author
     */
    public void setAvgSentenceLength(double avgLength) {
        avgSentenceLength = (avgSentenceLength + avgLength) / 2;
    }
    
    /**
     * returns word to sentence ratio of supplied texts for author
     * @return wordToSentenceRatio - word to sentence ratio of supplied texts for author
     */
    public double getWordToSentenceRatio() {
        return wordToSentenceRatio;
    }
    
    /**
     * sets word to sentence ratio of supplied texts for author
     * @param wordToSentenceRatio - word to sentence ratio of supplied texts for author
     */
    public void setWordToSentenceRatio(double wordToSentenceRatio) {
        this.wordToSentenceRatio = (this.wordToSentenceRatio + wordToSentenceRatio) / 2;
    }
    
    /**
     * returns vocabulary richness of supplied texts for author
     * @return vocabWidth - vocabulary richness of supplied texts for author
     */
    public double getVocabWidth() {
        return vocabWidth;
    }
    
    /**
     * sets vocabulary richness of supplied texts for author
     * @param wordToSentenceRatio - vocabulary richness of supplied texts for author
     */
    public void setVocabularyWidth(double vocabWidth) {
        this.vocabWidth = (this.vocabWidth + vocabWidth) / 2;
    }
    
    /**
     * Sets and combines maps of symbol frequencies
     * @param symbolMap - map of symbol frequencies used by author
     */
    public void setSymbolFrequency(Map<Character, Double> symbolFreq) {
        Set symbolSet = symbolFreq.entrySet();
        Iterator iterate = symbolSet.iterator();
        Character textCharacter;
        Double newFrequency, frequency ;
        while(iterate.hasNext()) {
            Map.Entry mapEntries = (Map.Entry) iterate.next();
            newFrequency = (Double) mapEntries.getValue();
            textCharacter = (Character) mapEntries.getKey();
            frequency = symbolMap.get(textCharacter);
            if (frequency == null) {
                symbolMap.put(textCharacter, newFrequency);   
            } 
            else {
                symbolMap.put(textCharacter, ((frequency + newFrequency) / 2));     
            }
        }
    }  
    
    /**
     * returns map of symbol frequencies
     * @return symbolMap - map of symbol frequencies used by author
     */
    public Map<Character, Double> getSymbolFrequencyMap() {
        return symbolMap;
    } 
    
    /**
     * Sets and combines maps of bi-gram frequencies
     * @param symbolMap - map of bi-gram frequencies used by author
     */
    public void setBiGramFrequency(Map<String, Double> bigramFreq) {
        Set symbolSet = bigramFreq.entrySet();
        Iterator iterate = symbolSet.iterator();
        String bigram;
        Double newFrequency, frequency ;
        while(iterate.hasNext()) {
            Map.Entry mapEntries = (Map.Entry) iterate.next();
            newFrequency = (Double) mapEntries.getValue();
            bigram = (String) mapEntries.getKey();
            frequency = bigramMap.get(bigram);
            if (frequency == null) {
                bigramMap.put(bigram, newFrequency);   
            } 
            else {
                bigramMap.put(bigram, ((frequency + newFrequency) / 2));     
            }
        }
    }
    
    /**
     * Sets or combines maps of tri-gram frequencies
     * 
     * @param symbolMap - map of tri-gram frequencies used by author
     */
    public void setTriGramFrequency(Map<String, Double> trigramFreq) {
        Set symbolSet = trigramFreq.entrySet();
        Iterator iterate = symbolSet.iterator();
        String trigram;
        Double newFrequency, frequency ;
        while(iterate.hasNext()) {
            Map.Entry mapEntries = (Map.Entry) iterate.next();
            newFrequency = (Double) mapEntries.getValue();
            trigram = (String) mapEntries.getKey();
            frequency = trigramMap.get(trigram);
            if (frequency == null) {
                trigramMap.put(trigram, newFrequency);   
            } 
            else {
                trigramMap.put(trigram, ((frequency + newFrequency) / 2));     
            }
        }
    }
    
    /**
     * returns map of tri-gram frequencies
     * 
     * @return triGramMap - map of tri-gram frequencies used by author
     */
    public Map<String, Double> getTriGramFrequencyMap() {
        return trigramMap;
    }
    
    /**
     * Sets or combines maps of 4-gram frequencies
     * 
     * @param symbolMap - map of 4-gram frequencies used by author
     */
    public void setFourGramFrequency(Map<String, Double> fourgramFreq) {
        Set symbolSet = fourgramFreq.entrySet();
        Iterator iterate = symbolSet.iterator();
        String fourgram;
        Double newFrequency, frequency ;
        while(iterate.hasNext()) {
            Map.Entry mapEntries = (Map.Entry) iterate.next();
            newFrequency = (Double) mapEntries.getValue();
            fourgram = (String) mapEntries.getKey();
            frequency = fourgramMap.get(fourgram);
            if (frequency == null) {
                fourgramMap.put(fourgram, newFrequency);   
            } 
            else {
                fourgramMap.put(fourgram, ((frequency + newFrequency) / 2));     
            }
        }
    }
    
    /**
     * returns map of 4-gram frequencies
     * 
     * @return fourGramMap - map of four-gram frequencies used by author
     */
    public Map<String, Double> getFourGramFrequencyMap() {
        return fourgramMap;
    }    
    
    /**
     * returns map of bi-gram frequencies
     * @return biGramMap - map of bi-gram frequencies used by author
     */
    public Map<String, Double> getBiGramFrequencyMap() {
        return bigramMap;
    }    
}
