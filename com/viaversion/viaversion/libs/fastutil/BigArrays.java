/*      */ package com.viaversion.viaversion.libs.fastutil;
/*      */ 
/*      */ import com.viaversion.viaversion.libs.fastutil.booleans.BooleanArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.booleans.BooleanBigArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.bytes.ByteArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.bytes.ByteBigArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.chars.CharArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.chars.CharBigArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.doubles.DoubleArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.doubles.DoubleBigArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.floats.FloatArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.floats.FloatBigArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.ints.IntBigArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.longs.LongArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.longs.LongBigArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.longs.LongComparator;
/*      */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectBigArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.shorts.ShortArrays;
/*      */ import com.viaversion.viaversion.libs.fastutil.shorts.ShortBigArrays;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.Objects;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*      */ import java.util.concurrent.atomic.AtomicLongArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigArrays
/*      */ {
/*      */   public static final int SEGMENT_SHIFT = 27;
/*      */   public static final int SEGMENT_SIZE = 134217728;
/*      */   public static final int SEGMENT_MASK = 134217727;
/*      */   private static final int SMALL = 7;
/*      */   private static final int MEDIUM = 40;
/*      */   
/*      */   public static int segment(long index) {
/*  217 */     return (int)(index >>> 27L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int displacement(long index) {
/*  227 */     return (int)(index & 0x7FFFFFFL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long start(int segment) {
/*  237 */     return segment << 27L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long nearestSegmentStart(long index, long min, long max) {
/*  265 */     long lower = start(segment(index));
/*  266 */     long upper = start(segment(index) + 1);
/*  267 */     if (upper >= max) {
/*  268 */       if (lower < min) {
/*  269 */         return index;
/*      */       }
/*  271 */       return lower;
/*      */     } 
/*  273 */     if (lower < min) return upper;
/*      */     
/*  275 */     long mid = lower + (upper - lower >> 1L);
/*  276 */     return (index <= mid) ? lower : upper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long index(int segment, int displacement) {
/*  289 */     return start(segment) + displacement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(long bigArrayLength, long from, long to) {
/*  309 */     assert bigArrayLength >= 0L;
/*  310 */     if (from < 0L) throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative"); 
/*  311 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  312 */     if (to > bigArrayLength) throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than big-array length (" + bigArrayLength + ")");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(long bigArrayLength, long offset, long length) {
/*  331 */     assert bigArrayLength >= 0L;
/*  332 */     if (offset < 0L) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  333 */     if (length < 0L) throw new IllegalArgumentException("Length (" + length + ") is negative"); 
/*  334 */     if (length > bigArrayLength - offset) throw new ArrayIndexOutOfBoundsException("Last index (" + Long.toUnsignedString(offset + length) + ") is greater than big-array length (" + bigArrayLength + ")");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureLength(long bigArrayLength) {
/*  345 */     if (bigArrayLength < 0L) throw new IllegalArgumentException("Negative big-array size: " + bigArrayLength); 
/*  346 */     if (bigArrayLength >= 288230376017494016L) throw new IllegalArgumentException("Big-array size too big: " + bigArrayLength);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void inPlaceMerge(long from, long mid, long to, LongComparator comp, BigSwapper swapper) {
/*      */     // Byte code:
/*      */     //   0: lload_0
/*      */     //   1: lload_2
/*      */     //   2: lcmp
/*      */     //   3: ifge -> 13
/*      */     //   6: lload_2
/*      */     //   7: lload #4
/*      */     //   9: lcmp
/*      */     //   10: iflt -> 14
/*      */     //   13: return
/*      */     //   14: lload #4
/*      */     //   16: lload_0
/*      */     //   17: lsub
/*      */     //   18: ldc2_w 2
/*      */     //   21: lcmp
/*      */     //   22: ifne -> 47
/*      */     //   25: aload #6
/*      */     //   27: lload_2
/*      */     //   28: lload_0
/*      */     //   29: invokeinterface compare : (JJ)I
/*      */     //   34: ifge -> 46
/*      */     //   37: aload #7
/*      */     //   39: lload_0
/*      */     //   40: lload_2
/*      */     //   41: invokeinterface swap : (JJ)V
/*      */     //   46: return
/*      */     //   47: lload_2
/*      */     //   48: lload_0
/*      */     //   49: lsub
/*      */     //   50: lload #4
/*      */     //   52: lload_2
/*      */     //   53: lsub
/*      */     //   54: lcmp
/*      */     //   55: ifle -> 84
/*      */     //   58: lload_0
/*      */     //   59: lload_2
/*      */     //   60: lload_0
/*      */     //   61: lsub
/*      */     //   62: ldc2_w 2
/*      */     //   65: ldiv
/*      */     //   66: ladd
/*      */     //   67: lstore #8
/*      */     //   69: lload_2
/*      */     //   70: lload #4
/*      */     //   72: lload #8
/*      */     //   74: aload #6
/*      */     //   76: invokestatic lowerBound : (JJJLcom/viaversion/viaversion/libs/fastutil/longs/LongComparator;)J
/*      */     //   79: lstore #10
/*      */     //   81: goto -> 107
/*      */     //   84: lload_2
/*      */     //   85: lload #4
/*      */     //   87: lload_2
/*      */     //   88: lsub
/*      */     //   89: ldc2_w 2
/*      */     //   92: ldiv
/*      */     //   93: ladd
/*      */     //   94: lstore #10
/*      */     //   96: lload_0
/*      */     //   97: lload_2
/*      */     //   98: lload #10
/*      */     //   100: aload #6
/*      */     //   102: invokestatic upperBound : (JJJLcom/viaversion/viaversion/libs/fastutil/longs/LongComparator;)J
/*      */     //   105: lstore #8
/*      */     //   107: lload #8
/*      */     //   109: lstore #12
/*      */     //   111: lload_2
/*      */     //   112: lstore #14
/*      */     //   114: lload #10
/*      */     //   116: lstore #16
/*      */     //   118: lload #14
/*      */     //   120: lload #12
/*      */     //   122: lcmp
/*      */     //   123: ifeq -> 254
/*      */     //   126: lload #14
/*      */     //   128: lload #16
/*      */     //   130: lcmp
/*      */     //   131: ifeq -> 254
/*      */     //   134: lload #12
/*      */     //   136: lstore #18
/*      */     //   138: lload #14
/*      */     //   140: lstore #20
/*      */     //   142: lload #18
/*      */     //   144: lload #20
/*      */     //   146: lconst_1
/*      */     //   147: lsub
/*      */     //   148: dup2
/*      */     //   149: lstore #20
/*      */     //   151: lcmp
/*      */     //   152: ifge -> 174
/*      */     //   155: aload #7
/*      */     //   157: lload #18
/*      */     //   159: dup2
/*      */     //   160: lconst_1
/*      */     //   161: ladd
/*      */     //   162: lstore #18
/*      */     //   164: lload #20
/*      */     //   166: invokeinterface swap : (JJ)V
/*      */     //   171: goto -> 142
/*      */     //   174: lload #14
/*      */     //   176: lstore #18
/*      */     //   178: lload #16
/*      */     //   180: lstore #20
/*      */     //   182: lload #18
/*      */     //   184: lload #20
/*      */     //   186: lconst_1
/*      */     //   187: lsub
/*      */     //   188: dup2
/*      */     //   189: lstore #20
/*      */     //   191: lcmp
/*      */     //   192: ifge -> 214
/*      */     //   195: aload #7
/*      */     //   197: lload #18
/*      */     //   199: dup2
/*      */     //   200: lconst_1
/*      */     //   201: ladd
/*      */     //   202: lstore #18
/*      */     //   204: lload #20
/*      */     //   206: invokeinterface swap : (JJ)V
/*      */     //   211: goto -> 182
/*      */     //   214: lload #12
/*      */     //   216: lstore #18
/*      */     //   218: lload #16
/*      */     //   220: lstore #20
/*      */     //   222: lload #18
/*      */     //   224: lload #20
/*      */     //   226: lconst_1
/*      */     //   227: lsub
/*      */     //   228: dup2
/*      */     //   229: lstore #20
/*      */     //   231: lcmp
/*      */     //   232: ifge -> 254
/*      */     //   235: aload #7
/*      */     //   237: lload #18
/*      */     //   239: dup2
/*      */     //   240: lconst_1
/*      */     //   241: ladd
/*      */     //   242: lstore #18
/*      */     //   244: lload #20
/*      */     //   246: invokeinterface swap : (JJ)V
/*      */     //   251: goto -> 222
/*      */     //   254: lload #8
/*      */     //   256: lload #10
/*      */     //   258: lload_2
/*      */     //   259: lsub
/*      */     //   260: ladd
/*      */     //   261: lstore_2
/*      */     //   262: lload_0
/*      */     //   263: lload #8
/*      */     //   265: lload_2
/*      */     //   266: aload #6
/*      */     //   268: aload #7
/*      */     //   270: invokestatic inPlaceMerge : (JJJLcom/viaversion/viaversion/libs/fastutil/longs/LongComparator;Lcom/viaversion/viaversion/libs/fastutil/BigSwapper;)V
/*      */     //   273: lload_2
/*      */     //   274: lload #10
/*      */     //   276: lload #4
/*      */     //   278: aload #6
/*      */     //   280: aload #7
/*      */     //   282: invokestatic inPlaceMerge : (JJJLcom/viaversion/viaversion/libs/fastutil/longs/LongComparator;Lcom/viaversion/viaversion/libs/fastutil/BigSwapper;)V
/*      */     //   285: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #359	-> 0
/*      */     //   #360	-> 14
/*      */     //   #361	-> 25
/*      */     //   #362	-> 37
/*      */     //   #364	-> 46
/*      */     //   #368	-> 47
/*      */     //   #369	-> 58
/*      */     //   #370	-> 69
/*      */     //   #372	-> 84
/*      */     //   #373	-> 96
/*      */     //   #375	-> 107
/*      */     //   #376	-> 111
/*      */     //   #377	-> 114
/*      */     //   #378	-> 118
/*      */     //   #379	-> 134
/*      */     //   #380	-> 138
/*      */     //   #381	-> 142
/*      */     //   #382	-> 174
/*      */     //   #383	-> 178
/*      */     //   #384	-> 182
/*      */     //   #385	-> 214
/*      */     //   #386	-> 218
/*      */     //   #387	-> 222
/*      */     //   #389	-> 254
/*      */     //   #390	-> 262
/*      */     //   #391	-> 273
/*      */     //   #392	-> 285
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   69	15	8	firstCut	J
/*      */     //   81	3	10	secondCut	J
/*      */     //   138	116	18	first1	J
/*      */     //   142	112	20	last1	J
/*      */     //   0	286	0	from	J
/*      */     //   0	286	2	mid	J
/*      */     //   0	286	4	to	J
/*      */     //   0	286	6	comp	Lcom/viaversion/viaversion/libs/fastutil/longs/LongComparator;
/*      */     //   0	286	7	swapper	Lcom/viaversion/viaversion/libs/fastutil/BigSwapper;
/*      */     //   107	179	8	firstCut	J
/*      */     //   96	190	10	secondCut	J
/*      */     //   111	175	12	first2	J
/*      */     //   114	172	14	middle2	J
/*      */     //   118	168	16	last2	J
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long lowerBound(long mid, long to, long firstCut, LongComparator comp) {
/*  407 */     long len = to - mid;
/*  408 */     while (len > 0L) {
/*  409 */       long half = len / 2L;
/*  410 */       long middle = mid + half;
/*  411 */       if (comp.compare(middle, firstCut) < 0) {
/*  412 */         mid = middle + 1L;
/*  413 */         len -= half + 1L; continue;
/*      */       } 
/*  415 */       len = half;
/*      */     } 
/*      */     
/*  418 */     return mid;
/*      */   }
/*      */ 
/*      */   
/*      */   private static long med3(long a, long b, long c, LongComparator comp) {
/*  423 */     int ab = comp.compare(a, b);
/*  424 */     int ac = comp.compare(a, c);
/*  425 */     int bc = comp.compare(b, c);
/*  426 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(long from, long to, LongComparator comp, BigSwapper swapper) {
/*  446 */     long length = to - from;
/*      */     
/*  448 */     if (length < 7L) {
/*  449 */       long i; for (i = from; i < to; i++) {
/*  450 */         long j; for (j = i; j > from && comp.compare(j - 1L, j) > 0; j--) {
/*  451 */           swapper.swap(j, j - 1L);
/*      */         }
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  457 */     long mid = from + to >>> 1L;
/*  458 */     mergeSort(from, mid, comp, swapper);
/*  459 */     mergeSort(mid, to, comp, swapper);
/*      */ 
/*      */     
/*  462 */     if (comp.compare(mid - 1L, mid) <= 0)
/*      */       return; 
/*  464 */     inPlaceMerge(from, mid, to, comp, swapper);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(long from, long to, LongComparator comp, BigSwapper swapper) {
/*  482 */     long len = to - from;
/*      */     
/*  484 */     if (len < 7L) {
/*  485 */       long i; for (i = from; i < to; ) { long j; for (j = i; j > from && comp.compare(j - 1L, j) > 0; j--)
/*  486 */           swapper.swap(j, j - 1L); 
/*      */         i++; }
/*      */       
/*      */       return;
/*      */     } 
/*  491 */     long m = from + len / 2L;
/*  492 */     if (len > 7L) {
/*  493 */       long l = from, n = to - 1L;
/*  494 */       if (len > 40L) {
/*  495 */         long s = len / 8L;
/*  496 */         l = med3(l, l + s, l + 2L * s, comp);
/*  497 */         m = med3(m - s, m, m + s, comp);
/*  498 */         n = med3(n - 2L * s, n - s, n, comp);
/*      */       } 
/*  500 */       m = med3(l, m, n, comp);
/*      */     } 
/*      */     
/*  503 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*      */     
/*  507 */     while (b <= c && (comparison = comp.compare(b, m)) <= 0) {
/*  508 */       if (comparison == 0) {
/*  509 */         if (a == m) { m = b; }
/*  510 */         else if (b == m) { m = a; }
/*  511 */          swapper.swap(a++, b);
/*      */       } 
/*  513 */       b++;
/*      */     } 
/*  515 */     while (c >= b && (comparison = comp.compare(c, m)) >= 0) {
/*  516 */       if (comparison == 0) {
/*  517 */         if (c == m) { m = d; }
/*  518 */         else if (d == m) { m = c; }
/*  519 */          swapper.swap(c, d--);
/*      */       } 
/*  521 */       c--;
/*      */     } 
/*  523 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  530 */       long n = from + len;
/*  531 */       long s = Math.min(a - from, b - a);
/*  532 */       vecSwap(swapper, from, b - s, s);
/*  533 */       s = Math.min(d - c, n - d - 1L);
/*  534 */       vecSwap(swapper, b, n - s, s);
/*      */       
/*  536 */       if ((s = b - a) > 1L) quickSort(from, from + s, comp, swapper); 
/*  537 */       if ((s = d - c) > 1L) quickSort(n - s, n, comp, swapper);
/*      */       
/*      */       return;
/*      */     } 
/*      */     if (b == m) {
/*      */       m = d;
/*      */     } else if (c == m) {
/*      */       m = c;
/*      */     } 
/*      */     swapper.swap(b++, c--);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static long upperBound(long from, long mid, long secondCut, LongComparator comp) {
/*  552 */     long len = mid - from;
/*  553 */     while (len > 0L) {
/*  554 */       long half = len / 2L;
/*  555 */       long middle = from + half;
/*  556 */       if (comp.compare(secondCut, middle) < 0) {
/*  557 */         len = half; continue;
/*      */       } 
/*  559 */       from = middle + 1L;
/*  560 */       len -= half + 1L;
/*      */     } 
/*      */     
/*  563 */     return from;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void vecSwap(BigSwapper swapper, long from, long l, long s) {
/*  568 */     for (int i = 0; i < s; ) { swapper.swap(from, l); i++; from++; l++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte get(byte[][] array, long index) {
/*  619 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(byte[][] array, long index, byte value) {
/*  630 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(byte[][] array, long first, long second) {
/*  641 */     byte t = array[segment(first)][displacement(first)];
/*  642 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/*  643 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] reverse(byte[][] a) {
/*  653 */     long length = length(a);
/*  654 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/*  655 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(byte[][] array, long index, byte incr) {
/*  666 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(byte[][] array, long index, byte factor) {
/*  677 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(byte[][] array, long index) {
/*  687 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(byte[][] array, long index) {
/*  697 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(byte[][] array) {
/*  707 */     int length = array.length;
/*  708 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(byte[][] srcArray, long srcPos, byte[][] destArray, long destPos, long length) {
/*  723 */     if (destPos <= srcPos) {
/*  724 */       int srcSegment = segment(srcPos);
/*  725 */       int destSegment = segment(destPos);
/*  726 */       int srcDispl = displacement(srcPos);
/*  727 */       int destDispl = displacement(destPos);
/*  728 */       while (length > 0L) {
/*  729 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/*  730 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/*  731 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/*  732 */         if ((srcDispl += l) == 134217728) {
/*  733 */           srcDispl = 0;
/*  734 */           srcSegment++;
/*      */         } 
/*  736 */         if ((destDispl += l) == 134217728) {
/*  737 */           destDispl = 0;
/*  738 */           destSegment++;
/*      */         } 
/*  740 */         length -= l;
/*      */       } 
/*      */     } else {
/*  743 */       int srcSegment = segment(srcPos + length);
/*  744 */       int destSegment = segment(destPos + length);
/*  745 */       int srcDispl = displacement(srcPos + length);
/*  746 */       int destDispl = displacement(destPos + length);
/*  747 */       while (length > 0L) {
/*  748 */         if (srcDispl == 0) {
/*  749 */           srcDispl = 134217728;
/*  750 */           srcSegment--;
/*      */         } 
/*  752 */         if (destDispl == 0) {
/*  753 */           destDispl = 134217728;
/*  754 */           destSegment--;
/*      */         } 
/*  756 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/*  757 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/*  758 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/*  759 */         srcDispl -= l;
/*  760 */         destDispl -= l;
/*  761 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyFromBig(byte[][] srcArray, long srcPos, byte[] destArray, int destPos, int length) {
/*  777 */     int srcSegment = segment(srcPos);
/*  778 */     int srcDispl = displacement(srcPos);
/*  779 */     while (length > 0) {
/*  780 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/*  781 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/*  782 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/*  783 */       if ((srcDispl += l) == 134217728) {
/*  784 */         srcDispl = 0;
/*  785 */         srcSegment++;
/*      */       } 
/*  787 */       destPos += l;
/*  788 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyToBig(byte[] srcArray, int srcPos, byte[][] destArray, long destPos, long length) {
/*  803 */     int destSegment = segment(destPos);
/*  804 */     int destDispl = displacement(destPos);
/*  805 */     while (length > 0L) {
/*  806 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/*  807 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/*  808 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/*  809 */       if ((destDispl += l) == 134217728) {
/*  810 */         destDispl = 0;
/*  811 */         destSegment++;
/*      */       } 
/*  813 */       srcPos += l;
/*  814 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] wrap(byte[] array) {
/*  828 */     if (array.length == 0) return ByteBigArrays.EMPTY_BIG_ARRAY; 
/*  829 */     if (array.length <= 134217728) return new byte[][] { array }; 
/*  830 */     byte[][] bigArray = ByteBigArrays.newBigArray(array.length);
/*  831 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/*  832 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length) {
/*  853 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] forceCapacity(byte[][] array, long length, long preserve) {
/*  872 */     ensureLength(length);
/*  873 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  874 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  875 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  876 */     int residual = (int)(length & 0x7FFFFFFL);
/*  877 */     if (residual != 0)
/*  878 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new byte[134217728]; i++; }
/*  879 */        base[baseLength - 1] = new byte[residual]; }
/*  880 */     else { for (int i = valid; i < baseLength; ) { base[i] = new byte[134217728]; i++; }  }
/*  881 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/*  882 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length, long preserve) {
/*  902 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] grow(byte[][] array, long length) {
/*  924 */     long oldLength = length(array);
/*  925 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] grow(byte[][] array, long length, long preserve) {
/*  950 */     long oldLength = length(array);
/*  951 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] trim(byte[][] array, long length) {
/*  969 */     ensureLength(length);
/*  970 */     long oldLength = length(array);
/*  971 */     if (length >= oldLength) return array; 
/*  972 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  973 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  974 */     int residual = (int)(length & 0x7FFFFFFL);
/*  975 */     if (residual != 0) base[baseLength - 1] = ByteArrays.trim(base[baseLength - 1], residual); 
/*  976 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] setLength(byte[][] array, long length) {
/*  996 */     long oldLength = length(array);
/*  997 */     if (length == oldLength) return array; 
/*  998 */     if (length < oldLength) return trim(array, length); 
/*  999 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] copy(byte[][] array, long offset, long length) {
/* 1012 */     ensureOffsetLength(array, offset, length);
/* 1013 */     byte[][] a = ByteBigArrays.newBigArray(length);
/* 1014 */     copy(array, offset, a, 0L, length);
/* 1015 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] copy(byte[][] array) {
/* 1025 */     byte[][] base = (byte[][])array.clone();
/* 1026 */     for (int i = base.length; i-- != 0; base[i] = (byte[])array[i].clone());
/* 1027 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(byte[][] array, byte value) {
/* 1041 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(byte[][] array, long from, long to, byte value) {
/* 1057 */     long length = length(array);
/* 1058 */     ensureFromTo(length, from, to);
/* 1059 */     if (length == 0L)
/* 1060 */       return;  int fromSegment = segment(from);
/* 1061 */     int toSegment = segment(to);
/* 1062 */     int fromDispl = displacement(from);
/* 1063 */     int toDispl = displacement(to);
/* 1064 */     if (fromSegment == toSegment) {
/* 1065 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 1068 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 1069 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 1070 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(byte[][] a1, byte[][] a2) {
/* 1085 */     if (length(a1) != length(a2)) return false; 
/* 1086 */     int i = a1.length;
/*      */     
/* 1088 */     while (i-- != 0) {
/* 1089 */       byte[] t = a1[i];
/* 1090 */       byte[] u = a2[i];
/* 1091 */       int j = t.length;
/* 1092 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 1094 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(byte[][] a) {
/* 1104 */     if (a == null) return "null"; 
/* 1105 */     long last = length(a) - 1L;
/* 1106 */     if (last == -1L) return "[]"; 
/* 1107 */     StringBuilder b = new StringBuilder();
/* 1108 */     b.append('['); long i;
/* 1109 */     for (i = 0L;; i++) {
/* 1110 */       b.append(String.valueOf(get(a, i)));
/* 1111 */       if (i == last) return b.append(']').toString(); 
/* 1112 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(byte[][] a, long from, long to) {
/* 1131 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(byte[][] a, long offset, long length) {
/* 1148 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(byte[][] a, byte[][] b) {
/* 1159 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] shuffle(byte[][] a, long from, long to, Random random) {
/* 1172 */     for (long i = to - from; i-- != 0L; ) {
/* 1173 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1174 */       byte t = get(a, from + i);
/* 1175 */       set(a, from + i, get(a, from + p));
/* 1176 */       set(a, from + p, t);
/*      */     } 
/* 1178 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] shuffle(byte[][] a, Random random) {
/* 1189 */     for (long i = length(a); i-- != 0L; ) {
/* 1190 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1191 */       byte t = get(a, i);
/* 1192 */       set(a, i, get(a, p));
/* 1193 */       set(a, p, t);
/*      */     } 
/* 1195 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int get(int[][] array, long index) {
/* 1246 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(int[][] array, long index, int value) {
/* 1257 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(AtomicIntegerArray[] array) {
/* 1267 */     int length = array.length;
/* 1268 */     return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int get(AtomicIntegerArray[] array, long index) {
/* 1279 */     return array[segment(index)].get(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(AtomicIntegerArray[] array, long index, int value) {
/* 1290 */     array[segment(index)].set(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAndSet(AtomicIntegerArray[] array, long index, int value) {
/* 1303 */     return array[segment(index)].getAndSet(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAndAdd(AtomicIntegerArray[] array, long index, int value) {
/* 1315 */     return array[segment(index)].getAndAdd(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int addAndGet(AtomicIntegerArray[] array, long index, int value) {
/* 1327 */     return array[segment(index)].addAndGet(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAndIncrement(AtomicIntegerArray[] array, long index) {
/* 1338 */     return array[segment(index)].getAndDecrement(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int incrementAndGet(AtomicIntegerArray[] array, long index) {
/* 1349 */     return array[segment(index)].incrementAndGet(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAndDecrement(AtomicIntegerArray[] array, long index) {
/* 1360 */     return array[segment(index)].getAndDecrement(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int decrementAndGet(AtomicIntegerArray[] array, long index) {
/* 1371 */     return array[segment(index)].decrementAndGet(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compareAndSet(AtomicIntegerArray[] array, long index, int expected, int value) {
/* 1386 */     return array[segment(index)].compareAndSet(displacement(index), expected, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[][] array, long first, long second) {
/* 1397 */     int t = array[segment(first)][displacement(first)];
/* 1398 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 1399 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] reverse(int[][] a) {
/* 1409 */     long length = length(a);
/* 1410 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 1411 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(int[][] array, long index, int incr) {
/* 1422 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(int[][] array, long index, int factor) {
/* 1433 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(int[][] array, long index) {
/* 1443 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(int[][] array, long index) {
/* 1453 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(int[][] array) {
/* 1463 */     int length = array.length;
/* 1464 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(int[][] srcArray, long srcPos, int[][] destArray, long destPos, long length) {
/* 1479 */     if (destPos <= srcPos) {
/* 1480 */       int srcSegment = segment(srcPos);
/* 1481 */       int destSegment = segment(destPos);
/* 1482 */       int srcDispl = displacement(srcPos);
/* 1483 */       int destDispl = displacement(destPos);
/* 1484 */       while (length > 0L) {
/* 1485 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 1486 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 1487 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 1488 */         if ((srcDispl += l) == 134217728) {
/* 1489 */           srcDispl = 0;
/* 1490 */           srcSegment++;
/*      */         } 
/* 1492 */         if ((destDispl += l) == 134217728) {
/* 1493 */           destDispl = 0;
/* 1494 */           destSegment++;
/*      */         } 
/* 1496 */         length -= l;
/*      */       } 
/*      */     } else {
/* 1499 */       int srcSegment = segment(srcPos + length);
/* 1500 */       int destSegment = segment(destPos + length);
/* 1501 */       int srcDispl = displacement(srcPos + length);
/* 1502 */       int destDispl = displacement(destPos + length);
/* 1503 */       while (length > 0L) {
/* 1504 */         if (srcDispl == 0) {
/* 1505 */           srcDispl = 134217728;
/* 1506 */           srcSegment--;
/*      */         } 
/* 1508 */         if (destDispl == 0) {
/* 1509 */           destDispl = 134217728;
/* 1510 */           destSegment--;
/*      */         } 
/* 1512 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 1513 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 1514 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 1515 */         srcDispl -= l;
/* 1516 */         destDispl -= l;
/* 1517 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyFromBig(int[][] srcArray, long srcPos, int[] destArray, int destPos, int length) {
/* 1533 */     int srcSegment = segment(srcPos);
/* 1534 */     int srcDispl = displacement(srcPos);
/* 1535 */     while (length > 0) {
/* 1536 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 1537 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 1538 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 1539 */       if ((srcDispl += l) == 134217728) {
/* 1540 */         srcDispl = 0;
/* 1541 */         srcSegment++;
/*      */       } 
/* 1543 */       destPos += l;
/* 1544 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyToBig(int[] srcArray, int srcPos, int[][] destArray, long destPos, long length) {
/* 1559 */     int destSegment = segment(destPos);
/* 1560 */     int destDispl = displacement(destPos);
/* 1561 */     while (length > 0L) {
/* 1562 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 1563 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 1564 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 1565 */       if ((destDispl += l) == 134217728) {
/* 1566 */         destDispl = 0;
/* 1567 */         destSegment++;
/*      */       } 
/* 1569 */       srcPos += l;
/* 1570 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] wrap(int[] array) {
/* 1584 */     if (array.length == 0) return IntBigArrays.EMPTY_BIG_ARRAY; 
/* 1585 */     if (array.length <= 134217728) return new int[][] { array }; 
/* 1586 */     int[][] bigArray = IntBigArrays.newBigArray(array.length);
/* 1587 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 1588 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] ensureCapacity(int[][] array, long length) {
/* 1609 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] forceCapacity(int[][] array, long length, long preserve) {
/* 1628 */     ensureLength(length);
/* 1629 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 1630 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 1631 */     int[][] base = Arrays.<int[]>copyOf(array, baseLength);
/* 1632 */     int residual = (int)(length & 0x7FFFFFFL);
/* 1633 */     if (residual != 0)
/* 1634 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new int[134217728]; i++; }
/* 1635 */        base[baseLength - 1] = new int[residual]; }
/* 1636 */     else { for (int i = valid; i < baseLength; ) { base[i] = new int[134217728]; i++; }  }
/* 1637 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 1638 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] ensureCapacity(int[][] array, long length, long preserve) {
/* 1658 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] grow(int[][] array, long length) {
/* 1680 */     long oldLength = length(array);
/* 1681 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] grow(int[][] array, long length, long preserve) {
/* 1706 */     long oldLength = length(array);
/* 1707 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] trim(int[][] array, long length) {
/* 1725 */     ensureLength(length);
/* 1726 */     long oldLength = length(array);
/* 1727 */     if (length >= oldLength) return array; 
/* 1728 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 1729 */     int[][] base = Arrays.<int[]>copyOf(array, baseLength);
/* 1730 */     int residual = (int)(length & 0x7FFFFFFL);
/* 1731 */     if (residual != 0) base[baseLength - 1] = IntArrays.trim(base[baseLength - 1], residual); 
/* 1732 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] setLength(int[][] array, long length) {
/* 1752 */     long oldLength = length(array);
/* 1753 */     if (length == oldLength) return array; 
/* 1754 */     if (length < oldLength) return trim(array, length); 
/* 1755 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] copy(int[][] array, long offset, long length) {
/* 1768 */     ensureOffsetLength(array, offset, length);
/* 1769 */     int[][] a = IntBigArrays.newBigArray(length);
/* 1770 */     copy(array, offset, a, 0L, length);
/* 1771 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] copy(int[][] array) {
/* 1781 */     int[][] base = (int[][])array.clone();
/* 1782 */     for (int i = base.length; i-- != 0; base[i] = (int[])array[i].clone());
/* 1783 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(int[][] array, int value) {
/* 1797 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(int[][] array, long from, long to, int value) {
/* 1813 */     long length = length(array);
/* 1814 */     ensureFromTo(length, from, to);
/* 1815 */     if (length == 0L)
/* 1816 */       return;  int fromSegment = segment(from);
/* 1817 */     int toSegment = segment(to);
/* 1818 */     int fromDispl = displacement(from);
/* 1819 */     int toDispl = displacement(to);
/* 1820 */     if (fromSegment == toSegment) {
/* 1821 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 1824 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 1825 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 1826 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(int[][] a1, int[][] a2) {
/* 1841 */     if (length(a1) != length(a2)) return false; 
/* 1842 */     int i = a1.length;
/*      */     
/* 1844 */     while (i-- != 0) {
/* 1845 */       int[] t = a1[i];
/* 1846 */       int[] u = a2[i];
/* 1847 */       int j = t.length;
/* 1848 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 1850 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(int[][] a) {
/* 1860 */     if (a == null) return "null"; 
/* 1861 */     long last = length(a) - 1L;
/* 1862 */     if (last == -1L) return "[]"; 
/* 1863 */     StringBuilder b = new StringBuilder();
/* 1864 */     b.append('['); long i;
/* 1865 */     for (i = 0L;; i++) {
/* 1866 */       b.append(String.valueOf(get(a, i)));
/* 1867 */       if (i == last) return b.append(']').toString(); 
/* 1868 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(int[][] a, long from, long to) {
/* 1887 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(int[][] a, long offset, long length) {
/* 1904 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(int[][] a, int[][] b) {
/* 1915 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] shuffle(int[][] a, long from, long to, Random random) {
/* 1928 */     for (long i = to - from; i-- != 0L; ) {
/* 1929 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1930 */       int t = get(a, from + i);
/* 1931 */       set(a, from + i, get(a, from + p));
/* 1932 */       set(a, from + p, t);
/*      */     } 
/* 1934 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] shuffle(int[][] a, Random random) {
/* 1945 */     for (long i = length(a); i-- != 0L; ) {
/* 1946 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1947 */       int t = get(a, i);
/* 1948 */       set(a, i, get(a, p));
/* 1949 */       set(a, p, t);
/*      */     } 
/* 1951 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long get(long[][] array, long index) {
/* 2002 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(long[][] array, long index, long value) {
/* 2013 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(AtomicLongArray[] array) {
/* 2023 */     int length = array.length;
/* 2024 */     return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long get(AtomicLongArray[] array, long index) {
/* 2035 */     return array[segment(index)].get(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(AtomicLongArray[] array, long index, long value) {
/* 2046 */     array[segment(index)].set(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getAndSet(AtomicLongArray[] array, long index, long value) {
/* 2059 */     return array[segment(index)].getAndSet(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getAndAdd(AtomicLongArray[] array, long index, long value) {
/* 2071 */     return array[segment(index)].getAndAdd(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long addAndGet(AtomicLongArray[] array, long index, long value) {
/* 2083 */     return array[segment(index)].addAndGet(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getAndIncrement(AtomicLongArray[] array, long index) {
/* 2094 */     return array[segment(index)].getAndDecrement(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long incrementAndGet(AtomicLongArray[] array, long index) {
/* 2105 */     return array[segment(index)].incrementAndGet(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getAndDecrement(AtomicLongArray[] array, long index) {
/* 2116 */     return array[segment(index)].getAndDecrement(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long decrementAndGet(AtomicLongArray[] array, long index) {
/* 2127 */     return array[segment(index)].decrementAndGet(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compareAndSet(AtomicLongArray[] array, long index, long expected, long value) {
/* 2142 */     return array[segment(index)].compareAndSet(displacement(index), expected, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(long[][] array, long first, long second) {
/* 2153 */     long t = array[segment(first)][displacement(first)];
/* 2154 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 2155 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] reverse(long[][] a) {
/* 2165 */     long length = length(a);
/* 2166 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 2167 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(long[][] array, long index, long incr) {
/* 2178 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(long[][] array, long index, long factor) {
/* 2189 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(long[][] array, long index) {
/* 2199 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(long[][] array, long index) {
/* 2209 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(long[][] array) {
/* 2219 */     int length = array.length;
/* 2220 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(long[][] srcArray, long srcPos, long[][] destArray, long destPos, long length) {
/* 2235 */     if (destPos <= srcPos) {
/* 2236 */       int srcSegment = segment(srcPos);
/* 2237 */       int destSegment = segment(destPos);
/* 2238 */       int srcDispl = displacement(srcPos);
/* 2239 */       int destDispl = displacement(destPos);
/* 2240 */       while (length > 0L) {
/* 2241 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 2242 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2243 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 2244 */         if ((srcDispl += l) == 134217728) {
/* 2245 */           srcDispl = 0;
/* 2246 */           srcSegment++;
/*      */         } 
/* 2248 */         if ((destDispl += l) == 134217728) {
/* 2249 */           destDispl = 0;
/* 2250 */           destSegment++;
/*      */         } 
/* 2252 */         length -= l;
/*      */       } 
/*      */     } else {
/* 2255 */       int srcSegment = segment(srcPos + length);
/* 2256 */       int destSegment = segment(destPos + length);
/* 2257 */       int srcDispl = displacement(srcPos + length);
/* 2258 */       int destDispl = displacement(destPos + length);
/* 2259 */       while (length > 0L) {
/* 2260 */         if (srcDispl == 0) {
/* 2261 */           srcDispl = 134217728;
/* 2262 */           srcSegment--;
/*      */         } 
/* 2264 */         if (destDispl == 0) {
/* 2265 */           destDispl = 134217728;
/* 2266 */           destSegment--;
/*      */         } 
/* 2268 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 2269 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2270 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 2271 */         srcDispl -= l;
/* 2272 */         destDispl -= l;
/* 2273 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyFromBig(long[][] srcArray, long srcPos, long[] destArray, int destPos, int length) {
/* 2289 */     int srcSegment = segment(srcPos);
/* 2290 */     int srcDispl = displacement(srcPos);
/* 2291 */     while (length > 0) {
/* 2292 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 2293 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2294 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 2295 */       if ((srcDispl += l) == 134217728) {
/* 2296 */         srcDispl = 0;
/* 2297 */         srcSegment++;
/*      */       } 
/* 2299 */       destPos += l;
/* 2300 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyToBig(long[] srcArray, int srcPos, long[][] destArray, long destPos, long length) {
/* 2315 */     int destSegment = segment(destPos);
/* 2316 */     int destDispl = displacement(destPos);
/* 2317 */     while (length > 0L) {
/* 2318 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 2319 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2320 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 2321 */       if ((destDispl += l) == 134217728) {
/* 2322 */         destDispl = 0;
/* 2323 */         destSegment++;
/*      */       } 
/* 2325 */       srcPos += l;
/* 2326 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] wrap(long[] array) {
/* 2340 */     if (array.length == 0) return LongBigArrays.EMPTY_BIG_ARRAY; 
/* 2341 */     if (array.length <= 134217728) return new long[][] { array }; 
/* 2342 */     long[][] bigArray = LongBigArrays.newBigArray(array.length);
/* 2343 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 2344 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] ensureCapacity(long[][] array, long length) {
/* 2365 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] forceCapacity(long[][] array, long length, long preserve) {
/* 2384 */     ensureLength(length);
/* 2385 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 2386 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 2387 */     long[][] base = Arrays.<long[]>copyOf(array, baseLength);
/* 2388 */     int residual = (int)(length & 0x7FFFFFFL);
/* 2389 */     if (residual != 0)
/* 2390 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new long[134217728]; i++; }
/* 2391 */        base[baseLength - 1] = new long[residual]; }
/* 2392 */     else { for (int i = valid; i < baseLength; ) { base[i] = new long[134217728]; i++; }  }
/* 2393 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 2394 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] ensureCapacity(long[][] array, long length, long preserve) {
/* 2414 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] grow(long[][] array, long length) {
/* 2436 */     long oldLength = length(array);
/* 2437 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] grow(long[][] array, long length, long preserve) {
/* 2462 */     long oldLength = length(array);
/* 2463 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] trim(long[][] array, long length) {
/* 2481 */     ensureLength(length);
/* 2482 */     long oldLength = length(array);
/* 2483 */     if (length >= oldLength) return array; 
/* 2484 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 2485 */     long[][] base = Arrays.<long[]>copyOf(array, baseLength);
/* 2486 */     int residual = (int)(length & 0x7FFFFFFL);
/* 2487 */     if (residual != 0) base[baseLength - 1] = LongArrays.trim(base[baseLength - 1], residual); 
/* 2488 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] setLength(long[][] array, long length) {
/* 2508 */     long oldLength = length(array);
/* 2509 */     if (length == oldLength) return array; 
/* 2510 */     if (length < oldLength) return trim(array, length); 
/* 2511 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] copy(long[][] array, long offset, long length) {
/* 2524 */     ensureOffsetLength(array, offset, length);
/* 2525 */     long[][] a = LongBigArrays.newBigArray(length);
/* 2526 */     copy(array, offset, a, 0L, length);
/* 2527 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] copy(long[][] array) {
/* 2537 */     long[][] base = (long[][])array.clone();
/* 2538 */     for (int i = base.length; i-- != 0; base[i] = (long[])array[i].clone());
/* 2539 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(long[][] array, long value) {
/* 2553 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(long[][] array, long from, long to, long value) {
/* 2569 */     long length = length(array);
/* 2570 */     ensureFromTo(length, from, to);
/* 2571 */     if (length == 0L)
/* 2572 */       return;  int fromSegment = segment(from);
/* 2573 */     int toSegment = segment(to);
/* 2574 */     int fromDispl = displacement(from);
/* 2575 */     int toDispl = displacement(to);
/* 2576 */     if (fromSegment == toSegment) {
/* 2577 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 2580 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 2581 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 2582 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(long[][] a1, long[][] a2) {
/* 2597 */     if (length(a1) != length(a2)) return false; 
/* 2598 */     int i = a1.length;
/*      */     
/* 2600 */     while (i-- != 0) {
/* 2601 */       long[] t = a1[i];
/* 2602 */       long[] u = a2[i];
/* 2603 */       int j = t.length;
/* 2604 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 2606 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(long[][] a) {
/* 2616 */     if (a == null) return "null"; 
/* 2617 */     long last = length(a) - 1L;
/* 2618 */     if (last == -1L) return "[]"; 
/* 2619 */     StringBuilder b = new StringBuilder();
/* 2620 */     b.append('['); long i;
/* 2621 */     for (i = 0L;; i++) {
/* 2622 */       b.append(String.valueOf(get(a, i)));
/* 2623 */       if (i == last) return b.append(']').toString(); 
/* 2624 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(long[][] a, long from, long to) {
/* 2643 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(long[][] a, long offset, long length) {
/* 2660 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(long[][] a, long[][] b) {
/* 2671 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] shuffle(long[][] a, long from, long to, Random random) {
/* 2684 */     for (long i = to - from; i-- != 0L; ) {
/* 2685 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 2686 */       long t = get(a, from + i);
/* 2687 */       set(a, from + i, get(a, from + p));
/* 2688 */       set(a, from + p, t);
/*      */     } 
/* 2690 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] shuffle(long[][] a, Random random) {
/* 2701 */     for (long i = length(a); i-- != 0L; ) {
/* 2702 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 2703 */       long t = get(a, i);
/* 2704 */       set(a, i, get(a, p));
/* 2705 */       set(a, p, t);
/*      */     } 
/* 2707 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double get(double[][] array, long index) {
/* 2758 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(double[][] array, long index, double value) {
/* 2769 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(double[][] array, long first, long second) {
/* 2780 */     double t = array[segment(first)][displacement(first)];
/* 2781 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 2782 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] reverse(double[][] a) {
/* 2792 */     long length = length(a);
/* 2793 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 2794 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(double[][] array, long index, double incr) {
/* 2805 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(double[][] array, long index, double factor) {
/* 2816 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(double[][] array, long index) {
/* 2826 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(double[][] array, long index) {
/* 2836 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(double[][] array) {
/* 2846 */     int length = array.length;
/* 2847 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(double[][] srcArray, long srcPos, double[][] destArray, long destPos, long length) {
/* 2862 */     if (destPos <= srcPos) {
/* 2863 */       int srcSegment = segment(srcPos);
/* 2864 */       int destSegment = segment(destPos);
/* 2865 */       int srcDispl = displacement(srcPos);
/* 2866 */       int destDispl = displacement(destPos);
/* 2867 */       while (length > 0L) {
/* 2868 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 2869 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2870 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 2871 */         if ((srcDispl += l) == 134217728) {
/* 2872 */           srcDispl = 0;
/* 2873 */           srcSegment++;
/*      */         } 
/* 2875 */         if ((destDispl += l) == 134217728) {
/* 2876 */           destDispl = 0;
/* 2877 */           destSegment++;
/*      */         } 
/* 2879 */         length -= l;
/*      */       } 
/*      */     } else {
/* 2882 */       int srcSegment = segment(srcPos + length);
/* 2883 */       int destSegment = segment(destPos + length);
/* 2884 */       int srcDispl = displacement(srcPos + length);
/* 2885 */       int destDispl = displacement(destPos + length);
/* 2886 */       while (length > 0L) {
/* 2887 */         if (srcDispl == 0) {
/* 2888 */           srcDispl = 134217728;
/* 2889 */           srcSegment--;
/*      */         } 
/* 2891 */         if (destDispl == 0) {
/* 2892 */           destDispl = 134217728;
/* 2893 */           destSegment--;
/*      */         } 
/* 2895 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 2896 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2897 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 2898 */         srcDispl -= l;
/* 2899 */         destDispl -= l;
/* 2900 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyFromBig(double[][] srcArray, long srcPos, double[] destArray, int destPos, int length) {
/* 2916 */     int srcSegment = segment(srcPos);
/* 2917 */     int srcDispl = displacement(srcPos);
/* 2918 */     while (length > 0) {
/* 2919 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 2920 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2921 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 2922 */       if ((srcDispl += l) == 134217728) {
/* 2923 */         srcDispl = 0;
/* 2924 */         srcSegment++;
/*      */       } 
/* 2926 */       destPos += l;
/* 2927 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyToBig(double[] srcArray, int srcPos, double[][] destArray, long destPos, long length) {
/* 2942 */     int destSegment = segment(destPos);
/* 2943 */     int destDispl = displacement(destPos);
/* 2944 */     while (length > 0L) {
/* 2945 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 2946 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2947 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 2948 */       if ((destDispl += l) == 134217728) {
/* 2949 */         destDispl = 0;
/* 2950 */         destSegment++;
/*      */       } 
/* 2952 */       srcPos += l;
/* 2953 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] wrap(double[] array) {
/* 2967 */     if (array.length == 0) return DoubleBigArrays.EMPTY_BIG_ARRAY; 
/* 2968 */     if (array.length <= 134217728) return new double[][] { array }; 
/* 2969 */     double[][] bigArray = DoubleBigArrays.newBigArray(array.length);
/* 2970 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 2971 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] ensureCapacity(double[][] array, long length) {
/* 2992 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] forceCapacity(double[][] array, long length, long preserve) {
/* 3011 */     ensureLength(length);
/* 3012 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 3013 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3014 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/* 3015 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3016 */     if (residual != 0)
/* 3017 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new double[134217728]; i++; }
/* 3018 */        base[baseLength - 1] = new double[residual]; }
/* 3019 */     else { for (int i = valid; i < baseLength; ) { base[i] = new double[134217728]; i++; }  }
/* 3020 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 3021 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] ensureCapacity(double[][] array, long length, long preserve) {
/* 3041 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] grow(double[][] array, long length) {
/* 3063 */     long oldLength = length(array);
/* 3064 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] grow(double[][] array, long length, long preserve) {
/* 3089 */     long oldLength = length(array);
/* 3090 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] trim(double[][] array, long length) {
/* 3108 */     ensureLength(length);
/* 3109 */     long oldLength = length(array);
/* 3110 */     if (length >= oldLength) return array; 
/* 3111 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3112 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/* 3113 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3114 */     if (residual != 0) base[baseLength - 1] = DoubleArrays.trim(base[baseLength - 1], residual); 
/* 3115 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] setLength(double[][] array, long length) {
/* 3135 */     long oldLength = length(array);
/* 3136 */     if (length == oldLength) return array; 
/* 3137 */     if (length < oldLength) return trim(array, length); 
/* 3138 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] copy(double[][] array, long offset, long length) {
/* 3151 */     ensureOffsetLength(array, offset, length);
/* 3152 */     double[][] a = DoubleBigArrays.newBigArray(length);
/* 3153 */     copy(array, offset, a, 0L, length);
/* 3154 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] copy(double[][] array) {
/* 3164 */     double[][] base = (double[][])array.clone();
/* 3165 */     for (int i = base.length; i-- != 0; base[i] = (double[])array[i].clone());
/* 3166 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(double[][] array, double value) {
/* 3180 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(double[][] array, long from, long to, double value) {
/* 3196 */     long length = length(array);
/* 3197 */     ensureFromTo(length, from, to);
/* 3198 */     if (length == 0L)
/* 3199 */       return;  int fromSegment = segment(from);
/* 3200 */     int toSegment = segment(to);
/* 3201 */     int fromDispl = displacement(from);
/* 3202 */     int toDispl = displacement(to);
/* 3203 */     if (fromSegment == toSegment) {
/* 3204 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 3207 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 3208 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 3209 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(double[][] a1, double[][] a2) {
/* 3224 */     if (length(a1) != length(a2)) return false; 
/* 3225 */     int i = a1.length;
/*      */     
/* 3227 */     while (i-- != 0) {
/* 3228 */       double[] t = a1[i];
/* 3229 */       double[] u = a2[i];
/* 3230 */       int j = t.length;
/* 3231 */       while (j-- != 0) { if (Double.doubleToLongBits(t[j]) != Double.doubleToLongBits(u[j])) return false;  }
/*      */     
/* 3233 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(double[][] a) {
/* 3243 */     if (a == null) return "null"; 
/* 3244 */     long last = length(a) - 1L;
/* 3245 */     if (last == -1L) return "[]"; 
/* 3246 */     StringBuilder b = new StringBuilder();
/* 3247 */     b.append('['); long i;
/* 3248 */     for (i = 0L;; i++) {
/* 3249 */       b.append(String.valueOf(get(a, i)));
/* 3250 */       if (i == last) return b.append(']').toString(); 
/* 3251 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(double[][] a, long from, long to) {
/* 3270 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(double[][] a, long offset, long length) {
/* 3287 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(double[][] a, double[][] b) {
/* 3298 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] shuffle(double[][] a, long from, long to, Random random) {
/* 3311 */     for (long i = to - from; i-- != 0L; ) {
/* 3312 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 3313 */       double t = get(a, from + i);
/* 3314 */       set(a, from + i, get(a, from + p));
/* 3315 */       set(a, from + p, t);
/*      */     } 
/* 3317 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] shuffle(double[][] a, Random random) {
/* 3328 */     for (long i = length(a); i-- != 0L; ) {
/* 3329 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 3330 */       double t = get(a, i);
/* 3331 */       set(a, i, get(a, p));
/* 3332 */       set(a, p, t);
/*      */     } 
/* 3334 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean get(boolean[][] array, long index) {
/* 3385 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(boolean[][] array, long index, boolean value) {
/* 3396 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(boolean[][] array, long first, long second) {
/* 3407 */     boolean t = array[segment(first)][displacement(first)];
/* 3408 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 3409 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] reverse(boolean[][] a) {
/* 3419 */     long length = length(a);
/* 3420 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 3421 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(boolean[][] array) {
/* 3431 */     int length = array.length;
/* 3432 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(boolean[][] srcArray, long srcPos, boolean[][] destArray, long destPos, long length) {
/* 3447 */     if (destPos <= srcPos) {
/* 3448 */       int srcSegment = segment(srcPos);
/* 3449 */       int destSegment = segment(destPos);
/* 3450 */       int srcDispl = displacement(srcPos);
/* 3451 */       int destDispl = displacement(destPos);
/* 3452 */       while (length > 0L) {
/* 3453 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 3454 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3455 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 3456 */         if ((srcDispl += l) == 134217728) {
/* 3457 */           srcDispl = 0;
/* 3458 */           srcSegment++;
/*      */         } 
/* 3460 */         if ((destDispl += l) == 134217728) {
/* 3461 */           destDispl = 0;
/* 3462 */           destSegment++;
/*      */         } 
/* 3464 */         length -= l;
/*      */       } 
/*      */     } else {
/* 3467 */       int srcSegment = segment(srcPos + length);
/* 3468 */       int destSegment = segment(destPos + length);
/* 3469 */       int srcDispl = displacement(srcPos + length);
/* 3470 */       int destDispl = displacement(destPos + length);
/* 3471 */       while (length > 0L) {
/* 3472 */         if (srcDispl == 0) {
/* 3473 */           srcDispl = 134217728;
/* 3474 */           srcSegment--;
/*      */         } 
/* 3476 */         if (destDispl == 0) {
/* 3477 */           destDispl = 134217728;
/* 3478 */           destSegment--;
/*      */         } 
/* 3480 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 3481 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3482 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 3483 */         srcDispl -= l;
/* 3484 */         destDispl -= l;
/* 3485 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyFromBig(boolean[][] srcArray, long srcPos, boolean[] destArray, int destPos, int length) {
/* 3501 */     int srcSegment = segment(srcPos);
/* 3502 */     int srcDispl = displacement(srcPos);
/* 3503 */     while (length > 0) {
/* 3504 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 3505 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3506 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 3507 */       if ((srcDispl += l) == 134217728) {
/* 3508 */         srcDispl = 0;
/* 3509 */         srcSegment++;
/*      */       } 
/* 3511 */       destPos += l;
/* 3512 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyToBig(boolean[] srcArray, int srcPos, boolean[][] destArray, long destPos, long length) {
/* 3527 */     int destSegment = segment(destPos);
/* 3528 */     int destDispl = displacement(destPos);
/* 3529 */     while (length > 0L) {
/* 3530 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 3531 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3532 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 3533 */       if ((destDispl += l) == 134217728) {
/* 3534 */         destDispl = 0;
/* 3535 */         destSegment++;
/*      */       } 
/* 3537 */       srcPos += l;
/* 3538 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] wrap(boolean[] array) {
/* 3552 */     if (array.length == 0) return BooleanBigArrays.EMPTY_BIG_ARRAY; 
/* 3553 */     if (array.length <= 134217728) return new boolean[][] { array }; 
/* 3554 */     boolean[][] bigArray = BooleanBigArrays.newBigArray(array.length);
/* 3555 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 3556 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] ensureCapacity(boolean[][] array, long length) {
/* 3577 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] forceCapacity(boolean[][] array, long length, long preserve) {
/* 3596 */     ensureLength(length);
/* 3597 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 3598 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3599 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/* 3600 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3601 */     if (residual != 0)
/* 3602 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new boolean[134217728]; i++; }
/* 3603 */        base[baseLength - 1] = new boolean[residual]; }
/* 3604 */     else { for (int i = valid; i < baseLength; ) { base[i] = new boolean[134217728]; i++; }  }
/* 3605 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 3606 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] ensureCapacity(boolean[][] array, long length, long preserve) {
/* 3626 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] grow(boolean[][] array, long length) {
/* 3648 */     long oldLength = length(array);
/* 3649 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] grow(boolean[][] array, long length, long preserve) {
/* 3674 */     long oldLength = length(array);
/* 3675 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] trim(boolean[][] array, long length) {
/* 3693 */     ensureLength(length);
/* 3694 */     long oldLength = length(array);
/* 3695 */     if (length >= oldLength) return array; 
/* 3696 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3697 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/* 3698 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3699 */     if (residual != 0) base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual); 
/* 3700 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] setLength(boolean[][] array, long length) {
/* 3720 */     long oldLength = length(array);
/* 3721 */     if (length == oldLength) return array; 
/* 3722 */     if (length < oldLength) return trim(array, length); 
/* 3723 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] copy(boolean[][] array, long offset, long length) {
/* 3736 */     ensureOffsetLength(array, offset, length);
/* 3737 */     boolean[][] a = BooleanBigArrays.newBigArray(length);
/* 3738 */     copy(array, offset, a, 0L, length);
/* 3739 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] copy(boolean[][] array) {
/* 3749 */     boolean[][] base = (boolean[][])array.clone();
/* 3750 */     for (int i = base.length; i-- != 0; base[i] = (boolean[])array[i].clone());
/* 3751 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(boolean[][] array, boolean value) {
/* 3765 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(boolean[][] array, long from, long to, boolean value) {
/* 3781 */     long length = length(array);
/* 3782 */     ensureFromTo(length, from, to);
/* 3783 */     if (length == 0L)
/* 3784 */       return;  int fromSegment = segment(from);
/* 3785 */     int toSegment = segment(to);
/* 3786 */     int fromDispl = displacement(from);
/* 3787 */     int toDispl = displacement(to);
/* 3788 */     if (fromSegment == toSegment) {
/* 3789 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 3792 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 3793 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 3794 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(boolean[][] a1, boolean[][] a2) {
/* 3809 */     if (length(a1) != length(a2)) return false; 
/* 3810 */     int i = a1.length;
/*      */     
/* 3812 */     while (i-- != 0) {
/* 3813 */       boolean[] t = a1[i];
/* 3814 */       boolean[] u = a2[i];
/* 3815 */       int j = t.length;
/* 3816 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 3818 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(boolean[][] a) {
/* 3828 */     if (a == null) return "null"; 
/* 3829 */     long last = length(a) - 1L;
/* 3830 */     if (last == -1L) return "[]"; 
/* 3831 */     StringBuilder b = new StringBuilder();
/* 3832 */     b.append('['); long i;
/* 3833 */     for (i = 0L;; i++) {
/* 3834 */       b.append(String.valueOf(get(a, i)));
/* 3835 */       if (i == last) return b.append(']').toString(); 
/* 3836 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(boolean[][] a, long from, long to) {
/* 3855 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(boolean[][] a, long offset, long length) {
/* 3872 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(boolean[][] a, boolean[][] b) {
/* 3883 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] shuffle(boolean[][] a, long from, long to, Random random) {
/* 3896 */     for (long i = to - from; i-- != 0L; ) {
/* 3897 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 3898 */       boolean t = get(a, from + i);
/* 3899 */       set(a, from + i, get(a, from + p));
/* 3900 */       set(a, from + p, t);
/*      */     } 
/* 3902 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] shuffle(boolean[][] a, Random random) {
/* 3913 */     for (long i = length(a); i-- != 0L; ) {
/* 3914 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 3915 */       boolean t = get(a, i);
/* 3916 */       set(a, i, get(a, p));
/* 3917 */       set(a, p, t);
/*      */     } 
/* 3919 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short get(short[][] array, long index) {
/* 3970 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(short[][] array, long index, short value) {
/* 3981 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(short[][] array, long first, long second) {
/* 3992 */     short t = array[segment(first)][displacement(first)];
/* 3993 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 3994 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] reverse(short[][] a) {
/* 4004 */     long length = length(a);
/* 4005 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 4006 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(short[][] array, long index, short incr) {
/* 4017 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(short[][] array, long index, short factor) {
/* 4028 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(short[][] array, long index) {
/* 4038 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(short[][] array, long index) {
/* 4048 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(short[][] array) {
/* 4058 */     int length = array.length;
/* 4059 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(short[][] srcArray, long srcPos, short[][] destArray, long destPos, long length) {
/* 4074 */     if (destPos <= srcPos) {
/* 4075 */       int srcSegment = segment(srcPos);
/* 4076 */       int destSegment = segment(destPos);
/* 4077 */       int srcDispl = displacement(srcPos);
/* 4078 */       int destDispl = displacement(destPos);
/* 4079 */       while (length > 0L) {
/* 4080 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 4081 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4082 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 4083 */         if ((srcDispl += l) == 134217728) {
/* 4084 */           srcDispl = 0;
/* 4085 */           srcSegment++;
/*      */         } 
/* 4087 */         if ((destDispl += l) == 134217728) {
/* 4088 */           destDispl = 0;
/* 4089 */           destSegment++;
/*      */         } 
/* 4091 */         length -= l;
/*      */       } 
/*      */     } else {
/* 4094 */       int srcSegment = segment(srcPos + length);
/* 4095 */       int destSegment = segment(destPos + length);
/* 4096 */       int srcDispl = displacement(srcPos + length);
/* 4097 */       int destDispl = displacement(destPos + length);
/* 4098 */       while (length > 0L) {
/* 4099 */         if (srcDispl == 0) {
/* 4100 */           srcDispl = 134217728;
/* 4101 */           srcSegment--;
/*      */         } 
/* 4103 */         if (destDispl == 0) {
/* 4104 */           destDispl = 134217728;
/* 4105 */           destSegment--;
/*      */         } 
/* 4107 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 4108 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4109 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 4110 */         srcDispl -= l;
/* 4111 */         destDispl -= l;
/* 4112 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyFromBig(short[][] srcArray, long srcPos, short[] destArray, int destPos, int length) {
/* 4128 */     int srcSegment = segment(srcPos);
/* 4129 */     int srcDispl = displacement(srcPos);
/* 4130 */     while (length > 0) {
/* 4131 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 4132 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4133 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 4134 */       if ((srcDispl += l) == 134217728) {
/* 4135 */         srcDispl = 0;
/* 4136 */         srcSegment++;
/*      */       } 
/* 4138 */       destPos += l;
/* 4139 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyToBig(short[] srcArray, int srcPos, short[][] destArray, long destPos, long length) {
/* 4154 */     int destSegment = segment(destPos);
/* 4155 */     int destDispl = displacement(destPos);
/* 4156 */     while (length > 0L) {
/* 4157 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 4158 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4159 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 4160 */       if ((destDispl += l) == 134217728) {
/* 4161 */         destDispl = 0;
/* 4162 */         destSegment++;
/*      */       } 
/* 4164 */       srcPos += l;
/* 4165 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] wrap(short[] array) {
/* 4179 */     if (array.length == 0) return ShortBigArrays.EMPTY_BIG_ARRAY; 
/* 4180 */     if (array.length <= 134217728) return new short[][] { array }; 
/* 4181 */     short[][] bigArray = ShortBigArrays.newBigArray(array.length);
/* 4182 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 4183 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] ensureCapacity(short[][] array, long length) {
/* 4204 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] forceCapacity(short[][] array, long length, long preserve) {
/* 4223 */     ensureLength(length);
/* 4224 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 4225 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4226 */     short[][] base = Arrays.<short[]>copyOf(array, baseLength);
/* 4227 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4228 */     if (residual != 0)
/* 4229 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new short[134217728]; i++; }
/* 4230 */        base[baseLength - 1] = new short[residual]; }
/* 4231 */     else { for (int i = valid; i < baseLength; ) { base[i] = new short[134217728]; i++; }  }
/* 4232 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 4233 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] ensureCapacity(short[][] array, long length, long preserve) {
/* 4253 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] grow(short[][] array, long length) {
/* 4275 */     long oldLength = length(array);
/* 4276 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] grow(short[][] array, long length, long preserve) {
/* 4301 */     long oldLength = length(array);
/* 4302 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] trim(short[][] array, long length) {
/* 4320 */     ensureLength(length);
/* 4321 */     long oldLength = length(array);
/* 4322 */     if (length >= oldLength) return array; 
/* 4323 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4324 */     short[][] base = Arrays.<short[]>copyOf(array, baseLength);
/* 4325 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4326 */     if (residual != 0) base[baseLength - 1] = ShortArrays.trim(base[baseLength - 1], residual); 
/* 4327 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] setLength(short[][] array, long length) {
/* 4347 */     long oldLength = length(array);
/* 4348 */     if (length == oldLength) return array; 
/* 4349 */     if (length < oldLength) return trim(array, length); 
/* 4350 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] copy(short[][] array, long offset, long length) {
/* 4363 */     ensureOffsetLength(array, offset, length);
/* 4364 */     short[][] a = ShortBigArrays.newBigArray(length);
/* 4365 */     copy(array, offset, a, 0L, length);
/* 4366 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] copy(short[][] array) {
/* 4376 */     short[][] base = (short[][])array.clone();
/* 4377 */     for (int i = base.length; i-- != 0; base[i] = (short[])array[i].clone());
/* 4378 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(short[][] array, short value) {
/* 4392 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(short[][] array, long from, long to, short value) {
/* 4408 */     long length = length(array);
/* 4409 */     ensureFromTo(length, from, to);
/* 4410 */     if (length == 0L)
/* 4411 */       return;  int fromSegment = segment(from);
/* 4412 */     int toSegment = segment(to);
/* 4413 */     int fromDispl = displacement(from);
/* 4414 */     int toDispl = displacement(to);
/* 4415 */     if (fromSegment == toSegment) {
/* 4416 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 4419 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 4420 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 4421 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(short[][] a1, short[][] a2) {
/* 4436 */     if (length(a1) != length(a2)) return false; 
/* 4437 */     int i = a1.length;
/*      */     
/* 4439 */     while (i-- != 0) {
/* 4440 */       short[] t = a1[i];
/* 4441 */       short[] u = a2[i];
/* 4442 */       int j = t.length;
/* 4443 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 4445 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(short[][] a) {
/* 4455 */     if (a == null) return "null"; 
/* 4456 */     long last = length(a) - 1L;
/* 4457 */     if (last == -1L) return "[]"; 
/* 4458 */     StringBuilder b = new StringBuilder();
/* 4459 */     b.append('['); long i;
/* 4460 */     for (i = 0L;; i++) {
/* 4461 */       b.append(String.valueOf(get(a, i)));
/* 4462 */       if (i == last) return b.append(']').toString(); 
/* 4463 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(short[][] a, long from, long to) {
/* 4482 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(short[][] a, long offset, long length) {
/* 4499 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(short[][] a, short[][] b) {
/* 4510 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] shuffle(short[][] a, long from, long to, Random random) {
/* 4523 */     for (long i = to - from; i-- != 0L; ) {
/* 4524 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 4525 */       short t = get(a, from + i);
/* 4526 */       set(a, from + i, get(a, from + p));
/* 4527 */       set(a, from + p, t);
/*      */     } 
/* 4529 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] shuffle(short[][] a, Random random) {
/* 4540 */     for (long i = length(a); i-- != 0L; ) {
/* 4541 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 4542 */       short t = get(a, i);
/* 4543 */       set(a, i, get(a, p));
/* 4544 */       set(a, p, t);
/*      */     } 
/* 4546 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char get(char[][] array, long index) {
/* 4597 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(char[][] array, long index, char value) {
/* 4608 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(char[][] array, long first, long second) {
/* 4619 */     char t = array[segment(first)][displacement(first)];
/* 4620 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 4621 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] reverse(char[][] a) {
/* 4631 */     long length = length(a);
/* 4632 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 4633 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(char[][] array, long index, char incr) {
/* 4644 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(char[][] array, long index, char factor) {
/* 4655 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(char[][] array, long index) {
/* 4665 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(char[][] array, long index) {
/* 4675 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(char[][] array) {
/* 4685 */     int length = array.length;
/* 4686 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(char[][] srcArray, long srcPos, char[][] destArray, long destPos, long length) {
/* 4701 */     if (destPos <= srcPos) {
/* 4702 */       int srcSegment = segment(srcPos);
/* 4703 */       int destSegment = segment(destPos);
/* 4704 */       int srcDispl = displacement(srcPos);
/* 4705 */       int destDispl = displacement(destPos);
/* 4706 */       while (length > 0L) {
/* 4707 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 4708 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4709 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 4710 */         if ((srcDispl += l) == 134217728) {
/* 4711 */           srcDispl = 0;
/* 4712 */           srcSegment++;
/*      */         } 
/* 4714 */         if ((destDispl += l) == 134217728) {
/* 4715 */           destDispl = 0;
/* 4716 */           destSegment++;
/*      */         } 
/* 4718 */         length -= l;
/*      */       } 
/*      */     } else {
/* 4721 */       int srcSegment = segment(srcPos + length);
/* 4722 */       int destSegment = segment(destPos + length);
/* 4723 */       int srcDispl = displacement(srcPos + length);
/* 4724 */       int destDispl = displacement(destPos + length);
/* 4725 */       while (length > 0L) {
/* 4726 */         if (srcDispl == 0) {
/* 4727 */           srcDispl = 134217728;
/* 4728 */           srcSegment--;
/*      */         } 
/* 4730 */         if (destDispl == 0) {
/* 4731 */           destDispl = 134217728;
/* 4732 */           destSegment--;
/*      */         } 
/* 4734 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 4735 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4736 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 4737 */         srcDispl -= l;
/* 4738 */         destDispl -= l;
/* 4739 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyFromBig(char[][] srcArray, long srcPos, char[] destArray, int destPos, int length) {
/* 4755 */     int srcSegment = segment(srcPos);
/* 4756 */     int srcDispl = displacement(srcPos);
/* 4757 */     while (length > 0) {
/* 4758 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 4759 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4760 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 4761 */       if ((srcDispl += l) == 134217728) {
/* 4762 */         srcDispl = 0;
/* 4763 */         srcSegment++;
/*      */       } 
/* 4765 */       destPos += l;
/* 4766 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyToBig(char[] srcArray, int srcPos, char[][] destArray, long destPos, long length) {
/* 4781 */     int destSegment = segment(destPos);
/* 4782 */     int destDispl = displacement(destPos);
/* 4783 */     while (length > 0L) {
/* 4784 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 4785 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4786 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 4787 */       if ((destDispl += l) == 134217728) {
/* 4788 */         destDispl = 0;
/* 4789 */         destSegment++;
/*      */       } 
/* 4791 */       srcPos += l;
/* 4792 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] wrap(char[] array) {
/* 4806 */     if (array.length == 0) return CharBigArrays.EMPTY_BIG_ARRAY; 
/* 4807 */     if (array.length <= 134217728) return new char[][] { array }; 
/* 4808 */     char[][] bigArray = CharBigArrays.newBigArray(array.length);
/* 4809 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 4810 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] ensureCapacity(char[][] array, long length) {
/* 4831 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] forceCapacity(char[][] array, long length, long preserve) {
/* 4850 */     ensureLength(length);
/* 4851 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 4852 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4853 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/* 4854 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4855 */     if (residual != 0)
/* 4856 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new char[134217728]; i++; }
/* 4857 */        base[baseLength - 1] = new char[residual]; }
/* 4858 */     else { for (int i = valid; i < baseLength; ) { base[i] = new char[134217728]; i++; }  }
/* 4859 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 4860 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] ensureCapacity(char[][] array, long length, long preserve) {
/* 4880 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] grow(char[][] array, long length) {
/* 4902 */     long oldLength = length(array);
/* 4903 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] grow(char[][] array, long length, long preserve) {
/* 4928 */     long oldLength = length(array);
/* 4929 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] trim(char[][] array, long length) {
/* 4947 */     ensureLength(length);
/* 4948 */     long oldLength = length(array);
/* 4949 */     if (length >= oldLength) return array; 
/* 4950 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4951 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/* 4952 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4953 */     if (residual != 0) base[baseLength - 1] = CharArrays.trim(base[baseLength - 1], residual); 
/* 4954 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] setLength(char[][] array, long length) {
/* 4974 */     long oldLength = length(array);
/* 4975 */     if (length == oldLength) return array; 
/* 4976 */     if (length < oldLength) return trim(array, length); 
/* 4977 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] copy(char[][] array, long offset, long length) {
/* 4990 */     ensureOffsetLength(array, offset, length);
/* 4991 */     char[][] a = CharBigArrays.newBigArray(length);
/* 4992 */     copy(array, offset, a, 0L, length);
/* 4993 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] copy(char[][] array) {
/* 5003 */     char[][] base = (char[][])array.clone();
/* 5004 */     for (int i = base.length; i-- != 0; base[i] = (char[])array[i].clone());
/* 5005 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(char[][] array, char value) {
/* 5019 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(char[][] array, long from, long to, char value) {
/* 5035 */     long length = length(array);
/* 5036 */     ensureFromTo(length, from, to);
/* 5037 */     if (length == 0L)
/* 5038 */       return;  int fromSegment = segment(from);
/* 5039 */     int toSegment = segment(to);
/* 5040 */     int fromDispl = displacement(from);
/* 5041 */     int toDispl = displacement(to);
/* 5042 */     if (fromSegment == toSegment) {
/* 5043 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 5046 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 5047 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 5048 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(char[][] a1, char[][] a2) {
/* 5063 */     if (length(a1) != length(a2)) return false; 
/* 5064 */     int i = a1.length;
/*      */     
/* 5066 */     while (i-- != 0) {
/* 5067 */       char[] t = a1[i];
/* 5068 */       char[] u = a2[i];
/* 5069 */       int j = t.length;
/* 5070 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 5072 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(char[][] a) {
/* 5082 */     if (a == null) return "null"; 
/* 5083 */     long last = length(a) - 1L;
/* 5084 */     if (last == -1L) return "[]"; 
/* 5085 */     StringBuilder b = new StringBuilder();
/* 5086 */     b.append('['); long i;
/* 5087 */     for (i = 0L;; i++) {
/* 5088 */       b.append(String.valueOf(get(a, i)));
/* 5089 */       if (i == last) return b.append(']').toString(); 
/* 5090 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(char[][] a, long from, long to) {
/* 5109 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(char[][] a, long offset, long length) {
/* 5126 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(char[][] a, char[][] b) {
/* 5137 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] shuffle(char[][] a, long from, long to, Random random) {
/* 5150 */     for (long i = to - from; i-- != 0L; ) {
/* 5151 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 5152 */       char t = get(a, from + i);
/* 5153 */       set(a, from + i, get(a, from + p));
/* 5154 */       set(a, from + p, t);
/*      */     } 
/* 5156 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] shuffle(char[][] a, Random random) {
/* 5167 */     for (long i = length(a); i-- != 0L; ) {
/* 5168 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 5169 */       char t = get(a, i);
/* 5170 */       set(a, i, get(a, p));
/* 5171 */       set(a, p, t);
/*      */     } 
/* 5173 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float get(float[][] array, long index) {
/* 5224 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(float[][] array, long index, float value) {
/* 5235 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(float[][] array, long first, long second) {
/* 5246 */     float t = array[segment(first)][displacement(first)];
/* 5247 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 5248 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] reverse(float[][] a) {
/* 5258 */     long length = length(a);
/* 5259 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 5260 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(float[][] array, long index, float incr) {
/* 5271 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(float[][] array, long index, float factor) {
/* 5282 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(float[][] array, long index) {
/* 5292 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(float[][] array, long index) {
/* 5302 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(float[][] array) {
/* 5312 */     int length = array.length;
/* 5313 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(float[][] srcArray, long srcPos, float[][] destArray, long destPos, long length) {
/* 5328 */     if (destPos <= srcPos) {
/* 5329 */       int srcSegment = segment(srcPos);
/* 5330 */       int destSegment = segment(destPos);
/* 5331 */       int srcDispl = displacement(srcPos);
/* 5332 */       int destDispl = displacement(destPos);
/* 5333 */       while (length > 0L) {
/* 5334 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 5335 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5336 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 5337 */         if ((srcDispl += l) == 134217728) {
/* 5338 */           srcDispl = 0;
/* 5339 */           srcSegment++;
/*      */         } 
/* 5341 */         if ((destDispl += l) == 134217728) {
/* 5342 */           destDispl = 0;
/* 5343 */           destSegment++;
/*      */         } 
/* 5345 */         length -= l;
/*      */       } 
/*      */     } else {
/* 5348 */       int srcSegment = segment(srcPos + length);
/* 5349 */       int destSegment = segment(destPos + length);
/* 5350 */       int srcDispl = displacement(srcPos + length);
/* 5351 */       int destDispl = displacement(destPos + length);
/* 5352 */       while (length > 0L) {
/* 5353 */         if (srcDispl == 0) {
/* 5354 */           srcDispl = 134217728;
/* 5355 */           srcSegment--;
/*      */         } 
/* 5357 */         if (destDispl == 0) {
/* 5358 */           destDispl = 134217728;
/* 5359 */           destSegment--;
/*      */         } 
/* 5361 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 5362 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5363 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 5364 */         srcDispl -= l;
/* 5365 */         destDispl -= l;
/* 5366 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyFromBig(float[][] srcArray, long srcPos, float[] destArray, int destPos, int length) {
/* 5382 */     int srcSegment = segment(srcPos);
/* 5383 */     int srcDispl = displacement(srcPos);
/* 5384 */     while (length > 0) {
/* 5385 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 5386 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5387 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 5388 */       if ((srcDispl += l) == 134217728) {
/* 5389 */         srcDispl = 0;
/* 5390 */         srcSegment++;
/*      */       } 
/* 5392 */       destPos += l;
/* 5393 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copyToBig(float[] srcArray, int srcPos, float[][] destArray, long destPos, long length) {
/* 5408 */     int destSegment = segment(destPos);
/* 5409 */     int destDispl = displacement(destPos);
/* 5410 */     while (length > 0L) {
/* 5411 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 5412 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5413 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 5414 */       if ((destDispl += l) == 134217728) {
/* 5415 */         destDispl = 0;
/* 5416 */         destSegment++;
/*      */       } 
/* 5418 */       srcPos += l;
/* 5419 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] wrap(float[] array) {
/* 5433 */     if (array.length == 0) return FloatBigArrays.EMPTY_BIG_ARRAY; 
/* 5434 */     if (array.length <= 134217728) return new float[][] { array }; 
/* 5435 */     float[][] bigArray = FloatBigArrays.newBigArray(array.length);
/* 5436 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 5437 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] ensureCapacity(float[][] array, long length) {
/* 5458 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] forceCapacity(float[][] array, long length, long preserve) {
/* 5477 */     ensureLength(length);
/* 5478 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 5479 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 5480 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/* 5481 */     int residual = (int)(length & 0x7FFFFFFL);
/* 5482 */     if (residual != 0)
/* 5483 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new float[134217728]; i++; }
/* 5484 */        base[baseLength - 1] = new float[residual]; }
/* 5485 */     else { for (int i = valid; i < baseLength; ) { base[i] = new float[134217728]; i++; }  }
/* 5486 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 5487 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] ensureCapacity(float[][] array, long length, long preserve) {
/* 5507 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] grow(float[][] array, long length) {
/* 5529 */     long oldLength = length(array);
/* 5530 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] grow(float[][] array, long length, long preserve) {
/* 5555 */     long oldLength = length(array);
/* 5556 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] trim(float[][] array, long length) {
/* 5574 */     ensureLength(length);
/* 5575 */     long oldLength = length(array);
/* 5576 */     if (length >= oldLength) return array; 
/* 5577 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 5578 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/* 5579 */     int residual = (int)(length & 0x7FFFFFFL);
/* 5580 */     if (residual != 0) base[baseLength - 1] = FloatArrays.trim(base[baseLength - 1], residual); 
/* 5581 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] setLength(float[][] array, long length) {
/* 5601 */     long oldLength = length(array);
/* 5602 */     if (length == oldLength) return array; 
/* 5603 */     if (length < oldLength) return trim(array, length); 
/* 5604 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] copy(float[][] array, long offset, long length) {
/* 5617 */     ensureOffsetLength(array, offset, length);
/* 5618 */     float[][] a = FloatBigArrays.newBigArray(length);
/* 5619 */     copy(array, offset, a, 0L, length);
/* 5620 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] copy(float[][] array) {
/* 5630 */     float[][] base = (float[][])array.clone();
/* 5631 */     for (int i = base.length; i-- != 0; base[i] = (float[])array[i].clone());
/* 5632 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(float[][] array, float value) {
/* 5646 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(float[][] array, long from, long to, float value) {
/* 5662 */     long length = length(array);
/* 5663 */     ensureFromTo(length, from, to);
/* 5664 */     if (length == 0L)
/* 5665 */       return;  int fromSegment = segment(from);
/* 5666 */     int toSegment = segment(to);
/* 5667 */     int fromDispl = displacement(from);
/* 5668 */     int toDispl = displacement(to);
/* 5669 */     if (fromSegment == toSegment) {
/* 5670 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 5673 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 5674 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 5675 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(float[][] a1, float[][] a2) {
/* 5690 */     if (length(a1) != length(a2)) return false; 
/* 5691 */     int i = a1.length;
/*      */     
/* 5693 */     while (i-- != 0) {
/* 5694 */       float[] t = a1[i];
/* 5695 */       float[] u = a2[i];
/* 5696 */       int j = t.length;
/* 5697 */       while (j-- != 0) { if (Float.floatToIntBits(t[j]) != Float.floatToIntBits(u[j])) return false;  }
/*      */     
/* 5699 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(float[][] a) {
/* 5709 */     if (a == null) return "null"; 
/* 5710 */     long last = length(a) - 1L;
/* 5711 */     if (last == -1L) return "[]"; 
/* 5712 */     StringBuilder b = new StringBuilder();
/* 5713 */     b.append('['); long i;
/* 5714 */     for (i = 0L;; i++) {
/* 5715 */       b.append(String.valueOf(get(a, i)));
/* 5716 */       if (i == last) return b.append(']').toString(); 
/* 5717 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(float[][] a, long from, long to) {
/* 5736 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(float[][] a, long offset, long length) {
/* 5753 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(float[][] a, float[][] b) {
/* 5764 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] shuffle(float[][] a, long from, long to, Random random) {
/* 5777 */     for (long i = to - from; i-- != 0L; ) {
/* 5778 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 5779 */       float t = get(a, from + i);
/* 5780 */       set(a, from + i, get(a, from + p));
/* 5781 */       set(a, from + p, t);
/*      */     } 
/* 5783 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] shuffle(float[][] a, Random random) {
/* 5794 */     for (long i = length(a); i-- != 0L; ) {
/* 5795 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 5796 */       float t = get(a, i);
/* 5797 */       set(a, i, get(a, p));
/* 5798 */       set(a, p, t);
/*      */     } 
/* 5800 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K get(K[][] array, long index) {
/* 5850 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void set(K[][] array, long index, K value) {
/* 5861 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void swap(K[][] array, long first, long second) {
/* 5872 */     K t = array[segment(first)][displacement(first)];
/* 5873 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 5874 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] reverse(K[][] a) {
/* 5884 */     long length = length(a);
/* 5885 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 5886 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long length(K[][] array) {
/* 5896 */     int length = array.length;
/* 5897 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void copy(K[][] srcArray, long srcPos, K[][] destArray, long destPos, long length) {
/* 5912 */     if (destPos <= srcPos) {
/* 5913 */       int srcSegment = segment(srcPos);
/* 5914 */       int destSegment = segment(destPos);
/* 5915 */       int srcDispl = displacement(srcPos);
/* 5916 */       int destDispl = displacement(destPos);
/* 5917 */       while (length > 0L) {
/* 5918 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 5919 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5920 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 5921 */         if ((srcDispl += l) == 134217728) {
/* 5922 */           srcDispl = 0;
/* 5923 */           srcSegment++;
/*      */         } 
/* 5925 */         if ((destDispl += l) == 134217728) {
/* 5926 */           destDispl = 0;
/* 5927 */           destSegment++;
/*      */         } 
/* 5929 */         length -= l;
/*      */       } 
/*      */     } else {
/* 5932 */       int srcSegment = segment(srcPos + length);
/* 5933 */       int destSegment = segment(destPos + length);
/* 5934 */       int srcDispl = displacement(srcPos + length);
/* 5935 */       int destDispl = displacement(destPos + length);
/* 5936 */       while (length > 0L) {
/* 5937 */         if (srcDispl == 0) {
/* 5938 */           srcDispl = 134217728;
/* 5939 */           srcSegment--;
/*      */         } 
/* 5941 */         if (destDispl == 0) {
/* 5942 */           destDispl = 134217728;
/* 5943 */           destSegment--;
/*      */         } 
/* 5945 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 5946 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5947 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 5948 */         srcDispl -= l;
/* 5949 */         destDispl -= l;
/* 5950 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void copyFromBig(K[][] srcArray, long srcPos, K[] destArray, int destPos, int length) {
/* 5966 */     int srcSegment = segment(srcPos);
/* 5967 */     int srcDispl = displacement(srcPos);
/* 5968 */     while (length > 0) {
/* 5969 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 5970 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5971 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 5972 */       if ((srcDispl += l) == 134217728) {
/* 5973 */         srcDispl = 0;
/* 5974 */         srcSegment++;
/*      */       } 
/* 5976 */       destPos += l;
/* 5977 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void copyToBig(K[] srcArray, int srcPos, K[][] destArray, long destPos, long length) {
/* 5992 */     int destSegment = segment(destPos);
/* 5993 */     int destDispl = displacement(destPos);
/* 5994 */     while (length > 0L) {
/* 5995 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 5996 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5997 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 5998 */       if ((destDispl += l) == 134217728) {
/* 5999 */         destDispl = 0;
/* 6000 */         destSegment++;
/*      */       } 
/* 6002 */       srcPos += l;
/* 6003 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] wrap(K[] array) {
/* 6018 */     if (array.length == 0 && array.getClass() == Object[].class) return (K[][])ObjectBigArrays.EMPTY_BIG_ARRAY; 
/* 6019 */     if (array.length <= 134217728) {
/* 6020 */       K[][] arrayOfK = (K[][])Array.newInstance(array.getClass(), 1);
/* 6021 */       arrayOfK[0] = array;
/* 6022 */       return arrayOfK;
/*      */     } 
/* 6024 */     K[][] bigArray = (K[][])ObjectBigArrays.newBigArray(array.getClass(), array.length);
/* 6025 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 6026 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length) {
/* 6047 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] forceCapacity(K[][] array, long length, long preserve) {
/* 6071 */     ensureLength(length);
/* 6072 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 6073 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 6074 */     K[][] base = (K[][])Arrays.<Object[]>copyOf((Object[][])array, baseLength);
/* 6075 */     Class<?> componentType = array.getClass().getComponentType();
/* 6076 */     int residual = (int)(length & 0x7FFFFFFL);
/* 6077 */     if (residual != 0)
/* 6078 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728); i++; }
/* 6079 */        base[baseLength - 1] = (K[])Array.newInstance(componentType.getComponentType(), residual); }
/* 6080 */     else { for (int i = valid; i < baseLength; ) { base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728); i++; }  }
/* 6081 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 6082 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length, long preserve) {
/* 6106 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] grow(K[][] array, long length) {
/* 6128 */     long oldLength = length(array);
/* 6129 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] grow(K[][] array, long length, long preserve) {
/* 6154 */     long oldLength = length(array);
/* 6155 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] trim(K[][] array, long length) {
/* 6173 */     ensureLength(length);
/* 6174 */     long oldLength = length(array);
/* 6175 */     if (length >= oldLength) return array; 
/* 6176 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 6177 */     K[][] base = (K[][])Arrays.<Object[]>copyOf((Object[][])array, baseLength);
/* 6178 */     int residual = (int)(length & 0x7FFFFFFL);
/* 6179 */     if (residual != 0) base[baseLength - 1] = (K[])ObjectArrays.trim((Object[])base[baseLength - 1], residual); 
/* 6180 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] setLength(K[][] array, long length) {
/* 6200 */     long oldLength = length(array);
/* 6201 */     if (length == oldLength) return array; 
/* 6202 */     if (length < oldLength) return trim(array, length); 
/* 6203 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] copy(K[][] array, long offset, long length) {
/* 6216 */     ensureOffsetLength(array, offset, length);
/* 6217 */     K[][] a = (K[][])ObjectBigArrays.newBigArray((Object[][])array, length);
/* 6218 */     copy(array, offset, a, 0L, length);
/* 6219 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] copy(K[][] array) {
/* 6229 */     K[][] base = (K[][])array.clone();
/* 6230 */     for (int i = base.length; i-- != 0; base[i] = (K[])array[i].clone());
/* 6231 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void fill(K[][] array, K value) {
/* 6245 */     for (int i = array.length; i-- != 0; Arrays.fill((Object[])array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void fill(K[][] array, long from, long to, K value) {
/* 6261 */     long length = length(array);
/* 6262 */     ensureFromTo(length, from, to);
/* 6263 */     if (length == 0L)
/* 6264 */       return;  int fromSegment = segment(from);
/* 6265 */     int toSegment = segment(to);
/* 6266 */     int fromDispl = displacement(from);
/* 6267 */     int toDispl = displacement(to);
/* 6268 */     if (fromSegment == toSegment) {
/* 6269 */       Arrays.fill((Object[])array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 6272 */     if (toDispl != 0) Arrays.fill((Object[])array[toSegment], 0, toDispl, value); 
/* 6273 */     for (; --toSegment > fromSegment; Arrays.fill((Object[])array[toSegment], value));
/* 6274 */     Arrays.fill((Object[])array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> boolean equals(K[][] a1, K[][] a2) {
/* 6289 */     if (length(a1) != length(a2)) return false; 
/* 6290 */     int i = a1.length;
/*      */     
/* 6292 */     while (i-- != 0) {
/* 6293 */       K[] t = a1[i];
/* 6294 */       K[] u = a2[i];
/* 6295 */       int j = t.length;
/* 6296 */       while (j-- != 0) { if (!Objects.equals(t[j], u[j])) return false;  }
/*      */     
/* 6298 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> String toString(K[][] a) {
/* 6308 */     if (a == null) return "null"; 
/* 6309 */     long last = length(a) - 1L;
/* 6310 */     if (last == -1L) return "[]"; 
/* 6311 */     StringBuilder b = new StringBuilder();
/* 6312 */     b.append('['); long i;
/* 6313 */     for (i = 0L;; i++) {
/* 6314 */       b.append(String.valueOf(get(a, i)));
/* 6315 */       if (i == last) return b.append(']').toString(); 
/* 6316 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureFromTo(K[][] a, long from, long to) {
/* 6335 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureOffsetLength(K[][] a, long offset, long length) {
/* 6352 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureSameLength(K[][] a, K[][] b) {
/* 6363 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] shuffle(K[][] a, long from, long to, Random random) {
/* 6376 */     for (long i = to - from; i-- != 0L; ) {
/* 6377 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 6378 */       K t = get(a, from + i);
/* 6379 */       set(a, from + i, get(a, from + p));
/* 6380 */       set(a, from + p, t);
/*      */     } 
/* 6382 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] shuffle(K[][] a, Random random) {
/* 6393 */     for (long i = length(a); i-- != 0L; ) {
/* 6394 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 6395 */       K t = get(a, i);
/* 6396 */       set(a, i, get(a, p));
/* 6397 */       set(a, p, t);
/*      */     } 
/* 6399 */     return a;
/*      */   }
/*      */   
/*      */   public static void main(String[] arg) {
/*      */     // Byte code:
/*      */     //   0: lconst_1
/*      */     //   1: aload_0
/*      */     //   2: iconst_0
/*      */     //   3: aaload
/*      */     //   4: invokestatic parseInt : (Ljava/lang/String;)I
/*      */     //   7: lshl
/*      */     //   8: invokestatic newBigArray : (J)[[I
/*      */     //   11: astore_1
/*      */     //   12: bipush #10
/*      */     //   14: istore #10
/*      */     //   16: iload #10
/*      */     //   18: iinc #10, -1
/*      */     //   21: ifeq -> 378
/*      */     //   24: invokestatic currentTimeMillis : ()J
/*      */     //   27: lneg
/*      */     //   28: lstore #8
/*      */     //   30: lconst_0
/*      */     //   31: lstore_2
/*      */     //   32: aload_1
/*      */     //   33: invokestatic length : ([[I)J
/*      */     //   36: lstore #11
/*      */     //   38: lload #11
/*      */     //   40: dup2
/*      */     //   41: lconst_1
/*      */     //   42: lsub
/*      */     //   43: lstore #11
/*      */     //   45: lconst_0
/*      */     //   46: lcmp
/*      */     //   47: ifeq -> 66
/*      */     //   50: lload_2
/*      */     //   51: lload #11
/*      */     //   53: aload_1
/*      */     //   54: lload #11
/*      */     //   56: invokestatic get : ([[IJ)I
/*      */     //   59: i2l
/*      */     //   60: lxor
/*      */     //   61: lxor
/*      */     //   62: lstore_2
/*      */     //   63: goto -> 38
/*      */     //   66: lload_2
/*      */     //   67: lconst_0
/*      */     //   68: lcmp
/*      */     //   69: ifne -> 78
/*      */     //   72: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   75: invokevirtual println : ()V
/*      */     //   78: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   81: new java/lang/StringBuilder
/*      */     //   84: dup
/*      */     //   85: invokespecial <init> : ()V
/*      */     //   88: ldc_w 'Single loop: '
/*      */     //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   94: lload #8
/*      */     //   96: invokestatic currentTimeMillis : ()J
/*      */     //   99: ladd
/*      */     //   100: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   103: ldc_w 'ms'
/*      */     //   106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   109: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   112: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   115: invokestatic currentTimeMillis : ()J
/*      */     //   118: lneg
/*      */     //   119: lstore #8
/*      */     //   121: lconst_0
/*      */     //   122: lstore #4
/*      */     //   124: aload_1
/*      */     //   125: arraylength
/*      */     //   126: istore #11
/*      */     //   128: iload #11
/*      */     //   130: iinc #11, -1
/*      */     //   133: ifeq -> 180
/*      */     //   136: aload_1
/*      */     //   137: iload #11
/*      */     //   139: aaload
/*      */     //   140: astore #12
/*      */     //   142: aload #12
/*      */     //   144: arraylength
/*      */     //   145: istore #13
/*      */     //   147: iload #13
/*      */     //   149: iinc #13, -1
/*      */     //   152: ifeq -> 177
/*      */     //   155: lload #4
/*      */     //   157: aload #12
/*      */     //   159: iload #13
/*      */     //   161: iaload
/*      */     //   162: i2l
/*      */     //   163: iload #11
/*      */     //   165: iload #13
/*      */     //   167: invokestatic index : (II)J
/*      */     //   170: lxor
/*      */     //   171: lxor
/*      */     //   172: lstore #4
/*      */     //   174: goto -> 147
/*      */     //   177: goto -> 128
/*      */     //   180: lload #4
/*      */     //   182: lconst_0
/*      */     //   183: lcmp
/*      */     //   184: ifne -> 193
/*      */     //   187: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   190: invokevirtual println : ()V
/*      */     //   193: lload_2
/*      */     //   194: lload #4
/*      */     //   196: lcmp
/*      */     //   197: ifeq -> 208
/*      */     //   200: new java/lang/AssertionError
/*      */     //   203: dup
/*      */     //   204: invokespecial <init> : ()V
/*      */     //   207: athrow
/*      */     //   208: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   211: new java/lang/StringBuilder
/*      */     //   214: dup
/*      */     //   215: invokespecial <init> : ()V
/*      */     //   218: ldc_w 'Double loop: '
/*      */     //   221: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   224: lload #8
/*      */     //   226: invokestatic currentTimeMillis : ()J
/*      */     //   229: ladd
/*      */     //   230: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   233: ldc_w 'ms'
/*      */     //   236: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   239: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   242: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   245: lconst_0
/*      */     //   246: lstore #6
/*      */     //   248: aload_1
/*      */     //   249: invokestatic length : ([[I)J
/*      */     //   252: lstore #11
/*      */     //   254: aload_1
/*      */     //   255: arraylength
/*      */     //   256: istore #13
/*      */     //   258: iload #13
/*      */     //   260: iinc #13, -1
/*      */     //   263: ifeq -> 310
/*      */     //   266: aload_1
/*      */     //   267: iload #13
/*      */     //   269: aaload
/*      */     //   270: astore #14
/*      */     //   272: aload #14
/*      */     //   274: arraylength
/*      */     //   275: istore #15
/*      */     //   277: iload #15
/*      */     //   279: iinc #15, -1
/*      */     //   282: ifeq -> 307
/*      */     //   285: lload #4
/*      */     //   287: aload #14
/*      */     //   289: iload #15
/*      */     //   291: iaload
/*      */     //   292: i2l
/*      */     //   293: lload #11
/*      */     //   295: lconst_1
/*      */     //   296: lsub
/*      */     //   297: dup2
/*      */     //   298: lstore #11
/*      */     //   300: lxor
/*      */     //   301: lxor
/*      */     //   302: lstore #4
/*      */     //   304: goto -> 277
/*      */     //   307: goto -> 258
/*      */     //   310: lload #6
/*      */     //   312: lconst_0
/*      */     //   313: lcmp
/*      */     //   314: ifne -> 323
/*      */     //   317: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   320: invokevirtual println : ()V
/*      */     //   323: lload_2
/*      */     //   324: lload #6
/*      */     //   326: lcmp
/*      */     //   327: ifeq -> 338
/*      */     //   330: new java/lang/AssertionError
/*      */     //   333: dup
/*      */     //   334: invokespecial <init> : ()V
/*      */     //   337: athrow
/*      */     //   338: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   341: new java/lang/StringBuilder
/*      */     //   344: dup
/*      */     //   345: invokespecial <init> : ()V
/*      */     //   348: ldc_w 'Double loop (with additional index): '
/*      */     //   351: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   354: lload #8
/*      */     //   356: invokestatic currentTimeMillis : ()J
/*      */     //   359: ladd
/*      */     //   360: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   363: ldc_w 'ms'
/*      */     //   366: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   369: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   372: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   375: goto -> 16
/*      */     //   378: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #6403	-> 0
/*      */     //   #6405	-> 12
/*      */     //   #6406	-> 24
/*      */     //   #6407	-> 30
/*      */     //   #6408	-> 32
/*      */     //   #6409	-> 66
/*      */     //   #6410	-> 78
/*      */     //   #6411	-> 115
/*      */     //   #6412	-> 121
/*      */     //   #6413	-> 124
/*      */     //   #6414	-> 136
/*      */     //   #6415	-> 142
/*      */     //   #6416	-> 177
/*      */     //   #6417	-> 180
/*      */     //   #6418	-> 193
/*      */     //   #6419	-> 208
/*      */     //   #6420	-> 245
/*      */     //   #6421	-> 248
/*      */     //   #6422	-> 254
/*      */     //   #6423	-> 266
/*      */     //   #6424	-> 272
/*      */     //   #6425	-> 307
/*      */     //   #6426	-> 310
/*      */     //   #6427	-> 323
/*      */     //   #6428	-> 338
/*      */     //   #6429	-> 375
/*      */     //   #6430	-> 378
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   38	28	11	i	J
/*      */     //   147	30	13	d	I
/*      */     //   142	35	12	t	[I
/*      */     //   128	52	11	i	I
/*      */     //   277	30	15	d	I
/*      */     //   272	35	14	t	[I
/*      */     //   258	52	13	i	I
/*      */     //   254	121	11	j	J
/*      */     //   32	346	2	x	J
/*      */     //   124	254	4	y	J
/*      */     //   248	130	6	z	J
/*      */     //   30	348	8	start	J
/*      */     //   16	362	10	k	I
/*      */     //   0	379	0	arg	[Ljava/lang/String;
/*      */     //   12	367	1	a	[[I
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\fastutil\BigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */