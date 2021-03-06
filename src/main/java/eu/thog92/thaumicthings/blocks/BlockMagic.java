package eu.thog92.thaumicthings.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.List;

public class BlockMagic extends Block
{

    @SideOnly(Side.CLIENT)
    private List<IIcon> textures;
    private String[] strTextures;

    BlockMagic(Material material, String name)
    {
        super(material);
        this.setBlockName(name);
        GameRegistry.registerBlock(this, name);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getTextureForRender(int side, int metadata)
    {
        IIcon texture;
        if (side >= this.textures.size())
            texture = this.getDefaultTexture();
        else
            texture = this.getTexture(side);

        return texture;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getTexture(int side)
    {
        return this.textures.get(side);
    }

    void setTextures(String... strTextures)
    {
        this.strTextures = strTextures;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir)
    {
        if (strTextures == null)
            return;

        this.textures = new ArrayList<IIcon>();

        for (String textureName : strTextures)
        {
            textures.add(ir.registerIcon(textureName));
        }
    }

    @SideOnly(Side.CLIENT)
    IIcon getDefaultTexture()
    {
        return this.textures.get(this.textures.size() - 1);
    }
}
