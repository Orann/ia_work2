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
        if (assignment != 0) {
            isAssigned = true;
        } else {
            isAssigned = false;
        }
        this.domain = domain;
    }

    public int getAssignment() {
        return assignment;
    }

    public ArrayList<Integer> getDomain() {
        return domain;
    }

    public ArrayList<Cell> getConstraints() {
        return constraints;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    boolean isConsistant(int value) {
        boolean isConsistant = true;
        for (Cell cell : constraints) {
            if (cell.getAssignment() == value) {
                isConsistant = false;
            }
        }
        return isConsistant;
    }

    public void addConstraint(Cell constraint) {
        constraints.add(constraint);
    }

    void updateDomains(int value) {
        for (Cell cell : constraints) {
            ArrayList<Integer> domain = cell.getDomain();
            if (domain != null) {
                domain.remove((Object) value);
            }
        }
    }

    public void assign(int value) {
        this.assignment = value;
        this.isAssigned = true;
        updateDomains(value);
    }

    public void unassign(int value) {
        this.assignment = 0;
        this.isAssigned = false;
        ArrayList<Cell> subConstraints; //Les contraintes de la contrainte considérée
        int i = 0;
        boolean addValue = true;

        for (Cell cell : constraints) {
            ArrayList<Integer> domain = cell.getDomain(); // le domaine qu'on va modifier (ou pas)
            if (domain != null) {
                Cell actualConstraint; // la contrainte de cell qu'on considère
                subConstraints = cell.getConstraints();
                while (addValue && i < subConstraints.size()) {
                    //Si la contrainte a un "voisin" qui est assigné à value, 
                    //ce n'est pas la peine de remettre value dans son domaine
                    if (subConstraints.get(i).getAssignment() == value) {
                        addValue = false;
                    }
                    i++;
                }
                if (addValue) {
                    domain.add(value);
                }

                //on réinitialise les variables de parcours
                i = 0;
                addValue = true;
            }
        }
    }

    @Override
    public String toString() {
        return "" + assignment;
    }

}
