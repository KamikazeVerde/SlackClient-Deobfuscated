/*    */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.provider;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
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
/*    */ public class PlayerAbilitiesProvider
/*    */   implements Provider
/*    */ {
/*    */   public float getFlyingSpeed(UserConnection connection) {
/* 26 */     return 0.05F;
/*    */   }
/*    */   
/*    */   public float getWalkingSpeed(UserConnection connection) {
/* 30 */     return 0.1F;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\provider\PlayerAbilitiesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */