/*    */ package cc.slack.utils.client;
/*    */ 
/*    */ public class ClientInfo {
/*    */   public final String name;
/*    */   public final String version;
/*    */   public final VersionType type;
/*    */   
/*    */   public ClientInfo(String name, String version, VersionType type) {
/*  9 */     this.name = name; this.version = version; this.type = type;
/*    */   }
/* 11 */   public String getName() { return this.name; } public String getVersion() {
/* 12 */     return this.version;
/*    */   }
/*    */   
/*    */   public enum VersionType {
/* 16 */     RELEASE, BETA, DEVELOPER, ALPHA;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getType() {
/* 22 */     return this.type.toString().charAt(0) + this.type.toString().substring(1).toLowerCase();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\client\ClientInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */