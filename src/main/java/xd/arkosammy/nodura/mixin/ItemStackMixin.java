package xd.arkosammy.nodura.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xd.arkosammy.nodura.DoDurabilityAccessor;
import xd.arkosammy.nodura.NoDuraMode;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyExpressionValue(method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamageable()Z"))
    private boolean onItemStackDamagedByNonEntity(boolean original, @Local @Nullable ServerPlayerEntity player){
        if(player == null){
            return original;
        }
        return original && ((DoDurabilityAccessor)player).noDura$getDurabilityMode() == NoDuraMode.DoDurability;
    }

    @ModifyExpressionValue(method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamageable()Z"))
    private <T extends LivingEntity> boolean onItemStackDamagedByEntity(boolean original, @Local T entity){
        if(!(entity instanceof ServerPlayerEntity player)){
            return original;
        }
        return original && ((DoDurabilityAccessor)player).noDura$getDurabilityMode() == NoDuraMode.DoDurability;
    }

}
