/*    */ package com.viaversion.viaversion.api.minecraft.metadata.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractMetaTypes
/*    */   implements MetaTypes
/*    */ {
/*    */   private final MetaType[] values;
/*    */   
/*    */   protected AbstractMetaTypes(int values) {
/* 33 */     this.values = new MetaType[values];
/*    */   }
/*    */ 
/*    */   
/*    */   public MetaType byId(int id) {
/* 38 */     return this.values[id];
/*    */   }
/*    */ 
/*    */   
/*    */   public MetaType[] values() {
/* 43 */     return this.values;
/*    */   }
/*    */   
/*    */   protected MetaType add(int typeId, Type<?> type) {
/* 47 */     MetaType metaType = MetaType.create(typeId, type);
/* 48 */     this.values[typeId] = metaType;
/* 49 */     return metaType;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\types\AbstractMetaTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */