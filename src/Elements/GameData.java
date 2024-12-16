package Elements;
import java.util.LinkedList;

import Structure.*;

public class GameData {
    public static LinkedList<Card> chanceDeck = new LinkedList<Card>();
    public static LinkedList<Card> communityChestDeck = new LinkedList<Card>();

    public static Tile[] GameTiles = {
        new Tile(292, 34, "Go", () -> {DataCache.currentPlayer.collect(200); DataCache.changeTurns();}),
        new Property(364, 34, "Mercury", 60, "Brown", 2),
        new Chest(408, 34),
        new Property(449, 34, "Pluto", 60, "Brown", 4),
        new Fine(491, 34, "Engine Repairs"),
        new Railroad(533, 34, "Astronaut"),
        new Property(575, 34, "Arkas", 100, "Light Blue", 6),
        new Property(617, 34, "Uranus", 100, "Light Blue", 6),
        new Chance(661, 34),
        new Property(702, 34, "Harriot", 120, "Light Blue", 8),
        new Tile(760, 41, "In Jail", () -> {DataCache.changeTurns();}, 0),
        new Tile(802, 17, "Just Visiting", () -> {DataCache.changeTurns();}),
        new Property(804, 76, "Tsavoritel", 140, "Purple", 10),
        new Facility(808, 103, "Oxy-Co"),
        new Property(814,135, "Thestias", 140, "Purple", 10),
        new Property(825, 165, "Arion", 160, "Purple", 12),
        new Railroad(832, 200, "Lunar Module"),
        new Property(844, 242, "Jupiter", 180, "Orange", 14),
        new Chest(866, 282),
        new Property(886, 329, "Dimidium", 180, "Orange", 14),
        new Property(905, 378, "Tapao Thong", 200, "Orange", 16),
        new Tile(930, 475, "Spaceship Docking", () -> {DataCache.changeTurns();}),
        new Property(812, 487, "Smertrios", 220, "Red", 18),
        new Chance(737, 487),
        new Property(670, 487, "Janssen", 220, "Red", 18),
        new Property(603, 487, "Mars", 240, "Red", 20),
        new Railroad(531, 487, "Apollo 11"),
        new Property(460, 487, "Naron", 260, "Yellow", 22),
        new Property(390, 487, "Venus", 260, "Yellow", 22),
        new Facility(320, 487, "Solar Power"),
        new Property(256, 487, "Orbitar", 280, "Yellow", 24),
        new Tile(139, 477, "Go To Jail", () -> {DataCache.currentPlayer.goToJail(); DataCache.changeTurns();}),
        new Property(166, 381, "Tadmor", 300, "Green", 26),
        new Property(182, 332, "Earth", 300, "Green", 26),
        new Chest(196, 287),
        new Property(214, 243, "Kepler-22 B", 330, "Green", 28),
        new Railroad(232, 206, "ISS"),
        new Property(242, 172, "Neptune", 350, "Dark Blue", 35),
        new Chance(255, 135),
        new Property(265, 108, "Brahe", 400, "Dark Blue", 50),
        new Fine(277, 77, "Low Fuel"),
    };

    private static Card[] chance = {
        new Card("Visiting family. Advance to Mars.", () -> {DataCache.currentPlayer.setTile("Mars");}, true),
        new Card("Tax returns! Collect $50.", () -> {DataCache.currentPlayer.collect(50);}, true),
        new Card("The Cosmic Empire raises taxes. Pay $50.", () -> {DataCache.currentPlayer.pay(50);}, false),
        new Card("Boosters activated by mistake. Advance to Go and collect $200!", () -> {DataCache.currentPlayer.setTile("Go"); DataCache.currentPlayer.collect(200);}, true),
        new Card("Need to pick up a shipment. Advance to Tsavoritel.", () -> {DataCache.currentPlayer.setTile("Tsavoritel");}, true),
        new Card("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rent to which they are otherwise entitled.", () -> {advanceToNearestRailroad();}, true),
        new Card("Tax returns! Collect $25.", () -> {DataCache.currentPlayer.collect(25);}, true),
        new Card("Get out of jail free!", () -> {DataCache.currentPlayer.addGetOutOfJailFreeCard();}, true),
        new Card("Out of fuel. Go back three spaces.", () -> {DataCache.currentPlayer.moveBack(3);}, false),
        new Card("Arrested for speeding! Go to Jail. Go directly to Jail. Do not pass Go, do not collect $200.", () -> {DataCache.currentPlayer.goToJail();}, false),
        new Card("General repairs needed on all settlements. For each settlement pay $25. For each base pay $100.", () -> {}, false),
        new Card("You've worked hard -- take a vacation! Advance to Brahe.", () -> {}, true),
        new Card("You've been elected to a board position in the Cosmic Empire. Pay each player $50.", () -> {DataCache.currentPlayer.payEach(50);}, false),
        new Card("Your Ellerycoin gains value. Collect $150.", () -> {}, true)
    };

    private static Card[] chest = {
        new Card("The Cosmic Empire pays you a bonus for your hard work. Collect $200!", () -> {}, true),
        new Card("Refill oxygen tanks. Pay $50.", () -> {}, false),
        new Card("Boosters activated by mistake. Advance to Go and collect $200!", () -> {}, true),
        new Card("Visiting family. Advance to Mars.", () -> {}, true),
        new Card("Need to pick up a shipment. Advance to Tsavoritel.", () -> {}, true),
        new Card("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rent to which they are otherwise entitled.", () -> {}, true),
        new Card("Tax returns! Collect $25.", () -> {}, true),
        new Card("Get out of jail free!", () -> {}, true),
        new Card("Out of fuel. Go back three spaces.", () -> {}, false),
        new Card("Arrested for speeding! Go to Jail. Go directly to Jail. Do not pass Go, do not collect $200.", () -> {}, false),
        new Card("General repairs needed on all settlements. For each settlement pay $25. For each base pay $100.", () -> {}, false),
        new Card("You've worked hard -- take a vacation! Advance to Brahe.", () -> {}, true),
        new Card("You sell settlement lodging to new clients. Collect $50 from each player.", () -> {}, true),
        new Card("Your Danicoin gains value. Collect $150.", () -> {}, true)
    };

    public static void shuffleChanceCards(){
        for(int i = chance.length - 1; i >= 0; i --){
            chanceDeck.add(chance[(int) (Math.random()*(chance.length-1))]);
        }
    }
    
    public static void shuffleCommunityChestCards(){
        for(int i = 0; i < chance.length; i++){
            communityChestDeck.add(chest[(int) (Math.random()*(chest.length-1))]);
        }
    }

    private static void advanceToNearestRailroad(){
        DataCache.currentPlayer.advanceToNearestRailroad();
        Railroad railroad = (Railroad)GameTiles[DataCache.currentPlayer.tile()];
        Player owner = railroad.isOwned();
        if(owner != null){
            DataCache.currentPlayer.payTo(railroad.rent()*2, owner);
        } else {
            railroad.runImplication();
        }
    }
}

