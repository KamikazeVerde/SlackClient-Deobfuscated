/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*    */ import java.util.Optional;
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
/*    */ public class CommandBlockProvider
/*    */   implements Provider
/*    */ {
/*    */   public void addOrUpdateBlock(UserConnection user, Position position, CompoundTag tag) throws Exception {
/* 35 */     checkPermission(user);
/* 36 */     if (isEnabled())
/* 37 */       getStorage(user).addOrUpdateBlock(position, tag); 
/*    */   }
/*    */   
/*    */   public Optional<CompoundTag> get(UserConnection user, Position position) throws Exception {
/* 41 */     checkPermission(user);
/* 42 */     if (isEnabled())
/* 43 */       return getStorage(user).getCommandBlock(position); 
/* 44 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   public void unloadChunk(UserConnection user, int x, int z) throws Exception {
/* 48 */     checkPermission(user);
/* 49 */     if (isEnabled())
/* 50 */       getStorage(user).unloadChunk(x, z); 
/*    */   }
/*    */   
/*    */   private CommandBlockStorage getStorage(UserConnection connection) {
/* 54 */     return (CommandBlockStorage)connection.get(CommandBlockStorage.class);
/*    */   }
/*    */   
/*    */   public void sendPermission(UserConnection user) throws Exception {
/* 58 */     if (!isEnabled())
/*    */       return; 
/* 60 */     PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.ENTITY_STATUS, null, user);
/*    */     
/* 62 */     EntityTracker1_9 tracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_9To1_8.class);
/* 63 */     wrapper.write((Type)Type.INT, Integer.valueOf(tracker.getProvidedEntityId()));
/* 64 */     wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)26));
/*    */     
/* 66 */     wrapper.scheduleSend(Protocol1_9To1_8.class);
/*    */     
/* 68 */     ((CommandBlockStorage)user.get(CommandBlockStorage.class)).setPermissions(true);
/*    */   }
/*    */ 
/*    */   
/*    */   private void checkPermission(UserConnection user) throws Exception {
/* 73 */     if (!isEnabled())
/*    */       return; 
/* 75 */     CommandBlockStorage storage = getStorage(user);
/* 76 */     if (!storage.isPermissions()) {
/* 77 */       sendPermission(user);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 82 */     return true;
/*    */   }
/*    */   
/*    */   public void unloadChunks(UserConnection userConnection) {
/* 86 */     if (isEnabled())
/* 87 */       getStorage(userConnection).unloadChunks(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\providers\CommandBlockProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */