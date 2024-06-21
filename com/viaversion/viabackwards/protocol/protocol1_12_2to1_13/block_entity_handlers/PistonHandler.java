/*     */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;
/*     */ 
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingDataLoader;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringJoiner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PistonHandler
/*     */   implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
/*     */ {
/*  37 */   private final Map<String, Integer> pistonIds = new HashMap<>();
/*     */   
/*     */   public PistonHandler() {
/*  40 */     if (Via.getConfig().isServersideBlockConnections()) {
/*  41 */       Object2IntMap object2IntMap = ConnectionData.getKeyToId();
/*  42 */       for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>)object2IntMap.entrySet()) {
/*  43 */         if (!((String)entry.getKey()).contains("piston")) {
/*     */           continue;
/*     */         }
/*     */         
/*  47 */         addEntries(entry.getKey(), ((Integer)entry.getValue()).intValue());
/*     */       } 
/*     */     } else {
/*  50 */       ListTag blockStates = (ListTag)MappingDataLoader.loadNBT("blockstates-1.13.nbt").get("blockstates");
/*  51 */       for (int id = 0; id < blockStates.size(); id++) {
/*  52 */         StringTag state = (StringTag)blockStates.get(id);
/*  53 */         String key = state.getValue();
/*  54 */         if (key.contains("piston"))
/*     */         {
/*     */ 
/*     */           
/*  58 */           addEntries(key, id);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addEntries(String data, int id) {
/*  65 */     id = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(id);
/*  66 */     this.pistonIds.put(data, Integer.valueOf(id));
/*     */     
/*  68 */     String substring = data.substring(10);
/*  69 */     if (!substring.startsWith("piston") && !substring.startsWith("sticky_piston")) {
/*     */       return;
/*     */     }
/*  72 */     String[] split = data.substring(0, data.length() - 1).split("\\[");
/*  73 */     String[] properties = split[1].split(",");
/*  74 */     data = split[0] + "[" + properties[1] + "," + properties[0] + "]";
/*  75 */     this.pistonIds.put(data, Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
/*  80 */     CompoundTag blockState = (CompoundTag)tag.get("blockState");
/*  81 */     if (blockState == null) return tag;
/*     */     
/*  83 */     String dataFromTag = getDataFromTag(blockState);
/*  84 */     if (dataFromTag == null) return tag;
/*     */     
/*  86 */     Integer id = this.pistonIds.get(dataFromTag);
/*  87 */     if (id == null)
/*     */     {
/*  89 */       return tag;
/*     */     }
/*     */     
/*  92 */     tag.put("blockId", (Tag)new IntTag(id.intValue() >> 4));
/*  93 */     tag.put("blockData", (Tag)new IntTag(id.intValue() & 0xF));
/*  94 */     return tag;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDataFromTag(CompoundTag tag) {
/*  99 */     StringTag name = (StringTag)tag.get("Name");
/* 100 */     if (name == null) return null;
/*     */     
/* 102 */     CompoundTag properties = (CompoundTag)tag.get("Properties");
/* 103 */     if (properties == null) return name.getValue();
/*     */     
/* 105 */     StringJoiner joiner = new StringJoiner(",", name.getValue() + "[", "]");
/* 106 */     for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)properties) {
/* 107 */       if (!(entry.getValue() instanceof StringTag))
/* 108 */         continue;  joiner.add((String)entry.getKey() + "=" + ((StringTag)entry.getValue()).getValue());
/*     */     } 
/* 110 */     return joiner.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\block_entity_handlers\PistonHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */