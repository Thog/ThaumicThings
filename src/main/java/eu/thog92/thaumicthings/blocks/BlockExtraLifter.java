package eu.thog92.thaumicthings.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eu.thog92.thaumicthings.ClientProxy;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;

import java.util.Random;

/**
 * Created by thog on 2/26/15.
 */
public class BlockExtraLifter extends BlockMagic {

    private IIcon iconTop;
    private IIcon iconBottom;
    private IIcon iconSide;
    private IIcon iconGlow;

    public BlockExtraLifter() {
        super(Material.wood, "blockExtraLifter");
        this.setTextures("thaumcraft:arcaneearbottom", "thaumcraft:liftertop", "thaumcraft:lifterside", "thaumcraft:animatedglow");
        this.setHardness(2.5F);
        this.setResistance(15.0F);
        this.setStepSound(soundTypeWood);
        this.setCreativeTab(Thaumcraft.tabTC);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.getTexture(side);
    }


    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random r) {
        //TileEntity tile = world.getTileEntity(x, y, z);
        Thaumcraft.proxy.sparkle((float)x + 0.2F + r.nextFloat() * 0.6F, (float)(y + 1), (float)z + 0.2F + r.nextFloat() * 0.6F, 1.0F, 3, -0.3F);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getTexture(int side)
    {
        IIcon texture;
        if(side == 3)
            texture = this.getDefaultTexture();
        else
            texture = super.getTexture(side);

        return texture;
    }



    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType()
    {
        return ClientProxy.extraLifter;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getDefaultTexture() {
        return this.textures.get(2);
    }


}
