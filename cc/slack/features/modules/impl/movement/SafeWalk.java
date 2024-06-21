/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.events.impl.player.MoveEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "SafeWalk", category = Category.MOVEMENT)
/*    */ public class SafeWalk
/*    */   extends Module
/*    */ {
/* 21 */   private final BooleanValue offGround = new BooleanValue("In Air", false);
/* 22 */   private final BooleanValue overEdge = new BooleanValue("Only Over Edge", true);
/* 23 */   private final BooleanValue avoidJump = new BooleanValue("Avoid During Jump", true);
/*    */   
/*    */   public SafeWalk() {
/* 26 */     addSettings(new Value[] { (Value)this.offGround, (Value)this.overEdge, (Value)this.avoidJump });
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onMove(MoveEvent event) {
/* 31 */     if (!isOverEdge() && ((Boolean)this.overEdge.getValue()).booleanValue())
/* 32 */       return;  if ((mc.getPlayer()).motionY > 0.0D && (mc.getPlayer()).offGroundTicks < 6 && ((Boolean)this.avoidJump.getValue()).booleanValue())
/* 33 */       return;  event.safewalk = ((mc.getPlayer()).onGround || ((Boolean)this.offGround.getValue()).booleanValue());
/*    */   }
/*    */   
/*    */   private boolean isOverEdge() {
/* 37 */     return (mc.getWorld().rayTraceBlocks(new Vec3(
/* 38 */           (mc.getPlayer()).posX, (mc.getPlayer()).posY, (mc.getPlayer()).posZ), new Vec3(
/* 39 */           (mc.getPlayer()).posX, (mc.getPlayer()).posY - 2.0D, (mc.getPlayer()).posZ), true, true, false) == null);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\SafeWalk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */