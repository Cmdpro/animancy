package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorMaterialRegistry {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL,
            Animancy.MOD_ID);
    public static final Holder<ArmorMaterial> STRIDER = ARMOR_MATERIALS.register("strider", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(Tags.Items.LEATHERS), List.of(
                    new ArmorMaterial.Layer(
                            ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "strider")
                    )
            ),
            0,
            0
    ));

    private static <T extends ArmorMaterial> DeferredHolder<ArmorMaterial, T> register(final String name, final Supplier<T> material) {
        return ARMOR_MATERIALS.register(name, material);
    }
}
