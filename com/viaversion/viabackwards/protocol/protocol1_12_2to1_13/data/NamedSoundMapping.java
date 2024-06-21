/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;
/*    */ 
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.NamedSoundRewriter;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class NamedSoundMapping
/*    */ {
/* 26 */   private static final Map<String, String> SOUNDS = new HashMap<>();
/*    */   
/*    */   static {
/*    */     try {
/* 30 */       Field field = NamedSoundRewriter.class.getDeclaredField("oldToNew");
/* 31 */       field.setAccessible(true);
/* 32 */       Map<String, String> sounds = (Map<String, String>)field.get(null);
/* 33 */       sounds.forEach((sound1_12, sound1_13) -> SOUNDS.put(sound1_13, sound1_12));
/* 34 */     } catch (NoSuchFieldException|IllegalAccessException ex) {
/* 35 */       ex.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getOldId(String sound1_13) {
/* 40 */     if (sound1_13.startsWith("minecraft:")) {
/* 41 */       sound1_13 = sound1_13.substring(10);
/*    */     }
/* 43 */     return SOUNDS.get(sound1_13);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\data\NamedSoundMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */