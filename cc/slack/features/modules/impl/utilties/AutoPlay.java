/*    */ package cc.slack.features.modules.impl.utilties;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.play.server.S02PacketChat;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "AutoPlay", category = Category.UTILITIES)
/*    */ public class AutoPlay
/*    */   extends Module
/*    */ {
/* 21 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Hypixel", "Universocraft", "Librecraft" });
/* 22 */   private final ModeValue<String> univalue = new ModeValue("Universocraft", (Object[])new String[] { "Skywars", "Bedwars" });
/*    */ 
/*    */ 
/*    */   
/*    */   public AutoPlay() {
/* 27 */     addSettings(new Value[] { (Value)this.mode, (Value)this.univalue });
/*    */   }
/*    */   
/*    */   private void process(IChatComponent chatComponent) {
/* 31 */     String value = chatComponent.getChatStyle().getChatClickEvent().getValue();
/* 32 */     if (value != null && value.startsWith("/play") && !value.startsWith("/play skyblock")) {
/* 33 */       mc.getPlayer().sendChatMessage(value);
/*    */     } else {
/* 35 */       for (IChatComponent component : chatComponent.getSiblings()) {
/* 36 */         process(component);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 43 */     if (!(event.getPacket() instanceof S02PacketChat))
/*    */       return; 
/* 45 */     IChatComponent chatComponent = ((S02PacketChat)event.getPacket()).getChatComponent();
/* 46 */     String unformattedText = chatComponent.getUnformattedText();
/*    */     
/* 48 */     switch ((String)this.mode.getValue()) {
/*    */       case "Hypixel":
/* 50 */         process(chatComponent);
/*    */         break;
/*    */       case "Universocraft":
/* 53 */         if (unformattedText.contains("Jugar de nuevo") || unformattedText.contains("Ha ganado")) {
/* 54 */           switch ((String)this.univalue.getValue()) {
/*    */             case "Skywars":
/* 56 */               mc.getPlayer().sendChatMessage("/skywars random");
/*    */               break;
/*    */             case "Bedwars":
/* 59 */               mc.getPlayer().sendChatMessage("/bedwars random");
/*    */               break;
/*    */           } 
/*    */         }
/*    */         break;
/*    */       case "Librecraft":
/* 65 */         if (unformattedText.contains("Â¡Partida finalizada!"))
/* 66 */           mc.getPlayer().sendChatMessage("/saliryentrar"); 
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\imp\\utilties\AutoPlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */