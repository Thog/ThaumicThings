package eu.thog92.thaumicthings.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eu.thog92.thaumicthings.entities.EntityBottleEthereal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;

/**
 * Created by Thog92 on 10/03/2015.
 */
public class ItemBottleEthereal extends Item
{
    public ItemBottleEthereal() {
        this.maxStackSize = 8;
        this.setMaxDamage(0);
        this.setCreativeTab(Thaumcraft.tabTC);
        this.setHasSubtypes(false);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected String getIconString()
    {
        return "thaumicthings:bottle_ethereal";
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!player.capabilities.isCreativeMode) {
            --stack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if(!world.isRemote) {
            world.spawnEntityInWorld(new EntityBottleEthereal(world, player));
        }

        return stack;
    }
}
