/*     */ package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacySoundRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
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
/*     */ public class SoundPackets1_12
/*     */   extends LegacySoundRewriter<Protocol1_11_1To1_12>
/*     */ {
/*     */   public SoundPackets1_12(Protocol1_11_1To1_12 protocol) {
/*  30 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  35 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  38 */             map(Type.STRING);
/*  39 */             map((Type)Type.VAR_INT);
/*  40 */             map((Type)Type.INT);
/*  41 */             map((Type)Type.INT);
/*  42 */             map((Type)Type.INT);
/*  43 */             map((Type)Type.FLOAT);
/*  44 */             map((Type)Type.FLOAT);
/*     */           }
/*     */         });
/*     */     
/*  48 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  51 */             map((Type)Type.VAR_INT);
/*  52 */             map((Type)Type.VAR_INT);
/*  53 */             map((Type)Type.INT);
/*  54 */             map((Type)Type.INT);
/*  55 */             map((Type)Type.INT);
/*  56 */             map((Type)Type.FLOAT);
/*  57 */             map((Type)Type.FLOAT);
/*     */             
/*  59 */             handler(wrapper -> {
/*     */                   int oldId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   int newId = SoundPackets1_12.this.handleSounds(oldId);
/*     */                   if (newId == -1) {
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   if (SoundPackets1_12.this.hasPitch(oldId)) {
/*     */                     wrapper.set((Type)Type.FLOAT, 1, Float.valueOf(SoundPackets1_12.this.handlePitch(oldId)));
/*     */                   }
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(newId));
/*     */                 });
/*     */           }
/*     */         });
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
/*     */   protected void registerRewrites() {
/*  84 */     added(26, 277, 1.4F);
/*  85 */     added(27, -1);
/*     */     
/*  87 */     added(72, 70);
/*  88 */     added(73, 70);
/*  89 */     added(74, 70);
/*  90 */     added(75, 70);
/*  91 */     added(80, 70);
/*     */     
/*  93 */     added(150, -1);
/*  94 */     added(151, -1);
/*     */     
/*  96 */     added(152, -1);
/*     */     
/*  98 */     added(195, -1);
/*     */     
/* 100 */     added(274, 198, 0.8F);
/* 101 */     added(275, 199, 0.8F);
/* 102 */     added(276, 200, 0.8F);
/* 103 */     added(277, 201, 0.8F);
/* 104 */     added(278, 191, 0.9F);
/* 105 */     added(279, 203, 1.5F);
/* 106 */     added(280, 202, 0.8F);
/*     */     
/* 108 */     added(319, 133, 0.6F);
/* 109 */     added(320, 134, 1.7F);
/* 110 */     added(321, 219, 1.5F);
/* 111 */     added(322, 136, 0.7F);
/* 112 */     added(323, 135, 1.6F);
/* 113 */     added(324, 138, 1.5F);
/* 114 */     added(325, 163, 1.5F);
/* 115 */     added(326, 170, 1.5F);
/* 116 */     added(327, 178, 1.5F);
/* 117 */     added(328, 186, 1.5F);
/* 118 */     added(329, 192, 1.5F);
/* 119 */     added(330, 198, 1.5F);
/* 120 */     added(331, 226, 1.5F);
/* 121 */     added(332, 259, 1.5F);
/* 122 */     added(333, 198, 1.3F);
/* 123 */     added(334, 291, 1.5F);
/* 124 */     added(335, 321, 1.5F);
/* 125 */     added(336, 337, 1.5F);
/* 126 */     added(337, 347, 1.5F);
/* 127 */     added(338, 351, 1.5F);
/* 128 */     added(339, 363, 1.5F);
/* 129 */     added(340, 376, 1.5F);
/* 130 */     added(341, 385, 1.5F);
/* 131 */     added(342, 390, 1.5F);
/* 132 */     added(343, 400, 1.5F);
/* 133 */     added(344, 403, 1.5F);
/* 134 */     added(345, 408, 1.5F);
/* 135 */     added(346, 414, 1.5F);
/* 136 */     added(347, 418, 1.5F);
/* 137 */     added(348, 427, 1.5F);
/* 138 */     added(349, 438, 1.5F);
/* 139 */     added(350, 442, 1.5F);
/* 140 */     added(351, 155);
/*     */     
/* 142 */     added(368, 316);
/* 143 */     added(369, 316);
/*     */ 
/*     */     
/* 146 */     added(544, -1);
/* 147 */     added(545, -1);
/* 148 */     added(546, 317, 1.5F);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11_1to1_12\packets\SoundPackets1_12.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */