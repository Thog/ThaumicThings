package eu.thog92.thaumicthings;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "thaumicthings", version = "0.1", dependencies = "required-after:Thaumcraft@[4.2,)")
public class ThaumicThings
{
    @Mod.Instance(value = "thaumicthings")
    public static ThaumicThings instance;

    @SidedProxy(serverSide = "eu.thog92.thaumicthings.CommonProxy", clientSide = "eu.thog92.thaumicthings.ClientProxy")
    public static CommonProxy proxy;

    public static Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        log = event.getModLog();
        proxy.loadConfiguration(event.getSuggestedConfigurationFile());
        proxy.initContents();
        proxy.registerRenders();

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
