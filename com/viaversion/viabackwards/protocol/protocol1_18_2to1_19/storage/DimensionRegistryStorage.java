/*    */ package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
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
/*    */ 
/*    */ 
/*    */ public final class DimensionRegistryStorage
/*    */   implements StorableObject
/*    */ {
/* 31 */   private final Map<String, CompoundTag> dimensions = new HashMap<>();
/* 32 */   private final Int2ObjectMap<CompoundTag> chatTypes = (Int2ObjectMap<CompoundTag>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   public CompoundTag dimension(String dimensionKey) {
/* 35 */     CompoundTag compoundTag = this.dimensions.get(dimensionKey);
/* 36 */     return (compoundTag != null) ? compoundTag.clone() : null;
/*    */   }
/*    */   
/*    */   public void addDimension(String dimensionKey, CompoundTag dimension) {
/* 40 */     this.dimensions.put(dimensionKey, dimension);
/*    */   }
/*    */   
/*    */   public CompoundTag chatType(int id) {
/* 44 */     return this.chatTypes.isEmpty() ? Protocol1_18_2To1_19.MAPPINGS.chatType(id) : (CompoundTag)this.chatTypes.get(id);
/*    */   }
/*    */   
/*    */   public void addChatType(int id, CompoundTag chatType) {
/* 48 */     this.chatTypes.put(id, chatType);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 52 */     this.dimensions.clear();
/* 53 */     this.chatTypes.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean clearOnServerSwitch() {
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18_2to1_19\storage\DimensionRegistryStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */