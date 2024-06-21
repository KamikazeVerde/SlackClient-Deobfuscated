/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.util.Pair;
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
/*    */ public class FlowerPotHandler
/*    */   implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
/*    */ {
/* 32 */   private static final Int2ObjectMap<Pair<String, Byte>> FLOWERS = (Int2ObjectMap<Pair<String, Byte>>)new Int2ObjectOpenHashMap(22, 0.99F);
/* 33 */   private static final Pair<String, Byte> AIR = new Pair("minecraft:air", Byte.valueOf((byte)0));
/*    */   
/*    */   static {
/* 36 */     FLOWERS.put(5265, AIR);
/* 37 */     register(5266, "minecraft:sapling", (byte)0);
/* 38 */     register(5267, "minecraft:sapling", (byte)1);
/* 39 */     register(5268, "minecraft:sapling", (byte)2);
/* 40 */     register(5269, "minecraft:sapling", (byte)3);
/* 41 */     register(5270, "minecraft:sapling", (byte)4);
/* 42 */     register(5271, "minecraft:sapling", (byte)5);
/* 43 */     register(5272, "minecraft:tallgrass", (byte)2);
/* 44 */     register(5273, "minecraft:yellow_flower", (byte)0);
/* 45 */     register(5274, "minecraft:red_flower", (byte)0);
/* 46 */     register(5275, "minecraft:red_flower", (byte)1);
/* 47 */     register(5276, "minecraft:red_flower", (byte)2);
/* 48 */     register(5277, "minecraft:red_flower", (byte)3);
/* 49 */     register(5278, "minecraft:red_flower", (byte)4);
/* 50 */     register(5279, "minecraft:red_flower", (byte)5);
/* 51 */     register(5280, "minecraft:red_flower", (byte)6);
/* 52 */     register(5281, "minecraft:red_flower", (byte)7);
/* 53 */     register(5282, "minecraft:red_flower", (byte)8);
/* 54 */     register(5283, "minecraft:red_mushroom", (byte)0);
/* 55 */     register(5284, "minecraft:brown_mushroom", (byte)0);
/* 56 */     register(5285, "minecraft:deadbush", (byte)0);
/* 57 */     register(5286, "minecraft:cactus", (byte)0);
/*    */   }
/*    */   
/*    */   private static void register(int id, String identifier, byte data) {
/* 61 */     FLOWERS.put(id, new Pair(identifier, Byte.valueOf(data)));
/*    */   }
/*    */   
/*    */   public static boolean isFlowah(int id) {
/* 65 */     return (id >= 5265 && id <= 5286);
/*    */   }
/*    */   
/*    */   public Pair<String, Byte> getOrDefault(int blockId) {
/* 69 */     Pair<String, Byte> pair = (Pair<String, Byte>)FLOWERS.get(blockId);
/* 70 */     return (pair != null) ? pair : AIR;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
/* 76 */     Pair<String, Byte> item = getOrDefault(blockId);
/*    */     
/* 78 */     tag.put("Item", (Tag)new StringTag((String)item.key()));
/* 79 */     tag.put("Data", (Tag)new IntTag(((Byte)item.value()).byteValue()));
/*    */     
/* 81 */     return tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\block_entity_handlers\FlowerPotHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */