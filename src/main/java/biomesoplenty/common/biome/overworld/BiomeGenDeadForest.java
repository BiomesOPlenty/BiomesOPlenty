/*******************************************************************************
 * Copyright 2015-2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package biomesoplenty.common.biome.overworld;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.api.config.IBOPWorldSettings;
import biomesoplenty.api.config.IBOPWorldSettings.GeneratorType;
import biomesoplenty.api.enums.BOPClimates;
import biomesoplenty.api.generation.GeneratorStage;
import biomesoplenty.common.block.BlockBOPDirt;
import biomesoplenty.common.block.BlockBOPGrass;
import biomesoplenty.common.enums.BOPGems;
import biomesoplenty.common.enums.BOPPlants;
import biomesoplenty.common.enums.BOPTrees;
import biomesoplenty.common.enums.BOPWoods;
import biomesoplenty.common.world.generator.GeneratorFlora;
import biomesoplenty.common.world.generator.GeneratorGrass;
import biomesoplenty.common.world.generator.GeneratorOreSingle;
import biomesoplenty.common.world.generator.GeneratorWaterside;
import biomesoplenty.common.world.generator.GeneratorWeighted;
import biomesoplenty.common.world.generator.tree.GeneratorBasicTree;
import biomesoplenty.common.world.generator.tree.GeneratorBigTree;
import biomesoplenty.common.world.generator.tree.GeneratorTaigaTree;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class BiomeGenDeadForest extends BOPBiome
{    
    public BiomeGenDeadForest()
    {
        super("dead_forest", new PropsBuilder("Dead Forest").withGuiColour(0xBCA165).withTemperature(0.3F).withRainfall(0.3F));
        
        // terrain
        this.terrainSettings.avgHeight(68).heightVariation(8, 25);
        
        this.topBlock = BOPBlocks.grass.getDefaultState().withProperty(BlockBOPGrass.VARIANT, BlockBOPGrass.BOPGrassType.LOAMY);
        this.fillerBlock = BOPBlocks.dirt.getDefaultState().withProperty(BlockBOPDirt.VARIANT, BlockBOPDirt.BOPDirtType.LOAMY);
        
        this.canSpawnInBiome = false;
        this.canGenerateVillages = false;
        
        if (BOPBiomes.gravel_beach.isPresent())
        {
        	this.beachBiomeLocation = ((BOPBiome)BOPBiomes.gravel_beach.get()).getResourceLocation();
        }
        
        this.addWeight(BOPClimates.BOREAL, 3);
        
        // gravel
        this.addGenerator("gravel", GeneratorStage.SAND_PASS2, (new GeneratorWaterside.Builder()).amountPerChunk(4).maxRadius(7).with(Blocks.GRAVEL.getDefaultState()).create());

        // trees
        GeneratorWeighted treeGenerator = new GeneratorWeighted(3);
        this.addGenerator("trees", GeneratorStage.TREE, treeGenerator);
        treeGenerator.add("spruce", 3, (new GeneratorTaigaTree.Builder()).minHeight(6).maxHeight(16).create()); // TODO: implement pine cones
        treeGenerator.add("dying_tree", 5, (new GeneratorBigTree.Builder()).minHeight(5).maxHeight(12).foliageHeight(2).leaves(BOPTrees.DEAD).create());
        treeGenerator.add("oak", 3, (new GeneratorBasicTree.Builder()).leaves(BOPTrees.DEAD).create());
        treeGenerator.add("dead_tree", 1, (new GeneratorBigTree.Builder()).minHeight(5).maxHeight(12).foliageHeight(0).foliageDensity(0.5D).log(BOPWoods.DEAD).leaves(Blocks.AIR.getDefaultState()).create());
        
        // other plants
        this.addGenerator("thorns", GeneratorStage.FLOWERS, (new GeneratorFlora.Builder()).amountPerChunk(0.3F).with(BOPPlants.THORN).create());
        this.addGenerator("shrubs", GeneratorStage.FLOWERS, (new GeneratorFlora.Builder()).amountPerChunk(0.1F).with(BOPPlants.SHRUB).create());
        this.addGenerator("water_reeds", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(0.8F).with(BOPPlants.REED).generationAttempts(32).create());
        this.addGenerator("dead_leaf_piles", GeneratorStage.FLOWERS, (new GeneratorFlora.Builder()).amountPerChunk(3.5F).with(BOPPlants.DEADLEAFPILE).generationAttempts(64).create());        
        
        // grasses
        GeneratorWeighted grassGenerator = new GeneratorWeighted(1.5F);
        this.addGenerator("grass", GeneratorStage.GRASS, grassGenerator);
        grassGenerator.add("shortgrass", 4, (new GeneratorGrass.Builder()).with(BOPPlants.SHORTGRASS).create());
        grassGenerator.add("mediumgrass", 3, (new GeneratorGrass.Builder()).with(BOPPlants.MEDIUMGRASS).create());
        grassGenerator.add("tallgrass", 2, (new GeneratorGrass.Builder()).with(BlockTallGrass.EnumType.GRASS).create());
        grassGenerator.add("dead_bushes", 1, (new GeneratorGrass.Builder()).with(BlockTallGrass.EnumType.DEAD_BUSH).create());
        
        // gem
        this.addGenerator("tanzanite", GeneratorStage.SAND, (new GeneratorOreSingle.Builder()).amountPerChunk(12).with(BOPGems.TANZANITE).create());
        
    }
    
    @Override
    public void applySettings(IBOPWorldSettings settings)
    {
        if (!settings.isEnabled(GeneratorType.MUSHROOMS)) {this.removeGenerator("glowshrooms");}
        
        if (!settings.isEnabled(GeneratorType.FLOWERS)) {this.removeGenerator("miners_delight");}
        
        if (!settings.isEnabled(GeneratorType.ROCK_FORMATIONS)) {this.removeGenerator("stone_formations");}
        
        if (!settings.isEnabled(GeneratorType.GEMS)) {this.removeGenerator("tanzanite");}
        if (!settings.isEnabled(GeneratorType.THORNS)) {this.removeGenerator("thorns");}
        
        if (!settings.isEnabled(GeneratorType.SOILS)) {this.topBlock = Blocks.GRASS.getDefaultState(); this.fillerBlock = Blocks.DIRT.getDefaultState();}
        
        if (!settings.isEnabled(GeneratorType.FOLIAGE)) {this.removeGenerator("bushes"); this.removeGenerator("koru"); this.removeGenerator("shrubs"); this.removeGenerator("leaf_piles"); this.removeGenerator("dead_leaf_piles"); this.removeGenerator("clover_patches"); this.removeGenerator("sprouts");}
        
        if (!settings.isEnabled(GeneratorType.PLANTS)) {this.removeGenerator("cattail"); this.removeGenerator("double_cattail"); this.removeGenerator("river_cane"); this.removeGenerator("tiny_cacti"); this.removeGenerator("roots"); this.removeGenerator("rafflesia"); this.removeGenerator("desert_sprouts");}
        
        if (!settings.isEnabled(GeneratorType.WATER_PLANTS)) {this.removeGenerator("algae"); this.removeGenerator("water_reeds"); this.removeGenerator("medium_lily"); this.removeGenerator("small_lily"); this.removeGenerator("tiny_lily");}
        
        GeneratorWeighted treeGen = (GeneratorWeighted)this.getGenerator("trees");
        if (!settings.isEnabled(GeneratorType.TREES)) {this.removeGenerator("trees");
        
        GeneratorWeighted treeGenerator = new GeneratorWeighted(3);
        this.addGenerator("trees", GeneratorStage.TREE, treeGenerator);
        treeGenerator.add("spruce", 3, (new GeneratorTaigaTree.Builder()).minHeight(6).maxHeight(16).create()); // TODO: implement pine cones
        treeGenerator.add("dying_tree", 5, (new GeneratorBigTree.Builder()).minHeight(5).maxHeight(12).foliageHeight(2).leaves(BlockPlanks.EnumType.OAK).create());
        treeGenerator.add("oak", 3, (new GeneratorBasicTree.Builder()).create());
        treeGenerator.add("dead_tree", 1, (new GeneratorBigTree.Builder()).minHeight(5).maxHeight(12).foliageHeight(0).foliageDensity(0.5D).log(BlockPlanks.EnumType.DARK_OAK).leaves(Blocks.AIR.getDefaultState()).create());
        }
        
        GeneratorWeighted grassGen = (GeneratorWeighted)this.getGenerator("grass");
        if (!settings.isEnabled(GeneratorType.GRASSES)) {grassGen.removeGenerator("shortgrass"); grassGen.removeGenerator("mediumgrass"); grassGen.removeGenerator("wheatgrass"); grassGen.removeGenerator("dampgrass");}
    }
    
    @Override
    public int getGrassColorAtPos(BlockPos pos)
    {
        return 0xBCA165;
    }
    
    @Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 0xBCA165;
    }
    
}
