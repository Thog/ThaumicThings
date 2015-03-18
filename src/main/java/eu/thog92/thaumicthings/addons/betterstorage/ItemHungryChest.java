package eu.thog92.thaumicthings.addons.betterstorage;

import net.mcft.copy.betterstorage.item.tile.ItemLockable;
import net.mcft.copy.betterstorage.tile.ContainerMaterial;
import net.mcft.copy.betterstorage.tile.TileLockable;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemHungryChest extends ItemLockable
{
    public ItemHungryChest(Block block)
    {
        super(block);
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (!((TileLockable) Block.getBlockFromItem(stack.getItem())).hasMaterial())
            return super.getItemStackDisplayName(stack);

        ContainerMaterial material = ContainerMaterial.getMaterial(stack, ContainerMaterial.iron);

        String name = StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name.full");
        String materialName = StatCollector.translateToLocal("material.betterstorage." + material.name);
        return name.replace("%MATERIAL%", materialName);
    }
}
