/*    */ package com.viaversion.viabackwards.api.data;
/*    */ 
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
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
/*    */ public class MappedItem
/*    */ {
/*    */   private final int id;
/*    */   private final String jsonName;
/*    */   private final Integer customModelData;
/*    */   
/*    */   public MappedItem(int id, String name) {
/* 30 */     this(id, name, null);
/*    */   }
/*    */   
/*    */   public MappedItem(int id, String name, Integer customModelData) {
/* 34 */     this.id = id;
/* 35 */     this.jsonName = ChatRewriter.legacyTextToJsonString("§f" + name, true);
/* 36 */     this.customModelData = customModelData;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getJsonName() {
/* 44 */     return this.jsonName;
/*    */   }
/*    */   
/*    */   public Integer customModelData() {
/* 48 */     return this.customModelData;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\data\MappedItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */