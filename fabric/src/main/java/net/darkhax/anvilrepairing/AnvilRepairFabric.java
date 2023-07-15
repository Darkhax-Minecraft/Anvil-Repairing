package net.darkhax.anvilrepairing;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class AnvilRepairFabric implements ModInitializer {

    @Override
    public void onInitialize(){
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (level.isClientSide || player.isSpectator()) return InteractionResult.PASS;
            if (AnvilRepairCommon.repairAnvil(level, AnvilRepairCommon.ANVIL_REPAIR_ITEMS, player, hitResult.getBlockPos()))
                return InteractionResult.SUCCESS;

            return InteractionResult.PASS;
        });
    }
}
