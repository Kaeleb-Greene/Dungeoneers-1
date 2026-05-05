//Kaeleb
public class Alchemist extends Player {
    
    public Alchemist(String name){
        super(name, 10, 2, 3, "Alchemist", false, false, 0);
    }

    //random attack; randomly returns raw dmg, applies poison to target, or weakens the target; on weakened target does only 1 dmg for their next attack
    public int throwPotion(Entity e){
        int Haselhoff  = ((int)(2 * Math.random()));
        if(Haselhoff == 0){
            System.out.println("\nA harming potion has been thrown!\n");
            return getDmg();
        } else {//(Haselhoff == 1)
            System.out.println("\nA poison potion has been thrown!");
            System.out.println("The enemy has been poisoned!\n");
            return poisonAttack(e);
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
