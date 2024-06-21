/*    */ package cc.slack.features.modules.impl.utilties;
/*    */ 
/*    */ import cc.slack.events.impl.game.TickEvent;
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.impl.StringValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.other.TimeUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S02PacketChat;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "AutoLogin", category = Category.UTILITIES)
/*    */ public class AutoLogin
/*    */   extends Module
/*    */ {
/* 24 */   private final StringValue password = new StringValue("Password", "DglaMaska13");
/*    */   
/*    */   private String text;
/*    */   private final TimeUtil timeUtil;
/*    */   
/*    */   public AutoLogin() {
/* 30 */     this.timeUtil = new TimeUtil();
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 35 */     Packet packet = event.getPacket();
/* 36 */     if (packet instanceof S02PacketChat) {
/* 37 */       S02PacketChat s02PacketChat = (S02PacketChat)packet;
/* 38 */       String text = s02PacketChat.getChatComponent().getUnformattedText();
/* 39 */       if (StringUtils.containsIgnoreCase(text, "/register") || StringUtils.containsIgnoreCase(text, "/register password password") || text.equalsIgnoreCase("/register <password> <password>")) {
/* 40 */         this.text = "/register " + (String)this.password.getValue() + " " + (String)this.password.getValue();
/* 41 */         this.timeUtil.reset();
/*    */       }
/* 43 */       else if (StringUtils.containsIgnoreCase(text, "/login password") || StringUtils.containsIgnoreCase(text, "/login") || text.equalsIgnoreCase("/login <password>")) {
/* 44 */         this.text = "/login " + (String)this.password.getValue();
/* 45 */         this.timeUtil.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onTick(TickEvent event) {
/* 53 */     if (this.timeUtil.hasReached(1500L) && this.text != null && !this.text.equals("")) {
/* 54 */       mc.getPlayer().sendChatMessage(this.text);
/* 55 */       System.out.println(this.text);
/* 56 */       this.text = "";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\imp\\utilties\AutoLogin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */