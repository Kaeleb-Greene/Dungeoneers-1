public class Alchemist extends Player {
    
    public Alchemist(int hp, int dmg, String name, int heal, String subClass, boolean isDefending, boolean isPoisoned){
        super(name, hp, dmg, heal, subClass, isDefending, isPoisoned);
    }

    public Alchemist(String name){
        this.hp = 10;
        this.dmg = 2;
        this.name = name;
        this.heal = 3;
        this.subClass = "Alchemist";
        this.isDefending = false;
        this.isPoisoned = false;
    }

    public void Action1(){

    }

    public void Action2(){

    }

    public void Defend(){
        this.setIsDefending(true);
    }
}
