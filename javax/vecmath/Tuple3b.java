/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public abstract class Tuple3b
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -483782685323607044L;
/*     */   public byte x;
/*     */   public byte y;
/*     */   public byte z;
/*     */   
/*     */   public Tuple3b(byte paramByte1, byte paramByte2, byte paramByte3) {
/*  78 */     this.x = paramByte1;
/*  79 */     this.y = paramByte2;
/*  80 */     this.z = paramByte3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3b(byte[] paramArrayOfbyte) {
/*  90 */     this.x = paramArrayOfbyte[0];
/*  91 */     this.y = paramArrayOfbyte[1];
/*  92 */     this.z = paramArrayOfbyte[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3b(Tuple3b paramTuple3b) {
/* 102 */     this.x = paramTuple3b.x;
/* 103 */     this.y = paramTuple3b.y;
/* 104 */     this.z = paramTuple3b.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3b() {
/* 113 */     this.x = 0;
/* 114 */     this.y = 0;
/* 115 */     this.z = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 125 */     return "(" + (this.x & 0xFF) + ", " + (this.y & 0xFF) + ", " + (this.z & 0xFF) + ")";
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
/*     */ 
/*     */   
/*     */   public final void get(byte[] paramArrayOfbyte) {
/* 139 */     paramArrayOfbyte[0] = this.x;
/* 140 */     paramArrayOfbyte[1] = this.y;
/* 141 */     paramArrayOfbyte[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3b paramTuple3b) {
/* 152 */     paramTuple3b.x = this.x;
/* 153 */     paramTuple3b.y = this.y;
/* 154 */     paramTuple3b.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3b paramTuple3b) {
/* 165 */     this.x = paramTuple3b.x;
/* 166 */     this.y = paramTuple3b.y;
/* 167 */     this.z = paramTuple3b.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(byte[] paramArrayOfbyte) {
/* 178 */     this.x = paramArrayOfbyte[0];
/* 179 */     this.y = paramArrayOfbyte[1];
/* 180 */     this.z = paramArrayOfbyte[2];
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
/*     */   public boolean equals(Tuple3b paramTuple3b) {
/*     */     try {
/* 193 */       return (this.x == paramTuple3b.x && this.y == paramTuple3b.y && this.z == paramTuple3b.z);
/*     */     } catch (NullPointerException nullPointerException) {
/* 195 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*     */     
/* 208 */     try { Tuple3b tuple3b = (Tuple3b)paramObject;
/* 209 */       return (this.x == tuple3b.x && this.y == tuple3b.y && this.z == tuple3b.z); }
/*     */     catch (NullPointerException nullPointerException)
/* 211 */     { return false; }
/* 212 */     catch (ClassCastException classCastException) { return false; }
/*     */   
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
/*     */   public int hashCode() {
/* 225 */     return (this.x & 0xFF) << 0 | (this.y & 0xFF) << 8 | (this.z & 0xFF) << 16;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 241 */       return super.clone();
/* 242 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 244 */       throw new InternalError();
/*     */     } 
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
/*     */   public final byte getX() {
/* 257 */     return this.x;
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
/*     */   public final void setX(byte paramByte) {
/* 269 */     this.x = paramByte;
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
/*     */   public final byte getY() {
/* 281 */     return this.y;
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
/*     */   public final void setY(byte paramByte) {
/* 293 */     this.y = paramByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getZ() {
/* 304 */     return this.z;
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
/*     */   public final void setZ(byte paramByte) {
/* 316 */     this.z = paramByte;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple3b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */