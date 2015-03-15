package eu.thog92.thaumicthings;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import eu.thog92.thaumicthings.blocks.BlockExtraLifter;
import eu.thog92.thaumicthings.entities.EntityBottleEthereal;
import eu.thog92.thaumicthings.items.ItemBottleEthereal;
import eu.thog92.thaumicthings.tiles.TileEntityExtraLifter;
import eu.thog92.thaumicthings.utils.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

public class CommonProxy
{

    public static Block extraLifter;
    public static Item bottleEthereal;
    private Configuration config;

    public void registerRenders()
    {

    }

    public void initContents()
    {
        ThaumicThings.log.trace("Init Mod Contents...");
        extraLifter = new BlockExtraLifter();
        bottleEthereal = new ItemBottleEthereal().setUnlocalizedName("bottleEthereal");
        GameRegistry.registerItem(bottleEthereal, bottleEthereal.getUnlocalizedName());


        //POTIONS
        int potionOffset = Potion.potionTypes.length;

        int potionCount = 1;
        if(potionOffset < (128 - potionCount))
        {
            ThaumicThings.log.trace("Extending Potion.potionTypes array to " + (potionOffset + potionCount));;
            // Extend potions array
            Potion[] potionTypes = new Potion[potionOffset + potionCount];
            System.arraycopy(Potion.potionTypes, 0, potionTypes, 0, potionOffset);
            ReflectionHelper.setPrivateFinalWithValue(Potion.class, null, potionTypes, new String[]{"potionTypes", "field_76425_a", "a"});
        }



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
        ThaumicThings.log.trace("Init Entities...");
        EntityRegistry.registerModEntity(EntityBottleEthereal.class, "BottleEthereal", 0, ThaumicThings.instance, 64, 20, true);
    }

    public void onBottleBreak(Item item, World world, double x, double y, double z)
    {

    }


    public void loadConfiguration(File file)
    {
        ThaumicThings.log.trace("Loading Configuration...");
        config = new Configuration(file);
        config.addCustomCategoryComment("", "");
    }

    private int getNextPotionID(int start) {
        if(Potion.potionTypes != null && start > 0 && start < Potion.potionTypes.length && Potion.potionTypes[start] == null) {
            return start;
        } else {
            ++start;
            if(start < 128) {
                start = getNextPotionID(start);
            } else {
                start = -1;
            }

            return start;
        }
    }
}
