/*    */ package com.viaversion.viaversion.bukkit.listeners.protocol1_19to1_18_2;
/*    */ 
/*    */ import com.viaversion.viaversion.ViaVersionPlugin;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.block.BlockBreakEvent;
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
/*    */ public final class BlockBreakListener
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   private static final Class<?> CRAFT_BLOCK_STATE_CLASS;
/*    */   
/*    */   static {
/*    */     try {
/* 38 */       CRAFT_BLOCK_STATE_CLASS = NMSUtil.obc("block.CraftBlockState");
/* 39 */     } catch (ClassNotFoundException e) {
/* 40 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public BlockBreakListener(ViaVersionPlugin plugin) {
/* 45 */     super((Plugin)plugin, Protocol1_19To1_18_2.class);
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR)
/*    */   public void blockBreak(BlockBreakEvent event) {
/* 50 */     Block block = event.getBlock();
/* 51 */     if (!event.isCancelled() || !isBlockEntity(block.getState())) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 56 */     int serverProtocolVersion = Via.getAPI().getServerVersion().highestSupportedVersion();
/* 57 */     long delay = (serverProtocolVersion > ProtocolVersion.v1_8.getVersion() && serverProtocolVersion < ProtocolVersion.v1_14.getVersion()) ? 2L : 1L;
/* 58 */     getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), () -> { BlockState state = block.getState(); if (isBlockEntity(state)) state.update(true, false);  }delay);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isBlockEntity(BlockState state) {
/* 68 */     return (state.getClass() != CRAFT_BLOCK_STATE_CLASS);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\protocol1_19to1_18_2\BlockBreakListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */