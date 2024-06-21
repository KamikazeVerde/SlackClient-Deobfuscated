/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Animations", category = Category.RENDER)
/*    */ public class Animations
/*    */   extends Module
/*    */ {
/* 16 */   public final ModeValue<String> blockStyle = new ModeValue("Block Animation", (Object[])new String[] { "1.7", "1.8", "Slide" });
/*    */   
/*    */   public Animations() {
/* 19 */     addSettings(new Value[] { (Value)this.blockStyle });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\Animations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */