import java.io.File;
import java.util.Scanner;

public abstract class Monster extends Entity{
    private String type;

Monster(String name, int dmg, int hp, String type){
    super(name, hp, dmg);
    this.type = type;
}

public String getType(){ return type; }
public void setType(String type){ this.type = type; }

    public String getMonsterName(){
        Scanner kb = null;
        try {
            int randNum = (int)(80*Math.random());
            kb = new Scanner(new File("MonsterNames.txt"));
            for(int i=0;i<randNum;i++){
                kb.nextLine();
            }
            return kb.nextLine() + " the " + type;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return "";
        } finally{
            kb.close();
        }
    }

//add to main
public int takeAction(){
    if (type = Megaknight){
        return splashAttack();
    } else if(type = Dragon){
        return poisonAttack();
    } else if(type = Goblin){
        return poisonAttack();
    } else {
        return normalAttack();
    }
}

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

}
