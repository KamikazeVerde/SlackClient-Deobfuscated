/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.reader.StreamReader;
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
/*     */ class SafeRepresenter
/*     */   extends BaseRepresenter
/*     */ {
/*     */   protected Map<Class<? extends Object>, Tag> classTags;
/*  44 */   protected TimeZone timeZone = null;
/*     */   protected DumperOptions.NonPrintableStyle nonPrintableStyle;
/*     */   
/*     */   public SafeRepresenter(DumperOptions options) {
/*  48 */     if (options == null) {
/*  49 */       throw new NullPointerException("DumperOptions must be provided.");
/*     */     }
/*  51 */     this.nullRepresenter = new RepresentNull();
/*  52 */     this.representers.put(String.class, new RepresentString());
/*  53 */     this.representers.put(Boolean.class, new RepresentBoolean());
/*  54 */     this.representers.put(Character.class, new RepresentString());
/*  55 */     this.representers.put(UUID.class, new RepresentUuid());
/*  56 */     this.representers.put(byte[].class, new RepresentByteArray());
/*     */     
/*  58 */     Represent primitiveArray = new RepresentPrimitiveArray();
/*  59 */     this.representers.put(short[].class, primitiveArray);
/*  60 */     this.representers.put(int[].class, primitiveArray);
/*  61 */     this.representers.put(long[].class, primitiveArray);
/*  62 */     this.representers.put(float[].class, primitiveArray);
/*  63 */     this.representers.put(double[].class, primitiveArray);
/*  64 */     this.representers.put(char[].class, primitiveArray);
/*  65 */     this.representers.put(boolean[].class, primitiveArray);
/*     */     
/*  67 */     this.multiRepresenters.put(Number.class, new RepresentNumber());
/*  68 */     this.multiRepresenters.put(List.class, new RepresentList());
/*  69 */     this.multiRepresenters.put(Map.class, new RepresentMap());
/*  70 */     this.multiRepresenters.put(Set.class, new RepresentSet());
/*  71 */     this.multiRepresenters.put(Iterator.class, new RepresentIterator());
/*  72 */     this.multiRepresenters.put((new Object[0]).getClass(), new RepresentArray());
/*  73 */     this.multiRepresenters.put(Date.class, new RepresentDate());
/*  74 */     this.multiRepresenters.put(Enum.class, new RepresentEnum());
/*  75 */     this.multiRepresenters.put(Calendar.class, new RepresentDate());
/*  76 */     this.classTags = new HashMap<>();
/*  77 */     this.nonPrintableStyle = options.getNonPrintableStyle();
/*     */   }
/*     */   
/*     */   protected Tag getTag(Class<?> clazz, Tag defaultTag) {
/*  81 */     if (this.classTags.containsKey(clazz)) {
/*  82 */       return this.classTags.get(clazz);
/*     */     }
/*  84 */     return defaultTag;
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
/*     */   public Tag addClassTag(Class<? extends Object> clazz, Tag tag) {
/*  96 */     if (tag == null) {
/*  97 */       throw new NullPointerException("Tag must be provided.");
/*     */     }
/*  99 */     return this.classTags.put(clazz, tag);
/*     */   }
/*     */   
/*     */   protected class RepresentNull
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 105 */       return SafeRepresenter.this.representScalar(Tag.NULL, "null");
/*     */     }
/*     */   }
/*     */   
/* 109 */   private static final Pattern MULTILINE_PATTERN = Pattern.compile("\n|| | ");
/*     */   
/*     */   protected class RepresentString
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 114 */       Tag tag = Tag.STR;
/* 115 */       DumperOptions.ScalarStyle style = null;
/* 116 */       String value = data.toString();
/* 117 */       if (SafeRepresenter.this.nonPrintableStyle == DumperOptions.NonPrintableStyle.BINARY && 
/* 118 */         !StreamReader.isPrintable(value)) {
/* 119 */         tag = Tag.BINARY;
/*     */         
/* 121 */         byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
/*     */ 
/*     */ 
/*     */         
/* 125 */         String checkValue = new String(bytes, StandardCharsets.UTF_8);
/* 126 */         if (!checkValue.equals(value)) {
/* 127 */           throw new YAMLException("invalid string value has occurred");
/*     */         }
/* 129 */         char[] binary = Base64Coder.encode(bytes);
/* 130 */         value = String.valueOf(binary);
/* 131 */         style = DumperOptions.ScalarStyle.LITERAL;
/*     */       } 
/*     */ 
/*     */       
/* 135 */       if (SafeRepresenter.this.defaultScalarStyle == DumperOptions.ScalarStyle.PLAIN && SafeRepresenter
/* 136 */         .MULTILINE_PATTERN.matcher(value).find()) {
/* 137 */         style = DumperOptions.ScalarStyle.LITERAL;
/*     */       }
/* 139 */       return SafeRepresenter.this.representScalar(tag, value, style);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentBoolean
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/*     */       String value;
/* 147 */       if (Boolean.TRUE.equals(data)) {
/* 148 */         value = "true";
/*     */       } else {
/* 150 */         value = "false";
/*     */       } 
/* 152 */       return SafeRepresenter.this.representScalar(Tag.BOOL, value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentNumber
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/*     */       Tag tag;
/*     */       String value;
/* 161 */       if (data instanceof Byte || data instanceof Short || data instanceof Integer || data instanceof Long || data instanceof java.math.BigInteger) {
/*     */         
/* 163 */         tag = Tag.INT;
/* 164 */         value = data.toString();
/*     */       } else {
/* 166 */         Number number = (Number)data;
/* 167 */         tag = Tag.FLOAT;
/* 168 */         if (number.equals(Double.valueOf(Double.NaN))) {
/* 169 */           value = ".NaN";
/* 170 */         } else if (number.equals(Double.valueOf(Double.POSITIVE_INFINITY))) {
/* 171 */           value = ".inf";
/* 172 */         } else if (number.equals(Double.valueOf(Double.NEGATIVE_INFINITY))) {
/* 173 */           value = "-.inf";
/*     */         } else {
/* 175 */           value = number.toString();
/*     */         } 
/*     */       } 
/* 178 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentList
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 186 */       return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), (List)data, DumperOptions.FlowStyle.AUTO);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class RepresentIterator
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 195 */       Iterator<Object> iter = (Iterator<Object>)data;
/* 196 */       return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), new SafeRepresenter.IteratorWrapper(iter), DumperOptions.FlowStyle.AUTO);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IteratorWrapper
/*     */     implements Iterable<Object>
/*     */   {
/*     */     private final Iterator<Object> iter;
/*     */     
/*     */     public IteratorWrapper(Iterator<Object> iter) {
/* 206 */       this.iter = iter;
/*     */     }
/*     */     
/*     */     public Iterator<Object> iterator() {
/* 210 */       return this.iter;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentArray
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 217 */       Object[] array = (Object[])data;
/* 218 */       List<Object> list = Arrays.asList(array);
/* 219 */       return SafeRepresenter.this.representSequence(Tag.SEQ, list, DumperOptions.FlowStyle.AUTO);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class RepresentPrimitiveArray
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 230 */       Class<?> type = data.getClass().getComponentType();
/*     */       
/* 232 */       if (byte.class == type)
/* 233 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asByteList(data), DumperOptions.FlowStyle.AUTO); 
/* 234 */       if (short.class == type)
/* 235 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asShortList(data), DumperOptions.FlowStyle.AUTO); 
/* 236 */       if (int.class == type)
/* 237 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asIntList(data), DumperOptions.FlowStyle.AUTO); 
/* 238 */       if (long.class == type)
/* 239 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asLongList(data), DumperOptions.FlowStyle.AUTO); 
/* 240 */       if (float.class == type)
/* 241 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asFloatList(data), DumperOptions.FlowStyle.AUTO); 
/* 242 */       if (double.class == type)
/* 243 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asDoubleList(data), DumperOptions.FlowStyle.AUTO); 
/* 244 */       if (char.class == type)
/* 245 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asCharList(data), DumperOptions.FlowStyle.AUTO); 
/* 246 */       if (boolean.class == type) {
/* 247 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asBooleanList(data), DumperOptions.FlowStyle.AUTO);
/*     */       }
/*     */       
/* 250 */       throw new YAMLException("Unexpected primitive '" + type.getCanonicalName() + "'");
/*     */     }
/*     */     
/*     */     private List<Byte> asByteList(Object in) {
/* 254 */       byte[] array = (byte[])in;
/* 255 */       List<Byte> list = new ArrayList<>(array.length);
/* 256 */       for (int i = 0; i < array.length; i++) {
/* 257 */         list.add(Byte.valueOf(array[i]));
/*     */       }
/* 259 */       return list;
/*     */     }
/*     */     
/*     */     private List<Short> asShortList(Object in) {
/* 263 */       short[] array = (short[])in;
/* 264 */       List<Short> list = new ArrayList<>(array.length);
/* 265 */       for (int i = 0; i < array.length; i++) {
/* 266 */         list.add(Short.valueOf(array[i]));
/*     */       }
/* 268 */       return list;
/*     */     }
/*     */     
/*     */     private List<Integer> asIntList(Object in) {
/* 272 */       int[] array = (int[])in;
/* 273 */       List<Integer> list = new ArrayList<>(array.length);
/* 274 */       for (int i = 0; i < array.length; i++) {
/* 275 */         list.add(Integer.valueOf(array[i]));
/*     */       }
/* 277 */       return list;
/*     */     }
/*     */     
/*     */     private List<Long> asLongList(Object in) {
/* 281 */       long[] array = (long[])in;
/* 282 */       List<Long> list = new ArrayList<>(array.length);
/* 283 */       for (int i = 0; i < array.length; i++) {
/* 284 */         list.add(Long.valueOf(array[i]));
/*     */       }
/* 286 */       return list;
/*     */     }
/*     */     
/*     */     private List<Float> asFloatList(Object in) {
/* 290 */       float[] array = (float[])in;
/* 291 */       List<Float> list = new ArrayList<>(array.length);
/* 292 */       for (int i = 0; i < array.length; i++) {
/* 293 */         list.add(Float.valueOf(array[i]));
/*     */       }
/* 295 */       return list;
/*     */     }
/*     */     
/*     */     private List<Double> asDoubleList(Object in) {
/* 299 */       double[] array = (double[])in;
/* 300 */       List<Double> list = new ArrayList<>(array.length);
/* 301 */       for (int i = 0; i < array.length; i++) {
/* 302 */         list.add(Double.valueOf(array[i]));
/*     */       }
/* 304 */       return list;
/*     */     }
/*     */     
/*     */     private List<Character> asCharList(Object in) {
/* 308 */       char[] array = (char[])in;
/* 309 */       List<Character> list = new ArrayList<>(array.length);
/* 310 */       for (int i = 0; i < array.length; i++) {
/* 311 */         list.add(Character.valueOf(array[i]));
/*     */       }
/* 313 */       return list;
/*     */     }
/*     */     
/*     */     private List<Boolean> asBooleanList(Object in) {
/* 317 */       boolean[] array = (boolean[])in;
/* 318 */       List<Boolean> list = new ArrayList<>(array.length);
/* 319 */       for (int i = 0; i < array.length; i++) {
/* 320 */         list.add(Boolean.valueOf(array[i]));
/*     */       }
/* 322 */       return list;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentMap
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 330 */       return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.MAP), (Map<?, ?>)data, DumperOptions.FlowStyle.AUTO);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class RepresentSet
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 339 */       Map<Object, Object> value = new LinkedHashMap<>();
/* 340 */       Set<Object> set = (Set<Object>)data;
/* 341 */       for (Object key : set) {
/* 342 */         value.put(key, null);
/*     */       }
/* 344 */       return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.SET), value, DumperOptions.FlowStyle.AUTO);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class RepresentDate
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/*     */       Calendar calendar;
/* 354 */       if (data instanceof Calendar) {
/* 355 */         calendar = (Calendar)data;
/*     */       } else {
/*     */         
/* 358 */         calendar = Calendar.getInstance((SafeRepresenter.this.getTimeZone() == null) ? TimeZone.getTimeZone("UTC") : SafeRepresenter.this.timeZone);
/* 359 */         calendar.setTime((Date)data);
/*     */       } 
/* 361 */       int years = calendar.get(1);
/* 362 */       int months = calendar.get(2) + 1;
/* 363 */       int days = calendar.get(5);
/* 364 */       int hour24 = calendar.get(11);
/* 365 */       int minutes = calendar.get(12);
/* 366 */       int seconds = calendar.get(13);
/* 367 */       int millis = calendar.get(14);
/* 368 */       StringBuilder buffer = new StringBuilder(String.valueOf(years));
/* 369 */       while (buffer.length() < 4)
/*     */       {
/* 371 */         buffer.insert(0, "0");
/*     */       }
/* 373 */       buffer.append("-");
/* 374 */       if (months < 10) {
/* 375 */         buffer.append("0");
/*     */       }
/* 377 */       buffer.append(months);
/* 378 */       buffer.append("-");
/* 379 */       if (days < 10) {
/* 380 */         buffer.append("0");
/*     */       }
/* 382 */       buffer.append(days);
/* 383 */       buffer.append("T");
/* 384 */       if (hour24 < 10) {
/* 385 */         buffer.append("0");
/*     */       }
/* 387 */       buffer.append(hour24);
/* 388 */       buffer.append(":");
/* 389 */       if (minutes < 10) {
/* 390 */         buffer.append("0");
/*     */       }
/* 392 */       buffer.append(minutes);
/* 393 */       buffer.append(":");
/* 394 */       if (seconds < 10) {
/* 395 */         buffer.append("0");
/*     */       }
/* 397 */       buffer.append(seconds);
/* 398 */       if (millis > 0) {
/* 399 */         if (millis < 10) {
/* 400 */           buffer.append(".00");
/* 401 */         } else if (millis < 100) {
/* 402 */           buffer.append(".0");
/*     */         } else {
/* 404 */           buffer.append(".");
/*     */         } 
/* 406 */         buffer.append(millis);
/*     */       } 
/*     */ 
/*     */       
/* 410 */       int gmtOffset = calendar.getTimeZone().getOffset(calendar.getTime().getTime());
/* 411 */       if (gmtOffset == 0) {
/* 412 */         buffer.append('Z');
/*     */       } else {
/* 414 */         if (gmtOffset < 0) {
/* 415 */           buffer.append('-');
/* 416 */           gmtOffset *= -1;
/*     */         } else {
/* 418 */           buffer.append('+');
/*     */         } 
/* 420 */         int minutesOffset = gmtOffset / 60000;
/* 421 */         int hoursOffset = minutesOffset / 60;
/* 422 */         int partOfHour = minutesOffset % 60;
/*     */         
/* 424 */         if (hoursOffset < 10) {
/* 425 */           buffer.append('0');
/*     */         }
/* 427 */         buffer.append(hoursOffset);
/* 428 */         buffer.append(':');
/* 429 */         if (partOfHour < 10) {
/* 430 */           buffer.append('0');
/*     */         }
/* 432 */         buffer.append(partOfHour);
/*     */       } 
/*     */       
/* 435 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), DumperOptions.ScalarStyle.PLAIN);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentEnum
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 443 */       Tag tag = new Tag(data.getClass());
/* 444 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), ((Enum)data).name());
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentByteArray
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 451 */       char[] binary = Base64Coder.encode((byte[])data);
/* 452 */       return SafeRepresenter.this.representScalar(Tag.BINARY, String.valueOf(binary), DumperOptions.ScalarStyle.LITERAL);
/*     */     }
/*     */   }
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 457 */     return this.timeZone;
/*     */   }
/*     */   
/*     */   public void setTimeZone(TimeZone timeZone) {
/* 461 */     this.timeZone = timeZone;
/*     */   }
/*     */   
/*     */   protected class RepresentUuid
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 467 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), new Tag(UUID.class)), data.toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\representer\SafeRepresenter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */