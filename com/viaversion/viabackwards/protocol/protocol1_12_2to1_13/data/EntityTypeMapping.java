/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
/*    */ import java.lang.reflect.Field;
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
/*    */ public class EntityTypeMapping
/*    */ {
/* 26 */   private static final Int2IntMap TYPES = (Int2IntMap)new Int2IntOpenHashMap();
/*    */   
/*    */   static {
/* 29 */     TYPES.defaultReturnValue(-1);
/*    */     try {
/* 31 */       Field field = EntityTypeRewriter.class.getDeclaredField("ENTITY_TYPES");
/* 32 */       field.setAccessible(true);
/* 33 */       Int2IntMap entityTypes = (Int2IntMap)field.get(null);
/* 34 */       for (ObjectIterator<Int2IntMap.Entry> objectIterator = entityTypes.int2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Int2IntMap.Entry entry = objectIterator.next();
/* 35 */         TYPES.put(entry.getIntValue(), entry.getIntKey()); }
/*    */     
/* 37 */     } catch (NoSuchFieldException|IllegalAccessException ex) {
/* 38 */       ex.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getOldId(int type1_13) {
/* 43 */     return TYPES.get(type1_13);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\data\EntityTypeMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */