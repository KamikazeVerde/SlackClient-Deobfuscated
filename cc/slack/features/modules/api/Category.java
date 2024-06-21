/*    */ package cc.slack.features.modules.api;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Category
/*    */ {
/*    */   final String name;
/*    */   final String icon;
/* 11 */   COMBAT(new Color(213, 35, 106)),
/* 12 */   MOVEMENT(new Color(213, 106, 35)),
/* 13 */   PLAYER(new Color(35, 213, 106)),
/* 14 */   WORLD(new Color(106, 213, 35)),
/* 15 */   EXPLOIT(new Color(213, 35, 35)),
/* 16 */   RENDER(new Color(106, 35, 213)),
/* 17 */   GHOST(new Color(0, 0, 0)),
/* 18 */   UTILITIES(new Color(0, 128, 128)),
/* 19 */   OTHER(new Color(102, 30, 200)); final Color color;
/*    */   public String getName() {
/* 21 */     return this.name; }
/* 22 */   public String getIcon() { return this.icon; } public Color getColor() {
/* 23 */     return this.color;
/*    */   }
/*    */   Category(Color color) {
/* 26 */     this.color = color;
/* 27 */     this.name = name().charAt(0) + name().substring(1).toLowerCase();
/* 28 */     this.icon = name().substring(0, 1);
/*    */   }
/*    */   
/*    */   public int getColorRGB() {
/* 32 */     return getColor().getRGB();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\api\Category.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */