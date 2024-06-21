/*     */ package de.florianmichael.viamcp.protocolinfo;
/*     */ 
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public class ProtocolInfo
/*     */ {
/*     */   private static final String NO_DESC = "No Description Available";
/*  28 */   public static ProtocolInfo R1_20 = new ProtocolInfo("Trails & Tales", "No Description Available", "16 May, 2023", ProtocolVersion.v1_20);
/*  29 */   public static ProtocolInfo R1_19_4 = new ProtocolInfo("The Wild Update", "No Description Available", "14 March, 2022", ProtocolVersion.v1_19_4);
/*  30 */   public static ProtocolInfo R1_19_3 = new ProtocolInfo("The Wild Update", "No Description Available", "August 5, 2022 - December 17, 2022", ProtocolVersion.v1_19_3);
/*  31 */   public static ProtocolInfo R1_19_1 = new ProtocolInfo("The Wild Update", "No Description Available", "July 27, 2022 - August 5, 2022", ProtocolVersion.v1_19_1);
/*  32 */   public static ProtocolInfo R1_19 = new ProtocolInfo("The Wild Update", "No Description Available", "June 7, 2022", ProtocolVersion.v1_19);
/*  33 */   public static ProtocolInfo R1_18_2 = new ProtocolInfo("Caves & Cliffs: Part II", "No Description Available", "February 28, 2022", ProtocolVersion.v1_18_2);
/*  34 */   public static ProtocolInfo R1_18 = new ProtocolInfo("Caves & Cliffs: Part II", "No Description Available", "November 30, 2021 - December 10, 2021", ProtocolVersion.v1_18);
/*  35 */   public static ProtocolInfo R1_17_1 = new ProtocolInfo("Caves & Cliffs: Part I", "No Description Available", "July 6, 2021", ProtocolVersion.v1_17_1);
/*  36 */   public static ProtocolInfo R1_17 = new ProtocolInfo("Caves & Cliffs: Part I", "No Description Available", "June 8, 2021", ProtocolVersion.v1_17);
/*  37 */   public static ProtocolInfo R1_16_4 = new ProtocolInfo("Nether Update", "No Description Available", "November 2, 2020 - January 13, 2021", ProtocolVersion.v1_16_4);
/*  38 */   public static ProtocolInfo R1_16_3 = new ProtocolInfo("Nether Update", "No Description Available", "September 7, 2020", ProtocolVersion.v1_16_3);
/*  39 */   public static ProtocolInfo R1_16_2 = new ProtocolInfo("Nether Update", "No Description Available", "August 11, 2020", ProtocolVersion.v1_16_2);
/*  40 */   public static ProtocolInfo R1_16_1 = new ProtocolInfo("Nether Update", "No Description Available", "June 24, 2020", ProtocolVersion.v1_16_1);
/*  41 */   public static ProtocolInfo R1_16 = new ProtocolInfo("Nether Update", "No Description Available", "June 23, 2020", ProtocolVersion.v1_16);
/*  42 */   public static ProtocolInfo R1_15_2 = new ProtocolInfo("Buzzy Bees", "No Description Available", "January 21, 2020", ProtocolVersion.v1_15_2);
/*  43 */   public static ProtocolInfo R1_15_1 = new ProtocolInfo("Buzzy Bees", "No Description Available", "December 17, 2019", ProtocolVersion.v1_15_1);
/*  44 */   public static ProtocolInfo R1_15 = new ProtocolInfo("Buzzy Bees", "No Description Available", "December 10, 2019", ProtocolVersion.v1_15);
/*  45 */   public static ProtocolInfo R1_14_4 = new ProtocolInfo("Village & Pillage", "No Description Available", "July 19, 2019", ProtocolVersion.v1_14_4);
/*  46 */   public static ProtocolInfo R1_14_3 = new ProtocolInfo("Village & Pillage", "No Description Available", "June 24, 2019", ProtocolVersion.v1_14_3);
/*  47 */   public static ProtocolInfo R1_14_2 = new ProtocolInfo("Village & Pillage", "No Description Available", "May 27, 2019", ProtocolVersion.v1_14_2);
/*  48 */   public static ProtocolInfo R1_14_1 = new ProtocolInfo("Village & Pillage", "No Description Available", "May 13, 2019", ProtocolVersion.v1_14_1);
/*  49 */   public static ProtocolInfo R1_14 = new ProtocolInfo("Village & Pillage", "No Description Available", "April 23, 2019", ProtocolVersion.v1_14);
/*  50 */   public static ProtocolInfo R1_13_2 = new ProtocolInfo("Update Aquatic", "No Description Available", "October 22, 2018", ProtocolVersion.v1_13_2);
/*  51 */   public static ProtocolInfo R1_13_1 = new ProtocolInfo("Update Aquatic", "No Description Available", "August 22, 2018", ProtocolVersion.v1_13_1);
/*  52 */   public static ProtocolInfo R1_13 = new ProtocolInfo("Update Aquatic", "No Description Available", "July 18, 2018", ProtocolVersion.v1_13);
/*  53 */   public static ProtocolInfo R1_12_2 = new ProtocolInfo("World of Color Update", "No Description Available", "September 18, 2017", ProtocolVersion.v1_12_2);
/*  54 */   public static ProtocolInfo R1_12_1 = new ProtocolInfo("World of Color Update", "No Description Available", "August 3, 2017", ProtocolVersion.v1_12_1);
/*  55 */   public static ProtocolInfo R1_12 = new ProtocolInfo("World of Color Update", "No Description Available", "June 7, 2017", ProtocolVersion.v1_12);
/*  56 */   public static ProtocolInfo R1_11_1 = new ProtocolInfo("Exploration Update", "No Description Available", "December 20, 2016 - December 21, 2016", ProtocolVersion.v1_11_1);
/*  57 */   public static ProtocolInfo R1_11 = new ProtocolInfo("Exploration Update", "No Description Available", "November 14, 2016", ProtocolVersion.v1_11);
/*  58 */   public static ProtocolInfo R1_10 = new ProtocolInfo("Frostburn Update", "No Description Available", "June 8, 2016 - June 23, 2016", ProtocolVersion.v1_10);
/*  59 */   public static ProtocolInfo R1_9_3 = new ProtocolInfo("Combat Update", "No Description Available", "May 10, 2016", ProtocolVersion.v1_9_3);
/*  60 */   public static ProtocolInfo R1_9_2 = new ProtocolInfo("Combat Update", "No Description Available", "March 30, 2016", ProtocolVersion.v1_9_2);
/*  61 */   public static ProtocolInfo R1_9_1 = new ProtocolInfo("Combat Update", "No Description Available", "March 30, 2016", ProtocolVersion.v1_9_1);
/*  62 */   public static ProtocolInfo R1_9 = new ProtocolInfo("Combat Update", "No Description Available", "February 29, 2016", ProtocolVersion.v1_9);
/*  63 */   public static ProtocolInfo R1_8 = new ProtocolInfo("Bountiful Update", "No Description Available", "September 2, 2014 - December 9, 2015", ProtocolVersion.v1_8);
/*  64 */   public static ProtocolInfo R1_7_6 = new ProtocolInfo("The Update that Changed the World", "No Description Available", "April 9, 2014 - June 26, 2014", ProtocolVersion.v1_7_6);
/*  65 */   public static ProtocolInfo R1_7 = new ProtocolInfo("The Update that Changed the World", "No Description Available", "October 22, 2013 - February 26, 2014", ProtocolVersion.v1_7_1);
/*     */   
/*  67 */   private static final List<ProtocolInfo> PROTOCOL_INFOS = Arrays.asList(new ProtocolInfo[] { R1_7, R1_7_6, R1_8, R1_9, R1_9_1, R1_9_2, R1_9_3, R1_10, R1_11, R1_11_1, R1_12, R1_12_1, R1_12_2, R1_13, R1_13_1, R1_13_2, R1_14, R1_14_1, R1_14_2, R1_14_3, R1_14_4, 
/*  68 */         R1_15, R1_15_1, R1_15_2, R1_16, R1_16_1, R1_16_2, R1_16_3, R1_16_4, R1_17, R1_17_1, R1_18, R1_18_2, R1_19, R1_19_1, R1_19_3, R1_19_4, R1_20 });
/*     */   
/*     */   private final String name;
/*     */   private final String description;
/*     */   private final String releaseDate;
/*     */   private final ProtocolVersion protocolVersion;
/*     */   
/*     */   public ProtocolInfo(String name, String description, String releaseDate, ProtocolVersion protocolVersion) {
/*  76 */     this.name = name;
/*  77 */     this.description = description;
/*  78 */     this.releaseDate = releaseDate;
/*  79 */     this.protocolVersion = protocolVersion;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  83 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  87 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getReleaseDate() {
/*  91 */     return this.releaseDate;
/*     */   }
/*     */   
/*     */   public ProtocolVersion getProtocolVersion() {
/*  95 */     return this.protocolVersion;
/*     */   }
/*     */   
/*     */   public static ProtocolInfo fromProtocolVersion(ProtocolVersion protocolVersion) {
/*  99 */     for (ProtocolInfo protocolInfo : PROTOCOL_INFOS) {
/* 100 */       if (protocolInfo.getProtocolVersion().getName().equals(protocolVersion.getName())) {
/* 101 */         return protocolInfo;
/*     */       }
/*     */     } 
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\viamcp\protocolinfo\ProtocolInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */