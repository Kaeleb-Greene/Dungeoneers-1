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
}
