/*    */ package cc.slack.features.modules.impl.utilties;
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.utils.other.PrintUtil;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*    */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*    */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*    */ import net.minecraft.network.play.server.S0APacketUseBed;
/*    */ import net.minecraft.network.play.server.S14PacketEntity;
/*    */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*    */ import net.minecraft.network.play.server.S41PacketServerDifficulty;
/*    */ import net.minecraft.network.play.server.S45PacketTitle;
/*    */ 
/*    */ @ModuleInfo(name = "PacketDebugger", category = Category.UTILITIES)
/*    */ public class PacketDebugger extends Module {
/* 25 */   private final BooleanValue printTime = new BooleanValue("Print Time", true);
/* 26 */   private final BooleanValue printFields = new BooleanValue("Print Fields", true);
/* 27 */   private final ArrayList<BooleanValue> settingsList = new ArrayList<>();
/*    */   
/* 29 */   private final Class[] packetsList = new Class[] { C00PacketKeepAlive.class, C01PacketChatMessage.class, C02PacketUseEntity.class, C03PacketPlayer.class, C03PacketPlayer.C04PacketPlayerPosition.class, C03PacketPlayer.C05PacketPlayerLook.class, C03PacketPlayer.C06PacketPlayerPosLook.class, C07PacketPlayerDigging.class, C08PacketPlayerBlockPlacement.class, C09PacketHeldItemChange.class, C0APacketAnimation.class, C0BPacketEntityAction.class, C0CPacketInput.class, C0DPacketCloseWindow.class, C0EPacketClickWindow.class, C0FPacketConfirmTransaction.class, C10PacketCreativeInventoryAction.class, C11PacketEnchantItem.class, C12PacketUpdateSign.class, C13PacketPlayerAbilities.class, C14PacketTabComplete.class, C15PacketClientSettings.class, C16PacketClientStatus.class, C17PacketCustomPayload.class, C18PacketSpectate.class, C19PacketResourcePackStatus.class, S00PacketKeepAlive.class, S01PacketJoinGame.class, S02PacketChat.class, S03PacketTimeUpdate.class, S04PacketEntityEquipment.class, S05PacketSpawnPosition.class, S06PacketUpdateHealth.class, S07PacketRespawn.class, S08PacketPlayerPosLook.class, S09PacketHeldItemChange.class, S0APacketUseBed.class, S0BPacketAnimation.class, S0CPacketSpawnPlayer.class, S0DPacketCollectItem.class, S0EPacketSpawnObject.class, S0FPacketSpawnMob.class, S10PacketSpawnPainting.class, S11PacketSpawnExperienceOrb.class, S12PacketEntityVelocity.class, S13PacketDestroyEntities.class, S14PacketEntity.class, S14PacketEntity.S15PacketEntityRelMove.class, S14PacketEntity.S16PacketEntityLook.class, S14PacketEntity.S17PacketEntityLookMove.class, S18PacketEntityTeleport.class, S19PacketEntityHeadLook.class, S19PacketEntityStatus.class, S1BPacketEntityAttach.class, S1CPacketEntityMetadata.class, S1DPacketEntityEffect.class, S1EPacketRemoveEntityEffect.class, S1FPacketSetExperience.class, S20PacketEntityProperties.class, S21PacketChunkData.class, S22PacketMultiBlockChange.class, S23PacketBlockChange.class, S24PacketBlockAction.class, S25PacketBlockBreakAnim.class, S26PacketMapChunkBulk.class, S27PacketExplosion.class, S28PacketEffect.class, S29PacketSoundEffect.class, S2APacketParticles.class, S2BPacketChangeGameState.class, S2CPacketSpawnGlobalEntity.class, S2DPacketOpenWindow.class, S2EPacketCloseWindow.class, S2FPacketSetSlot.class, S30PacketWindowItems.class, S31PacketWindowProperty.class, S32PacketConfirmTransaction.class, S33PacketUpdateSign.class, S34PacketMaps.class, S35PacketUpdateTileEntity.class, S36PacketSignEditorOpen.class, S37PacketStatistics.class, S38PacketPlayerListItem.class, S39PacketPlayerAbilities.class, S3APacketTabComplete.class, S3BPacketScoreboardObjective.class, S3CPacketUpdateScore.class, S3DPacketDisplayScoreboard.class, S3EPacketTeams.class, S3FPacketCustomPayload.class, S40PacketDisconnect.class, S41PacketServerDifficulty.class, S42PacketCombatEvent.class, S43PacketCamera.class, S44PacketWorldBorder.class, S45PacketTitle.class, S46PacketSetCompressionLevel.class, S47PacketPlayerListHeaderFooter.class, S48PacketResourcePackSend.class, S49PacketUpdateEntityNBT.class };
/*    */ 
/*    */   
/*    */   public PacketDebugger() {
/* 33 */     addSettings(new Value[] { (Value)this.printTime, (Value)this.printFields });
/* 34 */     for (Class c : this.packetsList) {
/* 35 */       this.settingsList.add(new BooleanValue(c.getName().replace("net.minecraft.network.play.server.", "").replace("net.minecraft.network.play.client.", ""), false));
/* 36 */       addSettings(new Value[] { (Value)this.settingsList.get(this.settingsList.size() - 1) });
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 42 */     Packet packet = event.getPacket();
/*    */     
/* 44 */     for (int i = 0; i < this.settingsList.size(); i++) {
/* 45 */       BooleanValue setting = this.settingsList.get(i);
/* 46 */       if (packet.getClass().getName() == setting.getName() && (
/* 47 */         (Boolean)setting.getValue()).booleanValue())
/* 48 */         for (Field field : this.packetsList[i].getDeclaredFields()) {
/*    */           try {
/* 50 */             if (field.isAccessible())
/* 51 */               PrintUtil.message(field.getName() + ": " + field.get(packet)); 
/* 52 */           } catch (Exception ignored) {
/* 53 */             PrintUtil.message(field.getName() + "Erm, what the sigma");
/*    */           } 
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\imp\\utilties\PacketDebugger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */