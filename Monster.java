import java.io.File;
import java.util.Scanner;

public class Monster extends Entity{
    private String type;

public Monster(String name, int dmg, int hp, String type, boolean isPoisoned){
    super(name, hp, dmg, false);
    this.type = type;
}

public String getType(){ return type; }
public void setType(String type){ this.type = type; }

//add to main
public int takeAction(){
    if (type == "Megaknight"){
        return this.splashAttack();
    } else if(type == "Dragon"){
        return this.poisonAttack();
    } else if(type == "Goblin"){
        return this.poisonAttack();
    } else {
        return this.normalAttack();
    }
}
<<<<<<< HEAD

//Attack types
public int normalAttack(){
    return super.getDmg();
}

public int splashAttack(){
    //returns dmg but applies dmg to all whole Player arraylist
    return super.getDmg();
}

public int poisonAttack(){
    //returns dmg then do 1 additional dmg every turn for the next 3 turns
    return super.getDmg();
}

public boolean isMonster(){
    // confirms that they are, indeed, a monster.
    return true;
}
=======
>>>>>>> 252405f64ab501cb622e7256f7d087282f5f0ca7
}
