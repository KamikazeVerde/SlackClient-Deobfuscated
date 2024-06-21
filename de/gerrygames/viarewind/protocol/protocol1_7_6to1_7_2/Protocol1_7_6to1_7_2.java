/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6to1_7_2;
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.State;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
/*    */ 
/*    */ public class Protocol1_7_6to1_7_2 extends AbstractProtocol<ClientboundPackets1_7, ClientboundPackets1_7, ServerboundPackets1_7, ServerboundPackets1_7> {
/* 13 */   public static ValueTransformer<String, String> INSERT_DASHES = new ValueTransformer<String, String>(Type.STRING)
/*    */     {
/*    */       public String transform(PacketWrapper wrapper, String inputValue) throws Exception {
/* 16 */         StringBuilder builder = new StringBuilder(inputValue);
/* 17 */         builder.insert(20, "-");
/* 18 */         builder.insert(16, "-");
/* 19 */         builder.insert(12, "-");
/* 20 */         builder.insert(8, "-");
/* 21 */         return builder.toString();
/*    */       }
/*    */     };
/*    */   
/*    */   public Protocol1_7_6to1_7_2() {
/* 26 */     super(ClientboundPackets1_7.class, ClientboundPackets1_7.class, ServerboundPackets1_7.class, ServerboundPackets1_7.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 32 */     registerClientbound(State.LOGIN, 2, 2, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 35 */             map(Type.STRING, Protocol1_7_6to1_7_2.INSERT_DASHES);
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 40 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_7.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 43 */             map((Type)Type.VAR_INT);
/* 44 */             map(Type.STRING, Protocol1_7_6to1_7_2.INSERT_DASHES);
/* 45 */             map(Type.STRING);
/* 46 */             create((Type)Type.VAR_INT, Integer.valueOf(0));
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6to1_7_2\Protocol1_7_6to1_7_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */