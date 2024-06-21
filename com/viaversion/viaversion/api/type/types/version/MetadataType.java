/*    */ package com.viaversion.viaversion.api.type.types.version;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes;
/*    */ import com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType;
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
/*    */ public final class MetadataType
/*    */   extends ModernMetaType
/*    */ {
/*    */   private final MetaTypes metaTypes;
/*    */   
/*    */   public MetadataType(MetaTypes metaTypes) {
/* 34 */     this.metaTypes = metaTypes;
/*    */   }
/*    */ 
/*    */   
/*    */   protected MetaType getType(int index) {
/* 39 */     return this.metaTypes.byId(index);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\MetadataType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */