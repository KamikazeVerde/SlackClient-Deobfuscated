/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "InvMove", category = Category.MOVEMENT)
/*    */ public class InvMove
/*    */   extends Module
/*    */ {
/* 21 */   private static final BooleanValue noOpen = new BooleanValue("Cancel Inventory Open", false);
/*    */ 
/*    */   
/*    */   public InvMove() {
/* 25 */     addSettings(new Value[] { (Value)noOpen });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 31 */     MovementUtil.updateBinds();
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 36 */     if (event.getPacket() instanceof C16PacketClientStatus && ((Boolean)noOpen.getValue()).booleanValue() && 
/* 37 */       event.getPacket() == new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT))
/* 38 */       event.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\InvMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */