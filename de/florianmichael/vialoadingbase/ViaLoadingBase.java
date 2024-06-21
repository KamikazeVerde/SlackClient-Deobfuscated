/*     */ package de.florianmichael.vialoadingbase;
/*     */ 
/*     */ import com.viaversion.viaversion.ViaManagerImpl;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.ViaManager;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
/*     */ import de.florianmichael.vialoadingbase.model.ComparableProtocolVersion;
/*     */ import de.florianmichael.vialoadingbase.model.Platform;
/*     */ import de.florianmichael.vialoadingbase.platform.ViaVersionPlatformImpl;
/*     */ import de.florianmichael.vialoadingbase.platform.viaversion.VLBViaCommandHandler;
/*     */ import de.florianmichael.vialoadingbase.platform.viaversion.VLBViaInjector;
/*     */ import de.florianmichael.vialoadingbase.platform.viaversion.VLBViaProviders;
/*     */ import de.florianmichael.vialoadingbase.util.JLoggerToLog4j;
/*     */ import java.io.File;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.logging.log4j.LogManager;
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
/*     */ public class ViaLoadingBase
/*     */ {
/*     */   public static final String VERSION = "${vialoadingbase_version}";
/*  46 */   public static final Logger LOGGER = (Logger)new JLoggerToLog4j(LogManager.getLogger("ViaLoadingBase"));
/*     */   
/*  48 */   public static final Platform PSEUDO_VIA_VERSION = new Platform("ViaVersion", () -> true, () -> { 
/*     */       }protocolVersions -> {
/*     */       
/*  51 */       }); public static final Platform PLATFORM_VIA_BACKWARDS = new Platform("ViaBackwards", () -> inClassPath("com.viaversion.viabackwards.api.ViaBackwardsPlatform"), () -> { 
/*  52 */       }); public static final Platform PLATFORM_VIA_REWIND = new Platform("ViaRewind", () -> inClassPath("de.gerrygames.viarewind.api.ViaRewindPlatform"), () -> {
/*     */       
/*  54 */       }); public static final Map<ProtocolVersion, ComparableProtocolVersion> PROTOCOLS = new LinkedHashMap<>();
/*     */   
/*     */   private static ViaLoadingBase instance;
/*     */   
/*     */   private final LinkedList<Platform> platforms;
/*     */   
/*     */   private final File runDirectory;
/*     */   private final int nativeVersion;
/*     */   private final BooleanSupplier forceNativeVersionCondition;
/*     */   private final Supplier<JsonObject> dumpSupplier;
/*     */   private final Consumer<ViaProviders> providers;
/*     */   private final Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer;
/*     */   private final Consumer<ComparableProtocolVersion> onProtocolReload;
/*     */   private ComparableProtocolVersion nativeProtocolVersion;
/*     */   private ComparableProtocolVersion targetProtocolVersion;
/*     */   
/*     */   public ViaLoadingBase(LinkedList<Platform> platforms, File runDirectory, int nativeVersion, BooleanSupplier forceNativeVersionCondition, Supplier<JsonObject> dumpSupplier, Consumer<ViaProviders> providers, Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer, Consumer<ComparableProtocolVersion> onProtocolReload) {
/*  71 */     this.platforms = platforms;
/*     */     
/*  73 */     this.runDirectory = new File(runDirectory, "ViaLoadingBase");
/*  74 */     this.nativeVersion = nativeVersion;
/*  75 */     this.forceNativeVersionCondition = forceNativeVersionCondition;
/*  76 */     this.dumpSupplier = dumpSupplier;
/*  77 */     this.providers = providers;
/*  78 */     this.managerBuilderConsumer = managerBuilderConsumer;
/*  79 */     this.onProtocolReload = onProtocolReload;
/*     */     
/*  81 */     instance = this;
/*  82 */     initPlatform();
/*     */   }
/*     */   
/*     */   public ComparableProtocolVersion getTargetVersion() {
/*  86 */     if (this.forceNativeVersionCondition != null && this.forceNativeVersionCondition.getAsBoolean()) return this.nativeProtocolVersion;
/*     */     
/*  88 */     return this.targetProtocolVersion;
/*     */   }
/*     */   
/*     */   public void reload(ProtocolVersion protocolVersion) {
/*  92 */     reload(fromProtocolVersion(protocolVersion));
/*     */   }
/*     */   
/*     */   public void reload(ComparableProtocolVersion protocolVersion) {
/*  96 */     this.targetProtocolVersion = protocolVersion;
/*     */     
/*  98 */     if (this.onProtocolReload != null) this.onProtocolReload.accept(this.targetProtocolVersion); 
/*     */   }
/*     */   
/*     */   public void initPlatform() {
/* 102 */     for (Platform platform : this.platforms) platform.createProtocolPath(); 
/* 103 */     for (ProtocolVersion preProtocol : Platform.TEMP_INPUT_PROTOCOLS) PROTOCOLS.put(preProtocol, new ComparableProtocolVersion(preProtocol.getVersion(), preProtocol.getName(), Platform.TEMP_INPUT_PROTOCOLS.indexOf(preProtocol)));
/*     */     
/* 105 */     this.nativeProtocolVersion = fromProtocolVersion(ProtocolVersion.getProtocol(this.nativeVersion));
/* 106 */     this.targetProtocolVersion = this.nativeProtocolVersion;
/*     */     
/* 108 */     ViaVersionPlatformImpl viaVersionPlatform = new ViaVersionPlatformImpl(LOGGER);
/* 109 */     ViaManagerImpl.ViaManagerBuilder builder = ViaManagerImpl.builder()
/* 110 */       .platform((ViaPlatform)viaVersionPlatform)
/* 111 */       .loader((ViaPlatformLoader)new VLBViaProviders())
/* 112 */       .injector((ViaInjector)new VLBViaInjector())
/* 113 */       .commandHandler((ViaCommandHandler)new VLBViaCommandHandler());
/*     */ 
/*     */     
/* 116 */     if (this.managerBuilderConsumer != null) this.managerBuilderConsumer.accept(builder);
/*     */     
/* 118 */     Via.init((ViaManager)builder.build());
/*     */     
/* 120 */     ViaManagerImpl manager = (ViaManagerImpl)Via.getManager();
/* 121 */     manager.addEnableListener(() -> {
/*     */           for (Platform platform : this.platforms)
/*     */             platform.build(LOGGER); 
/*     */         });
/* 125 */     manager.init();
/* 126 */     manager.onServerLoaded();
/* 127 */     manager.getProtocolManager().setMaxProtocolPathSize(2147483647);
/* 128 */     manager.getProtocolManager().setMaxPathDeltaIncrease(-1);
/* 129 */     ((ProtocolManagerImpl)manager.getProtocolManager()).refreshVersions();
/*     */     
/* 131 */     LOGGER.info("ViaLoadingBase has loaded " + Platform.COUNT + "/" + this.platforms.size() + " platforms");
/*     */   }
/*     */   
/*     */   public static ViaLoadingBase getInstance() {
/* 135 */     return instance;
/*     */   }
/*     */   
/*     */   public List<Platform> getSubPlatforms() {
/* 139 */     return this.platforms;
/*     */   }
/*     */   
/*     */   public File getRunDirectory() {
/* 143 */     return this.runDirectory;
/*     */   }
/*     */   
/*     */   public int getNativeVersion() {
/* 147 */     return this.nativeVersion;
/*     */   }
/*     */   
/*     */   public Supplier<JsonObject> getDumpSupplier() {
/* 151 */     return this.dumpSupplier;
/*     */   }
/*     */   
/*     */   public Consumer<ViaProviders> getProviders() {
/* 155 */     return this.providers;
/*     */   }
/*     */   
/*     */   public static boolean inClassPath(String name) {
/*     */     try {
/* 160 */       Class.forName(name);
/* 161 */       return true;
/* 162 */     } catch (Exception ignored) {
/* 163 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ComparableProtocolVersion fromProtocolVersion(ProtocolVersion protocolVersion) {
/* 168 */     return PROTOCOLS.get(protocolVersion);
/*     */   }
/*     */   
/*     */   public static ComparableProtocolVersion fromProtocolId(int protocolId) {
/* 172 */     return PROTOCOLS.values().stream().filter(protocol -> (protocol.getVersion() == paramInt)).findFirst().orElse(null);
/*     */   }
/*     */   
/*     */   public static List<ProtocolVersion> getProtocols() {
/* 176 */     return new LinkedList<>(PROTOCOLS.keySet());
/*     */   }
/*     */   
/*     */   public static class ViaLoadingBaseBuilder {
/* 180 */     private final LinkedList<Platform> platforms = new LinkedList<>();
/*     */     
/*     */     private File runDirectory;
/*     */     private Integer nativeVersion;
/*     */     private BooleanSupplier forceNativeVersionCondition;
/*     */     private Supplier<JsonObject> dumpSupplier;
/*     */     private Consumer<ViaProviders> providers;
/*     */     private Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer;
/*     */     private Consumer<ComparableProtocolVersion> onProtocolReload;
/*     */     
/*     */     public ViaLoadingBaseBuilder() {
/* 191 */       this.platforms.add(ViaLoadingBase.PSEUDO_VIA_VERSION);
/*     */       
/* 193 */       this.platforms.add(ViaLoadingBase.PLATFORM_VIA_BACKWARDS);
/* 194 */       this.platforms.add(ViaLoadingBase.PLATFORM_VIA_REWIND);
/*     */     }
/*     */     
/*     */     public static ViaLoadingBaseBuilder create() {
/* 198 */       return new ViaLoadingBaseBuilder();
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder platform(Platform platform) {
/* 202 */       this.platforms.add(platform);
/* 203 */       return this;
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder platform(Platform platform, int position) {
/* 207 */       this.platforms.add(position, platform);
/* 208 */       return this;
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder runDirectory(File runDirectory) {
/* 212 */       this.runDirectory = runDirectory;
/* 213 */       return this;
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder nativeVersion(int nativeVersion) {
/* 217 */       this.nativeVersion = Integer.valueOf(nativeVersion);
/* 218 */       return this;
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder forceNativeVersionCondition(BooleanSupplier forceNativeVersionCondition) {
/* 222 */       this.forceNativeVersionCondition = forceNativeVersionCondition;
/* 223 */       return this;
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder dumpSupplier(Supplier<JsonObject> dumpSupplier) {
/* 227 */       this.dumpSupplier = dumpSupplier;
/* 228 */       return this;
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder providers(Consumer<ViaProviders> providers) {
/* 232 */       this.providers = providers;
/* 233 */       return this;
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder managerBuilderConsumer(Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer) {
/* 237 */       this.managerBuilderConsumer = managerBuilderConsumer;
/* 238 */       return this;
/*     */     }
/*     */     
/*     */     public ViaLoadingBaseBuilder onProtocolReload(Consumer<ComparableProtocolVersion> onProtocolReload) {
/* 242 */       this.onProtocolReload = onProtocolReload;
/* 243 */       return this;
/*     */     }
/*     */     
/*     */     public void build() {
/* 247 */       if (ViaLoadingBase.getInstance() != null) {
/* 248 */         ViaLoadingBase.LOGGER.severe("ViaLoadingBase has already started the platform!");
/*     */         return;
/*     */       } 
/* 251 */       if (this.runDirectory == null || this.nativeVersion == null) {
/* 252 */         ViaLoadingBase.LOGGER.severe("Please check your ViaLoadingBaseBuilder arguments!");
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\ViaLoadingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */