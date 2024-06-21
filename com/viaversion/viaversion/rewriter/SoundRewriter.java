/*    */ package com.viaversion.viaversion.rewriter;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
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
/*    */ public class SoundRewriter<C extends ClientboundPacketType>
/*    */ {
/*    */   protected final Protocol<C, ?, ?, ?> protocol;
/*    */   protected final IdRewriteFunction idRewriter;
/*    */   
/*    */   public SoundRewriter(Protocol<C, ?, ?, ?> protocol) {
/* 31 */     this.protocol = protocol;
/* 32 */     this.idRewriter = (id -> protocol.getMappingData().getSoundMappings().getNewId(id));
/*    */   }
/*    */   
/*    */   public SoundRewriter(Protocol<C, ?, ?, ?> protocol, IdRewriteFunction idRewriter) {
/* 36 */     this.protocol = protocol;
/* 37 */     this.idRewriter = idRewriter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerSound(C packetType) {
/* 42 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 45 */             map((Type)Type.VAR_INT);
/* 46 */             handler(SoundRewriter.this.getSoundHandler());
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void register1_19_3Sound(C packetType) {
/* 53 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*    */           int soundId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*    */           if (soundId == 0) {
/*    */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*    */             return;
/*    */           } 
/*    */           int mappedId = this.idRewriter.rewrite(soundId - 1);
/*    */           if (mappedId == -1) {
/*    */             wrapper.cancel();
/*    */             return;
/*    */           } 
/*    */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(mappedId + 1));
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PacketHandler getSoundHandler() {
/* 72 */     return wrapper -> {
/*    */         int soundId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*    */         int mappedId = this.idRewriter.rewrite(soundId);
/*    */         if (mappedId == -1) {
/*    */           wrapper.cancel();
/*    */         } else if (soundId != mappedId) {
/*    */           wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(mappedId));
/*    */         } 
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\SoundRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */