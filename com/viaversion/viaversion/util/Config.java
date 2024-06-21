/*     */ package com.viaversion.viaversion.util;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
/*     */ import com.viaversion.viaversion.compatibility.YamlCompat;
/*     */ import com.viaversion.viaversion.compatibility.unsafe.Yaml1Compat;
/*     */ import com.viaversion.viaversion.compatibility.unsafe.Yaml2Compat;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentSkipListMap;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.Yaml;
/*     */ import org.yaml.snakeyaml.constructor.BaseConstructor;
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
/*     */ public abstract class Config
/*     */   implements ConfigurationProvider
/*     */ {
/*     */   private static final ThreadLocal<Yaml> YAML;
/*  44 */   private static final YamlCompat YAMP_COMPAT = YamlCompat.isVersion1() ? (YamlCompat)new Yaml1Compat() : (YamlCompat)new Yaml2Compat(); static {
/*  45 */     YAML = ThreadLocal.withInitial(() -> {
/*     */           DumperOptions options = new DumperOptions();
/*     */           options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
/*     */           options.setPrettyFlow(false);
/*     */           options.setIndent(2);
/*     */           return new Yaml((BaseConstructor)YAMP_COMPAT.createSafeConstructor(), YAMP_COMPAT.createRepresenter(options), options);
/*     */         });
/*     */   }
/*  53 */   private final CommentStore commentStore = new CommentStore('.', 2);
/*     */ 
/*     */   
/*     */   private final File configFile;
/*     */ 
/*     */   
/*     */   private Map<String, Object> config;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Config(File configFile) {
/*  64 */     this.configFile = configFile;
/*     */   }
/*     */   
/*     */   public URL getDefaultConfigURL() {
/*  68 */     return getClass().getClassLoader().getResource("assets/viaversion/config.yml");
/*     */   }
/*     */   
/*     */   public Map<String, Object> loadConfig(File location) {
/*  72 */     return loadConfig(location, getDefaultConfigURL());
/*     */   }
/*     */   
/*     */   public synchronized Map<String, Object> loadConfig(File location, URL jarConfigFile) {
/*  76 */     List<String> unsupported = getUnsupportedOptions();
/*     */     try {
/*  78 */       this.commentStore.storeComments(jarConfigFile.openStream());
/*  79 */       for (String option : unsupported) {
/*  80 */         List<String> comments = this.commentStore.header(option);
/*  81 */         if (comments != null) {
/*  82 */           comments.clear();
/*     */         }
/*     */       } 
/*  85 */     } catch (IOException e) {
/*  86 */       e.printStackTrace();
/*     */     } 
/*  88 */     Map<String, Object> config = null;
/*  89 */     if (location.exists()) {
/*  90 */       try (FileInputStream input = new FileInputStream(location)) {
/*  91 */         config = (Map<String, Object>)((Yaml)YAML.get()).load(input);
/*  92 */       } catch (IOException e) {
/*  93 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  96 */     if (config == null) {
/*  97 */       config = new HashMap<>();
/*     */     }
/*     */     
/* 100 */     Map<String, Object> defaults = config;
/* 101 */     try (InputStream stream = jarConfigFile.openStream()) {
/* 102 */       defaults = (Map<String, Object>)((Yaml)YAML.get()).load(stream);
/* 103 */       for (String option : unsupported) {
/* 104 */         defaults.remove(option);
/*     */       }
/*     */       
/* 107 */       for (Map.Entry<String, Object> entry : config.entrySet()) {
/*     */         
/* 109 */         if (defaults.containsKey(entry.getKey()) && !unsupported.contains(entry.getKey())) {
/* 110 */           defaults.put(entry.getKey(), entry.getValue());
/*     */         }
/*     */       } 
/* 113 */     } catch (IOException e) {
/* 114 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 117 */     handleConfig(defaults);
/*     */     
/* 119 */     saveConfig(location, defaults);
/*     */     
/* 121 */     return defaults;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void saveConfig(File location, Map<String, Object> config) {
/*     */     try {
/* 128 */       this.commentStore.writeComments(((Yaml)YAML.get()).dump(config), location);
/* 129 */     } catch (IOException e) {
/* 130 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String path, Object value) {
/* 138 */     this.config.put(path, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveConfig() {
/* 143 */     this.configFile.getParentFile().mkdirs();
/* 144 */     saveConfig(this.configFile, this.config);
/*     */   }
/*     */   
/*     */   public void saveConfig(File file) {
/* 148 */     saveConfig(file, this.config);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadConfig() {
/* 153 */     this.configFile.getParentFile().mkdirs();
/* 154 */     this.config = new ConcurrentSkipListMap<>(loadConfig(this.configFile));
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> getValues() {
/* 159 */     return this.config;
/*     */   }
/*     */   
/*     */   public <T> T get(String key, Class<T> clazz, T def) {
/* 163 */     Object o = this.config.get(key);
/* 164 */     if (o != null) {
/* 165 */       return (T)o;
/*     */     }
/* 167 */     return def;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String key, boolean def) {
/* 172 */     Object o = this.config.get(key);
/* 173 */     if (o != null) {
/* 174 */       return ((Boolean)o).booleanValue();
/*     */     }
/* 176 */     return def;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(String key, String def) {
/* 181 */     Object o = this.config.get(key);
/* 182 */     if (o != null) {
/* 183 */       return (String)o;
/*     */     }
/* 185 */     return def;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(String key, int def) {
/* 190 */     Object o = this.config.get(key);
/* 191 */     if (o != null) {
/* 192 */       if (o instanceof Number) {
/* 193 */         return ((Number)o).intValue();
/*     */       }
/* 195 */       return def;
/*     */     } 
/*     */     
/* 198 */     return def;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(String key, double def) {
/* 203 */     Object o = this.config.get(key);
/* 204 */     if (o != null) {
/* 205 */       if (o instanceof Number) {
/* 206 */         return ((Number)o).doubleValue();
/*     */       }
/* 208 */       return def;
/*     */     } 
/*     */     
/* 211 */     return def;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Integer> getIntegerList(String key) {
/* 216 */     Object o = this.config.get(key);
/* 217 */     return (o != null) ? (List<Integer>)o : new ArrayList<>();
/*     */   }
/*     */   
/*     */   public List<String> getStringList(String key) {
/* 221 */     Object o = this.config.get(key);
/* 222 */     return (o != null) ? (List<String>)o : new ArrayList<>();
/*     */   }
/*     */   
/*     */   public <T> List<T> getListSafe(String key, Class<T> type, String invalidValueMessage) {
/* 226 */     Object o = this.config.get(key);
/* 227 */     if (o instanceof List) {
/* 228 */       List<?> list = (List)o;
/* 229 */       List<T> filteredValues = new ArrayList<>();
/* 230 */       for (Object o1 : list) {
/* 231 */         if (type.isInstance(o1)) {
/* 232 */           filteredValues.add(type.cast(o1)); continue;
/* 233 */         }  if (invalidValueMessage != null) {
/* 234 */           Via.getPlatform().getLogger().warning(String.format(invalidValueMessage, new Object[] { o1 }));
/*     */         }
/*     */       } 
/* 237 */       return filteredValues;
/*     */     } 
/* 239 */     return new ArrayList<>();
/*     */   }
/*     */   
/*     */   public JsonElement getSerializedComponent(String key) {
/* 243 */     Object o = this.config.get(key);
/* 244 */     if (o != null && !((String)o).isEmpty()) {
/* 245 */       return GsonComponentSerializer.gson().serializeToTree((Component)LegacyComponentSerializer.legacySection().deserialize((String)o));
/*     */     }
/* 247 */     return null;
/*     */   }
/*     */   
/*     */   protected abstract void handleConfig(Map<String, Object> paramMap);
/*     */   
/*     */   public abstract List<String> getUnsupportedOptions();
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */