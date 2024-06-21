/*    */ package cc.slack.features.modules.impl.ghost;
/*    */ 
/*    */ import cc.slack.events.impl.player.AttackEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Reach", category = Category.GHOST)
/*    */ public class Reach
/*    */   extends Module
/*    */ {
/* 21 */   public final NumberValue<Double> reach = new NumberValue("Reach", Double.valueOf(3.1D), Double.valueOf(3.0D), Double.valueOf(6.0D), Double.valueOf(0.01D));
/* 22 */   public final NumberValue<Double> chance = new NumberValue("Chance", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.01D));
/*    */   
/* 24 */   public double combatReach = 3.0D;
/*    */ 
/*    */   
/*    */   public Reach() {
/* 28 */     addSettings(new Value[] { (Value)this.reach, (Value)this.chance });
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onAttack(AttackEvent event) {
/* 33 */     double rnd = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D);
/* 34 */     if (rnd <= ((Double)this.chance.getValue()).doubleValue()) {
/* 35 */       this.combatReach = ((Double)this.reach.getValue()).doubleValue();
/*    */     } else {
/* 37 */       this.combatReach = 3.0D;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\Reach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */