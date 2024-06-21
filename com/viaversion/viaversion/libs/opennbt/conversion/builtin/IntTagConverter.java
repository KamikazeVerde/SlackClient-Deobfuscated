/*    */ package com.viaversion.viaversion.libs.opennbt.conversion.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ 
/*    */ 
/*    */ public class IntTagConverter
/*    */   implements TagConverter<IntTag, Integer>
/*    */ {
/*    */   public Integer convert(IntTag tag) {
/* 12 */     return tag.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public IntTag convert(Integer value) {
/* 17 */     return new IntTag(value.intValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\builtin\IntTagConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */