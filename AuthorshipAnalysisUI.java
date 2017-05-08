import java.util.*;
import java.text.*;
import java.io.*;
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
public class AuthorshipAnalysisUI
{
    
   private TextProcessor processor = new TextProcessor();  
        
    /**
     * Constructor for objects of class AuthorshipAnalysisUI
     */
    public AuthorshipAnalysisUI() {
        
    }
    
    public void getTextsFromUser() {
        String filePath, authorName;
        int moreEntries;
        
        do {
             authorName = JOptionPane.showInputDialog(null, "Please enter author's name for text"); 
             filePath = JOptionPane.showInputDialog(null, "Please enter the name of the file containing the + "+  authorName +"'s text"); 
             
             processor.createAuthorList(authorName, filePath);
             
             moreEntries = JOptionPane.showConfirmDialog(null, "Would you like to enter more texts?",
                                "Yes or No?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        } while (moreEntries != 1);
    }
    
    public void getTextsFromFile(String FolderWithTexts) {
        
    }
    
    
    public void getUnknownTextFromUser() {
             String filePath = JOptionPane.showInputDialog(null, "Please enter the name of the file containing the text of unknown authorship");                
             if (filePath == null || filePath.equals("") || filePath.equals(" ")) {
                 System.out.println("The file you have entered is not valid.");
             }
             else {
                 processor.createUnknownAuthorProfile(filePath);
             }
    }
    
    
    
    
    public void displayComparisonResults() {
        double degree = 0.0;
        DecimalFormat formats = new DecimalFormat("###0.00");
        String authorName;
        boolean mostLikelyAuthor = true;
        Map analyzedAuthors = processor.compareKnownAuthorsToText();
        
        System.out.println("The following list will show the most likely author that wrote the unknown text: \n\n");
        Set comparedAuthors = analyzedAuthors.entrySet();
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
    
    
    
}