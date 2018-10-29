package work2;

import java.util.ArrayList;

/**
 *
 * @author Claire, Esther & Orann
 */
class Cell {

    private ArrayList<Cell> constraints;
    private int assignment;
    private boolean isAssigned;
    private ArrayList<Integer> domain;

    public Cell(int assignment, ArrayList<Integer> domain) {
        this.constraints = new ArrayList<>();
        this.assignment = assignment;
        this.domain = domain;
    }

    public int getAssignment() {
        return assignment;
    }

    public ArrayList<Integer> getDomain() {
        return domain;
    }

    public boolean isIsAssigned() {
        return isAssigned;
    }

    boolean isConsistant() {
        return false;
    }

    public void addConstraint(Cell constraint) {
        constraints.add(constraint);
    }

    void updateDomains(int value) {
        for (Cell cell : constraints) {
            ArrayList<Integer> domain = cell.getDomain();
            if (domain != null) {
                System.out.println(value);
                domain.remove((Object)value);
            }
        }
    }

    public void assign(int value) {
        this.assignment = value;
        updateDomains(value);
    }

    @Override
    public String toString() {
        return "" + assignment;
    }

}
