package xd.arkosammy.nodura.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xd.arkosammy.nodura.DoDurabilityAccessor;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements DoDurabilityAccessor {

    @Unique
    private boolean doDurability = true;

    @Override
    public void nodura$setDoDurability(boolean doDurability) {
        this.doDurability = doDurability;
    }

    @Override
    public boolean nodura$shouldDoDurability() {
        return this.doDurability;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void putCustomData(NbtCompound nbt, CallbackInfo ci){
        nbt.putBoolean("doDurability", this.doDurability);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readCustomData(NbtCompound nbt, CallbackInfo ci){
        this.doDurability = nbt.getBoolean("doDurability");
    }

}
