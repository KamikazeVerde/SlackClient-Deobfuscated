/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public abstract class ModernMetaType
/*    */   extends MetaTypeTemplate
/*    */ {
/*    */   private static final int END = 255;
/*    */   
/*    */   public Metadata read(ByteBuf buffer) throws Exception {
/* 35 */     short index = buffer.readUnsignedByte();
/* 36 */     if (index == 255) return null; 
/* 37 */     MetaType type = getType(Type.VAR_INT.readPrimitive(buffer));
/* 38 */     return new Metadata(index, type, type.type().read(buffer));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Metadata object) throws Exception {
/* 45 */     if (object == null) {
/* 46 */       buffer.writeByte(255);
/*    */     } else {
/* 48 */       buffer.writeByte(object.id());
/* 49 */       MetaType type = object.metaType();
/* 50 */       Type.VAR_INT.writePrimitive(buffer, type.typeId());
/* 51 */       type.type().write(buffer, object.getValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract MetaType getType(int paramInt);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\ModernMetaType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */