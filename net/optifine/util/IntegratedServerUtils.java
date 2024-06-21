/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.server.integrated.IntegratedServer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ public class IntegratedServerUtils
/*    */ {
/*    */   public static WorldServer getWorldServer() {
/* 19 */     Minecraft minecraft = Config.getMinecraft();
/* 20 */     WorldClient worldClient = minecraft.theWorld;
/*    */     
/* 22 */     if (worldClient == null)
/*    */     {
/* 24 */       return null;
/*    */     }
/* 26 */     if (!minecraft.isIntegratedServerRunning())
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     IntegratedServer integratedserver = minecraft.getIntegratedServer();
/*    */     
/* 34 */     if (integratedserver == null)
/*    */     {
/* 36 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 40 */     WorldProvider worldprovider = ((World)worldClient).provider;
/*    */     
/* 42 */     if (worldprovider == null)
/*    */     {
/* 44 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 48 */     int i = worldprovider.getDimensionId();
/*    */ 
/*    */     
/*    */     try {
/* 52 */       WorldServer worldserver = integratedserver.worldServerForDimension(i);
/* 53 */       return worldserver;
/*    */     }
/* 55 */     catch (NullPointerException var6) {
/*    */       
/* 57 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Entity getEntity(UUID uuid) {
/* 66 */     WorldServer worldserver = getWorldServer();
/*    */     
/* 68 */     if (worldserver == null)
/*    */     {
/* 70 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 74 */     Entity entity = worldserver.getEntityFromUuid(uuid);
/* 75 */     return entity;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static TileEntity getTileEntity(BlockPos pos) {
/* 81 */     WorldServer worldserver = getWorldServer();
/*    */     
/* 83 */     if (worldserver == null)
/*    */     {
/* 85 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 89 */     Chunk chunk = worldserver.getChunkProvider().provideChunk(pos.getX() >> 4, pos.getZ() >> 4);
/*    */     
/* 91 */     if (chunk == null)
/*    */     {
/* 93 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 97 */     TileEntity tileentity = chunk.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
/* 98 */     return tileentity;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\IntegratedServerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */