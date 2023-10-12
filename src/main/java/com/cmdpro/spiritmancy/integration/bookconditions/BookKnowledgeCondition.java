package com.cmdpro.spiritmancy.integration.bookconditions;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.moddata.ClientPlayerData;
import com.cmdpro.spiritmancy.moddata.PlayerModData;
import com.cmdpro.spiritmancy.moddata.PlayerModDataProvider;
import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.context.BookConditionContext;
import com.klikli_dev.modonomicon.book.conditions.context.BookConditionEntryContext;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BookKnowledgeCondition extends BookCondition {

    public ResourceLocation advancementId;
    public int knowledge;
    public BookKnowledgeCondition(Component component, int knowledge, ResourceLocation advancementId) {
        super(component);
        this.advancementId = advancementId;
        this.knowledge = knowledge;
    }

    public static BookKnowledgeCondition fromJson(JsonObject json) {
        ResourceLocation advancementId = new ResourceLocation("", "");
        if (json.has("advancement_id")) {
            advancementId = new ResourceLocation(GsonHelper.getAsString(json, "advancement_id"));
        }
        var knowledge = 1;
        if (json.has("knowledge")) {
            knowledge = json.get("knowledge").getAsInt();
        }


        //default tooltip
        Component tooltip = Component.literal("");
        if (json.has("tooltip")) {
            tooltip = tooltipFromJson(json);
        }

        return new BookKnowledgeCondition(tooltip, knowledge, advancementId);
    }

    @Override
    public List<Component> getTooltip(BookConditionContext context) {
        List<Component> list = new ArrayList<>();
        if (!tooltip.getString().equals("")) {
            list.add(tooltip);
        }
        if (!advancementId.equals(new ResourceLocation("", ""))) {
            list.add(Component.translatable("book.spiritmancy.condition.knowledge1", knowledge, ClientPlayerData.getPlayerKnowledge(), Component.translatable(Util.makeDescriptionId("advancement", advancementId) + ".title")));
        } else {
            list.add(tooltip = Component.translatable("book.spiritmancy.condition.knowledge2", knowledge, ClientPlayerData.getPlayerKnowledge()));
        }
        return list;
    }

    public static BookKnowledgeCondition fromNetwork(FriendlyByteBuf buffer) {
        var tooltip = buffer.readBoolean() ? buffer.readComponent() : null;
        var advancementId = buffer.readResourceLocation();
        var knowledge = buffer.readInt();
        return new BookKnowledgeCondition(tooltip, knowledge, advancementId);
    }

    @Override
    public ResourceLocation getType() {
        return new ResourceLocation(Spiritmancy.MOD_ID, "knowledge");
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.tooltip != null);
        if (this.tooltip != null) {
            buffer.writeComponent(this.tooltip);
        }
        buffer.writeResourceLocation(this.advancementId);
        buffer.writeInt(knowledge);
    }

    @Override
    public boolean test(BookConditionContext context, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (context instanceof BookConditionEntryContext context2) {
                AtomicReference<PlayerModData> data = new AtomicReference<>();
                player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent((data2) -> {
                    data.set(data2);
                });
                if (data.get().getUnlocked().containsKey(context.getBook().getId())) {
                    if (data.get().getUnlocked().get(context.getBook().getId()).contains(context2.getEntry().getId())) {
                        if (!advancementId.equals(new ResourceLocation("", ""))) {
                            var advancement = serverPlayer.getServer().getAdvancements().getAdvancement(this.advancementId);
                            return advancement != null && serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone();
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}