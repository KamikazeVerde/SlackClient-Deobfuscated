/*    */ package com.viaversion.viaversion.libs.opennbt.conversion.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ 
/*    */ 
/*    */ public class LongTagConverter
/*    */   implements TagConverter<LongTag, Long>
/*    */ {
/*    */   public Long convert(LongTag tag) {
/* 12 */     return tag.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public LongTag convert(Long value) {
/* 17 */     return new LongTag(value.longValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\builtin\LongTagConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */