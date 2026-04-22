public class Knight extends Player{

    public Knight(int hp, int dmg, String name, int heal, String subClass, boolean isDefending){
        super(name, hp, dmg, heal, subClass, isDefending);
    }
    
    public void Action1(){

    }

    public void Action2(){

    }

    public void Defend(){
        this.setIsDefending(true);
    }
}
