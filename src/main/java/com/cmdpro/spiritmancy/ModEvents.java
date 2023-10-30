package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.api.*;
import com.cmdpro.spiritmancy.config.SpiritmancyConfig;
import com.cmdpro.spiritmancy.init.AttributeInit;
import com.cmdpro.spiritmancy.init.EntityInit;
import com.cmdpro.spiritmancy.init.ItemInit;
import com.cmdpro.spiritmancy.init.ModCriteriaTriggers;
import com.cmdpro.spiritmancy.moddata.PlayerModData;
import com.cmdpro.spiritmancy.moddata.PlayerModDataProvider;
import com.cmdpro.spiritmancy.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.FrameType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Math;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Spiritmancy.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerModDataProvider.PLAYER_MODDATA).isPresent()) {
                event.addCapability(new ResourceLocation(Spiritmancy.MOD_ID, "properties"), new PlayerModDataProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            player.getCapability(CuriosCapability.INVENTORY).ifPresent((data) -> {
                if (data.findFirstCurio(ItemInit.SOULBARRIER.get()) != null) {
                    player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent((data2) -> {
                        if (data2.getSouls() > 0) {
                            event.setAmount(event.getAmount()*0.9f);
                            data2.setSouls(data2.getSouls()-1);
                        }
                    });
                }
            });
        }
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            for (ServerPlayer player : event.getPlayerList().getPlayers()) {
                syncToPlayer(player);
            }
        } else {
            syncToPlayer(event.getPlayer());
        }
    }
    protected static void syncToPlayer(ServerPlayer player) {

    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
                if (data.getSouls() > PlayerModData.getMaxSouls(event.player)) {
                    data.setSouls(PlayerModData.getMaxSouls(event.player));
                }
                data.updateData(event.player);
            });
        }
    }
    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
        event.getEntity().getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
            if (event.getAdvancement().getDisplay() != null) {
                if (event.getAdvancement().getDisplay().shouldAnnounceChat()) {
                    if ((event.getAdvancement().getId().getNamespace().equals("minecraft") || event.getAdvancement().getId().getNamespace().equals("spiritmancy")) || SpiritmancyConfig.otherModAdvancementsAllowed) {
                        int knowledge = 1;
                        if (event.getAdvancement().getDisplay().getFrame().equals(FrameType.GOAL)) {
                            knowledge = 2;
                        }
                        if (event.getAdvancement().getDisplay().getFrame().equals(FrameType.CHALLENGE)) {
                            knowledge = 3;
                        }
                        data.setKnowledge(data.getKnowledge() + knowledge);
                        event.getEntity().sendSystemMessage(Component.translatable("object.spiritmancy.knowledge", knowledge).withStyle(ChatFormatting.GREEN));
                    }
                }
            }
        });
    }
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        oldPlayer.revive();
        if(event.isWasDeath()) {
            oldPlayer.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerModData.class);
    }
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
                    data.updateData((ServerPlayer)event.getEntity());
                });
            }
        }
    }
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.getSource().getEntity() instanceof Player) {
                Player player = (Player) event.getSource().getEntity();
                if (player.getMainHandItem().is(ItemInit.SOULMETALDAGGER.get())) {
                    player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
                        float amount = Math.floor(event.getEntity().getMaxHealth()/10)+1;
                        data.setSouls(data.getSouls()+amount);
                        if (data.getSouls() >= PlayerModData.getMaxSouls(player)) {
                            data.setSouls(PlayerModData.getMaxSouls(player));
                        }
                    });
                }
            }
        }
    }
}
