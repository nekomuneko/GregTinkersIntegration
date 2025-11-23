package com.nekomu.gtic.data;

import com.nekomu.gtic.GTICMAIN;
import com.nekomu.gtic.item.GTICregistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.tags.ItemTags.AXES;
import static net.minecraft.tags.ItemTags.CLUSTER_MAX_HARVESTABLES;
import static slimeknights.tconstruct.common.TinkerTags.Items.*;
import static slimeknights.tconstruct.common.TinkerTags.Items.BONUS_SLOTS;

@SuppressWarnings({"unchecked","removal"})
public class GTICItemTagProvider extends ItemTagsProvider {
    public GTICItemTagProvider
            (PackOutput pGenerator, CompletableFuture<HolderLookup.Provider> lookupProvder,
             CompletableFuture<TagLookup<Block>>provider, ExistingFileHelper existingFileHelper) {
        super(pGenerator,lookupProvder,provider, GTICMAIN.MOD_ID,existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        tag(TOOL_PARTS).add(GTICregistry.DRILL_HEAD.get(), GTICregistry.EMPTY_UNIT.get(),GTICregistry.DRILL_BODY.get());
        addToolTag(GTICregistry.DRILL.get(),
                MODIFIABLE, MULTIPART_TOOL, DURABILITY, HARVEST_PRIMARY, STONE_HARVEST, MELEE_WEAPON, INTERACTABLE_RIGHT, AOE, CLUSTER_MAX_HARVESTABLES,
                BROAD_TOOLS, BONUS_SLOTS, ItemTags.PICKAXES, ItemTags.SHOVELS, AXES);
    }
    @SafeVarargs
    private void addToolTag(ItemLike tool, TagKey<Item>...tags) {
        Item Item = tool.asItem();
        for (TagKey<Item>tag:tags) {
            this.tag(tag).add(Item);
        }
    }
}

