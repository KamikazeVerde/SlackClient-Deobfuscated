/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.type.types.minecraft.MetaTypeTemplate;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class MetadataType
/*    */   extends MetaTypeTemplate {
/*    */   public Metadata read(ByteBuf buffer) throws Exception {
/* 10 */     byte item = buffer.readByte();
/* 11 */     if (item == Byte.MAX_VALUE) {
/* 12 */       return null;
/*    */     }
/* 14 */     int typeID = (item & 0xE0) >> 5;
/* 15 */     MetaType1_7_6_10 type = MetaType1_7_6_10.byId(typeID);
/* 16 */     int id = item & 0x1F;
/* 17 */     return new Metadata(id, type, type.type().read(buffer));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Metadata meta) throws Exception {
/* 23 */     int item = (meta.metaType().typeId() << 5 | meta.id() & 0x1F) & 0xFF;
/* 24 */     buffer.writeByte(item);
/* 25 */     meta.metaType().type().write(buffer, meta.getValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\MetadataType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */