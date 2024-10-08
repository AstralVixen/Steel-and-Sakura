package net.astralvixen.steelandsakura.procedures;

import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.astralvixen.steelandsakura.world.inventory.YugenToriiGUIMenu;
import net.astralvixen.steelandsakura.network.SteelandsakuraModVariables;
import net.astralvixen.steelandsakura.SteelandsakuraMod;

import io.netty.buffer.Unpooled;

public class OpenToriiGUIOnKeyPressedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity.getCapability(SteelandsakuraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SteelandsakuraModVariables.PlayerVariables())).nearTorii == true) {
			if (entity instanceof ServerPlayer _ent) {
				BlockPos _bpos = BlockPos.containing(x, y, z);
				NetworkHooks.openScreen((ServerPlayer) _ent, new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("YugenToriiGUI");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new YugenToriiGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
					}
				}, _bpos);
			}
		} else {
			SteelandsakuraMod.LOGGER.info("Not near");
		}
	}
}
