/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.optifine.BlockPosM;
/*     */ 
/*     */ public class IteratorAxis
/*     */   implements Iterator<BlockPos> {
/*     */   private double yDelta;
/*     */   private double zDelta;
/*     */   private int xStart;
/*     */   private int xEnd;
/*     */   private double yStart;
/*     */   private double yEnd;
/*     */   private double zStart;
/*     */   private double zEnd;
/*     */   private int xNext;
/*     */   private double yNext;
/*     */   private double zNext;
/*  21 */   private BlockPosM pos = new BlockPosM(0, 0, 0);
/*     */   
/*     */   private boolean hasNext = false;
/*     */   
/*     */   public IteratorAxis(BlockPos posStart, BlockPos posEnd, double yDelta, double zDelta) {
/*  26 */     this.yDelta = yDelta;
/*  27 */     this.zDelta = zDelta;
/*  28 */     this.xStart = posStart.getX();
/*  29 */     this.xEnd = posEnd.getX();
/*  30 */     this.yStart = posStart.getY();
/*  31 */     this.yEnd = posEnd.getY() - 0.5D;
/*  32 */     this.zStart = posStart.getZ();
/*  33 */     this.zEnd = posEnd.getZ() - 0.5D;
/*  34 */     this.xNext = this.xStart;
/*  35 */     this.yNext = this.yStart;
/*  36 */     this.zNext = this.zStart;
/*  37 */     this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  42 */     return this.hasNext;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos next() {
/*  47 */     if (!this.hasNext)
/*     */     {
/*  49 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*  53 */     this.pos.setXyz(this.xNext, this.yNext, this.zNext);
/*  54 */     nextPos();
/*  55 */     this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
/*  56 */     return (BlockPos)this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextPos() {
/*  62 */     this.zNext++;
/*     */     
/*  64 */     if (this.zNext >= this.zEnd) {
/*     */       
/*  66 */       this.zNext = this.zStart;
/*  67 */       this.yNext++;
/*     */       
/*  69 */       if (this.yNext >= this.yEnd) {
/*     */         
/*  71 */         this.yNext = this.yStart;
/*  72 */         this.yStart += this.yDelta;
/*  73 */         this.yEnd += this.yDelta;
/*  74 */         this.yNext = this.yStart;
/*  75 */         this.zStart += this.zDelta;
/*  76 */         this.zEnd += this.zDelta;
/*  77 */         this.zNext = this.zStart;
/*  78 */         this.xNext++;
/*     */         
/*  80 */         if (this.xNext >= this.xEnd);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/*  90 */     throw new RuntimeException("Not implemented");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  95 */     BlockPos blockpos = new BlockPos(-2, 10, 20);
/*  96 */     BlockPos blockpos1 = new BlockPos(2, 12, 22);
/*  97 */     double d0 = -0.5D;
/*  98 */     double d1 = 0.5D;
/*  99 */     IteratorAxis iteratoraxis = new IteratorAxis(blockpos, blockpos1, d0, d1);
/* 100 */     System.out.println("Start: " + blockpos + ", end: " + blockpos1 + ", yDelta: " + d0 + ", zDelta: " + d1);
/*     */     
/* 102 */     while (iteratoraxis.hasNext()) {
/*     */       
/* 104 */       BlockPos blockpos2 = iteratoraxis.next();
/* 105 */       System.out.println("" + blockpos2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\IteratorAxis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */