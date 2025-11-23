package com.nekomu.gtic.data;

import com.nekomu.gtic.GTICMAIN;
import com.nekomu.gtic.item.GTICregistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.server.packs.repository.Pack;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;

import java.util.function.Consumer;

public class GTICRecipeProvider extends RecipeProvider implements IToolRecipeHelper {
    public GTICRecipeProvider(PackOutput datagen) {
        super(datagen);
    }
    @Override
    public String getModId() {
        return GTICMAIN.MOD_ID;
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe>consumer) {
        String folder = "tools/building";
        toolBuilding(consumer, GTICregistry.DRILL.get(),folder);
        toolBuilding(consumer,GTICregistry.GTIC_DRILL.get(),folder);
    }
}
