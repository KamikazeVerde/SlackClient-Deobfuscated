/*    */ package com.viaversion.viaversion.api.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets;
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
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
/*    */ public interface ViaInjector
/*    */ {
/*    */   void inject() throws Exception;
/*    */   
/*    */   void uninject() throws Exception;
/*    */   
/*    */   default boolean lateProtocolVersionSetting() {
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   int getServerProtocolVersion() throws Exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default IntSortedSet getServerProtocolVersions() throws Exception {
/* 73 */     return IntSortedSets.singleton(getServerProtocolVersion());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default String getEncoderName() {
/* 82 */     return "via-encoder";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default String getDecoderName() {
/* 91 */     return "via-decoder";
/*    */   }
/*    */   
/*    */   JsonObject getDump();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\platform\ViaInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */