/*******************************************************************************
 * Copyright 2015-2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package biomesoplenty.common.biome.overworld;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.api.config.IBOPWorldSettings;
import biomesoplenty.api.config.IBOPWorldSettings.GeneratorType;
import biomesoplenty.api.enums.BOPClimates;
import biomesoplenty.api.generation.GeneratorStage;
import biomesoplenty.common.block.BlockBOPDirt;
import biomesoplenty.common.block.BlockBOPGrass;
import biomesoplenty.common.block.BlockBOPLeaves;
import biomesoplenty.common.enums.BOPFlowers;
import biomesoplenty.common.enums.BOPGems;
import biomesoplenty.common.enums.BOPPlants;
import biomesoplenty.common.enums.BOPTrees;
import biomesoplenty.common.enums.BOPWoods;
import biomesoplenty.common.world.generator.GeneratorFlora;
import biomesoplenty.common.world.generator.GeneratorGrass;
import biomesoplenty.common.world.generator.GeneratorLakes;
import biomesoplenty.common.world.generator.GeneratorOreSingle;
import biomesoplenty.common.world.generator.GeneratorWeighted;
import biomesoplenty.common.world.generator.tree.GeneratorBasicTree;
import biomesoplenty.common.world.generator.tree.GeneratorBigTree;
import biomesoplenty.common.world.generator.tree.GeneratorTaigaTree;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class BiomeGenOminousWoods extends BOPBiome
{    
    
    public BiomeGenOminousWoods()
    {
        super("ominous_woods", new PropsBuilder("Ominous Woods").withGuiColour(0x3F4151).withTemperature(0.45F).withRainfall(0.6F).withWaterColor(0x1E1B26));

        // terrain
        this.terrainSettings.avgHeight(68).heightVariation(5, 15);

        this.skyColor = 0x34333D;

        this.topBlock = BOPBlocks.grass.getDefaultState().withProperty(BlockBOPGrass.VARIANT, BlockBOPGrass.BOPGrassType.LOAMY);
        this.fillerBlock = BOPBlocks.dirt.getDefaultState().withProperty(BlockBOPDirt.VARIANT, BlockBOPDirt.BOPDirtType.LOAMY);
        
        this.canGenerateRivers = false;
        this.canGenerateVillages = false;
        this.canSpawnInBiome = false;
        
        this.beachBiomeLocation = null;
        
        this.addWeight(BOPClimates.COLD_SWAMP, 1);
        
        this.spawnableCreatureList.clear(); // none of your regular farmyard critters here
        this.spawnableWaterCreatureList.clear();
        
        // lakes
        this.addGenerator("poison_lakes", GeneratorStage.SAND, (new GeneratorLakes.Builder()).amountPerChunk(0.1F).waterLakeForBiome(this).liquid(BOPBlocks.poison).frozenLiquid((IBlockState)null).create());
        
        // trees & logs
        GeneratorWeighted treeGenerator = new GeneratorWeighted(12);
        this.addGenerator("trees", GeneratorStage.TREE, treeGenerator);
        treeGenerator.add("umbran_moss", 4, (new GeneratorBasicTree.Builder()).log(BOPWoods.UMBRAN).leaves(BOPTrees.UMBRAN).minHeight(8).maxHeight(12).leaves(BlockBOPLeaves.paging.getVariantState(BOPTrees.UMBRAN).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false))).maxLeavesRadius(2).vine(BOPBlocks.tree_moss.getDefaultState()).create());
        treeGenerator.add("umbran_spruce", 5, (new GeneratorTaigaTree.Builder()).log(BOPWoods.UMBRAN).leaves(BOPTrees.UMBRAN).maxHeight(20).create()); // TODO: implement pine cones
        treeGenerator.add("dead_tree", 3, (new GeneratorBigTree.Builder()).minHeight(5).maxHeight(12).foliageHeight(0).foliageDensity(0.5D).log(BOPWoods.DEAD).leaves(Blocks.AIR.getDefaultState()).create());
        treeGenerator.add("mega_umbran", 4, (new GeneratorTaigaTree.Builder()).log(BOPWoods.UMBRAN).leaves(BOPTrees.UMBRAN).minHeight(20).maxHeight(30).trunkWidth(2).create());
        treeGenerator.add("dying_tree", 1, (new GeneratorBigTree.Builder()).minHeight(5).maxHeight(12).foliageHeight(1).log(BOPWoods.DEAD).leaves(BOPTrees.DEAD).create());
        
        // grasses
        GeneratorWeighted grassGenerator = new GeneratorWeighted(3.0F);
        this.addGenerator("grass", GeneratorStage.GRASS, grassGenerator);
        grassGenerator.add("shortgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.SHORTGRASS).create());
        grassGenerator.add("mediumgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.MEDIUMGRASS).create());
        grassGenerator.add("tallgrass", 2, (new GeneratorGrass.Builder()).with(BlockTallGrass.EnumType.GRASS).create());
        
        // flowers
        GeneratorWeighted flowerGenerator = new GeneratorWeighted(0.7F);
        this.addGenerator("flowers", GeneratorStage.FLOWERS, flowerGenerator);
        flowerGenerator.add("deathbloom", 2, (new GeneratorFlora.Builder()).with(BOPFlowers.DEATHBLOOM).create());
        
        // other plants
        this.addGenerator("poison_ivy", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.25F).with(BOPPlants.POISONIVY).create());
        this.addGenerator("thorns", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.8F).with(BOPPlants.THORN).create());
        this.addGenerator("dead_leaf_piles", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.2F).with(BOPPlants.DEADLEAFPILE).create());

        // shrooms
        this.addGenerator("red_mushrooms", GeneratorStage.SHROOM,(new GeneratorFlora.Builder()).amountPerChunk(0.3F).with(Blocks.RED_MUSHROOM.getDefaultState()).create());
        this.addGenerator("brown_mushrooms", GeneratorStage.SHROOM,(new GeneratorFlora.Builder()).amountPerChunk(0.5F).with(Blocks.BROWN_MUSHROOM.getDefaultState()).create());

        // gem
        this.addGenerator("malachite", GeneratorStage.SAND, (new GeneratorOreSingle.Builder()).amountPerChunk(12).with(BOPGems.MALACHITE).create());
        
    }
    
    @Override
    public void applySettings(IBOPWorldSettings settings)
    {
        if (!settings.isEnabled(GeneratorType.MUSHROOMS)) {this.removeGenerator("glowshrooms");}
        
        if (!settings.isEnabled(GeneratorType.FLOWERS)) {this.removeGenerator("miners_delight");}
        
        if (!settings.isEnabled(GeneratorType.ROCK_FORMATIONS)) {this.removeGenerator("stone_formations");}
        
        if (!settings.isEnabled(GeneratorType.GEMS)) {this.removeGenerator("malachite");}
        if (!settings.isEnabled(GeneratorType.POISON_IVY)) {this.removeGenerator("poison_ivy");}
        if (!settings.isEnabled(GeneratorType.THORNS)) {this.removeGenerator("thorns");}
        
        if (!settings.isEnabled(GeneratorType.LIQUID_POISON)) {this.removeGenerator("poison_lakes");}
        
        if (!settings.isEnabled(GeneratorType.SOILS)) {this.topBlock = Blocks.GRASS.getDefaultState(); this.fillerBlock = Blocks.DIRT.getDefaultState();}
        
        if (!settings.isEnabled(GeneratorType.FOLIAGE)) {this.removeGenerator("bushes"); this.removeGenerator("koru"); this.removeGenerator("shrubs"); this.removeGenerator("leaf_piles"); this.removeGenerator("dead_leaf_piles"); this.removeGenerator("clover_patches"); this.removeGenerator("sprouts");}
        
        if (!settings.isEnabled(GeneratorType.PLANTS)) {this.removeGenerator("cattail"); this.removeGenerator("double_cattail"); this.removeGenerator("river_cane"); this.removeGenerator("tiny_cacti"); this.removeGenerator("roots"); this.removeGenerator("rafflesia"); this.removeGenerator("desert_sprouts");}
        
        if (!settings.isEnabled(GeneratorType.WATER_PLANTS)) {this.removeGenerator("algae"); this.removeGenerator("water_reeds"); this.removeGenerator("medium_lily"); this.removeGenerator("small_lily"); this.removeGenerator("tiny_lily");}
        
        if (!settings.isEnabled(GeneratorType.FLOWERS)) {this.removeGenerator("flowers");}
        
        GeneratorWeighted treeGen = (GeneratorWeighted)this.getGenerator("trees");
        if (!settings.isEnabled(GeneratorType.TREES)) {this.removeGenerator("trees");
        
        GeneratorWeighted treeGenerator = new GeneratorWeighted(9);
        this.addGenerator("trees", GeneratorStage.TREE, treeGenerator);
        treeGenerator.add("umbran_moss", 4, (new GeneratorBasicTree.Builder()).log(BlockPlanks.EnumType.DARK_OAK).leaves(BlockPlanks.EnumType.DARK_OAK).leaves(Blocks.LEAVES2.getStateFromMeta(BlockPlanks.EnumType.DARK_OAK.getMetadata()).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false))).minHeight(8).maxHeight(12).maxLeavesRadius(2).vine(Blocks.VINE.getDefaultState()).create());
        treeGenerator.add("umbran_spruce", 5, (new GeneratorTaigaTree.Builder()).log(BlockPlanks.EnumType.DARK_OAK).leaves(BlockPlanks.EnumType.DARK_OAK).maxHeight(20).create()); // TODO: implement pine cones
        treeGenerator.add("dead_tree", 3, (new GeneratorBigTree.Builder()).minHeight(5).maxHeight(12).foliageHeight(0).foliageDensity(0.5D).log(BlockPlanks.EnumType.DARK_OAK).leaves(Blocks.AIR.getDefaultState()).create());
        treeGenerator.add("mega_umbran", 4, (new GeneratorTaigaTree.Builder()).log(BlockPlanks.EnumType.DARK_OAK).leaves(BlockPlanks.EnumType.DARK_OAK).minHeight(20).maxHeight(30).trunkWidth(2).create());
        treeGenerator.add("dying_tree", 1, (new GeneratorBigTree.Builder()).minHeight(5).maxHeight(12).foliageHeight(1).log(BlockPlanks.EnumType.DARK_OAK).leaves(BlockPlanks.EnumType.DARK_OAK).create());
        }
        
        GeneratorWeighted grassGen = (GeneratorWeighted)this.getGenerator("grass");
        if (!settings.isEnabled(GeneratorType.GRASSES)) {grassGen.removeGenerator("shortgrass"); grassGen.removeGenerator("mediumgrass"); grassGen.removeGenerator("wheatgrass"); grassGen.removeGenerator("dampgrass");}
    }
    
    @Override
    public int getGrassColorAtPos(BlockPos pos)
    {
        return 0x3F4151;
    }
    
    @Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 0x3F4151;
    }
    
    // TODO: These 2 are copied from 1.7 - but are they used ever?
    public int getFogColour(BlockPos pos)
    {
        return 0x34333D;
    }
    public float getFogDensity(BlockPos pos)
    {
        return 0.145F;
    }
    
   
}
