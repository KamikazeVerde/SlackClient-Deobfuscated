/*    */ package com.viaversion.viaversion.libs.opennbt.conversion.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ 
/*    */ 
/*    */ public class ByteArrayTagConverter
/*    */   implements TagConverter<ByteArrayTag, byte[]>
/*    */ {
/*    */   public byte[] convert(ByteArrayTag tag) {
/* 12 */     return tag.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteArrayTag convert(byte[] value) {
/* 17 */     return new ByteArrayTag(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\builtin\ByteArrayTagConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */