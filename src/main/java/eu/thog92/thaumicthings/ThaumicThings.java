package eu.thog92.thaumicthings;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import eu.thog92.thaumicthings.addons.betterstorage.AddonBetterStorage;
import net.minecraft.util.DamageSource;
import org.apache.logging.log4j.Logger;

@Mod(modid = "thaumicthings", version = "0.1", dependencies = "required-after:Thaumcraft@[4.2,); after:betterstorage;")
public class ThaumicThings
{
    @Mod.Instance(value = "thaumicthings")
    public static ThaumicThings instance;

    @SidedProxy(serverSide = "eu.thog92.thaumicthings.CommonProxy", clientSide = "eu.thog92.thaumicthings.ClientProxy")
    public static CommonProxy proxy;

    public static Logger log;

    public static DamageSource ethereal = new DamageSource("ethereal").setDamageBypassesArmor().setMagicDamage();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        log = event.getModLog();
        proxy.loadConfiguration(event.getSuggestedConfigurationFile());
        proxy.initContents();
        proxy.registerRenders();

        if (Loader.isModLoaded("betterstorage"))
            AddonBetterStorage.preInit();

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.initTiles();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.initEntities();
    }
}
