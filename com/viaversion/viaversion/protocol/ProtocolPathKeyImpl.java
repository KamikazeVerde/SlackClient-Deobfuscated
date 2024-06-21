/*    */ package com.viaversion.viaversion.protocol;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.ProtocolPathKey;
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
/*    */ public class ProtocolPathKeyImpl
/*    */   implements ProtocolPathKey
/*    */ {
/*    */   private final int clientProtocolVersion;
/*    */   private final int serverProtocolVersion;
/*    */   
/*    */   public ProtocolPathKeyImpl(int clientProtocolVersion, int serverProtocolVersion) {
/* 27 */     this.clientProtocolVersion = clientProtocolVersion;
/* 28 */     this.serverProtocolVersion = serverProtocolVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public int clientProtocolVersion() {
/* 33 */     return this.clientProtocolVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public int serverProtocolVersion() {
/* 38 */     return this.serverProtocolVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 43 */     if (this == o) return true; 
/* 44 */     if (o == null || getClass() != o.getClass()) return false; 
/* 45 */     ProtocolPathKeyImpl that = (ProtocolPathKeyImpl)o;
/* 46 */     if (this.clientProtocolVersion != that.clientProtocolVersion) return false; 
/* 47 */     return (this.serverProtocolVersion == that.serverProtocolVersion);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     int result = this.clientProtocolVersion;
/* 53 */     result = 31 * result + this.serverProtocolVersion;
/* 54 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocol\ProtocolPathKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */