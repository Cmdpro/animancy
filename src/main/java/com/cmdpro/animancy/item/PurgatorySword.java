package com.cmdpro.animancy.item;

import com.cmdpro.animancy.Animancy;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.Map;
import java.util.UUID;

public class PurgatorySword extends SwordItem {
    public static final ResourceLocation REACH_ATTRIBUTE = ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "purgatory_sword_reach");
    public static final AttributeModifier REACH_MODIFIER = new AttributeModifier(REACH_ATTRIBUTE, 2d, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    public PurgatorySword(Tier pTier, Properties pProperties) {
        super(pTier, pProperties);
    }
    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry i : super.getDefaultAttributeModifiers(stack).modifiers()) {
            builder.add(i.attribute(), i.modifier(), i.slot());
        }
        builder.add(Attributes.GRAVITY, REACH_MODIFIER, EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }
}