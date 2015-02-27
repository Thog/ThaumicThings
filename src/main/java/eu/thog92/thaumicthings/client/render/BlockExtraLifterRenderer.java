package eu.thog92.thaumicthings.client.render;

import eu.thog92.thaumicthings.ClientProxy;
import eu.thog92.thaumicthings.blocks.BlockMagic;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Created by thog on 2/26/15.
 */
public class BlockExtraLifterRenderer extends BlockRenderingHandler
{

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return ClientProxy.extraLifter;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockMagic block, int modelId, RenderBlocks renderer)
    {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);


        //TODO: TileEntity
        int brightness = 180;

        // Liquids render
        tessellator.setColorOpaque_I(40960);

        tessellator.setBrightness(brightness);
        if (block.shouldSideBeRendered(world, x, y + 1, z, 6))
        {
            renderer.renderFaceYPos(block, (double) x, (double) ((float) y - 0.01F), (double) z, block.getTexture(3));
        }

        tessellator.setColorOpaque_I(14488063);
        if (block.shouldSideBeRendered(world, x + 1, y, z, 6))
        {
            renderer.renderFaceXPos(block, (double) ((float) x - 0.01F), (double) y, (double) z, block.getTexture(3));
        }

        if (block.shouldSideBeRendered(world, x - 1, y, z, 6))
        {
            renderer.renderFaceXNeg(block, (double) ((float) x + 0.01F), (double) y, (double) z, block.getTexture(3));
        }

        if (block.shouldSideBeRendered(world, x, y, z + 1, 6))
        {
            renderer.renderFaceZPos(block, (double) x, (double) y, (double) ((float) z - 0.01F), block.getTexture(3));
        }

        if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
        {
            renderer.renderFaceZNeg(block, (double) x, (double) y, (double) ((float) z + 0.01F), block.getTexture(3));
        }

        // Clean and restore default blockbounds
        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);

        return true;
    }

    @Override
    public void renderInventoryBlock(BlockMagic block, int metadata, int modelId, RenderBlocks renderer)
    {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        this.drawQuadsWithBlock(renderer, block);
        Color color = new Color(40960); // Default Thaumcraft Color
        GL11.glColor3f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F);
        block.setBlockBounds(0.01F, 0.9F, 0.01F, 0.99F, 0.99F, 0.99F);
        renderer.setRenderBoundsFromBlock(block);
        drawAllCube(renderer, block, block.getTexture(3));
        color = new Color(14488063); // Default Thaumcraft Color
        GL11.glColor3f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F);
        block.setBlockBounds(0.01F, 0.1F, 0.01F, 0.99F, 0.9F, 0.99F);
        renderer.setRenderBoundsFromBlock(block);
        drawAllCube(renderer, block, block.getTexture(3));

        // Restore Color
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
}
