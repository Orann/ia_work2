package work2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author Claire, Esther & Orann
 */
class Cell {

    private ArrayList<Cell> constraints;
    private int assignment;
    private boolean isAssigned;
    private ArrayList<Integer> domain;
    private int comparisonCriteria; 

    public Cell(int assignment, ArrayList<Integer> domain) {
        this.constraints = new ArrayList<>();
        this.assignment = assignment;
        if (assignment != 0) {
            isAssigned = true;
        } else {
            isAssigned = false;
        }
        this.domain = domain;
        this.comparisonCriteria = -1; 
    }

    public int getComparisonCriteria() {
        return comparisonCriteria;
    }

    public void setComparisonCriteria(int comparisonCriteria) {
        this.comparisonCriteria = comparisonCriteria;
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

    boolean propagateConstraints(int value) {
        boolean isArcConsistant = true; 
        for (Cell cell : constraints) {
            ArrayList<Integer> domain = cell.getDomain();
            if (!cell.isAssigned()) {
                domain.remove((Object) value);
                if (domain.isEmpty()){ // AC3
                    isArcConsistant = false;
                }
            }
        }
        return isArcConsistant;
    }

    public boolean assign(int value) {
        this.assignment = value;
        this.isAssigned = true;
        boolean isArcConsistant = propagateConstraints(value);
        return isArcConsistant;
    }

    public void unassign(int value) {
        this.assignment = 0;
        this.isAssigned = false;
        ArrayList<Cell> subConstraints; //Les contraintes de la contrainte considérée
        int i = 0;
        boolean addValue = true;

        for (Cell cell : constraints) {
            ArrayList<Integer> domain = cell.getDomain(); // le domaine qu'on va modifier (ou pas)
            if (!cell.isAssigned()) {
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

    public ArrayList<Integer> leastConstrainingValues() {
        int counter=0;
        ArrayList<Integer> tmp = new ArrayList<>();
        ArrayList<Integer> leastConstrainingValues = new ArrayList<>();
        ArrayList<ArrayList<Integer>> valueAndCounter = new ArrayList<>(); 
        for(Integer val : this.domain){
            for(Cell c : this.constraints){
                if (!c.isAssigned() && c.getDomain().contains(val)){
                    counter++;
                }
                
            }
            tmp.add(val);
            tmp.add(counter);
            valueAndCounter.add( (ArrayList<Integer>) tmp.clone());
            tmp.clear();
        }
        
        Collections.sort(valueAndCounter, new Comparator<ArrayList<Integer>>(){
            @Override
            public int compare(ArrayList<Integer> t, ArrayList<Integer> t1) {
                int tCounter = t.get(1);
                int t1Counter = t1.get(1);
                int ret;
                if (tCounter < t1Counter){
                    ret = -1; 
                }
                else if (tCounter == t1Counter){
                    ret = 0;
                }
                else{
                    ret = 1;
                }
                
                return ret;
            }
            
        });
        
        for(int i=0; i<valueAndCounter.size(); i++){
            leastConstrainingValues.add(valueAndCounter.get(i).get(0));
        }
        return leastConstrainingValues;
    }

}
