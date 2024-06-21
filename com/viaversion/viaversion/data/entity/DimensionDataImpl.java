/*    */ package com.viaversion.viaversion.data.entity;
/*    */ 
/*    */ import com.viaversion.viaversion.api.data.entity.DimensionData;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DimensionDataImpl
/*    */   implements DimensionData
/*    */ {
/*    */   private final int minY;
/*    */   private final int height;
/*    */   
/*    */   public DimensionDataImpl(int minY, int height) {
/* 32 */     this.minY = minY;
/* 33 */     this.height = height;
/*    */   }
/*    */   
/*    */   public DimensionDataImpl(CompoundTag dimensionData) {
/* 37 */     Tag height = dimensionData.get("height");
/* 38 */     if (height instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag) {
/* 39 */       this.height = ((NumberTag)height).asInt();
/*    */     } else {
/* 41 */       throw new IllegalArgumentException("height missing in dimension data: " + dimensionData);
/*    */     } 
/*    */     
/* 44 */     Tag minY = dimensionData.get("min_y");
/* 45 */     if (minY instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag) {
/* 46 */       this.minY = ((NumberTag)minY).asInt();
/*    */     } else {
/* 48 */       throw new IllegalArgumentException("min_y missing in dimension data: " + dimensionData);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int minY() {
/* 54 */     return this.minY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int height() {
/* 59 */     return this.height;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     return "DimensionData{minY=" + this.minY + ", height=" + this.height + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\data\entity\DimensionDataImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */