package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.item.DivinationTableItem;
import com.cmdpro.spiritmancy.item.SoulAltarItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DivinationTableItemRenderer extends GeoItemRenderer<DivinationTableItem> {
    public DivinationTableItemRenderer() {
        super(new DivinationTableItemModel());
    }
}