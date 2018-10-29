package work2;

import java.util.ArrayList;

/**
 *
 * @author Claire, Esther & Orann
 */
public class Sudoku {

    private ArrayList<ArrayList<Cell>> grid;

    public Sudoku(String sudoku) {
        grid = new ArrayList<>();
        ArrayList<Cell> ligne = new ArrayList<>();
        ArrayList<Integer> domain = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            domain.add(i);
        }
        int i = 1;
        for (char number : sudoku.toCharArray()) {
            if (number == '0') {
                ligne.add(new Cell(Character.getNumericValue(number), (ArrayList<Integer>) domain.clone()));
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
    

    @Override
    public String toString() {
        return "Sudoku{" + "grid=" + grid + '}';
    }
}
