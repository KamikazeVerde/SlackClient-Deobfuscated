/*    */ package com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*    */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
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
/*    */ public class Protocol1_14_2To1_14_3
/*    */   extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14>
/*    */ {
/*    */   public Protocol1_14_2To1_14_3() {
/* 29 */     super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 34 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_14.TRADE_LIST, wrapper -> {
/*    */           wrapper.passthrough((Type)Type.VAR_INT);
/*    */           
/*    */           int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*    */           
/*    */           for (int i = 0; i < size; i++) {
/*    */             wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
/*    */             wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
/*    */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*    */               wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
/*    */             }
/*    */             wrapper.passthrough((Type)Type.BOOLEAN);
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             wrapper.passthrough((Type)Type.FLOAT);
/*    */           } 
/*    */           wrapper.passthrough((Type)Type.VAR_INT);
/*    */           wrapper.passthrough((Type)Type.VAR_INT);
/*    */           wrapper.passthrough((Type)Type.BOOLEAN);
/*    */           wrapper.read((Type)Type.BOOLEAN);
/*    */         });
/* 57 */     RecipeRewriter<ClientboundPackets1_14> recipeHandler = new RecipeRewriter((Protocol)this);
/* 58 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_14.DECLARE_RECIPES, wrapper -> {
/*    */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */           int deleted = 0;
/*    */           for (int i = 0; i < size; i++) {
/*    */             String fullType = (String)wrapper.read(Type.STRING);
/*    */             String type = fullType.replace("minecraft:", "");
/*    */             String id = (String)wrapper.read(Type.STRING);
/*    */             if (type.equals("crafting_special_repairitem")) {
/*    */               deleted++;
/*    */             } else {
/*    */               wrapper.write(Type.STRING, fullType);
/*    */               wrapper.write(Type.STRING, id);
/*    */               recipeHandler.handleRecipeType(wrapper, type);
/*    */             } 
/*    */           } 
/*    */           wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(size - deleted));
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_14_2to1_14_3\Protocol1_14_2To1_14_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */