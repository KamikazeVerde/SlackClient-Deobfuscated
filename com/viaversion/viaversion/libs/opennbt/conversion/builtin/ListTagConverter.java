/*    */ package com.viaversion.viaversion.libs.opennbt.conversion.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListTagConverter
/*    */   implements TagConverter<ListTag, List>
/*    */ {
/*    */   public List convert(ListTag tag) {
/* 17 */     List<Object> ret = new ArrayList();
/* 18 */     List<? extends Tag> tags = tag.getValue();
/* 19 */     for (Tag t : tags) {
/* 20 */       ret.add(ConverterRegistry.convertToValue(t));
/*    */     }
/*    */     
/* 23 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public ListTag convert(List value) {
/* 28 */     List<Tag> tags = new ArrayList<>();
/* 29 */     for (Object o : value) {
/* 30 */       tags.add(ConverterRegistry.convertToTag(o));
/*    */     }
/*    */     
/* 33 */     return new ListTag(tags);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\builtin\ListTagConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */