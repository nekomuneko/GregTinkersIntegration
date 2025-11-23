package com.nekomu.gtic;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import com.nekomu.gtic.item.GTICregistry;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = GTICMAIN.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class GTICCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,"gtic");
    public static final RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("umpyoi",
            ()->CreativeModeTab.builder().icon(()->GTICregistry.DRILL.get().getRenderTool())
                    .title(Component.translatable("itemGroup.gtic"))
            .displayItems(GTICCreativeTabs::addTabItems).build());

    private static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        acceptPart(output,GTICregistry.DRILL_HEAD);
        acceptPart(output,GTICregistry.EMPTY_UNIT);
        acceptPart(output,GTICregistry.DRILL_BODY);
        acceptTool(output,GTICregistry.DRILL);
        acceptTool(output,GTICregistry.GTIC_DRILL);
    }

    private static void acceptPart(Consumer<ItemStack>output, Supplier<? extends IMaterialItem>item){
        item.get().addVariants(output,"");
    }
    private static void acceptTool(Consumer<ItemStack>output, Supplier<? extends IModifiable>tool) {
        ToolBuildHandler.addVariants(output,tool.get(),"");
    }

}
