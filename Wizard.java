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
            System.out.println("Unlucky! Only did 1 damage!\n");
            return getDmg()/5;
        } else {
            System.out.println("Nice shot! You got a critical hit for 10 damage!\n");
            return getDmg()*2;
        }
    }

    //on defend action sets isDefending variable to true; is return to false in main when the action goes through
    public void Defend(){
        this.setIsDefending(true);
    }

}
