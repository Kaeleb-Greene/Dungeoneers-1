//Kaeleb
public abstract class Entity {
    private String name;
    private int hp;
    private int dmg;
    private boolean isPoisoned;
    private int poisonTurnsLeft;
    private int floor = 1;

    public Entity(String name, int hp, int dmg, boolean isPoisoned, int poisonTurnsLeft){
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.isPoisoned = false;
        this.poisonTurnsLeft = poisonTurnsLeft;
    }

    //getters
    public String getName(){ return name; }
    public int getHp(){ return hp; }
    public int getDmg(){ return dmg; }
    public Boolean getIsPoisoned(){ return isPoisoned; }
    public int getFloor(){ return floor; }

    //setters
    public void setName(String name){ this.name = name; }
    public void setHp(int hp){ this.hp = hp; }
    public void setDmg(int dmg){ this.dmg = dmg; }
    public void setIsPoisoned(boolean isPoisoned){ this.isPoisoned = isPoisoned; }
    public void setFloor(int floor){ this.floor = floor; }

    public int getPoisonTurnsLeft(){return poisonTurnsLeft;}
    public void setPoisonTurnsLeft(int poisonTurnsLeft){this.poisonTurnsLeft = poisonTurnsLeft;}


     //calculates how an entity takes dmg
    public void takeDmg(int damage){
        setHp(getHp() - damage);
    }

    //attack types
    //basic attack type; returns raw dmg
    public int normalAttack(){
        return getDmg();
    }

    //multi attack; returns raw dmg, is applied to all party members in main
    public int splashAttack(){
        //returns dmg but applies dmg to all whole Player arraylist
        return getDmg();
    }

    //tick attack type; returns dmg as well as applies poison to target
    public int poisonAttack(Entity e){
        //if poisoned = true; do 1 additional dmg every turn for the next 3 turns
        e.setIsPoisoned(true);
        return getDmg();
    }

    //used to distinguish between monsters and players
    public abstract boolean isMonster();
}
