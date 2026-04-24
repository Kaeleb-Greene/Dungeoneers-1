public class Alchemist extends Player {
    
    public Alchemist(int hp, int dmg, String name, int heal, String subClass, boolean isDefending, boolean isPoisoned){
        super(name, hp, dmg, heal, subClass, isDefending, isPoisoned);
    }

    public void Action1(){

    }

    public void Action2(){

    }

    public void Defend(){
        this.setIsDefending(true);
    }
}
