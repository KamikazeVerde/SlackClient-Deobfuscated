/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ClassInheritanceMultiMap;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ 
/*     */ public class ChunkVisibility
/*     */ {
/*     */   public static final int MASK_FACINGS = 63;
/*  20 */   public static final EnumFacing[][] enumFacingArrays = makeEnumFacingArrays(false);
/*  21 */   public static final EnumFacing[][] enumFacingOppositeArrays = makeEnumFacingArrays(true);
/*  22 */   private static int counter = 0;
/*  23 */   private static int iMaxStatic = -1;
/*  24 */   private static int iMaxStaticFinal = 16;
/*  25 */   private static World worldLast = null;
/*  26 */   private static int pcxLast = Integer.MIN_VALUE;
/*  27 */   private static int pczLast = Integer.MIN_VALUE;
/*     */ 
/*     */   
/*     */   public static int getMaxChunkY(World world, Entity viewEntity, int renderDistanceChunks) {
/*  31 */     int i = MathHelper.floor_double(viewEntity.posX) >> 4;
/*  32 */     int j = MathHelper.floor_double(viewEntity.posY) >> 4;
/*  33 */     int k = MathHelper.floor_double(viewEntity.posZ) >> 4;
/*  34 */     Chunk chunk = world.getChunkFromChunkCoords(i, k);
/*  35 */     int l = i - renderDistanceChunks;
/*  36 */     int i1 = i + renderDistanceChunks;
/*  37 */     int j1 = k - renderDistanceChunks;
/*  38 */     int k1 = k + renderDistanceChunks;
/*     */     
/*  40 */     if (world != worldLast || i != pcxLast || k != pczLast) {
/*     */       
/*  42 */       counter = 0;
/*  43 */       iMaxStaticFinal = 16;
/*  44 */       worldLast = world;
/*  45 */       pcxLast = i;
/*  46 */       pczLast = k;
/*     */     } 
/*     */     
/*  49 */     if (counter == 0)
/*     */     {
/*  51 */       iMaxStatic = -1;
/*     */     }
/*     */     
/*  54 */     int l1 = iMaxStatic;
/*     */     
/*  56 */     switch (counter) {
/*     */       
/*     */       case 0:
/*  59 */         i1 = i;
/*  60 */         k1 = k;
/*     */         break;
/*     */       
/*     */       case 1:
/*  64 */         l = i;
/*  65 */         k1 = k;
/*     */         break;
/*     */       
/*     */       case 2:
/*  69 */         i1 = i;
/*  70 */         j1 = k;
/*     */         break;
/*     */       
/*     */       case 3:
/*  74 */         l = i;
/*  75 */         j1 = k;
/*     */         break;
/*     */     } 
/*  78 */     for (int i2 = l; i2 < i1; i2++) {
/*     */       
/*  80 */       for (int j2 = j1; j2 < k1; j2++) {
/*     */         
/*  82 */         Chunk chunk1 = world.getChunkFromChunkCoords(i2, j2);
/*     */         
/*  84 */         if (!chunk1.isEmpty()) {
/*     */           
/*  86 */           ExtendedBlockStorage[] aextendedblockstorage = chunk1.getBlockStorageArray();
/*     */           
/*  88 */           for (int k2 = aextendedblockstorage.length - 1; k2 > l1; k2--) {
/*     */             
/*  90 */             ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[k2];
/*     */             
/*  92 */             if (extendedblockstorage != null && !extendedblockstorage.isEmpty()) {
/*     */               
/*  94 */               if (k2 > l1)
/*     */               {
/*  96 */                 l1 = k2;
/*     */               }
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           
/*     */           try {
/* 105 */             Map<BlockPos, TileEntity> map = chunk1.getTileEntityMap();
/*     */             
/* 107 */             if (!map.isEmpty())
/*     */             {
/* 109 */               for (BlockPos blockpos : map.keySet())
/*     */               {
/* 111 */                 int l2 = blockpos.getY() >> 4;
/*     */                 
/* 113 */                 if (l2 > l1)
/*     */                 {
/* 115 */                   l1 = l2;
/*     */                 }
/*     */               }
/*     */             
/*     */             }
/* 120 */           } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 125 */           ClassInheritanceMultiMap[] arrayOfClassInheritanceMultiMap = chunk1.getEntityLists();
/*     */           
/* 127 */           for (int i3 = arrayOfClassInheritanceMultiMap.length - 1; i3 > l1; i3--) {
/*     */             
/* 129 */             ClassInheritanceMultiMap<Entity> classinheritancemultimap1 = arrayOfClassInheritanceMultiMap[i3];
/*     */             
/* 131 */             if (!classinheritancemultimap1.isEmpty() && (chunk1 != chunk || i3 != j || classinheritancemultimap1.size() != 1)) {
/*     */               
/* 133 */               if (i3 > l1)
/*     */               {
/* 135 */                 l1 = i3;
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     if (counter < 3) {
/*     */       
/* 147 */       iMaxStatic = l1;
/* 148 */       l1 = iMaxStaticFinal;
/*     */     }
/*     */     else {
/*     */       
/* 152 */       iMaxStaticFinal = l1;
/* 153 */       iMaxStatic = -1;
/*     */     } 
/*     */     
/* 156 */     counter = (counter + 1) % 4;
/* 157 */     return l1 << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFinished() {
/* 162 */     return (counter == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumFacing[][] makeEnumFacingArrays(boolean opposite) {
/* 167 */     int i = 64;
/* 168 */     EnumFacing[][] aenumfacing = new EnumFacing[i][];
/*     */     
/* 170 */     for (int j = 0; j < i; j++) {
/*     */       
/* 172 */       List<EnumFacing> list = new ArrayList<>();
/*     */       
/* 174 */       for (int k = 0; k < EnumFacing.VALUES.length; k++) {
/*     */         
/* 176 */         EnumFacing enumfacing = EnumFacing.VALUES[k];
/* 177 */         EnumFacing enumfacing1 = opposite ? enumfacing.getOpposite() : enumfacing;
/* 178 */         int l = 1 << enumfacing1.ordinal();
/*     */         
/* 180 */         if ((j & l) != 0)
/*     */         {
/* 182 */           list.add(enumfacing);
/*     */         }
/*     */       } 
/*     */       
/* 186 */       EnumFacing[] aenumfacing1 = list.<EnumFacing>toArray(new EnumFacing[list.size()]);
/* 187 */       aenumfacing[j] = aenumfacing1;
/*     */     } 
/*     */     
/* 190 */     return aenumfacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing[] getFacingsNotOpposite(int setDisabled) {
/* 195 */     int i = (setDisabled ^ 0xFFFFFFFF) & 0x3F;
/* 196 */     return enumFacingOppositeArrays[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 201 */     worldLast = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\render\ChunkVisibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */