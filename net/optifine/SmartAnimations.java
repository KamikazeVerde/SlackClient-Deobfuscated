/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class SmartAnimations
/*    */ {
/*    */   private static boolean active;
/* 10 */   private static BitSet spritesRendered = new BitSet();
/* 11 */   private static BitSet texturesRendered = new BitSet();
/*    */ 
/*    */   
/*    */   public static boolean isActive() {
/* 15 */     return (active && !Shaders.isShadowPass);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void update() {
/* 20 */     active = (Config.getGameSettings()).ofSmartAnimations;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void spriteRendered(int animationIndex) {
/* 25 */     spritesRendered.set(animationIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void spritesRendered(BitSet animationIndexes) {
/* 30 */     if (animationIndexes != null)
/*    */     {
/* 32 */       spritesRendered.or(animationIndexes);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isSpriteRendered(int animationIndex) {
/* 38 */     return spritesRendered.get(animationIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void resetSpritesRendered() {
/* 43 */     spritesRendered.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void textureRendered(int textureId) {
/* 48 */     if (textureId >= 0)
/*    */     {
/* 50 */       texturesRendered.set(textureId);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isTextureRendered(int texId) {
/* 56 */     return (texId < 0) ? false : texturesRendered.get(texId);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void resetTexturesRendered() {
/* 61 */     texturesRendered.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\SmartAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */