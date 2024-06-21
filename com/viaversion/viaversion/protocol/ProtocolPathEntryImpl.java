/*    */ package com.viaversion.viaversion.protocol;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
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
/*    */ public class ProtocolPathEntryImpl
/*    */   implements ProtocolPathEntry
/*    */ {
/*    */   private final int outputProtocolVersion;
/*    */   private final Protocol<?, ?, ?, ?> protocol;
/*    */   
/*    */   public ProtocolPathEntryImpl(int outputProtocolVersion, Protocol<?, ?, ?, ?> protocol) {
/* 28 */     this.outputProtocolVersion = outputProtocolVersion;
/* 29 */     this.protocol = protocol;
/*    */   }
/*    */ 
/*    */   
/*    */   public int outputProtocolVersion() {
/* 34 */     return this.outputProtocolVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public Protocol<?, ?, ?, ?> protocol() {
/* 39 */     return this.protocol;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 44 */     if (this == o) return true; 
/* 45 */     if (o == null || getClass() != o.getClass()) return false; 
/* 46 */     ProtocolPathEntryImpl that = (ProtocolPathEntryImpl)o;
/* 47 */     if (this.outputProtocolVersion != that.outputProtocolVersion) return false; 
/* 48 */     return this.protocol.equals(that.protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 53 */     int result = this.outputProtocolVersion;
/* 54 */     result = 31 * result + this.protocol.hashCode();
/* 55 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return "ProtocolPathEntryImpl{outputProtocolVersion=" + this.outputProtocolVersion + ", protocol=" + this.protocol + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocol\ProtocolPathEntryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */