/*    */ package com.viaversion.viaversion.rewriter;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public class StatisticsRewriter<C extends ClientboundPacketType>
/*    */ {
/*    */   private static final int CUSTOM_STATS_CATEGORY = 8;
/*    */   private final Protocol<C, ?, ?, ?> protocol;
/*    */   
/*    */   public StatisticsRewriter(Protocol<C, ?, ?, ?> protocol) {
/* 31 */     this.protocol = protocol;
/*    */   }
/*    */   
/*    */   public void register(C packetType) {
/* 35 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*    */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */           int newSize = size;
/*    */           for (int i = 0; i < size; i++) {
/*    */             int categoryId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*    */             int statisticId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*    */             int value = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*    */             if (categoryId == 8 && this.protocol.getMappingData().getStatisticsMappings() != null) {
/*    */               statisticId = this.protocol.getMappingData().getStatisticsMappings().getNewId(statisticId);
/*    */               if (statisticId == -1) {
/*    */                 newSize--;
/*    */                 continue;
/*    */               } 
/*    */             } else {
/*    */               RegistryType type = getRegistryTypeForStatistic(categoryId);
/*    */               IdRewriteFunction statisticsRewriter;
/*    */               if (type != null && (statisticsRewriter = getRewriter(type)) != null) {
/*    */                 statisticId = statisticsRewriter.rewrite(statisticId);
/*    */               }
/*    */             } 
/*    */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(categoryId));
/*    */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(statisticId));
/*    */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(value));
/*    */             continue;
/*    */           } 
/*    */           if (newSize != size) {
/*    */             wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(newSize));
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected IdRewriteFunction getRewriter(RegistryType type) {
/* 71 */     switch (type) {
/*    */       case BLOCK:
/* 73 */         return (this.protocol.getMappingData().getBlockMappings() != null) ? (id -> this.protocol.getMappingData().getNewBlockId(id)) : null;
/*    */       case ITEM:
/* 75 */         return (this.protocol.getMappingData().getItemMappings() != null) ? (id -> this.protocol.getMappingData().getNewItemId(id)) : null;
/*    */       case ENTITY:
/* 77 */         return (this.protocol.getEntityRewriter() != null) ? (id -> this.protocol.getEntityRewriter().newEntityId(id)) : null;
/*    */     } 
/* 79 */     throw new IllegalArgumentException("Unknown registry type in statistics packet: " + type);
/*    */   }
/*    */   
/*    */   public RegistryType getRegistryTypeForStatistic(int statisticsId) {
/* 83 */     switch (statisticsId) {
/*    */       case 0:
/* 85 */         return RegistryType.BLOCK;
/*    */       case 1:
/*    */       case 2:
/*    */       case 3:
/*    */       case 4:
/*    */       case 5:
/* 91 */         return RegistryType.ITEM;
/*    */       case 6:
/*    */       case 7:
/* 94 */         return RegistryType.ENTITY;
/*    */     } 
/* 96 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\StatisticsRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */