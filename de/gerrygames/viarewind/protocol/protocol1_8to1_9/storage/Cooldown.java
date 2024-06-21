/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.util.Pair;
/*     */ import de.gerrygames.viarewind.ViaRewind;
/*     */ import de.gerrygames.viarewind.api.ViaRewindConfig;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import de.gerrygames.viarewind.utils.Tickable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class Cooldown extends StoredObject implements Tickable {
/*     */   private double attackSpeed;
/*     */   private long lastHit;
/*     */   private final ViaRewindConfig.CooldownIndicator cooldownIndicator;
/*     */   private UUID bossUUID;
/*     */   private boolean lastSend;
/*     */   private static final int max = 10;
/*     */   
/*     */   public Cooldown(UserConnection user) {
/*  29 */     super(user); ViaRewindConfig.CooldownIndicator indicator;
/*     */     this.attackSpeed = 4.0D;
/*     */     this.lastHit = 0L;
/*     */     try {
/*  33 */       indicator = ViaRewind.getConfig().getCooldownIndicator();
/*  34 */     } catch (IllegalArgumentException e) {
/*  35 */       ViaRewind.getPlatform().getLogger().warning("Invalid cooldown-indicator setting");
/*  36 */       indicator = ViaRewindConfig.CooldownIndicator.DISABLED;
/*     */     } 
/*     */     
/*  39 */     this.cooldownIndicator = indicator;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  44 */     if (!hasCooldown()) {
/*  45 */       if (this.lastSend) {
/*  46 */         hide();
/*  47 */         this.lastSend = false;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  52 */     BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker)getUser().get(BlockPlaceDestroyTracker.class);
/*  53 */     if (tracker.isMining()) {
/*  54 */       this.lastHit = 0L;
/*  55 */       if (this.lastSend) {
/*  56 */         hide();
/*  57 */         this.lastSend = false;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  62 */     showCooldown();
/*  63 */     this.lastSend = true;
/*     */   }
/*     */   
/*     */   private void showCooldown() {
/*  67 */     if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
/*  68 */       sendTitle("", getTitle(), 0, 2, 5);
/*  69 */     } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
/*  70 */       sendActionBar(getTitle());
/*  71 */     } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
/*  72 */       sendBossBar((float)getCooldown());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void hide() {
/*  77 */     if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
/*  78 */       sendActionBar("§r");
/*  79 */     } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
/*  80 */       hideTitle();
/*  81 */     } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
/*  82 */       hideBossBar();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void hideBossBar() {
/*  87 */     if (this.bossUUID == null)
/*  88 */       return;  PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.BOSSBAR, null, getUser());
/*  89 */     wrapper.write(Type.UUID, this.bossUUID);
/*  90 */     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*  91 */     PacketUtil.sendPacket(wrapper, Protocol1_8TO1_9.class, false, true);
/*  92 */     this.bossUUID = null;
/*     */   }
/*     */   
/*     */   private void sendBossBar(float cooldown) {
/*  96 */     PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.BOSSBAR, getUser());
/*  97 */     if (this.bossUUID == null) {
/*  98 */       this.bossUUID = UUID.randomUUID();
/*  99 */       wrapper.write(Type.UUID, this.bossUUID);
/* 100 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/* 101 */       wrapper.write(Type.COMPONENT, new JsonPrimitive(" "));
/* 102 */       wrapper.write((Type)Type.FLOAT, Float.valueOf(cooldown));
/* 103 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/* 104 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/* 105 */       wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)0));
/*     */     } else {
/* 107 */       wrapper.write(Type.UUID, this.bossUUID);
/* 108 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(2));
/* 109 */       wrapper.write((Type)Type.FLOAT, Float.valueOf(cooldown));
/*     */     } 
/* 111 */     PacketUtil.sendPacket(wrapper, Protocol1_8TO1_9.class, false, true);
/*     */   }
/*     */   
/*     */   private void hideTitle() {
/* 115 */     PacketWrapper hide = PacketWrapper.create((PacketType)ClientboundPackets1_8.TITLE, null, getUser());
/* 116 */     hide.write((Type)Type.VAR_INT, Integer.valueOf(3));
/* 117 */     PacketUtil.sendPacket(hide, Protocol1_8TO1_9.class);
/*     */   }
/*     */   
/*     */   private void sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
/* 121 */     PacketWrapper timePacket = PacketWrapper.create((PacketType)ClientboundPackets1_8.TITLE, null, getUser());
/* 122 */     timePacket.write((Type)Type.VAR_INT, Integer.valueOf(2));
/* 123 */     timePacket.write((Type)Type.INT, Integer.valueOf(fadeIn));
/* 124 */     timePacket.write((Type)Type.INT, Integer.valueOf(stay));
/* 125 */     timePacket.write((Type)Type.INT, Integer.valueOf(fadeOut));
/* 126 */     PacketWrapper titlePacket = PacketWrapper.create((PacketType)ClientboundPackets1_8.TITLE, getUser());
/* 127 */     titlePacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/* 128 */     titlePacket.write(Type.COMPONENT, new JsonPrimitive(title));
/* 129 */     PacketWrapper subtitlePacket = PacketWrapper.create((PacketType)ClientboundPackets1_8.TITLE, getUser());
/* 130 */     subtitlePacket.write((Type)Type.VAR_INT, Integer.valueOf(1));
/* 131 */     subtitlePacket.write(Type.COMPONENT, new JsonPrimitive(subTitle));
/*     */     
/* 133 */     PacketUtil.sendPacket(titlePacket, Protocol1_8TO1_9.class);
/* 134 */     PacketUtil.sendPacket(subtitlePacket, Protocol1_8TO1_9.class);
/* 135 */     PacketUtil.sendPacket(timePacket, Protocol1_8TO1_9.class);
/*     */   }
/*     */   
/*     */   private void sendActionBar(String bar) {
/* 139 */     PacketWrapper actionBarPacket = PacketWrapper.create((PacketType)ClientboundPackets1_8.CHAT_MESSAGE, getUser());
/* 140 */     actionBarPacket.write(Type.COMPONENT, new JsonPrimitive(bar));
/* 141 */     actionBarPacket.write((Type)Type.BYTE, Byte.valueOf((byte)2));
/*     */     
/* 143 */     PacketUtil.sendPacket(actionBarPacket, Protocol1_8TO1_9.class);
/*     */   }
/*     */   
/*     */   public boolean hasCooldown() {
/* 147 */     long time = System.currentTimeMillis() - this.lastHit;
/* 148 */     double cooldown = restrain(time * this.attackSpeed / 1000.0D, 0.0D, 1.5D);
/* 149 */     return (cooldown > 0.1D && cooldown < 1.1D);
/*     */   }
/*     */   
/*     */   public double getCooldown() {
/* 153 */     long time = System.currentTimeMillis() - this.lastHit;
/* 154 */     return restrain(time * this.attackSpeed / 1000.0D, 0.0D, 1.0D);
/*     */   }
/*     */   
/*     */   private double restrain(double x, double a, double b) {
/* 158 */     if (x < a) return a; 
/* 159 */     return Math.min(x, b);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getTitle() {
/* 165 */     String symbol = (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) ? "■" : "˙";
/*     */     
/* 167 */     double cooldown = getCooldown();
/* 168 */     int green = (int)Math.floor(10.0D * cooldown);
/* 169 */     int grey = 10 - green;
/* 170 */     StringBuilder builder = new StringBuilder("§8");
/* 171 */     for (; green-- > 0; builder.append(symbol));
/* 172 */     builder.append("§7");
/* 173 */     for (; grey-- > 0; builder.append(symbol));
/* 174 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public double getAttackSpeed() {
/* 178 */     return this.attackSpeed;
/*     */   }
/*     */   
/*     */   public void setAttackSpeed(double attackSpeed) {
/* 182 */     this.attackSpeed = attackSpeed;
/*     */   }
/*     */   
/*     */   public void setAttackSpeed(double base, ArrayList<Pair<Byte, Double>> modifiers) {
/* 186 */     this.attackSpeed = base; int j;
/* 187 */     for (j = 0; j < modifiers.size(); j++) {
/* 188 */       if (((Byte)((Pair)modifiers.get(j)).key()).byteValue() == 0) {
/* 189 */         this.attackSpeed += ((Double)((Pair)modifiers.get(j)).value()).doubleValue();
/* 190 */         modifiers.remove(j--);
/*     */       } 
/*     */     } 
/* 193 */     for (j = 0; j < modifiers.size(); j++) {
/* 194 */       if (((Byte)((Pair)modifiers.get(j)).key()).byteValue() == 1) {
/* 195 */         this.attackSpeed += base * ((Double)((Pair)modifiers.get(j)).value()).doubleValue();
/* 196 */         modifiers.remove(j--);
/*     */       } 
/*     */     } 
/* 199 */     for (j = 0; j < modifiers.size(); j++) {
/* 200 */       if (((Byte)((Pair)modifiers.get(j)).key()).byteValue() == 2) {
/* 201 */         this.attackSpeed *= 1.0D + ((Double)((Pair)modifiers.get(j)).value()).doubleValue();
/* 202 */         modifiers.remove(j--);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void hit() {
/* 208 */     this.lastHit = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void setLastHit(long lastHit) {
/* 212 */     this.lastHit = lastHit;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\storage\Cooldown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */