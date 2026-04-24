import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class MainAdventure {
    public static void Main(String[] args){
        //List of actions: move, open inventory, attack 1, special attack, 
        //        pause? (includes exit game, settings, return to game), check stats
        // Create characters, turn order, receive health changes/effects
        ArrayList<Adventurer> party = createParty();
        //ArrayList<Adventurer> unconscious = new ArrayList<Adventurer>();
        boolean gameLost = false;
        int dungeonLevel = 1;
        while (!gameLost){
            ArrayList<Monster> monsters = createLevel(dungeonLevel);
            boolean levelCleared = false;

            ArrayList<Entity> entities = determineOrder(party, monsters);

            while (!levelCleared && !gameLost){
                for (Entity person: entities){
                    person.takeAction();

                    if (party.isEmpty()){
                        gameLost = true;
                        break;
                    }
                }
            }
        }
    }

    public static ArrayList<Monster> createLevel(int level){
        ArrayList<Monster> monsters = new ArrayList<Monster>(3);
        double randNum = Math.random();
        if(level == 1){
            monsters.add(Monster("placeholder",4,2,"Skeleton", false));
            monsters.add(Monster("placeholder",4,2,"Skeleton", false));
            monsters.add(Monster("placeholder",4,2,"Skeleton", false));
        } else if (level == 2){
            if(4*randNum < 1){
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
            } else if(4*randNum < 3){
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
            } else {
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
            }
        } else if (level == 3){
            if(5*randNum < 1){
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
            } else if(5*randNum < 3){
                monsters.add(Monster("placeholder",4,2,"Skeleton", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
            } else if(5*randNum < 4){
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
            } else {
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
            }
        } else if (level == 4){
            if(2*randNum < 1){
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
            } else {
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",6,2,"Zombie", false));
            }
        } else if (level == 5){
            if(3*randNum < 2){
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
            } else {
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
            }
        } else if (level == 6){
            if(3*randNum < 1){
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
            } else {
                monsters.add(Monster("placeholder",9,2,"Goblin", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
            }
        } else if (level == 7){
            monsters.add(Monster("placeholder",10,3,"Raider", false));
            monsters.add(Monster("placeholder",10,3,"Raider", false));
            monsters.add(Monster("placeholder",10,3,"Raider", false));
        } else if (level == 8){
            if(4*randNum < 1){
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
            } else {
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",14,4,"Orc", false));
            }
        } else if (level == 9){
            if(4*randNum < 1){
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",14,4,"Orc", false));
            } else {
                monsters.add(Monster("placeholder",10,3,"Raider", false));
                monsters.add(Monster("placeholder",14,4,"Orc", false));
                monsters.add(Monster("placeholder",14,4,"Orc", false));
            }
        } else if (level == 10){
            monsters.add(Monster("placeholder",14,4,"Orc", false));
            monsters.add(Monster("placeholder",14,4,"Orc", false));
            monsters.add(Monster("placeholder",14,4,"Orc", false));
        } else {
            if(5*randNum < 1){
                monsters.add(Monster("placeholder",14,4,"Orc", false));
                monsters.add(Monster("placeholder",14,4,"Orc", false));
                monsters.add(Monster("placeholder",14,4,"Orc", false));
            } else if(5*randNum < 2){
                monsters.add(Monster("placeholder",14,4,"Orc", false));
                monsters.add(Monster("placeholder",14,4,"Orc", false));
                monsters.add(Monster("placeholder",18,4,"Troll", false));
            } else if(5*randNum < 3){
                monsters.add(Monster("placeholder",14,4,"Orc", false));
                monsters.add(Monster("placeholder",14,4,"Orc", false));
                monsters.add(Monster("placeholder",30,2,"Golem", false));
            } else if(5*randNum < 4){
                monsters.add(Monster("placeholder",14,4,"Orc", false));
                monsters.add(Monster("placeholder",18,4,"Troll", false));
                monsters.add(Monster("placeholder",30,2,"Golem", false));
            } else {
                monsters.add(Monster("placeholder",18,4,"Troll", false));
                monsters.add(Monster(getMonsterName(),18,4,"Troll", false));
                monsters.add(Monster(getMonsterName(),30,2,"Golem", false));
            }
        }
        return monsters;
    }
    if (gameLost){
        System.out.println("GAME OVER! Better luck next time!");
    }


    public static String getMonsterName(){
        Scanner kb = null;
        try {
            int randNum = (int)(80*Math.random());
            kb = new Scanner(new File("MonsterNames.txt"));
            for(int i=0;i<randNum;i++){
                kb.nextLine();
            }
            return kb.nextLine() + " the " + Monster.getType();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return "";
        } finally{
            kb.close();
        }
    }


    public int poisonCheck(Entity Entity){
        if(Entity.getIsPoisoned() == true){
            Entity.takeDmg();
        } else {
            return
        }
        //checks each turn if player/monster isPoisoned = true, if true deals 1 dmg.
    } 

}