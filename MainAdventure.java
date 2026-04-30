import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class MainAdventure {
    public static void main(String[] args){
        Scanner kb = new Scanner(System.in);
        //List of actions: move, open inventory, attack 1, special attack, 
        //        pause? (includes exit game, settings, return to game), check stats
        // Create characters, turn order, receive health changes/effects
        ArrayList<Player> party = createParty(kb);
        //ArrayList<Adventurer> unconscious = new ArrayList<Adventurer>();
        boolean gameLost = false;
        int dungeonLevel = 1;
        while (!gameLost){
            ArrayList<Monster> monsters = createLevel(dungeonLevel, kb);
            boolean levelCleared = false;

            ArrayList<Entity> entities = determineOrder(party, monsters, kb);

            while (!levelCleared && !gameLost){
                for (Entity person: entities){
                    if(person.isMonster()){
                        computerAction((Monster) person, monsters, party);
                    } else {
                        monsters = takeAction((Player) person, monsters, party, kb);
                    }
                    if (party.isEmpty()){
                        gameLost = true;
                        System.out.println("Sorry! Better luck next time!");
                        break;
                    }
                }
                if (monsters.size() == 0){
                    levelCleared = true;
                }
            }
                
            entities = determineOrder(party, monsters, kb);
            if(levelCleared){
                System.out.println("Would you like to save? (Y/N)");
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
        kb.close();
    }

    public static ArrayList<Player> createParty(Scanner kb){
        int fileChoice = -1;
        ArrayList<Player> party = new ArrayList<Player>(3);
        System.out.println("Welcome to Dungeoneers! \n (1) Start New Game \n (2) Load Save?");
        while (fileChoice != 1 && fileChoice != 2){
            fileChoice = kb.nextInt();
            
        }
        if (fileChoice == 1){
                //Name, subClass
                for (int i = 1; i <= 3; i++){
                    System.out.println("Player " + i + ", Choose Your Subclass!");
                    System.out.println("(1) Knight: Slash your enemies with the sword! Save " +
                        "your teammates by taunting enemies to attack you! Most health!");
                    System.out.println("(2) Rogue: Sneak past your enemies! Poison an enemy with " +
                        "your dagger or quickly stab multiple in one turn! Least health!"); 
                    System.out.println("(3) Wizard: Roast your enemies with Fireballs! Magically " +
                        "block half of damage received. Low health!");
                    System.out.println("(4) Alchemist: Use potions to disable your opponents! " +
                        "Poison them over time, weaken their attacks, or just damage them. Medium health");
                    System.out.println("(5) Cleric: Bash enemies with your mace! Heal your allies! " +
                        "High health.");
                    
                    int nextClassChoice = kb.nextInt();
                    System.out.println("Player " + i + ", choose your name!");
                    kb.nextLine();
                    String nextName = kb.nextLine();
                    if (nextClassChoice == 1){
                        party.add(new Knight(nextName));
                        System.out.println("Successfully added Player " + i + ": " + nextName + " the Knight!");
                    } else if (nextClassChoice == 2){
                        party.add(new Rogue(nextName));
                        System.out.println("Successfully added Player " + i + ": " + nextName + " the Rogue!");
                    } else if (nextClassChoice == 3){
                        party.add(new Wizard(nextName));
                        System.out.println("Successfully added Player " + i + ": " + nextName + " the Wizard!");
                    } else if (nextClassChoice == 4){
                        party.add(new Alchemist(nextName));
                        System.out.println("Successfully added Player " + i + ": " + nextName + " the Alchemist!");
                    } else if (nextClassChoice == 5){
                        party.add(new Cleric(nextName));
                        System.out.println("Successfully added Player " + i + ": " + nextName + " the Cleric!");
                    }
                }
            } else { // if fileChoice == 2
                //Come back to this
                return party;

            }
        return party;
    }

    public static ArrayList <Entity> determineOrder(ArrayList<Player> party, ArrayList<Monster> monsters, Scanner kb){
        ArrayList<Entity> turnOrder = new ArrayList<Entity>();
        for (int i = 0; i < Math.min(party.size(),monsters.size()); i++){
            turnOrder.add(party.get(i));
            turnOrder.add(monsters.get(i));
        }
        if (party.size() > monsters.size()){
            for (int i = monsters.size(); i < party.size();i++){
                turnOrder.add(party.get(i));
            }
        }
        if (monsters.size() > party.size()){
            for (int i = party.size(); i < monsters.size();i++){
                turnOrder.add(monsters.get(i));
            }
        }
        return turnOrder;
    }


    public static ArrayList<Monster> createLevel(int level, Scanner kb){
        ArrayList<Monster> monsters = new ArrayList<Monster>(3);

        double randNum = Math.random();
        if(level == 1){
            monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
            monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
            monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
        } else if (level == 2){
            if(4*randNum < 1){
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
            } else if(4*randNum < 3){
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
            } else {
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
            }
        } else if (level == 3){
            if(5*randNum < 1){
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
            } else if(5*randNum < 3){
                monsters.add(new Monster(getMonsterName(),4,2,"Skeleton", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
            } else if(5*randNum < 4){
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
            } else {
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
            }
        } else if (level == 4){
            if(2*randNum < 1){
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
            } else {
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),6,2,"Zombie", false));
            }
        } else if (level == 5){
            if(3*randNum < 2){
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
            } else {
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
            }
        } else if (level == 6){
            if(3*randNum < 1){
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
            } else {
                monsters.add(new Monster(getMonsterName(),9,2,"Goblin", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
            }
        } else if (level == 7){
            monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
            monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
            monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
        } else if (level == 8){
            if(4*randNum < 1){
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
            } else {
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
            }
        } else if (level == 9){
            if(4*randNum < 1){
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
            } else {
                monsters.add(new Monster(getMonsterName(),10,3,"Raider", false));
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
            }
        } else if (level == 10){
            monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
            monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
            monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
        } else {
            if(5*randNum < 1){
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
            } else if(5*randNum < 2){
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
                monsters.add(new Monster(getMonsterName(),18,4,"Troll", false));
            } else if(5*randNum < 3){
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
                monsters.add(new Monster(getMonsterName(),30,2,"Golem", false));
            } else if(5*randNum < 4){
                monsters.add(new Monster(getMonsterName(),14,4,"Orc", false));
                monsters.add(new Monster(getMonsterName(),18,4,"Troll", false));
                monsters.add(new Monster(getMonsterName(),30,2,"Golem", false));
            } else {
                monsters.add(new Monster(getMonsterName(),18,4,"Troll", false));
                monsters.add(new Monster(getMonsterName(),18,4,"Troll", false));
                monsters.add(new Monster(getMonsterName(),30,2,"Golem", false));
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

    public static void computerAction(Monster m, ArrayList<Monster> monsters, ArrayList<Player> party){
       if (m.getHp() > 0){
        int damage = m.getDmg();
            if(m.getType().equals("Megaknight")){
                party.get(0).takeDmg(damage);
                party.get(1).takeDmg(damage);
                party.get(2).takeDmg(damage);
            } else {
                int randomPartyMember = 0;
                while(true){
                    randomPartyMember = (int)(3*Math.random());
                    int iterations = 0;
                    for(Player player : party){
                        if(player.getSubClass().equals("Knight")){
                            if(((Knight)player).getIsTaunting()){
                                if((int)(2*Math.random()) == 0){
                                    // taunt works
                                    randomPartyMember = iterations;
                                }
                            }
                        }
                        iterations++;
                    }
                    if(party.get(randomPartyMember).getSubClass().equals("Rogue")){
                        if((int)(2*Math.random()) == 0){
                            // sneak fails to work
                            break;
                        }
                    } else {
                        break;
                    }
                }
                party.get(randomPartyMember).takeDmg(damage);
                if(m.getType().equals("Dragon") || m.getType().equals("Goblin")){
                    if(4*Math.random() == 0){
                        party.get(randomPartyMember).setIsPoisoned(true);
                    }
                }
            }
       }
    }

    public static ArrayList<Monster> takeAction(Player player, ArrayList<Monster> monsters, ArrayList<Player> party, Scanner kb){
        int actionNumber;
        System.out.println("What action would you like to do?");
        if(player.getSubClass().equals("Knight")){
            Knight p = (Knight) player;

            System.out.println("0 | Swing Sword\n1 | Taunt\n2 | Defend");
            actionNumber = kb.nextInt();
            while(true){
                try{
                    if(actionNumber == 0){
                        System.out.println(p.getName() + ", which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getType() + " | " + monster.getHp() + "hp");
                            iterations++;
                        }
                        actionNumber = kb.nextInt();
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.swingSword());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.swingSword());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.swingSword());
                        } else {
                            throw new InvalidMoveException();
                        }
                        break;
                    } else if(actionNumber == 1){
                        p.taunt();
                        break;
                    } else if(actionNumber == 2){
                        p.Defend();
                        break;
                    } else {
                        throw new InvalidMoveException();
                    }
                }catch(InvalidMoveException ime){
                    System.out.println("Please input a valid number");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } else if(player.getSubClass().equals("Rogue")){
            Rogue p = (Rogue) player;
            System.out.println("0 | Poison Dagger\n1 | Multi Attack\n2 | Defend");
            actionNumber = kb.nextInt();
            while(true){
                try{
                    if(actionNumber == 0){
                        System.out.println("Which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getType() + " | " + monster.getHp() + " hp");
                            iterations++;
                        }
                        actionNumber = kb.nextInt();
                        // POISON EFFECT?
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.poisonDagger());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.poisonDagger());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.poisonDagger());
                        } else {
                            throw new InvalidMoveException();
                        }
                        break;
                    } else if(actionNumber == 1){
                        for(Monster monster : monsters)
                            monster.takeDmg(p.multiAttack());
                        break;
                    } else if(actionNumber == 2){
                        p.Defend();
                        break;
                    } else {
                        throw new InvalidMoveException();
                    }
                }catch(InvalidMoveException ime){
                    System.out.println("Please input a valid number");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } else if(player.getSubClass().equals("Wizard")){
            Wizard p = (Wizard) player;
            System.out.println("0 | Fireball\n1 | Vegas Fireball\n2 | Defend");
            actionNumber = kb.nextInt();
            while(true){
                try{
                    if(actionNumber == 0){
                        System.out.println("Which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getType() + " | " + monster.getHp() + "hp");
                            iterations++;
                        }
                        actionNumber = kb.nextInt();
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.fireball());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.fireball());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.fireball());
                        } else {
                            throw new InvalidMoveException();
                        }
                        break;
                    } else if(actionNumber == 1){System.out.println("Which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getType() + " | " + monster.getHp() + "hp");
                            iterations++;
                        }
                        actionNumber = kb.nextInt();
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.vegasFireball());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.vegasFireball());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.vegasFireball());
                        } else {
                            throw new InvalidMoveException();
                        }
                        break;
                    } else if(actionNumber == 2){
                        p.Defend();
                        break;
                    } else {
                        throw new InvalidMoveException();
                    }
                }catch(InvalidMoveException ime){
                    System.out.println("Please input a valid number");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } else if(player.getSubClass().equals("Alchemist")){
            Alchemist p = (Alchemist) player;
            System.out.println("0 | Throw Potion\n1 | Healing Potion\n2 | Defend");
            actionNumber = kb.nextInt();
            while(true){
                try{
                    if(actionNumber == 0){
                        System.out.println("Which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getType() + " | " + monster.getHp() + "hp");
                            iterations++;
                        }
                        actionNumber = kb.nextInt();
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.throwPotion());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.throwPotion());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.throwPotion());
                        } else {
                            throw new InvalidMoveException();
                        }
                        break;
                    } else if(actionNumber == 1){
                        System.out.println("Which character would you like to heal?\n0 | Player 1\n1 | Player 2\n2 | Player 3");
                        actionNumber = kb.nextInt();
                        if(actionNumber == 0){
                            party.get(0).heal(p.healingPotion());
                        } else if(actionNumber == 1){
                            party.get(1).takeDmg(p.healingPotion());
                        } else if(actionNumber == 2){
                            party.get(2).takeDmg(p.healingPotion());
                        } else {
                            throw new InvalidMoveException();
                        }
                        break;
                    } else if(actionNumber == 2){
                        p.Defend();
                        break;
                    } else {
                        throw new InvalidMoveException();
                    }
                }catch(InvalidMoveException ime){
                    System.out.println("Please input a valid number");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } else {
            Cleric p = (Cleric) player;
            System.out.println("0 | Swing Mace\n1 | Healing Spell\n2 | Defend");
            actionNumber = kb.nextInt();
            while(true){
                try{
                    if(actionNumber == 0){
                        System.out.println("Which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getType() + " | " + monster.getHp() + "hp");
                            iterations++;
                        }
                        actionNumber = kb.nextInt();
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.swingMace());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.swingMace());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.swingMace());
                        } else {
                            throw new InvalidMoveException();
                        }
                        break;
                    } else if(actionNumber == 1){
                        System.out.println("Which character would you like to heal?\n0 | Player 1\n1 | Player 2\n2 | Player 3");
                        actionNumber = kb.nextInt();
                        if(actionNumber == 0){
                            party.get(0).heal(p.healingSpell());
                        } else if(actionNumber == 1){
                            party.get(1).takeDmg(p.healingSpell());
                        } else if(actionNumber == 2){
                            party.get(2).takeDmg(p.healingSpell());
                        } else {
                            throw new InvalidMoveException();
                        }
                        break;
                    } else if(actionNumber == 2){
                        p.Defend();
                        break;
                    } else {
                        throw new InvalidMoveException();
                    }
                }catch(InvalidMoveException ime){
                    System.out.println("Please input a valid move.");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        ArrayList<Monster> m = new ArrayList<Monster>();
        for (Monster monster: monsters){
            if (monster.getHp() > 0){
                m.add(monster);
            }
        }
        return m;
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
    


    public static void poisonCheck(Entity Entity){
        if(Entity.getIsPoisoned() == true){
            Entity.takeDmg(1);
            Entity.setPoisonTurnsLeft(Entity.getPoisonTurnsLeft()-1);
        }
        //checks each turn if player/monster isPoisoned = true, if true deals 1 dmg.
        //Need to lower one turn of poison
    } 

}