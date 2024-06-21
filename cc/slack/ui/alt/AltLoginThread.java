/*    */ package cc.slack.ui.alt;
/*    */ 
/*    */ import cc.slack.utils.client.mc;
/*    */ import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
/*    */ import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
/*    */ import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ChatFormatting;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ public final class AltLoginThread extends Thread {
/*    */   private final String password;
/*    */   private String status;
/*    */   private final String username;
/* 15 */   private final Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public AltLoginThread(String username, String password) {
/* 18 */     super("Alt Login Thread");
/* 19 */     this.username = username;
/* 20 */     this.password = password;
/* 21 */     this.status = ChatFormatting.GRAY + "Waiting...";
/*    */   }
/*    */   
/*    */   private Session createSession(String username, String password) {
/*    */     try {
/* 26 */       MicrosoftAuthResult result = (new MicrosoftAuthenticator()).loginWithCredentials(username, password);
/* 27 */       return new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "microsoft");
/* 28 */     } catch (MicrosoftAuthenticationException e) {
/* 29 */       e.printStackTrace();
/* 30 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getStatus() {
/* 35 */     return this.status;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 40 */     if (this.password.equals("")) {
/* 41 */       this.mc.session = new Session(this.username, "", "", "mojang");
/* 42 */       this.status = ChatFormatting.GREEN + "Logged in. (" + this.username + " - offline name)";
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     this.status = ChatFormatting.YELLOW + "Logging in...";
/* 47 */     Session auth = createSession(this.username, this.password);
/*    */     
/* 49 */     if (auth == null) {
/* 50 */       this.status = ChatFormatting.RED + "Login failed!";
/*    */     } else {
/* 52 */       this.status = ChatFormatting.GREEN + "Logged in as " + auth.getUsername();
/* 53 */       (mc.getMinecraft()).session = auth;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setStatus(String status) {
/* 58 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\alt\AltLoginThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */