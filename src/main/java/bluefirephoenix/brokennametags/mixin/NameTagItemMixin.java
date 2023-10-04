package bluefirephoenix.brokennametags.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(NameTagItem.class)
public abstract class NameTagItemMixin extends Item {

    public NameTagItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(
            method = "useOnEntity",
            at = @At("HEAD"),
            cancellable = true
    )
    public void useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (stack.hasCustomName() && !(entity instanceof PlayerEntity) && !stack.getNbt().getBoolean("isBroken")) {
            if (!user.getWorld().isClient && entity.isAlive()) {
                entity.setCustomName(stack.getName());
                if (entity instanceof MobEntity) {
                    ((MobEntity)entity).setPersistent();
                }

                stack.decrement(1);
            }

            cir.setReturnValue(ActionResult.success(user.getWorld().isClient));
        } else {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getNbt() != null) {

            String deathMessage = stack.getNbt().getCompound("data").getString("deathMessage");

            if(!deathMessage.isEmpty()) {
                tooltip.add(Text.of(Formatting.GRAY + deathMessage));
            }
        }
    }
}
