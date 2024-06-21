/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ 
/*    */ public class KeyUtils
/*    */ {
/*    */   public static void fixKeyConflicts(KeyBinding[] keys, KeyBinding[] keysPrio) {
/* 12 */     Set<Integer> set = new HashSet<>();
/*    */     
/* 14 */     for (KeyBinding keybinding : keysPrio) {
/* 15 */       set.add(Integer.valueOf(keybinding.getKeyCode()));
/*    */     }
/*    */     
/* 18 */     Set<KeyBinding> set1 = new HashSet<>(Arrays.asList(keys));
/* 19 */     Arrays.<KeyBinding>asList(keysPrio).forEach(set1::remove);
/*    */     
/* 21 */     for (KeyBinding keybinding1 : set1) {
/*    */       
/* 23 */       Integer integer = Integer.valueOf(keybinding1.getKeyCode());
/*    */       
/* 25 */       if (set.contains(integer))
/*    */       {
/* 27 */         keybinding1.setKeyCode(0);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\KeyUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */