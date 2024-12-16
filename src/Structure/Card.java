package Structure;

public class Card {

    private String description;
    private Runnable action;
    private boolean positive;

    public Card(String description, Runnable action, boolean positive) {
        this.description = description;
        this.action = action;
        this.positive = positive;
    }

    public String description(){
        return description;
    }

    public void doAction(){
        action.run();
        System.out.println("POOOOPY YOU ARE POOOOPY");
    }

    public boolean isPostive(){
        return positive;
    }

    public String toString(){
        return description;
    }
}
