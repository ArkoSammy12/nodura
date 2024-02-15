package xd.arkosammy.nodura;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.DedicatedServerModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoDura implements DedicatedServerModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("no-dura");

	@Override
	public void onInitializeServer() {

		ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> ((DoDurabilityAccessor)newPlayer).nodura$setDoDurability(((DoDurabilityAccessor)oldPlayer).nodura$shouldDoDurability())));
		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {

			LiteralCommandNode<ServerCommandSource> parentNode = CommandManager
					.literal("nodura")
					.build();

			LiteralCommandNode<ServerCommandSource> toggleNode = CommandManager
					.literal("doDurability")
					.executes((context -> {
						ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
						player.sendMessage(Text.literal("doDurability is currently " + (((DoDurabilityAccessor)player).nodura$shouldDoDurability() ? "enabled" : "disabled")));
						return Command.SINGLE_SUCCESS;
					}))
					.build();

			ArgumentCommandNode<ServerCommandSource, Boolean> toggleArgumentNode = CommandManager
					.argument("value", BoolArgumentType.bool())
					.executes((context) -> {
						boolean doDurability = BoolArgumentType.getBool(context, "value");
						ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
 						((DoDurabilityAccessor)player).nodura$setDoDurability(doDurability);
						player.sendMessage(Text.literal("doDurability has been " + (((DoDurabilityAccessor)player).nodura$shouldDoDurability() ? "enabled" : "disabled")));
						return Command.SINGLE_SUCCESS;
					})
					.build();

			dispatcher.getRoot().addChild(parentNode);
			parentNode.addChild(toggleNode);
			toggleNode.addChild(toggleArgumentNode);
		}));

	}
}