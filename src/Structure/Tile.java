package Structure;

import Elements.DataCache;

public class Tile {
    private int x, y;
    private String name;
    private Runnable implication = () -> {
        System.out.println(name);
    };
    private boolean include = true;

    public Tile(){

    }

    public Tile(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Tile(int x, int y, String name, int i){
        this.x = x;
        this.y = y;
        this.name = name;
        include = false;
    }

    public Tile(int x, int y, String name, Runnable action){
        this.x = x;
        this.y = y;
        this.name = name;
        implication = action;
    }

    public Tile(int x, int y, String name, Runnable action, int i){
        this.x = x;
        this.y = y;
        this.name = name;
        implication = action;
        include = false;
    }

    public boolean exclude(){
        return !include;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public String name(){
        return name;
    }

    public void runImplication(){
        implication.run();
    }

    public void setImplication(Runnable action){
        implication = action;
    }

}
