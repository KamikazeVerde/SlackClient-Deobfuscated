/*    */ package cc.slack.ui.NewCGUI.components;
/*    */ 
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ public abstract class Components {
/*    */   private int posX;
/*    */   private int posY;
/*    */   private int width;
/*    */   
/* 10 */   public void setPosX(int posX) { this.posX = posX; } private int height; private int offsetY; private Components parent; public void setPosY(int posY) { this.posY = posY; } public void setWidth(int width) { this.width = width; } public void setHeight(int height) { this.height = height; } public void setOffsetY(int offsetY) { this.offsetY = offsetY; } public void setParent(Components parent) { this.parent = parent; }
/*    */ 
/*    */   
/* 13 */   public int getPosX() { return this.posX; } public int getPosY() { return this.posY; } public int getWidth() { return this.width; } public int getHeight() { return this.height; } public int getOffsetY() { return this.offsetY; } public Components getParent() {
/* 14 */     return this.parent;
/*    */   }
/*    */   public ScaledResolution getSR() {
/* 17 */     return new ScaledResolution(mc.getMinecraft());
/*    */   }
/*    */   
/*    */   public abstract void init();
/*    */   
/*    */   public abstract void update(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract void draw(FontRenderer paramFontRenderer, int paramInt1, int paramInt2, float paramFloat);
/*    */   
/*    */   public abstract void mouseClicked(int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   public abstract void mouseReleased(int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   public abstract void keyClicked(char paramChar, int paramInt);
/*    */   
/*    */   public abstract void close();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\NewCGUI\components\Components.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */