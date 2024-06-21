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
/*    */ public class Particle1_14Type
/*    */   extends AbstractParticleType
/*    */ {
/*    */   public Particle1_14Type() {
/* 31 */     this.readers.put(3, blockHandler());
/* 32 */     this.readers.put(23, blockHandler());
/* 33 */     this.readers.put(14, dustHandler());
/* 34 */     this.readers.put(32, itemHandler(Type.FLAT_VAR_INT_ITEM));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\Particle1_14Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */