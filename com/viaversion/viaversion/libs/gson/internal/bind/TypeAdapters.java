/*      */ package com.viaversion.viaversion.libs.gson.internal.bind;
/*      */ 
/*      */ import com.viaversion.viaversion.libs.gson.Gson;
/*      */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*      */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*      */ import com.viaversion.viaversion.libs.gson.JsonIOException;
/*      */ import com.viaversion.viaversion.libs.gson.JsonNull;
/*      */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*      */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*      */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*      */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*      */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*      */ import com.viaversion.viaversion.libs.gson.annotations.SerializedName;
/*      */ import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
/*      */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*      */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*      */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*      */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.AccessibleObject;
/*      */ import java.lang.reflect.Field;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.BitSet;
/*      */ import java.util.Calendar;
/*      */ import java.util.Currency;
/*      */ import java.util.Deque;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class TypeAdapters
/*      */ {
/*      */   private TypeAdapters() {
/*   68 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*   72 */   public static final TypeAdapter<Class> CLASS = (new TypeAdapter<Class>()
/*      */     {
/*      */       public void write(JsonWriter out, Class value) throws IOException {
/*   75 */         throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + value
/*   76 */             .getName() + ". Forgot to register a type adapter?");
/*      */       }
/*      */       
/*      */       public Class read(JsonReader in) throws IOException {
/*   80 */         throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
/*      */       }
/*   83 */     }).nullSafe();
/*      */   
/*   85 */   public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
/*      */   
/*   87 */   public static final TypeAdapter<BitSet> BIT_SET = (new TypeAdapter<BitSet>() {
/*      */       public BitSet read(JsonReader in) throws IOException {
/*   89 */         BitSet bitset = new BitSet();
/*   90 */         in.beginArray();
/*   91 */         int i = 0;
/*   92 */         JsonToken tokenType = in.peek();
/*   93 */         while (tokenType != JsonToken.END_ARRAY) {
/*      */           boolean set; int intValue;
/*   95 */           switch (tokenType) {
/*      */             case NUMBER:
/*      */             case STRING:
/*   98 */               intValue = in.nextInt();
/*   99 */               if (intValue == 0) {
/*  100 */                 boolean bool = false; break;
/*  101 */               }  if (intValue == 1) {
/*  102 */                 boolean bool = true; break;
/*      */               } 
/*  104 */               throw new JsonSyntaxException("Invalid bitset value " + intValue + ", expected 0 or 1; at path " + in.getPreviousPath());
/*      */ 
/*      */             
/*      */             case BOOLEAN:
/*  108 */               set = in.nextBoolean();
/*      */               break;
/*      */             default:
/*  111 */               throw new JsonSyntaxException("Invalid bitset value type: " + tokenType + "; at path " + in.getPath());
/*      */           } 
/*  113 */           if (set) {
/*  114 */             bitset.set(i);
/*      */           }
/*  116 */           i++;
/*  117 */           tokenType = in.peek();
/*      */         } 
/*  119 */         in.endArray();
/*  120 */         return bitset;
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, BitSet src) throws IOException {
/*  124 */         out.beginArray();
/*  125 */         for (int i = 0, length = src.length(); i < length; i++) {
/*  126 */           int value = src.get(i) ? 1 : 0;
/*  127 */           out.value(value);
/*      */         } 
/*  129 */         out.endArray();
/*      */       }
/*  131 */     }).nullSafe();
/*      */   
/*  133 */   public static final TypeAdapterFactory BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
/*      */   
/*  135 */   public static final TypeAdapter<Boolean> BOOLEAN = new TypeAdapter<Boolean>()
/*      */     {
/*      */       public Boolean read(JsonReader in) throws IOException {
/*  138 */         JsonToken peek = in.peek();
/*  139 */         if (peek == JsonToken.NULL) {
/*  140 */           in.nextNull();
/*  141 */           return null;
/*  142 */         }  if (peek == JsonToken.STRING)
/*      */         {
/*  144 */           return Boolean.valueOf(Boolean.parseBoolean(in.nextString()));
/*      */         }
/*  146 */         return Boolean.valueOf(in.nextBoolean());
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Boolean value) throws IOException {
/*  150 */         out.value(value);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING = new TypeAdapter<Boolean>() {
/*      */       public Boolean read(JsonReader in) throws IOException {
/*  160 */         if (in.peek() == JsonToken.NULL) {
/*  161 */           in.nextNull();
/*  162 */           return null;
/*      */         } 
/*  164 */         return Boolean.valueOf(in.nextString());
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Boolean value) throws IOException {
/*  168 */         out.value((value == null) ? "null" : value.toString());
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  173 */   public static final TypeAdapterFactory BOOLEAN_FACTORY = newFactory(boolean.class, (Class)Boolean.class, (TypeAdapter)BOOLEAN);
/*      */   
/*  175 */   public static final TypeAdapter<Number> BYTE = new TypeAdapter<Number>() {
/*      */       public Number read(JsonReader in) throws IOException {
/*      */         int intValue;
/*  178 */         if (in.peek() == JsonToken.NULL) {
/*  179 */           in.nextNull();
/*  180 */           return null;
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  185 */           intValue = in.nextInt();
/*  186 */         } catch (NumberFormatException e) {
/*  187 */           throw new JsonSyntaxException(e);
/*      */         } 
/*      */         
/*  190 */         if (intValue > 255 || intValue < -128) {
/*  191 */           throw new JsonSyntaxException("Lossy conversion from " + intValue + " to byte; at path " + in.getPreviousPath());
/*      */         }
/*  193 */         return Byte.valueOf((byte)intValue);
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Number value) throws IOException {
/*  197 */         if (value == null) {
/*  198 */           out.nullValue();
/*      */         } else {
/*  200 */           out.value(value.byteValue());
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  206 */   public static final TypeAdapterFactory BYTE_FACTORY = newFactory(byte.class, (Class)Byte.class, (TypeAdapter)BYTE);
/*      */   
/*  208 */   public static final TypeAdapter<Number> SHORT = new TypeAdapter<Number>() {
/*      */       public Number read(JsonReader in) throws IOException {
/*      */         int intValue;
/*  211 */         if (in.peek() == JsonToken.NULL) {
/*  212 */           in.nextNull();
/*  213 */           return null;
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  218 */           intValue = in.nextInt();
/*  219 */         } catch (NumberFormatException e) {
/*  220 */           throw new JsonSyntaxException(e);
/*      */         } 
/*      */         
/*  223 */         if (intValue > 65535 || intValue < -32768) {
/*  224 */           throw new JsonSyntaxException("Lossy conversion from " + intValue + " to short; at path " + in.getPreviousPath());
/*      */         }
/*  226 */         return Short.valueOf((short)intValue);
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Number value) throws IOException {
/*  230 */         if (value == null) {
/*  231 */           out.nullValue();
/*      */         } else {
/*  233 */           out.value(value.shortValue());
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  239 */   public static final TypeAdapterFactory SHORT_FACTORY = newFactory(short.class, (Class)Short.class, (TypeAdapter)SHORT);
/*      */   
/*  241 */   public static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>()
/*      */     {
/*      */       public Number read(JsonReader in) throws IOException {
/*  244 */         if (in.peek() == JsonToken.NULL) {
/*  245 */           in.nextNull();
/*  246 */           return null;
/*      */         } 
/*      */         try {
/*  249 */           return Integer.valueOf(in.nextInt());
/*  250 */         } catch (NumberFormatException e) {
/*  251 */           throw new JsonSyntaxException(e);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Number value) throws IOException {
/*  256 */         if (value == null) {
/*  257 */           out.nullValue();
/*      */         } else {
/*  259 */           out.value(value.intValue());
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  264 */   public static final TypeAdapterFactory INTEGER_FACTORY = newFactory(int.class, (Class)Integer.class, (TypeAdapter)INTEGER);
/*      */   
/*  266 */   public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER = (new TypeAdapter<AtomicInteger>() {
/*      */       public AtomicInteger read(JsonReader in) throws IOException {
/*      */         try {
/*  269 */           return new AtomicInteger(in.nextInt());
/*  270 */         } catch (NumberFormatException e) {
/*  271 */           throw new JsonSyntaxException(e);
/*      */         } 
/*      */       }
/*      */       public void write(JsonWriter out, AtomicInteger value) throws IOException {
/*  275 */         out.value(value.get());
/*      */       }
/*  277 */     }).nullSafe();
/*      */   
/*  279 */   public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, ATOMIC_INTEGER);
/*      */   
/*  281 */   public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN = (new TypeAdapter<AtomicBoolean>() {
/*      */       public AtomicBoolean read(JsonReader in) throws IOException {
/*  283 */         return new AtomicBoolean(in.nextBoolean());
/*      */       }
/*      */       public void write(JsonWriter out, AtomicBoolean value) throws IOException {
/*  286 */         out.value(value.get());
/*      */       }
/*  288 */     }).nullSafe();
/*      */   
/*  290 */   public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);
/*      */   
/*  292 */   public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY = (new TypeAdapter<AtomicIntegerArray>() {
/*      */       public AtomicIntegerArray read(JsonReader in) throws IOException {
/*  294 */         List<Integer> list = new ArrayList<>();
/*  295 */         in.beginArray();
/*  296 */         while (in.hasNext()) {
/*      */           try {
/*  298 */             int integer = in.nextInt();
/*  299 */             list.add(Integer.valueOf(integer));
/*  300 */           } catch (NumberFormatException e) {
/*  301 */             throw new JsonSyntaxException(e);
/*      */           } 
/*      */         } 
/*  304 */         in.endArray();
/*  305 */         int length = list.size();
/*  306 */         AtomicIntegerArray array = new AtomicIntegerArray(length);
/*  307 */         for (int i = 0; i < length; i++) {
/*  308 */           array.set(i, ((Integer)list.get(i)).intValue());
/*      */         }
/*  310 */         return array;
/*      */       }
/*      */       public void write(JsonWriter out, AtomicIntegerArray value) throws IOException {
/*  313 */         out.beginArray();
/*  314 */         for (int i = 0, length = value.length(); i < length; i++) {
/*  315 */           out.value(value.get(i));
/*      */         }
/*  317 */         out.endArray();
/*      */       }
/*  319 */     }).nullSafe();
/*      */   
/*  321 */   public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);
/*      */   
/*  323 */   public static final TypeAdapter<Number> LONG = new TypeAdapter<Number>()
/*      */     {
/*      */       public Number read(JsonReader in) throws IOException {
/*  326 */         if (in.peek() == JsonToken.NULL) {
/*  327 */           in.nextNull();
/*  328 */           return null;
/*      */         } 
/*      */         try {
/*  331 */           return Long.valueOf(in.nextLong());
/*  332 */         } catch (NumberFormatException e) {
/*  333 */           throw new JsonSyntaxException(e);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Number value) throws IOException {
/*  338 */         if (value == null) {
/*  339 */           out.nullValue();
/*      */         } else {
/*  341 */           out.value(value.longValue());
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  346 */   public static final TypeAdapter<Number> FLOAT = new TypeAdapter<Number>()
/*      */     {
/*      */       public Number read(JsonReader in) throws IOException {
/*  349 */         if (in.peek() == JsonToken.NULL) {
/*  350 */           in.nextNull();
/*  351 */           return null;
/*      */         } 
/*  353 */         return Float.valueOf((float)in.nextDouble());
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Number value) throws IOException {
/*  357 */         if (value == null) {
/*  358 */           out.nullValue();
/*      */         }
/*      */         else {
/*      */           
/*  362 */           Number floatNumber = (value instanceof Float) ? value : Float.valueOf(value.floatValue());
/*  363 */           out.value(floatNumber);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  368 */   public static final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>()
/*      */     {
/*      */       public Number read(JsonReader in) throws IOException {
/*  371 */         if (in.peek() == JsonToken.NULL) {
/*  372 */           in.nextNull();
/*  373 */           return null;
/*      */         } 
/*  375 */         return Double.valueOf(in.nextDouble());
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Number value) throws IOException {
/*  379 */         if (value == null) {
/*  380 */           out.nullValue();
/*      */         } else {
/*  382 */           out.value(value.doubleValue());
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  387 */   public static final TypeAdapter<Character> CHARACTER = new TypeAdapter<Character>()
/*      */     {
/*      */       public Character read(JsonReader in) throws IOException {
/*  390 */         if (in.peek() == JsonToken.NULL) {
/*  391 */           in.nextNull();
/*  392 */           return null;
/*      */         } 
/*  394 */         String str = in.nextString();
/*  395 */         if (str.length() != 1) {
/*  396 */           throw new JsonSyntaxException("Expecting character, got: " + str + "; at " + in.getPreviousPath());
/*      */         }
/*  398 */         return Character.valueOf(str.charAt(0));
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Character value) throws IOException {
/*  402 */         out.value((value == null) ? null : String.valueOf(value));
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  407 */   public static final TypeAdapterFactory CHARACTER_FACTORY = newFactory(char.class, (Class)Character.class, (TypeAdapter)CHARACTER);
/*      */   
/*  409 */   public static final TypeAdapter<String> STRING = new TypeAdapter<String>()
/*      */     {
/*      */       public String read(JsonReader in) throws IOException {
/*  412 */         JsonToken peek = in.peek();
/*  413 */         if (peek == JsonToken.NULL) {
/*  414 */           in.nextNull();
/*  415 */           return null;
/*      */         } 
/*      */         
/*  418 */         if (peek == JsonToken.BOOLEAN) {
/*  419 */           return Boolean.toString(in.nextBoolean());
/*      */         }
/*  421 */         return in.nextString();
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, String value) throws IOException {
/*  425 */         out.value(value);
/*      */       }
/*      */     };
/*      */   
/*  429 */   public static final TypeAdapter<BigDecimal> BIG_DECIMAL = new TypeAdapter<BigDecimal>() {
/*      */       public BigDecimal read(JsonReader in) throws IOException {
/*  431 */         if (in.peek() == JsonToken.NULL) {
/*  432 */           in.nextNull();
/*  433 */           return null;
/*      */         } 
/*  435 */         String s = in.nextString();
/*      */         try {
/*  437 */           return new BigDecimal(s);
/*  438 */         } catch (NumberFormatException e) {
/*  439 */           throw new JsonSyntaxException("Failed parsing '" + s + "' as BigDecimal; at path " + in.getPreviousPath(), e);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, BigDecimal value) throws IOException {
/*  444 */         out.value(value);
/*      */       }
/*      */     };
/*      */   
/*  448 */   public static final TypeAdapter<BigInteger> BIG_INTEGER = new TypeAdapter<BigInteger>() {
/*      */       public BigInteger read(JsonReader in) throws IOException {
/*  450 */         if (in.peek() == JsonToken.NULL) {
/*  451 */           in.nextNull();
/*  452 */           return null;
/*      */         } 
/*  454 */         String s = in.nextString();
/*      */         try {
/*  456 */           return new BigInteger(s);
/*  457 */         } catch (NumberFormatException e) {
/*  458 */           throw new JsonSyntaxException("Failed parsing '" + s + "' as BigInteger; at path " + in.getPreviousPath(), e);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, BigInteger value) throws IOException {
/*  463 */         out.value(value);
/*      */       }
/*      */     };
/*      */   
/*  467 */   public static final TypeAdapter<LazilyParsedNumber> LAZILY_PARSED_NUMBER = new TypeAdapter<LazilyParsedNumber>()
/*      */     {
/*      */       
/*      */       public LazilyParsedNumber read(JsonReader in) throws IOException
/*      */       {
/*  472 */         if (in.peek() == JsonToken.NULL) {
/*  473 */           in.nextNull();
/*  474 */           return null;
/*      */         } 
/*  476 */         return new LazilyParsedNumber(in.nextString());
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, LazilyParsedNumber value) throws IOException {
/*  480 */         out.value((Number)value);
/*      */       }
/*      */     };
/*      */   
/*  484 */   public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);
/*      */   
/*  486 */   public static final TypeAdapter<StringBuilder> STRING_BUILDER = new TypeAdapter<StringBuilder>()
/*      */     {
/*      */       public StringBuilder read(JsonReader in) throws IOException {
/*  489 */         if (in.peek() == JsonToken.NULL) {
/*  490 */           in.nextNull();
/*  491 */           return null;
/*      */         } 
/*  493 */         return new StringBuilder(in.nextString());
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, StringBuilder value) throws IOException {
/*  497 */         out.value((value == null) ? null : value.toString());
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  502 */   public static final TypeAdapterFactory STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
/*      */   
/*  504 */   public static final TypeAdapter<StringBuffer> STRING_BUFFER = new TypeAdapter<StringBuffer>()
/*      */     {
/*      */       public StringBuffer read(JsonReader in) throws IOException {
/*  507 */         if (in.peek() == JsonToken.NULL) {
/*  508 */           in.nextNull();
/*  509 */           return null;
/*      */         } 
/*  511 */         return new StringBuffer(in.nextString());
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, StringBuffer value) throws IOException {
/*  515 */         out.value((value == null) ? null : value.toString());
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  520 */   public static final TypeAdapterFactory STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
/*      */   
/*  522 */   public static final TypeAdapter<URL> URL = new TypeAdapter<URL>()
/*      */     {
/*      */       public URL read(JsonReader in) throws IOException {
/*  525 */         if (in.peek() == JsonToken.NULL) {
/*  526 */           in.nextNull();
/*  527 */           return null;
/*      */         } 
/*  529 */         String nextString = in.nextString();
/*  530 */         return "null".equals(nextString) ? null : new URL(nextString);
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, URL value) throws IOException {
/*  534 */         out.value((value == null) ? null : value.toExternalForm());
/*      */       }
/*      */     };
/*      */   
/*  538 */   public static final TypeAdapterFactory URL_FACTORY = newFactory(URL.class, URL);
/*      */   
/*  540 */   public static final TypeAdapter<URI> URI = new TypeAdapter<URI>()
/*      */     {
/*      */       public URI read(JsonReader in) throws IOException {
/*  543 */         if (in.peek() == JsonToken.NULL) {
/*  544 */           in.nextNull();
/*  545 */           return null;
/*      */         } 
/*      */         try {
/*  548 */           String nextString = in.nextString();
/*  549 */           return "null".equals(nextString) ? null : new URI(nextString);
/*  550 */         } catch (URISyntaxException e) {
/*  551 */           throw new JsonIOException(e);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, URI value) throws IOException {
/*  556 */         out.value((value == null) ? null : value.toASCIIString());
/*      */       }
/*      */     };
/*      */   
/*  560 */   public static final TypeAdapterFactory URI_FACTORY = newFactory(URI.class, URI);
/*      */   
/*  562 */   public static final TypeAdapter<InetAddress> INET_ADDRESS = new TypeAdapter<InetAddress>()
/*      */     {
/*      */       public InetAddress read(JsonReader in) throws IOException {
/*  565 */         if (in.peek() == JsonToken.NULL) {
/*  566 */           in.nextNull();
/*  567 */           return null;
/*      */         } 
/*      */         
/*  570 */         return InetAddress.getByName(in.nextString());
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, InetAddress value) throws IOException {
/*  574 */         out.value((value == null) ? null : value.getHostAddress());
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  579 */   public static final TypeAdapterFactory INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
/*      */   
/*  581 */   public static final TypeAdapter<UUID> UUID = new TypeAdapter<UUID>()
/*      */     {
/*      */       public UUID read(JsonReader in) throws IOException {
/*  584 */         if (in.peek() == JsonToken.NULL) {
/*  585 */           in.nextNull();
/*  586 */           return null;
/*      */         } 
/*  588 */         String s = in.nextString();
/*      */         try {
/*  590 */           return UUID.fromString(s);
/*  591 */         } catch (IllegalArgumentException e) {
/*  592 */           throw new JsonSyntaxException("Failed parsing '" + s + "' as UUID; at path " + in.getPreviousPath(), e);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, UUID value) throws IOException {
/*  597 */         out.value((value == null) ? null : value.toString());
/*      */       }
/*      */     };
/*      */   
/*  601 */   public static final TypeAdapterFactory UUID_FACTORY = newFactory(UUID.class, UUID);
/*      */   
/*  603 */   public static final TypeAdapter<Currency> CURRENCY = (new TypeAdapter<Currency>()
/*      */     {
/*      */       public Currency read(JsonReader in) throws IOException {
/*  606 */         String s = in.nextString();
/*      */         try {
/*  608 */           return Currency.getInstance(s);
/*  609 */         } catch (IllegalArgumentException e) {
/*  610 */           throw new JsonSyntaxException("Failed parsing '" + s + "' as Currency; at path " + in.getPreviousPath(), e);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void write(JsonWriter out, Currency value) throws IOException {
/*  615 */         out.value(value.getCurrencyCode());
/*      */       }
/*  617 */     }).nullSafe();
/*  618 */   public static final TypeAdapterFactory CURRENCY_FACTORY = newFactory(Currency.class, CURRENCY);
/*      */   
/*  620 */   public static final TypeAdapter<Calendar> CALENDAR = new TypeAdapter<Calendar>()
/*      */     {
/*      */       private static final String YEAR = "year";
/*      */       private static final String MONTH = "month";
/*      */       private static final String DAY_OF_MONTH = "dayOfMonth";
/*      */       private static final String HOUR_OF_DAY = "hourOfDay";
/*      */       private static final String MINUTE = "minute";
/*      */       private static final String SECOND = "second";
/*      */       
/*      */       public Calendar read(JsonReader in) throws IOException {
/*  630 */         if (in.peek() == JsonToken.NULL) {
/*  631 */           in.nextNull();
/*  632 */           return null;
/*      */         } 
/*  634 */         in.beginObject();
/*  635 */         int year = 0;
/*  636 */         int month = 0;
/*  637 */         int dayOfMonth = 0;
/*  638 */         int hourOfDay = 0;
/*  639 */         int minute = 0;
/*  640 */         int second = 0;
/*  641 */         while (in.peek() != JsonToken.END_OBJECT) {
/*  642 */           String name = in.nextName();
/*  643 */           int value = in.nextInt();
/*  644 */           if ("year".equals(name)) {
/*  645 */             year = value; continue;
/*  646 */           }  if ("month".equals(name)) {
/*  647 */             month = value; continue;
/*  648 */           }  if ("dayOfMonth".equals(name)) {
/*  649 */             dayOfMonth = value; continue;
/*  650 */           }  if ("hourOfDay".equals(name)) {
/*  651 */             hourOfDay = value; continue;
/*  652 */           }  if ("minute".equals(name)) {
/*  653 */             minute = value; continue;
/*  654 */           }  if ("second".equals(name)) {
/*  655 */             second = value;
/*      */           }
/*      */         } 
/*  658 */         in.endObject();
/*  659 */         return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
/*      */       }
/*      */ 
/*      */       
/*      */       public void write(JsonWriter out, Calendar value) throws IOException {
/*  664 */         if (value == null) {
/*  665 */           out.nullValue();
/*      */           return;
/*      */         } 
/*  668 */         out.beginObject();
/*  669 */         out.name("year");
/*  670 */         out.value(value.get(1));
/*  671 */         out.name("month");
/*  672 */         out.value(value.get(2));
/*  673 */         out.name("dayOfMonth");
/*  674 */         out.value(value.get(5));
/*  675 */         out.name("hourOfDay");
/*  676 */         out.value(value.get(11));
/*  677 */         out.name("minute");
/*  678 */         out.value(value.get(12));
/*  679 */         out.name("second");
/*  680 */         out.value(value.get(13));
/*  681 */         out.endObject();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  686 */   public static final TypeAdapterFactory CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, (Class)GregorianCalendar.class, CALENDAR);
/*      */   
/*  688 */   public static final TypeAdapter<Locale> LOCALE = new TypeAdapter<Locale>()
/*      */     {
/*      */       public Locale read(JsonReader in) throws IOException {
/*  691 */         if (in.peek() == JsonToken.NULL) {
/*  692 */           in.nextNull();
/*  693 */           return null;
/*      */         } 
/*  695 */         String locale = in.nextString();
/*  696 */         StringTokenizer tokenizer = new StringTokenizer(locale, "_");
/*  697 */         String language = null;
/*  698 */         String country = null;
/*  699 */         String variant = null;
/*  700 */         if (tokenizer.hasMoreElements()) {
/*  701 */           language = tokenizer.nextToken();
/*      */         }
/*  703 */         if (tokenizer.hasMoreElements()) {
/*  704 */           country = tokenizer.nextToken();
/*      */         }
/*  706 */         if (tokenizer.hasMoreElements()) {
/*  707 */           variant = tokenizer.nextToken();
/*      */         }
/*  709 */         if (country == null && variant == null)
/*  710 */           return new Locale(language); 
/*  711 */         if (variant == null) {
/*  712 */           return new Locale(language, country);
/*      */         }
/*  714 */         return new Locale(language, country, variant);
/*      */       }
/*      */ 
/*      */       
/*      */       public void write(JsonWriter out, Locale value) throws IOException {
/*  719 */         out.value((value == null) ? null : value.toString());
/*      */       }
/*      */     };
/*      */   
/*  723 */   public static final TypeAdapterFactory LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
/*      */   
/*  725 */   public static final TypeAdapter<JsonElement> JSON_ELEMENT = new TypeAdapter<JsonElement>()
/*      */     {
/*      */ 
/*      */       
/*      */       private JsonElement tryBeginNesting(JsonReader in, JsonToken peeked) throws IOException
/*      */       {
/*  731 */         switch (peeked) {
/*      */           case BEGIN_ARRAY:
/*  733 */             in.beginArray();
/*  734 */             return (JsonElement)new JsonArray();
/*      */           case BEGIN_OBJECT:
/*  736 */             in.beginObject();
/*  737 */             return (JsonElement)new JsonObject();
/*      */         } 
/*  739 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       private JsonElement readTerminal(JsonReader in, JsonToken peeked) throws IOException {
/*      */         String number;
/*  745 */         switch (peeked) {
/*      */           case STRING:
/*  747 */             return (JsonElement)new JsonPrimitive(in.nextString());
/*      */           case NUMBER:
/*  749 */             number = in.nextString();
/*  750 */             return (JsonElement)new JsonPrimitive((Number)new LazilyParsedNumber(number));
/*      */           case BOOLEAN:
/*  752 */             return (JsonElement)new JsonPrimitive(Boolean.valueOf(in.nextBoolean()));
/*      */           case NULL:
/*  754 */             in.nextNull();
/*  755 */             return (JsonElement)JsonNull.INSTANCE;
/*      */         } 
/*      */         
/*  758 */         throw new IllegalStateException("Unexpected token: " + peeked);
/*      */       }
/*      */ 
/*      */       
/*      */       public JsonElement read(JsonReader in) throws IOException {
/*  763 */         if (in instanceof JsonTreeReader) {
/*  764 */           return ((JsonTreeReader)in).nextJsonElement();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  769 */         JsonToken peeked = in.peek();
/*      */         
/*  771 */         JsonElement current = tryBeginNesting(in, peeked);
/*  772 */         if (current == null) {
/*  773 */           return readTerminal(in, peeked);
/*      */         }
/*      */         
/*  776 */         Deque<JsonElement> stack = new ArrayDeque<>();
/*      */         
/*      */         while (true) {
/*  779 */           while (in.hasNext()) {
/*  780 */             String name = null;
/*      */             
/*  782 */             if (current instanceof JsonObject) {
/*  783 */               name = in.nextName();
/*      */             }
/*      */             
/*  786 */             peeked = in.peek();
/*  787 */             JsonElement value = tryBeginNesting(in, peeked);
/*  788 */             boolean isNesting = (value != null);
/*      */             
/*  790 */             if (value == null) {
/*  791 */               value = readTerminal(in, peeked);
/*      */             }
/*      */             
/*  794 */             if (current instanceof JsonArray) {
/*  795 */               ((JsonArray)current).add(value);
/*      */             } else {
/*  797 */               ((JsonObject)current).add(name, value);
/*      */             } 
/*      */             
/*  800 */             if (isNesting) {
/*  801 */               stack.addLast(current);
/*  802 */               current = value;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  807 */           if (current instanceof JsonArray) {
/*  808 */             in.endArray();
/*      */           } else {
/*  810 */             in.endObject();
/*      */           } 
/*      */           
/*  813 */           if (stack.isEmpty()) {
/*  814 */             return current;
/*      */           }
/*      */           
/*  817 */           current = stack.removeLast();
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       public void write(JsonWriter out, JsonElement value) throws IOException {
/*  823 */         if (value == null || value.isJsonNull()) {
/*  824 */           out.nullValue();
/*  825 */         } else if (value.isJsonPrimitive()) {
/*  826 */           JsonPrimitive primitive = value.getAsJsonPrimitive();
/*  827 */           if (primitive.isNumber()) {
/*  828 */             out.value(primitive.getAsNumber());
/*  829 */           } else if (primitive.isBoolean()) {
/*  830 */             out.value(primitive.getAsBoolean());
/*      */           } else {
/*  832 */             out.value(primitive.getAsString());
/*      */           }
/*      */         
/*  835 */         } else if (value.isJsonArray()) {
/*  836 */           out.beginArray();
/*  837 */           for (JsonElement e : value.getAsJsonArray()) {
/*  838 */             write(out, e);
/*      */           }
/*  840 */           out.endArray();
/*      */         }
/*  842 */         else if (value.isJsonObject()) {
/*  843 */           out.beginObject();
/*  844 */           for (Map.Entry<String, JsonElement> e : (Iterable<Map.Entry<String, JsonElement>>)value.getAsJsonObject().entrySet()) {
/*  845 */             out.name(e.getKey());
/*  846 */             write(out, e.getValue());
/*      */           } 
/*  848 */           out.endObject();
/*      */         } else {
/*      */           
/*  851 */           throw new IllegalArgumentException("Couldn't write " + value.getClass());
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  857 */   public static final TypeAdapterFactory JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
/*      */   
/*      */   private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
/*  860 */     private final Map<String, T> nameToConstant = new HashMap<>();
/*  861 */     private final Map<String, T> stringToConstant = new HashMap<>();
/*  862 */     private final Map<T, String> constantToName = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EnumTypeAdapter(final Class<T> classOfT) {
/*      */       try {
/*  869 */         Field[] constantFields = AccessController.<Field[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Field[]>() {
/*      */               public Field[] run() {
/*  871 */                 Field[] fields = classOfT.getDeclaredFields();
/*  872 */                 ArrayList<Field> constantFieldsList = new ArrayList<>(fields.length);
/*  873 */                 for (Field f : fields) {
/*  874 */                   if (f.isEnumConstant()) {
/*  875 */                     constantFieldsList.add(f);
/*      */                   }
/*      */                 } 
/*      */                 
/*  879 */                 Field[] constantFields = constantFieldsList.<Field>toArray(new Field[0]);
/*  880 */                 AccessibleObject.setAccessible((AccessibleObject[])constantFields, true);
/*  881 */                 return constantFields;
/*      */               }
/*      */             });
/*  884 */         for (Field constantField : constantFields) {
/*      */           
/*  886 */           Enum enum_ = (Enum)constantField.get(null);
/*  887 */           String name = enum_.name();
/*  888 */           String toStringVal = enum_.toString();
/*      */           
/*  890 */           SerializedName annotation = constantField.<SerializedName>getAnnotation(SerializedName.class);
/*  891 */           if (annotation != null) {
/*  892 */             name = annotation.value();
/*  893 */             for (String alternate : annotation.alternate()) {
/*  894 */               this.nameToConstant.put(alternate, (T)enum_);
/*      */             }
/*      */           } 
/*  897 */           this.nameToConstant.put(name, (T)enum_);
/*  898 */           this.stringToConstant.put(toStringVal, (T)enum_);
/*  899 */           this.constantToName.put((T)enum_, name);
/*      */         } 
/*  901 */       } catch (IllegalAccessException e) {
/*  902 */         throw new AssertionError(e);
/*      */       } 
/*      */     }
/*      */     public T read(JsonReader in) throws IOException {
/*  906 */       if (in.peek() == JsonToken.NULL) {
/*  907 */         in.nextNull();
/*  908 */         return null;
/*      */       } 
/*  910 */       String key = in.nextString();
/*  911 */       Enum enum_ = (Enum)this.nameToConstant.get(key);
/*  912 */       return (enum_ == null) ? this.stringToConstant.get(key) : (T)enum_;
/*      */     }
/*      */     
/*      */     public void write(JsonWriter out, T value) throws IOException {
/*  916 */       out.value((value == null) ? null : this.constantToName.get(value));
/*      */     }
/*      */   }
/*      */   
/*  920 */   public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory() {
/*      */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/*  922 */         Class<? super T> rawType = typeToken.getRawType();
/*  923 */         if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
/*  924 */           return null;
/*      */         }
/*  926 */         if (!rawType.isEnum()) {
/*  927 */           rawType = rawType.getSuperclass();
/*      */         }
/*      */         
/*  930 */         TypeAdapter<T> adapter = new TypeAdapters.EnumTypeAdapter<>(rawType);
/*  931 */         return adapter;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public static <TT> TypeAdapterFactory newFactory(final TypeToken<TT> type, final TypeAdapter<TT> typeAdapter) {
/*  937 */     return new TypeAdapterFactory()
/*      */       {
/*      */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/*  940 */           return typeToken.equals(type) ? typeAdapter : null;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static <TT> TypeAdapterFactory newFactory(final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
/*  947 */     return new TypeAdapterFactory()
/*      */       {
/*      */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/*  950 */           return (typeToken.getRawType() == type) ? typeAdapter : null;
/*      */         }
/*      */         public String toString() {
/*  953 */           return "Factory[type=" + type.getName() + ",adapter=" + typeAdapter + "]";
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static <TT> TypeAdapterFactory newFactory(final Class<TT> unboxed, final Class<TT> boxed, final TypeAdapter<? super TT> typeAdapter) {
/*  960 */     return new TypeAdapterFactory()
/*      */       {
/*      */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/*  963 */           Class<? super T> rawType = typeToken.getRawType();
/*  964 */           return (rawType == unboxed || rawType == boxed) ? typeAdapter : null;
/*      */         }
/*      */         public String toString() {
/*  967 */           return "Factory[type=" + boxed.getName() + "+" + unboxed
/*  968 */             .getName() + ",adapter=" + typeAdapter + "]";
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> base, final Class<? extends TT> sub, final TypeAdapter<? super TT> typeAdapter) {
/*  975 */     return new TypeAdapterFactory()
/*      */       {
/*      */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/*  978 */           Class<? super T> rawType = typeToken.getRawType();
/*  979 */           return (rawType == base || rawType == sub) ? typeAdapter : null;
/*      */         }
/*      */         public String toString() {
/*  982 */           return "Factory[type=" + base.getName() + "+" + sub
/*  983 */             .getName() + ",adapter=" + typeAdapter + "]";
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T1> TypeAdapterFactory newTypeHierarchyFactory(final Class<T1> clazz, final TypeAdapter<T1> typeAdapter) {
/*  994 */     return new TypeAdapterFactory()
/*      */       {
/*      */         public <T2> TypeAdapter<T2> create(Gson gson, TypeToken<T2> typeToken) {
/*  997 */           final Class<? super T2> requestedType = typeToken.getRawType();
/*  998 */           if (!clazz.isAssignableFrom(requestedType)) {
/*  999 */             return null;
/*      */           }
/* 1001 */           return new TypeAdapter<T1>() {
/*      */               public void write(JsonWriter out, T1 value) throws IOException {
/* 1003 */                 typeAdapter.write(out, value);
/*      */               }
/*      */               
/*      */               public T1 read(JsonReader in) throws IOException {
/* 1007 */                 T1 result = (T1)typeAdapter.read(in);
/* 1008 */                 if (result != null && !requestedType.isInstance(result)) {
/* 1009 */                   throw new JsonSyntaxException("Expected a " + requestedType.getName() + " but was " + result
/* 1010 */                       .getClass().getName() + "; at path " + in.getPreviousPath());
/*      */                 }
/* 1012 */                 return result;
/*      */               }
/*      */             };
/*      */         }
/*      */         public String toString() {
/* 1017 */           return "Factory[typeHierarchy=" + clazz.getName() + ",adapter=" + typeAdapter + "]";
/*      */         }
/*      */       };
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\bind\TypeAdapters.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */