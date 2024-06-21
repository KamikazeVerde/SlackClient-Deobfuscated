/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class Particle1_17Type
/*    */   extends AbstractParticleType
/*    */ {
/*    */   public Particle1_17Type() {
/* 31 */     this.readers.put(4, blockHandler());
/* 32 */     this.readers.put(25, blockHandler());
/* 33 */     this.readers.put(15, dustHandler());
/* 34 */     this.readers.put(16, dustTransitionHandler());
/* 35 */     this.readers.put(36, itemHandler(Type.FLAT_VAR_INT_ITEM));
/* 36 */     this.readers.put(37, vibrationHandler(Type.POSITION1_14));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\Particle1_17Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */