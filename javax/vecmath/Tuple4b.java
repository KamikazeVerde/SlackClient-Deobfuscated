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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Tuple4b
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -8226727741811898211L;
/*     */   public byte x;
/*     */   public byte y;
/*     */   public byte z;
/*     */   public byte w;
/*     */   
/*     */   public Tuple4b(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
/*  84 */     this.x = paramByte1;
/*  85 */     this.y = paramByte2;
/*  86 */     this.z = paramByte3;
/*  87 */     this.w = paramByte4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b(byte[] paramArrayOfbyte) {
/*  97 */     this.x = paramArrayOfbyte[0];
/*  98 */     this.y = paramArrayOfbyte[1];
/*  99 */     this.z = paramArrayOfbyte[2];
/* 100 */     this.w = paramArrayOfbyte[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b(Tuple4b paramTuple4b) {
/* 110 */     this.x = paramTuple4b.x;
/* 111 */     this.y = paramTuple4b.y;
/* 112 */     this.z = paramTuple4b.z;
/* 113 */     this.w = paramTuple4b.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b() {
/* 122 */     this.x = 0;
/* 123 */     this.y = 0;
/* 124 */     this.z = 0;
/* 125 */     this.w = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 135 */     return "(" + (this.x & 0xFF) + ", " + (this.y & 0xFF) + ", " + (this.z & 0xFF) + ", " + (this.w & 0xFF) + ")";
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
/* 149 */     paramArrayOfbyte[0] = this.x;
/* 150 */     paramArrayOfbyte[1] = this.y;
/* 151 */     paramArrayOfbyte[2] = this.z;
/* 152 */     paramArrayOfbyte[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4b paramTuple4b) {
/* 163 */     paramTuple4b.x = this.x;
/* 164 */     paramTuple4b.y = this.y;
/* 165 */     paramTuple4b.z = this.z;
/* 166 */     paramTuple4b.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4b paramTuple4b) {
/* 177 */     this.x = paramTuple4b.x;
/* 178 */     this.y = paramTuple4b.y;
/* 179 */     this.z = paramTuple4b.z;
/* 180 */     this.w = paramTuple4b.w;
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
/* 191 */     this.x = paramArrayOfbyte[0];
/* 192 */     this.y = paramArrayOfbyte[1];
/* 193 */     this.z = paramArrayOfbyte[2];
/* 194 */     this.w = paramArrayOfbyte[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Tuple4b paramTuple4b) {
/*     */     try {
/* 206 */       return (this.x == paramTuple4b.x && this.y == paramTuple4b.y && this.z == paramTuple4b.z && this.w == paramTuple4b.w);
/*     */     } catch (NullPointerException nullPointerException) {
/*     */       
/* 209 */       return false;
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
/* 222 */     try { Tuple4b tuple4b = (Tuple4b)paramObject;
/* 223 */       return (this.x == tuple4b.x && this.y == tuple4b.y && this.z == tuple4b.z && this.w == tuple4b.w); }
/*     */     catch (NullPointerException nullPointerException)
/*     */     
/* 226 */     { return false; }
/* 227 */     catch (ClassCastException classCastException) { return false; }
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
/*     */   
/*     */   public int hashCode() {
/* 241 */     return (this.x & 0xFF) << 0 | (this.y & 0xFF) << 8 | (this.z & 0xFF) << 16 | (this.w & 0xFF) << 24;
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
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 258 */       return super.clone();
/* 259 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 261 */       throw new InternalError();
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
/* 274 */     return this.x;
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
/* 286 */     this.x = paramByte;
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
/* 298 */     return this.y;
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
/* 310 */     this.y = paramByte;
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
/* 321 */     return this.z;
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
/* 333 */     this.z = paramByte;
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
/*     */   public final byte getW() {
/* 345 */     return this.w;
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
/*     */   public final void setW(byte paramByte) {
/* 357 */     this.w = paramByte;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple4b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */