package com.nekomu.gtic.item;

import com.gregtechceu.gtceu.api.capability.CombinedCapabilityProvider;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.item.IGTTool;
import com.gregtechceu.gtceu.api.item.component.ElectricStats;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.item.tool.IGTToolDefinition;
import com.gregtechceu.gtceu.api.item.tool.ToolHelper;
import com.gregtechceu.gtceu.api.item.tool.aoe.AoESymmetrical;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

import java.util.List;
import java.util.Set;

public class GTICModifiableTool extends ModifiableItem implements IGTTool {

    private final GTToolType gtToolType;
    private final Material dummyMaterial;
    private final IGTToolDefinition gtToolStats;

    public GTICModifiableTool(Properties properties, ToolDefinition tinkersDefinition,
                              GTToolType gtToolType, Material dummyMaterial,
                              IGTToolDefinition gtToolStats) {
        super(properties, tinkersDefinition);
        this.gtToolType = gtToolType;
        this.dummyMaterial = dummyMaterial;
        this.gtToolStats = gtToolStats;
    }

    @Override
    public GTToolType getToolType() {
        return gtToolType;
    }

    @Override
    public Material getMaterial() {
        return dummyMaterial;
    }

    @Override
    public boolean isElectric() {
        return gtToolType.electricTier > -1;
    }

    @Override
    public int getElectricTier() {
        return gtToolType.electricTier;
    }

    @Override
    public IGTToolDefinition getToolStats() {
        return gtToolStats;
    }

    @Nullable
    @Override
    public SoundEntry getSound() {
        return gtToolType.soundEntry;
    }

    @Override
    public boolean playSoundOnBlockDestroy() {
        return gtToolType.playSoundOnBlockDestroy;
    }

    @Override
    public Set<GTToolType> getToolClasses(ItemStack stack) {
        return Set.of(gtToolType);
    }

    @Override
    public float getMaterialToolSpeed() {
        return 0F;
    }

    @Override
    public int getMaterialDurability() {
        return 0;
    }

    @Override
    public float getMaterialAttackDamage() {
        return 0F;
    }

    @Override
    public float getMaterialAttackSpeed() {
        return 0F;
    }

    @Override
    public int getMaterialEnchantability() {
        return 0;
    }

    @Override
    public int getMaterialHarvestLevel() {
        return 0;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        ICapabilityProvider tinkersCapability = super.initCapabilities(stack, nbt);

        if (!isElectric()) {
            return tinkersCapability;
        }

        int tier = getElectricTier();
        long maxCharge = 100000L;
        ElectricStats electricStats = ElectricStats.createElectricItem(maxCharge, tier);

        return new CombinedCapabilityProvider(List.of(
                tinkersCapability,
                new ICapabilityProvider() {
                    @Override
                    @NotNull
                    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                        return electricStats.getCapability(stack, cap);
                    }
                }
        ));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        initializeGTData(stack);
        return stack;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        super.onCraftedBy(stack, worldIn, playerIn);
        initializeGTData(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!stack.getOrCreateTag().getBoolean("GTInitialized")) {
            initializeGTData(stack);
            stack.getOrCreateTag().putBoolean("GTInitialized", true);
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        debugAOE(stack, pos, player);

        // プレイヤーがServerPlayerで、Shiftを押していない場合のみAOE処理
        if (player instanceof ServerPlayer serverPlayer && !player.isShiftKeyDown()) {
            // AOE定義を取得
            AoESymmetrical aoe = getToolStats().getAoEDefinition(stack);

            // AOEが有効な場合は処理を実行
            if (!aoe.isZero()) {
                boolean result = ToolHelper.areaOfEffectBlockBreakRoutine(stack, serverPlayer, pos);
                if (!player.level().isClientSide) {
                    System.out.println("AOE処理実行: " + result);
                }
                return result;
            }
        }

        boolean result = definition$onBlockStartBreak(stack, pos, player);
        if (!player.level().isClientSide) {
            System.out.println("definition$onBlockStartBreak 戻り値: " + result);
        }
        return result;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        debugGUI(stack, context);

        if (!context.getLevel().isClientSide) {
            System.out.println("=== Behavior個別実行 ===");
            for (var behavior : gtToolStats.getBehaviors()) {
                System.out.println("Behavior: " + behavior.getClass().getSimpleName());
                InteractionResult result = behavior.onItemUseFirst(stack, context);
                System.out.println("  -> 戻り値: " + result);
            }
            System.out.println("=======================");
        }

        InteractionResult gtResult = definition$onItemUseFirst(stack, context);

        if (!context.getLevel().isClientSide) {
            System.out.println("definition$onItemUseFirst 戻り値: " + gtResult);
        }

        if (gtResult != InteractionResult.PASS) {
            return gtResult;
        }

        InteractionResult tinkersResult = super.onItemUseFirst(stack, context);

        if (!context.getLevel().isClientSide) {
            System.out.println("super.onItemUseFirst 戻り値: " + tinkersResult);
        }

        return tinkersResult;
    }

    private void debugAOE(ItemStack stack, BlockPos pos, Player player) {
        if (!player.level().isClientSide && player instanceof ServerPlayer) {
            System.out.println("=== AOE Debug ===");
            System.out.println("Position: " + pos);
            System.out.println("Shift: " + player.isShiftKeyDown());
            CompoundTag behaviourTag = stack.getOrCreateTag().getCompound("GT.Behaviours");
            System.out.println("AOE NBT: " + behaviourTag);
            System.out.println("isElectric: " + isElectric());
            System.out.println("ToolType: " + gtToolType.name);
            System.out.println("ToolClasses: " + ToolHelper.getToolTypes(stack));
            System.out.println("================");
        }
    }

    private void debugGUI(ItemStack stack, UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            System.out.println("=== GUI Debug ===");
            System.out.println("Position: " + context.getClickedPos());
            System.out.println("Face: " + context.getClickedFace());
            System.out.println("Hand: " + context.getHand());
            System.out.println("Player Shift: " + context.getPlayer().isShiftKeyDown());
            System.out.println("Behaviors: " + gtToolStats.getBehaviors());
            System.out.println("ToolStats doesSneakBypassUse: " + gtToolStats.doesSneakBypassUse());
            System.out.println("=================");
        }
    }

    private void initializeGTData(ItemStack stack) {
        CompoundTag rootTag = stack.getOrCreateTag();

        // --- 行動用タグの初期化 ---
        CompoundTag behaviourTag = rootTag.getCompound(ToolHelper.BEHAVIOURS_TAG_KEY);
        if (behaviourTag.isEmpty()) {
            behaviourTag = new CompoundTag();
            rootTag.put(ToolHelper.BEHAVIOURS_TAG_KEY, behaviourTag);
        }

        // --- GT Behavior の NBT 初期化 ---
        for (var behavior : gtToolStats.getBehaviors()) {
            try {
                behavior.addBehaviorNBT(stack, behaviourTag);
            } catch (Throwable t) {
                System.err.println("[GTICModifiableTool] addBehaviorNBT failed for " + behavior.getClass().getName());
                t.printStackTrace();
            }
        }

        // --- AOE 初期化 ---
        AoESymmetrical aoe = gtToolStats.getAoEDefinition(stack);
        if (!aoe.isZero()) {
            behaviourTag.putInt(ToolHelper.MAX_AOE_COLUMN_KEY, aoe.column);
            behaviourTag.putInt(ToolHelper.MAX_AOE_ROW_KEY, aoe.row);
            behaviourTag.putInt(ToolHelper.MAX_AOE_LAYER_KEY, aoe.layer);

            behaviourTag.putInt(ToolHelper.AOE_COLUMN_KEY, aoe.column);
            behaviourTag.putInt(ToolHelper.AOE_ROW_KEY, aoe.row);
            behaviourTag.putInt(ToolHelper.AOE_LAYER_KEY, aoe.layer);
        }

        // --- 電動ツールの初期化 ---
        if (isElectric()) {
            long maxCharge = 100000L;
            rootTag.putLong(ToolHelper.MAX_CHARGE_KEY, maxCharge);
            rootTag.putLong(ToolHelper.CHARGE_KEY, rootTag.getLong(ToolHelper.CHARGE_KEY));
        }
    }
}