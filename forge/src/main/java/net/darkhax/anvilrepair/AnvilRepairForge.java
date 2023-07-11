package net.darkhax.anvilrepair;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("anvilrepair")
public class AnvilRepairForge {

    public AnvilRepairForge() {
        MinecraftForge.EVENT_BUS.addListener(this::handleInteractEvent);
    }

    private void handleInteractEvent(final PlayerInteractEvent.RightClickBlock event) {
        final Player player = event.getPlayer();
        if (event.getWorld().isClientSide || player.isSpectator()) return;
        if (AnvilRepairCommon.repairAnvil(event.getWorld(), AnvilRepairCommon.ANVIL_REPAIR_ITEMS, player, event.getHitVec().getBlockPos())) {
            event.setCanceled(true);
        }
    }
}
