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
/*    */ public class Particle1_13Type
/*    */   extends AbstractParticleType
/*    */ {
/*    */   public Particle1_13Type() {
/* 31 */     this.readers.put(3, blockHandler());
/* 32 */     this.readers.put(20, blockHandler());
/* 33 */     this.readers.put(11, dustHandler());
/* 34 */     this.readers.put(27, itemHandler(Type.FLAT_ITEM));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\Particle1_13Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */