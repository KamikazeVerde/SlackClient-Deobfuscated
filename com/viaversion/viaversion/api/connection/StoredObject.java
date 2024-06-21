/*    */ package com.viaversion.viaversion.api.connection;
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
/*    */ public abstract class StoredObject
/*    */   implements StorableObject
/*    */ {
/*    */   private final UserConnection user;
/*    */   
/*    */   protected StoredObject(UserConnection user) {
/* 29 */     this.user = user;
/*    */   }
/*    */   
/*    */   public UserConnection getUser() {
/* 33 */     return this.user;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\connection\StoredObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */