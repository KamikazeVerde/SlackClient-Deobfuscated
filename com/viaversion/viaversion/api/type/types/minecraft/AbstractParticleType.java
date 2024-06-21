/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.Particle;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
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
/*    */ public abstract class AbstractParticleType
/*    */   extends Type<Particle>
/*    */ {
/* 37 */   protected final Int2ObjectMap<ParticleReader> readers = (Int2ObjectMap<ParticleReader>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   protected AbstractParticleType() {
/* 40 */     super("Particle", Particle.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Particle object) throws Exception {
/* 45 */     Type.VAR_INT.writePrimitive(buffer, object.getId());
/* 46 */     for (Particle.ParticleData data : object.getArguments()) {
/* 47 */       data.getType().write(buffer, data.getValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle read(ByteBuf buffer) throws Exception {
/* 53 */     int type = Type.VAR_INT.readPrimitive(buffer);
/* 54 */     Particle particle = new Particle(type);
/*    */     
/* 56 */     ParticleReader reader = (ParticleReader)this.readers.get(type);
/* 57 */     if (reader != null) {
/* 58 */       reader.read(buffer, particle);
/*    */     }
/* 60 */     return particle;
/*    */   }
/*    */   
/*    */   protected ParticleReader blockHandler() {
/* 64 */     return (buf, particle) -> particle.getArguments().add(new Particle.ParticleData((Type)Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ParticleReader itemHandler(Type<Item> itemType) {
/* 70 */     return (buf, particle) -> particle.getArguments().add(new Particle.ParticleData(itemType, itemType.read(buf)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ParticleReader dustHandler() {
/* 76 */     return (buf, particle) -> {
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */       };
/*    */   }
/*    */   
/*    */   protected ParticleReader dustTransitionHandler() {
/* 85 */     return (buf, particle) -> {
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*    */       };
/*    */   }
/*    */   
/*    */   protected ParticleReader vibrationHandler(Type<Position> positionType) {
/* 97 */     return (buf, particle) -> {
/*    */         particle.getArguments().add(new Particle.ParticleData(positionType, positionType.read(buf)));
/*    */         String resourceLocation = (String)Type.STRING.read(buf);
/*    */         if (resourceLocation.equals("block")) {
/*    */           particle.getArguments().add(new Particle.ParticleData(positionType, positionType.read(buf)));
/*    */         } else if (resourceLocation.equals("entity")) {
/*    */           particle.getArguments().add(new Particle.ParticleData((Type)Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf))));
/*    */         } else {
/*    */           Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
/*    */         } 
/*    */         particle.getArguments().add(new Particle.ParticleData((Type)Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf))));
/*    */       };
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface ParticleReader {
/*    */     void read(ByteBuf param1ByteBuf, Particle param1Particle) throws Exception;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\AbstractParticleType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */