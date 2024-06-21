/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import java.util.List;
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
/*    */ public abstract class MetaListTypeTemplate
/*    */   extends Type<List<Metadata>>
/*    */ {
/*    */   protected MetaListTypeTemplate() {
/* 32 */     super("MetaData List", List.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends Type> getBaseClass() {
/* 37 */     return (Class)MetaListTypeTemplate.class;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\MetaListTypeTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */