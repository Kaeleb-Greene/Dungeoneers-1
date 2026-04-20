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
        ArrayList<Monster> monsters = new ArrayList<Enemy>(3);
        //Add monsters based on level
        return monsters;
    }
    if (gameLost){
        System.out.println("Sorry! Better luck next time!");
    }
}