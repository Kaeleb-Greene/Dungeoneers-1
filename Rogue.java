public class Rogue extends Player {
    
    public Rogue(String name){
        super(name, 6, 2 , 0, "rogue", false, false);
    }


    public int poisonDagger(){
        return poisonAttack();
    }

    public int multiAttack(){
        return splashAttack();
    }

    public void Defend(){
        this.setIsDefending(true);
    }

}
