//Kaeleb
public class Rogue extends Player {
    
    public Rogue(String name){
        super(name, 6, 2 , 0, "rogue", false, false);
    }

    //single hit attack; applies poison status to the target
    public int poisonDagger(){
        return poisonAttack();
    }

    //multi hit attack; does dmg to all enemies
    public int multiAttack(){
        return splashAttack();
    }

    //on defend action sets isDefending variable to true; is return to false in main when the action goes through
    public void Defend(){
        this.setIsDefending(true);
    }
}
