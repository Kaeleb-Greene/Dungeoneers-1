Game Design
    Single Player
    3v3 turn based combat
        1p 2p 3p vs. 1ai 2ai 3ai
    Action order
        1p, 1ai, 2p, 2ai, 3p, 3ai
Success counter
    Adds 1 to the counter after each ‘floor’
Character creation
    1p, 2p, 3p
    Player: enters name, enters race, picks class
    Classes:
        Wizard
            Attributes
                8 hp
            Action
                Fireball
                    5 damage
                Vegas Fireball
                    1 dmg 50/50 10 dmg
                FIREBALL!
                    50% does nothing
                    20% kill whole enemy team 
                    30% kill whole party
                Defend
                    Halves incoming dmg
        Rogue
            Attributes
                6 hp
                Passive
                    50% chance to reroll if ai is targeting rogue
            Action
                Poison dagger
                    2 damage
                    Applies poison to an enemy
                Multi-attack
                    1-2 damage to each enemy
                Defend
        Alchemist
            Attributes
                10 hp
            Action
                Throw potion
                    Applies random attribute
                        Poison (-1 HP each turn for battle)
                        Harming (-2 HP)
                            --Immobility (miss turn)
                        Weakness(sets enemy dmg to 1)
                Heal potion
                    Heals 3 hp to chosen allay
                Defend
        Knight
            Attributes
                14 hp
            Action
                Swing sword
                3-5 damage
            Taunt
                50% chance to skip targeting and auto-target the knight
            Defend
        Cleric
            Attributes
                12 hp
            Action
                Swing mace
                    2 dmg
                Heal Allay
                    Heals 4 hp to chosen allay
            Defend
Enemy AI
    Randomly picks name from txt file
    Picks enemy type based on floor
        Skeletons
            Appears: Floor 1-3
                4 hp
                2 dmg
        Zombie
            Appears: Floor 2-4
                6 hp
                2 dmg
        Goblin
            Appears: Floor 3-6
                9 hp
                2 dmg
                applies chance to poison
        Raiders
            Appears: Floor 5-10
                10 hp
                3-4 dmg
        Orc
            Appears: 8+
                14 hp
                3-4 dmg
        Troll
            Appears: 11+
                18 hp
                3-4 dmg
        Golem
            Appears: 11+
                40 hp
                2 dmg
        Dragon
            Appears: 11+
                25% chance to spawn Not with MK, or other Dragon
                30 hp
                5 dmg
                applies burn (poison but different)
        MEGA KNIGHT!!!!!
            Appears: when unlucky 💀
                5% chance Not with Dragon, or other MK
                30 hp
                3 dmg to everyone
On turn randomly pick party member to attack
    ifRogue check
    ifTaunt check
    ifImmobile
    ifWeak
    ifDefended
Save function
    On any player turn when in combat or between floors give option to save
    Save:
        Saves current game status to file
        Load from save option when starting game