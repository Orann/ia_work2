package work2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Claire, Esther & Orann
 */
public class Sudoku {

    //Attributes
    private ArrayList<ArrayList<Cell>> grid;
    private int NbCellsToComplete;

    private ArrayList<Cell> mrvListSelection;
    private ArrayList<Cell> degreeHeuristicListSelection;

    
    //Constructor
    public Sudoku(String sudoku) {
        grid = new ArrayList<>();
        NbCellsToComplete = 0;
        mrvListSelection = new ArrayList<>();
        degreeHeuristicListSelection = new ArrayList<>();
        ArrayList<Cell> ligne = new ArrayList<>();
        ArrayList<Integer> domain = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            domain.add(i);
        }
        int i = 1;
        for (char number : sudoku.toCharArray()) {
            if (number == '0') {
                ligne.add(new Cell(Character.getNumericValue(number), (ArrayList<Integer>) domain.clone()));
                NbCellsToComplete++;
            } else {
                ligne.add(new Cell(Character.getNumericValue(number), null));
            }
            if (i == 9) {
                grid.add((ArrayList<Cell>) ligne.clone());
                i = 1;
                ligne.clear();
            } else {
                i++;
            }
        }
        fillConstraints();
        Cell currentCell;
        int value;
        for (i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                currentCell = grid.get(i).get(j);
                value = currentCell.getAssignment();
                if (value != 0) {
                    currentCell.propagateConstraints(value);
                }
            }
        }

    }

    private void fillConstraints() {
        for (int i = 0; i < 9; i++) { //row
            for (int j = 0; j < 9; j++) { //column
                Cell currentCell = grid.get(i).get(j);
                //We add the constraints between currentCell and the cells of the same row and the same column
                for (int k = 0; k < 9; k++) { //ligne
                    for (int l = 0; l < 9; l++) { //colonne
                        if (((i == k) && (j != l)) || (i != k && j == l)) {
                            grid.get(k).get(l).addConstraint(currentCell);
                        }
                    }
                }
                //We add the constraints between currentCell and the cells of the same 3x3 square
                for (int m = (i / 3) * 3; m < ((i / 3) * 3) + 3; m++) {
                    for (int n = (j / 3) * 3; n < ((j / 3) * 3) + 3; n++) {
                        if (m != i && n != j) {
                            grid.get(m).get(n).addConstraint(currentCell);
                        }
                    }
                }

            }
        }
    }

    //Tests the objective function
    public boolean isAssignementsComplete(ArrayList<Cell> assignments) {
        boolean goal = false;
        if (assignments.size() == NbCellsToComplete) {
            goal = true;
        }
        return goal;
    }

    // Returns the first unassigned cell -> not the optimized method to choose an unassigned cell
    public Cell selectUnassignedCell() {
        boolean foundCell = false;
        int i = 0;
        int j = 0;
        Cell cell = null;
        while (!foundCell) {
            if (!grid.get(i).get(j).isAssigned()) {
                cell = grid.get(i).get(j);
                foundCell = true;
            }
            if (j == 8) {
                i++;
                j = 0;
            } else {
                j++;
            }
        }
        return cell;
    }

    //Selects an unassigned cell using the Most Restraining Value algorithm
    public ArrayList<Cell> mrvSelection() {
        mrvListSelection.clear();

        for (ArrayList<Cell> row : grid) {
            for (Cell cell : row) {
                if (!cell.isAssigned()) {
                    mrvListSelection.add(cell);
                }
            }
        }

        Collections.sort(mrvListSelection, new Comparator<Cell>() {
            @Override
            public int compare(Cell t, Cell t1) {
                int ret;
                int tSize = t.getDomain().size();
                int t1Size = t1.getDomain().size();
                if (tSize < t1Size) {
                    ret = -1;
                } else if (tSize == t1Size) {
                    ret = 0;
                } else {
                    ret = 1;
                }
                return ret;
            }
        });

        int domain = mrvListSelection.get(0).getDomain().size();
        ArrayList<Cell> bestMrvVariables = new ArrayList<>();
        bestMrvVariables.add(mrvListSelection.get(0));
        int i = 1;
        while (i<mrvListSelection.size() && mrvListSelection.get(i).getDomain().size() == domain) {
            bestMrvVariables.add(mrvListSelection.get(i));
            i++;
        }

        return bestMrvVariables;
    }

    // Selects an unassigned cell using the degree heuristic selection algorithm
    public Cell degreeHeuristicSelection(ArrayList<Cell> mrvResult) {
        degreeHeuristicListSelection.clear();
        int counter = 0;

        for (Cell cell : mrvResult) {
            degreeHeuristicListSelection.add(cell);
            for (Cell constraint : cell.getConstraints()) {
                if (!constraint.isAssigned()) {
                    counter++;
                }
            }
            cell.setComparisonCriteria(-counter);

        }

        Collections.sort(degreeHeuristicListSelection, new Comparator<Cell>() {
            @Override
            public int compare(Cell t, Cell t1) {
                int ret;
                int criteria = t.getComparisonCriteria();
                int criteria1 = t1.getComparisonCriteria();

                if (criteria < criteria1) {
                    ret = -1;
                } else if (criteria == criteria1) {
                    ret = 0;
                } else {
                    ret = 1;
                }
                return ret;
            }
        });

        return degreeHeuristicListSelection.get(0);
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < 9; i++) {
            if(i%3 == 0 && i!=0){
                res+="\n-----------------------\n";
            }
            else res += "\n";
            for (int j = 0; j < 9; j++) {
                if(j%3 == 0 && j!=0){
                    res+=" | ";
                }
                res += grid.get(i).get(j).getAssignment()+" ";
            }
            
        }
        res+="\n";
        return res;
    }
}
