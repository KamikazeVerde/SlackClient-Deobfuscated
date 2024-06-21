/*    */ package com.viaversion.viabackwards.api.entities.storage;
/*    */ 
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
/*    */ public final class WrappedMetadata
/*    */ {
/*    */   private final List<Metadata> metadataList;
/*    */   
/*    */   public WrappedMetadata(List<Metadata> metadataList) {
/* 29 */     this.metadataList = metadataList;
/*    */   }
/*    */   
/*    */   public boolean has(Metadata data) {
/* 33 */     return this.metadataList.contains(data);
/*    */   }
/*    */   
/*    */   public void remove(Metadata data) {
/* 37 */     this.metadataList.remove(data);
/*    */   }
/*    */   
/*    */   public void remove(int index) {
/* 41 */     this.metadataList.removeIf(meta -> (meta.id() == index));
/*    */   }
/*    */   
/*    */   public void add(Metadata data) {
/* 45 */     this.metadataList.add(data);
/*    */   }
/*    */   
/*    */   public Metadata get(int index) {
/* 49 */     for (Metadata meta : this.metadataList) {
/* 50 */       if (index == meta.id()) {
/* 51 */         return meta;
/*    */       }
/*    */     } 
/* 54 */     return null;
/*    */   }
/*    */   
/*    */   public List<Metadata> metadataList() {
/* 58 */     return this.metadataList;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "MetaStorage{metaDataList=" + this.metadataList + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\entities\storage\WrappedMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */