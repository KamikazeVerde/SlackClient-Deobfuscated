/*    */ package cc.slack.utils.other;
/*    */ 
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public final class PrintUtil extends mc {
/*    */   public static void print(String message) {
/*  9 */     System.out.println("[Slack] " + message);
/*    */   }
/*    */   
/*    */   public static void debugMessage(String message) {
/* 13 */     getPlayer().addChatMessage((IChatComponent)new ChatComponentText("Â§f[Â§cDEBUGÂ§f] Â§e" + message));
/*    */   }
/*    */   
/*    */   public static void message(String message) {
/* 17 */     getPlayer().addChatMessage((IChatComponent)new ChatComponentText("Â§cSlack Â§eÂ» Â§f" + message));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\other\PrintUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */