/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.events.impl.render.RenderScoreboard;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Scoreboard", category = Category.RENDER)
/*    */ public class ScoreboardModule
/*    */   extends Module
/*    */ {
/* 18 */   public final BooleanValue noscoreboard = new BooleanValue("No Scoreboard", false);
/* 19 */   private final NumberValue<Double> x = new NumberValue("PosX", Double.valueOf(0.0D), Double.valueOf(-1000.0D), Double.valueOf(1000.0D), Double.valueOf(0.1D));
/* 20 */   private final NumberValue<Double> y = new NumberValue("PosY", Double.valueOf(30.0D), Double.valueOf(-1000.0D), Double.valueOf(1000.0D), Double.valueOf(0.1D));
/*    */ 
/*    */   
/*    */   public ScoreboardModule() {
/* 24 */     addSettings(new Value[] { (Value)this.noscoreboard, (Value)this.x, (Value)this.y });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onRenderScoreboard(RenderScoreboard event) {
/* 30 */     if (event.isPre()) {
/* 31 */       GlStateManager.translate(-((Double)this.x.getValue()).doubleValue(), ((Double)this.y.getValue()).doubleValue(), 1.0D);
/*    */     } else {
/*    */       
/* 34 */       GlStateManager.translate(((Double)this.x.getValue()).doubleValue(), -((Double)this.y.getValue()).doubleValue(), 1.0D);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\ScoreboardModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */