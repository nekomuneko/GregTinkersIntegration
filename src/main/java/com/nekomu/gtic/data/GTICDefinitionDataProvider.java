package com.nekomu.gtic.data;

import net.minecraft.tags.BlockTags;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import com.nekomu.gtic.item.GTICregistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.ToolActions;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.tools.definition.module.aoe.BoxAOEIterator;
import slimeknights.tconstruct.library.tools.definition.module.build.MultiplyStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.SetStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolActionsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.IsEffectiveModule;
import slimeknights.tconstruct.library.tools.nbt.MultiplierNBT;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;

import static com.nekomu.gtic.item.GTICregistry.*;

public class GTICDefinitionDataProvider extends AbstractToolDefinitionDataProvider {
    public GTICDefinitionDataProvider(PackOutput packOutput, String MOD_ID) {
        super(packOutput,MOD_ID);
    }
    @Override
    protected void addToolDefinitions() {

        define(GTICregistry.DRILL)
                .module(PartStatsModule.parts()
                        .part(DRILL_HEAD)
                        .part(EMPTY_UNIT)
                        .part(DRILL_BODY).build())
                .module(new SetStatsModule(StatsNBT.builder()
                        .set(ToolStats.ATTACK_DAMAGE,3f)
                        .set(ToolStats.MINING_SPEED,3f).build()))
                .module(new MultiplyStatsModule(MultiplierNBT.builder()
                        .set(ToolStats.ATTACK_DAMAGE,5f)
                        .set(ToolStats.MINING_SPEED,10f)
                        .set(ToolStats.DURABILITY,10f).build()))
                .largeToolStartingSlots()
                .module(ToolActionsModule.of(ToolActions.PICKAXE_DIG, ToolActions.SHOVEL_DIG))
                .module(BoxAOEIterator.builder(8,7,16).addDepth(1).addHeight(1).build())
                .module(IsEffectiveModule.tag(BlockTags.MINEABLE_WITH_PICKAXE));

        define(GTIC_DRILL)
                .module(PartStatsModule.parts()
                        .part(DRILL_HEAD)
                        .part(EMPTY_UNIT)
                        .part(DRILL_BODY).build())
                .module(new SetStatsModule(StatsNBT.builder()
                        .set(ToolStats.ATTACK_DAMAGE,3f)
                        .set(ToolStats.MINING_SPEED,3f).build()))
                .module(new MultiplyStatsModule(MultiplierNBT.builder()
                        .set(ToolStats.ATTACK_DAMAGE,5f)
                        .set(ToolStats.MINING_SPEED,10f)
                        .set(ToolStats.DURABILITY,10f).build()))
                .largeToolStartingSlots()
                .build();


    }
    @Override
    public String getName() {
        return "GregTinkersIntegration ToolDefinition";}
}
