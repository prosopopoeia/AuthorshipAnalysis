import java.util.*;
import java.text.*;
import javax.swing.*;


/**
 * several examples of text with known authorship are scanned. Statistical 
 * measures are created for each example and those associated with the same 
 * author are combined and remembered. A text of unknown authorship is supplied and
 * the most likely authors of those with known texts.  A report indicating the 
 * degree of match to each of the known authors and listing these in order from 
 * best match to worst match is output.
 * 
 * @author Jason Isaacs 
 * @version v1.2
 */
public class AuthorshipAnalyzer
{
    private Author knownAuthor, unknownAuthor, author;
    ArrayList<Author> authorList = new ArrayList<Author>();
    TreeMap<String, Double> authorMatchList = new TreeMap<String, Double>();/////reverse for clarity
    private BookMetrics metrics;
    
    /**
     * Constructor for objects of class AuthorshipAnalyzer
     */
    public AuthorshipAnalyzer() {
        authorList = new ArrayList<Author>();
    }
    
    /**
     * Compares text of unknown authorship with list of known authors
     */
    public void compareTexts()
    {
       
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
    
    private void getTextsFromUser() {
        String filePath, authorName;
        int moreEntries;
        do {
             authorName = JOptionPane.showInputDialog(null, "Please enter author's name for text"); 
             filePath = JOptionPane.showInputDialog(null, "Please enter the name of the file containing the + "+  authorName +"'s text"); 
             
             createAuthorList(authorName, filePath);
             
             moreEntries = JOptionPane.showConfirmDialog(null, "Would you like to enter more texts?",
                                "Yes or No?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        } while (moreEntries != 1);
    }
    
    /**
     * Creates list of Author objects stored in a Map
     * 
     * @param authorName - author's name
     * @param filePath - file path to author's text
     */
    private void createAuthorList(String authorName, String filePath) {
        metrics = new BookMetrics(filePath, true);
        int authIndex = 0;
        if (authorList.contains(authorName)) {
            authIndex = authorList.indexOf(authorName);
            knownAuthor = authorList.get(authIndex);
            knownAuthor.setAvgWordLength(metrics.avgWordLength());
            knownAuthor.setAvgSentenceLength(metrics.avgSentenceLength());
            knownAuthor.setWordToSentenceRatio(metrics.wordToSentenceRatio());
            knownAuthor.setVocabularyWidth(metrics.vocabularyWidth());
            knownAuthor.setSymbolFrequency(metrics.getSymbolFrequencyMap());
            knownAuthor.setBiGramFrequency(metrics.getSymbolPairFrequencyMap());
            knownAuthor.setTriGramFrequency(metrics.getTriGramFrequencyMap());
            knownAuthor.setFourGramFrequency(metrics.getFourGramFrequencyMap());//-----------------review logic
            authorList.add(knownAuthor);
        }
        else {
            knownAuthor = new Author(authorName, metrics);
            authorList.add(knownAuthor);
        }
    }
    
    private void getUnknownTextFromUser() {
             String filePath = JOptionPane.showInputDialog(null, "Please enter the name of the file containing the text of unknown authorship");                
             if (filePath == null || filePath.equals("") || filePath.equals(" ")) {
                 System.out.println("The file you have entered is not valid.");
             }
             else {
                 createUnknownAuthorProfile(filePath);
             }
    }
    
    private void compareKnownAuthorsToText() {
        double degreeOfMatch = 0.0;
        for (int i = 0; i < authorList.size(); i++) {
             degreeOfMatch = unknownAuthor.compares(authorList.get(i));
             authorMatchList.put(authorList.get(i).getName(), degreeOfMatch);
        }
    }
    
    private void displayComparisonResults() {
        double degree = 0.0;
        DecimalFormat formats = new DecimalFormat("###0.00");
        String authorName;
        boolean mostLikelyAuthor = true;
        
        System.out.println("The following list will show the most likely author that wrote the unknown text: \n\n");
        Set comparedAuthors = authorMatchList.entrySet();
        Iterator iterates = comparedAuthors.iterator();
        while (iterates.hasNext()) {
            Map.Entry entries = (Map.Entry) iterates.next();
            authorName = (String) entries.getKey();
            degree = (Double) entries.getValue();
            if (mostLikelyAuthor) {
               System.out.format("The most likely author: " + authorName + " with the degree of match of " + formats.format(degree) +
                                           "\nThe following is a list of the other authors supplied with their corresponding likeliness " +
                                           "of having written the unknown text: \n");
               mostLikelyAuthor = false;
            }    
            System.out.println("Author name: " + authorName + " - Degree of match: " + formats.format(degree));
        }
    }
    
    
    /**
     * Driver for command-line invocation.
     * 
     */
    public static void main(String[] args) {
         AuthorshipAnalyzer analyzer = new AuthorshipAnalyzer();
         
         analyzer.getTextsFromUser();
         analyzer.getUnknownTextFromUser();
         analyzer.compareKnownAuthorsToText();
         analyzer.displayComparisonResults();
    }
}