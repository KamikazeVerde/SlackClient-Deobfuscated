/*     */ package com.viaversion.viaversion.api.minecraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Position
/*     */ {
/*     */   protected final int x;
/*     */   protected final int y;
/*     */   protected final int z;
/*     */   
/*     */   public Position(int x, int y, int z) {
/*  31 */     this.x = x;
/*  32 */     this.y = y;
/*  33 */     this.z = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public Position(int x, short y, int z) {
/*  38 */     this(x, y, z);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Position(Position toCopy) {
/*  43 */     this(toCopy.x(), toCopy.y(), toCopy.z());
/*     */   }
/*     */   
/*     */   public Position getRelative(BlockFace face) {
/*  47 */     return new Position(this.x + face.modX(), (short)(this.y + face.modY()), this.z + face.modZ());
/*     */   }
/*     */   
/*     */   public int x() {
/*  51 */     return this.x;
/*     */   }
/*     */   
/*     */   public int y() {
/*  55 */     return this.y;
/*     */   }
/*     */   
/*     */   public int z() {
/*  59 */     return this.z;
/*     */   }
/*     */   
/*     */   public GlobalPosition withDimension(String dimension) {
/*  63 */     return new GlobalPosition(dimension, this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int getX() {
/*  68 */     return this.x;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int getY() {
/*  73 */     return this.y;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int getZ() {
/*  78 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  83 */     if (this == o) return true; 
/*  84 */     if (o == null || getClass() != o.getClass()) return false; 
/*  85 */     Position position = (Position)o;
/*  86 */     if (this.x != position.x) return false; 
/*  87 */     if (this.y != position.y) return false; 
/*  88 */     return (this.z == position.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  93 */     int result = this.x;
/*  94 */     result = 31 * result + this.y;
/*  95 */     result = 31 * result + this.z;
/*  96 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "Position{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\Position.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */