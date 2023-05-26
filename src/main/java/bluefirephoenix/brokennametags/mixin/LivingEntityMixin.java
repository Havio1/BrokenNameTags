package bluefirephoenix.brokennametags.mixin;

import bluefirephoenix.brokennametags.BrokenNameTags;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.village.Merchant;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow protected boolean dead;

    @Shadow public abstract void remove(RemovalReason reason);

    LivingEntity entity;

    boolean intelligentCreatureVanilla;

    MerchantEntity traders;
    IllagerEntity raiders;
    AbstractPiglinEntity piglins;

    String[] blacklistedCreatures= {"villager", "evoker"};

    Identifier currentEntity;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {

        currentEntity = Registries.ENTITY_TYPE.getId(this.getType());

        //intelligentCreatureVanilla = this.isPartOf(traders) ||  this.isPartOf(raiders)  ||   ;
            //("minecraft:" + this.entity.getEntityName()) == "minecraft:villager" this instanceof Merchant || Entity.ID_KEY.equals("cat")
        //Registries.ENTITY_TYPE.getId(this.getType())
            //this instanceof CrossbowUser && this instanceof InventoryOwner ||  this.entity.ID_KEY.equals("evoker") Arrays.toString(f).matches() Entity.ID_KEY

        //Make this data-driven
        if (
                (currentEntity == Registries.ENTITY_TYPE.getId(EntityType.PIGLIN)) ||
                (currentEntity == Registries.ENTITY_TYPE.getId(EntityType.PIGLIN_BRUTE)) ||
                (currentEntity == Registries.ENTITY_TYPE.getId(EntityType.EVOKER)) ||
                (currentEntity == Registries.ENTITY_TYPE.getId(EntityType.PILLAGER)) ||
                (currentEntity == Registries.ENTITY_TYPE.getId(EntityType.VILLAGER)) ||
                (currentEntity == Registries.ENTITY_TYPE.getId(EntityType.WANDERING_TRADER))
        ){
            System.out.println("Intelligent creatures don't drop name tags! " + Registries.ENTITY_TYPE.getId(this.getType()));
        }else if (!this.isRemoved() && !this.dead) {
            // check if the entity has a custom name
            System.out.println("Creatures drop name tags! " + Registries.ENTITY_TYPE.getId(this.getType()));
            if (hasCustomName() || (this instanceof Tameable && ((Tameable) this).getOwner() != null)) { // the order of this if statement matters
                ItemStack tag = new ItemStack(BrokenNameTags.BROKEN_NAMETAG, 1);
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
                tag.getNbt().put("data", nbt);
                dropStack(tag);
            }
        }
    }
}