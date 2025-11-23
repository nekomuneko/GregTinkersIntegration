package com.nekomu.gtic.data;

import com.nekomu.gtic.GTICMAIN;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.core.HolderLookup.Provider;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = GTICMAIN.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(event.includeServer(),new GTICRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(),new GTICDefinitionDataProvider(packOutput,GTICMAIN.MOD_ID));
        generator.addProvider(event.includeServer(),new GTICSlotLayoutProvider(packOutput));
        var blockTagProvider = new BlockTagsProvider(packOutput,lookupProvider, GTICMAIN.MOD_ID,existingFileHelper) {
            @Override
            protected void addTags(Provider p_256380_) {
            }
        };
        generator.addProvider(event.includeServer(),blockTagProvider);
        generator.addProvider(event.includeServer(),new GTICItemTagProvider(packOutput,lookupProvider,
                blockTagProvider.contentsGetter(),existingFileHelper));

        TinkerMaterialSpriteProvider materialSprites = new TinkerMaterialSpriteProvider();
        GTICTextureProvider GTICSprites = new GTICTextureProvider(packOutput);
        generator.addProvider(event.includeClient(), new MaterialPartTextureGenerator(packOutput,existingFileHelper,GTICSprites,materialSprites));
    }
}
