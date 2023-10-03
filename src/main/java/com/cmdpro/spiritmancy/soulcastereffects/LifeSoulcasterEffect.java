package com.cmdpro.spiritmancy.soulcastereffects;

import com.cmdpro.spiritmancy.api.SoulcasterEffect;
import net.minecraft.world.entity.LivingEntity;

import java.awt.*;

public class LifeSoulcasterEffect extends SoulcasterEffect {
    public LifeSoulcasterEffect() {
        soulCost = 3;
        color = new Color(0f, 1f, 0f);
    }

    @Override
    public void hitEntity(LivingEntity caster, LivingEntity victim, int amount) {
        super.hitEntity(caster, victim, amount);
        victim.heal(2*amount);
    }
}
