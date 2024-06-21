/*    */ package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
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
/*    */ 
/*    */ 
/*    */ public class FakeTileEntity
/*    */ {
/* 31 */   private static final Int2ObjectMap<CompoundTag> tileEntities = (Int2ObjectMap<CompoundTag>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   static {
/* 34 */     register("Furnace", new int[] { 61, 62 });
/* 35 */     register("Chest", new int[] { 54, 146 });
/* 36 */     register("EnderChest", new int[] { 130 });
/* 37 */     register("RecordPlayer", new int[] { 84 });
/* 38 */     register("Trap", new int[] { 23 });
/* 39 */     register("Dropper", new int[] { 158 });
/* 40 */     register("Sign", new int[] { 63, 68 });
/* 41 */     register("MobSpawner", new int[] { 52 });
/* 42 */     register("Music", new int[] { 25 });
/* 43 */     register("Piston", new int[] { 33, 34, 29, 36 });
/* 44 */     register("Cauldron", new int[] { 117 });
/* 45 */     register("EnchantTable", new int[] { 116 });
/* 46 */     register("Airportal", new int[] { 119, 120 });
/* 47 */     register("Beacon", new int[] { 138 });
/* 48 */     register("Skull", new int[] { 144 });
/* 49 */     register("DLDetector", new int[] { 178, 151 });
/* 50 */     register("Hopper", new int[] { 154 });
/* 51 */     register("Comparator", new int[] { 149, 150 });
/* 52 */     register("FlowerPot", new int[] { 140 });
/* 53 */     register("Banner", new int[] { 176, 177 });
/* 54 */     register("EndGateway", new int[] { 209 });
/* 55 */     register("Control", new int[] { 137 });
/*    */   }
/*    */   
/*    */   private static void register(String name, int... ids) {
/* 59 */     for (int id : ids) {
/* 60 */       CompoundTag comp = new CompoundTag();
/* 61 */       comp.put("id", (Tag)new StringTag(name));
/* 62 */       tileEntities.put(id, comp);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isTileEntity(int block) {
/* 67 */     return tileEntities.containsKey(block);
/*    */   }
/*    */   
/*    */   public static CompoundTag createTileEntity(int x, int y, int z, int block) {
/* 71 */     CompoundTag originalTag = (CompoundTag)tileEntities.get(block);
/* 72 */     if (originalTag != null) {
/* 73 */       CompoundTag tag = originalTag.clone();
/* 74 */       tag.put("x", (Tag)new IntTag(x));
/* 75 */       tag.put("y", (Tag)new IntTag(y));
/* 76 */       tag.put("z", (Tag)new IntTag(z));
/* 77 */       return tag;
/*    */     } 
/* 79 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_3to1_9_1_2\chunks\FakeTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */