package eu.thog92.thaumicthings.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eu.thog92.thaumicthings.ClientProxy;
import eu.thog92.thaumicthings.ThaumicThings;
import eu.thog92.thaumicthings.tileentity.TileEntityExtraLifter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;

import java.util.Random;

/**
 * Created by thog on 2/26/15.
 */
public class BlockExtraLifter extends BlockMagic implements ITileEntityProvider
{

    public BlockExtraLifter()
    {
        super(Material.wood, "blockExtraLifter");
        this.setTextures("thaumcraft:arcaneearbottom", "thaumcraft:liftertop", "thaumcraft:lifterside", "thaumcraft:animatedglow");
        this.setHardness(2.5F);
        this.setResistance(15.0F);
        this.setStepSound(soundTypeWood);
        this.setCreativeTab(Thaumcraft.tabTC);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return this.getTextureForRender(side, meta);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random r)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityExtraLifter)) return;
        TileEntityExtraLifter tileLifter = (TileEntityExtraLifter) tile;
        float directionModifer = tileLifter.getModifier();
        if(tileLifter.isDisabled()) return;
        float modifier = tileLifter.reverted ? 3.0F : 0.0F;
        if (metadata == 0)
            ThaumicThings.proxy.sparkle((float) x + 0.2F + r.nextFloat() * 0.6F, (float) (y + 0.5F) - modifier, (float) z + 0.2F + r.nextFloat() * 0.6F, 1.0F, 3, 0, directionModifer * -0.3F, 0);
        else if (metadata == 1)
            ThaumicThings.proxy.sparkle((float) x + 0.2F + r.nextFloat() * 0.6F, (float) (y + 0.5F) + modifier, (float) z + 0.2F + r.nextFloat() * 0.6F, 1.0F, 3, 0, directionModifer * 0.3F, 0);
        else if (metadata == 2)
            ThaumicThings.proxy.sparkle((float) x + 0.2F + r.nextFloat() * 0.6F, (float) (y + 0.5F), (float) z + 0.2F - modifier + r.nextFloat() * 0.6F, 1.0F, 3, 0, 0, directionModifer * -0.3F);
        else if (metadata == 3)
            ThaumicThings.proxy.sparkle((float) x + 0.2F + r.nextFloat() * 0.6F, (float) (y + 0.5F), (float) z + 0.2F + modifier + r.nextFloat() * 0.6F, 1.0F, 3, 0, 0, directionModifer * 0.3F);
        else if (metadata == 4)
            ThaumicThings.proxy.sparkle((float) x + 0.2F - modifier + r.nextFloat() * 0.6F, (float) (y + 0.5F), (float) z + 0.2F + r.nextFloat() * 0.6F, 1.0F, 3, directionModifer * -0.3F, 0, 0);
        else if (metadata == 5)
            ThaumicThings.proxy.sparkle((float) x + 0.2F + modifier + r.nextFloat() * 0.6F, (float) (y + 0.5F), (float) z + 0.2F + r.nextFloat() * 0.6F, 1.0F, 3, directionModifer * 0.3F, 0, 0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getTextureForRender(int side, int meta)
    {
        IIcon texture = null;

        switch (meta)
        {
            case 0:
            {
                if (side == 0)
                    texture = super.getTextureForRender(1, 0);
                else if (side == 1)
                    texture = super.getTextureForRender(0, 0);
                else if (side == 3)
                    texture = this.getDefaultTexture();

                break;
            }

            case 2:
            {

                if (side == 0 || side == 1)
                    texture = this.getDefaultTexture();
                else if (side == 2)
                    texture = super.getTextureForRender(1, 0);
                else if (side == 3)
                    texture = super.getTextureForRender(0, 0);
                break;
            }

            case 3:
            {

                if (side == 0 || side == 1)
                    texture = this.getDefaultTexture();
                else if (side == 2)
                    texture = super.getTextureForRender(0, 0);
                else if (side == 3)
                    texture = super.getTextureForRender(1, 0);
                break;
            }

            case 4:
            {
                if (side == 0 || side == 1 || side == 3)
                    texture = this.getDefaultTexture();
                else if (side == 4)
                    texture = super.getTextureForRender(1, 0);
                else if (side == 5)
                    texture = super.getTextureForRender(0, 0);
                break;
            }

            case 5:
            {
                if (side == 0 || side == 1 || side == 3)
                    texture = this.getDefaultTexture();
                else if (side == 4)
                    texture = super.getTextureForRender(0, 0);
                else if (side == 5)
                    texture = super.getTextureForRender(1, 0);
                break;
            }

            case 1:
            default:
            {
                if (side == 3)
                    texture = this.getDefaultTexture();
                break;
            }


        }


        if (texture == null)
            texture = super.getTextureForRender(side, meta);

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
    public IIcon getDefaultTexture()
    {
        return this.getTexture(2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityExtraLifter(metadata);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack)
    {
        int l = BlockPistonBase.determineOrientation(world, x, y, z, placer);
        System.out.println(l);
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }

    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side)
    {
        //TODO: Check side and metadata
        return true;
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block par5) {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileEntityExtraLifter && ((TileEntityExtraLifter)te).isDisabled() != ((TileEntityExtraLifter)te).lastDisabledStatus()) {
            this.updateNeighborLifter(world, x, y, z);
        }

        super.onNeighborBlockChange(world, x, y, z, par5);
    }

    private void updateNeighborLifter(World world, int x, int y, int z) {
        TileEntityExtraLifter te = (TileEntityExtraLifter) world.getTileEntity(x, y, z);

        for(int count = 1; te.getNeightborBlock(count) == this; ++count) {
            TileEntity neightborTe = te.getNeightborTile(count);
            if(neightborTe != null && neightborTe instanceof TileEntityExtraLifter) {
                ((TileEntityExtraLifter)neightborTe).requiresUpdate = true;
            }
        }
        te.requiresUpdate = true;
    }

}
