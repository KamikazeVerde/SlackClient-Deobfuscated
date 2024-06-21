/*    */ package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*    */ import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
/*    */ import com.viaversion.viaversion.util.Key;
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
/*    */ public final class BackwardsMappings
/*    */   extends BackwardsMappings
/*    */ {
/* 31 */   private final Object2IntMap<String> mappedSounds = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*    */   
/*    */   public BackwardsMappings() {
/* 34 */     super("1.19.3", "1.19", Protocol1_19_3To1_19_1.class);
/* 35 */     this.mappedSounds.defaultReturnValue(-1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadExtras(CompoundTag data) {
/* 40 */     super.loadExtras(data);
/*    */     
/* 42 */     JsonArray sounds = VBMappingDataLoader.loadData("sounds-1.19.json").getAsJsonArray("sounds");
/* 43 */     int i = 0;
/* 44 */     for (JsonElement sound : sounds) {
/* 45 */       this.mappedSounds.put(sound.getAsString(), i++);
/*    */     }
/*    */   }
/*    */   
/*    */   public int mappedSound(String sound) {
/* 50 */     return this.mappedSounds.getInt(Key.stripMinecraftNamespace(sound));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_1to1_19_3\data\BackwardsMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */