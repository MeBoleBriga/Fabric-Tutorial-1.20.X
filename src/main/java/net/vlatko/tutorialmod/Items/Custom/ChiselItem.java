package net.vlatko.tutorialmod.Items.Custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {

    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.NETHERRACK, Blocks.NETHER_BRICKS,
                    Blocks.OAK_LOG, Blocks.OAK_WOOD
            );

    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if (!world.isClient()) {
            world.setBlockState(
                    context.getBlockPos(),
                    CHISEL_MAP.get(clickedBlock).getDefaultState()
            );

            if (context.getPlayer() instanceof ServerPlayerEntity player) {
                context.getStack().damage(1, player,
                        p -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }

            world.playSound(
                    null,
                    context.getBlockPos(),
                    SoundEvents.BLOCK_GRINDSTONE_USE,
                    SoundCategory.BLOCKS
            );
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel.shift_down"));
        } else {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
