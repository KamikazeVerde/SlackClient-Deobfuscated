/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.util.ChatColorUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class GameProfileStorage extends StoredObject {
/*  16 */   private final Map<UUID, GameProfile> properties = new HashMap<>();
/*     */   
/*     */   public GameProfileStorage(UserConnection user) {
/*  19 */     super(user);
/*     */   }
/*     */   
/*     */   public GameProfile put(UUID uuid, String name) {
/*  23 */     GameProfile gameProfile = new GameProfile(uuid, name);
/*  24 */     this.properties.put(uuid, gameProfile);
/*  25 */     return gameProfile;
/*     */   }
/*     */   
/*     */   public void putProperty(UUID uuid, Property property) {
/*  29 */     ((GameProfile)this.properties.computeIfAbsent((K)uuid, profile -> new GameProfile(uuid, null))).properties.add(property);
/*     */   }
/*     */   
/*     */   public void putProperty(UUID uuid, String name, String value, String signature) {
/*  33 */     putProperty(uuid, new Property(name, value, signature));
/*     */   }
/*     */   
/*     */   public GameProfile get(UUID uuid) {
/*  37 */     return this.properties.get(uuid);
/*     */   }
/*     */   
/*     */   public GameProfile get(String name, boolean ignoreCase) {
/*  41 */     if (ignoreCase) name = name.toLowerCase();
/*     */     
/*  43 */     for (GameProfile profile : this.properties.values()) {
/*  44 */       if (profile.name == null)
/*     */         continue; 
/*  46 */       String n = ignoreCase ? profile.name.toLowerCase() : profile.name;
/*     */       
/*  48 */       if (n.equals(name)) {
/*  49 */         return profile;
/*     */       }
/*     */     } 
/*  52 */     return null;
/*     */   }
/*     */   
/*     */   public List<GameProfile> getAllWithPrefix(String prefix, boolean ignoreCase) {
/*  56 */     if (ignoreCase) prefix = prefix.toLowerCase();
/*     */     
/*  58 */     ArrayList<GameProfile> profiles = new ArrayList<>();
/*     */     
/*  60 */     for (GameProfile profile : this.properties.values()) {
/*  61 */       if (profile.name == null)
/*     */         continue; 
/*  63 */       String n = ignoreCase ? profile.name.toLowerCase() : profile.name;
/*     */       
/*  65 */       if (n.startsWith(prefix)) profiles.add(profile);
/*     */     
/*     */     } 
/*  68 */     return profiles;
/*     */   }
/*     */   
/*     */   public GameProfile remove(UUID uuid) {
/*  72 */     return this.properties.remove(uuid);
/*     */   }
/*     */   
/*     */   public static class GameProfile
/*     */   {
/*     */     public final String name;
/*     */     public final UUID uuid;
/*     */     public String displayName;
/*     */     public int ping;
/*  81 */     public List<GameProfileStorage.Property> properties = new ArrayList<>();
/*  82 */     public int gamemode = 0;
/*     */     
/*     */     public GameProfile(UUID uuid, String name) {
/*  85 */       this.name = name;
/*  86 */       this.uuid = uuid;
/*     */     }
/*     */     
/*     */     public Item getSkull() {
/*  90 */       CompoundTag tag = new CompoundTag();
/*  91 */       CompoundTag ownerTag = new CompoundTag();
/*  92 */       tag.put("SkullOwner", (Tag)ownerTag);
/*  93 */       ownerTag.put("Id", (Tag)new StringTag(this.uuid.toString()));
/*  94 */       CompoundTag properties = new CompoundTag();
/*  95 */       ownerTag.put("Properties", (Tag)properties);
/*  96 */       ListTag textures = new ListTag(CompoundTag.class);
/*  97 */       properties.put("textures", (Tag)textures);
/*  98 */       for (GameProfileStorage.Property property : this.properties) {
/*  99 */         if (property.name.equals("textures")) {
/* 100 */           CompoundTag textureTag = new CompoundTag();
/* 101 */           textureTag.put("Value", (Tag)new StringTag(property.value));
/* 102 */           if (property.signature != null) {
/* 103 */             textureTag.put("Signature", (Tag)new StringTag(property.signature));
/*     */           }
/* 105 */           textures.add((Tag)textureTag);
/*     */         } 
/*     */       } 
/*     */       
/* 109 */       return (Item)new DataItem(397, (byte)1, (short)3, tag);
/*     */     }
/*     */     
/*     */     public String getDisplayName() {
/* 113 */       String displayName = (this.displayName == null) ? this.name : this.displayName;
/* 114 */       if (displayName.length() > 16) displayName = ChatUtil.removeUnusedColor(displayName, 'f'); 
/* 115 */       if (displayName.length() > 16) displayName = ChatColorUtil.stripColor(displayName); 
/* 116 */       if (displayName.length() > 16) displayName = displayName.substring(0, 16); 
/* 117 */       return displayName;
/*     */     }
/*     */     
/*     */     public void setDisplayName(String displayName) {
/* 121 */       this.displayName = displayName;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Property {
/*     */     public final String name;
/*     */     public final String value;
/*     */     public final String signature;
/*     */     
/*     */     public Property(String name, String value, String signature) {
/* 131 */       this.name = name;
/* 132 */       this.value = value;
/* 133 */       this.signature = signature;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\storage\GameProfileStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */