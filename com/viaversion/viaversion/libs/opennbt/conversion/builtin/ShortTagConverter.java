/*    */ package com.viaversion.viaversion.libs.opennbt.conversion.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ 
/*    */ 
/*    */ public class ShortTagConverter
/*    */   implements TagConverter<ShortTag, Short>
/*    */ {
/*    */   public Short convert(ShortTag tag) {
/* 12 */     return tag.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public ShortTag convert(Short value) {
/* 17 */     return new ShortTag(value.shortValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\builtin\ShortTagConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */