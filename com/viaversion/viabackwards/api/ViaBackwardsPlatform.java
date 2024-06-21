/*     */ package com.viaversion.viabackwards.api;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.ViaBackwardsConfig;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.Protocol1_11To1_11_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.Protocol1_12_1To1_12_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12to1_12_1.Protocol1_12To1_12_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.Protocol1_13_1To1_13_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_1to1_14_2.Protocol1_14_1To1_14_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3.Protocol1_14_2To1_14_3;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4.Protocol1_14_3To1_14_4;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.Protocol1_14To1_14_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_1to1_15_2.Protocol1_15_1To1_15_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15to1_15_1.Protocol1_15To1_15_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_2to1_16_3.Protocol1_16_2To1_16_3;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4.Protocol1_16_3To1_16_4;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16to1_16_1.Protocol1_16To1_16_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.Protocol1_17To1_17_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.Protocol1_18To1_18_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.Protocol1_19_1To1_19_3;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.Protocol1_19_3To1_19_4;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.Protocol1_19_4To1_20;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.Protocol1_19To1_19_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolManager;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.update.Version;
/*     */ import java.io.File;
/*     */ import java.util.logging.Logger;
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
/*     */ public interface ViaBackwardsPlatform
/*     */ {
/*     */   public static final String MINIMUM_VV_VERSION = "4.7.0";
/*     */   public static final String IMPL_VERSION = "git-ViaBackwards-4.7.0-1.20-pre5-SNAPSHOT:c468ec71";
/*     */   
/*     */   default void init(File dataFolder) {
/*  71 */     ViaBackwardsConfig config = new ViaBackwardsConfig(new File(dataFolder, "config.yml"));
/*  72 */     config.reloadConfig();
/*     */     
/*  74 */     ViaBackwards.init(this, (ViaBackwardsConfig)config);
/*     */     
/*  76 */     if (isOutdated()) {
/*  77 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     Via.getManager().getSubPlatforms().add("git-ViaBackwards-4.7.0-1.20-pre5-SNAPSHOT:c468ec71");
/*     */     
/*  83 */     getLogger().info("Loading translations...");
/*  84 */     TranslatableRewriter.loadTranslatables();
/*     */     
/*  86 */     getLogger().info("Registering protocols...");
/*  87 */     ProtocolManager protocolManager = Via.getManager().getProtocolManager();
/*  88 */     protocolManager.registerProtocol((Protocol)new Protocol1_9_4To1_10(), ProtocolVersion.v1_9_3, ProtocolVersion.v1_10);
/*     */     
/*  90 */     protocolManager.registerProtocol((Protocol)new Protocol1_10To1_11(), ProtocolVersion.v1_10, ProtocolVersion.v1_11);
/*  91 */     protocolManager.registerProtocol((Protocol)new Protocol1_11To1_11_1(), ProtocolVersion.v1_11, ProtocolVersion.v1_11_1);
/*     */     
/*  93 */     protocolManager.registerProtocol((Protocol)new Protocol1_11_1To1_12(), ProtocolVersion.v1_11_1, ProtocolVersion.v1_12);
/*  94 */     protocolManager.registerProtocol((Protocol)new Protocol1_12To1_12_1(), ProtocolVersion.v1_12, ProtocolVersion.v1_12_1);
/*  95 */     protocolManager.registerProtocol((Protocol)new Protocol1_12_1To1_12_2(), ProtocolVersion.v1_12_1, ProtocolVersion.v1_12_2);
/*     */     
/*  97 */     protocolManager.registerProtocol((Protocol)new Protocol1_12_2To1_13(), ProtocolVersion.v1_12_2, ProtocolVersion.v1_13);
/*  98 */     protocolManager.registerProtocol((Protocol)new Protocol1_13To1_13_1(), ProtocolVersion.v1_13, ProtocolVersion.v1_13_1);
/*  99 */     protocolManager.registerProtocol((Protocol)new Protocol1_13_1To1_13_2(), ProtocolVersion.v1_13_1, ProtocolVersion.v1_13_2);
/*     */     
/* 101 */     protocolManager.registerProtocol((Protocol)new Protocol1_13_2To1_14(), ProtocolVersion.v1_13_2, ProtocolVersion.v1_14);
/* 102 */     protocolManager.registerProtocol((Protocol)new Protocol1_14To1_14_1(), ProtocolVersion.v1_14, ProtocolVersion.v1_14_1);
/* 103 */     protocolManager.registerProtocol((Protocol)new Protocol1_14_1To1_14_2(), ProtocolVersion.v1_14_1, ProtocolVersion.v1_14_2);
/* 104 */     protocolManager.registerProtocol((Protocol)new Protocol1_14_2To1_14_3(), ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_3);
/* 105 */     protocolManager.registerProtocol((Protocol)new Protocol1_14_3To1_14_4(), ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_4);
/*     */     
/* 107 */     protocolManager.registerProtocol((Protocol)new Protocol1_14_4To1_15(), ProtocolVersion.v1_14_4, ProtocolVersion.v1_15);
/* 108 */     protocolManager.registerProtocol((Protocol)new Protocol1_15To1_15_1(), ProtocolVersion.v1_15, ProtocolVersion.v1_15_1);
/* 109 */     protocolManager.registerProtocol((Protocol)new Protocol1_15_1To1_15_2(), ProtocolVersion.v1_15_1, ProtocolVersion.v1_15_2);
/*     */     
/* 111 */     protocolManager.registerProtocol((Protocol)new Protocol1_15_2To1_16(), ProtocolVersion.v1_15_2, ProtocolVersion.v1_16);
/* 112 */     protocolManager.registerProtocol((Protocol)new Protocol1_16To1_16_1(), ProtocolVersion.v1_16, ProtocolVersion.v1_16_1);
/* 113 */     protocolManager.registerProtocol((Protocol)new Protocol1_16_1To1_16_2(), ProtocolVersion.v1_16_1, ProtocolVersion.v1_16_2);
/* 114 */     protocolManager.registerProtocol((Protocol)new Protocol1_16_2To1_16_3(), ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_3);
/* 115 */     protocolManager.registerProtocol((Protocol)new Protocol1_16_3To1_16_4(), ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_4);
/*     */     
/* 117 */     protocolManager.registerProtocol((Protocol)new Protocol1_16_4To1_17(), ProtocolVersion.v1_16_4, ProtocolVersion.v1_17);
/* 118 */     protocolManager.registerProtocol((Protocol)new Protocol1_17To1_17_1(), ProtocolVersion.v1_17, ProtocolVersion.v1_17_1);
/*     */     
/* 120 */     protocolManager.registerProtocol((Protocol)new Protocol1_17_1To1_18(), ProtocolVersion.v1_17_1, ProtocolVersion.v1_18);
/* 121 */     protocolManager.registerProtocol((Protocol)new Protocol1_18To1_18_2(), ProtocolVersion.v1_18, ProtocolVersion.v1_18_2);
/*     */     
/* 123 */     protocolManager.registerProtocol((Protocol)new Protocol1_18_2To1_19(), ProtocolVersion.v1_18_2, ProtocolVersion.v1_19);
/* 124 */     protocolManager.registerProtocol((Protocol)new Protocol1_19To1_19_1(), ProtocolVersion.v1_19, ProtocolVersion.v1_19_1);
/* 125 */     protocolManager.registerProtocol((Protocol)new Protocol1_19_1To1_19_3(), ProtocolVersion.v1_19_1, ProtocolVersion.v1_19_3);
/* 126 */     protocolManager.registerProtocol((Protocol)new Protocol1_19_3To1_19_4(), ProtocolVersion.v1_19_3, ProtocolVersion.v1_19_4);
/*     */     
/* 128 */     protocolManager.registerProtocol((Protocol)new Protocol1_19_4To1_20(), ProtocolVersion.v1_19_4, ProtocolVersion.v1_20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Logger getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isOutdated() {
/* 139 */     String vvVersion = Via.getPlatform().getPluginVersion();
/* 140 */     if (vvVersion != null && (new Version(vvVersion)).compareTo(new Version("4.7.0--")) < 0) {
/* 141 */       getLogger().severe("================================");
/* 142 */       getLogger().severe("YOUR VIAVERSION IS OUTDATED");
/* 143 */       getLogger().severe("PLEASE USE VIAVERSION 4.7.0 OR NEWER");
/* 144 */       getLogger().severe("LINK: https://ci.viaversion.com/");
/* 145 */       getLogger().severe("VIABACKWARDS WILL NOW DISABLE");
/* 146 */       getLogger().severe("================================");
/* 147 */       return true;
/*     */     } 
/* 149 */     return false;
/*     */   }
/*     */   
/*     */   void disable();
/*     */   
/*     */   File getDataFolder();
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\ViaBackwardsPlatform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */