/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ 
/*    */ 
/*    */ public abstract class OldMetaType
/*    */   extends MetaTypeTemplate
/*    */ {
/*    */   private static final int END = 127;
/*    */   
/*    */   public Metadata read(ByteBuf buffer) throws Exception {
/* 34 */     byte index = buffer.readByte();
/* 35 */     if (index == Byte.MAX_VALUE) return null; 
/* 36 */     MetaType type = getType((index & 0xE0) >> 5);
/* 37 */     return new Metadata(index & 0x1F, type, type.type().read(buffer));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Metadata object) throws Exception {
/* 44 */     if (object == null) {
/* 45 */       buffer.writeByte(127);
/*    */     } else {
/* 47 */       int index = (object.metaType().typeId() << 5 | object.id() & 0x1F) & 0xFF;
/* 48 */       buffer.writeByte(index);
/* 49 */       object.metaType().type().write(buffer, object.getValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract MetaType getType(int paramInt);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\OldMetaType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */