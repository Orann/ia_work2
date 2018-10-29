package work2;

import java.util.ArrayList;

/**
 *
 * @author Calire, Esther & Orann
 */
class Cell {
    private ArrayList<Cell> constraints;
    int assignment;
    boolean isAssigned;
    ArrayList<Integer> domain;

    public Cell(ArrayList<Cell> constraints, int assignment, ArrayList<Integer> domain) {
        this.constraints = constraints;
        this.assignment = assignment;
        this.domain = domain;
    }

    public int getAssignment() {
        return assignment;
    }

    public boolean isIsAssigned() {
        return isAssigned;
    }   
    
    boolean isConsistant(){
        return false;
    }
}
