
/**
 * Write a description of class TextAnalyzerRunner here.
 * 
 * @author Jason Isaacs
 * @version 1.3
 */
public class TextAnalyzerRunner
{
    
    /**
     * Driver for command-line invocation of Authorship Analysis Tool.
     * 
     */
    public static void main(String[] args) {
         AuthorshipAnalysisUI analyzer = new AuthorshipAnalysisUI();
         
         analyzer.getTextsFromUser();
         analyzer.getUnknownTextFromUser();
         //analyzer.compareKnownAuthorsToText();
         analyzer.displayComparisonResults();
    }
}
