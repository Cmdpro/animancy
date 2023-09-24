package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.item.SpiritTankItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SpiritTankItemRenderer extends GeoItemRenderer<SpiritTankItem> {
    public SpiritTankItemRenderer() {
        super(new SpiritTankItemModel());
    }
}