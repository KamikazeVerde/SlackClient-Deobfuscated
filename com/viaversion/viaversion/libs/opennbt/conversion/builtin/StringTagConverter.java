/*    */ package com.viaversion.viaversion.libs.opennbt.conversion.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ 
/*    */ 
/*    */ public class StringTagConverter
/*    */   implements TagConverter<StringTag, String>
/*    */ {
/*    */   public String convert(StringTag tag) {
/* 12 */     return tag.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public StringTag convert(String value) {
/* 17 */     return new StringTag(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\builtin\StringTagConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */