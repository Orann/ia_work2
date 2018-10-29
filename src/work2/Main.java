package work2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Claire, Esther & Orann
 */
public class Main {
    private final static String testSudoku = "018000700000300200070000000000071000600000040300000000400500003020080000000000060";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Sudoku s = new Sudoku(testSudoku);
        System.out.println(s);
        
        //Load 100 sudokus :
        String sudokusFile = "";
        try {
            sudokusFile = new String(Files.readAllBytes(Paths.get("./sudokus.txt")), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] sudokus = sudokusFile.split(System.getProperty("line.separator"));
        //System.out.println(sudokus[0]);
    }
    
}
