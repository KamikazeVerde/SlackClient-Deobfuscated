/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
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
/*    */ public class StatisticData
/*    */ {
/*    */   private final int categoryId;
/*    */   private final int newId;
/*    */   private final int value;
/*    */   
/*    */   public StatisticData(int categoryId, int newId, int value) {
/* 27 */     this.categoryId = categoryId;
/* 28 */     this.newId = newId;
/* 29 */     this.value = value;
/*    */   }
/*    */   
/*    */   public int getCategoryId() {
/* 33 */     return this.categoryId;
/*    */   }
/*    */   
/*    */   public int getNewId() {
/* 37 */     return this.newId;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 41 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\StatisticData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */