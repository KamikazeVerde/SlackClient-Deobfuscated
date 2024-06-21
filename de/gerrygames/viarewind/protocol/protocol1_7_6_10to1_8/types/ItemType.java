/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class ItemType
/*    */   extends Type<Item> {
/*    */   public ItemType(boolean compressed) {
/* 12 */     super(Item.class);
/* 13 */     this.compressed = compressed;
/*    */   }
/*    */   private final boolean compressed;
/*    */   
/*    */   public Item read(ByteBuf buffer) throws Exception {
/* 18 */     int readerIndex = buffer.readerIndex();
/* 19 */     short id = buffer.readShort();
/* 20 */     if (id < 0) {
/* 21 */       return null;
/*    */     }
/* 23 */     DataItem dataItem = new DataItem();
/* 24 */     dataItem.setIdentifier(id);
/* 25 */     dataItem.setAmount(buffer.readByte());
/* 26 */     dataItem.setData(buffer.readShort());
/* 27 */     dataItem.setTag((CompoundTag)(this.compressed ? Types1_7_6_10.COMPRESSED_NBT : Types1_7_6_10.NBT).read(buffer));
/* 28 */     return (Item)dataItem;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Item item) throws Exception {
/* 33 */     if (item == null) {
/* 34 */       buffer.writeShort(-1);
/*    */     } else {
/* 36 */       buffer.writeShort(item.identifier());
/* 37 */       buffer.writeByte(item.amount());
/* 38 */       buffer.writeShort(item.data());
/* 39 */       (this.compressed ? Types1_7_6_10.COMPRESSED_NBT : Types1_7_6_10.NBT).write(buffer, item.tag());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\ItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */