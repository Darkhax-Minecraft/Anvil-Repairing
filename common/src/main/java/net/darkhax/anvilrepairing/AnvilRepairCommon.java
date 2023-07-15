package net.darkhax.anvilrepairing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;

public class AnvilRepairCommon {

    public static final String MODID = "anvilrepairing";
    public static final TagKey<Item> ANVIL_REPAIR_ITEMS = TagKey.create(
            Registry.ITEM_REGISTRY, new ResourceLocation(MODID, "anvil_repair_items"));


    public static boolean repairAnvil(Level level, TagKey<Item> ironTag, Player player, BlockPos pos) {
        final BlockState blockState = level.getBlockState(pos);
        if (isVanillaAnvil(blockState) && player.getMainHandItem().is(ironTag)) {
            if (!player.getAbilities().instabuild) player.getMainHandItem().shrink(1);
            upgradeAnvil(level, pos, blockState.getBlock(), blockState.getValue(AnvilBlock.FACING));
            return true;
        }
        return false;
    }

    public static boolean isVanillaAnvil(BlockState state) {
        return state.is(Blocks.CHIPPED_ANVIL) || state.is(Blocks.DAMAGED_ANVIL);
    }

    public static void upgradeAnvil(Level level, BlockPos pos, Block block, Direction direction) {
        if (block == Blocks.CHIPPED_ANVIL) {
            level.setBlock(pos, Blocks.ANVIL.defaultBlockState().setValue(AnvilBlock.FACING, direction), Block.UPDATE_CLIENTS);
            level.levelEvent(LevelEvent.SOUND_ANVIL_USED, pos, 0);
        }
        else {
            //block == DamagedAnvilHere
            level.setBlock(pos, Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(AnvilBlock.FACING, direction), Block.UPDATE_CLIENTS);
            level.levelEvent(LevelEvent.SOUND_ANVIL_USED, pos, 0);
        }
    }
}
