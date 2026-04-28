import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class MainAdventure {
    public static void main(String[] args){
        Scanner kb = new Scanner(System.in);
        //List of actions: move, open inventory, attack 1, special attack, 
        //        pause? (includes exit game, settings, return to game), check stats
        // Create characters, turn order, receive health changes/effects
        ArrayList<Player> party = createParty();
        //ArrayList<Adventurer> unconscious = new ArrayList<Adventurer>();
        boolean gameLost = false;
        int dungeonLevel = 1;
        while (!gameLost){
            ArrayList<Monster> monsters = createLevel(dungeonLevel);
            boolean levelCleared = false;

            ArrayList<Entity> entities = determineOrder(party, monsters);

            while (!levelCleared && !gameLost){
                for (Entity person: entities){
                    if(person.isMonster()){
                        computerAction(person);
                    } else {
                        takeAction(person);
                    }
                    if (party.isEmpty()){
                        gameLost = true;
                        System.out.println("Sorry! Better luck next time!");
                        break;
                    }
                }
            }
            if(levelCleared){
                System.out.println("Would you like to save?");
                String answer = kb.next();
                if(!(answer.equals("yes") || answer.equals("Yes") || answer.equals("YES"))){
                    // The player did not actually lose the game, but this breaks the loop. It will be reset when they load their save.
                    gameLost = true;
                    // temp
                    String save = vigenereCipher(linearCipher(party.get(0).toString()
                                                             + party.get(1).toString()
                                                             + party.get(2).toString()
                                                             + dungeonLevel, true),true);
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

    // This linear cipher has a decimation of 7 and a shift of 18
    public static String linearCipher(String s, boolean encoding){
        ArrayList<Integer> nums = new ArrayList<Integer>();
        // total variable that adds each character
        String total = "";
        if(encoding){
            for(int i=0; i<s.length(); i++){
                // does some operations to get a new int x corresponding to a character
                int x = s.charAt(i);
                x-=97;
                x*=7;
                x+=18;
                nums.add(x);
            }
            for(int i=0; i<nums.size(); i++){
                // does some operations to get that character to a lowercase letter
                int y = nums.get(i);
                y%=26;
                y+=97;
                total = total + (char)y;
            }
        } else {
            for(int i=0; i<s.length(); i++){
                // does some operations to get a new int x corresponding to a character
                int x = s.charAt(i);
                x-=97;
                for(int j=0;j>25;j++){
                    x*=7;
                    x+=18;
                    x%=26;
                }
                x+=97;
                total = total + (char)x;
            }
        }
        return total;
    }
    public static String vigenereCipher(String s, boolean encoding){
        // declares a total
        String total = "";
        // this key will be used as a shift
        String key = "landonvanberkum";
        for(int i=0; i<s.length(); i++){
            // declares various different int variables
            int x = s.charAt(i);
            int keyNum = key.charAt(i%15);
            int finalNum;
            // does some operations so we can properly shift the characters
            x-=97;
            keyNum-=97;
            if(encoding){ finalNum = x+keyNum; }
            else{ finalNum = x-keyNum; }
            finalNum%=26;
            finalNum+=97;
            // adds to the total
            total += (char)finalNum;
        }
        return total;
    }

    public static void computerAction(Monster m){
        int damage = m.takeAction();
        if(m.getType().equals("Megaknight")){
            party.get(0).takeDamage(damage);
            party.get(1).takeDamage(damage);
            party.get(2).takeDamage(damage);
        } else {
            int randomPartyMember = (int)(3*Math.random());
            // **REMINDER** add sneak and taunt in
            party.get(randomPartyMember).takeDamage(damage);
            if(m.getType().equals("Dragon") || m.getType().equals("Goblin")){
                if(4*Math.random() == 0){
                    party.get(randomPartyMember).setIsPoisoned(true);
                }
            }
        }
    }


    public static String getMonsterName(){
        Scanner kb = null;
        try {
            int randNum = (int)(80*Math.random());
            kb = new Scanner(new File("MonsterNames.txt"));
            for(int i=0;i<randNum;i++){
                kb.nextLine();
            }
            return kb.nextLine();
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