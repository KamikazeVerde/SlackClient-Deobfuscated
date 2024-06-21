/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9;
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Windows;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.Timer;
/*    */ 
/*    */ public class Protocol1_8TO1_9 extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> {
/* 23 */   public static final Timer TIMER = new Timer("ViaRewind-1_8TO1_9", true);
/* 24 */   public static final Set<String> VALID_ATTRIBUTES = new HashSet<>();
/* 25 */   public static final ValueTransformer<Double, Integer> TO_OLD_INT = new ValueTransformer<Double, Integer>((Type)Type.INT) {
/*    */       public Integer transform(PacketWrapper wrapper, Double inputValue) {
/* 27 */         return Integer.valueOf((int)(inputValue.doubleValue() * 32.0D));
/*    */       }
/*    */     };
/* 30 */   public static final ValueTransformer<Float, Byte> DEGREES_TO_ANGLE = new ValueTransformer<Float, Byte>((Type)Type.BYTE)
/*    */     {
/*    */       public Byte transform(PacketWrapper packetWrapper, Float degrees) throws Exception {
/* 33 */         return Byte.valueOf((byte)(int)(degrees.floatValue() / 360.0F * 256.0F));
/*    */       }
/*    */     };
/*    */   
/*    */   static {
/* 38 */     VALID_ATTRIBUTES.add("generic.maxHealth");
/* 39 */     VALID_ATTRIBUTES.add("generic.followRange");
/* 40 */     VALID_ATTRIBUTES.add("generic.knockbackResistance");
/* 41 */     VALID_ATTRIBUTES.add("generic.movementSpeed");
/* 42 */     VALID_ATTRIBUTES.add("generic.attackDamage");
/* 43 */     VALID_ATTRIBUTES.add("horse.jumpStrength");
/* 44 */     VALID_ATTRIBUTES.add("zombie.spawnReinforcements");
/*    */   }
/*    */   
/*    */   public Protocol1_8TO1_9() {
/* 48 */     super(ClientboundPackets1_9.class, ClientboundPackets1_8.class, ServerboundPackets1_9.class, ServerboundPackets1_8.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 53 */     EntityPackets.register((Protocol)this);
/* 54 */     InventoryPackets.register((Protocol)this);
/* 55 */     PlayerPackets.register((Protocol)this);
/* 56 */     ScoreboardPackets.register((Protocol)this);
/* 57 */     SpawnPackets.register((Protocol)this);
/* 58 */     WorldPackets.register((Protocol)this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(UserConnection userConnection) {
/* 63 */     Ticker.init();
/*    */     
/* 65 */     userConnection.put((StorableObject)new Windows(userConnection));
/* 66 */     userConnection.put((StorableObject)new EntityTracker(userConnection));
/* 67 */     userConnection.put((StorableObject)new Levitation(userConnection));
/* 68 */     userConnection.put((StorableObject)new PlayerPosition(userConnection));
/* 69 */     userConnection.put((StorableObject)new Cooldown(userConnection));
/* 70 */     userConnection.put((StorableObject)new BlockPlaceDestroyTracker(userConnection));
/* 71 */     userConnection.put((StorableObject)new BossBarStorage(userConnection));
/* 72 */     userConnection.put((StorableObject)new ClientWorld(userConnection));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\Protocol1_8TO1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */