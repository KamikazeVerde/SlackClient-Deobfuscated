/*    */ package com.viaversion.viaversion.velocity.platform;
/*    */ 
/*    */ import com.velocitypowered.api.proxy.Player;
/*    */ import com.viaversion.viaversion.ViaAPIBase;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VelocityViaAPI
/*    */   extends ViaAPIBase<Player>
/*    */ {
/*    */   public int getPlayerVersion(Player player) {
/* 28 */     return getPlayerVersion(player.getUniqueId());
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
/* 33 */     sendRawPacket(player.getUniqueId(), packet);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\platform\VelocityViaAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */