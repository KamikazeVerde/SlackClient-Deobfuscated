/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityList;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class EntityUtils
/*    */ {
/* 11 */   private static final Map<Class, Integer> mapIdByClass = (Map)new HashMap<>();
/* 12 */   private static final Map<String, Integer> mapIdByName = new HashMap<>();
/* 13 */   private static final Map<String, Class> mapClassByName = (Map)new HashMap<>();
/*    */ 
/*    */   
/*    */   public static int getEntityIdByClass(Entity entity) {
/* 17 */     return (entity == null) ? -1 : getEntityIdByClass(entity.getClass());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getEntityIdByClass(Class cls) {
/* 22 */     Integer integer = mapIdByClass.get(cls);
/* 23 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getEntityIdByName(String name) {
/* 28 */     Integer integer = mapIdByName.get(name);
/* 29 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Class getEntityClassByName(String name) {
/* 34 */     return mapClassByName.get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 39 */     for (int i = 0; i < 1000; i++) {
/*    */       
/* 41 */       Class oclass = EntityList.getClassFromID(i);
/*    */       
/* 43 */       if (oclass != null) {
/*    */         
/* 45 */         String s = EntityList.getStringFromID(i);
/*    */         
/* 47 */         if (s != null) {
/*    */           
/* 49 */           if (mapIdByClass.containsKey(oclass))
/*    */           {
/* 51 */             Config.warn("Duplicate entity class: " + oclass + ", id1: " + mapIdByClass.get(oclass) + ", id2: " + i);
/*    */           }
/*    */           
/* 54 */           if (mapIdByName.containsKey(s))
/*    */           {
/* 56 */             Config.warn("Duplicate entity name: " + s + ", id1: " + mapIdByName.get(s) + ", id2: " + i);
/*    */           }
/*    */           
/* 59 */           if (mapClassByName.containsKey(s))
/*    */           {
/* 61 */             Config.warn("Duplicate entity name: " + s + ", class1: " + mapClassByName.get(s) + ", class2: " + oclass);
/*    */           }
/*    */           
/* 64 */           mapIdByClass.put(oclass, Integer.valueOf(i));
/* 65 */           mapIdByName.put(s, Integer.valueOf(i));
/* 66 */           mapClassByName.put(s, oclass);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\EntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */