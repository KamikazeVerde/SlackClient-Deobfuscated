/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
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
/*    */ public abstract class BaseChunkBulkType
/*    */   extends Type<Chunk[]>
/*    */ {
/*    */   protected BaseChunkBulkType() {
/* 31 */     super(Chunk[].class);
/*    */   }
/*    */   
/*    */   protected BaseChunkBulkType(String typeName) {
/* 35 */     super(typeName, Chunk[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends Type> getBaseClass() {
/* 40 */     return (Class)BaseChunkBulkType.class;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\BaseChunkBulkType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */