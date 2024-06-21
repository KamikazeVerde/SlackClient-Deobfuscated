/*     */ package us.myles.ViaVersion.api.protocol;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.protocol.version.VersionRange;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ @Deprecated
/*     */ public class ProtocolVersion
/*     */ {
/*  42 */   private static final Int2ObjectMap<ProtocolVersion> versions = (Int2ObjectMap<ProtocolVersion>)new Int2ObjectOpenHashMap();
/*  43 */   private static final List<ProtocolVersion> versionList = new ArrayList<>();
/*     */   
/*  45 */   public static final ProtocolVersion v1_4_6 = register(51, "1.4.6/7", new VersionRange("1.4", 6, 7));
/*  46 */   public static final ProtocolVersion v1_5_1 = register(60, "1.5.1");
/*  47 */   public static final ProtocolVersion v1_5_2 = register(61, "1.5.2");
/*  48 */   public static final ProtocolVersion v_1_6_1 = register(73, "1.6.1");
/*  49 */   public static final ProtocolVersion v_1_6_2 = register(74, "1.6.2");
/*  50 */   public static final ProtocolVersion v_1_6_3 = register(77, "1.6.3");
/*  51 */   public static final ProtocolVersion v_1_6_4 = register(78, "1.6.4");
/*  52 */   public static final ProtocolVersion v1_7_1 = register(4, "1.7-1.7.5", new VersionRange("1.7", 0, 5));
/*  53 */   public static final ProtocolVersion v1_7_6 = register(5, "1.7.6-1.7.10", new VersionRange("1.7", 6, 10));
/*  54 */   public static final ProtocolVersion v1_8 = register(47, "1.8.x");
/*  55 */   public static final ProtocolVersion v1_9 = register(107, "1.9");
/*  56 */   public static final ProtocolVersion v1_9_1 = register(108, "1.9.1");
/*  57 */   public static final ProtocolVersion v1_9_2 = register(109, "1.9.2");
/*  58 */   public static final ProtocolVersion v1_9_3 = register(110, "1.9.3/4", new VersionRange("1.9", 3, 4));
/*  59 */   public static final ProtocolVersion v1_10 = register(210, "1.10.x");
/*  60 */   public static final ProtocolVersion v1_11 = register(315, "1.11");
/*  61 */   public static final ProtocolVersion v1_11_1 = register(316, "1.11.1/2", new VersionRange("1.11", 1, 2));
/*  62 */   public static final ProtocolVersion v1_12 = register(335, "1.12");
/*  63 */   public static final ProtocolVersion v1_12_1 = register(338, "1.12.1");
/*  64 */   public static final ProtocolVersion v1_12_2 = register(340, "1.12.2");
/*  65 */   public static final ProtocolVersion v1_13 = register(393, "1.13");
/*  66 */   public static final ProtocolVersion v1_13_1 = register(401, "1.13.1");
/*  67 */   public static final ProtocolVersion v1_13_2 = register(404, "1.13.2");
/*  68 */   public static final ProtocolVersion v1_14 = register(477, "1.14");
/*  69 */   public static final ProtocolVersion v1_14_1 = register(480, "1.14.1");
/*  70 */   public static final ProtocolVersion v1_14_2 = register(485, "1.14.2");
/*  71 */   public static final ProtocolVersion v1_14_3 = register(490, "1.14.3");
/*  72 */   public static final ProtocolVersion v1_14_4 = register(498, "1.14.4");
/*  73 */   public static final ProtocolVersion v1_15 = register(573, "1.15");
/*  74 */   public static final ProtocolVersion v1_15_1 = register(575, "1.15.1");
/*  75 */   public static final ProtocolVersion v1_15_2 = register(578, "1.15.2");
/*  76 */   public static final ProtocolVersion v1_16 = register(735, "1.16");
/*  77 */   public static final ProtocolVersion v1_16_1 = register(736, "1.16.1");
/*  78 */   public static final ProtocolVersion v1_16_2 = register(751, "1.16.2");
/*  79 */   public static final ProtocolVersion v1_16_3 = register(753, "1.16.3");
/*  80 */   public static final ProtocolVersion v1_16_4 = register(754, "1.16.4/5", new VersionRange("1.16", 4, 5));
/*  81 */   public static final ProtocolVersion v1_17 = register(755, "1.17");
/*  82 */   public static final ProtocolVersion v1_17_1 = register(756, "1.17.1");
/*  83 */   public static final ProtocolVersion v1_18 = register(757, "1.18/1.18.1", new VersionRange("1.18", 0, 1));
/*  84 */   public static final ProtocolVersion v1_18_2 = register(758, "1.18.2");
/*  85 */   public static final ProtocolVersion v1_19 = register(759, "1.19");
/*  86 */   public static final ProtocolVersion v1_19_1 = register(760, "1.19.1");
/*  87 */   public static final ProtocolVersion v1_19_3 = register(761, "1.19.3");
/*  88 */   public static final ProtocolVersion v1_19_4 = register(762, "1.19.4");
/*  89 */   public static final ProtocolVersion v1_20 = register(763, "1.20");
/*  90 */   public static final ProtocolVersion unknown = register(-1, "UNKNOWN"); private final int version; private final int snapshotVersion;
/*     */   
/*     */   public static ProtocolVersion register(int version, String name) {
/*  93 */     return register(version, -1, name);
/*     */   }
/*     */   private final String name; private final boolean versionWildcard; private final Set<String> includedVersions;
/*     */   public static ProtocolVersion register(int version, int snapshotVersion, String name) {
/*  97 */     return register(version, snapshotVersion, name, null);
/*     */   }
/*     */   
/*     */   public static ProtocolVersion register(int version, String name, VersionRange versionRange) {
/* 101 */     return register(version, -1, name, versionRange);
/*     */   }
/*     */   
/*     */   public static ProtocolVersion register(int version, int snapshotVersion, String name, VersionRange versionRange) {
/* 105 */     ProtocolVersion protocol = new ProtocolVersion(version, snapshotVersion, name, versionRange);
/* 106 */     versionList.add(protocol);
/* 107 */     versions.put(protocol.getVersion(), protocol);
/* 108 */     if (protocol.isSnapshot()) {
/* 109 */       versions.put(protocol.getFullSnapshotVersion(), protocol);
/*     */     }
/* 111 */     return protocol;
/*     */   }
/*     */   
/*     */   public static boolean isRegistered(int id) {
/* 115 */     return versions.containsKey(id);
/*     */   }
/*     */   
/*     */   public static ProtocolVersion getProtocol(int id) {
/* 119 */     ProtocolVersion protocolVersion = (ProtocolVersion)versions.get(id);
/* 120 */     if (protocolVersion != null) {
/* 121 */       return protocolVersion;
/*     */     }
/* 123 */     return new ProtocolVersion(id, "Unknown (" + id + ")");
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getIndex(ProtocolVersion version) {
/* 128 */     return versionList.indexOf(version);
/*     */   }
/*     */   
/*     */   public static List<ProtocolVersion> getProtocols() {
/* 132 */     return Collections.unmodifiableList(new ArrayList<>((Collection<? extends ProtocolVersion>)versions.values()));
/*     */   }
/*     */   
/*     */   public static ProtocolVersion getClosest(String protocol) {
/* 136 */     for (ObjectIterator<ProtocolVersion> objectIterator = versions.values().iterator(); objectIterator.hasNext(); ) { ProtocolVersion version = objectIterator.next();
/* 137 */       String name = version.getName();
/* 138 */       if (name.equals(protocol)) {
/* 139 */         return version;
/*     */       }
/*     */       
/* 142 */       if (version.isVersionWildcard()) {
/*     */         
/* 144 */         String majorVersion = name.substring(0, name.length() - 2);
/* 145 */         if (majorVersion.equals(protocol) || protocol.startsWith(name.substring(0, name.length() - 1)))
/* 146 */           return version;  continue;
/*     */       } 
/* 148 */       if (version.isRange() && 
/* 149 */         version.getIncludedVersions().contains(protocol)) {
/* 150 */         return version;
/*     */       } }
/*     */ 
/*     */     
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtocolVersion(int version, String name) {
/* 164 */     this(version, -1, name, null);
/*     */   }
/*     */   
/*     */   public ProtocolVersion(int version, int snapshotVersion, String name, VersionRange versionRange) {
/* 168 */     this.version = version;
/* 169 */     this.snapshotVersion = snapshotVersion;
/* 170 */     this.name = name;
/* 171 */     this.versionWildcard = name.endsWith(".x");
/*     */     
/* 173 */     Preconditions.checkArgument((!this.versionWildcard || versionRange == null), "A version cannot be a wildcard and a range at the same time!");
/* 174 */     if (versionRange != null) {
/* 175 */       this.includedVersions = new LinkedHashSet<>();
/* 176 */       for (int i = versionRange.rangeFrom(); i <= versionRange.rangeTo(); i++) {
/* 177 */         if (i == 0) {
/* 178 */           this.includedVersions.add(versionRange.baseVersion());
/*     */         }
/*     */         
/* 181 */         this.includedVersions.add(versionRange.baseVersion() + "." + i);
/*     */       } 
/*     */     } else {
/* 184 */       this.includedVersions = Collections.singleton(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getVersion() {
/* 189 */     return this.version;
/*     */   }
/*     */   
/*     */   public int getSnapshotVersion() {
/* 193 */     Preconditions.checkArgument(isSnapshot());
/* 194 */     return this.snapshotVersion;
/*     */   }
/*     */   
/*     */   public int getFullSnapshotVersion() {
/* 198 */     Preconditions.checkArgument(isSnapshot());
/* 199 */     return 0x40000000 | this.snapshotVersion;
/*     */   }
/*     */   
/*     */   public int getOriginalVersion() {
/* 203 */     return (this.snapshotVersion == -1) ? this.version : (0x40000000 | this.snapshotVersion);
/*     */   }
/*     */   
/*     */   public boolean isKnown() {
/* 207 */     return (this.version != -1);
/*     */   }
/*     */   
/*     */   public boolean isRange() {
/* 211 */     return (this.includedVersions.size() != 1);
/*     */   }
/*     */   
/*     */   public Set<String> getIncludedVersions() {
/* 215 */     return Collections.unmodifiableSet(this.includedVersions);
/*     */   }
/*     */   
/*     */   public boolean isVersionWildcard() {
/* 219 */     return this.versionWildcard;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 223 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isSnapshot() {
/* 227 */     return (this.snapshotVersion != -1);
/*     */   }
/*     */   
/*     */   public int getId() {
/* 231 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 236 */     if (this == o) return true; 
/* 237 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 239 */     ProtocolVersion that = (ProtocolVersion)o;
/* 240 */     return (this.version == that.version);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 245 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 250 */     return String.format("%s (%d)", new Object[] { this.name, Integer.valueOf(this.version) });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar\\us\myles\ViaVersion\api\protocol\ProtocolVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */