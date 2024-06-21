/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Ambience", category = Category.RENDER)
/*    */ public class Ambience
/*    */   extends Module
/*    */ {
/* 23 */   public ModeValue<String> timemode = new ModeValue("Time", (Object[])new String[] { "None", "Sun", "Night", "Custom" });
/* 24 */   private final NumberValue<Integer> customtime = new NumberValue("Custom Time", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(24), Integer.valueOf(1));
/* 25 */   public ModeValue<String> weathermode = new ModeValue("Weather", (Object[])new String[] { "None", "Clear", "Rain", "Thunder" });
/* 26 */   private final NumberValue<Float> weatherstrength = new NumberValue("Weather Strength", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0F), Float.valueOf(0.01F));
/*    */ 
/*    */ 
/*    */   
/*    */   public Ambience() {
/* 31 */     addSettings(new Value[] { (Value)this.timemode, (Value)this.customtime, (Value)this.weathermode, (Value)this.weatherstrength });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 37 */     switch ((String)this.timemode.getValue()) {
/*    */       case "Sun":
/* 39 */         mc.getWorld().setWorldTime(6000L);
/*    */         break;
/*    */       case "Night":
/* 42 */         mc.getWorld().setWorldTime(15000L);
/*    */         break;
/*    */       case "Custom":
/* 45 */         mc.getWorld().setWorldTime((((Integer)this.customtime.getValue()).intValue() * 1000));
/*    */         break;
/*    */     } 
/*    */     
/* 49 */     switch ((String)this.weathermode.getValue()) {
/*    */       case "Clear":
/* 51 */         mc.getWorld().setRainStrength(0.0F);
/* 52 */         mc.getWorld().setThunderStrength(0.0F);
/*    */         break;
/*    */       case "Rain":
/* 55 */         mc.getWorld().setRainStrength(((Float)this.weatherstrength.getValue()).floatValue());
/* 56 */         mc.getWorld().setThunderStrength(0.0F);
/*    */         break;
/*    */       case "Thunder":
/* 59 */         mc.getWorld().setRainStrength(((Float)this.weatherstrength.getValue()).floatValue());
/* 60 */         mc.getWorld().setThunderStrength(((Float)this.weatherstrength.getValue()).floatValue());
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 67 */     if (!((String)this.timemode.getValue()).contains("None") && event.getPacket() instanceof net.minecraft.network.play.server.S03PacketTimeUpdate) {
/* 68 */       event.cancel();
/*    */     }
/*    */     
/* 71 */     if (!((String)this.weathermode.getValue()).contains("None") && event.getPacket() instanceof S2BPacketChangeGameState && (
/* 72 */       (S2BPacketChangeGameState)event.getPacket()).getGameState() >= 7 && ((S2BPacketChangeGameState)event.getPacket()).getGameState() <= 8)
/* 73 */       event.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\Ambience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */