/*    */ package de.gerrygames.viarewind.replacement;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ 
/*    */ public class ReplacementRegistry {
/*  8 */   private final Int2ObjectMap<Replacement> itemReplacements = (Int2ObjectMap<Replacement>)new Int2ObjectOpenHashMap();
/*  9 */   private final Int2ObjectMap<Replacement> blockReplacements = (Int2ObjectMap<Replacement>)new Int2ObjectOpenHashMap();
/*    */ 
/*    */   
/*    */   public void registerItem(int id, Replacement replacement) {
/* 13 */     registerItem(id, -1, replacement);
/*    */   }
/*    */   
/*    */   public void registerBlock(int id, Replacement replacement) {
/* 17 */     registerBlock(id, -1, replacement);
/*    */   }
/*    */   
/*    */   public void registerItemBlock(int id, Replacement replacement) {
/* 21 */     registerItemBlock(id, -1, replacement);
/*    */   }
/*    */   
/*    */   public void registerItem(int id, int data, Replacement replacement) {
/* 25 */     this.itemReplacements.put(combine(id, data), replacement);
/*    */   }
/*    */   
/*    */   public void registerBlock(int id, int data, Replacement replacement) {
/* 29 */     this.blockReplacements.put(combine(id, data), replacement);
/*    */   }
/*    */   
/*    */   public void registerItemBlock(int id, int data, Replacement replacement) {
/* 33 */     registerItem(id, data, replacement);
/* 34 */     registerBlock(id, data, replacement);
/*    */   }
/*    */   
/*    */   public Item replace(Item item) {
/* 38 */     Replacement replacement = (Replacement)this.itemReplacements.get(combine(item.identifier(), item.data()));
/* 39 */     if (replacement == null) replacement = (Replacement)this.itemReplacements.get(combine(item.identifier(), -1)); 
/* 40 */     return (replacement == null) ? item : replacement.replace(item);
/*    */   }
/*    */   
/*    */   public Replacement replace(int id, int data) {
/* 44 */     Replacement replacement = (Replacement)this.blockReplacements.get(combine(id, data));
/* 45 */     if (replacement == null) {
/* 46 */       replacement = (Replacement)this.blockReplacements.get(combine(id, -1));
/*    */     }
/* 48 */     return replacement;
/*    */   }
/*    */   
/*    */   public static int combine(int id, int data) {
/* 52 */     return id << 16 | data & 0xFFFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\replacement\ReplacementRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */