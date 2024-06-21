/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum TextureType
/*    */ {
/*  5 */   TEXTURE_1D(3552),
/*  6 */   TEXTURE_2D(3553),
/*  7 */   TEXTURE_3D(32879),
/*  8 */   TEXTURE_RECTANGLE(34037);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   TextureType(int id) {
/* 14 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 19 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\texture\TextureType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */