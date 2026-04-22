public class Cleric extends Player {
    
    Cleric(int hp, int dmg, String name, int heal, String subClass, boolean isDefending){
        super(name, hp, dmg, heal, subClass, isDefending);
    }


    public int Action1(){
        return this.getDmg();
    }

    public int Action2(){
        return this.getHeal();
    }

    public void Defend(){
        this.setIsDefending(true);
    }
}
