package xd.arkosammy.nodura;

import net.minecraft.util.StringIdentifiable;

public enum NoDuraMode implements StringIdentifiable {
    DO_DURABILITY("do_durability"),
    NO_DURABILITY("no_durability");

    private final String identifier;

    NoDuraMode(String identifier){
        this.identifier = identifier;
    }

    public static NoDuraMode fromString(String identifier){
        for(NoDuraMode mode : NoDuraMode.values()){
            if(mode.identifier.equals(identifier)){
                return mode;
            }
        }
        return DO_DURABILITY;
    }

    @Override
    public String asString() {
        return this.identifier;
    }
}
