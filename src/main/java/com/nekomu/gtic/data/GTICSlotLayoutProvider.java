package com.nekomu.gtic.data;

import com.nekomu.gtic.item.GTICregistry;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;

public class GTICSlotLayoutProvider extends AbstractStationSlotLayoutProvider {
    public GTICSlotLayoutProvider (PackOutput packOutput){
        super(packOutput);
    }
    @Override
    public String getName() {
        return "GregTinkersIntegration Station Slot Layout";
    }


    @Override
    protected void addLayouts() {
        defineModifiable(GTICregistry.DRILL)
                .sortIndex(SORT_HARVEST)
                .addInputItem(GTICregistry.DRILL_HEAD,39,35)
                .addInputItem(GTICregistry.EMPTY_UNIT,21,53)
                .addInputItem(GTICregistry.DRILL_BODY,1,1)
                .build();
        defineModifiable(GTICregistry.GTIC_DRILL)
                .sortIndex(SORT_ARMOR)
                .addInputItem(GTICregistry.DRILL_HEAD,39,35)
                .addInputItem(GTICregistry.EMPTY_UNIT,21,53)
                .addInputItem(GTICregistry.DRILL_BODY,1,1)
                .build();
    }
}
