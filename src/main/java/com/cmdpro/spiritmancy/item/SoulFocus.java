package com.cmdpro.spiritmancy.item;

import com.cmdpro.spiritmancy.api.ISoulContainer;
import com.cmdpro.spiritmancy.moddata.PlayerModDataProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulFocus extends Item {

    public SoulFocus(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(item, level, components, flag);
        String recipe = "none";
        if (item.hasTag()) {
            if (item.getOrCreateTag().contains("recipe")) {
                recipe = item.getOrCreateTag().getString("recipe").replace(':', '.');
            }
        }
        components.add(Component.translatable("item.spiritmancy.soulfocus." + recipe).withStyle(ChatFormatting.GRAY));
    }
}
