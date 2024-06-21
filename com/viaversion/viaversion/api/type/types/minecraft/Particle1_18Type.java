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
/*    */ public class Particle1_18Type
/*    */   extends AbstractParticleType
/*    */ {
/*    */   public Particle1_18Type() {
/* 31 */     this.readers.put(2, blockHandler());
/* 32 */     this.readers.put(3, blockHandler());
/* 33 */     this.readers.put(24, blockHandler());
/* 34 */     this.readers.put(14, dustHandler());
/* 35 */     this.readers.put(15, dustTransitionHandler());
/* 36 */     this.readers.put(35, itemHandler(Type.FLAT_VAR_INT_ITEM));
/* 37 */     this.readers.put(36, vibrationHandler(Type.POSITION1_14));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\Particle1_18Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */