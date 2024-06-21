/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.optifine.BlockPosM;
/*     */ 
/*     */ public class Iterator3d
/*     */   implements Iterator<BlockPos> {
/*     */   private IteratorAxis iteratorAxis;
/*  11 */   private BlockPosM blockPos = new BlockPosM(0, 0, 0);
/*  12 */   private int axis = 0;
/*     */   
/*     */   private int kX;
/*     */   private int kY;
/*     */   private int kZ;
/*     */   private static final int AXIS_X = 0;
/*     */   private static final int AXIS_Y = 1;
/*     */   private static final int AXIS_Z = 2;
/*     */   
/*     */   public Iterator3d(BlockPos posStart, BlockPos posEnd, int width, int height) {
/*  22 */     boolean flag = (posStart.getX() > posEnd.getX());
/*  23 */     boolean flag1 = (posStart.getY() > posEnd.getY());
/*  24 */     boolean flag2 = (posStart.getZ() > posEnd.getZ());
/*  25 */     posStart = reverseCoord(posStart, flag, flag1, flag2);
/*  26 */     posEnd = reverseCoord(posEnd, flag, flag1, flag2);
/*  27 */     this.kX = flag ? -1 : 1;
/*  28 */     this.kY = flag1 ? -1 : 1;
/*  29 */     this.kZ = flag2 ? -1 : 1;
/*  30 */     Vec3 vec3 = new Vec3((posEnd.getX() - posStart.getX()), (posEnd.getY() - posStart.getY()), (posEnd.getZ() - posStart.getZ()));
/*  31 */     Vec3 vec31 = vec3.normalize();
/*  32 */     Vec3 vec32 = new Vec3(1.0D, 0.0D, 0.0D);
/*  33 */     double d0 = vec31.dotProduct(vec32);
/*  34 */     double d1 = Math.abs(d0);
/*  35 */     Vec3 vec33 = new Vec3(0.0D, 1.0D, 0.0D);
/*  36 */     double d2 = vec31.dotProduct(vec33);
/*  37 */     double d3 = Math.abs(d2);
/*  38 */     Vec3 vec34 = new Vec3(0.0D, 0.0D, 1.0D);
/*  39 */     double d4 = vec31.dotProduct(vec34);
/*  40 */     double d5 = Math.abs(d4);
/*     */     
/*  42 */     if (d5 >= d3 && d5 >= d1) {
/*     */       
/*  44 */       this.axis = 2;
/*  45 */       BlockPos blockpos3 = new BlockPos(posStart.getZ(), posStart.getY() - width, posStart.getX() - height);
/*  46 */       BlockPos blockpos5 = new BlockPos(posEnd.getZ(), posStart.getY() + width + 1, posStart.getX() + height + 1);
/*  47 */       int k = posEnd.getZ() - posStart.getZ();
/*  48 */       double d9 = (posEnd.getY() - posStart.getY()) / 1.0D * k;
/*  49 */       double d11 = (posEnd.getX() - posStart.getX()) / 1.0D * k;
/*  50 */       this.iteratorAxis = new IteratorAxis(blockpos3, blockpos5, d9, d11);
/*     */     }
/*  52 */     else if (d3 >= d1 && d3 >= d5) {
/*     */       
/*  54 */       this.axis = 1;
/*  55 */       BlockPos blockpos2 = new BlockPos(posStart.getY(), posStart.getX() - width, posStart.getZ() - height);
/*  56 */       BlockPos blockpos4 = new BlockPos(posEnd.getY(), posStart.getX() + width + 1, posStart.getZ() + height + 1);
/*  57 */       int j = posEnd.getY() - posStart.getY();
/*  58 */       double d8 = (posEnd.getX() - posStart.getX()) / 1.0D * j;
/*  59 */       double d10 = (posEnd.getZ() - posStart.getZ()) / 1.0D * j;
/*  60 */       this.iteratorAxis = new IteratorAxis(blockpos2, blockpos4, d8, d10);
/*     */     }
/*     */     else {
/*     */       
/*  64 */       this.axis = 0;
/*  65 */       BlockPos blockpos = new BlockPos(posStart.getX(), posStart.getY() - width, posStart.getZ() - height);
/*  66 */       BlockPos blockpos1 = new BlockPos(posEnd.getX(), posStart.getY() + width + 1, posStart.getZ() + height + 1);
/*  67 */       int i = posEnd.getX() - posStart.getX();
/*  68 */       double d6 = (posEnd.getY() - posStart.getY()) / 1.0D * i;
/*  69 */       double d7 = (posEnd.getZ() - posStart.getZ()) / 1.0D * i;
/*  70 */       this.iteratorAxis = new IteratorAxis(blockpos, blockpos1, d6, d7);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockPos reverseCoord(BlockPos pos, boolean revX, boolean revY, boolean revZ) {
/*  76 */     if (revX)
/*     */     {
/*  78 */       pos = new BlockPos(-pos.getX(), pos.getY(), pos.getZ());
/*     */     }
/*     */     
/*  81 */     if (revY)
/*     */     {
/*  83 */       pos = new BlockPos(pos.getX(), -pos.getY(), pos.getZ());
/*     */     }
/*     */     
/*  86 */     if (revZ)
/*     */     {
/*  88 */       pos = new BlockPos(pos.getX(), pos.getY(), -pos.getZ());
/*     */     }
/*     */     
/*  91 */     return pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  96 */     return this.iteratorAxis.hasNext();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos next() {
/* 101 */     BlockPos blockpos = this.iteratorAxis.next();
/*     */     
/* 103 */     switch (this.axis) {
/*     */       
/*     */       case 0:
/* 106 */         this.blockPos.setXyz(blockpos.getX() * this.kX, blockpos.getY() * this.kY, blockpos.getZ() * this.kZ);
/* 107 */         return (BlockPos)this.blockPos;
/*     */       
/*     */       case 1:
/* 110 */         this.blockPos.setXyz(blockpos.getY() * this.kX, blockpos.getX() * this.kY, blockpos.getZ() * this.kZ);
/* 111 */         return (BlockPos)this.blockPos;
/*     */       
/*     */       case 2:
/* 114 */         this.blockPos.setXyz(blockpos.getZ() * this.kX, blockpos.getY() * this.kY, blockpos.getX() * this.kZ);
/* 115 */         return (BlockPos)this.blockPos;
/*     */     } 
/*     */     
/* 118 */     this.blockPos.setXyz(blockpos.getX() * this.kX, blockpos.getY() * this.kY, blockpos.getZ() * this.kZ);
/* 119 */     return (BlockPos)this.blockPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/* 125 */     throw new RuntimeException("Not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 130 */     BlockPos blockpos = new BlockPos(10, 20, 30);
/* 131 */     BlockPos blockpos1 = new BlockPos(30, 40, 20);
/* 132 */     Iterator3d iterator3d = new Iterator3d(blockpos, blockpos1, 1, 1);
/*     */     
/* 134 */     while (iterator3d.hasNext()) {
/*     */       
/* 136 */       BlockPos blockpos2 = iterator3d.next();
/* 137 */       System.out.println("" + blockpos2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\Iterator3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */