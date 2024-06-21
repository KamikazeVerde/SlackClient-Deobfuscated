/*     */ package net.optifine;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3i;
/*     */ 
/*     */ public class BlockPosM
/*     */   extends BlockPos
/*     */ {
/*     */   private int mx;
/*     */   private int my;
/*     */   private int mz;
/*     */   private int level;
/*     */   private BlockPosM[] facings;
/*     */   private boolean needsUpdate;
/*     */   
/*     */   public BlockPosM(int x, int y, int z) {
/*  21 */     this(x, y, z, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM(double xIn, double yIn, double zIn) {
/*  26 */     this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM(int x, int y, int z, int level) {
/*  31 */     super(0, 0, 0);
/*  32 */     this.mx = x;
/*  33 */     this.my = y;
/*  34 */     this.mz = z;
/*  35 */     this.level = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  43 */     return this.mx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  51 */     return this.my;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  59 */     return this.mz;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXyz(int x, int y, int z) {
/*  64 */     this.mx = x;
/*  65 */     this.my = y;
/*  66 */     this.mz = z;
/*  67 */     this.needsUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXyz(double xIn, double yIn, double zIn) {
/*  72 */     setXyz(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM set(Vec3i vec) {
/*  77 */     setXyz(vec.getX(), vec.getY(), vec.getZ());
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM set(int xIn, int yIn, int zIn) {
/*  83 */     setXyz(xIn, yIn, zIn);
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos offsetMutable(EnumFacing facing) {
/*  89 */     return offset(facing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing) {
/*  97 */     if (this.level <= 0)
/*     */     {
/*  99 */       return super.offset(facing, 1);
/*     */     }
/*     */ 
/*     */     
/* 103 */     if (this.facings == null)
/*     */     {
/* 105 */       this.facings = new BlockPosM[EnumFacing.VALUES.length];
/*     */     }
/*     */     
/* 108 */     if (this.needsUpdate)
/*     */     {
/* 110 */       update();
/*     */     }
/*     */     
/* 113 */     int i = facing.getIndex();
/* 114 */     BlockPosM blockposm = this.facings[i];
/*     */     
/* 116 */     if (blockposm == null) {
/*     */       
/* 118 */       int j = this.mx + facing.getFrontOffsetX();
/* 119 */       int k = this.my + facing.getFrontOffsetY();
/* 120 */       int l = this.mz + facing.getFrontOffsetZ();
/* 121 */       blockposm = new BlockPosM(j, k, l, this.level - 1);
/* 122 */       this.facings[i] = blockposm;
/*     */     } 
/*     */     
/* 125 */     return blockposm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing, int n) {
/* 137 */     return (n == 1) ? offset(facing) : super.offset(facing, n);
/*     */   }
/*     */ 
/*     */   
/*     */   private void update() {
/* 142 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 144 */       BlockPosM blockposm = this.facings[i];
/*     */       
/* 146 */       if (blockposm != null) {
/*     */         
/* 148 */         EnumFacing enumfacing = EnumFacing.VALUES[i];
/* 149 */         int j = this.mx + enumfacing.getFrontOffsetX();
/* 150 */         int k = this.my + enumfacing.getFrontOffsetY();
/* 151 */         int l = this.mz + enumfacing.getFrontOffsetZ();
/* 152 */         blockposm.setXyz(j, k, l);
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     this.needsUpdate = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos toImmutable() {
/* 161 */     return new BlockPos(this.mx, this.my, this.mz);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable getAllInBoxMutable(BlockPos from, BlockPos to) {
/* 166 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 167 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 168 */     return new Iterable()
/*     */       {
/*     */         public Iterator iterator()
/*     */         {
/* 172 */           return (Iterator)new AbstractIterator()
/*     */             {
/* 174 */               private BlockPosM theBlockPosM = null;
/*     */               
/*     */               protected BlockPosM computeNext0() {
/* 177 */                 if (this.theBlockPosM == null) {
/*     */                   
/* 179 */                   this.theBlockPosM = new BlockPosM(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 3);
/* 180 */                   return this.theBlockPosM;
/*     */                 } 
/* 182 */                 if (this.theBlockPosM.equals(blockpos1))
/*     */                 {
/* 184 */                   return (BlockPosM)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 188 */                 int i = this.theBlockPosM.getX();
/* 189 */                 int j = this.theBlockPosM.getY();
/* 190 */                 int k = this.theBlockPosM.getZ();
/*     */                 
/* 192 */                 if (i < blockpos1.getX()) {
/*     */                   
/* 194 */                   i++;
/*     */                 }
/* 196 */                 else if (j < blockpos1.getY()) {
/*     */                   
/* 198 */                   i = blockpos.getX();
/* 199 */                   j++;
/*     */                 }
/* 201 */                 else if (k < blockpos1.getZ()) {
/*     */                   
/* 203 */                   i = blockpos.getX();
/* 204 */                   j = blockpos.getY();
/* 205 */                   k++;
/*     */                 } 
/*     */                 
/* 208 */                 this.theBlockPosM.setXyz(i, j, k);
/* 209 */                 return this.theBlockPosM;
/*     */               }
/*     */ 
/*     */               
/*     */               protected Object computeNext() {
/* 214 */                 return computeNext0();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\BlockPosM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */