
import java.util.*;
/**
 * 
 * 
 * @author Jason Isaacs
 * @version 1.3
 */
public class TextProcessor
{
    List<Author> authorList;
    private Author knownAuthor, unknownAuthor;
    private BookMetrics metrics;
    
    
    public TextProcessor() {
        authorList = new ArrayList<Author>();
    }
    /**
     * Creates a profile for text with unknown authorship
     * 
     * @param  filePath - path of file of unknown authorship
     */
    public void createUnknownAuthorProfile(String filePath) {
        metrics = new BookMetrics(filePath, true);
        unknownAuthor = new Author("unknown", metrics);
    }
    
    /**
     * Creates list of Author objects stored in a Map
     * 
     * @param authorName - author's name
     * @param filePath - file path to author's text
     */
    public void createAuthorList(String authorName, String filePath) {
        metrics = new BookMetrics(filePath, true);
        int authorIndex = 0;
        if (authorList.contains(authorName)) {
            authorIndex = authorList.indexOf(authorName);
            knownAuthor = authorList.get(authorIndex);
            knownAuthor.setAvgWordLength(metrics.avgWordLength());
            knownAuthor.setAvgSentenceLength(metrics.avgSentenceLength());
            knownAuthor.setWordToSentenceRatio(metrics.wordToSentenceRatio());
            knownAuthor.setVocabularyWidth(metrics.vocabularyWidth());
            knownAuthor.setSymbolFrequency(metrics.getSymbolFrequencyMap());
            knownAuthor.setBiGramFrequency(metrics.getSymbolPairFrequencyMap());
            knownAuthor.setTriGramFrequency(metrics.getTriGramFrequencyMap());
            knownAuthor.setFourGramFrequency(metrics.getFourGramFrequencyMap());
            authorList.add(knownAuthor);
        }
        else {
            knownAuthor = new Author(authorName, metrics);
            authorList.add(knownAuthor);
        }
    }
    
    
    /**
     * Compares texts with unknown authorship
     * 
     */
    public Map compareKnownAuthorsToText() {
        double degreeOfMatch = 0.0;
        Map analyzedAuthors = new TreeMap<String, Double>();
        for (int i = 0; i < authorList.size(); i++) {
             degreeOfMatch = unknownAuthor.compares(authorList.get(i));
             analyzedAuthors.put(authorList.get(i).getName(), degreeOfMatch);
        }
        return analyzedAuthors;
    }
    
   
}
