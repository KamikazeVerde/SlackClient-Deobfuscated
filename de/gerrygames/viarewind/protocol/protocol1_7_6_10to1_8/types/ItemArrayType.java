/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class ItemArrayType extends Type<Item[]> {
/*    */   private final boolean compressed;
/*    */   
/*    */   public ItemArrayType(boolean compressed) {
/* 11 */     super(Item[].class);
/* 12 */     this.compressed = compressed;
/*    */   }
/*    */ 
/*    */   
/*    */   public Item[] read(ByteBuf buffer) throws Exception {
/* 17 */     int amount = Type.SHORT.readPrimitive(buffer);
/* 18 */     Item[] items = new Item[amount];
/*    */     
/* 20 */     for (int i = 0; i < amount; i++) {
/* 21 */       items[i] = (Item)(this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).read(buffer);
/*    */     }
/* 23 */     return items;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Item[] items) throws Exception {
/* 28 */     Type.SHORT.writePrimitive(buffer, (short)items.length);
/* 29 */     for (Item item : items)
/* 30 */       (this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).write(buffer, item); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\ItemArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */