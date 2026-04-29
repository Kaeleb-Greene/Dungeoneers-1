public abstract class Entity {
    private String name;
    private int hp;
    private int dmg;
    private boolean isPoisoned;

    public Entity(String name, int hp, int dmg, boolean isPoisoned, int poisonTurnsLeft){
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.isPoisoned = false;
        this.poisonTurnsLeft = poisonTurnsLeft;
    }

    public int getDmg(){ return dmg; }
    public void setDmg(int dmg){ this.dmg = dmg; }

    public int getHp(){ return hp; }
    public void setHp(int hp){ this.hp = hp; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public Boolean getIsPoisoned(){ return isPoisoned; }
    public void setIsPoisoned(boolean isPoisoned){ this.isPoisoned = isPoisoned; }

    public int getPoisonTurnsLeft(){return poisonTurnsLeft;}
    public void setPoisonTurnsLeft(int poisonTurnsLeft){this.poisonTurnsLeft = poisonTurnsLeft;}


    public void takeDmg(int damage){
        setHp(getHp() - getDmg());
    }

        //Attack types
    public int normalAttack(){
        return getDmg();
    }

    public int splashAttack(){
        //returns dmg but applies dmg to all whole Player arraylist
        return getDmg();
    }

    public int poisonAttack(){
        //returns dmg then do 1 additional dmg every turn for the next 3 turns
        setIsPoisoned(true);
        return getDmg();
    }

    public abstract boolean isMonster();
}
