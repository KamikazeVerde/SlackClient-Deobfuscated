/*     */ package cc.slack.utils.other;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.player.RotationUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockUtils extends mc {
/*     */   public Block getBlock(Vec3 vec3) {
/*  22 */     return getBlock(new BlockPos(vec3));
/*     */   }
/*     */   public static Block getBlock(BlockPos blockPos) {
/*  25 */     if (mc.getWorld() != null && blockPos != null) {
/*  26 */       return mc.getWorld().getBlockState(blockPos).getBlock();
/*     */     }
/*  28 */     return null;
/*     */   }
/*     */   
/*     */   public static Material getMaterial(BlockPos blockPos) {
/*  32 */     Block block = getBlock(blockPos);
/*  33 */     if (block != null) {
/*  34 */       return block.getMaterial();
/*     */     }
/*  36 */     return null;
/*     */   }
/*     */   
/*     */   public static float getHardness(BlockPos blockPos) {
/*  40 */     return getBlock(blockPos).getPlayerRelativeBlockHardness((EntityPlayer)mc.getPlayer(), (World)mc.getWorld(), blockPos);
/*     */   }
/*     */   
/*     */   public static boolean isReplaceable(BlockPos blockPos) {
/*  44 */     Material material = getMaterial(blockPos);
/*  45 */     return (material != null && material.isReplaceable());
/*     */   }
/*     */   
/*     */   public static boolean isReplaceableNotBed(BlockPos blockPos) {
/*  49 */     Material material = getMaterial(blockPos);
/*  50 */     return (material != null && material.isReplaceable() && !(getBlock(blockPos) instanceof net.minecraft.block.BlockBed));
/*     */   }
/*     */   
/*     */   public static boolean isAir(BlockPos blockPos) {
/*  54 */     Material material = getMaterial(blockPos);
/*  55 */     return (material == Material.air);
/*     */   }
/*     */   
/*     */   public static IBlockState getState(BlockPos blockPos) {
/*  59 */     WorldClient worldClient = (Minecraft.getMinecraft()).theWorld;
/*  60 */     return worldClient.getBlockState(blockPos);
/*     */   }
/*     */   
/*     */   public static boolean canBeClicked(BlockPos blockPos) {
/*  64 */     return (getBlock(blockPos) != null && ((Block)Objects.<Block>requireNonNull(getBlock(blockPos))).canCollideCheck(getState(blockPos), false) && 
/*  65 */       mc.getWorld().getWorldBorder().contains(blockPos));
/*     */   }
/*     */   
/*     */   public static String getBlockName(int id) {
/*  69 */     return Block.getBlockById(id).getLocalizedName();
/*     */   }
/*     */   
/*     */   public static boolean isFullBlock(BlockPos blockPos) {
/*  73 */     AxisAlignedBB axisAlignedBB = (getBlock(blockPos) != null) ? getBlock(blockPos).getCollisionBoundingBox((World)mc.getWorld(), blockPos, getState(blockPos)) : null;
/*  74 */     if (axisAlignedBB == null) return false; 
/*  75 */     return (axisAlignedBB.maxX - axisAlignedBB.minX == 1.0D && axisAlignedBB.maxY - axisAlignedBB.minY == 1.0D && axisAlignedBB.maxZ - axisAlignedBB.minZ == 1.0D);
/*     */   }
/*     */   
/*     */   public static double getCenterDistance(BlockPos blockPos) {
/*  79 */     return mc.getPlayer().getDistance(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D);
/*     */   }
/*     */   
/*     */   public static float[] getCenterRotation(BlockPos blockPos) {
/*  83 */     return RotationUtil.getRotations(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D);
/*     */   }
/*     */   
/*     */   public static float[] getFaceRotation(EnumFacing face, BlockPos blockPos) {
/*  87 */     Vec3i faceVec = face.getDirectionVec();
/*  88 */     Vec3 blockFaceVec = new Vec3(faceVec.getX() * 0.5D, faceVec.getY() * 0.5D, faceVec.getZ() * 0.5D);
/*  89 */     blockFaceVec.add(blockPos.toVec3());
/*  90 */     blockFaceVec.addVector(0.5D, 0.5D, 0.5D);
/*  91 */     return RotationUtil.getRotations(blockFaceVec);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAbsoluteValue(BlockPos blockPos) {
/*  96 */     return blockPos.getX() + blockPos.getY() * 1000 + blockPos.getZ() * 200000;
/*     */   }
/*     */   
/*     */   public static EnumFacing getHorizontalFacingEnum(BlockPos blockPos) {
/* 100 */     double dx = (mc.getPlayer()).posX - blockPos.getX() + 0.5D;
/* 101 */     double dz = (mc.getPlayer()).posZ - blockPos.getZ() + 0.5D;
/*     */     
/* 103 */     if (dx > 0.0D) {
/* 104 */       if (dz > dx)
/* 105 */         return EnumFacing.SOUTH; 
/* 106 */       if (-dz > dx) {
/* 107 */         return EnumFacing.NORTH;
/*     */       }
/* 109 */       return EnumFacing.EAST;
/*     */     } 
/*     */     
/* 112 */     if (dz > -dx)
/* 113 */       return EnumFacing.SOUTH; 
/* 114 */     if (dz < dx) {
/* 115 */       return EnumFacing.NORTH;
/*     */     }
/* 117 */     return EnumFacing.WEST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<BlockPos, Block> searchBlocks(int radius) {
/* 123 */     Map<BlockPos, Block> blocks = new HashMap<>();
/*     */     
/* 125 */     for (int x = radius; x >= -radius + 1; x--) {
/* 126 */       for (int y = radius; y >= -radius + 1; y--) {
/* 127 */         for (int z = radius; z >= -radius + 1; z--) {
/* 128 */           BlockPos blockPos = new BlockPos((mc.getPlayer()).posX + x, (mc.getPlayer()).posY + y, (mc.getPlayer()).posZ + z);
/* 129 */           Block block = getBlock(blockPos);
/* 130 */           if (block != null) {
/* 131 */             blocks.put(blockPos, block);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return blocks;
/*     */   }
/*     */   
/*     */   public static boolean collideBlock(AxisAlignedBB axisAlignedBB, Function<Block, Boolean> collide) {
/* 141 */     int x = MathHelper.floor_double((mc.getPlayer().getEntityBoundingBox()).minX); for (; x < 
/* 142 */       MathHelper.floor_double((mc.getPlayer().getEntityBoundingBox()).maxX) + 1; x++) {
/* 143 */       int z = MathHelper.floor_double((mc.getPlayer().getEntityBoundingBox()).minZ); for (; z < 
/* 144 */         MathHelper.floor_double((mc.getPlayer().getEntityBoundingBox()).maxZ) + 1; z++) {
/* 145 */         Block block = getBlock(new BlockPos(x, axisAlignedBB.minY, z));
/*     */         
/* 147 */         if (!((Boolean)collide.apply(block)).booleanValue()) {
/* 148 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 152 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean collideBlockIntersects(AxisAlignedBB axisAlignedBB, Predicate<Block> collide) {
/* 156 */     int x = MathHelper.floor_double((mc.getPlayer().getEntityBoundingBox()).minX); for (; x < 
/* 157 */       MathHelper.floor_double((mc.getPlayer().getEntityBoundingBox()).maxX) + 1; x++) {
/* 158 */       int z = MathHelper.floor_double((mc.getPlayer().getEntityBoundingBox()).minZ); for (; z < 
/* 159 */         MathHelper.floor_double((mc.getPlayer().getEntityBoundingBox()).maxZ) + 1; z++) {
/* 160 */         BlockPos blockPos = new BlockPos(x, axisAlignedBB.minY, z);
/* 161 */         Block block = getBlock(blockPos);
/*     */         
/* 163 */         if (collide.test(block)) {
/* 164 */           AxisAlignedBB boundingBox = (block != null) ? block.getCollisionBoundingBox((World)mc.getWorld(), blockPos, getState(blockPos)) : null;
/* 165 */           if (boundingBox != null)
/*     */           {
/* 167 */             if (mc.getPlayer().getEntityBoundingBox().intersectsWith(boundingBox))
/* 168 */               return true; 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */   public static Vec3 floorVec3(Vec3 vec3) {
/* 176 */     return new Vec3(Math.floor(vec3.xCoord), Math.floor(vec3.yCoord), Math.floor(vec3.zCoord));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\other\BlockUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */