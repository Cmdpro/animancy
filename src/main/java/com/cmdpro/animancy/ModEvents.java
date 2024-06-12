package com.cmdpro.animancy;

import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.config.AnimancyConfig;
import com.cmdpro.animancy.entity.SoulKeeper;
import com.cmdpro.animancy.entity.SoulRitualController;
import com.cmdpro.animancy.init.*;
import com.cmdpro.animancy.moddata.PlayerModData;
import com.cmdpro.animancy.moddata.PlayerModDataProvider;
import com.cmdpro.animancy.networking.ModMessages;
import com.cmdpro.animancy.networking.packet.SoulTypeSyncS2CPacket;
import com.cmdpro.animancy.particle.Soul3ParticleOptions;
import com.cmdpro.animancy.soultypes.SoulEntityBind;
import com.cmdpro.animancy.soultypes.SoulEntityBindManager;
import com.cmdpro.animancy.soultypes.SoulTypeManager;
import com.cmdpro.animancy.soultypes.SoulTypeSerializer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Math;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

@Mod.EventBusSubscriber(modid = Animancy.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerModDataProvider.PLAYER_MODDATA).isPresent()) {
                event.addCapability(new ResourceLocation(Animancy.MOD_ID, "properties"), new PlayerModDataProvider());
            }
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
        ModMessages.sendToPlayer(new SoulTypeSyncS2CPacket(SoulTypeManager.types), player);
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {

            });
        }
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

                });
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().is(Blocks.SOUL_FIRE) && !event.getLevel().isClientSide()) {
            IMultiblock ritual = PatchouliAPI.get().getMultiblock(new ResourceLocation(Animancy.MOD_ID, "soulritual"));
            if (
                    ritual.validate(event.getEntity().level(), event.getPos().below(), Rotation.NONE) ||
                    ritual.validate(event.getEntity().level(), event.getPos().below(), Rotation.CLOCKWISE_90) ||
                    ritual.validate(event.getEntity().level(), event.getPos().below(), Rotation.CLOCKWISE_180) ||
                    ritual.validate(event.getEntity().level(), event.getPos().below(), Rotation.COUNTERCLOCKWISE_90)
            ) {
                if (true) {//playerHasNeededEntry((ServerPlayer)event.getEntity(), true, "animancy:arcane/soulritual")) {
                    List<SoulKeeper> entitiesNearby = event.getEntity().level().getEntitiesOfClass(SoulKeeper.class, AABB.ofSize(event.getPos().getCenter(), 50, 50, 50));
                    if (entitiesNearby.isEmpty()) {
                        SoulRitualController ritualController = new SoulRitualController(EntityInit.SOULRITUALCONTROLLER.get(), event.getEntity().level());
                        ritualController.setPos(event.getPos().getCenter());
                        event.getEntity().level().addFreshEntity(ritualController);
                    } else {
                        event.getEntity().sendSystemMessage(Component.translatable("animancy.soulritualfail").withStyle(ChatFormatting.RED));
                    }
                } else {
                    event.getEntity().sendSystemMessage(Component.translatable("animancy.soulritualfail2").withStyle(ChatFormatting.RED));
                }
            }
        }
    }
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.getEntity() instanceof SoulKeeper soulKeeper) {
                for (Player i : soulKeeper.level().players()) {
                    if (soulKeeper.ritualPos.distanceTo(i.position()) <= 10) {
                        ModCriteriaTriggers.KILLSOULKEEPER.trigger((ServerPlayer)i);
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getMainHandItem().is(TagInit.Items.SOULDAGGERS)) {
                ResourceLocation entityId = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType());
                if (entityId != null) {
                    SoulEntityBind bind = SoulEntityBindManager.findBindForMob(entityId);
                    if (bind != null) {
                        for (Map.Entry<ResourceLocation, Float> o : bind.soulTypes.entrySet()) {
                            if (player.getInventory().hasAnyMatching((item) -> item.is(ItemInit.SOULTANK.get()))) {
                                float amount = o.getValue();
                                ResourceLocation type = o.getKey();
                                for (ItemStack i : player.getInventory().items) {
                                    if (i.is(ItemInit.SOULTANK.get())) {
                                        if (SoulTankItem.addFill(i, type, amount)) {
                                            Soul3ParticleOptions particle = new Soul3ParticleOptions(player.getUUID().toString(), type.toString());
                                            ((ServerLevel) event.getEntity().level()).sendParticles(particle, event.getEntity().position().x, event.getEntity().position().y, event.getEntity().position().z, (int) Math.floor(amount), 0.1, 0.1, 0.1, 0);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(SoulTypeManager.getOrCreateInstance());
        event.addListener(SoulEntityBindManager.getOrCreateInstance());
    }
}
