public class Cleric extends Player {
    
    public Cleric(int hp, int dmg, String name, int heal, String subClass, boolean isDefending, boolean isPoisoned){
        super(name, hp, dmg, heal, subClass, isDefending, isPoisoned);
    }

    public Cleric(String name){
        this.hp = ;
        this.dmg = ;
        this.name = name;
        this.heal = ;
        this.subClass = cleric;
        this.isDefending = false;
        this.isPoisoned = false;
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
