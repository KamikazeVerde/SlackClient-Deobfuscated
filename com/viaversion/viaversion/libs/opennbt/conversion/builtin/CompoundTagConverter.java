/*    */ package com.viaversion.viaversion.libs.opennbt.conversion.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
/*    */ import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompoundTagConverter
/*    */   implements TagConverter<CompoundTag, Map>
/*    */ {
/*    */   public Map convert(CompoundTag tag) {
/* 17 */     Map<String, Object> ret = new HashMap<>();
/* 18 */     Map<String, Tag> tags = tag.getValue();
/* 19 */     for (Map.Entry<String, Tag> entry : tags.entrySet()) {
/* 20 */       ret.put(entry.getKey(), ConverterRegistry.convertToValue(entry.getValue()));
/*    */     }
/*    */     
/* 23 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag convert(Map value) {
/* 28 */     Map<String, Tag> tags = new HashMap<>();
/* 29 */     for (Object na : value.keySet()) {
/* 30 */       String n = (String)na;
/* 31 */       tags.put(n, ConverterRegistry.convertToTag(value.get(n)));
/*    */     } 
/*    */     
/* 34 */     return new CompoundTag(tags);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\builtin\CompoundTagConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */