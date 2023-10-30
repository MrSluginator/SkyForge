package net.dilger.sky_forge_mod.init;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.menu.ExampleMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, SkyForgeMod.MOD_ID);

    public static final RegistryObject<MenuType<AbstractContainerMenu>> EXAMPLE_MENU = MENU_TYPES.register("example_menu",
            () -> IForgeMenuType.create(ExampleMenu::new));
        
}