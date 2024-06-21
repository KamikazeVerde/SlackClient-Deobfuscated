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
/*    */ public class ComparableProtocolVersion
/*    */   extends ProtocolVersion
/*    */ {
/*    */   private final int index;
/*    */   
/*    */   public ComparableProtocolVersion(int version, String name, int index) {
/* 27 */     super(version, name);
/* 28 */     this.index = index;
/*    */   }
/*    */   
/*    */   public boolean isOlderThan(ProtocolVersion other) {
/* 32 */     return (getIndex() > ViaLoadingBase.fromProtocolVersion(other).getIndex());
/*    */   }
/*    */   
/*    */   public boolean isOlderThanOrEqualTo(ProtocolVersion other) {
/* 36 */     return (getIndex() >= ViaLoadingBase.fromProtocolVersion(other).getIndex());
/*    */   }
/*    */   
/*    */   public boolean isNewerThan(ProtocolVersion other) {
/* 40 */     return (getIndex() < ViaLoadingBase.fromProtocolVersion(other).getIndex());
/*    */   }
/*    */   
/*    */   public boolean isNewerThanOrEqualTo(ProtocolVersion other) {
/* 44 */     return (getIndex() <= ViaLoadingBase.fromProtocolVersion(other).getIndex());
/*    */   }
/*    */   
/*    */   public boolean isEqualTo(ProtocolVersion other) {
/* 48 */     return (getIndex() == ViaLoadingBase.fromProtocolVersion(other).getIndex());
/*    */   }
/*    */   
/*    */   public boolean is113Plus() {
/* 52 */     return (getIndex() <= ViaLoadingBase.fromProtocolVersion(ProtocolVersion.v1_13).getIndex());
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 56 */     return this.index;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\model\ComparableProtocolVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */