/*    */ package com.viaversion.viaversion.api.type.types.version;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
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
/*    */ public class Metadata1_9Type
/*    */   extends ModernMetaType
/*    */ {
/*    */   protected MetaType getType(int index) {
/* 32 */     return (MetaType)MetaType1_9.byId(index);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\Metadata1_9Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */