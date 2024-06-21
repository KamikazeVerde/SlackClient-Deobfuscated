/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonParser;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
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
/*    */ public class CommandBlockHandler
/*    */   implements BlockEntityProvider.BlockEntityHandler
/*    */ {
/* 33 */   private final Protocol1_13To1_12_2 protocol = (Protocol1_13To1_12_2)Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class);
/*    */ 
/*    */   
/*    */   public int transform(UserConnection user, CompoundTag tag) {
/* 37 */     Tag name = tag.get("CustomName");
/* 38 */     if (name instanceof StringTag) {
/* 39 */       ((StringTag)name).setValue(ChatRewriter.legacyTextToJsonString(((StringTag)name).getValue()));
/*    */     }
/* 41 */     Tag out = tag.get("LastOutput");
/* 42 */     if (out instanceof StringTag) {
/* 43 */       JsonElement value = JsonParser.parseString(((StringTag)out).getValue());
/* 44 */       this.protocol.getComponentRewriter().processText(value);
/* 45 */       ((StringTag)out).setValue(value.toString());
/*    */     } 
/* 47 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\providers\blockentities\CommandBlockHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */