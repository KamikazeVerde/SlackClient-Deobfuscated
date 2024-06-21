/*    */ package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.entity.PlayerDeathEvent;
/*    */ import org.bukkit.plugin.Plugin;
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
/*    */ public class DeathListener
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   public DeathListener(Plugin plugin) {
/* 37 */     super(plugin, Protocol1_9To1_8.class);
/*    */   }
/*    */   
/*    */   @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
/*    */   public void onDeath(PlayerDeathEvent e) {
/* 42 */     Player p = e.getEntity();
/* 43 */     if (isOnPipe(p) && Via.getConfig().isShowNewDeathMessages() && checkGamerule(p.getWorld()) && e.getDeathMessage() != null)
/* 44 */       sendPacket(p, e.getDeathMessage()); 
/*    */   }
/*    */   
/*    */   public boolean checkGamerule(World w) {
/*    */     try {
/* 49 */       return Boolean.parseBoolean(w.getGameRuleValue("showDeathMessages"));
/* 50 */     } catch (Exception e) {
/* 51 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void sendPacket(final Player p, final String msg) {
/* 56 */     Via.getPlatform().runSync(new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 60 */             UserConnection userConnection = DeathListener.this.getUserConnection(p);
/* 61 */             if (userConnection != null) {
/* 62 */               PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.COMBAT_EVENT, null, userConnection);
/*    */               try {
/* 64 */                 wrapper.write((Type)Type.VAR_INT, Integer.valueOf(2));
/* 65 */                 wrapper.write((Type)Type.VAR_INT, Integer.valueOf(p.getEntityId()));
/* 66 */                 wrapper.write((Type)Type.INT, Integer.valueOf(p.getEntityId()));
/* 67 */                 Protocol1_9To1_8.FIX_JSON.write(wrapper, msg);
/*    */                 
/* 69 */                 wrapper.scheduleSend(Protocol1_9To1_8.class);
/* 70 */               } catch (Exception e) {
/* 71 */                 e.printStackTrace();
/*    */               } 
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\protocol1_9to1_8\DeathListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */