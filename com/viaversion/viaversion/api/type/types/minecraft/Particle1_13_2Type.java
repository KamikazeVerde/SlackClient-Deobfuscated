/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.Particle;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public class Particle1_13_2Type
/*    */   extends Type<Particle>
/*    */ {
/*    */   public Particle1_13_2Type() {
/* 33 */     super("Particle", Particle.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Particle object) throws Exception {
/* 38 */     Type.VAR_INT.writePrimitive(buffer, object.getId());
/* 39 */     for (Particle.ParticleData data : object.getArguments()) {
/* 40 */       data.getType().write(buffer, data.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public Particle read(ByteBuf buffer) throws Exception {
/* 45 */     int type = Type.VAR_INT.readPrimitive(buffer);
/* 46 */     Particle particle = new Particle(type);
/*    */     
/* 48 */     switch (type) {
/*    */       
/*    */       case 3:
/*    */       case 20:
/* 52 */         particle.getArguments().add(new Particle.ParticleData((Type)Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buffer))));
/*    */         break;
/*    */       
/*    */       case 11:
/* 56 */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buffer))));
/* 57 */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buffer))));
/* 58 */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buffer))));
/* 59 */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buffer))));
/*    */         break;
/*    */       
/*    */       case 27:
/* 63 */         particle.getArguments().add(new Particle.ParticleData(Type.FLAT_VAR_INT_ITEM, Type.FLAT_VAR_INT_ITEM.read(buffer)));
/*    */         break;
/*    */     } 
/* 66 */     return particle;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\Particle1_13_2Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */