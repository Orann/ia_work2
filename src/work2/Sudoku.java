package work2;

import java.util.ArrayList;

/**
 *
 * @author Claire, Esther & Orann
 */
public class Sudoku {

    private ArrayList<ArrayList<Cell>> grid;
    private int NbCellsToComplete;

    public Sudoku(String sudoku) {
        grid = new ArrayList<>();
        NbCellsToComplete = 0;
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
            }
            else i++;
        }
        fillConstraints();
        Cell currentCell;
        int value;
        for (i=0; i<9; i++){
            for (int j=0; j<9; j++){ 
                currentCell= grid.get(i).get(j);
                value = currentCell.getAssignment();
                if(value != 0){
                    currentCell.updateDomains(value);
                }
            }
        }
        
    }
    
    private void fillConstraints(){
        for(int i = 0 ; i < 9 ; i++){ //ligne
            for(int j = 0 ; j < 9 ; j++){ //colonne
                Cell currentCell = grid.get(i).get(j);
                //On ajoute la contrainte entre currentCell et les cellules de 
                //la même ligne et de la même colonne
                for(int k = 0 ; k < 9 ; k++){ //ligne
                    for(int l = 0 ; l < 9 ; l++){ //colonne
                        if(((i == k) && (j!=l)) || (i!=k && j==l)){
                            grid.get(k).get(l).addConstraint(currentCell);
                        }
                    }
                }
                //On ajoute les contraintes entre currentCell et les cellules du
                //même carré de 3
                for(int m = (i/3)*3 ; m < ((i/3)*3)+3 ; m++){
                    for(int n = (j/3)*3; n < ((j/3)*3)+3; n++){
                        if(m != i && n != j){
                            grid.get(m).get(n).addConstraint(currentCell);
                        }
                    }
                }
                
            }
        }
    }
    
    //Test de la fonction objectif
    public boolean isAssignementsComplete(ArrayList<Cell> assignments){
        boolean goal = false;
        if(assignments.size() == NbCellsToComplete){
            goal = true;
        }
        return goal;
    }   
    
    // On récupère une cellule non assigné 
    public Cell selectUnassignedCell(ArrayList<Cell> assignements){
        boolean foundCell = false;
        int i = 0;
        int j= 0;
        Cell cell = null;
        while(!foundCell){
            if(!grid.get(i).get(j).isAssigned()){
                cell = grid.get(i).get(j);
                foundCell = true;
            }
            if ( j==8 ){
                i++;
                j=0;
            }
            else{
                j++;
            }
        }
        return cell;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                res += grid.get(i).get(j).getAssignment();
            }
            res += "\n";
        }
        return res;
    }
}
