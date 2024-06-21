/*    */ package cc.slack.features.modules.impl.other;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import net.minecraft.network.play.server.S02PacketChat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Killsults", category = Category.OTHER)
/*    */ public class Killsults
/*    */   extends Module
/*    */ {
/* 21 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Universocraft" });
/*    */ 
/*    */   
/*    */   public Killsults() {
/* 25 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 30 */     if (event.getPacket() instanceof S02PacketChat) {
/* 31 */       S02PacketChat packet = (S02PacketChat)event.getPacket();
/* 32 */       String message = packet.getChatComponent().getUnformattedText();
/* 33 */       switch ((String)this.mode.getValue()) {
/*    */         case "Universocraft":
/* 35 */           if ((message.contains(mc.getPlayer().getNameClear()) && message.contains("fue brutalmente asesinado por")) || (message.contains(mc.getPlayer().getNameClear()) && message.contains("fue empujado al vacÃ­o por")) || (message.contains(mc.getPlayer().getNameClear()) && message.contains("no resistiÃ³ los ataques de")) || (message.contains(mc.getPlayer().getNameClear()) && message.contains("pensÃ³ que era un buen momento de morir a manos de")) || (message.contains(mc.getPlayer().getNameClear()) && message.contains("ha sido asesinado por"))) {
/* 36 */             sendInsult();
/*    */           }
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendInsult() {
/* 47 */     String[] insultslist = { "U r a piece of yyshit", "Slack > All minecraft clients", "Do you know how to use your hands?", "DG chews me", "Bro, he is using FDP" };
/* 48 */     int randomIndex = ThreadLocalRandom.current().nextInt(0, insultslist.length);
/* 49 */     mc.getPlayer().sendChatMessage(insultslist[randomIndex]);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\other\Killsults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */