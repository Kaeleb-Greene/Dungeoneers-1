//Kaeleb
public abstract class Player extends Entity {
    private String subClass;
    private int heal;
    private boolean isDefending;

public Player(String name, int hp, int dmg, int heal, String subClass, boolean isDefending, boolean isPoisoned, int poisonTurnsLeft){
        super(name, hp, dmg, isPoisoned,poisonTurnsLeft);
        this.subClass = subClass;
        this.heal = heal;
        this.isDefending = false;

    }

    //getters
    public int getHeal() { return heal; }
    public String getSubClass(){ return subClass; }
    public boolean getIsDefending(){ return isDefending; }

    //setters
    public void setIsDefending(boolean isDefending){ this.isDefending = isDefending; }

    //calculates how players should get healed
    public void heal(){
        setHp(getHp() + getHeal());
    }

    public void takeDmg(int damage){
    //calculates how players should take dmg; if isDefending is true, halfs the amount of incoming dmg
        if (getIsDefending() == true){
            setHp(getHp() - damage/2);
            setIsDefending(false);
        } else {
            setHp(getHp() - damage);
        }
    }

    public boolean isMonster(){
        //the player is not a monster.
        return false;
    }
}
