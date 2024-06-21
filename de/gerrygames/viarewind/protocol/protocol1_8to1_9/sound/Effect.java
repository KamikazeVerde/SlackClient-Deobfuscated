/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Effect {
/*  6 */   private static final HashMap<Integer, Integer> effects = new HashMap<>();
/*    */   
/*    */   public static int getOldId(int id) {
/*  9 */     return ((Integer)effects.getOrDefault(Integer.valueOf(id), Integer.valueOf(id))).intValue();
/*    */   }
/*    */   
/*    */   static {
/* 13 */     effects.put(Integer.valueOf(1003), Integer.valueOf(1002));
/* 14 */     effects.put(Integer.valueOf(1005), Integer.valueOf(1003));
/* 15 */     effects.put(Integer.valueOf(1006), Integer.valueOf(1003));
/* 16 */     effects.put(Integer.valueOf(1007), Integer.valueOf(1003));
/* 17 */     effects.put(Integer.valueOf(1008), Integer.valueOf(1003));
/* 18 */     effects.put(Integer.valueOf(1009), Integer.valueOf(1004));
/* 19 */     effects.put(Integer.valueOf(1010), Integer.valueOf(1005));
/* 20 */     effects.put(Integer.valueOf(1011), Integer.valueOf(1006));
/* 21 */     effects.put(Integer.valueOf(1012), Integer.valueOf(1006));
/* 22 */     effects.put(Integer.valueOf(1013), Integer.valueOf(1006));
/* 23 */     effects.put(Integer.valueOf(1014), Integer.valueOf(1006));
/* 24 */     effects.put(Integer.valueOf(1015), Integer.valueOf(1007));
/* 25 */     effects.put(Integer.valueOf(1016), Integer.valueOf(1008));
/* 26 */     effects.put(Integer.valueOf(1017), Integer.valueOf(1008));
/* 27 */     effects.put(Integer.valueOf(1018), Integer.valueOf(1009));
/* 28 */     effects.put(Integer.valueOf(1019), Integer.valueOf(1010));
/* 29 */     effects.put(Integer.valueOf(1020), Integer.valueOf(1011));
/* 30 */     effects.put(Integer.valueOf(1021), Integer.valueOf(1012));
/* 31 */     effects.put(Integer.valueOf(1022), Integer.valueOf(1012));
/* 32 */     effects.put(Integer.valueOf(1023), Integer.valueOf(1013));
/* 33 */     effects.put(Integer.valueOf(1024), Integer.valueOf(1014));
/* 34 */     effects.put(Integer.valueOf(1025), Integer.valueOf(1015));
/* 35 */     effects.put(Integer.valueOf(1026), Integer.valueOf(1016));
/* 36 */     effects.put(Integer.valueOf(1027), Integer.valueOf(1017));
/* 37 */     effects.put(Integer.valueOf(1028), Integer.valueOf(1018));
/* 38 */     effects.put(Integer.valueOf(1029), Integer.valueOf(1020));
/* 39 */     effects.put(Integer.valueOf(1030), Integer.valueOf(1021));
/* 40 */     effects.put(Integer.valueOf(1031), Integer.valueOf(1022));
/* 41 */     effects.put(Integer.valueOf(1032), Integer.valueOf(-1));
/* 42 */     effects.put(Integer.valueOf(1033), Integer.valueOf(-1));
/* 43 */     effects.put(Integer.valueOf(1034), Integer.valueOf(-1));
/* 44 */     effects.put(Integer.valueOf(1035), Integer.valueOf(-1));
/* 45 */     effects.put(Integer.valueOf(1036), Integer.valueOf(1003));
/* 46 */     effects.put(Integer.valueOf(1037), Integer.valueOf(1006));
/*    */     
/* 48 */     effects.put(Integer.valueOf(3000), Integer.valueOf(-1));
/* 49 */     effects.put(Integer.valueOf(3001), Integer.valueOf(-1));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\sound\Effect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */