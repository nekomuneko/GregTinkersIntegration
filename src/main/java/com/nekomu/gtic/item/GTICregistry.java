package com.nekomu.gtic.item;

import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.nekomu.gtic.GTICMAIN;
import com.nekomu.gtic.data.GTICToolType;
import net.minecraft.world.item.CreativeModeTab;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import static com.nekomu.gtic.item.GTICToolDifinition.GTIC_DRILL_DEFINITION;

public class GTICregistry extends TinkerModule {
    public static final ItemDeferredRegisterExtension ITEMS = new ItemDeferredRegisterExtension(GTICMAIN.MOD_ID);

    public static final ItemObject<ToolPartItem> DRILL_HEAD = ITEMS.register(
            "drill_head",()-> new ToolPartItem(ITEM_PROPS, HeadMaterialStats.ID));

    public static final ItemObject<ToolPartItem> EMPTY_UNIT = ITEMS.register(
            "empty_unit",()-> new ToolPartItem(ITEM_PROPS, HandleMaterialStats.ID));

    public static final ItemObject<ToolPartItem> DRILL_BODY = ITEMS.register(
            "drill_body",()-> new ToolPartItem(ITEM_PROPS, HeadMaterialStats.ID));

    public static final ItemObject<GTICToolDifinition> DRILL = ITEMS.register("drill", GTICToolDifinition::new);

    public static final ItemObject<GTICModifiableTool> GTIC_DRILL = ITEMS.register(
            "gtic_drill",
            ()-> new GTICModifiableTool(
                    ITEM_PROPS,
                    GTIC_DRILL_DEFINITION,
                    GTICToolType.GTIC_DRILL,
                    GTMaterials.Neutronium,
                    GTICToolType.GTIC_DRILL.toolDefinition
            )
    );





    public static void addTabItem(CreativeModeTab.Output output) {
        addPartToTab(output,DRILL_HEAD);
        addPartToTab(output,EMPTY_UNIT);
        addPartToTab(output,DRILL_BODY);
        if (GTIC_DRILL !=null) {
            output.accept(GTIC_DRILL.get().asItem());
        }
    }
    private static void addPartToTab(CreativeModeTab.Output output,ItemObject<ToolPartItem>part) {
        if (part !=null) {
            part.get().addVariants(output::accept,"");
        }
    }
}
