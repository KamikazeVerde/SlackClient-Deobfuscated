/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*    */ import de.gerrygames.viarewind.utils.PacketUtil;
/*    */ import de.gerrygames.viarewind.utils.Tickable;
/*    */ 
/*    */ public class Levitation extends StoredObject implements Tickable {
/*    */   private int amplifier;
/*    */   private volatile boolean active = false;
/*    */   
/*    */   public Levitation(UserConnection user) {
/* 16 */     super(user);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 21 */     if (!this.active) {
/*    */       return;
/*    */     }
/*    */     
/* 25 */     int vY = (this.amplifier + 1) * 360;
/* 26 */     PacketWrapper packet = PacketWrapper.create(18, null, getUser());
/* 27 */     packet.write((Type)Type.VAR_INT, Integer.valueOf(((EntityTracker)getUser().get(EntityTracker.class)).getPlayerId()));
/* 28 */     packet.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 29 */     packet.write((Type)Type.SHORT, Short.valueOf((short)vY));
/* 30 */     packet.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 31 */     PacketUtil.sendPacket(packet, Protocol1_8TO1_9.class);
/*    */   }
/*    */   
/*    */   public void setActive(boolean active) {
/* 35 */     this.active = active;
/*    */   }
/*    */   
/*    */   public void setAmplifier(int amplifier) {
/* 39 */     this.amplifier = amplifier;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\storage\Levitation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */