/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
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
/*     */ public class SoundRewriter<C extends ClientboundPacketType>
/*     */   extends SoundRewriter<C>
/*     */ {
/*     */   private final BackwardsProtocol<C, ?, ?, ?> protocol;
/*     */   
/*     */   public SoundRewriter(BackwardsProtocol<C, ?, ?, ?> protocol) {
/*  31 */     super((Protocol)protocol);
/*  32 */     this.protocol = protocol;
/*     */   }
/*     */   
/*     */   public void registerNamedSound(C packetType) {
/*  36 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  39 */             map(Type.STRING);
/*  40 */             handler(SoundRewriter.this.getNamedSoundHandler());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerStopSound(C packetType) {
/*  46 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  49 */             handler(SoundRewriter.this.getStopSoundHandler());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public PacketHandler getNamedSoundHandler() {
/*  55 */     return wrapper -> {
/*     */         String soundId = (String)wrapper.get(Type.STRING, 0);
/*     */         String mappedId = this.protocol.getMappingData().getMappedNamedSound(soundId);
/*     */         if (mappedId == null) {
/*     */           return;
/*     */         }
/*     */         if (!mappedId.isEmpty()) {
/*     */           wrapper.set(Type.STRING, 0, mappedId);
/*     */         } else {
/*     */           wrapper.cancel();
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketHandler getStopSoundHandler() {
/*  71 */     return wrapper -> {
/*     */         byte flags = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */         if ((flags & 0x2) == 0) {
/*     */           return;
/*     */         }
/*     */         if ((flags & 0x1) != 0) {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */         }
/*     */         String soundId = (String)wrapper.read(Type.STRING);
/*     */         String mappedId = this.protocol.getMappingData().getMappedNamedSound(soundId);
/*     */         if (mappedId == null) {
/*     */           wrapper.write(Type.STRING, soundId);
/*     */           return;
/*     */         } 
/*     */         if (!mappedId.isEmpty()) {
/*     */           wrapper.write(Type.STRING, mappedId);
/*     */         } else {
/*     */           wrapper.cancel();
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register1_19_3Sound(C packetType) {
/*  98 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, get1_19_3SoundHandler());
/*     */   }
/*     */   
/*     */   public PacketHandler get1_19_3SoundHandler() {
/* 102 */     return wrapper -> {
/*     */         int soundId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */         if (soundId != 0) {
/*     */           int mappedId = this.idRewriter.rewrite(soundId - 1);
/*     */           if (mappedId == -1) {
/*     */             wrapper.cancel();
/*     */             return;
/*     */           } 
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(mappedId + 1));
/*     */           return;
/*     */         } 
/*     */         wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */         String soundIdentifier = (String)wrapper.read(Type.STRING);
/*     */         String mappedIdentifier = this.protocol.getMappingData().getMappedNamedSound(soundIdentifier);
/*     */         if (mappedIdentifier != null) {
/*     */           if (mappedIdentifier.isEmpty()) {
/*     */             wrapper.cancel();
/*     */             return;
/*     */           } 
/*     */           soundIdentifier = mappedIdentifier;
/*     */         } 
/*     */         wrapper.write(Type.STRING, soundIdentifier);
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\SoundRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */