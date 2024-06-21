/*    */ package cc.slack.features.modules.impl.combat;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.network.PacketUtil;
/*    */ import cc.slack.utils.player.RotationUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*    */ import net.minecraft.network.play.client.C0APacketAnimation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "AntiFireball", category = Category.COMBAT)
/*    */ public class AntiFireball
/*    */   extends Module
/*    */ {
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 27 */     for (Entity entity : (mc.getWorld()).loadedEntityList) {
/* 28 */       if (entity instanceof net.minecraft.entity.projectile.EntityFireball && 
/* 29 */         mc.getPlayer().getDistanceSqToEntity(entity) < 3.0D) {
/* 30 */         RotationUtil.setClientRotation(RotationUtil.getRotations(entity));
/* 31 */         PacketUtil.send((Packet)new C0APacketAnimation());
/* 32 */         PacketUtil.send((Packet)new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\combat\AntiFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */