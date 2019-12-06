/*******************************************************************************
 * Copyright 2014-2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package biomesoplenty.common.crafting;

import biomesoplenty.api.item.BOPItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;

public class BiomeEssenceRecipe extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
	static {
		RecipeSorter.register("biomesoplenty:biome_essence", BiomeEssenceRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
	}

    private ItemStack recipeOutput = ItemStack.EMPTY;
    
    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world)
    {
        ItemStack biomeRadar = ItemStack.EMPTY;
        ItemStack biomeEssence = ItemStack.EMPTY;

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);

                if (itemstack != null)
                {
                    if (itemstack.getItem() == BOPItems.biome_finder) biomeRadar = itemstack.copy();
                    else if (itemstack.getItem() == BOPItems.biome_essence) biomeEssence = itemstack.copy();
                }
            }
        }
        
        if (biomeRadar != ItemStack.EMPTY && biomeEssence != ItemStack.EMPTY)
        {
            if (!biomeEssence.hasTagCompound() || !biomeEssence.getTagCompound().hasKey("biomeID")) return false;
            
            int biomeID = biomeEssence.getTagCompound().getInteger("biomeID");
            
            if (!biomeRadar.hasTagCompound()) biomeRadar.setTagCompound(new NBTTagCompound());
            
            biomeRadar.getTagCompound().setInteger("biomeIDToFind", biomeID);
            biomeRadar.getTagCompound().setBoolean("found", false);
            
            recipeOutput = biomeRadar;
            
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1)
    {
        return recipeOutput.copy();
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return recipeOutput;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> itemList = NonNullList.create();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            itemList.add(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return itemList;
    }

    @Override
    public boolean isDynamic()
    {
        return true;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width * height >= 1;
    }
}
