public class Wizard extends Player {
    
    Wizard(int hp, int dmg, String name, int heal, String subClass, boolean isDefending){
        super(name, hp, dmg, heal, subClass, isDefending);
    }

    public void Action1(){
        return Entity.getDmg();
    }

    public void Action2(){

    }

    public void Action3(){

    }

    public void Defend(){
        this.setIsDefending(true);
    }

}
