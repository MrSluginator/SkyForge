package net.dilger.sky_forge_mod.init;

//import net.dilger.sky_forge_mod.entity.ExampleAnimatedEntity;
import net.dilger.sky_forge_mod.entity.ExampleEntity;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SkyForgeMod.MOD_ID);

    public static final RegistryObject<EntityType<ExampleEntity>> EXAMPLE_ENTITY = ENTITIES.register("example_entity",
            () -> EntityType.Builder.<ExampleEntity>of(ExampleEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 1.0f)
                    .build(new ResourceLocation(SkyForgeMod.MOD_ID, "example_entity").toString())
    );
    /*public static final RegistryObject<EntityType<ExampleAnimatedEntity>> EXAMPLE_ANIMATED_ENTITY = ENTITIES.register("example_animated_entity",
            () -> EntityType.Builder.<ExampleAnimatedEntity>of(ExampleAnimatedEntity::new, MobCategory.CREATURE)
                    .sized(0.25f, 0.25f)
                    .build(new ResourceLocation(SkyForgeMod.MOD_ID, "example_animated_entity").toString())
    );*/
}