import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class MainAdventure {
    //Miles/Nathan
    public static void main(String[] args){
        System.out.println("\u001B[0m");
        Scanner kb = new Scanner(System.in);
        //List of actions: move, open inventory, attack 1, special attack, 
        //        pause? (includes exit game, settings, return to game), check stats
        //Create characters, turn order, receive health changes/effects
        ArrayList<Player> party = createParty(kb);
        // Either this value is one or it is the value from the loaded save.
        int dungeonLevel = party.get(0).getFloor();
        boolean gameLost = false;
        while (!gameLost){
            ArrayList<Monster> monsters = createLevel(dungeonLevel, kb);
            boolean levelCleared = false;
            System.out.println("\n\u001B[32mWelcome to Level " + dungeonLevel + "!\u001B[0m\n");
            ArrayList<Entity> entities = determineOrder(party, monsters, kb);

            while (!levelCleared && !gameLost){
                for (Entity person: entities){
                    poisonCheck(person);
                    for(Player player : party){
                        if(player.getHp() < 0){
                            player.setHp(0);
                        }
                    }
                    if(person.isMonster()){
                        Monster currentEnemy = (Monster)person;
                        System.out.println("\n"+currentEnemy.getName() + " the " + currentEnemy.getType() + "'s turn!\n");
                        party = computerAction((Monster) person, monsters, party);
                    } else {
                        System.out.println("Your Party: ");
                        for (Player player: party){
                            System.out.print(player.getName() + " the " + player.getSubClass() + ": " + player.getHp() + " health");
                            if (player.getIsPoisoned()){
                                System.out.println(" (Poisoned)");
                            } else{
                                System.out.println();
                            }
                        }
                        System.out.println("\nMonster List: ");
                        for (Monster monster: monsters){
                            System.out.print(monster.getName() + " the " + monster.getType() + ": " + monster.getHp() + " health");
                            if (monster.getIsPoisoned()){
                                System.out.println(" (Poisoned)");
                            } else{
                                System.out.println();
                            }
                        }
                        Player currentPlayer = (Player)person;
                        System.out.println("\n"+currentPlayer.getName() + " the " + currentPlayer.getSubClass() + "'s turn!");
                        monsters = takeAction((Player) person, monsters, party, kb, dungeonLevel);
                    }
                    if (party.get(0).getHp() <= 0 && party.get(1).getHp() <= 0 && party.get(2).getHp() <= 0){
                        gameLost = true;
                        System.out.println("\u001B[31mOh no, your entire party is unconscious!\u001B[0m\n\n");
                        if (dungeonLevel <= 5){
                            System.out.println("\u001B[31mYou cleared a total of " + (dungeonLevel-1) + " floors.\u001B[0m\n\n");
                        } else if (dungeonLevel >= 10){
                            System.out.println("\u001B[34mYou cleared a total of " + (dungeonLevel-1) + " floors.\u001B[0m\n\n");
                        } else{ //6-9
                        System.out.println("You cleared a total of " + (dungeonLevel-1) + " floors.\n\n");
                        }
                        break;
                    }
                    if (monsters.size() == 0){
                        levelCleared = true;
                        System.out.println("\n\u001B[32mYou completed Floor " + dungeonLevel + "!\u001B[0m\n"); 
                        // Restores party hp and revives dead party members
                        dungeonLevel++;
                        // heals all players
                        for(Player player : party){
                            if(player.getSubClass().equals("Knight")){
                                player.setHp(14);
                            } else if(player.getSubClass().equals("Cleric")){
                                player.setHp(12);
                            } else if(player.getSubClass().equals("Alchemist")){
                                player.setHp(10);
                            } else if(player.getSubClass().equals("Wizard")){
                                player.setHp(8);
                            } else if(player.getSubClass().equals("Rogue")){
                                player.setHp(6);
                            }
                            player.setIsPoisoned(false);
                            player.setPoisonTurnsLeft(0);
                            player.setIsDefending(false);
                        }
                        break;
                    }
                }
            }
                
            entities = determineOrder(party, monsters, kb);
            if(levelCleared){
                System.out.println("Would you like to save and exit? (Y/N)");
                String answer = kb.next();
                if((answer.equals("yes") || answer.equals("Yes") || answer.equals("YES") || answer.equals("Y") || answer.equals("y"))){
                    // The player did not actually lose the game, but this breaks the loop. It will be reset when they load their save.
                    gameLost = true;
                    String save = party.get(0).getSubClass() + "\n"
                                + party.get(0).getName() + "\n"
                                + party.get(1).getSubClass() + "\n"
                                + party.get(1).getName() + "\n"
                                + party.get(2).getSubClass() + "\n"
                                + party.get(2).getName() + "\n"
                                + dungeonLevel;
                    saveToBinaryFile(save);
                }
            }
        }
        kb.close();
    }

    //Nathan
    //Character creation interface
        //welcomes players to the game, prompts them to make their characters names and subclasses
    public static ArrayList<Player> createParty(Scanner kb){
        int fileChoice = -1;
        ArrayList<Player> party = new ArrayList<Player>(3);
        System.out.println("Welcome to Dungeoneers! \n 1 | Start New Game \n 2 | Load Save");
        while (fileChoice != 1 && fileChoice != 2){
            fileChoice = kb.nextInt();
            
        }
        if (fileChoice == 1){
                //Name, subClass
                for (int i = 1; i <= 3; i++){
                    System.out.println("\nPlayer " + i + ", Choose Your Subclass!");
                    System.out.println("1 | Knight: Slash your enemies with the sword! Save " +
                        "your teammates by taunting enemies to attack you! Most health!");
                    System.out.println("2 | Rogue: Sneak past your enemies! Poison an enemy with " +
                        "your dagger or quickly stab multiple in one turn! Least health!"); 
                    System.out.println("3 | Wizard: Roast your enemies with Fireballs! Vegas Fireball can do huge damage-- if you're lucky... Low health!");
                    System.out.println("4 | Alchemist: Use potions to harm your opponents or heal your allies! " +
                        "Poison them over time or just damage them. Medium health");
                    System.out.println("5 | Cleric: Bash enemies with your mace! Heal your allies! " +
                        "High health.");
                    int nextClassChoice = 0;
                    while(nextClassChoice > 5 || nextClassChoice < 1){
                        try {
                            nextClassChoice = kb.nextInt();
                            if(nextClassChoice > 5 || nextClassChoice < 1){
                                System.out.println("\u001B[33mPlease input a valid number.\u001B[0m");
                            }
                        } catch (Exception e) {
                            System.out.println("\u001B[33mWow, attempting to make a fool of us by inputting a string rather than an int?\u001B[0m");
                            System.out.println("\u001B[33mWe will not be standing for this. Good luck surviving the dungeon now...\u001B[0m");
                            nextClassChoice = 6;
                            break;
                        }
                    }
                    System.out.println("\nPlayer " + i + ", choose your name!");
                    kb.nextLine();
                    String nextName = kb.nextLine();
                    if (nextClassChoice == 1){
                        party.add(new Knight(nextName));
                        System.out.println("Welcome, Player " + i + ": " + nextName + " the Knight!");
                    } else if (nextClassChoice == 2){
                        party.add(new Rogue(nextName));
                        System.out.println("Welcome, Player " + i + ": " + nextName + " the Rogue!");
                    } else if (nextClassChoice == 3){
                        party.add(new Wizard(nextName));
                        System.out.println("Welcome, Player " + i + ": " + nextName + " the Wizard!");
                    } else if (nextClassChoice == 4){
                        party.add(new Alchemist(nextName));
                        System.out.println("Welcome, Player " + i + ": " + nextName + " the Alchemist!");
                    } else if (nextClassChoice == 5){
                        party.add(new Cleric(nextName));
                        System.out.println("Welcome, Player " + i + ": " + nextName + " the Cleric!");
                    } else if (nextClassChoice == 6){
                        party.add(new Knight(nextName, 1));
                        System.out.println("Welcome, Player " + i + ": " + nextName + " the Knight!");
                    }
                }
            } else { // if fileChoice == 2
                party = readSaveFromBinaryFile();
                return party;

            }
        return party;
    }

    //Nathan
    //Determines who is attacking at a given time
        //normal turn order: Player 1, AI 1, Player 2, AI 2, Player 3, AI 3
        //changes depending on how amount of monsters/players in their respective party
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


    //Collaborative
    //The Wall of Monsters
    //determines how monsters are spawned based on current floor
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
        int randInt = (int)(20*Math.random());
        if(randInt == 0){
            randInt = (int)(3*Math.random());
            monsters.remove(randInt);
            monsters.add(new Monster(getMonsterName(), 30, 3, "Megaknight", false));
        } else if (randInt >= 15 && level > 10){
            randInt = (int)(3*Math.random());
            monsters.remove(randInt);
            monsters.add(new Monster(getMonsterName(), 30, 5, "Dragon", false));
        }
        return monsters;
    }

    //Miles
    //determines who the AI will attack in combat
    public static ArrayList<Player> computerAction(Monster m, ArrayList<Monster> monsters, ArrayList<Player> party){
        if (m.getHp() > 0){
        int damage = m.getDmg();
            if(m.getType().equals("Megaknight")){
                if(party.get(0).getHp() > 0)
                    party.get(0).takeDmg(damage);
                if(party.get(1).getHp() > 0)
                    party.get(1).takeDmg(damage);
                if(party.get(2).getHp() > 0)
                    party.get(2).takeDmg(damage);
                System.out.println(m.getName() + " the Megaknight dealt " + damage + " to all players!\n");
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
                    if(party.get(randomPartyMember).getHp() > 0){
                        if(party.get(randomPartyMember).getSubClass().equals("Rogue")){
                            if((int)(2*Math.random()) == 0){
                                // sneak fails to work
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                System.out.println("\n" + m.getName() + " the " + m.getType() + " dealt " + damage + " damage to " + party.get(randomPartyMember).getName() + ".\n");
                if (party.get(randomPartyMember).getIsDefending()){
                    System.out.println("But some damage was defended.\n");
                }
                party.get(randomPartyMember).takeDmg(damage);
                if(m.getType().equals("Dragon") || m.getType().equals("Goblin")){
                    if((int)(4*Math.random()) == 0){
                        party.get(randomPartyMember).setIsPoisoned(true);
                        party.get(randomPartyMember).setPoisonTurnsLeft(5);
                        System.out.println(party.get(randomPartyMember) + " is poisoned for 4 turns!\n");
                    }
                }
            }
        } else {
            System.out.println("But they were unable to move!\n");
        }
        return party;
    }

    //Miles
    //prompts actions from a player based on that players subclass
    public static ArrayList<Monster> takeAction(Player player, ArrayList<Monster> monsters, ArrayList<Player> party, Scanner kb, int dungeonLevel){
        if(player.getHp() <= 0){
            System.out.println("\nBut they were unresponsive...");
            return monsters;
        }
        int actionNumber;
        System.out.println("\nWhat action would you like to do?");
        if(player.getSubClass().equals("Knight")){
            Knight p = (Knight) player;

            System.out.println("0 | Swing Sword\n1 | Taunt\n2 | Defend");
            while(true){
                try {
                    actionNumber = kb.nextInt();
                } catch (Exception e) {
                    System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                    String save = party.get(0).getSubClass() + "\n"
                                + party.get(0).getName() + "\n"
                                + party.get(1).getSubClass() + "\n"
                                + party.get(1).getName() + "\n"
                                + party.get(2).getSubClass() + "\n"
                                + party.get(2).getName() + "\n"
                                + dungeonLevel;
                    saveToBinaryFile(save);
                    for(Player member : party){
                        member.setHp(0);
                    }
                    break;
                }
                try{
                    if(actionNumber == 0){
                        System.out.println("\n" + p.getName() + ", which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getName() + " the " + monster.getType() + ": " + monster.getHp() + " health");
                            iterations++;
                        }
                        try {
                            actionNumber = kb.nextInt();
                        } catch (Exception e) {
                            System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                            String save = party.get(0).getSubClass() + "\n"
                                        + party.get(0).getName() + "\n"
                                        + party.get(1).getSubClass() + "\n"
                                        + party.get(1).getName() + "\n"
                                        + party.get(2).getSubClass() + "\n"
                                        + party.get(2).getName() + "\n"
                                        + dungeonLevel;
                            saveToBinaryFile(save);
                            for(Player member : party){
                                member.setHp(0);
                            }
                            break;
                        }
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.swingSword());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.swingSword());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.swingSword());
                        } else {
                            throw new InvalidMoveException();
                        }
                        System.out.println("Dealt " + player.getDmg() + " damage!");
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
                    System.out.println("\u001B[33mPlease input a valid number\u001B[0m");
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Swing Sword\n1 | Taunt\n2 | Defend");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Swing Sword\n1 | Taunt\n2 | Defend");
                }
            }
        } else if(player.getSubClass().equals("Rogue")){
            Rogue p = (Rogue) player;
            System.out.println("0 | Poison Dagger\n1 | Multi Attack\n2 | Defend");
            while(true){
                try {
                    actionNumber = kb.nextInt();
                } catch (Exception e) {
                    System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                    String save = party.get(0).getSubClass() + "\n"
                                + party.get(0).getName() + "\n"
                                + party.get(1).getSubClass() + "\n"
                                + party.get(1).getName() + "\n"
                                + party.get(2).getSubClass() + "\n"
                                + party.get(2).getName() + "\n"
                                + dungeonLevel;
                    saveToBinaryFile(save);
                    for(Player member : party){
                        member.setHp(0);
                    }
                    break;
                }
                try{
                    if(actionNumber == 0){
                        System.out.println("\n" + p.getName() + ", which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getName() + " the " + monster.getType() + ": " + monster.getHp() + " health");
                            iterations++;
                        }
                        try {
                            actionNumber = kb.nextInt();
                        } catch (Exception e) {
                            System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                            String save = party.get(0).getSubClass() + "\n"
                                        + party.get(0).getName() + "\n"
                                        + party.get(1).getSubClass() + "\n"
                                        + party.get(1).getName() + "\n"
                                        + party.get(2).getSubClass() + "\n"
                                        + party.get(2).getName() + "\n"
                                        + dungeonLevel;
                            saveToBinaryFile(save);
                            for(Player member : party){
                                member.setHp(0);
                            }
                            break;
                        }
                        // POISON EFFECT?
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.poisonDagger(monsters.get(0)));
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.poisonDagger(monsters.get(1)));
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.poisonDagger(monsters.get(2)));
                        } else {
                            throw new InvalidMoveException();
                        }
                        System.out.println("Dealt " + player.getDmg() + " damage!");
                        break;
                    } else if(actionNumber == 1){
                        for(Monster monster : monsters)
                            monster.takeDmg(p.multiAttack());
                        System.out.println(p.getName() + " the Rogue dealt 2 damage to each enemy!\n");
                        break;
                    } else if(actionNumber == 2){
                        p.Defend();
                        break;
                    } else {
                        throw new InvalidMoveException();
                    }
                }catch(InvalidMoveException ime){
                    System.out.println("\u001B[33mPlease input a valid number.\u001B[0m");
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Poison Dagger\n1 | Multi Attack\n2 | Defend");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("\n\u001B[33mWhat action would you like to do?\u001B[0m");
                    System.out.println("0 | Poison Dagger\n1 | Multi Attack\n2 | Defend");
                }
            }
        } else if(player.getSubClass().equals("Wizard")){
            Wizard p = (Wizard) player;
            System.out.println("0 | Fireball\n1 | Vegas Fireball\n2 | Defend");
            while(true){
                try {
                    actionNumber = kb.nextInt();
                } catch (Exception e) {
                    System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                    String save = party.get(0).getSubClass() + "\n"
                                + party.get(0).getName() + "\n"
                                + party.get(1).getSubClass() + "\n"
                                + party.get(1).getName() + "\n"
                                + party.get(2).getSubClass() + "\n"
                                + party.get(2).getName() + "\n"
                                + dungeonLevel;
                    saveToBinaryFile(save);
                    for(Player member : party){
                        member.setHp(0);
                    }
                    break;
                }
                try{
                    if(actionNumber == 0){
                        System.out.println("\n" + p.getName() + ", which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getName() + " the " + monster.getType() + ": " + monster.getHp() + " health");
                            iterations++;
                        }
                        try {
                            actionNumber = kb.nextInt();
                        } catch (Exception e) {
                            System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                            String save = party.get(0).getSubClass() + "\n"
                                        + party.get(0).getName() + "\n"
                                        + party.get(1).getSubClass() + "\n"
                                        + party.get(1).getName() + "\n"
                                        + party.get(2).getSubClass() + "\n"
                                        + party.get(2).getName() + "\n"
                                        + dungeonLevel;
                            saveToBinaryFile(save);
                            for(Player member : party){
                                member.setHp(0);
                            }
                            break;
                        }
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.fireball());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.fireball());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.fireball());
                        } else {
                            throw new InvalidMoveException();
                        }
                        System.out.println(monsters.get(actionNumber).getName() + " the " + monsters.get(actionNumber).getType() + " took 5 damage!");
                        break;
                    } else if(actionNumber == 1){
                        System.out.println("\n" + p.getName() + ", which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getName() + " the " + monster.getType() + ": " + monster.getHp() + " health");
                            iterations++;
                        }
                        try {
                            actionNumber = kb.nextInt();
                        } catch (Exception e) {
                            System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                            String save = party.get(0).getSubClass() + "\n"
                                        + party.get(0).getName() + "\n"
                                        + party.get(1).getSubClass() + "\n"
                                        + party.get(1).getName() + "\n"
                                        + party.get(2).getSubClass() + "\n"
                                        + party.get(2).getName() + "\n"
                                        + dungeonLevel;
                            saveToBinaryFile(save);
                            for(Player member : party){
                                member.setHp(0);
                            }
                            break;
                        }
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
                    System.out.println("\u001B[33mPlease input a valid number.\u001B[0m");
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Fireball\n1 | Vegas Fireball\n2 | Defend");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Fireball\n1 | Vegas Fireball\n2 | Defend");
                }
            }
        } else if(player.getSubClass().equals("Alchemist")){
            Alchemist p = (Alchemist) player;
            System.out.println("0 | Throw Potion\n1 | Healing Potion\n2 | Defend");
            while(true){
                try {
                    actionNumber = kb.nextInt();
                } catch (Exception e) {
                    System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                    String save = party.get(0).getSubClass() + "\n"
                                + party.get(0).getName() + "\n"
                                + party.get(1).getSubClass() + "\n"
                                + party.get(1).getName() + "\n"
                                + party.get(2).getSubClass() + "\n"
                                + party.get(2).getName() + "\n"
                                + dungeonLevel;
                    saveToBinaryFile(save);
                    for(Player member : party){
                        member.setHp(0);
                    }
                    break;
                }
                try{
                    if(actionNumber == 0){
                        System.out.println("\n" + p.getName() + ", which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getName() + " the " + monster.getType() + ": " + monster.getHp() + " health");
                            iterations++;
                        }
                        try {
                            actionNumber = kb.nextInt();
                        } catch (Exception e) {
                            System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                            String save = party.get(0).getSubClass() + "\n"
                                        + party.get(0).getName() + "\n"
                                        + party.get(1).getSubClass() + "\n"
                                        + party.get(1).getName() + "\n"
                                        + party.get(2).getSubClass() + "\n"
                                        + party.get(2).getName() + "\n"
                                        + dungeonLevel;
                            saveToBinaryFile(save);
                            for(Player member : party){
                                member.setHp(0);
                            }
                            break;
                        }
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.throwPotion(monsters.get(0)));
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.throwPotion(monsters.get(1)));
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.throwPotion(monsters.get(2)));
                        } else {
                            throw new InvalidMoveException();
                        }
                        System.out.println(monsters.get(actionNumber).getName() + " the " + monsters.get(actionNumber).getType() + " took 2 damage!");
                        break;
                    } else if(actionNumber == 1){
                        System.out.println("\nWhich character would you like to heal?\n0 | " + party.get(0).getName() + " the " + party.get(0).getSubClass() + ": " + party.get(0).getHp() + " health" + "\n1 | " 
                                                                                            + party.get(1).getName() + " the " + party.get(1).getSubClass() + ": " + party.get(1).getHp() + " health\n2 | "
                                                                                            + party.get(2).getName() + " the " + party.get(2).getSubClass() + ": " + party.get(2).getHp() + "health");
                        actionNumber = kb.nextInt();
                        while (party.get(actionNumber).getHp() <= 0){
                            System.out.println("\u001B[33mYou can't raise the dead. Try someone alive.\u001B[0m");
                            System.out.println("\nWhich character would you like to heal?\n0 | " + party.get(0).getName() + " the " + party.get(0).getSubClass() + ": " + party.get(0).getHp() + " health" + "\n1 | " 
                                                                                            + party.get(1).getName() + " the " + party.get(1).getSubClass() + ": " + party.get(1).getHp() + " health\n2 | "
                                                                                            + party.get(2).getName() + " the " + party.get(2).getSubClass() + ": " + party.get(2).getHp() + " health");
                            
                            try {
                            actionNumber = kb.nextInt();
                        } catch (Exception e) {
                            System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                            String save = party.get(0).getSubClass() + "\n"
                                        + party.get(0).getName() + "\n"
                                        + party.get(1).getSubClass() + "\n"
                                        + party.get(1).getName() + "\n"
                                        + party.get(2).getSubClass() + "\n"
                                        + party.get(2).getName() + "\n"
                                        + dungeonLevel;
                            saveToBinaryFile(save);
                            for(Player member : party){
                                member.setHp(0);
                            }
                            return monsters;
                        }
                        }
                    if(actionNumber == 0){
                        party.get(0).heal(p.healingPotion());
                    } else if(actionNumber == 1){
                        party.get(1).heal(p.healingPotion());
                    } else if(actionNumber == 2){
                        party.get(2).heal(p.healingPotion());
                    } else {
                        throw new InvalidMoveException();
                    } 
                    System.out.println(party.get(actionNumber).getName() + " the " + party.get(actionNumber).getSubClass() + 
                        " is now at " + party.get(actionNumber).getHp() + " health!\n");
                    break;
                } else if(actionNumber == 2){
                        p.Defend();
                        break;
                    } else {
                        throw new InvalidMoveException();
                    }
                }catch(InvalidMoveException ime){
                    System.out.println("\u001B[33mPlease input a valid number.\u001B[0m");
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Throw Potion\n1 | Healing Potion\n2 | Defend");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Throw Potion\n1 | Healing Potion\n2 | Defend");
                }
            }
        } else {
            Cleric p = (Cleric) player;
            System.out.println("0 | Swing Mace\n1 | Healing Spell\n2 | Defend");
            while(true){
                try {
                    actionNumber = kb.nextInt();
                } catch (Exception e) {
                    System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                    String save = party.get(0).getSubClass() + "\n"
                                + party.get(0).getName() + "\n"
                                + party.get(1).getSubClass() + "\n"
                                + party.get(1).getName() + "\n"
                                + party.get(2).getSubClass() + "\n"
                                + party.get(2).getName() + "\n"
                                + dungeonLevel;
                    saveToBinaryFile(save);
                    for(Player member : party){
                        member.setHp(0);
                    }
                    break;
                }
                try{
                    if(actionNumber == 0){
                        System.out.println("\n" + p.getName() + ", which enemy would you like to hit?");
                        int iterations = 0;
                        for(Monster monster : monsters){
                            System.out.println(iterations + " | " + monster.getName() + " the " + monster.getType() + ": " + monster.getHp() + " health");
                            iterations++;
                        }
                        try {
                            actionNumber = kb.nextInt();
                        } catch (Exception e) {
                            System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                            String save = party.get(0).getSubClass() + "\n"
                                        + party.get(0).getName() + "\n"
                                        + party.get(1).getSubClass() + "\n"
                                        + party.get(1).getName() + "\n"
                                        + party.get(2).getSubClass() + "\n"
                                        + party.get(2).getName() + "\n"
                                        + dungeonLevel;
                            saveToBinaryFile(save);
                            for(Player member : party){
                                member.setHp(0);
                            }
                            break;
                        }
                        if(actionNumber == 0){
                            monsters.get(0).takeDmg(p.swingMace());
                        } else if(actionNumber == 1){
                            monsters.get(1).takeDmg(p.swingMace());
                        } else if(actionNumber == 2){
                            monsters.get(2).takeDmg(p.swingMace());
                        } else {
                            throw new InvalidMoveException();
                        }
                        System.out.println("Dealt " + player.getDmg() + " damage!");
                        break;
                    } else if(actionNumber == 1){
                          System.out.println("\nWhich character would you like to heal?\n0 | " + party.get(0).getName() + " the " + party.get(0).getSubClass() + ": " + party.get(0).getHp() + " health\n1 | " 
                                                                                            + party.get(1).getName() + " the " + party.get(1).getSubClass() + ": " + party.get(1).getHp() + " health\n2 | "
                                                                                            + party.get(2).getName() + " the " + party.get(2).getSubClass() + ": " + party.get(2).getHp() + " health");
                        try {
                            actionNumber = kb.nextInt();
                        } catch (Exception e) {
                            System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                            String save = party.get(0).getSubClass() + "\n"
                                        + party.get(0).getName() + "\n"
                                        + party.get(1).getSubClass() + "\n"
                                        + party.get(1).getName() + "\n"
                                        + party.get(2).getSubClass() + "\n"
                                        + party.get(2).getName() + "\n"
                                        + dungeonLevel;
                            saveToBinaryFile(save);
                            for(Player member : party){
                                member.setHp(0);
                            }
                            break;
                        }
                        while (party.get(actionNumber).getHp() <= 0){
                            System.out.println("\u001B[33mYou can't raise the dead. Try someone alive.\u001B[0m");
                            System.out.println("\nWhich character would you like to heal?\n0 | " + party.get(0).getName() + " the " + party.get(0).getSubClass() + ": " + party.get(0).getHp() + " health" + "\n1 | " 
                                                                                            + party.get(1).getName() + " the " + party.get(1).getSubClass() + ": " + party.get(1).getHp() + " health\n2 | "
                                                                                            + party.get(2).getName() + " the " + party.get(2).getSubClass() + ": " + party.get(2).getHp() + " health");
                            
                           try {
                                actionNumber = kb.nextInt();
                            } catch (Exception e) {
                                System.out.println("\u001B[33mMan, inputting a string? That's kinda lame. You die now. But we're nice enough to save your game.\u001B[0m");
                                String save = party.get(0).getSubClass() + "\n"
                                            + party.get(0).getName() + "\n"
                                            + party.get(1).getSubClass() + "\n"
                                            + party.get(1).getName() + "\n"
                                            + party.get(2).getSubClass() + "\n"
                                            + party.get(2).getName() + "\n"
                                            + dungeonLevel;
                                saveToBinaryFile(save);
                                for(Player member : party){
                                    member.setHp(0);
                                }
                                return monsters;
                            }
                        }
                        if(actionNumber == 0){
                            party.get(0).heal(p.healingSpell());
                        } else if(actionNumber == 1){
                            party.get(1).heal(p.healingSpell());
                        } else if(actionNumber == 2){
                            party.get(2).heal(p.healingSpell());
                        } else {
                            throw new InvalidMoveException();
                        }
                        System.out.println(party.get(actionNumber).getName() + " the " + party.get(actionNumber).getSubClass() + 
                            " is now at " + party.get(actionNumber).getHp() + " health!\n");
                        break;
                    } else if(actionNumber == 2){
                        p.Defend();
                        break;
                    } else {
                        throw new InvalidMoveException();
                    }
                }catch(InvalidMoveException ime){
                    System.out.println("\u001B[33mPlease input a valid move.\u001B[0m");
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Swing Mace\n1 | Healing Spell\n2 | Defend");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("\nWhat action would you like to do?");
                    System.out.println("0 | Swing Mace\n1 | Healing Spell\n2 | Defend");
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

    //Miles
    //generates random number then goes that many lines down in the monstername.txt file to pick a random name and apply it to a monster
    public static String getMonsterName(){
        Scanner kb = null;
        try {
            int randNum = (int)(100*Math.random());
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

    //Nathan
    //checks each turn if player/monster isPoisoned = true, if true deals 1 dmg.
        //Need to lower one turn of poison
    public static void poisonCheck(Entity Entity){
        if(Entity.getIsPoisoned() == true){
            Entity.takeDmg(1);
            System.out.println(Entity.getName() + " took poison damage!\n");
            Entity.setPoisonTurnsLeft(Entity.getPoisonTurnsLeft()-1);
            if (Entity.getHp() <= 0){
                System.out.println(Entity.getName() + " died to the poison!\n");
            }
            if (Entity.getPoisonTurnsLeft() <= 0){
                Entity.setIsPoisoned(false);
            }
        }
    } 
    public static void saveToBinaryFile(String text){
        try{
            FileOutputStream fos = new FileOutputStream("save.obj", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(text);
            oos.close();
            System.out.println("\n\u001B[32mBinary File successfully written\u001B[0m\n");
        } catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());        
        }
    }
    public static ArrayList<Player> readSaveFromBinaryFile(){
        ArrayList<Player> party = new ArrayList<Player>();
        try { // outer try/catch to handle IO
            FileInputStream fis = new FileInputStream("save.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            String type = (String)ois.readObject();
            Scanner typeScan = new Scanner(type);
            try { // inner try/catch to handle reading from the file
                for(int i = 0;i<3;i++){
                    String thisSubClass = typeScan.nextLine();
                    String thisName = typeScan.nextLine();
                    if(thisSubClass.equals("Knight")){
                        party.add(new Knight(thisName));
                    } else if(thisSubClass.equals("Rogue")){
                        party.add(new Rogue(thisName));
                    } else if(thisSubClass.equals("Wizard")){
                        party.add(new Wizard(thisName));
                    } else if(thisSubClass.equals("Alchemist")){
                        party.add(new Alchemist(thisName));
                    } else {
                        party.add(new Cleric(thisName));
                    }
                }
                System.out.println("\u001B[32mSave file successfully read.\u001B[0m");
                int floor = typeScan.nextInt();
                party.get(0).setFloor(floor);
            } catch (Exception e){
                System.out.println(e.getMessage());
            } finally {
                ois.close();
                typeScan.close();
            }
        } catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
        return party;
    }
}