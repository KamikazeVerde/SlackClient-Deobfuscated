/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.impl.world.Scaffold;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Sprint", category = Category.MOVEMENT)
/*    */ public class Sprint
/*    */   extends Module
/*    */ {
/* 26 */   private final BooleanValue omniSprint = new BooleanValue("OmniSprint", false);
/*    */ 
/*    */   
/*    */   public Sprint() {
/* 30 */     addSettings(new Value[] { (Value)this.omniSprint });
/*    */   }
/*    */   
/*    */   private boolean hasPotion() {
/* 34 */     return (!mc.getPlayer().isPotionActive(Potion.confusion) && 
/* 35 */       !mc.getPlayer().isPotionActive(Potion.blindness));
/*    */   }
/*    */   
/*    */   private boolean isMoving() {
/* 39 */     return (!(mc.getPlayer()).isCollidedHorizontally && (
/* 40 */       mc.getPlayer().getFoodStats().getFoodLevel() > 6 || (mc.getPlayer()).capabilities.allowFlying) && 
/* 41 */       !mc.getPlayer().isSneaking() && !mc.getPlayer().isUsingItem());
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent e) {
/* 47 */     if (((Scaffold)Slack.getInstance().getModuleManager().getInstance(Scaffold.class)).isToggle())
/* 48 */       return;  mc.getPlayer().setSprinting(((((Boolean)this.omniSprint.getValue()).booleanValue() || (mc.getPlayer()).moveForward > 0.0F) && isMoving() && hasPotion()));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\Sprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */