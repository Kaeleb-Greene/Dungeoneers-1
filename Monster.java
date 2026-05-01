//Kaeleb
public class Monster extends Entity{
    private String type;

public Monster(String name, int dmg, int hp, String type, boolean isPoisoned){
    super(name, hp, dmg, false,0);
    this.type = type;
}

    //getters
    public String getType(){ return type; }

    //setters
    public void setType(String type){ this.type = type; }

    public boolean isMonster(){
        //the monster is a monster :)
        return true;
    }
}