public class Knight extends Player{

    public Knight(String name){
        super(name, 14, 4, 0, "Knight", false, false,0);
    }

    boolean isTaunting = false;

    public int swingSword(){
        return getDmg();
    }

    public void taunt(){
        isTaunting = true;
    }

    public void Defend(){
        this.setIsDefending(true);
    }
}

