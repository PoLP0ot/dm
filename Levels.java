public enum Levels {
    EASY(1, 3),
    MEDIUM(60, 20),
    HARD(100, 30),
    CUSTOM;

    
    private final int mines;
    private final int taille;

    Levels(){
        this.mines=10;
        this.taille=10;
        
    }



    Levels(int mines, int taille) {
    
        this.mines = mines;
        this.taille = taille;
    }

    public int getTaille() {
        return taille;
    }

    public int getMines() {
        return mines;
    }
    
}


