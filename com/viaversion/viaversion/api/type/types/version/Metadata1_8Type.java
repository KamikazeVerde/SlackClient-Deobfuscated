/*    */ package com.viaversion.viaversion.api.type.types.version;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*    */ import com.viaversion.viaversion.api.type.types.minecraft.OldMetaType;
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
/*    */ 
/*    */ public class Metadata1_8Type
/*    */   extends OldMetaType
/*    */ {
/*    */   protected MetaType getType(int index) {
/* 33 */     return (MetaType)MetaType1_8.byId(index);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\Metadata1_8Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */