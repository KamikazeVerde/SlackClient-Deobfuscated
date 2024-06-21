/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import de.gerrygames.viarewind.utils.Tickable;
/*     */ 
/*     */ 
/*     */ public class WorldBorder
/*     */   extends StoredObject
/*     */   implements Tickable
/*     */ {
/*     */   private double x;
/*     */   private double z;
/*     */   private double oldDiameter;
/*     */   private double newDiameter;
/*     */   private long lerpTime;
/*     */   
/*     */   public WorldBorder(UserConnection user) {
/*  23 */     super(user);
/*     */   }
/*     */   private long lerpStartTime; private int portalTeleportBoundary; private int warningTime; private int warningBlocks; private boolean init = false; private static final int VIEW_DISTANCE = 16;
/*     */   
/*     */   public void tick() {
/*  28 */     if (!isInit()) {
/*     */       return;
/*     */     }
/*     */     
/*  32 */     sendPackets();
/*     */   }
/*     */   
/*     */   private enum Side {
/*  36 */     NORTH(0, -1),
/*  37 */     EAST(1, 0),
/*  38 */     SOUTH(0, 1),
/*  39 */     WEST(-1, 0);
/*     */     
/*     */     private final int modX;
/*     */     
/*     */     private final int modZ;
/*     */     
/*     */     Side(int modX, int modZ) {
/*  46 */       this.modX = modX;
/*  47 */       this.modZ = modZ;
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendPackets() {
/*  52 */     PlayerPosition position = (PlayerPosition)getUser().get(PlayerPosition.class);
/*     */     
/*  54 */     double radius = getSize() / 2.0D;
/*     */     
/*  56 */     for (Side side : Side.values()) {
/*     */       double d;
/*     */       double pos;
/*     */       double center;
/*  60 */       if (side.modX != 0) {
/*  61 */         pos = position.getPosZ();
/*  62 */         center = this.z;
/*  63 */         d = Math.abs(this.x + radius * side.modX - position.getPosX());
/*     */       } else {
/*  65 */         center = this.x;
/*  66 */         pos = position.getPosX();
/*  67 */         d = Math.abs(this.z + radius * side.modZ - position.getPosZ());
/*     */       } 
/*  69 */       if (d < 16.0D) {
/*     */         
/*  71 */         double r = Math.sqrt(256.0D - d * d);
/*     */         
/*  73 */         double minH = Math.ceil(pos - r);
/*  74 */         double maxH = Math.floor(pos + r);
/*  75 */         double minV = Math.ceil(position.getPosY() - r);
/*  76 */         double maxV = Math.floor(position.getPosY() + r);
/*     */         
/*  78 */         if (minH < center - radius) minH = Math.ceil(center - radius); 
/*  79 */         if (maxH > center + radius) maxH = Math.floor(center + radius); 
/*  80 */         if (minV < 0.0D) minV = 0.0D;
/*     */         
/*  82 */         double centerH = (minH + maxH) / 2.0D;
/*  83 */         double centerV = (minV + maxV) / 2.0D;
/*     */         
/*  85 */         int a = (int)Math.floor((maxH - minH) * (maxV - minV) * 0.5D);
/*     */         
/*  87 */         double b = 2.5D;
/*     */         
/*  89 */         PacketWrapper particles = PacketWrapper.create(42, null, getUser());
/*  90 */         particles.write(Type.STRING, "fireworksSpark");
/*  91 */         particles.write((Type)Type.FLOAT, Float.valueOf((float)((side.modX != 0) ? (this.x + radius * side.modX) : centerH)));
/*  92 */         particles.write((Type)Type.FLOAT, Float.valueOf((float)centerV));
/*  93 */         particles.write((Type)Type.FLOAT, Float.valueOf((float)((side.modX == 0) ? (this.z + radius * side.modZ) : centerH)));
/*  94 */         particles.write((Type)Type.FLOAT, Float.valueOf((float)((side.modX != 0) ? 0.0D : ((maxH - minH) / b))));
/*  95 */         particles.write((Type)Type.FLOAT, Float.valueOf((float)((maxV - minV) / b)));
/*  96 */         particles.write((Type)Type.FLOAT, Float.valueOf((float)((side.modX == 0) ? 0.0D : ((maxH - minH) / b))));
/*  97 */         particles.write((Type)Type.FLOAT, Float.valueOf(0.0F));
/*  98 */         particles.write((Type)Type.INT, Integer.valueOf(a));
/*     */         
/* 100 */         PacketUtil.sendPacket(particles, Protocol1_7_6_10TO1_8.class, true, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private boolean isInit() {
/* 105 */     return this.init;
/*     */   }
/*     */   
/*     */   public void init(double x, double z, double oldDiameter, double newDiameter, long lerpTime, int portalTeleportBoundary, int warningTime, int warningBlocks) {
/* 109 */     this.x = x;
/* 110 */     this.z = z;
/* 111 */     this.oldDiameter = oldDiameter;
/* 112 */     this.newDiameter = newDiameter;
/* 113 */     this.lerpTime = lerpTime;
/* 114 */     this.portalTeleportBoundary = portalTeleportBoundary;
/* 115 */     this.warningTime = warningTime;
/* 116 */     this.warningBlocks = warningBlocks;
/* 117 */     this.init = true;
/*     */   }
/*     */   
/*     */   public double getX() {
/* 121 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 125 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setCenter(double x, double z) {
/* 129 */     this.x = x;
/* 130 */     this.z = z;
/*     */   }
/*     */   
/*     */   public double getOldDiameter() {
/* 134 */     return this.oldDiameter;
/*     */   }
/*     */   
/*     */   public double getNewDiameter() {
/* 138 */     return this.newDiameter;
/*     */   }
/*     */   
/*     */   public long getLerpTime() {
/* 142 */     return this.lerpTime;
/*     */   }
/*     */   
/*     */   public void lerpSize(double oldDiameter, double newDiameter, long lerpTime) {
/* 146 */     this.oldDiameter = oldDiameter;
/* 147 */     this.newDiameter = newDiameter;
/* 148 */     this.lerpTime = lerpTime;
/* 149 */     this.lerpStartTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void setSize(double size) {
/* 153 */     this.oldDiameter = size;
/* 154 */     this.newDiameter = size;
/* 155 */     this.lerpTime = 0L;
/*     */   }
/*     */   
/*     */   public double getSize() {
/* 159 */     if (this.lerpTime == 0L) return this.newDiameter;
/*     */     
/* 161 */     long time = System.currentTimeMillis() - this.lerpStartTime;
/* 162 */     double percent = time / this.lerpTime;
/* 163 */     if (percent > 1.0D) { percent = 1.0D; }
/* 164 */     else if (percent < 0.0D) { percent = 0.0D; }
/*     */     
/* 166 */     return this.oldDiameter + (this.newDiameter - this.oldDiameter) * percent;
/*     */   }
/*     */   
/*     */   public int getPortalTeleportBoundary() {
/* 170 */     return this.portalTeleportBoundary;
/*     */   }
/*     */   
/*     */   public void setPortalTeleportBoundary(int portalTeleportBoundary) {
/* 174 */     this.portalTeleportBoundary = portalTeleportBoundary;
/*     */   }
/*     */   
/*     */   public int getWarningTime() {
/* 178 */     return this.warningTime;
/*     */   }
/*     */   
/*     */   public void setWarningTime(int warningTime) {
/* 182 */     this.warningTime = warningTime;
/*     */   }
/*     */   
/*     */   public int getWarningBlocks() {
/* 186 */     return this.warningBlocks;
/*     */   }
/*     */   
/*     */   public void setWarningBlocks(int warningBlocks) {
/* 190 */     this.warningBlocks = warningBlocks;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\storage\WorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */