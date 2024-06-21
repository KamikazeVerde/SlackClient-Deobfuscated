/*    */ package com.viaversion.viaversion.sponge.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.ViaAPIBase;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import org.spongepowered.api.entity.living.player.Player;
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
/*    */ public class SpongeViaAPI
/*    */   extends ViaAPIBase<Player>
/*    */ {
/*    */   public int getPlayerVersion(Player player) {
/* 28 */     return getPlayerVersion(player.uniqueId());
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
/* 33 */     sendRawPacket(player.uniqueId(), packet);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\platform\SpongeViaAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */