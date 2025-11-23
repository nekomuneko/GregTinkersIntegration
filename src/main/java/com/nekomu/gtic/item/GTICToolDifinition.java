package com.nekomu.gtic.item;

import com.nekomu.gtic.GTICMAIN;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

public class GTICToolDifinition extends ModifiableItem {
    public static ToolDefinition DRILL = ToolDefinition.create(GTICregistry.DRILL);
    public GTICToolDifinition(){
        super(GTICMAIN.defaultitemProperties().stacksTo(1),DRILL);
    }


    public static final ToolDefinition GTIC_DRILL_DEFINITION = ToolDefinition.create(
            new ResourceLocation(GTICMAIN.MOD_ID, "gtic_drill"));

}

