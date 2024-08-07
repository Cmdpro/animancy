package com.cmdpro.animancy.block.entity;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.api.AnimancyUtil;
import com.cmdpro.animancy.networking.ModMessages;
import com.cmdpro.animancy.registry.BlockEntityRegistry;
import com.cmdpro.animancy.registry.RecipeRegistry;
import com.cmdpro.animancy.particle.Soul4ParticleOptions;
import com.cmdpro.animancy.recipe.ISoulAltarRecipe;
import com.cmdpro.animancy.recipe.NonMenuCraftingContainer;
import com.cmdpro.animancy.screen.SoulAltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import java.util.*;

public class SoulAltarBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private final ItemStackHandler itemHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return super.isItemValid(slot, stack);
        }
    };
    public void drops() {
        SimpleContainer inventory = getInv();

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public List<ResourceLocation> upgrades;
    public SoulAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SOULALTAR.get(), pos, state);
        item = ItemStack.EMPTY;
        souls = new HashMap<>();
        craftingTicks = -1;
        upgrades = new ArrayList<>();
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt){
        CompoundTag tag = pkt.getTag();
        getSouls().clear();
        for (Tag i : (ListTag)tag.get("souls")) {
            getSouls().put(ResourceLocation.tryParse(((CompoundTag)i).getString("key")), ((CompoundTag)i).getFloat("value"));
        }
        soulCost.clear();
        for (Tag i : (ListTag)tag.get("soulCost")) {
            soulCost.put(ResourceLocation.tryParse(((CompoundTag)i).getString("key")), ((CompoundTag)i).getFloat("value"));
        }
        item = ItemStack.of(tag.getCompound("item"));
        craftingTicks = tag.getInt("craftingTicks");
        maxCraftingTicks = tag.getInt("maxCraftingTicks");
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        ListTag tag2 = new ListTag();
        for (Map.Entry<ResourceLocation, Float> i : getSouls().entrySet()) {
            if (i.getKey() != null) {
                CompoundTag tag3 = new CompoundTag();
                tag3.putString("key", i.getKey().toString());
                tag3.putFloat("value", i.getValue());
                tag2.add(tag3);
            }
        }
        tag.put("souls", tag2);
        ListTag tag4 = new ListTag();
        for (Map.Entry<ResourceLocation, Float> i : soulCost.entrySet()) {
            if (i.getKey() != null) {
                CompoundTag tag3 = new CompoundTag();
                tag3.putString("key", i.getKey().toString());
                tag3.putFloat("value", i.getValue());
                tag4.add(tag3);
            }
        }
        tag.put("soulCost", tag4);
        tag.put("item", item.save(new CompoundTag()));
        tag.putInt("craftingTicks", craftingTicks);
        tag.putInt("maxCraftingTicks", maxCraftingTicks);
        return tag;
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }
    public int craftingTicks;
    public int maxCraftingTicks;
    public ItemStack item;
    public SimpleContainer getInv() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }
    public CraftingContainer getCraftingInv() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            items.add(itemHandler.getStackInSlot(i));
        }
        CraftingContainer inventory = new NonMenuCraftingContainer(items, 3, 3);
        return inventory;
    }
    public boolean hasUpgrades(List<ResourceLocation> upgrades) {
        return this.upgrades.containsAll(upgrades);
    }
    public static InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                        Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof SoulAltarBlockEntity ent) {
            if (ent.recipe != null) {
                if (ent.enoughSouls) {
                    if (AnimancyUtil.playerHasAdvancement(pPlayer, ent.recipe.getAdvancement())) {
                        if (ent.hasUpgrades(ent.recipe.getUpgrades())) {
                            if (ent.craftingTicks <= -1) {
                                ent.craftingTicks = 0;
                            }
                        } else {
                            pPlayer.sendSystemMessage(Component.translatable("block.animancy.soul_altar.low_tier"));
                        }
                    } else {
                        pPlayer.sendSystemMessage(Component.translatable("block.animancy.soul_altar.dont_know_how"));
                    }
                } else {
                    pPlayer.sendSystemMessage(Component.translatable("block.animancy.soul_altar.not_enough_souls"));
                }
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }
    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }
    public void detectMultiblock() {
        souls.clear();
        upgrades.clear();
        BlockPos[] pillars = {
                getBlockPos().offset(2, 0, 0),
                getBlockPos().offset(-2, 0, 0),
                getBlockPos().offset(0, 0, 2),
                getBlockPos().offset(0, 0, -2)
        };
        boolean allPillars = true;
        for (BlockPos i : pillars) {
            if (level.getBlockEntity(i) instanceof GoldPillarBlockEntity ent) {
                if (!ent.itemHandler.getStackInSlot(0).isEmpty()) {
                    souls.put(SoulTankItem.getFillTypeLocation(ent.itemHandler.getStackInSlot(0)), souls.getOrDefault(SoulTankItem.getFillTypeLocation(ent.itemHandler.getStackInSlot(0)), 0f)+SoulTankItem.getFillNumber(ent.itemHandler.getStackInSlot(0)));
                }
            } else {
                allPillars = false;
            }
        }
        if (allPillars) {
            upgrades.add(new ResourceLocation(Animancy.MOD_ID, "basic"));
        }
    }
    public float getTotalSouls() {
        float total = 0;
        for (Map.Entry<ResourceLocation, Float> i : souls.entrySet()) {
            total += i.getValue();
        }
        return total;
    }
    public ISoulAltarRecipe recipe;
    public boolean enoughSouls;
    public Map<ResourceLocation, Float> soulCost = new HashMap<>();
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SoulAltarBlockEntity pBlockEntity) {
        if (!pLevel.isClientSide()) {
            pBlockEntity.detectMultiblock();
            Optional<ISoulAltarRecipe> recipe = pLevel.getRecipeManager().getRecipeFor(RecipeRegistry.SOULALTAR.get(), pBlockEntity.getCraftingInv(), pLevel);
            if (recipe.isPresent()) {
                pBlockEntity.recipe = recipe.get();
                pBlockEntity.maxCraftingTicks = recipe.get().getMaxCraftingTime();
                pBlockEntity.soulCost = recipe.get().getSoulCost();
                pBlockEntity.item = recipe.get().getResultItem(pLevel.registryAccess());
                boolean enoughSouls = true;
                for (Map.Entry<ResourceLocation, Float> i : recipe.get().getSoulCost().entrySet()) {
                    if (pBlockEntity.getSouls().containsKey(i.getKey())) {
                        if (pBlockEntity.getSouls().get(i.getKey()) < i.getValue()) {
                            enoughSouls = false;
                            break;
                        }
                    } else {
                        enoughSouls = false;
                        break;
                    }
                }
                pBlockEntity.enoughSouls = enoughSouls;
                if (pBlockEntity.craftingTicks >= 0) {
                    pBlockEntity.craftingTicks++;
                    pBlockEntity.craftingEffects();
                    if (pBlockEntity.craftingTicks >= pBlockEntity.maxCraftingTicks) {
                        pBlockEntity.actuallyCraft();
                    }
                    if (!enoughSouls) {
                        pBlockEntity.craftingTicks = -1;
                    }
                    if (!pBlockEntity.hasUpgrades(pBlockEntity.recipe.getUpgrades())) {
                        pBlockEntity.craftingTicks = -1;
                    }
                }
            } else {
                pBlockEntity.recipe = null;
                if (!pBlockEntity.soulCost.isEmpty()) {
                    pBlockEntity.soulCost = new HashMap<>();
                }
                pBlockEntity.item = ItemStack.EMPTY;
                if (pBlockEntity.craftingTicks >= 0) {
                    pBlockEntity.craftingTicks = -1;
                }
            }
            pBlockEntity.updateBlock();
        }
    }
    public void craftingEffects() {
        Map<ResourceLocation, Float> remaining = new HashMap<>();
        for (Map.Entry<ResourceLocation, Float> i : recipe.getSoulCost().entrySet()) {
            remaining.put(i.getKey(), i.getValue());
        }
        for (int i = 0; i < 4; i++) {
            double x = getBlockPos().getCenter().x + (Math.cos(Math.toRadians((craftingTicks*5)+(i*90)))*((1f-((float)craftingTicks/maxCraftingTicks))*2));
            double y = getBlockPos().getCenter().y + 1;
            double z = getBlockPos().getCenter().z + (Math.sin(Math.toRadians((craftingTicks*5)+(i*90)))*((1f-((float)craftingTicks/maxCraftingTicks))*2));
            BlockPos[] pillars = {
                    getBlockPos().offset(2, 0, 0),
                    getBlockPos().offset(0, 0, 2),
                    getBlockPos().offset(-2, 0, 0),
                    getBlockPos().offset(0, 0, -2)
            };
            if (level.getBlockEntity(pillars[i]) instanceof GoldPillarBlockEntity ent) {
                if (!ent.itemHandler.getStackInSlot(0).isEmpty() && ent.itemHandler.getStackInSlot(0).getItem() instanceof SoulTankItem) {
                    ResourceLocation type = SoulTankItem.getFillTypeLocation(ent.itemHandler.getStackInSlot(0));
                    if (remaining.containsKey(type)) {
                        ItemStack stack = ent.itemHandler.getStackInSlot(0).copy();
                        remaining.put(type, SoulTankItem.removeFill(stack, type, remaining.get(type), false));
                        ent.itemHandler.setStackInSlot(0, stack);
                        if (remaining.get(type) <= 0) {
                            remaining.remove(type);
                        }
                        Soul4ParticleOptions options = new Soul4ParticleOptions(type.toString());
                        ((ServerLevel)level).sendParticles(options, x, y, z, 3, 0.05, 0.05, 0.05, 0);
                        level.playSound(null, x, y, z, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 1f, 0.5f+(((float)craftingTicks/maxCraftingTicks)*1.5f));
                    }
                }
            }
        }
    }
    public void actuallyCraft() {
        craftingTicks = -2;
        for (int i = 0; i < 9; i++) {
            itemHandler.getStackInSlot(i).shrink(1);
        }
        ItemStack stack = recipe.assemble(getCraftingInv(), level.registryAccess());
        ItemEntity entity = new ItemEntity(level, (float) getBlockPos().getX() + 0.5f, (float) getBlockPos().getY() + 1f, (float) getBlockPos().getZ() + 0.5f, stack);
        level.addFreshEntity(entity);
        level.playSound(null, getBlockPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 2, 1);
        BlockPos[] pillars = {
                getBlockPos().offset(2, 0, 0),
                getBlockPos().offset(-2, 0, 0),
                getBlockPos().offset(0, 0, 2),
                getBlockPos().offset(0, 0, -2)
        };
        Map<ResourceLocation, Float> remaining = new HashMap<>();
        for (Map.Entry<ResourceLocation, Float> i : recipe.getSoulCost().entrySet()) {
            remaining.put(i.getKey(), i.getValue());
        }
        for (BlockPos o : pillars) {
            if (level.getBlockEntity(o) instanceof GoldPillarBlockEntity ent2) {
                if (!ent2.itemHandler.getStackInSlot(0).isEmpty() && ent2.itemHandler.getStackInSlot(0).getItem() instanceof SoulTankItem) {
                    ResourceLocation type = SoulTankItem.getFillTypeLocation(ent2.itemHandler.getStackInSlot(0));
                    if (remaining.containsKey(type)) {
                        remaining.put(type, SoulTankItem.removeFill(ent2.itemHandler.getStackInSlot(0), type, remaining.get(type)));
                        if (remaining.get(type) <= 0) {
                            remaining.remove(type);
                        }
                        ent2.updateBlock();
                    }
                }
            }
        }
    }
    protected void updateBlock() {
        BlockState blockState = level.getBlockState(this.getBlockPos());
        this.level.sendBlockUpdated(this.getBlockPos(), blockState, blockState, 3);
        this.setChanged();
    }
    private <E extends GeoAnimatable> PlayState predicate(AnimationState event) {
        event.getController().setAnimation(RawAnimation.begin().then("animation.soulaltar.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller", 0, this::predicate));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.animancy.soul_altar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new SoulAltarMenu(pContainerId, pInventory, this);
    }
    Map<ResourceLocation, Float> souls = new HashMap<>();
    public Map<ResourceLocation, Float> getSouls() {
        return souls;
    }
}
