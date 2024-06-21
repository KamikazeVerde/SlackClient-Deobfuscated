/*     */ package com.viaversion.viaversion.api.type.types.minecraft;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.data.ParticleMappings;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleType
/*     */   extends Type<Particle>
/*     */ {
/*     */   private final Int2ObjectMap<ParticleReader> readers;
/*     */   
/*     */   public ParticleType(Int2ObjectMap<ParticleReader> readers) {
/*  41 */     super("Particle", Particle.class);
/*  42 */     this.readers = readers;
/*     */   }
/*     */   
/*     */   public ParticleType() {
/*  46 */     this((Int2ObjectMap<ParticleReader>)new Int2ObjectArrayMap());
/*     */   }
/*     */   
/*     */   public ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol) {
/*  50 */     return filler(protocol, true);
/*     */   }
/*     */   
/*     */   public ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
/*  54 */     return new ParticleTypeFiller(protocol, useMappedNames);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf buffer, Particle object) throws Exception {
/*  59 */     Type.VAR_INT.writePrimitive(buffer, object.getId());
/*  60 */     for (Particle.ParticleData data : object.getArguments()) {
/*  61 */       data.getType().write(buffer, data.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Particle read(ByteBuf buffer) throws Exception {
/*  67 */     int type = Type.VAR_INT.readPrimitive(buffer);
/*  68 */     Particle particle = new Particle(type);
/*     */     
/*  70 */     ParticleReader reader = (ParticleReader)this.readers.get(type);
/*  71 */     if (reader != null) {
/*  72 */       reader.read(buffer, particle);
/*     */     }
/*  74 */     return particle;
/*     */   }
/*     */   
/*     */   public static ParticleReader itemHandler(Type<Item> itemType) {
/*  78 */     return (buf, particle) -> particle.add(itemType, itemType.read(buf));
/*     */   }
/*     */   public static final class Readers { public static final ParticleType.ParticleReader BLOCK;
/*     */     
/*     */     static {
/*  83 */       BLOCK = ((buf, particle) -> particle.add((Type)Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf))));
/*     */     }
/*     */     
/*  86 */     public static final ParticleType.ParticleReader ITEM = ParticleType.itemHandler(Type.FLAT_ITEM); public static final ParticleType.ParticleReader DUST;
/*  87 */     public static final ParticleType.ParticleReader VAR_INT_ITEM = ParticleType.itemHandler(Type.FLAT_VAR_INT_ITEM); public static final ParticleType.ParticleReader DUST_TRANSITION; static {
/*  88 */       DUST = ((buf, particle) -> {
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */         });
/*  94 */       DUST_TRANSITION = ((buf, particle) -> {
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */           particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
/*     */         });
/* 103 */       VIBRATION = ((buf, particle) -> {
/*     */           particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
/*     */           
/*     */           String resourceLocation = (String)Type.STRING.read(buf);
/*     */           particle.add(Type.STRING, resourceLocation);
/*     */           resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
/*     */           if (resourceLocation.equals("block")) {
/*     */             particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
/*     */           } else if (resourceLocation.equals("entity")) {
/*     */             particle.add((Type)Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf)));
/*     */           } else {
/*     */             Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
/*     */           } 
/*     */           particle.add((Type)Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf)));
/*     */         });
/* 118 */       SCULK_CHARGE = ((buf, particle) -> particle.add((Type)Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf))));
/*     */ 
/*     */       
/* 121 */       SHRIEK = ((buf, particle) -> particle.add((Type)Type.VAR_INT, Integer.valueOf(Type.VAR_INT.readPrimitive(buf))));
/*     */     }
/*     */     
/*     */     public static final ParticleType.ParticleReader VIBRATION;
/*     */     public static final ParticleType.ParticleReader SCULK_CHARGE;
/*     */     public static final ParticleType.ParticleReader SHRIEK; }
/*     */   
/*     */   public final class ParticleTypeFiller { private final ParticleMappings mappings;
/*     */     private final boolean useMappedNames;
/*     */     
/*     */     private ParticleTypeFiller(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
/* 132 */       this.mappings = protocol.getMappingData().getParticleMappings();
/* 133 */       this.useMappedNames = useMappedNames;
/*     */     }
/*     */     
/*     */     public ParticleTypeFiller reader(String identifier, ParticleType.ParticleReader reader) {
/* 137 */       ParticleType.this.readers.put(this.useMappedNames ? this.mappings.mappedId(identifier) : this.mappings.id(identifier), reader);
/* 138 */       return this;
/*     */     }
/*     */     
/*     */     public ParticleTypeFiller reader(int id, ParticleType.ParticleReader reader) {
/* 142 */       ParticleType.this.readers.put(id, reader);
/* 143 */       return this;
/*     */     } }
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ParticleReader {
/*     */     void read(ByteBuf param1ByteBuf, Particle param1Particle) throws Exception;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\ParticleType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */