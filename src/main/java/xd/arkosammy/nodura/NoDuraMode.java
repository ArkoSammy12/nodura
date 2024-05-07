package xd.arkosammy.nodura;

import net.minecraft.util.StringIdentifiable;

import java.util.Optional;

public enum NoDuraMode implements StringIdentifiable {
    // In PascalCase so they show up pretty in the gamerules
    DoDurability("do_durability"),
    NoDurability("no_durability");

    private final String identifier;

    NoDuraMode(String identifier){
        this.identifier = identifier;
    }

    public static Optional<NoDuraMode> fromString(String identifier){
        for(NoDuraMode mode : NoDuraMode.values()){
            if(mode.identifier.equals(identifier)){
                return Optional.of(mode);
            }
        }
        return Optional.empty();
    }

    @Override
    public String asString() {
        return this.identifier;
    }
}
