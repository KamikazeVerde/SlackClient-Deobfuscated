/*    */ package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data;
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
/*    */ 
/*    */ public class BlockColors
/*    */ {
/* 22 */   private static final String[] COLORS = new String[16];
/*    */   
/*    */   static {
/* 25 */     COLORS[0] = "White";
/* 26 */     COLORS[1] = "Orange";
/* 27 */     COLORS[2] = "Magenta";
/* 28 */     COLORS[3] = "Light Blue";
/* 29 */     COLORS[4] = "Yellow";
/* 30 */     COLORS[5] = "Lime";
/* 31 */     COLORS[6] = "Pink";
/* 32 */     COLORS[7] = "Gray";
/* 33 */     COLORS[8] = "Light Gray";
/* 34 */     COLORS[9] = "Cyan";
/* 35 */     COLORS[10] = "Purple";
/* 36 */     COLORS[11] = "Blue";
/* 37 */     COLORS[12] = "Brown";
/* 38 */     COLORS[13] = "Green";
/* 39 */     COLORS[14] = "Red";
/* 40 */     COLORS[15] = "Black";
/*    */   }
/*    */   
/*    */   public static String get(int key) {
/* 44 */     return (key >= 0 && key < COLORS.length) ? COLORS[key] : "Unknown color";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11_1to1_12\data\BlockColors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */