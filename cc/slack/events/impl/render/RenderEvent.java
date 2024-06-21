/*    */ package cc.slack.events.impl.render;
/*    */ 
/*    */ 
/*    */ public class RenderEvent extends Event {
/*    */   public State state;
/*    */   public float partialTicks;
/*    */   
/*  8 */   public void setState(State state) { this.state = state; } public float width; public float height; public void setPartialTicks(float partialTicks) { this.partialTicks = partialTicks; } public void setWidth(float width) { this.width = width; } public void setHeight(float height) { this.height = height; }
/*    */ 
/*    */   
/* 11 */   public State getState() { return this.state; }
/* 12 */   public float getPartialTicks() { return this.partialTicks; } public float getWidth() { return this.width; } public float getHeight() { return this.height; }
/*    */   
/*    */   public RenderEvent(State state, float partialTicks) {
/* 15 */     this.state = state;
/* 16 */     this.partialTicks = partialTicks;
/*    */   }
/*    */   
/*    */   public RenderEvent(State state, float partialTicks, float width, float height) {
/* 20 */     this.state = state;
/* 21 */     this.partialTicks = partialTicks;
/* 22 */     this.width = width;
/* 23 */     this.height = height;
/*    */   }
/*    */   
/*    */   public enum State {
/* 27 */     RENDER_3D, RENDER_2D;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\render\RenderEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */