/*    */ package cc.slack.features.modules.impl.combat;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ @ModuleInfo(name = "FastBow", category = Category.COMBAT)
/*    */ public class FastBow
/*    */   extends Module {
/* 22 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Vanilla" });
/*    */   
/*    */   public FastBow() {
/* 25 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 31 */     switch ((String)this.mode.getValue()) {
/*    */       case "Vanilla":
/* 33 */         if (mc.getPlayer().getHealth() > 0.0F && ((mc.getPlayer()).onGround || (mc.getPlayer()).capabilities.isCreativeMode) && (mc.getPlayer()).inventory.getCurrentItem() != null && (mc.getPlayer()).inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemBow && (mc.getGameSettings()).keyBindUseItem.pressed) {
/* 34 */           mc.getPlayerController().sendUseItem((EntityPlayer)mc.getPlayer(), (World)mc.getWorld(), (mc.getPlayer()).inventory.getCurrentItem());
/* 35 */           (mc.getPlayer()).inventory.getCurrentItem().getItem().onItemRightClick((mc.getPlayer()).inventory.getCurrentItem(), (World)mc.getWorld(), (EntityPlayer)mc.getPlayer());
/* 36 */           for (int i = 0; i < 20; i++) {
/* 37 */             (mc.getPlayer()).sendQueue.addToSendQueue((Packet)new C03PacketPlayer(false));
/*    */           }
/* 39 */           mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
/* 40 */           (mc.getPlayer()).inventory.getCurrentItem().getItem().onPlayerStoppedUsing((mc.getPlayer()).inventory.getCurrentItem(), (World)mc.getWorld(), (EntityPlayer)mc.getPlayer(), 0);
/*    */         } 
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\combat\FastBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */