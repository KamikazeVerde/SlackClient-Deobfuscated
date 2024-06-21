/*    */ package com.viaversion.viaversion.rewriter.meta;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
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
/*    */ public interface MetaHandlerEvent
/*    */ {
/*    */   UserConnection user();
/*    */   
/*    */   int entityId();
/*    */   
/*    */   EntityType entityType();
/*    */   
/*    */   default int index() {
/* 55 */     return meta().id();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void setIndex(int index) {
/* 64 */     meta().setId(index);
/*    */   }
/*    */   
/*    */   Metadata meta();
/*    */   
/*    */   void cancel();
/*    */   
/*    */   boolean cancelled();
/*    */   
/*    */   Metadata metaAtIndex(int paramInt);
/*    */   
/*    */   List<Metadata> metadataList();
/*    */   
/*    */   List<Metadata> extraMeta();
/*    */   
/*    */   void createExtraMeta(Metadata paramMetadata);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\meta\MetaHandlerEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */