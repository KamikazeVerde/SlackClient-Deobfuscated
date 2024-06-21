/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ public class StrUtils
/*     */ {
/*     */   public static boolean equalsMask(String str, String mask, char wildChar, char wildCharSingle) {
/*  11 */     if (mask != null && str != null) {
/*     */       
/*  13 */       if (mask.indexOf(wildChar) < 0)
/*     */       {
/*  15 */         return (mask.indexOf(wildCharSingle) < 0) ? mask.equals(str) : equalsMaskSingle(str, mask, wildCharSingle);
/*     */       }
/*     */ 
/*     */       
/*  19 */       List<String> list = new ArrayList();
/*  20 */       String s = "" + wildChar;
/*     */       
/*  22 */       if (mask.startsWith(s))
/*     */       {
/*  24 */         list.add("");
/*     */       }
/*     */       
/*  27 */       StringTokenizer stringtokenizer = new StringTokenizer(mask, s);
/*     */       
/*  29 */       while (stringtokenizer.hasMoreElements())
/*     */       {
/*  31 */         list.add(stringtokenizer.nextToken());
/*     */       }
/*     */       
/*  34 */       if (mask.endsWith(s))
/*     */       {
/*  36 */         list.add("");
/*     */       }
/*     */       
/*  39 */       String s1 = list.get(0);
/*     */       
/*  41 */       if (!startsWithMaskSingle(str, s1, wildCharSingle))
/*     */       {
/*  43 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  47 */       String s2 = list.get(list.size() - 1);
/*     */       
/*  49 */       if (!endsWithMaskSingle(str, s2, wildCharSingle))
/*     */       {
/*  51 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  55 */       int i = 0;
/*     */       
/*  57 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/*  59 */         String s3 = list.get(j);
/*     */         
/*  61 */         if (s3.length() > 0) {
/*     */           
/*  63 */           int k = indexOfMaskSingle(str, s3, i, wildCharSingle);
/*     */           
/*  65 */           if (k < 0)
/*     */           {
/*  67 */             return false;
/*     */           }
/*     */           
/*  70 */           i = k + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/*  74 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     return (mask == str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equalsMaskSingle(String str, String mask, char wildCharSingle) {
/*  87 */     if (str != null && mask != null) {
/*     */       
/*  89 */       if (str.length() != mask.length())
/*     */       {
/*  91 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  95 */       for (int i = 0; i < mask.length(); i++) {
/*     */         
/*  97 */         char c0 = mask.charAt(i);
/*     */         
/*  99 */         if (c0 != wildCharSingle && str.charAt(i) != c0)
/*     */         {
/* 101 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 105 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     return (str == mask);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOfMaskSingle(String str, String mask, int startPos, char wildCharSingle) {
/* 116 */     if (str != null && mask != null) {
/*     */       
/* 118 */       if (startPos >= 0 && startPos <= str.length()) {
/*     */         
/* 120 */         if (str.length() < startPos + mask.length())
/*     */         {
/* 122 */           return -1;
/*     */         }
/*     */ 
/*     */         
/* 126 */         for (int i = startPos; i + mask.length() <= str.length(); i++) {
/*     */           
/* 128 */           String s = str.substring(i, i + mask.length());
/*     */           
/* 130 */           if (equalsMaskSingle(s, mask, wildCharSingle))
/*     */           {
/* 132 */             return i;
/*     */           }
/*     */         } 
/*     */         
/* 136 */         return -1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 141 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean endsWithMaskSingle(String str, String mask, char wildCharSingle) {
/* 152 */     if (str != null && mask != null) {
/*     */       
/* 154 */       if (str.length() < mask.length())
/*     */       {
/* 156 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 160 */       String s = str.substring(str.length() - mask.length(), str.length());
/* 161 */       return equalsMaskSingle(s, mask, wildCharSingle);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     return (str == mask);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean startsWithMaskSingle(String str, String mask, char wildCharSingle) {
/* 172 */     if (str != null && mask != null) {
/*     */       
/* 174 */       if (str.length() < mask.length())
/*     */       {
/* 176 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 180 */       String s = str.substring(0, mask.length());
/* 181 */       return equalsMaskSingle(s, mask, wildCharSingle);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 186 */     return (str == mask);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equalsMask(String str, String[] masks, char wildChar) {
/* 192 */     for (int i = 0; i < masks.length; i++) {
/*     */       
/* 194 */       String s = masks[i];
/*     */       
/* 196 */       if (equalsMask(str, s, wildChar))
/*     */       {
/* 198 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsMask(String str, String mask, char wildChar) {
/* 207 */     if (mask != null && str != null) {
/*     */       
/* 209 */       if (mask.indexOf(wildChar) < 0)
/*     */       {
/* 211 */         return mask.equals(str);
/*     */       }
/*     */ 
/*     */       
/* 215 */       List<String> list = new ArrayList();
/* 216 */       String s = "" + wildChar;
/*     */       
/* 218 */       if (mask.startsWith(s))
/*     */       {
/* 220 */         list.add("");
/*     */       }
/*     */       
/* 223 */       StringTokenizer stringtokenizer = new StringTokenizer(mask, s);
/*     */       
/* 225 */       while (stringtokenizer.hasMoreElements())
/*     */       {
/* 227 */         list.add(stringtokenizer.nextToken());
/*     */       }
/*     */       
/* 230 */       if (mask.endsWith(s))
/*     */       {
/* 232 */         list.add("");
/*     */       }
/*     */       
/* 235 */       String s1 = list.get(0);
/*     */       
/* 237 */       if (!str.startsWith(s1))
/*     */       {
/* 239 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 243 */       String s2 = list.get(list.size() - 1);
/*     */       
/* 245 */       if (!str.endsWith(s2))
/*     */       {
/* 247 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 251 */       int i = 0;
/*     */       
/* 253 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/* 255 */         String s3 = list.get(j);
/*     */         
/* 257 */         if (s3.length() > 0) {
/*     */           
/* 259 */           int k = str.indexOf(s3, i);
/*     */           
/* 261 */           if (k < 0)
/*     */           {
/* 263 */             return false;
/*     */           }
/*     */           
/* 266 */           i = k + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/* 270 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     return (mask == str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] split(String str, String separators) {
/* 283 */     if (str != null && str.length() > 0) {
/*     */       
/* 285 */       if (separators == null)
/*     */       {
/* 287 */         return new String[] { str };
/*     */       }
/*     */ 
/*     */       
/* 291 */       List<String> list = new ArrayList();
/* 292 */       int i = 0;
/*     */       
/* 294 */       for (int j = 0; j < str.length(); j++) {
/*     */         
/* 296 */         char c0 = str.charAt(j);
/*     */         
/* 298 */         if (equals(c0, separators)) {
/*     */           
/* 300 */           list.add(str.substring(i, j));
/* 301 */           i = j + 1;
/*     */         } 
/*     */       } 
/*     */       
/* 305 */       list.add(str.substring(i, str.length()));
/* 306 */       return list.<String>toArray(new String[list.size()]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 311 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equals(char ch, String matches) {
/* 317 */     for (int i = 0; i < matches.length(); i++) {
/*     */       
/* 319 */       if (matches.charAt(i) == ch)
/*     */       {
/* 321 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsTrim(String a, String b) {
/* 330 */     if (a != null)
/*     */     {
/* 332 */       a = a.trim();
/*     */     }
/*     */     
/* 335 */     if (b != null)
/*     */     {
/* 337 */       b = b.trim();
/*     */     }
/*     */     
/* 340 */     return equals(a, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEmpty(String string) {
/* 345 */     return (string == null) ? true : ((string.trim().length() <= 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String stringInc(String str) {
/* 350 */     int i = parseInt(str, -1);
/*     */     
/* 352 */     if (i == -1)
/*     */     {
/* 354 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 358 */     i++;
/* 359 */     String s = "" + i;
/* 360 */     return (s.length() > str.length()) ? "" : fillLeft("" + i, str.length(), '0');
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseInt(String s, int defVal) {
/* 366 */     if (s == null)
/*     */     {
/* 368 */       return defVal;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 374 */       return Integer.parseInt(s);
/*     */     }
/* 376 */     catch (NumberFormatException var3) {
/*     */       
/* 378 */       return defVal;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFilled(String string) {
/* 385 */     return !isEmpty(string);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String addIfNotContains(String target, String source) {
/* 390 */     for (int i = 0; i < source.length(); i++) {
/*     */       
/* 392 */       if (target.indexOf(source.charAt(i)) < 0)
/*     */       {
/* 394 */         target = target + source.charAt(i);
/*     */       }
/*     */     } 
/*     */     
/* 398 */     return target;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fillLeft(String s, int len, char fillChar) {
/* 403 */     if (s == null)
/*     */     {
/* 405 */       s = "";
/*     */     }
/*     */     
/* 408 */     if (s.length() >= len)
/*     */     {
/* 410 */       return s;
/*     */     }
/*     */ 
/*     */     
/* 414 */     StringBuffer stringbuffer = new StringBuffer();
/* 415 */     int i = len - s.length();
/*     */     
/* 417 */     while (stringbuffer.length() < i)
/*     */     {
/* 419 */       stringbuffer.append(fillChar);
/*     */     }
/*     */     
/* 422 */     return stringbuffer.toString() + s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fillRight(String s, int len, char fillChar) {
/* 428 */     if (s == null)
/*     */     {
/* 430 */       s = "";
/*     */     }
/*     */     
/* 433 */     if (s.length() >= len)
/*     */     {
/* 435 */       return s;
/*     */     }
/*     */ 
/*     */     
/* 439 */     StringBuffer stringbuffer = new StringBuffer(s);
/*     */     
/* 441 */     while (stringbuffer.length() < len)
/*     */     {
/* 443 */       stringbuffer.append(fillChar);
/*     */     }
/*     */     
/* 446 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Object a, Object b) {
/* 452 */     return (a == b) ? true : ((a != null && a.equals(b)) ? true : ((b != null && b.equals(a))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean startsWith(String str, String[] prefixes) {
/* 457 */     if (str == null)
/*     */     {
/* 459 */       return false;
/*     */     }
/* 461 */     if (prefixes == null)
/*     */     {
/* 463 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 467 */     for (int i = 0; i < prefixes.length; i++) {
/*     */       
/* 469 */       String s = prefixes[i];
/*     */       
/* 471 */       if (str.startsWith(s))
/*     */       {
/* 473 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 477 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean endsWith(String str, String[] suffixes) {
/* 483 */     if (str == null)
/*     */     {
/* 485 */       return false;
/*     */     }
/* 487 */     if (suffixes == null)
/*     */     {
/* 489 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 493 */     for (int i = 0; i < suffixes.length; i++) {
/*     */       
/* 495 */       String s = suffixes[i];
/*     */       
/* 497 */       if (str.endsWith(s))
/*     */       {
/* 499 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 503 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String prefix) {
/* 509 */     if (str != null && prefix != null) {
/*     */       
/* 511 */       if (str.startsWith(prefix))
/*     */       {
/* 513 */         str = str.substring(prefix.length());
/*     */       }
/*     */       
/* 516 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 520 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String str, String suffix) {
/* 526 */     if (str != null && suffix != null) {
/*     */       
/* 528 */       if (str.endsWith(suffix))
/*     */       {
/* 530 */         str = str.substring(0, str.length() - suffix.length());
/*     */       }
/*     */       
/* 533 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 537 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replaceSuffix(String str, String suffix, String suffixNew) {
/* 543 */     if (str != null && suffix != null) {
/*     */       
/* 545 */       if (!str.endsWith(suffix))
/*     */       {
/* 547 */         return str;
/*     */       }
/*     */ 
/*     */       
/* 551 */       if (suffixNew == null)
/*     */       {
/* 553 */         suffixNew = "";
/*     */       }
/*     */       
/* 556 */       str = str.substring(0, str.length() - suffix.length());
/* 557 */       return str + suffixNew;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 562 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replacePrefix(String str, String prefix, String prefixNew) {
/* 568 */     if (str != null && prefix != null) {
/*     */       
/* 570 */       if (!str.startsWith(prefix))
/*     */       {
/* 572 */         return str;
/*     */       }
/*     */ 
/*     */       
/* 576 */       if (prefixNew == null)
/*     */       {
/* 578 */         prefixNew = "";
/*     */       }
/*     */       
/* 581 */       str = str.substring(prefix.length());
/* 582 */       return prefixNew + str;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 587 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findPrefix(String[] strs, String prefix) {
/* 593 */     if (strs != null && prefix != null) {
/*     */       
/* 595 */       for (int i = 0; i < strs.length; i++) {
/*     */         
/* 597 */         String s = strs[i];
/*     */         
/* 599 */         if (s.startsWith(prefix))
/*     */         {
/* 601 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 605 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 609 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findSuffix(String[] strs, String suffix) {
/* 615 */     if (strs != null && suffix != null) {
/*     */       
/* 617 */       for (int i = 0; i < strs.length; i++) {
/*     */         
/* 619 */         String s = strs[i];
/*     */         
/* 621 */         if (s.endsWith(suffix))
/*     */         {
/* 623 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 627 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 631 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] remove(String[] strs, int start, int end) {
/* 637 */     if (strs == null)
/*     */     {
/* 639 */       return strs;
/*     */     }
/* 641 */     if (end > 0 && start < strs.length) {
/*     */       
/* 643 */       if (start >= end)
/*     */       {
/* 645 */         return strs;
/*     */       }
/*     */ 
/*     */       
/* 649 */       List<String> list = new ArrayList<>(strs.length);
/*     */       
/* 651 */       for (int i = 0; i < strs.length; i++) {
/*     */         
/* 653 */         String s = strs[i];
/*     */         
/* 655 */         if (i < start || i >= end)
/*     */         {
/* 657 */           list.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 661 */       String[] astring = list.<String>toArray(new String[list.size()]);
/* 662 */       return astring;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 667 */     return strs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String str, String[] suffixes) {
/* 673 */     if (str != null && suffixes != null) {
/*     */       
/* 675 */       int i = str.length();
/*     */       
/* 677 */       for (int j = 0; j < suffixes.length; j++) {
/*     */         
/* 679 */         String s = suffixes[j];
/* 680 */         str = removeSuffix(str, s);
/*     */         
/* 682 */         if (str.length() != i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 688 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 692 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String[] prefixes) {
/* 698 */     if (str != null && prefixes != null) {
/*     */       
/* 700 */       int i = str.length();
/*     */       
/* 702 */       for (int j = 0; j < prefixes.length; j++) {
/*     */         
/* 704 */         String s = prefixes[j];
/* 705 */         str = removePrefix(str, s);
/*     */         
/* 707 */         if (str.length() != i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 713 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 717 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String str, String[] prefixes, String[] suffixes) {
/* 723 */     str = removePrefix(str, prefixes);
/* 724 */     str = removeSuffix(str, suffixes);
/* 725 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String str, String prefix, String suffix) {
/* 730 */     return removePrefixSuffix(str, new String[] { prefix }, new String[] { suffix });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSegment(String str, String start, String end) {
/* 735 */     if (str != null && start != null && end != null) {
/*     */       
/* 737 */       int i = str.indexOf(start);
/*     */       
/* 739 */       if (i < 0)
/*     */       {
/* 741 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 745 */       int j = str.indexOf(end, i);
/* 746 */       return (j < 0) ? null : str.substring(i, j + end.length());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 751 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addSuffixCheck(String str, String suffix) {
/* 757 */     return (str != null && suffix != null) ? (str.endsWith(suffix) ? str : (str + suffix)) : str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String addPrefixCheck(String str, String prefix) {
/* 762 */     return (str != null && prefix != null) ? (str.endsWith(prefix) ? str : (prefix + str)) : str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String trim(String str, String chars) {
/* 767 */     if (str != null && chars != null) {
/*     */       
/* 769 */       str = trimLeading(str, chars);
/* 770 */       str = trimTrailing(str, chars);
/* 771 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 775 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String trimLeading(String str, String chars) {
/* 781 */     if (str != null && chars != null) {
/*     */       
/* 783 */       int i = str.length();
/*     */       
/* 785 */       for (int j = 0; j < i; j++) {
/*     */         
/* 787 */         char c0 = str.charAt(j);
/*     */         
/* 789 */         if (chars.indexOf(c0) < 0)
/*     */         {
/* 791 */           return str.substring(j);
/*     */         }
/*     */       } 
/*     */       
/* 795 */       return "";
/*     */     } 
/*     */ 
/*     */     
/* 799 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String trimTrailing(String str, String chars) {
/* 805 */     if (str != null && chars != null) {
/*     */       
/* 807 */       int i = str.length();
/*     */       
/*     */       int j;
/* 810 */       for (j = i; j > 0; j--) {
/*     */         
/* 812 */         char c0 = str.charAt(j - 1);
/*     */         
/* 814 */         if (chars.indexOf(c0) < 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 820 */       return (j == i) ? str : str.substring(0, j);
/*     */     } 
/*     */ 
/*     */     
/* 824 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\StrUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */