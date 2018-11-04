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

    private static Sudoku sudoku;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Load 100 sudokus :
        String sudokusFile = "";
        try {
            sudokusFile = new String(Files.readAllBytes(Paths.get("./sudokus.txt")), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] sudokus = sudokusFile.split(System.getProperty("line.separator"));
        
        for(String sudokuString : sudokus){
            sudoku = new Sudoku(sudokuString);
            System.out.println("Empty sudoku :");
            System.out.println(sudoku);
            ArrayList<Cell> results = backtrackingSearch(new ArrayList<Cell>());
            System.out.println("Solved sudoku :");
            System.out.println(sudoku);
            System.out.println("\n");
        }        
    }

    private static ArrayList<Cell> backtrackingSearch(ArrayList<Cell> assignements) {
        Cell cell;
        ArrayList<Cell> results = null;
        ArrayList<Cell> mrvResult;
        boolean isArcConsistant;
        if (sudoku.isAssignementsComplete(assignements)) {
            return assignements;
        } else {
            mrvResult = sudoku.mrvSelection();
            if (mrvResult.size() == 1) {
                cell = mrvResult.get(0);
            } else {
                cell = sudoku.degreeHeuristicSelection(mrvResult);
            }
            ArrayList<Integer> values = cell.leastConstrainingValues();
            for (Integer value : values) {
                if (cell.isConsistant(value)) {
                    isArcConsistant = cell.assign(value);
                    if (isArcConsistant) {
                        assignements.add(cell);
                        results = backtrackingSearch(assignements);
                        if (results != null) {
                            return results;
                        } else {
                            assignements.remove(cell);
                            cell.unassign(value);
                        }
                    }
                    else{
                        cell.unassign(value);
                    }
                }
            }
        }
        return results;
    }
}
