package com.cmdpro.spiritmancy.block.entity;

import com.cmdpro.spiritmancy.api.SoulTankItem;
import com.cmdpro.spiritmancy.api.SpiritmancyUtil;
import com.cmdpro.spiritmancy.init.BlockEntityInit;
import com.cmdpro.spiritmancy.init.RecipeInit;
import com.cmdpro.spiritmancy.recipe.ISoulAltarRecipe;
import com.cmdpro.spiritmancy.recipe.NonMenuCraftingContainer;
import com.cmdpro.spiritmancy.screen.SoulAltarMenu;
import com.cmdpro.spiritmancy.soultypes.SoulType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.phys.Vec3;
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

    public SoulAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.SOULALTAR.get(), pos, state);
        item = ItemStack.EMPTY;
        souls = new HashMap<>();
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
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        ListTag tag2 = new ListTag();
        for (Map.Entry<ResourceLocation, Float> i : getSouls().entrySet()) {
            CompoundTag tag3 = new CompoundTag();
            tag3.putString("key", i.getKey().toString());
            tag3.putFloat("value", i.getValue());
            tag2.add(tag3);
        }
        tag.put("souls", tag2);
        ListTag tag4 = new ListTag();
        for (Map.Entry<ResourceLocation, Float> i : soulCost.entrySet()) {
            CompoundTag tag3 = new CompoundTag();
            tag3.putString("key", i.getKey().toString());
            tag3.putFloat("value", i.getValue());
            tag4.add(tag3);
        }
        tag.put("soulCost", tag4);
        tag.put("item", item.save(new CompoundTag()));
        return tag;
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        ListTag tag2 = new ListTag();
        for (Map.Entry<ResourceLocation, Float> i : getSouls().entrySet()) {
            CompoundTag tag3 = new CompoundTag();
            tag3.putString("key", i.getKey().toString());
            tag3.putFloat("value", i.getValue());
            tag2.add(tag3);
        }
        tag.put("souls", tag2);
        super.saveAdditional(tag);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        getSouls().clear();
        for (Tag i : (ListTag)nbt.get("souls")) {
            getSouls().put(ResourceLocation.tryParse(((CompoundTag)i).getString("key")), ((CompoundTag)i).getFloat("value"));
        }
    }
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
    public static InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                        Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof SoulAltarBlockEntity ent) {
            if (ent.recipe != null) {
                if (ent.enoughSouls) {
                    if (SpiritmancyUtil.playerHasAdvancement(pPlayer, ent.recipe.getAdvancement())) {
                        for (int i = 0; i < 9; i++) {
                            ent.itemHandler.getStackInSlot(i).shrink(1);
                        }
                        ItemStack stack = ent.recipe.assemble(ent.getCraftingInv(), pLevel.registryAccess());
                        ItemEntity entity = new ItemEntity(pLevel, (float) pPos.getX() + 0.5f, (float) pPos.getY() + 1f, (float) pPos.getZ() + 0.5f, stack);
                        pLevel.addFreshEntity(entity);
                        pLevel.playSound(null, pPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 2, 1);
                        BlockPos[] pillars = {
                                ent.getBlockPos().offset(2, 0, 0),
                                ent.getBlockPos().offset(-2, 0, 0),
                                ent.getBlockPos().offset(0, 0, 2),
                                ent.getBlockPos().offset(0, 0, -2)
                        };
                        Map<ResourceLocation, Float> remaining = new HashMap<>();
                        for (Map.Entry<ResourceLocation, Float> i : ent.recipe.getSoulCost().entrySet()) {
                            remaining.put(i.getKey(), i.getValue());
                        }
                        for (BlockPos o : pillars) {
                            if (pLevel.getBlockEntity(o) instanceof GoldPillarBlockEntity ent2) {
                                if (ent2.item != null && !ent2.item.isEmpty() && ent2.item.getItem() instanceof SoulTankItem) {
                                    ResourceLocation type = SoulTankItem.getFillTypeLocation(ent2.item);
                                    if (remaining.containsKey(type)) {
                                        remaining.put(type, SoulTankItem.removeFill(ent2.item, type, remaining.get(type)));
                                        if (remaining.get(type) <= 0) {
                                            remaining.remove(type);
                                        }
                                        ent2.updateBlock();
                                    }
                                }
                            }
                        }
                    } else {
                        pPlayer.sendSystemMessage(Component.translatable("block.spiritmancy.soulaltar.dontknowhow"));
                    }
                } else {
                    pPlayer.sendSystemMessage(Component.translatable("block.spiritmancy.soulaltar.notenoughsouls"));
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
    public void detectSouls() {
        souls.clear();
        BlockPos[] pillars = {
                getBlockPos().offset(2, 0, 0),
                getBlockPos().offset(-2, 0, 0),
                getBlockPos().offset(0, 0, 2),
                getBlockPos().offset(0, 0, -2)
        };
        for (BlockPos i : pillars) {
            if (level.getBlockEntity(i) instanceof GoldPillarBlockEntity ent) {
                if (ent.item != null && !ent.item.isEmpty()) {
                    souls.put(SoulTankItem.getFillTypeLocation(ent.item), souls.getOrDefault(SoulTankItem.getFillTypeLocation(ent.item), 0f)+SoulTankItem.getFillNumber(ent.item));
                }
            }
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
            pBlockEntity.detectSouls();
            Optional<ISoulAltarRecipe> recipe = pLevel.getRecipeManager().getRecipeFor(RecipeInit.SOULALTAR.get(), pBlockEntity.getCraftingInv(), pLevel);
            if (recipe.isPresent()) {
                pBlockEntity.recipe = recipe.get();
                pBlockEntity.soulCost = recipe.get().getSoulCost();
                pBlockEntity.item = recipe.get().getResultItem(pLevel.registryAccess());
                boolean enoughEnergy = true;
                for (Map.Entry<ResourceLocation, Float> i : recipe.get().getSoulCost().entrySet()) {
                    if (pBlockEntity.getSouls().containsKey(i.getKey())) {
                        if (pBlockEntity.getSouls().get(i.getKey()) < i.getValue()) {
                            enoughEnergy = false;
                            break;
                        }
                    } else {
                        enoughEnergy = false;
                        break;
                    }
                }
                pBlockEntity.enoughSouls = enoughEnergy;
            } else {
                pBlockEntity.recipe = null;
                if (!pBlockEntity.soulCost.isEmpty()) {
                    pBlockEntity.soulCost = new HashMap<>();
                }
                pBlockEntity.item = ItemStack.EMPTY;
            }
            pBlockEntity.updateBlock();
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
        return Component.translatable("block.spiritmancy.soulaltar");
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
