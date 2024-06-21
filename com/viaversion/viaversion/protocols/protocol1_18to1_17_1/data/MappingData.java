/*    */ package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.data;
/*    */ 
/*    */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
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
/*    */ public final class MappingData
/*    */   extends MappingDataBase
/*    */ {
/* 27 */   private final Object2IntMap<String> blockEntityIds = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*    */   
/*    */   public MappingData() {
/* 30 */     super("1.17", "1.18");
/* 31 */     this.blockEntityIds.defaultReturnValue(-1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadExtras(CompoundTag data) {
/* 36 */     String[] blockEntities = blockEntities();
/* 37 */     for (int id = 0; id < blockEntities.length; id++) {
/* 38 */       this.blockEntityIds.put(blockEntities[id], id);
/*    */     }
/*    */   }
/*    */   
/*    */   public Object2IntMap<String> blockEntityIds() {
/* 43 */     return this.blockEntityIds;
/*    */   }
/*    */   
/*    */   private String[] blockEntities() {
/* 47 */     return new String[] { "furnace", "chest", "trapped_chest", "ender_chest", "jukebox", "dispenser", "dropper", "sign", "mob_spawner", "piston", "brewing_stand", "enchanting_table", "end_portal", "beacon", "skull", "daylight_detector", "hopper", "comparator", "banner", "structure_block", "end_gateway", "command_block", "shulker_box", "bed", "conduit", "barrel", "smoker", "blast_furnace", "lectern", "bell", "jigsaw", "campfire", "beehive", "sculk_sensor" };
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18to1_17_1\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */