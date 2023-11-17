package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.config.SpiritmancyConfig;
import com.cmdpro.spiritmancy.init.*;
import com.cmdpro.spiritmancy.integration.BookAltarRecipePage;
import com.cmdpro.spiritmancy.integration.SpiritmancyModonomiconConstants;
import com.cmdpro.spiritmancy.integration.bookconditions.BookAncientKnowledgeCondition;
import com.cmdpro.spiritmancy.integration.bookconditions.BookKnowledgeCondition;
import com.cmdpro.spiritmancy.moddata.ClientPlayerData;
import com.cmdpro.spiritmancy.networking.ModMessages;
import com.cmdpro.spiritmancy.networking.packet.PlayerUnlockEntryC2SPacket;
import com.google.common.collect.ImmutableList;
import com.klikli_dev.modonomicon.book.BookEntry;
import com.klikli_dev.modonomicon.book.BookEntryParent;
import com.klikli_dev.modonomicon.bookstate.BookUnlockStateManager;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import com.klikli_dev.modonomicon.events.ModonomiconEvents;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;


import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("spiritmancy")
@Mod.EventBusSubscriber(modid = Spiritmancy.MOD_ID)
public class Spiritmancy
{
    public static ResourceKey<DamageType> soulExplosion = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Spiritmancy.MOD_ID, "soulexplosion"));

    public static final String MOD_ID = "spiritmancy";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RandomSource random;
    public static ImmutableList<EntityType<? extends LivingEntity>> soulCrystalEntities = ImmutableList.of();
    public Spiritmancy()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        EntityInit.ENTITY_TYPES.register(bus);
        MenuInit.register(bus);
        RecipeInit.register(bus);
        SoundInit.register(bus);
        CreativeModeTabInit.register(bus);
        ParticleInit.register(bus);
        AttributeInit.ATTRIBUTES.register(bus);
        SoulcasterEffectInit.SOULCASTER_EFFECTS.register(bus);
        GeckoLib.initialize();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
        random = RandomSource.create();
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, SpiritmancyConfig.COMMON_SPEC, "spiritmancy.toml");

        LoaderRegistry.registerConditionLoader(new ResourceLocation(MOD_ID, "knowledge"), BookKnowledgeCondition::fromJson, BookKnowledgeCondition::fromNetwork);
        LoaderRegistry.registerConditionLoader(new ResourceLocation(MOD_ID, "ancientknowledge"), BookAncientKnowledgeCondition::fromJson, BookAncientKnowledgeCondition::fromNetwork);
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

        }
        if (event.getTabKey() == CreativeModeTabInit.ITEMS.getKey()) {
            event.accept(ItemInit.SOULMETALDAGGER);
            event.accept(ItemInit.SOULMETAL);
            event.accept(ItemInit.SOULLINKER);
            event.accept(ItemInit.SOULCRYSTAL);
            event.accept(ItemInit.SOULFOCUS);
            event.accept(ItemInit.CRYSTALSOULSMUSICDISC);
            event.accept(ItemInit.ANCIENTPAGE);
            event.accept(ItemInit.THESOULSSCREAMMUSICDISC);
            event.accept(ItemInit.THESOULSREVENGEMUSICDISC);
            event.accept(ItemInit.SOULMETALWAND);
            event.accept(ItemInit.ICECRYSTAL);
            event.accept(ItemInit.FLAMECRYSTAL);
            event.accept(ItemInit.DEATHCRYSTAL);
            event.accept(ItemInit.LIFECRYSTAL);
            event.accept(ItemInit.ENDERCRYSTAL);
            event.accept(ItemInit.SOULBARRIER);
            event.accept(ItemInit.SOULORB);
            event.accept(ItemInit.SOULTRANSFORMER);
            event.accept(ItemInit.SOULBOOSTER);
            event.accept(ItemInit.PURGATORYINGOT);
        }
        if (event.getTabKey() == CreativeModeTabInit.BLOCKS.getKey()) {
            event.accept(ItemInit.SPIRITTANK_ITEM);
            event.accept(ItemInit.SOULPOINT_ITEM);
            event.accept(ItemInit.SOULALTAR_ITEM);
            event.accept(BlockInit.SOULSHAPER);
            event.accept(BlockInit.SOULCASTERSTABLE);
            event.accept(BlockInit.ECHOSOIL);
        }
        setupSoulCrystalEntities();
        if (event.getTabKey() == CreativeModeTabInit.FULLCRYSTALS.getKey()) {
            for (EntityType<? extends LivingEntity> i : Spiritmancy.soulCrystalEntities) {
                ItemStack stack = new ItemStack(ItemInit.FULLSOULCRYSTAL.get());
                CompoundTag tag = stack.getOrCreateTag();
                tag.put("entitydata", new CompoundTag());
                tag.putString("entitytype", ForgeRegistries.ENTITY_TYPES.getKey(i).toString());
                event.accept(stack);
            }
        }
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        ModMessages.register();
        event.enqueueWork(ModCriteriaTriggers::register);
        LoaderRegistry.registerPredicate(new ResourceLocation("spiritmancy:airorfire"), (getter, pos, state) -> state.isAir() || state.is(Blocks.SOUL_FIRE) || state.is(Blocks.FIRE));
        LoaderRegistry.registerPageLoader(SpiritmancyModonomiconConstants.Page.ALTAR_RECIPE, BookAltarRecipePage::fromJson, BookAltarRecipePage::fromNetwork);
    }
    private void complete(final FMLLoadCompleteEvent event)
    {
        setupSoulCrystalEntities();
    }
    public void setupSoulCrystalEntities() {
        if (soulCrystalEntities.isEmpty()) {
            soulCrystalEntities = ImmutableList.copyOf(
                    ForgeRegistries.ENTITY_TYPES.getValues().stream()
                            .filter(DefaultAttributes::hasSupplier)
                            .map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                            .filter((i) -> !i.getTags().toList().contains(Tags.EntityTypes.BOSSES))
                            .filter((i) -> !i.getDescriptionId().equals("entity.minecraft.player"))
                            .filter((i) -> !i.getDescriptionId().equals("entity.minecraft.warden"))
                            .collect(Collectors.toList()));
        }
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("spiritmancy", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
    }


}
