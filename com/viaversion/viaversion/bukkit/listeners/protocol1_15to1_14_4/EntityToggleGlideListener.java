/*    */ package com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4;
/*    */ 
/*    */ import com.viaversion.viaversion.ViaVersionPlugin;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.entity.EntityToggleGlideEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.potion.PotionEffectType;
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
/*    */ public class EntityToggleGlideListener
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   private boolean swimmingMethodExists;
/*    */   
/*    */   public EntityToggleGlideListener(ViaVersionPlugin plugin) {
/* 40 */     super((Plugin)plugin, Protocol1_15To1_14_4.class);
/*    */     try {
/* 42 */       Player.class.getMethod("isSwimming", new Class[0]);
/* 43 */       this.swimmingMethodExists = true;
/* 44 */     } catch (NoSuchMethodException noSuchMethodException) {}
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR)
/*    */   public void entityToggleGlide(EntityToggleGlideEvent event) {
/* 50 */     if (!(event.getEntity() instanceof Player))
/*    */       return; 
/* 52 */     Player player = (Player)event.getEntity();
/* 53 */     if (!isOnPipe(player)) {
/*    */       return;
/*    */     }
/* 56 */     if (event.isGliding() && event.isCancelled()) {
/* 57 */       PacketWrapper packet = PacketWrapper.create((PacketType)ClientboundPackets1_15.ENTITY_METADATA, null, getUserConnection(player));
/*    */       try {
/* 59 */         packet.write((Type)Type.VAR_INT, Integer.valueOf(player.getEntityId()));
/*    */         
/* 61 */         byte bitmask = 0;
/*    */         
/* 63 */         if (player.getFireTicks() > 0) {
/* 64 */           bitmask = (byte)(bitmask | 0x1);
/*    */         }
/* 66 */         if (player.isSneaking()) {
/* 67 */           bitmask = (byte)(bitmask | 0x2);
/*    */         }
/*    */         
/* 70 */         if (player.isSprinting()) {
/* 71 */           bitmask = (byte)(bitmask | 0x8);
/*    */         }
/* 73 */         if (this.swimmingMethodExists && player.isSwimming()) {
/* 74 */           bitmask = (byte)(bitmask | 0x10);
/*    */         }
/* 76 */         if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
/* 77 */           bitmask = (byte)(bitmask | 0x20);
/*    */         }
/* 79 */         if (player.isGlowing()) {
/* 80 */           bitmask = (byte)(bitmask | 0x40);
/*    */         }
/*    */ 
/*    */         
/* 84 */         packet.write(Types1_14.METADATA_LIST, Arrays.asList(new Metadata[] { new Metadata(0, Types1_14.META_TYPES.byteType, Byte.valueOf(bitmask)) }));
/* 85 */         packet.scheduleSend(Protocol1_15To1_14_4.class);
/* 86 */       } catch (Exception e) {
/* 87 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\protocol1_15to1_14_4\EntityToggleGlideListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */