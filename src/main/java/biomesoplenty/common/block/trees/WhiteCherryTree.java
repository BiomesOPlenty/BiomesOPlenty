package biomesoplenty.common.block.trees;

import biomesoplenty.common.world.gen.feature.BOPBiomeFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class WhiteCherryTree extends Tree
{
   @Nullable
   protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random)
   {
      return (AbstractTreeFeature<NoFeatureConfig>)(random.nextInt(10) == 0 ? BOPBiomeFeatures.BIG_WHITE_CHERRY_TREE : BOPBiomeFeatures.WHITE_CHERRY_TREE);
   }
}