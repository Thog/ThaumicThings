package eu.thog92.thaumicthings;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ThaumicThings.MODID, version = ThaumicThings.VERSION, dependencies = "required-after:Thaumcraft@[4.2,)")
public class ThaumicThings
{
    public static final String MODID = "thaumicthings";
    public static final String VERSION = "0.0.1";

    @SidedProxy(serverSide = "eu.thog92.thaumicthings.CommonProxy", clientSide = "eu.thog92.thaumicthings.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.initContents();
        proxy.registerRenders();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.initTiles();
    }
}
