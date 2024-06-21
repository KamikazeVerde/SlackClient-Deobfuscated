/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
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
/*     */ public class ParticleRewriter
/*     */ {
/*  33 */   private static final List<NewParticle> particles = new ArrayList<>();
/*     */   
/*     */   static {
/*  36 */     add(34);
/*  37 */     add(19);
/*  38 */     add(18);
/*  39 */     add(21);
/*  40 */     add(4);
/*  41 */     add(43);
/*  42 */     add(22);
/*  43 */     add(42);
/*  44 */     add(42);
/*  45 */     add(6);
/*  46 */     add(14);
/*  47 */     add(37);
/*  48 */     add(30);
/*  49 */     add(12);
/*  50 */     add(26);
/*  51 */     add(17);
/*  52 */     add(0);
/*  53 */     add(44);
/*  54 */     add(10);
/*  55 */     add(9);
/*  56 */     add(1);
/*  57 */     add(24);
/*  58 */     add(32);
/*  59 */     add(33);
/*  60 */     add(35);
/*  61 */     add(15);
/*  62 */     add(23);
/*  63 */     add(31);
/*  64 */     add(-1);
/*  65 */     add(5);
/*  66 */     add(11, reddustHandler());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     add(29);
/*  72 */     add(34);
/*  73 */     add(28);
/*  74 */     add(25);
/*  75 */     add(2);
/*  76 */     add(27, iconcrackHandler());
/*     */     
/*  78 */     add(3, blockHandler());
/*     */     
/*  80 */     add(3, blockHandler());
/*     */     
/*  82 */     add(36);
/*  83 */     add(-1);
/*  84 */     add(13);
/*  85 */     add(8);
/*  86 */     add(16);
/*  87 */     add(7);
/*  88 */     add(40);
/*  89 */     add(20, blockHandler());
/*     */     
/*  91 */     add(41);
/*  92 */     add(38);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Particle rewriteParticle(int particleId, Integer[] data) {
/* 104 */     if (particleId >= particles.size()) {
/* 105 */       Via.getPlatform().getLogger().severe("Failed to transform particles with id " + particleId + " and data " + Arrays.toString((Object[])data));
/* 106 */       return null;
/*     */     } 
/*     */     
/* 109 */     NewParticle rewrite = particles.get(particleId);
/* 110 */     return rewrite.handle(new Particle(rewrite.getId()), data);
/*     */   }
/*     */   
/*     */   private static void add(int newId) {
/* 114 */     particles.add(new NewParticle(newId, null));
/*     */   }
/*     */   
/*     */   private static void add(int newId, ParticleDataHandler dataHandler) {
/* 118 */     particles.add(new NewParticle(newId, dataHandler));
/*     */   }
/*     */ 
/*     */   
/*     */   private static ParticleDataHandler reddustHandler() {
/* 123 */     return new ParticleDataHandler()
/*     */       {
/*     */         public Particle handler(Particle particle, Integer[] data) {
/* 126 */           particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(ParticleRewriter.randomBool() ? 1.0F : 0.0F)));
/* 127 */           particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(0.0F)));
/* 128 */           particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(ParticleRewriter.randomBool() ? 1.0F : 0.0F)));
/* 129 */           particle.getArguments().add(new Particle.ParticleData((Type)Type.FLOAT, Float.valueOf(1.0F)));
/* 130 */           return particle;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static boolean randomBool() {
/* 136 */     return ThreadLocalRandom.current().nextBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ParticleDataHandler iconcrackHandler() {
/* 141 */     return new ParticleDataHandler()
/*     */       {
/*     */         public Particle handler(Particle particle, Integer[] data) {
/*     */           DataItem dataItem;
/* 145 */           if (data.length == 1) {
/* 146 */             dataItem = new DataItem(data[0].intValue(), (byte)1, (short)0, null);
/* 147 */           } else if (data.length == 2) {
/* 148 */             dataItem = new DataItem(data[0].intValue(), (byte)1, data[1].shortValue(), null);
/*     */           } else {
/* 150 */             return particle;
/*     */           } 
/*     */           
/* 153 */           ((Protocol1_13To1_12_2)Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class)).getItemRewriter().handleItemToClient((Item)dataItem);
/*     */           
/* 155 */           particle.getArguments().add(new Particle.ParticleData(Type.FLAT_ITEM, dataItem));
/* 156 */           return particle;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static ParticleDataHandler blockHandler() {
/* 163 */     return new ParticleDataHandler()
/*     */       {
/*     */         public Particle handler(Particle particle, Integer[] data) {
/* 166 */           int value = data[0].intValue();
/* 167 */           int combined = (value & 0xFFF) << 4 | value >> 12 & 0xF;
/* 168 */           int newId = WorldPackets.toNewId(combined);
/*     */           
/* 170 */           particle.getArguments().add(new Particle.ParticleData((Type)Type.VAR_INT, Integer.valueOf(newId)));
/* 171 */           return particle;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static interface ParticleDataHandler {
/*     */     Particle handler(Particle param1Particle, Integer[] param1ArrayOfInteger);
/*     */   }
/*     */   
/*     */   private static class NewParticle {
/*     */     private final int id;
/*     */     private final ParticleRewriter.ParticleDataHandler handler;
/*     */     
/*     */     public NewParticle(int id, ParticleRewriter.ParticleDataHandler handler) {
/* 185 */       this.id = id;
/* 186 */       this.handler = handler;
/*     */     }
/*     */     
/*     */     public Particle handle(Particle particle, Integer[] data) {
/* 190 */       if (this.handler != null)
/* 191 */         return this.handler.handler(particle, data); 
/* 192 */       return particle;
/*     */     }
/*     */     
/*     */     public int getId() {
/* 196 */       return this.id;
/*     */     }
/*     */     
/*     */     public ParticleRewriter.ParticleDataHandler getHandler() {
/* 200 */       return this.handler;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\ParticleRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */