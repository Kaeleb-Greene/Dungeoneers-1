//Kaeleb
public class Knight extends Player{

    public Knight(String name){
        super(name, 14, 4, 0, "Knight", false, false);
    }
    
    //creates new instance variable boolean that is set to false by default
    boolean isTaunting = false;

    public boolean getIsTaunting(){
        return isTaunting;
    }

    //basic attack; returns raw dmg output
    public int swingSword(){
        return getDmg();
    }

    //on taunt action sets isTaunting variable to true; is returned to false in main when the action goes through
    public void taunt(){
        isTaunting = true;
    }

    //on defend action sets isDefending variable to true; is return to false in main when the action goes through
    public void Defend(){
        this.setIsDefending(true);
    }
}