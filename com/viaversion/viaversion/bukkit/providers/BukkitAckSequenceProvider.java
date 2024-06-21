/*    */ package com.viaversion.viaversion.bukkit.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.ViaVersionPlugin;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import com.viaversion.viaversion.bukkit.tasks.protocol1_19to1_18_2.AckSequenceTask;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.SequenceStorage;
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
/*    */ public final class BukkitAckSequenceProvider
/*    */   extends AckSequenceProvider
/*    */ {
/*    */   private final ViaVersionPlugin plugin;
/*    */   
/*    */   public BukkitAckSequenceProvider(ViaVersionPlugin plugin) {
/* 32 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleSequence(UserConnection connection, int sequence) {
/* 37 */     SequenceStorage sequenceStorage = (SequenceStorage)connection.get(SequenceStorage.class);
/* 38 */     int previousSequence = sequenceStorage.setSequenceId(sequence);
/* 39 */     if (previousSequence == -1) {
/* 40 */       int serverProtocolVersion = connection.getProtocolInfo().getServerProtocolVersion();
/* 41 */       long delay = (serverProtocolVersion > ProtocolVersion.v1_8.getVersion() && serverProtocolVersion < ProtocolVersion.v1_14.getVersion()) ? 2L : 1L;
/* 42 */       this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new AckSequenceTask(connection, sequenceStorage), delay);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\providers\BukkitAckSequenceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */