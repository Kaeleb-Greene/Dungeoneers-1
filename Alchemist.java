public class Alchemist extends Player {
    
    public Alchemist(String name){
        super(name, 10, 2, 3, "Alchemist", false, false);
    }

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

    public int healingPotion(){
        return this.getHeal();
    }

    public void Defend(){
        this.setIsDefending(true);
    }
}
