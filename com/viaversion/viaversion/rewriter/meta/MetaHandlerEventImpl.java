/*    */ package com.viaversion.viaversion.rewriter.meta;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
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
/*    */ public class MetaHandlerEventImpl
/*    */   implements MetaHandlerEvent
/*    */ {
/*    */   private final UserConnection connection;
/*    */   private final EntityType entityType;
/*    */   private final int entityId;
/*    */   private final List<Metadata> metadataList;
/*    */   private final Metadata meta;
/*    */   private List<Metadata> extraData;
/*    */   private boolean cancel;
/*    */   
/*    */   public MetaHandlerEventImpl(UserConnection connection, EntityType entityType, int entityId, Metadata meta, List<Metadata> metadataList) {
/* 39 */     this.connection = connection;
/* 40 */     this.entityType = entityType;
/* 41 */     this.entityId = entityId;
/* 42 */     this.meta = meta;
/* 43 */     this.metadataList = metadataList;
/*    */   }
/*    */ 
/*    */   
/*    */   public UserConnection user() {
/* 48 */     return this.connection;
/*    */   }
/*    */ 
/*    */   
/*    */   public int entityId() {
/* 53 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType entityType() {
/* 58 */     return this.entityType;
/*    */   }
/*    */ 
/*    */   
/*    */   public Metadata meta() {
/* 63 */     return this.meta;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 68 */     this.cancel = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean cancelled() {
/* 73 */     return this.cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public Metadata metaAtIndex(int index) {
/* 78 */     for (Metadata meta : this.metadataList) {
/* 79 */       if (index == meta.id()) {
/* 80 */         return meta;
/*    */       }
/*    */     } 
/* 83 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Metadata> metadataList() {
/* 88 */     return Collections.unmodifiableList(this.metadataList);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Metadata> extraMeta() {
/* 93 */     return this.extraData;
/*    */   }
/*    */ 
/*    */   
/*    */   public void createExtraMeta(Metadata metadata) {
/* 98 */     ((this.extraData != null) ? this.extraData : (this.extraData = new ArrayList<>())).add(metadata);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\meta\MetaHandlerEventImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */