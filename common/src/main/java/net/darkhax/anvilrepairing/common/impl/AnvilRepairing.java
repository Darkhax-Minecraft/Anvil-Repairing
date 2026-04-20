package net.darkhax.anvilrepairing.common.impl;

import net.darkhax.bookshelf.common.api.util.MathsHelper;
import net.darkhax.pricklemc.common.api.config.ConfigManager;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnvilRepairing {

    public static final String MOD_ID = "anvilrepairing";
    public static final String MOD_NAME = "AnvilRepairing";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final Identifier ADVANCEMENT_ID = id("story/repair");
    public static final TagKey<Item> REPAIR_ITEMS = TagKey.create(Registries.ITEM, id("anvil_repair_items"));
    public static final Config CONFIG = ConfigManager.load(MOD_ID, new Config());

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static InteractionResult attemptRepair(Player player, Level level, BlockState state, BlockPos pos) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer && !player.isSpectator() && player.getMainHandItem().is(REPAIR_ITEMS) && (state.is(Blocks.CHIPPED_ANVIL) || state.is(Blocks.DAMAGED_ANVIL))) {
            if (!player.getAbilities().instabuild) {
                player.getMainHandItem().shrink(1);
            }
            if (repairAnvil(level, pos, state, state.getValue(AnvilBlock.FACING))) {
                awardAdvancement(serverPlayer, ADVANCEMENT_ID);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static boolean repairAnvil(Level level, BlockPos pos, BlockState state, Direction direction) {
        final Block newBlock = state.is(Blocks.CHIPPED_ANVIL) ? Blocks.ANVIL : state.is(Blocks.DAMAGED_ANVIL) ? Blocks.CHIPPED_ANVIL : null;
        if (newBlock != null) {
            if (MathsHelper.percentChance(CONFIG.repair_chance)) {
                level.setBlock(pos, newBlock.defaultBlockState().setValue(AnvilBlock.FACING, direction), Block.UPDATE_CLIENTS);
                level.levelEvent(LevelEvent.SOUND_ANVIL_USED, pos, 0);
                level.levelEvent(LevelEvent.PARTICLES_TURTLE_EGG_PLACEMENT, pos, 10);
                return true;
            }
            else {
                level.levelEvent(LevelEvent.SOUND_ANVIL_BROKEN, pos, 0);
                level.levelEvent(LevelEvent.PARTICLES_SHOOT_SMOKE, pos, 1);
            }
        }
        return false;
    }

    private static boolean awardAdvancement(ServerPlayer player, Identifier advancementId) {
        if (CONFIG.grant_advancement) {
            final AdvancementHolder toGrant = player.level().getServer().getAdvancements().get(advancementId);
            if (toGrant != null) {
                final AdvancementProgress progress = player.getAdvancements().getOrStartProgress(toGrant);
                if (!progress.isDone()) {
                    for (String criteria : progress.getRemainingCriteria()) {
                        player.getAdvancements().award(toGrant, criteria);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}