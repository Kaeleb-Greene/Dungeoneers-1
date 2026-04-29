public class Wizard extends Player {
    
    public Wizard(String name){
        super(name, 8, 5, 0, "Wizard", false, false,0);
    }

    public int fireball(){
        return getDmg();
    }

    public int vegasFireball(){
        if((int)(2 * Math.random()) == 0){
            return getDmg()/5;
        } else {
            return getDmg()*2;
        }
    }

    public void Defend(){
        this.setIsDefending(true);
    }

}
