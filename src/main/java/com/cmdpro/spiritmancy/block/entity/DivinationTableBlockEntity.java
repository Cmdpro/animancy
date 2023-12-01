package com.cmdpro.spiritmancy.block.entity;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.api.ISoulContainer;
import com.cmdpro.spiritmancy.init.BlockEntityInit;
import com.cmdpro.spiritmancy.init.ParticleInit;
import com.cmdpro.spiritmancy.recipe.SoulAltarRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DivinationTableBlockEntity extends BlockEntity implements ISoulContainer, GeoBlockEntity {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private float souls;
    public int craftProgress;

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return slot == 0 ? stack.is(Items.AMETHYST_SHARD) : true;
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 80;
    public DivinationTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.SOULALTAR.get(), pos, state);
        item = ItemStack.EMPTY;
        linked = new ArrayList<>();
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return DivinationTableBlockEntity.this.progress;
                    case 1: return DivinationTableBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: DivinationTableBlockEntity.this.progress = value; break;
                    case 1: DivinationTableBlockEntity.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public float getMaxSouls() {
        return 20;
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private List<BlockPos> linked;
    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt){
        CompoundTag tag = pkt.getTag();
        item = ItemStack.of(tag.getCompound("item"));
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("item", item.save(new CompoundTag()));
        return tag;
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putFloat("souls", souls);
        if (!this.linked.isEmpty()) {
            ListTag listtag = new ListTag();
            for(BlockPos i : this.linked) {
                CompoundTag tag2 = new CompoundTag();
                tag2.putInt("x", i.getX());
                tag2.putInt("y", i.getY());
                tag2.putInt("z", i.getZ());
                listtag.add(tag2);
            }
            tag.put("linked", listtag);
        }
        super.saveAdditional(tag);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        souls = nbt.getFloat("souls");
        linked.clear();
        if (nbt.contains("linked")) {
            ((ListTag) nbt.get("linked")).forEach((i) -> {
                CompoundTag i2 = ((CompoundTag)i);
                BlockPos pos = new BlockPos(i2.getInt("x"), i2.getInt("y"), i2.getInt("z"));
                linked.add(pos);
            });
        }
    }
    @Override
    public float getSouls() {
        return souls;
    }
    @Override
    public void setSouls(float amount) {
        souls = amount;
    }

    @Override
    public List<BlockPos> getLinked() {
        return linked;
    }
    public ItemStack item;
    public SimpleContainer getInv() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, DivinationTableBlockEntity pBlockEntity) {
        if (!pLevel.isClientSide()) {

            pBlockEntity.updateBlock();
            pLevel.sendBlockUpdated(pPos, pState, pState, Block.UPDATE_ALL);
        }
        pBlockEntity.soulContainerTick(pLevel, pPos, pState, pBlockEntity);
    }
    protected void updateBlock() {
        BlockState blockState = level.getBlockState(this.getBlockPos());
        this.level.sendBlockUpdated(this.getBlockPos(), blockState, blockState, 3);
        this.setChanged();
    }
    private <E extends GeoAnimatable> PlayState predicate(AnimationState event) {
        event.getController().setAnimation(RawAnimation.begin().then("animation.divinationtable.idle", Animation.LoopType.LOOP));
        if (!item.isEmpty()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.divinationtable.studying", Animation.LoopType.LOOP));
        }
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
}
