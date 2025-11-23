package com.nekomu.gtic.data;

import com.nekomu.gtic.GTICMAIN;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;

public class GTICTextureProvider extends AbstractPartSpriteProvider {
    public GTICTextureProvider(PackOutput output) {
        super(GTICMAIN.MOD_ID);
    }
    @Override
    protected void addAllSpites(){
        addHead("drill_head");
        addBinding("drill_body");
        addHandle("empty_unit");

        buildTool("drill")
                .addBreakableHead("drill_head")
                .addHandle("empty_unit");

        addHead("drill_head");
        addBinding("drill_body");
        addHandle("empty_unit");

        buildTool("gtic_drill")
                .addBreakableHead("drill_head")
                .addHandle("empty_unit");
    }


    @Override
    public String getName(){
        return "GregTinkersIntegration Texture";
    }
}
