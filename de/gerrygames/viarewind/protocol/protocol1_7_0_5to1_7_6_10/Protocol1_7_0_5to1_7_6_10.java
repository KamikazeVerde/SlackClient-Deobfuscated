/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.State;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public class Protocol1_7_0_5to1_7_6_10 extends AbstractProtocol<ClientboundPackets1_7, ClientboundPackets1_7, ServerboundPackets1_7, ServerboundPackets1_7> {
/* 19 */   public static final ValueTransformer<String, String> REMOVE_DASHES = new ValueTransformer<String, String>(Type.STRING)
/*    */     {
/*    */       public String transform(PacketWrapper packetWrapper, String s) {
/* 22 */         return s.replace("-", "");
/*    */       }
/*    */     };
/*    */   
/*    */   public Protocol1_7_0_5to1_7_6_10() {
/* 27 */     super(ClientboundPackets1_7.class, ClientboundPackets1_7.class, ServerboundPackets1_7.class, ServerboundPackets1_7.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 33 */     registerClientbound(State.LOGIN, 2, 2, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 36 */             map(Type.STRING, Protocol1_7_0_5to1_7_6_10.REMOVE_DASHES);
/* 37 */             map(Type.STRING);
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 42 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_7.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 45 */             map((Type)Type.VAR_INT);
/* 46 */             map(Type.STRING, Protocol1_7_0_5to1_7_6_10.REMOVE_DASHES);
/* 47 */             map(Type.STRING);
/* 48 */             handler(packetWrapper -> {
/*    */                   int size = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue(); for (int i = 0; i < size * 3; i++)
/*    */                     packetWrapper.read(Type.STRING); 
/*    */                 });
/* 52 */             map((Type)Type.INT);
/* 53 */             map((Type)Type.INT);
/* 54 */             map((Type)Type.INT);
/* 55 */             map((Type)Type.BYTE);
/* 56 */             map((Type)Type.BYTE);
/* 57 */             map((Type)Type.SHORT);
/* 58 */             map(Types1_7_6_10.METADATA_LIST);
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 63 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_7.TEAMS, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 66 */             map(Type.STRING);
/* 67 */             map((Type)Type.BYTE);
/* 68 */             handler(packetWrapper -> {
/*    */                   byte mode = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*    */                   if (mode == 0 || mode == 2) {
/*    */                     packetWrapper.passthrough(Type.STRING);
/*    */                     packetWrapper.passthrough(Type.STRING);
/*    */                     packetWrapper.passthrough(Type.STRING);
/*    */                     packetWrapper.passthrough((Type)Type.BYTE);
/*    */                   } 
/*    */                   if (mode == 0 || mode == 3 || mode == 4) {
/*    */                     List<String> entryList = new ArrayList<>();
/*    */                     int size = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*    */                     for (int i = 0; i < size; i++)
/*    */                       entryList.add((String)packetWrapper.read(Type.STRING)); 
/*    */                     entryList = (List<String>)entryList.stream().map(()).distinct().collect(Collectors.toList());
/*    */                     packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)entryList.size()));
/*    */                     for (String entry : entryList)
/*    */                       packetWrapper.write(Type.STRING, entry); 
/*    */                   } 
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void init(UserConnection userConnection) {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_0_5to1_7_6_10\Protocol1_7_0_5to1_7_6_10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */