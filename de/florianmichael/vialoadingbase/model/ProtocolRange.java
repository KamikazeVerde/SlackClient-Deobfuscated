/*    */ package de.florianmichael.vialoadingbase.model;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
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
/*    */ public class ProtocolRange
/*    */ {
/*    */   private final ComparableProtocolVersion lowerBound;
/*    */   private final ComparableProtocolVersion upperBound;
/*    */   
/*    */   public ProtocolRange(ProtocolVersion lowerBound, ProtocolVersion upperBound) {
/* 28 */     this(ViaLoadingBase.fromProtocolVersion(lowerBound), ViaLoadingBase.fromProtocolVersion(upperBound));
/*    */   }
/*    */   
/*    */   public ProtocolRange(ComparableProtocolVersion lowerBound, ComparableProtocolVersion upperBound) {
/* 32 */     if (lowerBound == null && upperBound == null) {
/* 33 */       throw new RuntimeException("Invalid protocol range");
/*    */     }
/* 35 */     this.lowerBound = lowerBound;
/* 36 */     this.upperBound = upperBound;
/*    */   }
/*    */   
/*    */   public static ProtocolRange andNewer(ProtocolVersion version) {
/* 40 */     return new ProtocolRange(null, version);
/*    */   }
/*    */   
/*    */   public static ProtocolRange singleton(ProtocolVersion version) {
/* 44 */     return new ProtocolRange(version, version);
/*    */   }
/*    */   
/*    */   public static ProtocolRange andOlder(ProtocolVersion version) {
/* 48 */     return new ProtocolRange(version, null);
/*    */   }
/*    */   
/*    */   public boolean contains(ComparableProtocolVersion protocolVersion) {
/* 52 */     if (this.lowerBound != null && protocolVersion.getIndex() < this.lowerBound.getIndex()) return false;
/*    */     
/* 54 */     return !(this.upperBound != null && protocolVersion.getIndex() > this.upperBound.getIndex());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     if (this.lowerBound == null) return String.valueOf(this.upperBound.getName()) + "+"; 
/* 60 */     if (this.upperBound == null) return String.valueOf(this.lowerBound.getName()) + "-"; 
/* 61 */     if (this.lowerBound == this.upperBound) return this.lowerBound.getName();
/*    */     
/* 63 */     return String.valueOf(this.lowerBound.getName()) + " - " + this.upperBound.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\model\ProtocolRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */