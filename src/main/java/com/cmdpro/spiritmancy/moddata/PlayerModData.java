package com.cmdpro.spiritmancy.moddata;

import com.cmdpro.spiritmancy.init.AttributeInit;
import com.cmdpro.spiritmancy.networking.ModMessages;
import com.cmdpro.spiritmancy.networking.packet.PlayerDataSyncS2CPacket;
import com.klikli_dev.modonomicon.bookstate.BookUnlockStates;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerModData {
    public PlayerModData() {
        souls = 0;
        unlocked = new HashMap<>();
    }
    private HashMap<ResourceLocation, List<ResourceLocation>> unlocked;
    private float souls;
    private int knowledge;
    private BlockPos linkingFrom;
    public HashMap<ResourceLocation, List<ResourceLocation>> getUnlocked() {
        return unlocked;
    }
    public float getSouls() {
        return souls;
    }
    public void setSouls(float amount) {
        this.souls = amount;
    }
    public int getKnowledge() {
        return knowledge;
    }
    public void setKnowledge(int amount) {
        this.knowledge = amount;
    }
    public BlockPos getLinkingFrom() {
        return linkingFrom;
    }
    public void setLinkingFrom(BlockPos pos) {
        this.linkingFrom = pos;
    }

    public void updateData(ServerPlayer player) {
        ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(getSouls(), getKnowledge()), (player));
    }
    public void updateData(Player player) {
        updateData((ServerPlayer)player);
    }
    public static float getMaxSouls(Player player) {
        return (float)player.getAttributeValue(AttributeInit.MAXSOULS.get());
    }
    public void copyFrom(PlayerModData source) {
        this.souls = source.souls;
        this.knowledge = source.knowledge;
        this.unlocked = source.unlocked;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("souls", souls);
        nbt.putInt("knowledge", knowledge);
        ListTag tag = new ListTag();
        for (ResourceLocation i : unlocked.keySet()) {
            CompoundTag tag2 = new CompoundTag();
            tag2.putString("key", i.toString());
            ListTag tag3 = new ListTag();
            for (ResourceLocation o : unlocked.get(i)) {
                CompoundTag tag4 = new CompoundTag();
                tag4.putString("value", o.toString());
                tag3.add(tag4);
            }
            tag2.put("value", tag3);
            tag.add(tag2);
        }
        nbt.put("unlocked", tag);
    }
    public void loadNBTData(CompoundTag nbt) {
        this.souls = nbt.getFloat("souls");
        unlocked.clear();
        this.knowledge = nbt.getInt("knowledge");
        if (nbt.contains("unlocked")) {
            for (Tag i : (ListTag) nbt.get("unlocked")) {
                List<ResourceLocation> list = new ArrayList<>();
                for (Tag o : (ListTag) ((CompoundTag) i).get("value")) {
                    list.add(ResourceLocation.of(((CompoundTag) o).getString("value"), ':'));
                }
                ResourceLocation id = ResourceLocation.of(((CompoundTag) i).getString("key"), ':');
                unlocked.put(id, list);
            }
        }
    }
}
