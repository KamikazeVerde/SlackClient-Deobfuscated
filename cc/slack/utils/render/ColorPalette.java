/*    */ package cc.slack.utils.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum ColorPalette
/*    */ {
/*    */   final Color color;
/* 10 */   Background(new Color(20, 20, 20)),
/* 11 */   Primary(new Color(35, 35, 35)),
/* 12 */   Secondary(new Color(16, 16, 16)); public Color getColor() {
/* 13 */     return this.color;
/*    */   }
/*    */   ColorPalette(Color color) {
/* 16 */     this.color = color;
/*    */   }
/*    */   
/*    */   public int getRGB() {
/* 20 */     return this.color.getRGB();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\render\ColorPalette.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */