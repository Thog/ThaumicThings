package eu.thog92.thaumicthings.addons.betterstorage;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcft.copy.betterstorage.client.renderer.ItemRendererContainer;
import net.mcft.copy.betterstorage.client.renderer.TileEntityReinforcedChestRenderer;
import net.mcft.copy.betterstorage.proxy.ClientProxy;
import net.mcft.copy.betterstorage.tile.entity.TileEntityReinforcedChest;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class AddonBetterStorage
{
    public static Block hungryReinforcedChest;

    public static int hungryReinforcedChestRenderID = -1;

    public static void preInit()
    {
        GameRegistry.registerTileEntity(TileEntityReinforcedHungryChest.class, "ReinforcedHungryChest");
        hungryReinforcedChest = new TileReinforcedHungryChest();
        hungryReinforcedChestRenderID = ClientProxy.registerTileEntityRenderer(TileEntityReinforcedHungryChest.class, new TileEntityReinforcedChestRenderer());
    }

    @SideOnly(Side.CLIENT)
    public static void initRenders()
    {
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(hungryReinforcedChest), new ItemRendererContainer(TileEntityReinforcedHungryChest.class));
    }
}
