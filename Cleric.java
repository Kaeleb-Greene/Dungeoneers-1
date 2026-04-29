//Kaeleb
public class Cleric extends Player {
    
    public Cleric(String name){
        super(name, 12, 2, 4, "Cleric", false, false);
    }

    //basic attack; returns raw dmg output
    public int swingMace(){
        return this.getDmg();
    }

    //healing ability; return heal value that is added to an allies
    public int healingSpell(){
        return this.getHeal();
    }

    //on defend action sets isDefending variable to true; is return to false in main when the action goes through
    public void Defend(){
        this.setIsDefending(true);
    }
}
