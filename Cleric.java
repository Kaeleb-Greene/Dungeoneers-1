public class Cleric extends Player {
    
    public Cleric(String name){
        super(name, 12, 2, 4, "Cleric", false, false,0);
    }


    public int swingMace(){

        return this.getDmg();
    }

    public int healingSpell(){
        return this.getHeal();
    }

    public void Defend(){
        this.setIsDefending(true);
    }
}
