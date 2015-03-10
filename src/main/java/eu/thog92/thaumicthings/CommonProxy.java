package eu.thog92.thaumicthings;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import eu.thog92.thaumicthings.blocks.BlockExtraLifter;
import eu.thog92.thaumicthings.entities.EntityBottleEthereal;
import eu.thog92.thaumicthings.items.ItemBottleEthereal;
import eu.thog92.thaumicthings.tiles.TileEntityExtraLifter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class CommonProxy
{

    public Block extraLifter;
    public Item bottleEthereal;

    public void registerRenders()
    {

    }

    public void initContents()
    {
        extraLifter = new BlockExtraLifter();
        bottleEthereal = new ItemBottleEthereal().setUnlocalizedName("bottleEthereal");
        GameRegistry.registerItem(bottleEthereal, bottleEthereal.getUnlocalizedName());
    }

    public void sparkle(float x, float y, float z, float size, int color, double motionX, double motionY, double motionZ)
    {

    }

    public void initTiles()
    {
        GameRegistry.registerTileEntity(TileEntityExtraLifter.class, "TileEntityExtraLifter");
    }

    public void initEntities()
    {
        EntityRegistry.registerModEntity(EntityBottleEthereal.class, "BottleEthereal", 0, ThaumicThings.instance, 64, 20, true);
    }

    public void onBottleBreak(Item item, World world, double x, double y, double z)
    {

    }


}
