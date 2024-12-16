package Structure;

import Elements.DataCache;

public class Facility extends Property{
    public Facility(int x, int y, String name){
        super(x, y, name, 260, "Facility", 0);
        this.setImplication(() -> {
            DataCache.changeTurns();
        });
    }

    public int rent(){
        return DataCache.roll*4;
    }
}
