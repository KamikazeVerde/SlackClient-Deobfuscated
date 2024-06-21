/*    */ package cc.slack.features.modules.impl.other;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Targets", category = Category.OTHER)
/*    */ public class Targets
/*    */   extends Module
/*    */ {
/* 16 */   public final BooleanValue teams = new BooleanValue("Teams", false);
/* 17 */   public final BooleanValue playerTarget = new BooleanValue("Players", false);
/* 18 */   public final BooleanValue animalTarget = new BooleanValue("Animals", false);
/* 19 */   public final BooleanValue mobsTarget = new BooleanValue("Mobs", false);
/*    */   
/*    */   public Targets() {
/* 22 */     addSettings(new Value[] { (Value)this.teams, (Value)this.playerTarget, (Value)this.animalTarget, (Value)this.mobsTarget });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\other\Targets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */