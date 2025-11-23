package com.nekomu.gtic.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.item.tool.ToolHelper;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.common.data.item.GTToolActions;
import com.gregtechceu.gtceu.common.item.tool.behavior.AOEConfigUIBehavior;
import com.gregtechceu.gtceu.common.item.tool.behavior.TorchPlaceBehavior;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.nekomu.gtic.item.GTICModifiableTool;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

public class GTICToolType {

    public static final GTToolType GTIC_DRILL = GTToolType.builder("gtdrill")
            .toolTag(GTToolType.ToolItemTagType.MATCH, CustomTags.DRILLS)
            .toolTag(ItemTags.PICKAXES)
            .toolTag(ItemTags.SHOVELS)
            .toolTag(ItemTags.HOES)
            .toolTag(ItemTags.CLUSTER_MAX_HARVESTABLES)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .toolStats(b -> b.blockBreaking().aoe(1, 1, 0)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV)
                    .behaviors(AOEConfigUIBehavior.INSTANCE, TorchPlaceBehavior.INSTANCE))
            .sound(GTSoundEntries.DRILL_TOOL, true)
            .electric(GTValues.LV)
            .toolClassNames("drill")
            .defaultActions(GTToolActions.DEFAULT_DRILL_ACTIONS)
            .build();
}
