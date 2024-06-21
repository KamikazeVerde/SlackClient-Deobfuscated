/*    */ package cc.slack.features.modules.impl.other;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "AntiBot", category = Category.OTHER)
/*    */ public class AntiBot
/*    */   extends Module
/*    */ {
/* 18 */   public final BooleanValue colored = new BooleanValue("Colored name", true);
/*    */ 
/*    */ 
/*    */   
/*    */   public AntiBot() {
/* 23 */     addSettings(new Value[] { (Value)this.colored });
/*    */   }
/*    */   
/*    */   public boolean isBot(EntityLivingBase e) {
/* 27 */     if (((Boolean)this.colored.getValue()).booleanValue() && e.getCustomNameTag().contains("§")) return true;
/*    */     
/* 29 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\other\AntiBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */