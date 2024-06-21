/*    */ package cc.slack.features.modules.impl.ghost;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.AttackUtil;
/*    */ import cc.slack.utils.player.RotationUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "AimAssist", category = Category.GHOST)
/*    */ public class AimAssist
/*    */   extends Module
/*    */ {
/* 25 */   private final BooleanValue lowerSens = new BooleanValue("Lower Sensitivity On Target", true);
/* 26 */   private final NumberValue<Float> lowerSensAmount = new NumberValue("Lowered Sensitity Percentage", Float.valueOf(0.6F), Float.valueOf(0.0F), Float.valueOf(1.0F), Float.valueOf(0.1F));
/* 27 */   private final BooleanValue accelSens = new BooleanValue("Dynamic Acceleration", true);
/*    */   
/*    */   private float prevDist;
/*    */   private float currDist;
/*    */   private float[] prevRot;
/*    */   private boolean wasAccel = false;
/*    */   private float sens;
/*    */   private float gameSens;
/*    */   
/*    */   public AimAssist() {
/* 37 */     addSettings(new Value[] { (Value)this.lowerSens, (Value)this.lowerSensAmount, (Value)this.accelSens });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 43 */     this.gameSens = (mc.getGameSettings()).mouseSensitivity;
/* 44 */     if (((Boolean)this.lowerSens.getValue()).booleanValue() && 
/* 45 */       (mc.getMinecraft()).objectMouseOver.entityHit != null) {
/* 46 */       this.sens = this.gameSens * ((Float)this.lowerSensAmount.getValue()).floatValue();
/*    */     }
/*    */     
/* 49 */     if (((Boolean)this.accelSens.getValue()).booleanValue() && 
/* 50 */       (mc.getMinecraft()).objectMouseOver.entityHit == null) {
/* 51 */       EntityLivingBase target = AttackUtil.getTarget(4.6D, "FOV");
/* 52 */       if (target != null) {
/* 53 */         if (this.wasAccel) {
/* 54 */           this.prevDist = this.currDist;
/* 55 */           this.currDist = (float)RotationUtil.getRotationDifference((Entity)target);
/* 56 */           if (RotationUtil.getRotationDifference(this.prevRot) * 0.6D < (this.prevDist - this.currDist)) {
/* 57 */             this.sens = this.gameSens * 1.2F;
/*    */           } else {
/* 59 */             this.sens = this.gameSens;
/*    */           } 
/*    */           
/* 62 */           this.prevRot = new float[] { (mc.getPlayer()).rotationYaw, (mc.getPlayer()).rotationPitch };
/*    */         } else {
/* 64 */           this.prevRot = new float[] { (mc.getPlayer()).rotationYaw, (mc.getPlayer()).rotationPitch };
/* 65 */           this.prevDist = (float)RotationUtil.getRotationDifference((Entity)target);
/* 66 */           this.currDist = this.prevDist;
/* 67 */           this.wasAccel = true;
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float getSens() {
/* 75 */     if (isToggle()) {
/* 76 */       return this.sens;
/*    */     }
/* 78 */     return (mc.getGameSettings()).mouseSensitivity;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\AimAssist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */