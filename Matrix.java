 
import java.util.Random;

public class Matrix{

    private boolean[][] cases;
    private  static int DIMDEF = 3;
    private  static int NBM = 1 ;

    Matrix(){
        this(NBM, DIMDEF);
    }

    Matrix(int nb){
        this(nb, DIMDEF);
    }

    Matrix(int nb, int dim){
        this.NBM = nb;
        cases = new boolean[dim][dim];
        
        //place mines
        fillRandomly();
        // compute

        //display
    }
    /*
     * GetDim
     */

    int getDim(){
        return cases.length;
    }

    int getWidth() {
        return cases.length;
    }

    int getHeight() {
        return cases[0].length;
    }

    boolean getNum(int i, int j){
        return cases[i][j];
    }

    int getnbMines(){
        return NBM;
    }
    
    public void setNum(int x, int y, boolean value){
        cases[x][y] = value;
    }
    
    /**
     * place randomly mines
     */
    void fillRandomly(){
        Random generator = new Random();
        do{
            int x =generator.nextInt(cases.length);
            int y =generator.nextInt(cases[0].length);

            if(!cases[x][y]){
                cases[x][y] = true;
                NBM--;
            }

        } while (NBM != 0);
    }

    /**
     * display the matrix
     */

     void display(){
        for (int i=0; i<cases[0].length ; i++){
            for (int j=0; j<cases[1].length; j++)
                if (cases[i][j]==true)
                    System.out.print("X " + computeMinesNumber(i,j) + " ");
                else
                    System.out.print("O " + computeMinesNumber(i,j) + " "); 
            System.out.println();
        }
     }
    /**
     * compute the number of mines around a case
     */

    int computeMinesNumber(int x, int y){

            int nbMines = 0;
            for (int i = Math.max(0,x-1); i < Math.min(cases.length,x+2); i++){
                for (int j = Math.max(0,y-1); j<Math.min(cases[0].length,y+2); j++){
                    if(cases[i][j] && !(i==x && j==y)){
                            nbMines++;        
                    }
                }
            }       
            return nbMines;
        }

    int computeAllmines(){
        NBM=0;
        for(int i=0; i<cases.length; i++){
            for(int j=0; j<cases[0].length; j++){
                if(cases[i][j]){
                    NBM++;
                }
            }
        }
        return NBM;
    }

}