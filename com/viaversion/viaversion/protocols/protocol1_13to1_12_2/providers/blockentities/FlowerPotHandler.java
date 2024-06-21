/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
/*    */ import com.viaversion.viaversion.util.Pair;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */   implements BlockEntityProvider.BlockEntityHandler
/*    */ {
/* 29 */   private static final Map<Pair<?, Byte>, Integer> flowers = new ConcurrentHashMap<>();
/*    */   
/*    */   static {
/* 32 */     register("air", (byte)0, (byte)0, 5265);
/* 33 */     register("sapling", (byte)6, (byte)0, 5266);
/* 34 */     register("sapling", (byte)6, (byte)1, 5267);
/* 35 */     register("sapling", (byte)6, (byte)2, 5268);
/* 36 */     register("sapling", (byte)6, (byte)3, 5269);
/* 37 */     register("sapling", (byte)6, (byte)4, 5270);
/* 38 */     register("sapling", (byte)6, (byte)5, 5271);
/* 39 */     register("tallgrass", (byte)31, (byte)2, 5272);
/* 40 */     register("yellow_flower", (byte)37, (byte)0, 5273);
/* 41 */     register("red_flower", (byte)38, (byte)0, 5274);
/* 42 */     register("red_flower", (byte)38, (byte)1, 5275);
/* 43 */     register("red_flower", (byte)38, (byte)2, 5276);
/* 44 */     register("red_flower", (byte)38, (byte)3, 5277);
/* 45 */     register("red_flower", (byte)38, (byte)4, 5278);
/* 46 */     register("red_flower", (byte)38, (byte)5, 5279);
/* 47 */     register("red_flower", (byte)38, (byte)6, 5280);
/* 48 */     register("red_flower", (byte)38, (byte)7, 5281);
/* 49 */     register("red_flower", (byte)38, (byte)8, 5282);
/* 50 */     register("red_mushroom", (byte)40, (byte)0, 5283);
/* 51 */     register("brown_mushroom", (byte)39, (byte)0, 5284);
/* 52 */     register("deadbush", (byte)32, (byte)0, 5285);
/* 53 */     register("cactus", (byte)81, (byte)0, 5286);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void register(String identifier, byte numbericBlockId, byte blockData, int newId) {
/* 58 */     flowers.put(new Pair(identifier, Byte.valueOf(blockData)), Integer.valueOf(newId));
/* 59 */     flowers.put(new Pair(Byte.valueOf(numbericBlockId), Byte.valueOf(blockData)), Integer.valueOf(newId));
/*    */   }
/*    */ 
/*    */   
/*    */   public int transform(UserConnection user, CompoundTag tag) {
/* 64 */     Object item = tag.contains("Item") ? tag.get("Item").getValue() : null;
/* 65 */     Object data = tag.contains("Data") ? tag.get("Data").getValue() : null;
/*    */ 
/*    */     
/* 68 */     if (item instanceof String) {
/* 69 */       item = ((String)item).replace("minecraft:", "");
/* 70 */     } else if (item instanceof Number) {
/* 71 */       item = Byte.valueOf(((Number)item).byteValue());
/*    */     } else {
/* 73 */       item = Byte.valueOf((byte)0);
/*    */     } 
/*    */ 
/*    */     
/* 77 */     if (data instanceof Number) {
/* 78 */       data = Byte.valueOf(((Number)data).byteValue());
/*    */     } else {
/* 80 */       data = Byte.valueOf((byte)0);
/*    */     } 
/*    */     
/* 83 */     Integer flower = flowers.get(new Pair(item, Byte.valueOf(((Byte)data).byteValue())));
/* 84 */     if (flower != null) return flower.intValue(); 
/* 85 */     flower = flowers.get(new Pair(item, Byte.valueOf((byte)0)));
/* 86 */     if (flower != null) return flower.intValue();
/*    */     
/* 88 */     return 5265;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\providers\blockentities\FlowerPotHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */