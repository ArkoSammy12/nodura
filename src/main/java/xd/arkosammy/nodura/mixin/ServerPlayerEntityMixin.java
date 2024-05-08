package xd.arkosammy.nodura.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xd.arkosammy.nodura.DoDurabilityAccessor;
import xd.arkosammy.nodura.NoDura;
import xd.arkosammy.nodura.NoDuraMode;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements DoDurabilityAccessor {

    @Shadow public abstract ServerWorld getServerWorld();

    @Unique
    private NoDuraMode noDuraMode = NoDuraMode.DoDurability;

    @Override
    public void nodura$setDurabilityMode(NoDuraMode noDuraMode) {
        this.noDuraMode = noDuraMode;
    }

    @Override
    public NoDuraMode nodura$getDurabilityMode() {
        return this.noDuraMode;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onServerPlayerEntity(CallbackInfo ci) {
        this.noDuraMode = this.getServerWorld().getGameRules().get(NoDura.DEFAULT_NO_DURA_MODE).get();
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void putCustomData(NbtCompound nbt, CallbackInfo ci){
        nbt.putString("noDuraMode", this.noDuraMode.asString());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readCustomData(NbtCompound nbt, CallbackInfo ci){
        this.noDuraMode = NoDuraMode.fromString(nbt.getString("noDuraMode")).orElse(this.getServerWorld().getGameRules().get(NoDura.DEFAULT_NO_DURA_MODE).get());
    }

}
