public class Wizard extends Player {
    
    public Wizard(int hp, int dmg, String name, int heal, String subClass, boolean isDefending){
        super(name, hp, dmg, heal, subClass, isDefending);
    }

    public int Action1(){
        return this.getDmg();
    }

    public void Action2(){

    }

    public void Action3(){

    }

    public void Defend(){
        this.setIsDefending(true);
    }

}
