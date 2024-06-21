/*     */ package com.viaversion.viaversion.commands.defaultsubs;
/*     */ 
/*     */ import com.google.common.io.CharStreams;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*     */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.dump.DumpTemplate;
/*     */ import com.viaversion.viaversion.dump.VersionInfo;
/*     */ import com.viaversion.viaversion.libs.gson.GsonBuilder;
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
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
/*     */ public class DumpSubCmd
/*     */   extends ViaSubCommand
/*     */ {
/*     */   public String name() {
/*  51 */     return "dump";
/*     */   }
/*     */ 
/*     */   
/*     */   public String description() {
/*  56 */     return "Dump information about your server, this is helpful if you report bugs.";
/*     */   }
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
/*     */   public boolean execute(final ViaCommandSender sender, String[] args) {
/*  70 */     final VersionInfo version = new VersionInfo(System.getProperty("java.version"), System.getProperty("os.name"), Via.getAPI().getServerVersion().lowestSupportedVersion(), Via.getManager().getProtocolManager().getSupportedVersions(), Via.getPlatform().getPlatformName(), Via.getPlatform().getPlatformVersion(), Via.getPlatform().getPluginVersion(), "git-ViaVersion-4.7.0-1.20-pre5-SNAPSHOT:5c145d01", Via.getManager().getSubPlatforms());
/*     */ 
/*     */     
/*  73 */     Map<String, Object> configuration = Via.getPlatform().getConfigurationProvider().getValues();
/*  74 */     final DumpTemplate template = new DumpTemplate(version, configuration, Via.getPlatform().getDump(), Via.getManager().getInjector().getDump(), getPlayerSample(sender.getUUID()));
/*     */     
/*  76 */     Via.getPlatform().runAsync(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             HttpURLConnection con;
/*     */             try {
/*  82 */               con = (HttpURLConnection)(new URL("https://dump.viaversion.com/documents")).openConnection();
/*  83 */             } catch (IOException e) {
/*  84 */               sender.sendMessage("§4Failed to dump, please check the console for more information");
/*  85 */               Via.getPlatform().getLogger().log(Level.WARNING, "Could not paste ViaVersion dump to ViaVersion Dump", e);
/*     */               return;
/*     */             } 
/*     */             try {
/*  89 */               con.setRequestProperty("Content-Type", "application/json");
/*  90 */               con.addRequestProperty("User-Agent", "ViaVersion/" + version.getPluginVersion());
/*  91 */               con.setRequestMethod("POST");
/*  92 */               con.setDoOutput(true);
/*     */               
/*  94 */               OutputStream out = con.getOutputStream();
/*  95 */               out.write((new GsonBuilder()).setPrettyPrinting().create().toJson(template).getBytes(StandardCharsets.UTF_8));
/*  96 */               out.close();
/*     */               
/*  98 */               if (con.getResponseCode() == 429) {
/*  99 */                 sender.sendMessage("§4You can only paste once every minute to protect our systems.");
/*     */                 
/*     */                 return;
/*     */               } 
/* 103 */               String rawOutput = CharStreams.toString(new InputStreamReader(con.getInputStream()));
/* 104 */               con.getInputStream().close();
/*     */               
/* 106 */               JsonObject output = (JsonObject)GsonUtil.getGson().fromJson(rawOutput, JsonObject.class);
/*     */               
/* 108 */               if (!output.has("key")) {
/* 109 */                 throw new InvalidObjectException("Key is not given in Hastebin output");
/*     */               }
/* 111 */               sender.sendMessage("§2We've made a dump with useful information, report your issue and provide this url: " + DumpSubCmd.this.getUrl(output.get("key").getAsString()));
/* 112 */             } catch (Exception e) {
/* 113 */               sender.sendMessage("§4Failed to dump, please check the console for more information");
/* 114 */               Via.getPlatform().getLogger().log(Level.WARNING, "Could not paste ViaVersion dump to Hastebin", e);
/*     */               try {
/* 116 */                 if (con.getResponseCode() < 200 || con.getResponseCode() > 400) {
/* 117 */                   String rawOutput = CharStreams.toString(new InputStreamReader(con.getErrorStream()));
/* 118 */                   con.getErrorStream().close();
/* 119 */                   Via.getPlatform().getLogger().log(Level.WARNING, "Page returned: " + rawOutput);
/*     */                 } 
/* 121 */               } catch (IOException e1) {
/* 122 */                 Via.getPlatform().getLogger().log(Level.WARNING, "Failed to capture further info", e1);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   private String getUrl(String id) {
/* 132 */     return String.format("https://dump.viaversion.com/%s", new Object[] { id });
/*     */   }
/*     */   
/*     */   private JsonObject getPlayerSample(UUID senderUuid) {
/* 136 */     JsonObject playerSample = new JsonObject();
/*     */     
/* 138 */     JsonObject versions = new JsonObject();
/* 139 */     playerSample.add("versions", (JsonElement)versions);
/* 140 */     Map<ProtocolVersion, Integer> playerVersions = new TreeMap<>((o1, o2) -> ProtocolVersion.getIndex(o2) - ProtocolVersion.getIndex(o1));
/* 141 */     for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
/* 142 */       ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(connection.getProtocolInfo().getProtocolVersion());
/* 143 */       playerVersions.compute(protocolVersion, (v, num) -> Integer.valueOf((num != null) ? (num.intValue() + 1) : 1));
/*     */     } 
/* 145 */     for (Map.Entry<ProtocolVersion, Integer> entry : playerVersions.entrySet()) {
/* 146 */       versions.addProperty(((ProtocolVersion)entry.getKey()).getName(), entry.getValue());
/*     */     }
/*     */ 
/*     */     
/* 150 */     Set<List<String>> pipelines = new HashSet<>();
/* 151 */     UserConnection senderConnection = Via.getAPI().getConnection(senderUuid);
/* 152 */     if (senderConnection != null && senderConnection.getChannel() != null) {
/* 153 */       pipelines.add(senderConnection.getChannel().pipeline().names());
/*     */     }
/*     */ 
/*     */     
/* 157 */     for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
/* 158 */       if (connection.getChannel() == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 162 */       List<String> names = connection.getChannel().pipeline().names();
/* 163 */       if (pipelines.add(names) && pipelines.size() == 3) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 168 */     int i = 0;
/* 169 */     for (List<String> pipeline : pipelines) {
/* 170 */       JsonArray senderPipeline = new JsonArray(pipeline.size());
/* 171 */       for (String name : pipeline) {
/* 172 */         senderPipeline.add(name);
/*     */       }
/*     */       
/* 175 */       playerSample.add("pipeline-" + i++, (JsonElement)senderPipeline);
/*     */     } 
/*     */     
/* 178 */     return playerSample;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\DumpSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */