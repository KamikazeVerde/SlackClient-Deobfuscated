/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
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
/*     */ public class EntityTypeRewriter
/*     */ {
/*  24 */   private static final Int2IntMap ENTITY_TYPES = (Int2IntMap)new Int2IntOpenHashMap(83, 0.99F);
/*     */   
/*     */   static {
/*  27 */     ENTITY_TYPES.defaultReturnValue(-1);
/*  28 */     registerEntity(1, 32);
/*  29 */     registerEntity(2, 22);
/*  30 */     registerEntity(3, 0);
/*  31 */     registerEntity(4, 15);
/*  32 */     registerEntity(5, 84);
/*  33 */     registerEntity(6, 71);
/*  34 */     registerEntity(7, 74);
/*  35 */     registerEntity(8, 35);
/*  36 */     registerEntity(9, 49);
/*  37 */     registerEntity(10, 2);
/*  38 */     registerEntity(11, 67);
/*  39 */     registerEntity(12, 34);
/*  40 */     registerEntity(13, 65);
/*  41 */     registerEntity(14, 75);
/*  42 */     registerEntity(15, 23);
/*  43 */     registerEntity(16, 77);
/*  44 */     registerEntity(17, 76);
/*  45 */     registerEntity(18, 33);
/*  46 */     registerEntity(19, 85);
/*  47 */     registerEntity(20, 55);
/*  48 */     registerEntity(21, 24);
/*  49 */     registerEntity(22, 25);
/*  50 */     registerEntity(23, 30);
/*  51 */     registerEntity(24, 68);
/*  52 */     registerEntity(25, 60);
/*  53 */     registerEntity(26, 13);
/*  54 */     registerEntity(27, 89);
/*  55 */     registerEntity(28, 63);
/*  56 */     registerEntity(29, 88);
/*  57 */     registerEntity(30, 1);
/*  58 */     registerEntity(31, 11);
/*  59 */     registerEntity(32, 46);
/*  60 */     registerEntity(33, 20);
/*  61 */     registerEntity(34, 21);
/*  62 */     registerEntity(35, 78);
/*  63 */     registerEntity(36, 81);
/*  64 */     registerEntity(37, 31);
/*  65 */     registerEntity(40, 41);
/*  66 */     registerEntity(41, 5);
/*  67 */     registerEntity(42, 39);
/*  68 */     registerEntity(43, 40);
/*  69 */     registerEntity(44, 42);
/*  70 */     registerEntity(45, 45);
/*  71 */     registerEntity(46, 43);
/*  72 */     registerEntity(47, 44);
/*  73 */     registerEntity(50, 10);
/*  74 */     registerEntity(51, 62);
/*  75 */     registerEntity(52, 69);
/*  76 */     registerEntity(53, 27);
/*  77 */     registerEntity(54, 87);
/*  78 */     registerEntity(55, 64);
/*  79 */     registerEntity(56, 26);
/*  80 */     registerEntity(57, 53);
/*  81 */     registerEntity(58, 18);
/*  82 */     registerEntity(59, 6);
/*  83 */     registerEntity(60, 61);
/*  84 */     registerEntity(61, 4);
/*  85 */     registerEntity(62, 38);
/*  86 */     registerEntity(63, 17);
/*  87 */     registerEntity(64, 83);
/*  88 */     registerEntity(65, 3);
/*  89 */     registerEntity(66, 82);
/*  90 */     registerEntity(67, 19);
/*  91 */     registerEntity(68, 28);
/*  92 */     registerEntity(69, 59);
/*  93 */     registerEntity(200, 16);
/*  94 */     registerEntity(90, 51);
/*  95 */     registerEntity(91, 58);
/*  96 */     registerEntity(92, 9);
/*  97 */     registerEntity(93, 7);
/*  98 */     registerEntity(94, 70);
/*  99 */     registerEntity(95, 86);
/* 100 */     registerEntity(96, 47);
/* 101 */     registerEntity(97, 66);
/* 102 */     registerEntity(98, 48);
/* 103 */     registerEntity(99, 80);
/* 104 */     registerEntity(100, 29);
/* 105 */     registerEntity(101, 56);
/* 106 */     registerEntity(102, 54);
/* 107 */     registerEntity(103, 36);
/* 108 */     registerEntity(104, 37);
/* 109 */     registerEntity(105, 50);
/* 110 */     registerEntity(120, 79);
/*     */   }
/*     */   
/*     */   private static void registerEntity(int type1_12, int type1_13) {
/* 114 */     ENTITY_TYPES.put(type1_12, type1_13);
/*     */   }
/*     */   
/*     */   public static int getNewId(int type1_12) {
/* 118 */     return ENTITY_TYPES.getOrDefault(type1_12, type1_12);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\EntityTypeRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */