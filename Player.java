public abstract class Player extends Entity {
    private String subClass;
    private int heal;
    private boolean isDefending;

public Player(String name, int hp, int dmg, int heal, String subClass, boolean isDefending, boolean isPoisoned){
        super(name, hp, dmg, isPoisoned);
        this.subClass = subClass;
        this.heal = heal;
        this.isDefending = false;
    }

    public int getHeal() { return heal; }
    public boolean getIsDefending(){ return isDefending; }
    public void setIsDefending(boolean isDefending){ this.isDefending = isDefending; }

    public void heal(){
        setHp(getHp() + getHeal());
    }

    public void takeDmg(){
        if (getIsDefending() == true){
        setHp(getHp() - getDmg()/2);
        setIsDefending(false);
        } else {
        setHp(getHp() - getDmg());
        }
    }

    public boolean isMonster(){
        // confirms that the player is not a monster and should be distinct from monsters.
    }
}
