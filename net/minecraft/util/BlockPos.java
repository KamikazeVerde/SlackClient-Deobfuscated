/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPos
/*     */   extends Vec3i
/*     */ {
/*  12 */   public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
/*  13 */   private static final int NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
/*  14 */   private static final int NUM_Z_BITS = NUM_X_BITS;
/*  15 */   private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
/*  16 */   private static final int Y_SHIFT = 0 + NUM_Z_BITS;
/*  17 */   private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
/*  18 */   private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
/*  19 */   private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
/*  20 */   private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;
/*     */ 
/*     */   
/*     */   public BlockPos(int x, int y, int z) {
/*  24 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(double x, double y, double z) {
/*  29 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Entity source) {
/*  34 */     this(source.posX, source.posY, source.posZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Vec3 source) {
/*  39 */     this(source.xCoord, source.yCoord, source.zCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Vec3i source) {
/*  44 */     this(source.getX(), source.getY(), source.getZ());
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
/*     */   public BlockPos add(double x, double y, double z) {
/*  56 */     return (x == 0.0D && y == 0.0D && z == 0.0D) ? this : new BlockPos(getX() + x, getY() + y, getZ() + z);
/*     */   }
/*     */   
/*     */   public Vec3 toVec3() {
/*  60 */     return new Vec3(getX(), getY(), getZ());
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
/*     */   public BlockPos add(int x, int y, int z) {
/*  72 */     return (x == 0 && y == 0 && z == 0) ? this : new BlockPos(getX() + x, getY() + y, getZ() + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(Vec3i vec) {
/*  80 */     return (vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0) ? this : new BlockPos(getX() + vec.getX(), getY() + vec.getY(), getZ() + vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos subtract(Vec3i vec) {
/*  88 */     return (vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0) ? this : new BlockPos(getX() - vec.getX(), getY() - vec.getY(), getZ() - vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos up() {
/*  96 */     return up(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos up(int n) {
/* 104 */     return offset(EnumFacing.UP, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos down() {
/* 112 */     return down(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos down(int n) {
/* 120 */     return offset(EnumFacing.DOWN, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos north() {
/* 128 */     return north(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos north(int n) {
/* 136 */     return offset(EnumFacing.NORTH, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos south() {
/* 144 */     return south(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos south(int n) {
/* 152 */     return offset(EnumFacing.SOUTH, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos west() {
/* 160 */     return west(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos west(int n) {
/* 168 */     return offset(EnumFacing.WEST, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos east() {
/* 176 */     return east(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos east(int n) {
/* 184 */     return offset(EnumFacing.EAST, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing) {
/* 192 */     return offset(facing, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing, int n) {
/* 203 */     return (n == 0) ? this : new BlockPos(getX() + facing.getFrontOffsetX() * n, getY() + facing.getFrontOffsetY() * n, getZ() + facing.getFrontOffsetZ() * n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos crossProduct(Vec3i vec) {
/* 211 */     return new BlockPos(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toLong() {
/* 219 */     return (getX() & X_MASK) << X_SHIFT | (getY() & Y_MASK) << Y_SHIFT | (getZ() & Z_MASK) << 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos fromLong(long serialized) {
/* 227 */     int i = (int)(serialized << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
/* 228 */     int j = (int)(serialized << 64 - Y_SHIFT - NUM_Y_BITS >> 64 - NUM_Y_BITS);
/* 229 */     int k = (int)(serialized << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);
/* 230 */     return new BlockPos(i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
/* 235 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 236 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 237 */     return new Iterable<BlockPos>()
/*     */       {
/*     */         public Iterator<BlockPos> iterator()
/*     */         {
/* 241 */           return (Iterator<BlockPos>)new AbstractIterator<BlockPos>()
/*     */             {
/* 243 */               private BlockPos lastReturned = null;
/*     */               
/*     */               protected BlockPos computeNext() {
/* 246 */                 if (this.lastReturned == null) {
/*     */                   
/* 248 */                   this.lastReturned = blockpos;
/* 249 */                   return this.lastReturned;
/*     */                 } 
/* 251 */                 if (this.lastReturned.equals(blockpos1))
/*     */                 {
/* 253 */                   return (BlockPos)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 257 */                 int i = this.lastReturned.getX();
/* 258 */                 int j = this.lastReturned.getY();
/* 259 */                 int k = this.lastReturned.getZ();
/*     */                 
/* 261 */                 if (i < blockpos1.getX()) {
/*     */                   
/* 263 */                   i++;
/*     */                 }
/* 265 */                 else if (j < blockpos1.getY()) {
/*     */                   
/* 267 */                   i = blockpos.getX();
/* 268 */                   j++;
/*     */                 }
/* 270 */                 else if (k < blockpos1.getZ()) {
/*     */                   
/* 272 */                   i = blockpos.getX();
/* 273 */                   j = blockpos.getY();
/* 274 */                   k++;
/*     */                 } 
/*     */                 
/* 277 */                 this.lastReturned = new BlockPos(i, j, k);
/* 278 */                 return this.lastReturned;
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterable<MutableBlockPos> getAllInBoxMutable(BlockPos from, BlockPos to) {
/* 288 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 289 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 290 */     return new Iterable<MutableBlockPos>()
/*     */       {
/*     */         public Iterator<BlockPos.MutableBlockPos> iterator()
/*     */         {
/* 294 */           return (Iterator<BlockPos.MutableBlockPos>)new AbstractIterator<BlockPos.MutableBlockPos>()
/*     */             {
/* 296 */               private BlockPos.MutableBlockPos theBlockPos = null;
/*     */               
/*     */               protected BlockPos.MutableBlockPos computeNext() {
/* 299 */                 if (this.theBlockPos == null) {
/*     */                   
/* 301 */                   this.theBlockPos = new BlockPos.MutableBlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/* 302 */                   return this.theBlockPos;
/*     */                 } 
/* 304 */                 if (this.theBlockPos.equals(blockpos1))
/*     */                 {
/* 306 */                   return (BlockPos.MutableBlockPos)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 310 */                 int i = this.theBlockPos.getX();
/* 311 */                 int j = this.theBlockPos.getY();
/* 312 */                 int k = this.theBlockPos.getZ();
/*     */                 
/* 314 */                 if (i < blockpos1.getX()) {
/*     */                   
/* 316 */                   i++;
/*     */                 }
/* 318 */                 else if (j < blockpos1.getY()) {
/*     */                   
/* 320 */                   i = blockpos.getX();
/* 321 */                   j++;
/*     */                 }
/* 323 */                 else if (k < blockpos1.getZ()) {
/*     */                   
/* 325 */                   i = blockpos.getX();
/* 326 */                   j = blockpos.getY();
/* 327 */                   k++;
/*     */                 } 
/*     */                 
/* 330 */                 this.theBlockPos.x = i;
/* 331 */                 this.theBlockPos.y = j;
/* 332 */                 this.theBlockPos.z = k;
/* 333 */                 return this.theBlockPos;
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class MutableBlockPos
/*     */     extends BlockPos
/*     */   {
/*     */     private int x;
/*     */     private int y;
/*     */     private int z;
/*     */     
/*     */     public MutableBlockPos() {
/* 349 */       this(0, 0, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos(int x_, int y_, int z_) {
/* 354 */       super(0, 0, 0);
/* 355 */       this.x = x_;
/* 356 */       this.y = y_;
/* 357 */       this.z = z_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getX() {
/* 362 */       return this.x;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getY() {
/* 367 */       return this.y;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getZ() {
/* 372 */       return this.z;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos func_181079_c(int p_181079_1_, int p_181079_2_, int p_181079_3_) {
/* 377 */       this.x = p_181079_1_;
/* 378 */       this.y = p_181079_2_;
/* 379 */       this.z = p_181079_3_;
/* 380 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\BlockPos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */