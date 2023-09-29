package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.item.SoulAltarItem;
import com.cmdpro.spiritmancy.item.SoulPointItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SoulAltarItemRenderer extends GeoItemRenderer<SoulAltarItem> {
    public SoulAltarItemRenderer() {
        super(new SoulAltarItemModel());
    }
}