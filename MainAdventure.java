import java.util.Scanner;
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
            monsters.add(Monster("placeholder",4,2,"Skeleton"));
            monsters.add(Monster("placeholder",4,2,"Skeleton"));
            monsters.add(Monster("placeholder",4,2,"Skeleton"));
        } else if (level == 2){
            if(4*randNum < 1){
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
            } else if(4*randNum < 3){
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
            } else {
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
            }
        } else if (level == 3){
            if(5*randNum < 1){
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
            } else if(5*randNum < 3){
                monsters.add(Monster("placeholder",4,2,"Skeleton"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
                monsters.add(Monster("placeholder",9,2,"Goblin"));
            } else if(5*randNum < 4){
                monsters.add(Monster("placeholder",6,2,"Zombie"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
            } else {
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
            }
        } else if (level == 4){
            if(2*randNum < 1){
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
            } else {
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",6,2,"Zombie"));
            }
        } else if (level == 5){
            if(3*randNum < 2){
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
            } else {
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
            }
        } else if (level == 6){
            if(3*randNum < 1){
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
            } else {
                monsters.add(Monster("placeholder",9,2,"Goblin"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
            }
        } else if (level == 7){
            monsters.add(Monster("placeholder",10,3,"Raider"));
            monsters.add(Monster("placeholder",10,3,"Raider"));
            monsters.add(Monster("placeholder",10,3,"Raider"));
        } else if (level == 8){
            if(4*randNum < 1){
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
            } else {
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",14,4,"Orc"));
            }
        } else if (level == 9){
            if(4*randNum < 1){
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",14,4,"Orc"));
            } else {
                monsters.add(Monster("placeholder",10,3,"Raider"));
                monsters.add(Monster("placeholder",14,4,"Orc"));
                monsters.add(Monster("placeholder",14,4,"Orc"));
            }
        } else if (level == 10){
            monsters.add(Monster("placeholder",14,4,"Orc"));
            monsters.add(Monster("placeholder",14,4,"Orc"));
            monsters.add(Monster("placeholder",14,4,"Orc"));
        } else {
            if(5*randNum < 1){
                monsters.add(Monster("placeholder",14,4,"Orc"));
                monsters.add(Monster("placeholder",14,4,"Orc"));
                monsters.add(Monster("placeholder",14,4,"Orc"));
            } else if(5*randNum < 2){
                monsters.add(Monster("placeholder",14,4,"Orc"));
                monsters.add(Monster("placeholder",14,4,"Orc"));
                monsters.add(Monster("placeholder",18,4,"Troll"));
            } else if(5*randNum < 3){
                monsters.add(Monster("placeholder",14,4,"Orc"));
                monsters.add(Monster("placeholder",14,4,"Orc"));
                monsters.add(Monster("placeholder",30,2,"Golem"));
            } else if(5*randNum < 4){
                monsters.add(Monster("placeholder",14,4,"Orc"));
                monsters.add(Monster("placeholder",18,4,"Troll"));
                monsters.add(Monster("placeholder",30,2,"Golem"));
            } else {
                monsters.add(Monster("placeholder",18,4,"Troll"));
                monsters.add(Monster("placeholder",18,4,"Troll"));
                monsters.add(Monster("placeholder",30,2,"Golem"));
            }
        }
        return monsters;
    }
    if (gameLost){
        System.out.println("Sorry! Better luck next time!");
    }
}