/*     */ package net.minecraft.client.settings;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ 
/*     */ 
/*     */ public class KeyBinding
/*     */   implements Comparable<KeyBinding>
/*     */ {
/*  14 */   private static final List<KeyBinding> keybindArray = Lists.newArrayList();
/*  15 */   private static final IntHashMap<KeyBinding> hash = new IntHashMap();
/*  16 */   private static final Set<String> keybindSet = Sets.newHashSet();
/*     */   
/*     */   private final String keyDescription;
/*     */   private final int keyCodeDefault;
/*     */   private final String keyCategory;
/*     */   
/*     */   public void setPressed(boolean pressed) {
/*  23 */     this.pressed = pressed;
/*     */   }
/*     */   private int keyCode; public boolean pressed;
/*     */   private int pressTime;
/*     */   
/*     */   public static void onTick(int keyCode) {
/*  29 */     if (keyCode != 0) {
/*     */       
/*  31 */       KeyBinding keybinding = (KeyBinding)hash.lookup(keyCode);
/*     */       
/*  33 */       if (keybinding != null)
/*     */       {
/*  35 */         keybinding.pressTime++;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setKeyBindState(int keyCode, boolean pressed) {
/*  42 */     if (keyCode != 0) {
/*     */       
/*  44 */       KeyBinding keybinding = (KeyBinding)hash.lookup(keyCode);
/*     */       
/*  46 */       if (keybinding != null)
/*     */       {
/*  48 */         keybinding.pressed = pressed;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unPressAllKeys() {
/*  55 */     for (KeyBinding keybinding : keybindArray)
/*     */     {
/*  57 */       keybinding.unpressKey();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resetKeyBindingArrayAndHash() {
/*  63 */     hash.clearMap();
/*     */     
/*  65 */     for (KeyBinding keybinding : keybindArray)
/*     */     {
/*  67 */       hash.addKey(keybinding.keyCode, keybinding);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<String> getKeybinds() {
/*  73 */     return keybindSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyBinding(String description, int keyCode, String category) {
/*  78 */     this.keyDescription = description;
/*  79 */     this.keyCode = keyCode;
/*  80 */     this.keyCodeDefault = keyCode;
/*  81 */     this.keyCategory = category;
/*  82 */     keybindArray.add(this);
/*  83 */     hash.addKey(keyCode, this);
/*  84 */     keybindSet.add(category);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeyDown() {
/*  92 */     return this.pressed;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKeyCategory() {
/*  97 */     return this.keyCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPressed() {
/* 106 */     if (this.pressTime == 0)
/*     */     {
/* 108 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 112 */     this.pressTime--;
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unpressKey() {
/* 119 */     this.pressTime = 0;
/* 120 */     this.pressed = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKeyDescription() {
/* 125 */     return this.keyDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKeyCodeDefault() {
/* 130 */     return this.keyCodeDefault;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKeyCode() {
/* 135 */     return this.keyCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeyCode(int keyCode) {
/* 140 */     this.keyCode = keyCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(KeyBinding p_compareTo_1_) {
/* 145 */     int i = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));
/*     */     
/* 147 */     if (i == 0)
/*     */     {
/* 149 */       i = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
/*     */     }
/*     */     
/* 152 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\settings\KeyBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */