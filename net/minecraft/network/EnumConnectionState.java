/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.network.handshake.client.C00Handshake;
/*     */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*     */ import net.minecraft.network.login.client.C01PacketEncryptionResponse;
/*     */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*     */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*     */ import net.minecraft.network.login.server.S02PacketLoginSuccess;
/*     */ import net.minecraft.network.login.server.S03PacketEnableCompression;
/*     */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*     */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.network.play.client.C0CPacketInput;
/*     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*     */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*     */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*     */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*     */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*     */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*     */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.network.play.client.C18PacketSpectate;
/*     */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*     */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*     */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*     */ import net.minecraft.network.play.server.S02PacketChat;
/*     */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*     */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*     */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*     */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*     */ import net.minecraft.network.play.server.S07PacketRespawn;
/*     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*     */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.server.S0APacketUseBed;
/*     */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*     */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*     */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*     */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*     */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*     */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*     */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*     */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*     */ import net.minecraft.network.play.server.S14PacketEntity;
/*     */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*     */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*     */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*     */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*     */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*     */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*     */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*     */ import net.minecraft.network.play.server.S21PacketChunkData;
/*     */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*     */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*     */ import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
/*     */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*     */ import net.minecraft.network.play.server.S27PacketExplosion;
/*     */ import net.minecraft.network.play.server.S28PacketEffect;
/*     */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*     */ import net.minecraft.network.play.server.S2APacketParticles;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*     */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*     */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*     */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*     */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*     */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*     */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*     */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*     */ import net.minecraft.network.play.server.S34PacketMaps;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*     */ import net.minecraft.network.play.server.S37PacketStatistics;
/*     */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*     */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*     */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*     */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*     */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*     */ import net.minecraft.network.play.server.S3EPacketTeams;
/*     */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*     */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*     */ import net.minecraft.network.play.server.S41PacketServerDifficulty;
/*     */ import net.minecraft.network.play.server.S42PacketCombatEvent;
/*     */ import net.minecraft.network.play.server.S43PacketCamera;
/*     */ import net.minecraft.network.play.server.S44PacketWorldBorder;
/*     */ import net.minecraft.network.play.server.S45PacketTitle;
/*     */ import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
/*     */ import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
/*     */ import net.minecraft.network.play.server.S48PacketResourcePackSend;
/*     */ import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
/*     */ import net.minecraft.network.status.client.C00PacketServerQuery;
/*     */ import net.minecraft.network.status.client.C01PacketPing;
/*     */ import net.minecraft.network.status.server.S00PacketServerInfo;
/*     */ import net.minecraft.network.status.server.S01PacketPong;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public enum EnumConnectionState
/*     */ {
/* 116 */   HANDSHAKING(-1)
/*     */   {
/*     */     EnumConnectionState(int protocolId) {
/* 119 */       registerPacket(PacketDirection.OUTGOING, (Class)C00Handshake.class);
/*     */     }
/*     */   },
/* 122 */   PLAY(0)
/*     */   {
/*     */     EnumConnectionState(int protocolId) {
/* 125 */       registerPacket(PacketDirection.INCOMING, (Class)S00PacketKeepAlive.class);
/* 126 */       registerPacket(PacketDirection.INCOMING, (Class)S01PacketJoinGame.class);
/* 127 */       registerPacket(PacketDirection.INCOMING, (Class)S02PacketChat.class);
/* 128 */       registerPacket(PacketDirection.INCOMING, (Class)S03PacketTimeUpdate.class);
/* 129 */       registerPacket(PacketDirection.INCOMING, (Class)S04PacketEntityEquipment.class);
/* 130 */       registerPacket(PacketDirection.INCOMING, (Class)S05PacketSpawnPosition.class);
/* 131 */       registerPacket(PacketDirection.INCOMING, (Class)S06PacketUpdateHealth.class);
/* 132 */       registerPacket(PacketDirection.INCOMING, (Class)S07PacketRespawn.class);
/* 133 */       registerPacket(PacketDirection.INCOMING, (Class)S08PacketPlayerPosLook.class);
/* 134 */       registerPacket(PacketDirection.INCOMING, (Class)S09PacketHeldItemChange.class);
/* 135 */       registerPacket(PacketDirection.INCOMING, (Class)S0APacketUseBed.class);
/* 136 */       registerPacket(PacketDirection.INCOMING, (Class)S0BPacketAnimation.class);
/* 137 */       registerPacket(PacketDirection.INCOMING, (Class)S0CPacketSpawnPlayer.class);
/* 138 */       registerPacket(PacketDirection.INCOMING, (Class)S0DPacketCollectItem.class);
/* 139 */       registerPacket(PacketDirection.INCOMING, (Class)S0EPacketSpawnObject.class);
/* 140 */       registerPacket(PacketDirection.INCOMING, (Class)S0FPacketSpawnMob.class);
/* 141 */       registerPacket(PacketDirection.INCOMING, (Class)S10PacketSpawnPainting.class);
/* 142 */       registerPacket(PacketDirection.INCOMING, (Class)S11PacketSpawnExperienceOrb.class);
/* 143 */       registerPacket(PacketDirection.INCOMING, (Class)S12PacketEntityVelocity.class);
/* 144 */       registerPacket(PacketDirection.INCOMING, (Class)S13PacketDestroyEntities.class);
/* 145 */       registerPacket(PacketDirection.INCOMING, (Class)S14PacketEntity.class);
/* 146 */       registerPacket(PacketDirection.INCOMING, (Class)S14PacketEntity.S15PacketEntityRelMove.class);
/* 147 */       registerPacket(PacketDirection.INCOMING, (Class)S14PacketEntity.S16PacketEntityLook.class);
/* 148 */       registerPacket(PacketDirection.INCOMING, (Class)S14PacketEntity.S17PacketEntityLookMove.class);
/* 149 */       registerPacket(PacketDirection.INCOMING, (Class)S18PacketEntityTeleport.class);
/* 150 */       registerPacket(PacketDirection.INCOMING, (Class)S19PacketEntityHeadLook.class);
/* 151 */       registerPacket(PacketDirection.INCOMING, (Class)S19PacketEntityStatus.class);
/* 152 */       registerPacket(PacketDirection.INCOMING, (Class)S1BPacketEntityAttach.class);
/* 153 */       registerPacket(PacketDirection.INCOMING, (Class)S1CPacketEntityMetadata.class);
/* 154 */       registerPacket(PacketDirection.INCOMING, (Class)S1DPacketEntityEffect.class);
/* 155 */       registerPacket(PacketDirection.INCOMING, (Class)S1EPacketRemoveEntityEffect.class);
/* 156 */       registerPacket(PacketDirection.INCOMING, (Class)S1FPacketSetExperience.class);
/* 157 */       registerPacket(PacketDirection.INCOMING, (Class)S20PacketEntityProperties.class);
/* 158 */       registerPacket(PacketDirection.INCOMING, (Class)S21PacketChunkData.class);
/* 159 */       registerPacket(PacketDirection.INCOMING, (Class)S22PacketMultiBlockChange.class);
/* 160 */       registerPacket(PacketDirection.INCOMING, (Class)S23PacketBlockChange.class);
/* 161 */       registerPacket(PacketDirection.INCOMING, (Class)S24PacketBlockAction.class);
/* 162 */       registerPacket(PacketDirection.INCOMING, (Class)S25PacketBlockBreakAnim.class);
/* 163 */       registerPacket(PacketDirection.INCOMING, (Class)S26PacketMapChunkBulk.class);
/* 164 */       registerPacket(PacketDirection.INCOMING, (Class)S27PacketExplosion.class);
/* 165 */       registerPacket(PacketDirection.INCOMING, (Class)S28PacketEffect.class);
/* 166 */       registerPacket(PacketDirection.INCOMING, (Class)S29PacketSoundEffect.class);
/* 167 */       registerPacket(PacketDirection.INCOMING, (Class)S2APacketParticles.class);
/* 168 */       registerPacket(PacketDirection.INCOMING, (Class)S2BPacketChangeGameState.class);
/* 169 */       registerPacket(PacketDirection.INCOMING, (Class)S2CPacketSpawnGlobalEntity.class);
/* 170 */       registerPacket(PacketDirection.INCOMING, (Class)S2DPacketOpenWindow.class);
/* 171 */       registerPacket(PacketDirection.INCOMING, (Class)S2EPacketCloseWindow.class);
/* 172 */       registerPacket(PacketDirection.INCOMING, (Class)S2FPacketSetSlot.class);
/* 173 */       registerPacket(PacketDirection.INCOMING, (Class)S30PacketWindowItems.class);
/* 174 */       registerPacket(PacketDirection.INCOMING, (Class)S31PacketWindowProperty.class);
/* 175 */       registerPacket(PacketDirection.INCOMING, (Class)S32PacketConfirmTransaction.class);
/* 176 */       registerPacket(PacketDirection.INCOMING, (Class)S33PacketUpdateSign.class);
/* 177 */       registerPacket(PacketDirection.INCOMING, (Class)S34PacketMaps.class);
/* 178 */       registerPacket(PacketDirection.INCOMING, (Class)S35PacketUpdateTileEntity.class);
/* 179 */       registerPacket(PacketDirection.INCOMING, (Class)S36PacketSignEditorOpen.class);
/* 180 */       registerPacket(PacketDirection.INCOMING, (Class)S37PacketStatistics.class);
/* 181 */       registerPacket(PacketDirection.INCOMING, (Class)S38PacketPlayerListItem.class);
/* 182 */       registerPacket(PacketDirection.INCOMING, (Class)S39PacketPlayerAbilities.class);
/* 183 */       registerPacket(PacketDirection.INCOMING, (Class)S3APacketTabComplete.class);
/* 184 */       registerPacket(PacketDirection.INCOMING, (Class)S3BPacketScoreboardObjective.class);
/* 185 */       registerPacket(PacketDirection.INCOMING, (Class)S3CPacketUpdateScore.class);
/* 186 */       registerPacket(PacketDirection.INCOMING, (Class)S3DPacketDisplayScoreboard.class);
/* 187 */       registerPacket(PacketDirection.INCOMING, (Class)S3EPacketTeams.class);
/* 188 */       registerPacket(PacketDirection.INCOMING, (Class)S3FPacketCustomPayload.class);
/* 189 */       registerPacket(PacketDirection.INCOMING, (Class)S40PacketDisconnect.class);
/* 190 */       registerPacket(PacketDirection.INCOMING, (Class)S41PacketServerDifficulty.class);
/* 191 */       registerPacket(PacketDirection.INCOMING, (Class)S42PacketCombatEvent.class);
/* 192 */       registerPacket(PacketDirection.INCOMING, (Class)S43PacketCamera.class);
/* 193 */       registerPacket(PacketDirection.INCOMING, (Class)S44PacketWorldBorder.class);
/* 194 */       registerPacket(PacketDirection.INCOMING, (Class)S45PacketTitle.class);
/* 195 */       registerPacket(PacketDirection.INCOMING, (Class)S46PacketSetCompressionLevel.class);
/* 196 */       registerPacket(PacketDirection.INCOMING, (Class)S47PacketPlayerListHeaderFooter.class);
/* 197 */       registerPacket(PacketDirection.INCOMING, (Class)S48PacketResourcePackSend.class);
/* 198 */       registerPacket(PacketDirection.INCOMING, (Class)S49PacketUpdateEntityNBT.class);
/* 199 */       registerPacket(PacketDirection.OUTGOING, (Class)C00PacketKeepAlive.class);
/* 200 */       registerPacket(PacketDirection.OUTGOING, (Class)C01PacketChatMessage.class);
/* 201 */       registerPacket(PacketDirection.OUTGOING, (Class)C02PacketUseEntity.class);
/* 202 */       registerPacket(PacketDirection.OUTGOING, (Class)C03PacketPlayer.class);
/* 203 */       registerPacket(PacketDirection.OUTGOING, (Class)C03PacketPlayer.C04PacketPlayerPosition.class);
/* 204 */       registerPacket(PacketDirection.OUTGOING, (Class)C03PacketPlayer.C05PacketPlayerLook.class);
/* 205 */       registerPacket(PacketDirection.OUTGOING, (Class)C03PacketPlayer.C06PacketPlayerPosLook.class);
/* 206 */       registerPacket(PacketDirection.OUTGOING, (Class)C07PacketPlayerDigging.class);
/* 207 */       registerPacket(PacketDirection.OUTGOING, (Class)C08PacketPlayerBlockPlacement.class);
/* 208 */       registerPacket(PacketDirection.OUTGOING, (Class)C09PacketHeldItemChange.class);
/* 209 */       registerPacket(PacketDirection.OUTGOING, (Class)C0APacketAnimation.class);
/* 210 */       registerPacket(PacketDirection.OUTGOING, (Class)C0BPacketEntityAction.class);
/* 211 */       registerPacket(PacketDirection.OUTGOING, (Class)C0CPacketInput.class);
/* 212 */       registerPacket(PacketDirection.OUTGOING, (Class)C0DPacketCloseWindow.class);
/* 213 */       registerPacket(PacketDirection.OUTGOING, (Class)C0EPacketClickWindow.class);
/* 214 */       registerPacket(PacketDirection.OUTGOING, (Class)C0FPacketConfirmTransaction.class);
/* 215 */       registerPacket(PacketDirection.OUTGOING, (Class)C10PacketCreativeInventoryAction.class);
/* 216 */       registerPacket(PacketDirection.OUTGOING, (Class)C11PacketEnchantItem.class);
/* 217 */       registerPacket(PacketDirection.OUTGOING, (Class)C12PacketUpdateSign.class);
/* 218 */       registerPacket(PacketDirection.OUTGOING, (Class)C13PacketPlayerAbilities.class);
/* 219 */       registerPacket(PacketDirection.OUTGOING, (Class)C14PacketTabComplete.class);
/* 220 */       registerPacket(PacketDirection.OUTGOING, (Class)C15PacketClientSettings.class);
/* 221 */       registerPacket(PacketDirection.OUTGOING, (Class)C16PacketClientStatus.class);
/* 222 */       registerPacket(PacketDirection.OUTGOING, (Class)C17PacketCustomPayload.class);
/* 223 */       registerPacket(PacketDirection.OUTGOING, (Class)C18PacketSpectate.class);
/* 224 */       registerPacket(PacketDirection.OUTGOING, (Class)C19PacketResourcePackStatus.class);
/*     */     }
/*     */   },
/* 227 */   STATUS(1)
/*     */   {
/*     */     EnumConnectionState(int protocolId) {
/* 230 */       registerPacket(PacketDirection.OUTGOING, (Class)C00PacketServerQuery.class);
/* 231 */       registerPacket(PacketDirection.INCOMING, (Class)S00PacketServerInfo.class);
/* 232 */       registerPacket(PacketDirection.OUTGOING, (Class)C01PacketPing.class);
/* 233 */       registerPacket(PacketDirection.INCOMING, (Class)S01PacketPong.class);
/*     */     }
/*     */   },
/* 236 */   LOGIN(2)
/*     */   {
/*     */     EnumConnectionState(int protocolId) {
/* 239 */       registerPacket(PacketDirection.INCOMING, (Class)S00PacketDisconnect.class);
/* 240 */       registerPacket(PacketDirection.INCOMING, (Class)S01PacketEncryptionRequest.class);
/* 241 */       registerPacket(PacketDirection.INCOMING, (Class)S02PacketLoginSuccess.class);
/* 242 */       registerPacket(PacketDirection.INCOMING, (Class)S03PacketEnableCompression.class);
/* 243 */       registerPacket(PacketDirection.OUTGOING, (Class)C00PacketLoginStart.class);
/* 244 */       registerPacket(PacketDirection.OUTGOING, (Class)C01PacketEncryptionResponse.class);
/*     */     } };
/*     */   
/*     */   static {
/* 248 */     field_181136_e = -1;
/* 249 */     field_181137_f = 2;
/* 250 */     STATES_BY_ID = new EnumConnectionState[field_181137_f - field_181136_e + 1];
/* 251 */     STATES_BY_CLASS = Maps.newHashMap();
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
/*     */ 
/*     */     
/* 310 */     for (EnumConnectionState enumconnectionstate : values()) {
/*     */       
/* 312 */       int i = enumconnectionstate.getId();
/*     */       
/* 314 */       if (i < field_181136_e || i > field_181137_f)
/*     */       {
/* 316 */         throw new Error("Invalid protocol ID " + Integer.toString(i));
/*     */       }
/*     */       
/* 319 */       STATES_BY_ID[i - field_181136_e] = enumconnectionstate;
/*     */       
/* 321 */       for (PacketDirection enumpacketdirection : enumconnectionstate.directionMaps.keySet()) {
/*     */         
/* 323 */         for (Class<? extends Packet> oclass : (Iterable<Class<? extends Packet>>)((BiMap)enumconnectionstate.directionMaps.get(enumpacketdirection)).values()) {
/*     */           
/* 325 */           if (STATES_BY_CLASS.containsKey(oclass) && STATES_BY_CLASS.get(oclass) != enumconnectionstate)
/*     */           {
/* 327 */             throw new Error("Packet " + oclass + " is already assigned to protocol " + STATES_BY_CLASS.get(oclass) + " - can't reassign to " + enumconnectionstate);
/*     */           }
/*     */ 
/*     */           
/*     */           try {
/* 332 */             oclass.newInstance();
/*     */           }
/* 334 */           catch (Throwable var10) {
/*     */             
/* 336 */             throw new Error("Packet " + oclass + " fails instantiation checks! " + oclass);
/*     */           } 
/*     */           
/* 339 */           STATES_BY_CLASS.put(oclass, enumconnectionstate);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int field_181136_e;
/*     */   private static int field_181137_f;
/*     */   private static final EnumConnectionState[] STATES_BY_ID;
/*     */   private static final Map<Class<? extends Packet>, EnumConnectionState> STATES_BY_CLASS;
/*     */   private final int id;
/*     */   private final Map<PacketDirection, BiMap<Integer, Class<? extends Packet>>> directionMaps;
/*     */   
/*     */   EnumConnectionState(int protocolId) {
/*     */     this.directionMaps = Maps.newEnumMap(PacketDirection.class);
/*     */     this.id = protocolId;
/*     */   }
/*     */   
/*     */   protected EnumConnectionState registerPacket(PacketDirection direction, Class<? extends Packet> packetClass) {
/*     */     HashBiMap hashBiMap;
/*     */     BiMap<Integer, Class<? extends Packet>> bimap = this.directionMaps.get(direction);
/*     */     if (bimap == null) {
/*     */       hashBiMap = HashBiMap.create();
/*     */       this.directionMaps.put(direction, hashBiMap);
/*     */     } 
/*     */     if (hashBiMap.containsValue(packetClass)) {
/*     */       String s = direction + " packet " + packetClass + " is already known to ID " + hashBiMap.inverse().get(packetClass);
/*     */       LogManager.getLogger().fatal(s);
/*     */       throw new IllegalArgumentException(s);
/*     */     } 
/*     */     hashBiMap.put(Integer.valueOf(hashBiMap.size()), packetClass);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public Integer getPacketId(PacketDirection direction, Packet packetIn) {
/*     */     return (Integer)((BiMap)this.directionMaps.get(direction)).inverse().get(packetIn.getClass());
/*     */   }
/*     */   
/*     */   public Packet getPacket(PacketDirection direction, int packetId) throws InstantiationException, IllegalAccessException {
/*     */     Class<? extends Packet> oclass = (Class<? extends Packet>)((BiMap)this.directionMaps.get(direction)).get(Integer.valueOf(packetId));
/*     */     return (oclass == null) ? null : oclass.newInstance();
/*     */   }
/*     */   
/*     */   public int getId() {
/*     */     return this.id;
/*     */   }
/*     */   
/*     */   public static EnumConnectionState getById(int stateId) {
/*     */     return (stateId >= field_181136_e && stateId <= field_181137_f) ? STATES_BY_ID[stateId - field_181136_e] : null;
/*     */   }
/*     */   
/*     */   public static EnumConnectionState getFromPacket(Packet packetIn) {
/*     */     return STATES_BY_CLASS.get(packetIn.getClass());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\network\EnumConnectionState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */