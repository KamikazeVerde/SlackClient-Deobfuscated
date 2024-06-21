/*    */ package cc.slack.features.modules.impl.ghost;
/*    */ 
/*    */ import cc.slack.events.impl.render.RenderEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.other.MathUtil;
/*    */ import cc.slack.utils.player.AttackUtil;
/*    */ import cc.slack.utils.player.RotationUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "AimBot", category = Category.GHOST)
/*    */ public class AimBot
/*    */   extends Module
/*    */ {
/* 24 */   private final BooleanValue silent = new BooleanValue("Silent Aimbot", false);
/* 25 */   private final BooleanValue silentMoveFix = new BooleanValue("Silent Aimbot Move Fix", true);
/*    */   
/* 27 */   private final NumberValue<Integer> fov = new NumberValue("Max FOV", Integer.valueOf(80), Integer.valueOf(0), Integer.valueOf(180), Integer.valueOf(5));
/* 28 */   private final NumberValue<Integer> minFov = new NumberValue("Min FOV", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(180), Integer.valueOf(2));
/*    */   
/* 30 */   private final NumberValue<Float> aimRange = new NumberValue("Aim Range", Float.valueOf(4.5F), Float.valueOf(0.0F), Float.valueOf(8.0F), Float.valueOf(0.1F));
/*    */   
/* 32 */   private final NumberValue<Integer> aimSpeed = new NumberValue("Aim Speed", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(180), Integer.valueOf(1));
/*    */   
/*    */   public boolean isSilent;
/* 35 */   private EntityLivingBase target = null;
/*    */   
/*    */   public AimBot() {
/* 38 */     addSettings(new Value[] { (Value)this.silent, (Value)this.silentMoveFix, (Value)this.fov, (Value)this.minFov, (Value)this.aimRange, (Value)this.aimSpeed });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onRender(RenderEvent event) {
/* 45 */     if (event.getState() != RenderEvent.State.RENDER_3D)
/*    */       return; 
/* 47 */     this.target = AttackUtil.getTarget(((Float)this.aimRange.getValue()).floatValue(), "fov");
/* 48 */     if (this.target == null) {
/* 49 */       if (this.isSilent) {
/* 50 */         this.isSilent = false;
/* 51 */         RotationUtil.disable();
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     float[] targetRotation = RotationUtil.getTargetRotations(this.target.getEntityBoundingBox(), RotationUtil.TargetRotation.CENTER, 0.0D);
/*    */     
/* 58 */     if (RotationUtil.getRotationDifference(targetRotation) > ((Integer)this.fov.getValue()).intValue())
/* 59 */       return;  if (RotationUtil.getRotationDifference(targetRotation) < ((Integer)this.minFov.getValue()).intValue())
/*    */       return; 
/* 61 */     float[] clientRotation = RotationUtil.getLimitedRotation(RotationUtil.clientRotation, targetRotation, (((Integer)this.aimSpeed
/*    */ 
/*    */         
/* 64 */         .getValue()).intValue() + (float)MathUtil.getRandomInRange(0.0D, ((Integer)this.aimSpeed.getValue()).intValue() / 5.0D)) * 20.0F / Minecraft.getDebugFPS());
/*    */ 
/*    */     
/* 67 */     if (((Boolean)this.silent.getValue()).booleanValue()) {
/* 68 */       RotationUtil.setClientRotation(clientRotation, 1);
/* 69 */       RotationUtil.setStrafeFix(((Boolean)this.silentMoveFix.getValue()).booleanValue(), false);
/*    */     } else {
/* 71 */       RotationUtil.setPlayerRotation(clientRotation);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\AimBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */