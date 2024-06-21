/*     */ package com.viaversion.viaversion.api.minecraft;
/*     */ 
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
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
/*     */ public enum BlockFace
/*     */ {
/*     */   public static final BlockFace[] HORIZONTAL;
/*  29 */   NORTH((byte)0, (byte)0, (byte)-1, EnumAxis.Z),
/*  30 */   SOUTH((byte)0, (byte)0, (byte)1, EnumAxis.Z),
/*  31 */   EAST((byte)1, (byte)0, (byte)0, EnumAxis.X),
/*  32 */   WEST((byte)-1, (byte)0, (byte)0, EnumAxis.X),
/*  33 */   TOP((byte)0, (byte)1, (byte)0, EnumAxis.Y),
/*  34 */   BOTTOM((byte)0, (byte)-1, (byte)0, EnumAxis.Y);
/*     */   static {
/*  36 */     HORIZONTAL = new BlockFace[] { NORTH, SOUTH, EAST, WEST };
/*     */     
/*  38 */     opposites = new EnumMap<>(BlockFace.class);
/*     */ 
/*     */     
/*  41 */     opposites.put(NORTH, SOUTH);
/*  42 */     opposites.put(SOUTH, NORTH);
/*  43 */     opposites.put(EAST, WEST);
/*  44 */     opposites.put(WEST, EAST);
/*  45 */     opposites.put(TOP, BOTTOM);
/*  46 */     opposites.put(BOTTOM, TOP);
/*     */   }
/*     */   private static final Map<BlockFace, BlockFace> opposites;
/*     */   private final byte modX;
/*     */   private final byte modY;
/*     */   private final byte modZ;
/*     */   private final EnumAxis axis;
/*     */   
/*     */   BlockFace(byte modX, byte modY, byte modZ, EnumAxis axis) {
/*  55 */     this.modX = modX;
/*  56 */     this.modY = modY;
/*  57 */     this.modZ = modZ;
/*  58 */     this.axis = axis;
/*     */   }
/*     */   
/*     */   public BlockFace opposite() {
/*  62 */     return opposites.get(this);
/*     */   }
/*     */   
/*     */   public byte modX() {
/*  66 */     return this.modX;
/*     */   }
/*     */   
/*     */   public byte modY() {
/*  70 */     return this.modY;
/*     */   }
/*     */   
/*     */   public byte modZ() {
/*  74 */     return this.modZ;
/*     */   }
/*     */   
/*     */   public EnumAxis axis() {
/*  78 */     return this.axis;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public byte getModX() {
/*  83 */     return this.modX;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public byte getModY() {
/*  88 */     return this.modY;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public byte getModZ() {
/*  93 */     return this.modZ;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public EnumAxis getAxis() {
/*  98 */     return this.axis;
/*     */   }
/*     */   
/*     */   public enum EnumAxis {
/* 102 */     X, Y, Z;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\BlockFace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */