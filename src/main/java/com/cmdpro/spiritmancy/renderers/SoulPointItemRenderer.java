package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.item.SoulPointItem;
import com.cmdpro.spiritmancy.item.SpiritTankItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SoulPointItemRenderer extends GeoItemRenderer<SoulPointItem> {
    public SoulPointItemRenderer() {
        super(new SoulPointItemModel());
    }
}