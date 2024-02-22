package xd.arkosammy.nodura.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xd.arkosammy.nodura.DoDurabilityAccessor;
import xd.arkosammy.nodura.NoDuraMode;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements DoDurabilityAccessor {

    @Unique
    private NoDuraMode noDuraMode = NoDuraMode.DO_DURABILITY;

    @Override
    public void nodura$setDurabilityMode(NoDuraMode noDuraMode) {
        this.noDuraMode = noDuraMode;
    }

    @Override
    public NoDuraMode noDura$getDurabilityMode() {
        return this.noDuraMode;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void putCustomData(NbtCompound nbt, CallbackInfo ci){
        nbt.putString("noDuraMode", this.noDuraMode.asString());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readCustomData(NbtCompound nbt, CallbackInfo ci){
        this.noDuraMode = NoDuraMode.fromString(nbt.getString("noDuraMode"));
    }

}
