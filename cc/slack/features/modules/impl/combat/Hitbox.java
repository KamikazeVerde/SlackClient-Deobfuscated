/*    */ package cc.slack.features.modules.impl.combat;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Hitbox", category = Category.COMBAT)
/*    */ public class Hitbox
/*    */   extends Module
/*    */ {
/* 16 */   public final NumberValue<Float> hitboxSize = new NumberValue("Expand", Float.valueOf(0.1F), Float.valueOf(0.0F), Float.valueOf(1.0F), Float.valueOf(0.01F));
/*    */ 
/*    */   
/*    */   public Hitbox() {
/* 20 */     addSettings(new Value[] { (Value)this.hitboxSize });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\combat\Hitbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */