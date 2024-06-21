/*    */ package fr.litarvan.openauth.microsoft;
/*    */ 
/*    */ import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
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
/*    */ public class MicrosoftAuthResult
/*    */ {
/*    */   private final MinecraftProfile profile;
/*    */   private final String accessToken;
/*    */   private final String refreshToken;
/*    */   private final String xuid;
/*    */   private final String clientId;
/*    */   
/*    */   public MicrosoftAuthResult(MinecraftProfile profile, String accessToken, String refreshToken, String xuid, String clientId) {
/* 44 */     this.profile = profile;
/* 45 */     this.accessToken = accessToken;
/* 46 */     this.refreshToken = refreshToken;
/* 47 */     this.xuid = xuid;
/* 48 */     this.clientId = clientId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MinecraftProfile getProfile() {
/* 56 */     return this.profile;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAccessToken() {
/* 64 */     return this.accessToken;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRefreshToken() {
/* 73 */     return this.refreshToken;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getXuid() {
/* 81 */     return this.xuid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getClientId() {
/* 89 */     return this.clientId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\fr\litarvan\openauth\microsoft\MicrosoftAuthResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */