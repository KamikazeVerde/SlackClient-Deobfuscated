/*    */ package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
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
/*    */ public final class BackwardsMappings
/*    */   extends BackwardsMappings
/*    */ {
/* 28 */   private final Int2ObjectMap<String> blockEntities = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   public BackwardsMappings() {
/* 31 */     super("1.18", "1.17", Protocol1_18To1_17_1.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadExtras(CompoundTag data) {
/* 36 */     for (ObjectIterator<Object2IntMap.Entry<String>> objectIterator = Protocol1_18To1_17_1.MAPPINGS.blockEntityIds().object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<String> entry = objectIterator.next();
/* 37 */       this.blockEntities.put(entry.getIntValue(), entry.getKey()); }
/*    */   
/*    */   }
/*    */   
/*    */   public Int2ObjectMap<String> blockEntities() {
/* 42 */     return this.blockEntities;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_17_1to1_18\data\BackwardsMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */