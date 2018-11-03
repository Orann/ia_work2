package work2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Claire, Esther & Orann
 */
public class Main {
    private final static String testSudoku = "018000700000300200070000000000071000600000040300000000400500003020080000000000060";
    private static Sudoku sudoku;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        sudoku = new Sudoku(testSudoku);
        System.out.println(sudoku);
        
        //Load 100 sudokus :
        String sudokusFile = "";
        try {
            sudokusFile = new String(Files.readAllBytes(Paths.get("./sudokus.txt")), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] sudokus = sudokusFile.split(System.getProperty("line.separator"));
        //System.out.println(sudokus[0]);
        
        //Algo principal avec Backtracking :
        ArrayList<Cell> results = backtrackingSearch(new ArrayList<Cell>());
        System.out.println(sudoku);
    }
    
    private static ArrayList<Cell> backtrackingSearch(ArrayList<Cell> assignements){
        Cell cell;
        ArrayList<Cell> results = null;
        if(sudoku.isAssignementsComplete(assignements)){
            return assignements;
        }
        else {
           cell = sudoku.selectUnassignedCell(assignements);
           
           for(Integer value : (ArrayList<Integer>) cell.getDomain().clone()){
               if (cell.isConsistant(value)){
                   cell.assign(value);
                   assignements.add(cell);
                   results = backtrackingSearch(assignements);
                   if(results != null){
                       return results;
                   }
                   else{
                       assignements.remove(cell);
                       cell.unassign(value);
                   }
               }
           }
        }
        return results;
    }    
}
