package net.bluefirephoenix.brokennametags.registry.tag;

import net.bluefirephoenix.brokennametags.BrokenNameTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> BLACKLISTED = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(BrokenNameTags.MOD_ID, "blacklisted"));
}
