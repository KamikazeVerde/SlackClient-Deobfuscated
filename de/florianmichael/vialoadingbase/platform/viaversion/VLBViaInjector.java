/*    */ package de.florianmichael.vialoadingbase.platform.viaversion;
/*    */ 
/*    */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
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
/*    */ public class VLBViaInjector
/*    */   implements ViaInjector
/*    */ {
/*    */   public void inject() {}
/*    */   
/*    */   public void uninject() {}
/*    */   
/*    */   public String getDecoderName() {
/* 39 */     return "via-decoder";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getEncoderName() {
/* 44 */     return "via-encoder";
/*    */   }
/*    */ 
/*    */   
/*    */   public IntSortedSet getServerProtocolVersions() {
/* 49 */     IntLinkedOpenHashSet intLinkedOpenHashSet = new IntLinkedOpenHashSet();
/* 50 */     for (ProtocolVersion value : ProtocolVersion.getProtocols()) {
/* 51 */       if (value.getVersion() >= ProtocolVersion.v1_7_1.getVersion()) {
/* 52 */         intLinkedOpenHashSet.add(value.getVersion());
/*    */       }
/*    */     } 
/*    */     
/* 56 */     return (IntSortedSet)intLinkedOpenHashSet;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getServerProtocolVersion() {
/* 61 */     return getServerProtocolVersions().firstInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonObject getDump() {
/* 66 */     return new JsonObject();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\platform\viaversion\VLBViaInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */