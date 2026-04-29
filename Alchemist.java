//Kaeleb
public class Alchemist extends Player {
    
    public Alchemist(String name){
        super(name, 10, 2, 3, "Alchemist", false, false, 0);
    }

    //random attack; randomly returns raw dmg, applies poison to target, or weakens the target; on weakened target does only 1 dmg for their next attack
    public int throwPotion(){
        int Haselhoff  = ((int)(3 * Math.random()));
        if(Haselhoff == 0){
            return getDmg();
        } else if(Haselhoff == 1){
            return poisonAttack();
        } else {
            return 0;
        }
    }

    //healing ability; return heal value that is added to an allies
    public int healingPotion(){
        return this.getHeal();
    }

    //on defend action sets isDefending variable to true; is return to false in main when the action goes through
    public void Defend(){
        this.setIsDefending(true);
    }
}
