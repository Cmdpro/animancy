package com.cmdpro.animancy;

import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.entity.SoulKeeper;
import com.cmdpro.animancy.entity.SoulRitualController;
import com.cmdpro.animancy.registry.*;
import com.cmdpro.animancy.networking.ModMessages;
import com.cmdpro.animancy.networking.packet.SoulTypeSyncS2CPacket;
import com.cmdpro.animancy.particle.Soul3ParticleOptions;
import com.cmdpro.animancy.soultypes.SoulEntityBind;
import com.cmdpro.animancy.soultypes.SoulEntityBindManager;
import com.cmdpro.animancy.soultypes.SoulTypeManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Math;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = Animancy.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void entitySpawnRestriction(RegisterSpawnPlacementsEvent event) {

        event.register(EntityRegistry.CULTIST_HUSK.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
    @SubscribeEvent
    public static void onAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if (event.getAdvancement().id().equals(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "animancy"))) {
            event.getEntity().sendSystemMessage(Component.translatable("advancements.animancy.animancy.on_grant").withStyle(ChatFormatting.DARK_PURPLE));
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
    public static void onPlayerPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().is(Blocks.SOUL_FIRE) && !event.getLevel().isClientSide()) {
            IMultiblock ritual = PatchouliAPI.get().getMultiblock(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soulritual"));
            if (
                    ritual.validate(event.getEntity().level(), event.getPos().below(), Rotation.NONE) ||
                    ritual.validate(event.getEntity().level(), event.getPos().below(), Rotation.CLOCKWISE_90) ||
                    ritual.validate(event.getEntity().level(), event.getPos().below(), Rotation.CLOCKWISE_180) ||
                    ritual.validate(event.getEntity().level(), event.getPos().below(), Rotation.COUNTERCLOCKWISE_90)
            ) {
                if (false) {//playerHasNeededEntry((ServerPlayer)event.getEntity(), true, "animancy:arcane/soulritual")) {
                    List<SoulKeeper> entitiesNearby = event.getEntity().level().getEntitiesOfClass(SoulKeeper.class, AABB.ofSize(event.getPos().getCenter(), 50, 50, 50));
                    if (entitiesNearby.isEmpty()) {
                        SoulRitualController ritualController = new SoulRitualController(EntityRegistry.SOULRITUALCONTROLLER.get(), event.getEntity().level());
                        ritualController.setPos(event.getPos().getCenter());
                        event.getEntity().level().addFreshEntity(ritualController);
                    } else {
                        event.getEntity().sendSystemMessage(Component.translatable("animancy.soul_ritual_fail").withStyle(ChatFormatting.RED));
                    }
                } else {
                    event.getEntity().sendSystemMessage(Component.translatable("animancy.soul_ritual_fail2").withStyle(ChatFormatting.RED));
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
                        CriteriaTriggerRegistry.KILLSOULKEEPER.get().trigger((ServerPlayer)i);
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getMainHandItem().is(TagRegistry.Items.SOULDAGGERS)) {
                ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
                if (entityId != null) {
                    SoulEntityBind bind = SoulEntityBindManager.findBindForMob(entityId);
                    if (bind != null) {
                        for (Map.Entry<ResourceLocation, Float> o : bind.soulTypes.entrySet()) {
                            if (player.getInventory().hasAnyMatching((item) -> item.is(ItemRegistry.SOULTANK.get()))) {
                                float amount = o.getValue();
                                ResourceLocation type = o.getKey();
                                for (ItemStack i : player.getInventory().items) {
                                    if (i.is(ItemRegistry.SOULTANK.get())) {
                                        if (SoulTankItem.addFill(i, type, amount)) {
                                            Soul3ParticleOptions particle = new Soul3ParticleOptions(player.getUUID(), type);
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
