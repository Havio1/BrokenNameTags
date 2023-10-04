package net.bluefirephoenix.brokennametags.mixin;

import net.minecraft.recipe.RepairItemRecipe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {

    /*
    @Inject(
            method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir) {
        List<ItemStack> list = Lists.newArrayList();
        ItemStack itemStack;


        ItemStack itemStack3 = list.get(0);
        itemStack = list.get(1);

        if (itemStack3.isOf(Items.NAME_TAG) && itemStack3.getCount() == 1 && itemStack.isOf(Items.IRON_INGOT) && itemStack.getCount() == 1) {

            cir.setReturnValue(Items.NAME_TAG.getDefaultStack());
        }


    }
     */

}
