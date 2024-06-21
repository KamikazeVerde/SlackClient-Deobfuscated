/*    */ package com.viaversion.viaversion.api.minecraft;
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
/*    */ public class VillagerData
/*    */ {
/*    */   private final int type;
/*    */   private final int profession;
/*    */   private final int level;
/*    */   
/*    */   public VillagerData(int type, int profession, int level) {
/* 31 */     this.type = type;
/* 32 */     this.profession = profession;
/* 33 */     this.level = level;
/*    */   }
/*    */   
/*    */   public int type() {
/* 37 */     return this.type;
/*    */   }
/*    */   
/*    */   public int profession() {
/* 41 */     return this.profession;
/*    */   }
/*    */   
/*    */   public int level() {
/* 45 */     return this.level;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getType() {
/* 50 */     return this.type;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getProfession() {
/* 55 */     return this.profession;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getLevel() {
/* 60 */     return this.level;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\VillagerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */