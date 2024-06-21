/*    */ package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import java.util.Map;
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
/*    */ public final class DimensionRegistryStorage
/*    */   implements StorableObject
/*    */ {
/*    */   private Map<CompoundTag, String> dimensions;
/*    */   
/*    */   public String dimensionKey(CompoundTag dimensionData) {
/* 30 */     return this.dimensions.get(dimensionData);
/*    */   }
/*    */   
/*    */   public void setDimensions(Map<CompoundTag, String> dimensions) {
/* 34 */     this.dimensions = dimensions;
/*    */   }
/*    */   
/*    */   public Map<CompoundTag, String> dimensions() {
/* 38 */     return this.dimensions;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean clearOnServerSwitch() {
/* 43 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19to1_18_2\storage\DimensionRegistryStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */