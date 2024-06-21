/*    */ package cc.slack.features.modules.impl.world;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "FastPlace", category = Category.WORLD)
/*    */ public class FastPlace
/*    */   extends Module
/*    */ {
/* 15 */   public final NumberValue<Integer> placeDelay = new NumberValue("PlaceDelay", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(4), Integer.valueOf(1));
/*    */   
/*    */   public FastPlace() {
/* 18 */     addSettings(new Value[] { (Value)this.placeDelay });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\world\FastPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */