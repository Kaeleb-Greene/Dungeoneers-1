//Kaeleb
public class Wizard extends Player {
    
    public Wizard(String name){
        super(name, 8, 5, 0, "Wizard", false, false,0);
    }

    //basic attack; returns raw dmg output
    public int fireball(){
        return getDmg();
    }

    //gamblers attack; randomly does either 1 dmg or doubles base dmg
    public int vegasFireball(){
        if((int)(2 * Math.random()) == 0){
            return getDmg()/5;
        } else {
            return getDmg()*2;
        }
    }

    //on defend action sets isDefending variable to true; is return to false in main when the action goes through
    public void Defend(){
        this.setIsDefending(true);
    }

}
