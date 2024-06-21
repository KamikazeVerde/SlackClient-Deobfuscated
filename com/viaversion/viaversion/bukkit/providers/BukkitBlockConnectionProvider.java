/*    */ package com.viaversion.viaversion.bukkit.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
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
/*    */ public class BukkitBlockConnectionProvider
/*    */   extends BlockConnectionProvider
/*    */ {
/*    */   private Chunk lastChunk;
/*    */   
/*    */   public int getWorldBlockData(UserConnection user, int bx, int by, int bz) {
/* 34 */     UUID uuid = user.getProtocolInfo().getUuid();
/* 35 */     Player player = Bukkit.getPlayer(uuid);
/* 36 */     if (player != null) {
/* 37 */       World world = player.getWorld();
/* 38 */       int x = bx >> 4;
/* 39 */       int z = bz >> 4;
/* 40 */       if (world.isChunkLoaded(x, z)) {
/* 41 */         Chunk c = getChunk(world, x, z);
/* 42 */         Block b = c.getBlock(bx, by, bz);
/* 43 */         return b.getTypeId() << 4 | b.getData();
/*    */       } 
/*    */     } 
/* 46 */     return 0;
/*    */   }
/*    */   
/*    */   public Chunk getChunk(World world, int x, int z) {
/* 50 */     if (this.lastChunk != null && this.lastChunk.getWorld().equals(world) && this.lastChunk.getX() == x && this.lastChunk.getZ() == z) {
/* 51 */       return this.lastChunk;
/*    */     }
/* 53 */     return this.lastChunk = world.getChunkAt(x, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\providers\BukkitBlockConnectionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */