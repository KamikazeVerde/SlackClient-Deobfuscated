/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.ChunkCoordIntPair;
/*    */ 
/*    */ public class ChunkPosComparator
/*    */   implements Comparator<ChunkCoordIntPair>
/*    */ {
/*    */   private int chunkPosX;
/*    */   private int chunkPosZ;
/*    */   private double yawRad;
/*    */   private double pitchNorm;
/*    */   
/*    */   public ChunkPosComparator(int chunkPosX, int chunkPosZ, double yawRad, double pitchRad) {
/* 16 */     this.chunkPosX = chunkPosX;
/* 17 */     this.chunkPosZ = chunkPosZ;
/* 18 */     this.yawRad = yawRad;
/* 19 */     this.pitchNorm = 1.0D - MathHelper.clamp_double(Math.abs(pitchRad) / 1.5707963267948966D, 0.0D, 1.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(ChunkCoordIntPair cp1, ChunkCoordIntPair cp2) {
/* 24 */     int i = getDistSq(cp1);
/* 25 */     int j = getDistSq(cp2);
/* 26 */     return i - j;
/*    */   }
/*    */ 
/*    */   
/*    */   private int getDistSq(ChunkCoordIntPair cp) {
/* 31 */     int i = cp.chunkXPos - this.chunkPosX;
/* 32 */     int j = cp.chunkZPos - this.chunkPosZ;
/* 33 */     int k = i * i + j * j;
/* 34 */     double d0 = MathHelper.func_181159_b(j, i);
/* 35 */     double d1 = Math.abs(d0 - this.yawRad);
/*    */     
/* 37 */     if (d1 > Math.PI)
/*    */     {
/* 39 */       d1 = 6.283185307179586D - d1;
/*    */     }
/*    */     
/* 42 */     k = (int)(k * 1000.0D * this.pitchNorm * d1 * d1);
/* 43 */     return k;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\ChunkPosComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */