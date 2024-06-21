/*     */ package com.viaversion.viaversion.util;
/*     */ 
/*     */ import java.util.function.IntToLongFunction;
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
/*     */ public final class CompactArrayUtil
/*     */ {
/*  28 */   private static final long[] RECIPROCAL_MULT_AND_ADD = new long[] { 4294967295L, 0L, 1431655765L, 0L, 858993459L, 715827882L, 613566756L, 0L, 477218588L, 429496729L, 390451572L, 357913941L, 330382099L, 306783378L, 286331153L, 0L, 252645135L, 238609294L, 226050910L, 214748364L, 204522252L, 195225786L, 186737708L, 178956970L, 171798691L, 165191049L, 159072862L, 153391689L, 148102320L, 143165576L, 138547332L, 0L, 130150524L, 126322567L, 122713351L, 119304647L, 116080197L, 113025455L, 110127366L, 107374182L, 104755299L, 102261126L, 99882960L, 97612893L, 95443717L, 93368854L, 91382282L, 89478485L, 87652393L, 85899345L, 84215045L, 82595524L, 81037118L, 79536431L, 78090314L, 76695844L, 75350303L, 74051160L, 72796055L, 71582788L, 70409299L, 69273666L, 68174084L, 0L };
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
/*  39 */   private static final int[] RECIPROCAL_RIGHT_SHIFT = new int[] { 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5 };
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
/*     */   private CompactArrayUtil() {
/*  51 */     throw new AssertionError();
/*     */   }
/*     */   
/*     */   public static long[] createCompactArrayWithPadding(int bitsPerEntry, int entries, IntToLongFunction valueGetter) {
/*  55 */     long maxEntryValue = (1L << bitsPerEntry) - 1L;
/*  56 */     char valuesPerLong = (char)(64 / bitsPerEntry);
/*  57 */     int magicIndex = valuesPerLong - 1;
/*  58 */     long divideAdd = RECIPROCAL_MULT_AND_ADD[magicIndex];
/*  59 */     long divideMul = (divideAdd != 0L) ? divideAdd : 2147483648L;
/*  60 */     int divideShift = RECIPROCAL_RIGHT_SHIFT[magicIndex];
/*  61 */     int size = (entries + valuesPerLong - 1) / valuesPerLong;
/*     */     
/*  63 */     long[] data = new long[size];
/*     */     
/*  65 */     for (int i = 0; i < entries; i++) {
/*  66 */       long value = valueGetter.applyAsLong(i);
/*  67 */       int cellIndex = (int)(i * divideMul + divideAdd >> 32L >> divideShift);
/*  68 */       int bitIndex = (i - cellIndex * valuesPerLong) * bitsPerEntry;
/*  69 */       data[cellIndex] = data[cellIndex] & (maxEntryValue << bitIndex ^ 0xFFFFFFFFFFFFFFFFL) | (value & maxEntryValue) << bitIndex;
/*     */     } 
/*     */     
/*  72 */     return data;
/*     */   }
/*     */   
/*     */   public static void iterateCompactArrayWithPadding(int bitsPerEntry, int entries, long[] data, BiIntConsumer consumer) {
/*  76 */     long maxEntryValue = (1L << bitsPerEntry) - 1L;
/*  77 */     char valuesPerLong = (char)(64 / bitsPerEntry);
/*  78 */     int magicIndex = valuesPerLong - 1;
/*  79 */     long divideAdd = RECIPROCAL_MULT_AND_ADD[magicIndex];
/*  80 */     long divideMul = (divideAdd != 0L) ? divideAdd : 2147483648L;
/*  81 */     int divideShift = RECIPROCAL_RIGHT_SHIFT[magicIndex];
/*     */     
/*  83 */     for (int i = 0; i < entries; i++) {
/*  84 */       int cellIndex = (int)(i * divideMul + divideAdd >> 32L >> divideShift);
/*  85 */       int bitIndex = (i - cellIndex * valuesPerLong) * bitsPerEntry;
/*  86 */       int value = (int)(data[cellIndex] >> bitIndex & maxEntryValue);
/*  87 */       consumer.consume(i, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static long[] createCompactArray(int bitsPerEntry, int entries, IntToLongFunction valueGetter) {
/*  92 */     long maxEntryValue = (1L << bitsPerEntry) - 1L;
/*  93 */     long[] data = new long[(int)Math.ceil((entries * bitsPerEntry) / 64.0D)];
/*  94 */     for (int i = 0; i < entries; i++) {
/*  95 */       long value = valueGetter.applyAsLong(i);
/*  96 */       int bitIndex = i * bitsPerEntry;
/*  97 */       int startIndex = bitIndex / 64;
/*  98 */       int endIndex = ((i + 1) * bitsPerEntry - 1) / 64;
/*  99 */       int startBitSubIndex = bitIndex % 64;
/* 100 */       data[startIndex] = data[startIndex] & (maxEntryValue << startBitSubIndex ^ 0xFFFFFFFFFFFFFFFFL) | (value & maxEntryValue) << startBitSubIndex;
/* 101 */       if (startIndex != endIndex) {
/* 102 */         int endBitSubIndex = 64 - startBitSubIndex;
/* 103 */         data[endIndex] = data[endIndex] >>> endBitSubIndex << endBitSubIndex | (value & maxEntryValue) >> endBitSubIndex;
/*     */       } 
/*     */     } 
/* 106 */     return data;
/*     */   }
/*     */   
/*     */   public static void iterateCompactArray(int bitsPerEntry, int entries, long[] data, BiIntConsumer consumer) {
/* 110 */     long maxEntryValue = (1L << bitsPerEntry) - 1L;
/* 111 */     for (int i = 0; i < entries; i++) {
/* 112 */       int value, bitIndex = i * bitsPerEntry;
/* 113 */       int startIndex = bitIndex / 64;
/* 114 */       int endIndex = ((i + 1) * bitsPerEntry - 1) / 64;
/* 115 */       int startBitSubIndex = bitIndex % 64;
/*     */       
/* 117 */       if (startIndex == endIndex) {
/* 118 */         value = (int)(data[startIndex] >>> startBitSubIndex & maxEntryValue);
/*     */       } else {
/* 120 */         int endBitSubIndex = 64 - startBitSubIndex;
/* 121 */         value = (int)((data[startIndex] >>> startBitSubIndex | data[endIndex] << endBitSubIndex) & maxEntryValue);
/*     */       } 
/* 123 */       consumer.consume(i, value);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\CompactArrayUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */