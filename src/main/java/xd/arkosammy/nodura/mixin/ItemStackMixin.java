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

    @ModifyExpressionValue(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamageable()Z"))
    private boolean onItemStackDamagedByNonEntity(boolean original, @Local(argsOnly = true) @Nullable ServerPlayerEntity player){
        if(player == null){
            return original;
        }
        return original && ((DoDurabilityAccessor)player).nodura$getDurabilityMode() == NoDuraMode.DoDurability;
    }

    @ModifyExpressionValue(method = "damage(ILnet/minecraft/item/ItemConvertible;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamageable()Z"))
    private <T extends LivingEntity> boolean onItemStackDamagedByEntity(boolean original, @Local(argsOnly = true) T entity){
        if(!(entity instanceof ServerPlayerEntity player)){
            return original;
        }
        return original && ((DoDurabilityAccessor)player).nodura$getDurabilityMode() == NoDuraMode.DoDurability;
    }

}
