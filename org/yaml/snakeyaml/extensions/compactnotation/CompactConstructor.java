/*     */ package org.yaml.snakeyaml.extensions.compactnotation;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.LoaderOptions;
/*     */ import org.yaml.snakeyaml.constructor.Construct;
/*     */ import org.yaml.snakeyaml.constructor.Constructor;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.Property;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
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
/*     */ public class CompactConstructor
/*     */   extends Constructor
/*     */ {
/*  40 */   private static final Pattern GUESS_COMPACT = Pattern.compile("\\p{Alpha}.*\\s*\\((?:,?\\s*(?:(?:\\w*)|(?:\\p{Alpha}\\w*\\s*=.+))\\s*)+\\)");
/*  41 */   private static final Pattern FIRST_PATTERN = Pattern.compile("(\\p{Alpha}.*)(\\s*)\\((.*?)\\)");
/*     */   
/*  43 */   private static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\s*(\\p{Alpha}\\w*)\\s*=(.+)");
/*     */ 
/*     */ 
/*     */   
/*     */   private Construct compactConstruct;
/*     */ 
/*     */ 
/*     */   
/*     */   public CompactConstructor(LoaderOptions loadingConfig) {
/*  52 */     super(loadingConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompactConstructor() {
/*  59 */     super(new LoaderOptions());
/*     */   }
/*     */   
/*     */   protected Object constructCompactFormat(ScalarNode node, CompactData data) {
/*     */     try {
/*  64 */       Object obj = createInstance(node, data);
/*  65 */       Map<String, Object> properties = new HashMap<>(data.getProperties());
/*  66 */       setProperties(obj, properties);
/*  67 */       return obj;
/*  68 */     } catch (Exception e) {
/*  69 */       throw new YAMLException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object createInstance(ScalarNode node, CompactData data) throws Exception {
/*  74 */     Class<?> clazz = getClassForName(data.getPrefix());
/*  75 */     Class<?>[] args = new Class[data.getArguments().size()];
/*  76 */     for (int i = 0; i < args.length; i++)
/*     */     {
/*  78 */       args[i] = String.class;
/*     */     }
/*  80 */     Constructor<?> c = clazz.getDeclaredConstructor(args);
/*  81 */     c.setAccessible(true);
/*  82 */     return c.newInstance(data.getArguments().toArray());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setProperties(Object bean, Map<String, Object> data) throws Exception {
/*  87 */     if (data == null) {
/*  88 */       throw new NullPointerException("Data for Compact Object Notation cannot be null.");
/*     */     }
/*  90 */     for (Map.Entry<String, Object> entry : data.entrySet()) {
/*  91 */       String key = entry.getKey();
/*  92 */       Property property = getPropertyUtils().getProperty(bean.getClass(), key);
/*     */       try {
/*  94 */         property.set(bean, entry.getValue());
/*  95 */       } catch (IllegalArgumentException e) {
/*  96 */         throw new YAMLException("Cannot set property='" + key + "' with value='" + data.get(key) + "' (" + data
/*  97 */             .get(key).getClass() + ") in " + bean);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public CompactData getCompactData(String scalar) {
/* 103 */     if (!scalar.endsWith(")")) {
/* 104 */       return null;
/*     */     }
/* 106 */     if (scalar.indexOf('(') < 0) {
/* 107 */       return null;
/*     */     }
/* 109 */     Matcher m = FIRST_PATTERN.matcher(scalar);
/* 110 */     if (m.matches()) {
/* 111 */       String tag = m.group(1).trim();
/* 112 */       String content = m.group(3);
/* 113 */       CompactData data = new CompactData(tag);
/* 114 */       if (content.length() == 0) {
/* 115 */         return data;
/*     */       }
/* 117 */       String[] names = content.split("\\s*,\\s*");
/* 118 */       for (int i = 0; i < names.length; i++) {
/* 119 */         String section = names[i];
/* 120 */         if (section.indexOf('=') < 0) {
/* 121 */           data.getArguments().add(section);
/*     */         } else {
/* 123 */           Matcher sm = PROPERTY_NAME_PATTERN.matcher(section);
/* 124 */           if (sm.matches()) {
/* 125 */             String name = sm.group(1);
/* 126 */             String value = sm.group(2).trim();
/* 127 */             data.getProperties().put(name, value);
/*     */           } else {
/* 129 */             return null;
/*     */           } 
/*     */         } 
/*     */       } 
/* 133 */       return data;
/*     */     } 
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Construct getCompactConstruct() {
/* 144 */     if (this.compactConstruct == null) {
/* 145 */       this.compactConstruct = createCompactConstruct();
/*     */     }
/* 147 */     return this.compactConstruct;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Construct createCompactConstruct() {
/* 156 */     return (Construct)new ConstructCompactObject();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Construct getConstructor(Node node) {
/* 161 */     if (node instanceof MappingNode) {
/* 162 */       MappingNode mnode = (MappingNode)node;
/* 163 */       List<NodeTuple> list = mnode.getValue();
/* 164 */       if (list.size() == 1) {
/* 165 */         NodeTuple tuple = list.get(0);
/* 166 */         Node key = tuple.getKeyNode();
/* 167 */         if (key instanceof ScalarNode) {
/* 168 */           ScalarNode scalar = (ScalarNode)key;
/* 169 */           if (GUESS_COMPACT.matcher(scalar.getValue()).matches()) {
/* 170 */             return getCompactConstruct();
/*     */           }
/*     */         } 
/*     */       } 
/* 174 */     } else if (node instanceof ScalarNode) {
/* 175 */       ScalarNode scalar = (ScalarNode)node;
/* 176 */       if (GUESS_COMPACT.matcher(scalar.getValue()).matches()) {
/* 177 */         return getCompactConstruct();
/*     */       }
/*     */     } 
/* 180 */     return super.getConstructor(node);
/*     */   }
/*     */   
/*     */   public class ConstructCompactObject
/*     */     extends Constructor.ConstructMapping {
/*     */     public ConstructCompactObject() {
/* 186 */       super(CompactConstructor.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 191 */       MappingNode mnode = (MappingNode)node;
/* 192 */       NodeTuple nodeTuple = mnode.getValue().iterator().next();
/*     */       
/* 194 */       Node valueNode = nodeTuple.getValueNode();
/*     */       
/* 196 */       if (valueNode instanceof MappingNode) {
/* 197 */         valueNode.setType(object.getClass());
/* 198 */         constructJavaBean2ndStep((MappingNode)valueNode, object);
/*     */       } else {
/*     */         
/* 201 */         CompactConstructor.this.applySequence(object, CompactConstructor.this.constructSequence((SequenceNode)valueNode));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object construct(Node node) {
/*     */       ScalarNode tmpNode;
/* 211 */       if (node instanceof MappingNode) {
/*     */         
/* 213 */         MappingNode mnode = (MappingNode)node;
/* 214 */         NodeTuple nodeTuple = mnode.getValue().iterator().next();
/* 215 */         node.setTwoStepsConstruction(true);
/* 216 */         tmpNode = (ScalarNode)nodeTuple.getKeyNode();
/*     */       } else {
/*     */         
/* 219 */         tmpNode = (ScalarNode)node;
/*     */       } 
/*     */       
/* 222 */       CompactData data = CompactConstructor.this.getCompactData(tmpNode.getValue());
/* 223 */       if (data == null) {
/* 224 */         return CompactConstructor.this.constructScalar(tmpNode);
/*     */       }
/* 226 */       return CompactConstructor.this.constructCompactFormat(tmpNode, data);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applySequence(Object bean, List<?> value) {
/*     */     try {
/* 233 */       Property property = getPropertyUtils().getProperty(bean.getClass(), getSequencePropertyName(bean.getClass()));
/* 234 */       property.set(bean, value);
/* 235 */     } catch (Exception e) {
/* 236 */       throw new YAMLException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getSequencePropertyName(Class<?> bean) {
/* 248 */     Set<Property> properties = getPropertyUtils().getProperties(bean);
/* 249 */     for (Iterator<Property> iterator = properties.iterator(); iterator.hasNext(); ) {
/* 250 */       Property property = iterator.next();
/* 251 */       if (!List.class.isAssignableFrom(property.getType())) {
/* 252 */         iterator.remove();
/*     */       }
/*     */     } 
/* 255 */     if (properties.size() == 0)
/* 256 */       throw new YAMLException("No list property found in " + bean); 
/* 257 */     if (properties.size() > 1) {
/* 258 */       throw new YAMLException("Many list properties found in " + bean + "; Please override getSequencePropertyName() to specify which property to use.");
/*     */     }
/*     */     
/* 261 */     return ((Property)properties.iterator().next()).getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\extensions\compactnotation\CompactConstructor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */