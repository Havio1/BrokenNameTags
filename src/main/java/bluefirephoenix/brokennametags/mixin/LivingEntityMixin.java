package bluefirephoenix.brokennametags.mixin;

import bluefirephoenix.brokennametags.BrokenNameTags;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.village.Merchant;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.UUID;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    protected boolean dead;

    @Shadow
    public abstract void remove(RemovalReason reason);

    @Shadow public abstract boolean isAlive();

    @Unique
    Identifier currentEntity;

    @Unique
    String currentEntityUUID;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {

        currentEntity = Registries.ENTITY_TYPE.getId(this.getType());
        currentEntityUUID = this.getUuidAsString();

        //Make this data-driven
        if (!this.isRemoved() && !this.dead) {

            // check if the entity has a custom name
            if (hasCustomName() || (this instanceof Tameable && ((Tameable) this).getOwner() != null)) { // the order of this if statement matters
                ItemStack tag = new ItemStack(Items.NAME_TAG, 1);
                NbtCompound nbt = new NbtCompound();

                Text name = getDefaultName();

                if (hasCustomName()) {
                    name = getCustomName();
                }

                if (source.getDeathMessage((LivingEntity) (Object) this) != null) {
                    nbt.putString("deathMessage", source.getDeathMessage((LivingEntity) (Object) this).getString());
                }

                tag.setCustomName(name); // todo this is causing the crash

                assert tag.getNbt() != null;
                tag.getNbt().put("data",nbt);
                tag.getNbt().putBoolean("isBroken",true);

                tag.getNbt().putString("entityUUID", currentEntityUUID);

                dropStack(tag);
            }
        }
    }
}