/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Cape", category = Category.RENDER)
/*    */ public class Cape
/*    */   extends Module
/*    */ {
/* 18 */   private final ModeValue<String> capes = new ModeValue("Cape", (Object[])new String[] { "Slack", "Rise6" });
/*    */   
/*    */   public Cape() {
/* 21 */     addSettings(new Value[] { (Value)this.capes });
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 26 */     switch ((String)this.capes.getValue()) {
/*    */       case "Slack":
/* 28 */         mc.getPlayer().setLocationOfCape(new ResourceLocation("slack/capes/slack.png"));
/*    */         break;
/*    */       case "Rise6":
/* 31 */         mc.getPlayer().setLocationOfCape(new ResourceLocation("slack/capes/rise6.png"));
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 38 */     mc.getPlayer().setLocationOfCape(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\Cape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */