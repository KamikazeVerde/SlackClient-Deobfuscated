/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerProfileCache
/*     */ {
/*  45 */   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*  46 */   private final Map<String, ProfileEntry> usernameToProfileEntryMap = Maps.newHashMap();
/*  47 */   private final Map<UUID, ProfileEntry> uuidToProfileEntryMap = Maps.newHashMap();
/*  48 */   private final LinkedList<GameProfile> gameProfiles = Lists.newLinkedList();
/*     */   
/*     */   private final MinecraftServer mcServer;
/*     */   
/*  52 */   private static final ParameterizedType TYPE = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  56 */         return new Type[] { PlayerProfileCache.ProfileEntry.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  60 */         return List.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  64 */         return null;
/*     */       }
/*     */     };
/*     */   protected final Gson gson; private final File usercacheFile;
/*     */   
/*     */   public PlayerProfileCache(MinecraftServer server, File cacheFile) {
/*  70 */     this.mcServer = server;
/*  71 */     this.usercacheFile = cacheFile;
/*  72 */     GsonBuilder gsonbuilder = new GsonBuilder();
/*  73 */     gsonbuilder.registerTypeHierarchyAdapter(ProfileEntry.class, new Serializer());
/*  74 */     this.gson = gsonbuilder.create();
/*  75 */     load();
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
/*     */   private static GameProfile getGameProfile(MinecraftServer server, String username) {
/*  89 */     final GameProfile[] agameprofile = new GameProfile[1];
/*  90 */     ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback()
/*     */       {
/*     */         public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_)
/*     */         {
/*  94 */           agameprofile[0] = p_onProfileLookupSucceeded_1_;
/*     */         }
/*     */         
/*     */         public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
/*  98 */           agameprofile[0] = null;
/*     */         }
/*     */       };
/* 101 */     server.getGameProfileRepository().findProfilesByNames(new String[] { username }, Agent.MINECRAFT, profilelookupcallback);
/*     */     
/* 103 */     if (!server.isServerInOnlineMode() && agameprofile[0] == null) {
/*     */       
/* 105 */       UUID uuid = EntityPlayer.getUUID(new GameProfile(null, username));
/* 106 */       GameProfile gameprofile = new GameProfile(uuid, username);
/* 107 */       profilelookupcallback.onProfileLookupSucceeded(gameprofile);
/*     */     } 
/*     */     
/* 110 */     return agameprofile[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(GameProfile gameProfile) {
/* 120 */     addEntry(gameProfile, null);
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
/*     */   private void addEntry(GameProfile gameProfile, Date expirationDate) {
/* 132 */     UUID uuid = gameProfile.getId();
/*     */     
/* 134 */     if (expirationDate == null) {
/*     */       
/* 136 */       Calendar calendar = Calendar.getInstance();
/* 137 */       calendar.setTime(new Date());
/* 138 */       calendar.add(2, 1);
/* 139 */       expirationDate = calendar.getTime();
/*     */     } 
/*     */     
/* 142 */     String s = gameProfile.getName().toLowerCase(Locale.ROOT);
/* 143 */     ProfileEntry playerprofilecache$profileentry = new ProfileEntry(gameProfile, expirationDate);
/*     */     
/* 145 */     if (this.uuidToProfileEntryMap.containsKey(uuid)) {
/*     */       
/* 147 */       ProfileEntry playerprofilecache$profileentry1 = this.uuidToProfileEntryMap.get(uuid);
/* 148 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry1.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 149 */       this.gameProfiles.remove(gameProfile);
/*     */     } 
/*     */     
/* 152 */     this.usernameToProfileEntryMap.put(gameProfile.getName().toLowerCase(Locale.ROOT), playerprofilecache$profileentry);
/* 153 */     this.uuidToProfileEntryMap.put(uuid, playerprofilecache$profileentry);
/* 154 */     this.gameProfiles.addFirst(gameProfile);
/* 155 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getGameProfileForUsername(String username) {
/* 166 */     String s = username.toLowerCase(Locale.ROOT);
/* 167 */     ProfileEntry playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */     
/* 169 */     if (playerprofilecache$profileentry != null && (new Date()).getTime() >= playerprofilecache$profileentry.expirationDate.getTime()) {
/*     */       
/* 171 */       this.uuidToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getId());
/* 172 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 173 */       this.gameProfiles.remove(playerprofilecache$profileentry.getGameProfile());
/* 174 */       playerprofilecache$profileentry = null;
/*     */     } 
/*     */     
/* 177 */     if (playerprofilecache$profileentry != null) {
/*     */       
/* 179 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 180 */       this.gameProfiles.remove(gameprofile);
/* 181 */       this.gameProfiles.addFirst(gameprofile);
/*     */     }
/*     */     else {
/*     */       
/* 185 */       GameProfile gameprofile1 = getGameProfile(this.mcServer, s);
/*     */       
/* 187 */       if (gameprofile1 != null) {
/*     */         
/* 189 */         addEntry(gameprofile1);
/* 190 */         playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     save();
/* 195 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getUsernames() {
/* 203 */     List<String> list = Lists.newArrayList(this.usernameToProfileEntryMap.keySet());
/* 204 */     return list.<String>toArray(new String[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getProfileByUUID(UUID uuid) {
/* 214 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/* 215 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProfileEntry getByUUID(UUID uuid) {
/* 225 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/*     */     
/* 227 */     if (playerprofilecache$profileentry != null) {
/*     */       
/* 229 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 230 */       this.gameProfiles.remove(gameprofile);
/* 231 */       this.gameProfiles.addFirst(gameprofile);
/*     */     } 
/*     */     
/* 234 */     return playerprofilecache$profileentry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/* 242 */     BufferedReader bufferedreader = null;
/*     */ 
/*     */     
/*     */     try {
/* 246 */       bufferedreader = Files.newReader(this.usercacheFile, Charsets.UTF_8);
/* 247 */       List<ProfileEntry> list = (List<ProfileEntry>)this.gson.fromJson(bufferedreader, TYPE);
/* 248 */       this.usernameToProfileEntryMap.clear();
/* 249 */       this.uuidToProfileEntryMap.clear();
/* 250 */       this.gameProfiles.clear();
/*     */       
/* 252 */       for (ProfileEntry playerprofilecache$profileentry : Lists.reverse(list))
/*     */       {
/* 254 */         if (playerprofilecache$profileentry != null)
/*     */         {
/* 256 */           addEntry(playerprofilecache$profileentry.getGameProfile(), playerprofilecache$profileentry.getExpirationDate());
/*     */         }
/*     */       }
/*     */     
/* 260 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/* 264 */     catch (JsonParseException jsonParseException) {
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 270 */       IOUtils.closeQuietly(bufferedreader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/* 279 */     String s = this.gson.toJson(getEntriesWithLimit(1000));
/* 280 */     BufferedWriter bufferedwriter = null;
/*     */ 
/*     */     
/*     */     try {
/* 284 */       bufferedwriter = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
/* 285 */       bufferedwriter.write(s);
/*     */       
/*     */       return;
/* 288 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/* 292 */     catch (IOException var9) {
/*     */       
/*     */       return;
/*     */     }
/*     */     finally {
/*     */       
/* 298 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ProfileEntry> getEntriesWithLimit(int limitSize) {
/* 304 */     ArrayList<ProfileEntry> arraylist = Lists.newArrayList();
/*     */     
/* 306 */     for (GameProfile gameprofile : Lists.newArrayList(Iterators.limit(this.gameProfiles.iterator(), limitSize))) {
/*     */       
/* 308 */       ProfileEntry playerprofilecache$profileentry = getByUUID(gameprofile.getId());
/*     */       
/* 310 */       if (playerprofilecache$profileentry != null)
/*     */       {
/* 312 */         arraylist.add(playerprofilecache$profileentry);
/*     */       }
/*     */     } 
/*     */     
/* 316 */     return arraylist;
/*     */   }
/*     */ 
/*     */   
/*     */   class ProfileEntry
/*     */   {
/*     */     private final GameProfile gameProfile;
/*     */     private final Date expirationDate;
/*     */     
/*     */     private ProfileEntry(GameProfile gameProfileIn, Date expirationDateIn) {
/* 326 */       this.gameProfile = gameProfileIn;
/* 327 */       this.expirationDate = expirationDateIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getGameProfile() {
/* 332 */       return this.gameProfile;
/*     */     }
/*     */ 
/*     */     
/*     */     public Date getExpirationDate() {
/* 337 */       return this.expirationDate;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class Serializer
/*     */     implements JsonDeserializer<ProfileEntry>, JsonSerializer<ProfileEntry>
/*     */   {
/*     */     private Serializer() {}
/*     */ 
/*     */     
/*     */     public JsonElement serialize(PlayerProfileCache.ProfileEntry p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 349 */       JsonObject jsonobject = new JsonObject();
/* 350 */       jsonobject.addProperty("name", p_serialize_1_.getGameProfile().getName());
/* 351 */       UUID uuid = p_serialize_1_.getGameProfile().getId();
/* 352 */       jsonobject.addProperty("uuid", (uuid == null) ? "" : uuid.toString());
/* 353 */       jsonobject.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(p_serialize_1_.getExpirationDate()));
/* 354 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public PlayerProfileCache.ProfileEntry deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 359 */       if (p_deserialize_1_.isJsonObject()) {
/*     */         
/* 361 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 362 */         JsonElement jsonelement = jsonobject.get("name");
/* 363 */         JsonElement jsonelement1 = jsonobject.get("uuid");
/* 364 */         JsonElement jsonelement2 = jsonobject.get("expiresOn");
/*     */         
/* 366 */         if (jsonelement != null && jsonelement1 != null) {
/*     */           
/* 368 */           String s = jsonelement1.getAsString();
/* 369 */           String s1 = jsonelement.getAsString();
/* 370 */           Date date = null;
/*     */           
/* 372 */           if (jsonelement2 != null) {
/*     */             
/*     */             try {
/*     */               
/* 376 */               date = PlayerProfileCache.dateFormat.parse(jsonelement2.getAsString());
/*     */             }
/* 378 */             catch (ParseException var14) {
/*     */               
/* 380 */               date = null;
/*     */             } 
/*     */           }
/*     */           
/* 384 */           if (s1 != null && s != null) {
/*     */             UUID uuid;
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 390 */               uuid = UUID.fromString(s);
/*     */             }
/* 392 */             catch (Throwable var13) {
/*     */               
/* 394 */               return null;
/*     */             } 
/*     */             
/* 397 */             PlayerProfileCache.this.getClass(); PlayerProfileCache.ProfileEntry playerprofilecache$profileentry = new PlayerProfileCache.ProfileEntry(new GameProfile(uuid, s1), date);
/* 398 */             return playerprofilecache$profileentry;
/*     */           } 
/*     */ 
/*     */           
/* 402 */           return null;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 407 */         return null;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 412 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\server\management\PlayerProfileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */