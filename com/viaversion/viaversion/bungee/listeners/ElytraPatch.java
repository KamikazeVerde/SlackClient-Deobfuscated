/*    */ package com.viaversion.viaversion.bungee.listeners;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*    */ import java.util.Collections;
/*    */ import net.md_5.bungee.api.event.ServerConnectedEvent;
/*    */ import net.md_5.bungee.api.plugin.Listener;
/*    */ import net.md_5.bungee.event.EventHandler;
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
/*    */ 
/*    */ public class ElytraPatch
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler(priority = 32)
/*    */   public void onServerConnected(ServerConnectedEvent event) {
/* 43 */     UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(event.getPlayer().getUniqueId());
/* 44 */     if (user == null)
/*    */       return; 
/*    */     try {
/* 47 */       if (user.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
/* 48 */         EntityTracker1_9 tracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_9To1_8.class);
/* 49 */         int entityId = tracker.getProvidedEntityId();
/*    */         
/* 51 */         PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.ENTITY_METADATA, null, user);
/*    */         
/* 53 */         wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 54 */         wrapper.write(Types1_9.METADATA_LIST, Collections.singletonList(new Metadata(0, (MetaType)MetaType1_9.Byte, Byte.valueOf((byte)0))));
/*    */         
/* 56 */         wrapper.scheduleSend(Protocol1_9To1_8.class);
/*    */       } 
/* 58 */     } catch (Exception e) {
/* 59 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\listeners\ElytraPatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */