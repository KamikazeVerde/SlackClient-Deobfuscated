/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.events.impl.player.MotionEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Bobbing", category = Category.RENDER)
/*    */ public class Bobbing
/*    */   extends Module
/*    */ {
/* 21 */   public BooleanValue nobobvalue = new BooleanValue("NoBob", true);
/* 22 */   public BooleanValue custombobbing = new BooleanValue("Custom Bobbing", false);
/* 23 */   public NumberValue<Float> bobbingvalue = new NumberValue("Bobbing Amount", Float.valueOf(0.03F), Float.valueOf(-0.5F), Float.valueOf(0.5F), Float.valueOf(0.01F));
/*    */   
/*    */   public Bobbing() {
/* 26 */     addSettings(new Value[] { (Value)this.nobobvalue, (Value)this.custombobbing, (Value)this.bobbingvalue });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onMotion(MotionEvent event) {
/* 32 */     if (((Boolean)this.custombobbing.getValue()).booleanValue() && (mc.getPlayer()).onGround && !((Boolean)this.nobobvalue.getValue()).booleanValue() && MovementUtil.isMoving()) {
/* 33 */       (mc.getPlayer()).cameraYaw = ((Float)this.bobbingvalue.getValue()).floatValue();
/*    */     }
/*    */     
/* 36 */     if (((Boolean)this.nobobvalue.getValue()).booleanValue() && MovementUtil.isMoving())
/* 37 */       (mc.getPlayer()).cameraYaw = 0.0F; 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\Bobbing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */