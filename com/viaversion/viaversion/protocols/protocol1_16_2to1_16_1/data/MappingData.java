/*    */ package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.data;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*    */ import com.viaversion.viaversion.api.data.MappingDataLoader;
/*    */ import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class MappingData
/*    */   extends MappingDataBase
/*    */ {
/* 33 */   private final Map<String, CompoundTag> dimensionDataMap = new HashMap<>();
/*    */   private CompoundTag dimensionRegistry;
/*    */   
/*    */   public MappingData() {
/* 37 */     super("1.16", "1.16.2");
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadExtras(CompoundTag data) {
/*    */     try {
/* 43 */       this.dimensionRegistry = BinaryTagIO.readInputStream(MappingDataLoader.getResource("dimension-registry-1.16.2.nbt"));
/* 44 */     } catch (IOException e) {
/* 45 */       Via.getPlatform().getLogger().severe("Error loading dimension registry:");
/* 46 */       e.printStackTrace();
/*    */     } 
/*    */ 
/*    */     
/* 50 */     ListTag dimensions = (ListTag)((CompoundTag)this.dimensionRegistry.get("minecraft:dimension_type")).get("value");
/* 51 */     for (Tag dimension : dimensions) {
/* 52 */       CompoundTag dimensionCompound = (CompoundTag)dimension;
/*    */       
/* 54 */       CompoundTag dimensionData = new CompoundTag(((CompoundTag)dimensionCompound.get("element")).getValue());
/* 55 */       this.dimensionDataMap.put(((StringTag)dimensionCompound.get("name")).getValue(), dimensionData);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Map<String, CompoundTag> getDimensionDataMap() {
/* 60 */     return this.dimensionDataMap;
/*    */   }
/*    */   
/*    */   public CompoundTag getDimensionRegistry() {
/* 64 */     return this.dimensionRegistry.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16_2to1_16_1\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */