/*      */ package org.yaml.snakeyaml.emitter;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.yaml.snakeyaml.DumperOptions;
/*      */ import org.yaml.snakeyaml.comments.CommentEventsCollector;
/*      */ import org.yaml.snakeyaml.comments.CommentLine;
/*      */ import org.yaml.snakeyaml.comments.CommentType;
/*      */ import org.yaml.snakeyaml.error.YAMLException;
/*      */ import org.yaml.snakeyaml.events.CollectionStartEvent;
/*      */ import org.yaml.snakeyaml.events.DocumentEndEvent;
/*      */ import org.yaml.snakeyaml.events.DocumentStartEvent;
/*      */ import org.yaml.snakeyaml.events.Event;
/*      */ import org.yaml.snakeyaml.events.MappingStartEvent;
/*      */ import org.yaml.snakeyaml.events.NodeEvent;
/*      */ import org.yaml.snakeyaml.events.ScalarEvent;
/*      */ import org.yaml.snakeyaml.events.SequenceStartEvent;
/*      */ import org.yaml.snakeyaml.reader.StreamReader;
/*      */ import org.yaml.snakeyaml.scanner.Constant;
/*      */ import org.yaml.snakeyaml.util.ArrayStack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Emitter
/*      */   implements Emitable
/*      */ {
/*      */   public static final int MIN_INDENT = 1;
/*      */   public static final int MAX_INDENT = 10;
/*   78 */   private static final char[] SPACE = new char[] { ' ' };
/*      */   
/*   80 */   private static final Pattern SPACES_PATTERN = Pattern.compile("\\s");
/*   81 */   private static final Set<Character> INVALID_ANCHOR = new HashSet<>();
/*      */   
/*      */   static {
/*   84 */     INVALID_ANCHOR.add(Character.valueOf('['));
/*   85 */     INVALID_ANCHOR.add(Character.valueOf(']'));
/*   86 */     INVALID_ANCHOR.add(Character.valueOf('{'));
/*   87 */     INVALID_ANCHOR.add(Character.valueOf('}'));
/*   88 */     INVALID_ANCHOR.add(Character.valueOf(','));
/*   89 */     INVALID_ANCHOR.add(Character.valueOf('*'));
/*   90 */     INVALID_ANCHOR.add(Character.valueOf('&'));
/*      */   }
/*      */   
/*   93 */   private static final Map<Character, String> ESCAPE_REPLACEMENTS = new HashMap<>();
/*      */ 
/*      */   
/*      */   static {
/*   97 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(false), "0");
/*   98 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\007'), "a");
/*   99 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\b'), "b");
/*  100 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\t'), "t");
/*  101 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\n'), "n");
/*  102 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\013'), "v");
/*  103 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\f'), "f");
/*  104 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\r'), "r");
/*  105 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\033'), "e");
/*  106 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('"'), "\"");
/*  107 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\\'), "\\");
/*  108 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(''), "N");
/*  109 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(' '), "_");
/*  110 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(' '), "L");
/*  111 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(' '), "P");
/*      */   }
/*      */   
/*  114 */   private static final Map<String, String> DEFAULT_TAG_PREFIXES = new LinkedHashMap<>();
/*      */   private final Writer stream;
/*      */   
/*      */   static {
/*  118 */     DEFAULT_TAG_PREFIXES.put("!", "!");
/*  119 */     DEFAULT_TAG_PREFIXES.put("tag:yaml.org,2002:", "!!");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final ArrayStack<EmitterState> states;
/*      */ 
/*      */   
/*      */   private EmitterState state;
/*      */ 
/*      */   
/*      */   private final Queue<Event> events;
/*      */ 
/*      */   
/*      */   private Event event;
/*      */ 
/*      */   
/*      */   private final ArrayStack<Integer> indents;
/*      */ 
/*      */   
/*      */   private Integer indent;
/*      */ 
/*      */   
/*      */   private int flowLevel;
/*      */ 
/*      */   
/*      */   private boolean rootContext;
/*      */ 
/*      */   
/*      */   private boolean mappingContext;
/*      */ 
/*      */   
/*      */   private boolean simpleKeyContext;
/*      */   
/*      */   private int column;
/*      */   
/*      */   private boolean whitespace;
/*      */   
/*      */   private boolean indention;
/*      */   
/*      */   private boolean openEnded;
/*      */   
/*      */   private final Boolean canonical;
/*      */   
/*      */   private final Boolean prettyFlow;
/*      */   
/*      */   private final boolean allowUnicode;
/*      */   
/*      */   private int bestIndent;
/*      */   
/*      */   private final int indicatorIndent;
/*      */   
/*      */   private final boolean indentWithIndicator;
/*      */   
/*      */   private int bestWidth;
/*      */   
/*      */   private final char[] bestLineBreak;
/*      */   
/*      */   private final boolean splitLines;
/*      */   
/*      */   private final int maxSimpleKeyLength;
/*      */   
/*      */   private final boolean emitComments;
/*      */   
/*      */   private Map<String, String> tagPrefixes;
/*      */   
/*      */   private String preparedAnchor;
/*      */   
/*      */   private String preparedTag;
/*      */   
/*      */   private ScalarAnalysis analysis;
/*      */   
/*      */   private DumperOptions.ScalarStyle style;
/*      */   
/*      */   private final CommentEventsCollector blockCommentsCollector;
/*      */   
/*      */   private final CommentEventsCollector inlineCommentsCollector;
/*      */ 
/*      */   
/*      */   public Emitter(Writer stream, DumperOptions opts) {
/*  199 */     if (stream == null) {
/*  200 */       throw new NullPointerException("Writer must be provided.");
/*      */     }
/*  202 */     if (opts == null) {
/*  203 */       throw new NullPointerException("DumperOptions must be provided.");
/*      */     }
/*      */     
/*  206 */     this.stream = stream;
/*      */     
/*  208 */     this.states = new ArrayStack(100);
/*  209 */     this.state = new ExpectStreamStart();
/*      */     
/*  211 */     this.events = new ArrayDeque<>(100);
/*  212 */     this.event = null;
/*      */     
/*  214 */     this.indents = new ArrayStack(10);
/*  215 */     this.indent = null;
/*      */     
/*  217 */     this.flowLevel = 0;
/*      */     
/*  219 */     this.mappingContext = false;
/*  220 */     this.simpleKeyContext = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  228 */     this.column = 0;
/*  229 */     this.whitespace = true;
/*  230 */     this.indention = true;
/*      */ 
/*      */     
/*  233 */     this.openEnded = false;
/*      */ 
/*      */     
/*  236 */     this.canonical = Boolean.valueOf(opts.isCanonical());
/*  237 */     this.prettyFlow = Boolean.valueOf(opts.isPrettyFlow());
/*  238 */     this.allowUnicode = opts.isAllowUnicode();
/*  239 */     this.bestIndent = 2;
/*  240 */     if (opts.getIndent() > 1 && opts.getIndent() < 10) {
/*  241 */       this.bestIndent = opts.getIndent();
/*      */     }
/*  243 */     this.indicatorIndent = opts.getIndicatorIndent();
/*  244 */     this.indentWithIndicator = opts.getIndentWithIndicator();
/*  245 */     this.bestWidth = 80;
/*  246 */     if (opts.getWidth() > this.bestIndent * 2) {
/*  247 */       this.bestWidth = opts.getWidth();
/*      */     }
/*  249 */     this.bestLineBreak = opts.getLineBreak().getString().toCharArray();
/*  250 */     this.splitLines = opts.getSplitLines();
/*  251 */     this.maxSimpleKeyLength = opts.getMaxSimpleKeyLength();
/*  252 */     this.emitComments = opts.isProcessComments();
/*      */ 
/*      */     
/*  255 */     this.tagPrefixes = new LinkedHashMap<>();
/*      */ 
/*      */     
/*  258 */     this.preparedAnchor = null;
/*  259 */     this.preparedTag = null;
/*      */ 
/*      */     
/*  262 */     this.analysis = null;
/*  263 */     this.style = null;
/*      */ 
/*      */     
/*  266 */     this.blockCommentsCollector = new CommentEventsCollector(this.events, new CommentType[] { CommentType.BLANK_LINE, CommentType.BLOCK });
/*      */     
/*  268 */     this.inlineCommentsCollector = new CommentEventsCollector(this.events, new CommentType[] { CommentType.IN_LINE });
/*      */   }
/*      */   
/*      */   public void emit(Event event) throws IOException {
/*  272 */     this.events.add(event);
/*  273 */     while (!needMoreEvents()) {
/*  274 */       this.event = this.events.poll();
/*  275 */       this.state.expect();
/*  276 */       this.event = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needMoreEvents() {
/*  283 */     if (this.events.isEmpty()) {
/*  284 */       return true;
/*      */     }
/*      */     
/*  287 */     Iterator<Event> iter = this.events.iterator();
/*  288 */     Event event = iter.next();
/*  289 */     while (event instanceof org.yaml.snakeyaml.events.CommentEvent) {
/*  290 */       if (!iter.hasNext()) {
/*  291 */         return true;
/*      */       }
/*  293 */       event = iter.next();
/*      */     } 
/*      */     
/*  296 */     if (event instanceof DocumentStartEvent)
/*  297 */       return needEvents(iter, 1); 
/*  298 */     if (event instanceof SequenceStartEvent)
/*  299 */       return needEvents(iter, 2); 
/*  300 */     if (event instanceof MappingStartEvent)
/*  301 */       return needEvents(iter, 3); 
/*  302 */     if (event instanceof org.yaml.snakeyaml.events.StreamStartEvent)
/*  303 */       return needEvents(iter, 2); 
/*  304 */     if (event instanceof org.yaml.snakeyaml.events.StreamEndEvent)
/*  305 */       return false; 
/*  306 */     if (this.emitComments) {
/*  307 */       return needEvents(iter, 1);
/*      */     }
/*  309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean needEvents(Iterator<Event> iter, int count) {
/*  313 */     int level = 0;
/*  314 */     int actualCount = 0;
/*  315 */     while (iter.hasNext()) {
/*  316 */       Event event = iter.next();
/*  317 */       if (event instanceof org.yaml.snakeyaml.events.CommentEvent) {
/*      */         continue;
/*      */       }
/*  320 */       actualCount++;
/*  321 */       if (event instanceof DocumentStartEvent || event instanceof CollectionStartEvent) {
/*  322 */         level++;
/*  323 */       } else if (event instanceof DocumentEndEvent || event instanceof org.yaml.snakeyaml.events.CollectionEndEvent) {
/*  324 */         level--;
/*  325 */       } else if (event instanceof org.yaml.snakeyaml.events.StreamEndEvent) {
/*  326 */         level = -1;
/*      */       } 
/*  328 */       if (level < 0) {
/*  329 */         return false;
/*      */       }
/*      */     } 
/*  332 */     return (actualCount < count);
/*      */   }
/*      */   
/*      */   private void increaseIndent(boolean flow, boolean indentless) {
/*  336 */     this.indents.push(this.indent);
/*  337 */     if (this.indent == null) {
/*  338 */       if (flow) {
/*  339 */         this.indent = Integer.valueOf(this.bestIndent);
/*      */       } else {
/*  341 */         this.indent = Integer.valueOf(0);
/*      */       } 
/*  343 */     } else if (!indentless) {
/*  344 */       this.indent = Integer.valueOf(this.indent.intValue() + this.bestIndent);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private class ExpectStreamStart
/*      */     implements EmitterState
/*      */   {
/*      */     private ExpectStreamStart() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  355 */       if (Emitter.this.event instanceof org.yaml.snakeyaml.events.StreamStartEvent) {
/*  356 */         Emitter.this.writeStreamStart();
/*  357 */         Emitter.this.state = new Emitter.ExpectFirstDocumentStart();
/*      */       } else {
/*  359 */         throw new EmitterException("expected StreamStartEvent, but got " + Emitter.this.event);
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class ExpectNothing implements EmitterState {
/*      */     private ExpectNothing() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  367 */       throw new EmitterException("expecting nothing, but got " + Emitter.this.event);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectFirstDocumentStart
/*      */     implements EmitterState {
/*      */     private ExpectFirstDocumentStart() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  376 */       (new Emitter.ExpectDocumentStart(true)).expect();
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectDocumentStart
/*      */     implements EmitterState {
/*      */     private final boolean first;
/*      */     
/*      */     public ExpectDocumentStart(boolean first) {
/*  385 */       this.first = first;
/*      */     }
/*      */     
/*      */     public void expect() throws IOException {
/*  389 */       if (Emitter.this.event instanceof DocumentStartEvent) {
/*  390 */         DocumentStartEvent ev = (DocumentStartEvent)Emitter.this.event;
/*  391 */         if ((ev.getVersion() != null || ev.getTags() != null) && Emitter.this.openEnded) {
/*  392 */           Emitter.this.writeIndicator("...", true, false, false);
/*  393 */           Emitter.this.writeIndent();
/*      */         } 
/*  395 */         if (ev.getVersion() != null) {
/*  396 */           String versionText = Emitter.this.prepareVersion(ev.getVersion());
/*  397 */           Emitter.this.writeVersionDirective(versionText);
/*      */         } 
/*  399 */         Emitter.this.tagPrefixes = (Map)new LinkedHashMap<>(Emitter.DEFAULT_TAG_PREFIXES);
/*  400 */         if (ev.getTags() != null) {
/*  401 */           Set<String> handles = new TreeSet<>(ev.getTags().keySet());
/*  402 */           for (String handle : handles) {
/*  403 */             String prefix = (String)ev.getTags().get(handle);
/*  404 */             Emitter.this.tagPrefixes.put(prefix, handle);
/*  405 */             String handleText = Emitter.this.prepareTagHandle(handle);
/*  406 */             String prefixText = Emitter.this.prepareTagPrefix(prefix);
/*  407 */             Emitter.this.writeTagDirective(handleText, prefixText);
/*      */           } 
/*      */         } 
/*      */         
/*  411 */         boolean implicit = (this.first && !ev.getExplicit() && !Emitter.this.canonical.booleanValue() && ev.getVersion() == null && (ev.getTags() == null || ev.getTags().isEmpty()) && !Emitter.this.checkEmptyDocument());
/*  412 */         if (!implicit) {
/*  413 */           Emitter.this.writeIndent();
/*  414 */           Emitter.this.writeIndicator("---", true, false, false);
/*  415 */           if (Emitter.this.canonical.booleanValue()) {
/*  416 */             Emitter.this.writeIndent();
/*      */           }
/*      */         } 
/*  419 */         Emitter.this.state = new Emitter.ExpectDocumentRoot();
/*  420 */       } else if (Emitter.this.event instanceof org.yaml.snakeyaml.events.StreamEndEvent) {
/*  421 */         Emitter.this.writeStreamEnd();
/*  422 */         Emitter.this.state = new Emitter.ExpectNothing();
/*  423 */       } else if (Emitter.this.event instanceof org.yaml.snakeyaml.events.CommentEvent) {
/*  424 */         Emitter.this.blockCommentsCollector.collectEvents(Emitter.this.event);
/*  425 */         Emitter.this.writeBlockComment();
/*      */       } else {
/*      */         
/*  428 */         throw new EmitterException("expected DocumentStartEvent, but got " + Emitter.this.event);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectDocumentEnd
/*      */     implements EmitterState {
/*      */     public void expect() throws IOException {
/*  436 */       Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  437 */       Emitter.this.writeBlockComment();
/*  438 */       if (Emitter.this.event instanceof DocumentEndEvent) {
/*  439 */         Emitter.this.writeIndent();
/*  440 */         if (((DocumentEndEvent)Emitter.this.event).getExplicit()) {
/*  441 */           Emitter.this.writeIndicator("...", true, false, false);
/*  442 */           Emitter.this.writeIndent();
/*      */         } 
/*  444 */         Emitter.this.flushStream();
/*  445 */         Emitter.this.state = new Emitter.ExpectDocumentStart(false);
/*      */       } else {
/*  447 */         throw new EmitterException("expected DocumentEndEvent, but got " + Emitter.this.event);
/*      */       } 
/*      */     }
/*      */     private ExpectDocumentEnd() {} }
/*      */   
/*      */   private class ExpectDocumentRoot implements EmitterState { private ExpectDocumentRoot() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  455 */       Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  456 */       if (!Emitter.this.blockCommentsCollector.isEmpty()) {
/*  457 */         Emitter.this.writeBlockComment();
/*  458 */         if (Emitter.this.event instanceof DocumentEndEvent) {
/*  459 */           (new Emitter.ExpectDocumentEnd()).expect();
/*      */           return;
/*      */         } 
/*      */       } 
/*  463 */       Emitter.this.states.push(new Emitter.ExpectDocumentEnd());
/*  464 */       Emitter.this.expectNode(true, false, false);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void expectNode(boolean root, boolean mapping, boolean simpleKey) throws IOException {
/*  471 */     this.rootContext = root;
/*  472 */     this.mappingContext = mapping;
/*  473 */     this.simpleKeyContext = simpleKey;
/*  474 */     if (this.event instanceof org.yaml.snakeyaml.events.AliasEvent) {
/*  475 */       expectAlias();
/*  476 */     } else if (this.event instanceof ScalarEvent || this.event instanceof CollectionStartEvent) {
/*  477 */       processAnchor("&");
/*  478 */       processTag();
/*  479 */       if (this.event instanceof ScalarEvent) {
/*  480 */         expectScalar();
/*  481 */       } else if (this.event instanceof SequenceStartEvent) {
/*  482 */         if (this.flowLevel != 0 || this.canonical.booleanValue() || ((SequenceStartEvent)this.event).isFlow() || 
/*  483 */           checkEmptySequence()) {
/*  484 */           expectFlowSequence();
/*      */         } else {
/*  486 */           expectBlockSequence();
/*      */         }
/*      */       
/*  489 */       } else if (this.flowLevel != 0 || this.canonical.booleanValue() || ((MappingStartEvent)this.event).isFlow() || 
/*  490 */         checkEmptyMapping()) {
/*  491 */         expectFlowMapping();
/*      */       } else {
/*  493 */         expectBlockMapping();
/*      */       } 
/*      */     } else {
/*      */       
/*  497 */       throw new EmitterException("expected NodeEvent, but got " + this.event);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void expectAlias() throws IOException {
/*  502 */     if (!(this.event instanceof org.yaml.snakeyaml.events.AliasEvent)) {
/*  503 */       throw new EmitterException("Alias must be provided");
/*      */     }
/*  505 */     processAnchor("*");
/*  506 */     this.state = (EmitterState)this.states.pop();
/*      */   }
/*      */   
/*      */   private void expectScalar() throws IOException {
/*  510 */     increaseIndent(true, false);
/*  511 */     processScalar();
/*  512 */     this.indent = (Integer)this.indents.pop();
/*  513 */     this.state = (EmitterState)this.states.pop();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void expectFlowSequence() throws IOException {
/*  519 */     writeIndicator("[", true, true, false);
/*  520 */     this.flowLevel++;
/*  521 */     increaseIndent(true, false);
/*  522 */     if (this.prettyFlow.booleanValue()) {
/*  523 */       writeIndent();
/*      */     }
/*  525 */     this.state = new ExpectFirstFlowSequenceItem();
/*      */   }
/*      */   
/*      */   private class ExpectFirstFlowSequenceItem implements EmitterState { private ExpectFirstFlowSequenceItem() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  531 */       if (Emitter.this.event instanceof org.yaml.snakeyaml.events.SequenceEndEvent) {
/*  532 */         Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*  533 */         Emitter.this.flowLevel--;
/*  534 */         Emitter.this.writeIndicator("]", false, false, false);
/*  535 */         Emitter.this.inlineCommentsCollector.collectEvents();
/*  536 */         Emitter.this.writeInlineComments();
/*  537 */         Emitter.this.state = (EmitterState)Emitter.this.states.pop();
/*  538 */       } else if (Emitter.this.event instanceof org.yaml.snakeyaml.events.CommentEvent) {
/*  539 */         Emitter.this.blockCommentsCollector.collectEvents(Emitter.this.event);
/*  540 */         Emitter.this.writeBlockComment();
/*      */       } else {
/*  542 */         if (Emitter.this.canonical.booleanValue() || (Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines) || Emitter.this.prettyFlow.booleanValue()) {
/*  543 */           Emitter.this.writeIndent();
/*      */         }
/*  545 */         Emitter.this.states.push(new Emitter.ExpectFlowSequenceItem());
/*  546 */         Emitter.this.expectNode(false, false, false);
/*  547 */         Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
/*  548 */         Emitter.this.writeInlineComments();
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class ExpectFlowSequenceItem implements EmitterState {
/*      */     private ExpectFlowSequenceItem() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  556 */       if (Emitter.this.event instanceof org.yaml.snakeyaml.events.SequenceEndEvent) {
/*  557 */         Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*  558 */         Emitter.this.flowLevel--;
/*  559 */         if (Emitter.this.canonical.booleanValue()) {
/*  560 */           Emitter.this.writeIndicator(",", false, false, false);
/*  561 */           Emitter.this.writeIndent();
/*  562 */         } else if (Emitter.this.prettyFlow.booleanValue()) {
/*  563 */           Emitter.this.writeIndent();
/*      */         } 
/*  565 */         Emitter.this.writeIndicator("]", false, false, false);
/*  566 */         Emitter.this.inlineCommentsCollector.collectEvents();
/*  567 */         Emitter.this.writeInlineComments();
/*  568 */         if (Emitter.this.prettyFlow.booleanValue()) {
/*  569 */           Emitter.this.writeIndent();
/*      */         }
/*  571 */         Emitter.this.state = (EmitterState)Emitter.this.states.pop();
/*  572 */       } else if (Emitter.this.event instanceof org.yaml.snakeyaml.events.CommentEvent) {
/*  573 */         Emitter.this.event = Emitter.this.blockCommentsCollector.collectEvents(Emitter.this.event);
/*      */       } else {
/*  575 */         Emitter.this.writeIndicator(",", false, false, false);
/*  576 */         Emitter.this.writeBlockComment();
/*  577 */         if (Emitter.this.canonical.booleanValue() || (Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines) || Emitter.this.prettyFlow.booleanValue()) {
/*  578 */           Emitter.this.writeIndent();
/*      */         }
/*  580 */         Emitter.this.states.push(new ExpectFlowSequenceItem());
/*  581 */         Emitter.this.expectNode(false, false, false);
/*  582 */         Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
/*  583 */         Emitter.this.writeInlineComments();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void expectFlowMapping() throws IOException {
/*  591 */     writeIndicator("{", true, true, false);
/*  592 */     this.flowLevel++;
/*  593 */     increaseIndent(true, false);
/*  594 */     if (this.prettyFlow.booleanValue()) {
/*  595 */       writeIndent();
/*      */     }
/*  597 */     this.state = new ExpectFirstFlowMappingKey();
/*      */   }
/*      */   
/*      */   private class ExpectFirstFlowMappingKey implements EmitterState { private ExpectFirstFlowMappingKey() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  603 */       Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  604 */       Emitter.this.writeBlockComment();
/*  605 */       if (Emitter.this.event instanceof org.yaml.snakeyaml.events.MappingEndEvent) {
/*  606 */         Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*  607 */         Emitter.this.flowLevel--;
/*  608 */         Emitter.this.writeIndicator("}", false, false, false);
/*  609 */         Emitter.this.inlineCommentsCollector.collectEvents();
/*  610 */         Emitter.this.writeInlineComments();
/*  611 */         Emitter.this.state = (EmitterState)Emitter.this.states.pop();
/*      */       } else {
/*  613 */         if (Emitter.this.canonical.booleanValue() || (Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines) || Emitter.this.prettyFlow.booleanValue()) {
/*  614 */           Emitter.this.writeIndent();
/*      */         }
/*  616 */         if (!Emitter.this.canonical.booleanValue() && Emitter.this.checkSimpleKey()) {
/*  617 */           Emitter.this.states.push(new Emitter.ExpectFlowMappingSimpleValue());
/*  618 */           Emitter.this.expectNode(false, true, true);
/*      */         } else {
/*  620 */           Emitter.this.writeIndicator("?", true, false, false);
/*  621 */           Emitter.this.states.push(new Emitter.ExpectFlowMappingValue());
/*  622 */           Emitter.this.expectNode(false, true, false);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class ExpectFlowMappingKey implements EmitterState {
/*      */     private ExpectFlowMappingKey() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  631 */       if (Emitter.this.event instanceof org.yaml.snakeyaml.events.MappingEndEvent) {
/*  632 */         Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*  633 */         Emitter.this.flowLevel--;
/*  634 */         if (Emitter.this.canonical.booleanValue()) {
/*  635 */           Emitter.this.writeIndicator(",", false, false, false);
/*  636 */           Emitter.this.writeIndent();
/*      */         } 
/*  638 */         if (Emitter.this.prettyFlow.booleanValue()) {
/*  639 */           Emitter.this.writeIndent();
/*      */         }
/*  641 */         Emitter.this.writeIndicator("}", false, false, false);
/*  642 */         Emitter.this.inlineCommentsCollector.collectEvents();
/*  643 */         Emitter.this.writeInlineComments();
/*  644 */         Emitter.this.state = (EmitterState)Emitter.this.states.pop();
/*      */       } else {
/*  646 */         Emitter.this.writeIndicator(",", false, false, false);
/*  647 */         Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  648 */         Emitter.this.writeBlockComment();
/*  649 */         if (Emitter.this.canonical.booleanValue() || (Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines) || Emitter.this.prettyFlow.booleanValue()) {
/*  650 */           Emitter.this.writeIndent();
/*      */         }
/*  652 */         if (!Emitter.this.canonical.booleanValue() && Emitter.this.checkSimpleKey()) {
/*  653 */           Emitter.this.states.push(new Emitter.ExpectFlowMappingSimpleValue());
/*  654 */           Emitter.this.expectNode(false, true, true);
/*      */         } else {
/*  656 */           Emitter.this.writeIndicator("?", true, false, false);
/*  657 */           Emitter.this.states.push(new Emitter.ExpectFlowMappingValue());
/*  658 */           Emitter.this.expectNode(false, true, false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectFlowMappingSimpleValue implements EmitterState { private ExpectFlowMappingSimpleValue() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  667 */       Emitter.this.writeIndicator(":", false, false, false);
/*  668 */       Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  669 */       Emitter.this.writeInlineComments();
/*  670 */       Emitter.this.states.push(new Emitter.ExpectFlowMappingKey());
/*  671 */       Emitter.this.expectNode(false, true, false);
/*  672 */       Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
/*  673 */       Emitter.this.writeInlineComments();
/*      */     } }
/*      */   
/*      */   private class ExpectFlowMappingValue implements EmitterState {
/*      */     private ExpectFlowMappingValue() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  680 */       if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth || Emitter.this.prettyFlow.booleanValue()) {
/*  681 */         Emitter.this.writeIndent();
/*      */       }
/*  683 */       Emitter.this.writeIndicator(":", true, false, false);
/*  684 */       Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  685 */       Emitter.this.writeInlineComments();
/*  686 */       Emitter.this.states.push(new Emitter.ExpectFlowMappingKey());
/*  687 */       Emitter.this.expectNode(false, true, false);
/*  688 */       Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
/*  689 */       Emitter.this.writeInlineComments();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void expectBlockSequence() throws IOException {
/*  696 */     boolean indentless = (this.mappingContext && !this.indention);
/*  697 */     increaseIndent(false, indentless);
/*  698 */     this.state = new ExpectFirstBlockSequenceItem();
/*      */   }
/*      */   
/*      */   private class ExpectFirstBlockSequenceItem implements EmitterState { private ExpectFirstBlockSequenceItem() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  704 */       (new Emitter.ExpectBlockSequenceItem(true)).expect();
/*      */     } }
/*      */ 
/*      */   
/*      */   private class ExpectBlockSequenceItem
/*      */     implements EmitterState {
/*      */     private final boolean first;
/*      */     
/*      */     public ExpectBlockSequenceItem(boolean first) {
/*  713 */       this.first = first;
/*      */     }
/*      */     
/*      */     public void expect() throws IOException {
/*  717 */       if (!this.first && Emitter.this.event instanceof org.yaml.snakeyaml.events.SequenceEndEvent) {
/*  718 */         Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*  719 */         Emitter.this.state = (EmitterState)Emitter.this.states.pop();
/*  720 */       } else if (Emitter.this.event instanceof org.yaml.snakeyaml.events.CommentEvent) {
/*  721 */         Emitter.this.blockCommentsCollector.collectEvents(Emitter.this.event);
/*      */       } else {
/*  723 */         Emitter.this.writeIndent();
/*  724 */         if (!Emitter.this.indentWithIndicator || this.first) {
/*  725 */           Emitter.this.writeWhitespace(Emitter.this.indicatorIndent);
/*      */         }
/*  727 */         Emitter.this.writeIndicator("-", true, false, true);
/*  728 */         if (Emitter.this.indentWithIndicator && this.first) {
/*  729 */           Emitter.this.indent = Integer.valueOf(Emitter.this.indent.intValue() + Emitter.this.indicatorIndent);
/*      */         }
/*  731 */         if (!Emitter.this.blockCommentsCollector.isEmpty()) {
/*  732 */           Emitter.this.increaseIndent(false, false);
/*  733 */           Emitter.this.writeBlockComment();
/*  734 */           if (Emitter.this.event instanceof ScalarEvent) {
/*  735 */             Emitter.this.analysis = Emitter.this.analyzeScalar(((ScalarEvent)Emitter.this.event).getValue());
/*  736 */             if (!Emitter.this.analysis.isEmpty()) {
/*  737 */               Emitter.this.writeIndent();
/*      */             }
/*      */           } 
/*  740 */           Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*      */         } 
/*  742 */         Emitter.this.states.push(new ExpectBlockSequenceItem(false));
/*  743 */         Emitter.this.expectNode(false, false, false);
/*  744 */         Emitter.this.inlineCommentsCollector.collectEvents();
/*  745 */         Emitter.this.writeInlineComments();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void expectBlockMapping() throws IOException {
/*  752 */     increaseIndent(false, false);
/*  753 */     this.state = new ExpectFirstBlockMappingKey();
/*      */   }
/*      */   
/*      */   private class ExpectFirstBlockMappingKey implements EmitterState { private ExpectFirstBlockMappingKey() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  759 */       (new Emitter.ExpectBlockMappingKey(true)).expect();
/*      */     } }
/*      */ 
/*      */   
/*      */   private class ExpectBlockMappingKey
/*      */     implements EmitterState {
/*      */     private final boolean first;
/*      */     
/*      */     public ExpectBlockMappingKey(boolean first) {
/*  768 */       this.first = first;
/*      */     }
/*      */     
/*      */     public void expect() throws IOException {
/*  772 */       Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  773 */       Emitter.this.writeBlockComment();
/*  774 */       if (!this.first && Emitter.this.event instanceof org.yaml.snakeyaml.events.MappingEndEvent) {
/*  775 */         Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*  776 */         Emitter.this.state = (EmitterState)Emitter.this.states.pop();
/*      */       } else {
/*  778 */         Emitter.this.writeIndent();
/*  779 */         if (Emitter.this.checkSimpleKey()) {
/*  780 */           Emitter.this.states.push(new Emitter.ExpectBlockMappingSimpleValue());
/*  781 */           Emitter.this.expectNode(false, true, true);
/*      */         } else {
/*  783 */           Emitter.this.writeIndicator("?", true, false, true);
/*  784 */           Emitter.this.states.push(new Emitter.ExpectBlockMappingValue());
/*  785 */           Emitter.this.expectNode(false, true, false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isFoldedOrLiteral(Event event) {
/*  792 */     if (!event.is(Event.ID.Scalar)) {
/*  793 */       return false;
/*      */     }
/*  795 */     ScalarEvent scalarEvent = (ScalarEvent)event;
/*  796 */     DumperOptions.ScalarStyle style = scalarEvent.getScalarStyle();
/*  797 */     return (style == DumperOptions.ScalarStyle.FOLDED || style == DumperOptions.ScalarStyle.LITERAL);
/*      */   }
/*      */   
/*      */   private class ExpectBlockMappingSimpleValue implements EmitterState { private ExpectBlockMappingSimpleValue() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  803 */       Emitter.this.writeIndicator(":", false, false, false);
/*  804 */       Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  805 */       if (!Emitter.this.isFoldedOrLiteral(Emitter.this.event) && 
/*  806 */         Emitter.this.writeInlineComments()) {
/*  807 */         Emitter.this.increaseIndent(true, false);
/*  808 */         Emitter.this.writeIndent();
/*  809 */         Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*      */       } 
/*      */       
/*  812 */       Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  813 */       if (!Emitter.this.blockCommentsCollector.isEmpty()) {
/*  814 */         Emitter.this.increaseIndent(true, false);
/*  815 */         Emitter.this.writeBlockComment();
/*  816 */         Emitter.this.writeIndent();
/*  817 */         Emitter.this.indent = (Integer)Emitter.this.indents.pop();
/*      */       } 
/*  819 */       Emitter.this.states.push(new Emitter.ExpectBlockMappingKey(false));
/*  820 */       Emitter.this.expectNode(false, true, false);
/*  821 */       Emitter.this.inlineCommentsCollector.collectEvents();
/*  822 */       Emitter.this.writeInlineComments();
/*      */     } }
/*      */   
/*      */   private class ExpectBlockMappingValue implements EmitterState {
/*      */     private ExpectBlockMappingValue() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  829 */       Emitter.this.writeIndent();
/*  830 */       Emitter.this.writeIndicator(":", true, false, true);
/*  831 */       Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  832 */       Emitter.this.writeInlineComments();
/*  833 */       Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
/*  834 */       Emitter.this.writeBlockComment();
/*  835 */       Emitter.this.states.push(new Emitter.ExpectBlockMappingKey(false));
/*  836 */       Emitter.this.expectNode(false, true, false);
/*  837 */       Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
/*  838 */       Emitter.this.writeInlineComments();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkEmptySequence() {
/*  845 */     return (this.event instanceof SequenceStartEvent && !this.events.isEmpty() && this.events
/*  846 */       .peek() instanceof org.yaml.snakeyaml.events.SequenceEndEvent);
/*      */   }
/*      */   
/*      */   private boolean checkEmptyMapping() {
/*  850 */     return (this.event instanceof MappingStartEvent && !this.events.isEmpty() && this.events
/*  851 */       .peek() instanceof org.yaml.snakeyaml.events.MappingEndEvent);
/*      */   }
/*      */   
/*      */   private boolean checkEmptyDocument() {
/*  855 */     if (!(this.event instanceof DocumentStartEvent) || this.events.isEmpty()) {
/*  856 */       return false;
/*      */     }
/*  858 */     Event event = this.events.peek();
/*  859 */     if (event instanceof ScalarEvent) {
/*  860 */       ScalarEvent e = (ScalarEvent)event;
/*  861 */       return (e.getAnchor() == null && e.getTag() == null && e.getImplicit() != null && e
/*  862 */         .getValue().length() == 0);
/*      */     } 
/*  864 */     return false;
/*      */   }
/*      */   
/*      */   private boolean checkSimpleKey() {
/*  868 */     int length = 0;
/*  869 */     if (this.event instanceof NodeEvent && ((NodeEvent)this.event).getAnchor() != null) {
/*  870 */       if (this.preparedAnchor == null) {
/*  871 */         this.preparedAnchor = prepareAnchor(((NodeEvent)this.event).getAnchor());
/*      */       }
/*  873 */       length += this.preparedAnchor.length();
/*      */     } 
/*  875 */     String tag = null;
/*  876 */     if (this.event instanceof ScalarEvent) {
/*  877 */       tag = ((ScalarEvent)this.event).getTag();
/*  878 */     } else if (this.event instanceof CollectionStartEvent) {
/*  879 */       tag = ((CollectionStartEvent)this.event).getTag();
/*      */     } 
/*  881 */     if (tag != null) {
/*  882 */       if (this.preparedTag == null) {
/*  883 */         this.preparedTag = prepareTag(tag);
/*      */       }
/*  885 */       length += this.preparedTag.length();
/*      */     } 
/*  887 */     if (this.event instanceof ScalarEvent) {
/*  888 */       if (this.analysis == null) {
/*  889 */         this.analysis = analyzeScalar(((ScalarEvent)this.event).getValue());
/*      */       }
/*  891 */       length += this.analysis.getScalar().length();
/*      */     } 
/*  893 */     return (length < this.maxSimpleKeyLength && (this.event instanceof org.yaml.snakeyaml.events.AliasEvent || (this.event instanceof ScalarEvent && 
/*  894 */       !this.analysis.isEmpty() && !this.analysis.isMultiline()) || 
/*  895 */       checkEmptySequence() || checkEmptyMapping()));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void processAnchor(String indicator) throws IOException {
/*  901 */     NodeEvent ev = (NodeEvent)this.event;
/*  902 */     if (ev.getAnchor() == null) {
/*  903 */       this.preparedAnchor = null;
/*      */       return;
/*      */     } 
/*  906 */     if (this.preparedAnchor == null) {
/*  907 */       this.preparedAnchor = prepareAnchor(ev.getAnchor());
/*      */     }
/*  909 */     writeIndicator(indicator + this.preparedAnchor, true, false, false);
/*  910 */     this.preparedAnchor = null;
/*      */   }
/*      */   
/*      */   private void processTag() throws IOException {
/*  914 */     String tag = null;
/*  915 */     if (this.event instanceof ScalarEvent) {
/*  916 */       ScalarEvent ev = (ScalarEvent)this.event;
/*  917 */       tag = ev.getTag();
/*  918 */       if (this.style == null) {
/*  919 */         this.style = chooseScalarStyle();
/*      */       }
/*  921 */       if ((!this.canonical.booleanValue() || tag == null) && ((this.style == null && ev
/*  922 */         .getImplicit().canOmitTagInPlainScalar()) || (this.style != null && ev
/*  923 */         .getImplicit().canOmitTagInNonPlainScalar()))) {
/*  924 */         this.preparedTag = null;
/*      */         return;
/*      */       } 
/*  927 */       if (ev.getImplicit().canOmitTagInPlainScalar() && tag == null) {
/*  928 */         tag = "!";
/*  929 */         this.preparedTag = null;
/*      */       } 
/*      */     } else {
/*  932 */       CollectionStartEvent ev = (CollectionStartEvent)this.event;
/*  933 */       tag = ev.getTag();
/*  934 */       if ((!this.canonical.booleanValue() || tag == null) && ev.getImplicit()) {
/*  935 */         this.preparedTag = null;
/*      */         return;
/*      */       } 
/*      */     } 
/*  939 */     if (tag == null) {
/*  940 */       throw new EmitterException("tag is not specified");
/*      */     }
/*  942 */     if (this.preparedTag == null) {
/*  943 */       this.preparedTag = prepareTag(tag);
/*      */     }
/*  945 */     writeIndicator(this.preparedTag, true, false, false);
/*  946 */     this.preparedTag = null;
/*      */   }
/*      */   
/*      */   private DumperOptions.ScalarStyle chooseScalarStyle() {
/*  950 */     ScalarEvent ev = (ScalarEvent)this.event;
/*  951 */     if (this.analysis == null) {
/*  952 */       this.analysis = analyzeScalar(ev.getValue());
/*      */     }
/*  954 */     if ((!ev.isPlain() && ev.getScalarStyle() == DumperOptions.ScalarStyle.DOUBLE_QUOTED) || this.canonical
/*  955 */       .booleanValue()) {
/*  956 */       return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
/*      */     }
/*  958 */     if (ev.isPlain() && ev.getImplicit().canOmitTagInPlainScalar() && (
/*  959 */       !this.simpleKeyContext || (!this.analysis.isEmpty() && !this.analysis.isMultiline())) && ((this.flowLevel != 0 && this.analysis
/*  960 */       .isAllowFlowPlain()) || (this.flowLevel == 0 && this.analysis
/*  961 */       .isAllowBlockPlain()))) {
/*  962 */       return null;
/*      */     }
/*      */     
/*  965 */     if (!ev.isPlain() && (ev.getScalarStyle() == DumperOptions.ScalarStyle.LITERAL || ev
/*  966 */       .getScalarStyle() == DumperOptions.ScalarStyle.FOLDED) && 
/*  967 */       this.flowLevel == 0 && !this.simpleKeyContext && this.analysis.isAllowBlock()) {
/*  968 */       return ev.getScalarStyle();
/*      */     }
/*      */     
/*  971 */     if ((ev.isPlain() || ev.getScalarStyle() == DumperOptions.ScalarStyle.SINGLE_QUOTED) && 
/*  972 */       this.analysis.isAllowSingleQuoted() && (!this.simpleKeyContext || !this.analysis.isMultiline())) {
/*  973 */       return DumperOptions.ScalarStyle.SINGLE_QUOTED;
/*      */     }
/*      */     
/*  976 */     return DumperOptions.ScalarStyle.DOUBLE_QUOTED;
/*      */   }
/*      */   
/*      */   private void processScalar() throws IOException {
/*  980 */     ScalarEvent ev = (ScalarEvent)this.event;
/*  981 */     if (this.analysis == null) {
/*  982 */       this.analysis = analyzeScalar(ev.getValue());
/*      */     }
/*  984 */     if (this.style == null) {
/*  985 */       this.style = chooseScalarStyle();
/*      */     }
/*  987 */     boolean split = (!this.simpleKeyContext && this.splitLines);
/*  988 */     if (this.style == null) {
/*  989 */       writePlain(this.analysis.getScalar(), split);
/*      */     } else {
/*  991 */       switch (this.style) {
/*      */         case DOUBLE_QUOTED:
/*  993 */           writeDoubleQuoted(this.analysis.getScalar(), split);
/*      */           break;
/*      */         case SINGLE_QUOTED:
/*  996 */           writeSingleQuoted(this.analysis.getScalar(), split);
/*      */           break;
/*      */         case FOLDED:
/*  999 */           writeFolded(this.analysis.getScalar(), split);
/*      */           break;
/*      */         case LITERAL:
/* 1002 */           writeLiteral(this.analysis.getScalar());
/*      */           break;
/*      */         default:
/* 1005 */           throw new YAMLException("Unexpected style: " + this.style);
/*      */       } 
/*      */     } 
/* 1008 */     this.analysis = null;
/* 1009 */     this.style = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String prepareVersion(DumperOptions.Version version) {
/* 1015 */     if (version.major() != 1) {
/* 1016 */       throw new EmitterException("unsupported YAML version: " + version);
/*      */     }
/* 1018 */     return version.getRepresentation();
/*      */   }
/*      */   
/* 1021 */   private static final Pattern HANDLE_FORMAT = Pattern.compile("^![-_\\w]*!$");
/*      */   
/*      */   private String prepareTagHandle(String handle) {
/* 1024 */     if (handle.length() == 0)
/* 1025 */       throw new EmitterException("tag handle must not be empty"); 
/* 1026 */     if (handle.charAt(0) != '!' || handle.charAt(handle.length() - 1) != '!')
/* 1027 */       throw new EmitterException("tag handle must start and end with '!': " + handle); 
/* 1028 */     if (!"!".equals(handle) && !HANDLE_FORMAT.matcher(handle).matches()) {
/* 1029 */       throw new EmitterException("invalid character in the tag handle: " + handle);
/*      */     }
/* 1031 */     return handle;
/*      */   }
/*      */   
/*      */   private String prepareTagPrefix(String prefix) {
/* 1035 */     if (prefix.length() == 0) {
/* 1036 */       throw new EmitterException("tag prefix must not be empty");
/*      */     }
/* 1038 */     StringBuilder chunks = new StringBuilder();
/* 1039 */     int start = 0;
/* 1040 */     int end = 0;
/* 1041 */     if (prefix.charAt(0) == '!') {
/* 1042 */       end = 1;
/*      */     }
/* 1044 */     while (end < prefix.length()) {
/* 1045 */       end++;
/*      */     }
/* 1047 */     if (start < end) {
/* 1048 */       chunks.append(prefix, start, end);
/*      */     }
/* 1050 */     return chunks.toString();
/*      */   }
/*      */   
/*      */   private String prepareTag(String tag) {
/* 1054 */     if (tag.length() == 0) {
/* 1055 */       throw new EmitterException("tag must not be empty");
/*      */     }
/* 1057 */     if ("!".equals(tag)) {
/* 1058 */       return tag;
/*      */     }
/* 1060 */     String handle = null;
/* 1061 */     String suffix = tag;
/*      */     
/* 1063 */     for (String prefix : this.tagPrefixes.keySet()) {
/* 1064 */       if (tag.startsWith(prefix) && ("!".equals(prefix) || prefix.length() < tag.length())) {
/* 1065 */         handle = prefix;
/*      */       }
/*      */     } 
/* 1068 */     if (handle != null) {
/* 1069 */       suffix = tag.substring(handle.length());
/* 1070 */       handle = this.tagPrefixes.get(handle);
/*      */     } 
/*      */     
/* 1073 */     int end = suffix.length();
/* 1074 */     String suffixText = (end > 0) ? suffix.substring(0, end) : "";
/*      */     
/* 1076 */     if (handle != null) {
/* 1077 */       return handle + suffixText;
/*      */     }
/* 1079 */     return "!<" + suffixText + ">";
/*      */   }
/*      */   
/*      */   static String prepareAnchor(String anchor) {
/* 1083 */     if (anchor.length() == 0) {
/* 1084 */       throw new EmitterException("anchor must not be empty");
/*      */     }
/* 1086 */     for (Character invalid : INVALID_ANCHOR) {
/* 1087 */       if (anchor.indexOf(invalid.charValue()) > -1) {
/* 1088 */         throw new EmitterException("Invalid character '" + invalid + "' in the anchor: " + anchor);
/*      */       }
/*      */     } 
/* 1091 */     Matcher matcher = SPACES_PATTERN.matcher(anchor);
/* 1092 */     if (matcher.find()) {
/* 1093 */       throw new EmitterException("Anchor may not contain spaces: " + anchor);
/*      */     }
/* 1095 */     return anchor;
/*      */   }
/*      */   
/* 1098 */   private static final Pattern LEADING_ZERO_PATTERN = Pattern.compile("0[0-9_]+");
/*      */ 
/*      */   
/*      */   private ScalarAnalysis analyzeScalar(String scalar) {
/* 1102 */     if (scalar.length() == 0) {
/* 1103 */       return new ScalarAnalysis(scalar, true, false, false, true, true, false);
/*      */     }
/*      */     
/* 1106 */     boolean blockIndicators = false;
/* 1107 */     boolean flowIndicators = false;
/* 1108 */     boolean lineBreaks = false;
/* 1109 */     boolean specialCharacters = false;
/* 1110 */     boolean leadingZeroNumber = LEADING_ZERO_PATTERN.matcher(scalar).matches();
/*      */ 
/*      */     
/* 1113 */     boolean leadingSpace = false;
/* 1114 */     boolean leadingBreak = false;
/* 1115 */     boolean trailingSpace = false;
/* 1116 */     boolean trailingBreak = false;
/* 1117 */     boolean breakSpace = false;
/* 1118 */     boolean spaceBreak = false;
/*      */ 
/*      */     
/* 1121 */     if (scalar.startsWith("---") || scalar.startsWith("...")) {
/* 1122 */       blockIndicators = true;
/* 1123 */       flowIndicators = true;
/*      */     } 
/*      */     
/* 1126 */     boolean preceededByWhitespace = true;
/*      */     
/* 1128 */     boolean followedByWhitespace = (scalar.length() == 1 || Constant.NULL_BL_T_LINEBR.has(scalar.codePointAt(1)));
/*      */     
/* 1130 */     boolean previousSpace = false;
/*      */ 
/*      */     
/* 1133 */     boolean previousBreak = false;
/*      */     
/* 1135 */     int index = 0;
/*      */     
/* 1137 */     while (index < scalar.length()) {
/* 1138 */       int c = scalar.codePointAt(index);
/*      */       
/* 1140 */       if (index == 0) {
/*      */         
/* 1142 */         if ("#,[]{}&*!|>'\"%@`".indexOf(c) != -1) {
/* 1143 */           flowIndicators = true;
/* 1144 */           blockIndicators = true;
/*      */         } 
/* 1146 */         if (c == 63 || c == 58) {
/* 1147 */           flowIndicators = true;
/* 1148 */           if (followedByWhitespace) {
/* 1149 */             blockIndicators = true;
/*      */           }
/*      */         } 
/* 1152 */         if (c == 45 && followedByWhitespace) {
/* 1153 */           flowIndicators = true;
/* 1154 */           blockIndicators = true;
/*      */         } 
/*      */       } else {
/*      */         
/* 1158 */         if (",?[]{}".indexOf(c) != -1) {
/* 1159 */           flowIndicators = true;
/*      */         }
/* 1161 */         if (c == 58) {
/* 1162 */           flowIndicators = true;
/* 1163 */           if (followedByWhitespace) {
/* 1164 */             blockIndicators = true;
/*      */           }
/*      */         } 
/* 1167 */         if (c == 35 && preceededByWhitespace) {
/* 1168 */           flowIndicators = true;
/* 1169 */           blockIndicators = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1173 */       boolean isLineBreak = Constant.LINEBR.has(c);
/* 1174 */       if (isLineBreak) {
/* 1175 */         lineBreaks = true;
/*      */       }
/* 1177 */       if (c != 10 && (32 > c || c > 126)) {
/* 1178 */         if (c == 133 || (c >= 160 && c <= 55295) || (c >= 57344 && c <= 65533) || (c >= 65536 && c <= 1114111)) {
/*      */ 
/*      */           
/* 1181 */           if (!this.allowUnicode) {
/* 1182 */             specialCharacters = true;
/*      */           }
/*      */         } else {
/* 1185 */           specialCharacters = true;
/*      */         } 
/*      */       }
/*      */       
/* 1189 */       if (c == 32) {
/* 1190 */         if (index == 0) {
/* 1191 */           leadingSpace = true;
/*      */         }
/* 1193 */         if (index == scalar.length() - 1) {
/* 1194 */           trailingSpace = true;
/*      */         }
/* 1196 */         if (previousBreak) {
/* 1197 */           breakSpace = true;
/*      */         }
/* 1199 */         previousSpace = true;
/* 1200 */         previousBreak = false;
/* 1201 */       } else if (isLineBreak) {
/* 1202 */         if (index == 0) {
/* 1203 */           leadingBreak = true;
/*      */         }
/* 1205 */         if (index == scalar.length() - 1) {
/* 1206 */           trailingBreak = true;
/*      */         }
/* 1208 */         if (previousSpace) {
/* 1209 */           spaceBreak = true;
/*      */         }
/* 1211 */         previousSpace = false;
/* 1212 */         previousBreak = true;
/*      */       } else {
/* 1214 */         previousSpace = false;
/* 1215 */         previousBreak = false;
/*      */       } 
/*      */ 
/*      */       
/* 1219 */       index += Character.charCount(c);
/* 1220 */       preceededByWhitespace = (Constant.NULL_BL_T.has(c) || isLineBreak);
/* 1221 */       followedByWhitespace = true;
/* 1222 */       if (index + 1 < scalar.length()) {
/* 1223 */         int nextIndex = index + Character.charCount(scalar.codePointAt(index));
/* 1224 */         if (nextIndex < scalar.length())
/*      */         {
/* 1226 */           followedByWhitespace = (Constant.NULL_BL_T.has(scalar.codePointAt(nextIndex)) || isLineBreak);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1231 */     boolean allowFlowPlain = true;
/* 1232 */     boolean allowBlockPlain = true;
/* 1233 */     boolean allowSingleQuoted = true;
/* 1234 */     boolean allowBlock = true;
/*      */     
/* 1236 */     if (leadingSpace || leadingBreak || trailingSpace || trailingBreak || leadingZeroNumber) {
/* 1237 */       allowFlowPlain = allowBlockPlain = false;
/*      */     }
/*      */     
/* 1240 */     if (trailingSpace) {
/* 1241 */       allowBlock = false;
/*      */     }
/*      */ 
/*      */     
/* 1245 */     if (breakSpace) {
/* 1246 */       allowFlowPlain = allowBlockPlain = allowSingleQuoted = false;
/*      */     }
/*      */ 
/*      */     
/* 1250 */     if (spaceBreak || specialCharacters) {
/* 1251 */       allowFlowPlain = allowBlockPlain = allowSingleQuoted = allowBlock = false;
/*      */     }
/*      */ 
/*      */     
/* 1255 */     if (lineBreaks) {
/* 1256 */       allowFlowPlain = false;
/*      */     }
/*      */     
/* 1259 */     if (flowIndicators) {
/* 1260 */       allowFlowPlain = false;
/*      */     }
/*      */     
/* 1263 */     if (blockIndicators) {
/* 1264 */       allowBlockPlain = false;
/*      */     }
/*      */     
/* 1267 */     return new ScalarAnalysis(scalar, false, lineBreaks, allowFlowPlain, allowBlockPlain, allowSingleQuoted, allowBlock);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void flushStream() throws IOException {
/* 1274 */     this.stream.flush();
/*      */   }
/*      */ 
/*      */   
/*      */   void writeStreamStart() {}
/*      */ 
/*      */   
/*      */   void writeStreamEnd() throws IOException {
/* 1282 */     flushStream();
/*      */   }
/*      */ 
/*      */   
/*      */   void writeIndicator(String indicator, boolean needWhitespace, boolean whitespace, boolean indentation) throws IOException {
/* 1287 */     if (!this.whitespace && needWhitespace) {
/* 1288 */       this.column++;
/* 1289 */       this.stream.write(SPACE);
/*      */     } 
/* 1291 */     this.whitespace = whitespace;
/* 1292 */     this.indention = (this.indention && indentation);
/* 1293 */     this.column += indicator.length();
/* 1294 */     this.openEnded = false;
/* 1295 */     this.stream.write(indicator);
/*      */   }
/*      */   
/*      */   void writeIndent() throws IOException {
/*      */     int indent;
/* 1300 */     if (this.indent != null) {
/* 1301 */       indent = this.indent.intValue();
/*      */     } else {
/* 1303 */       indent = 0;
/*      */     } 
/*      */     
/* 1306 */     if (!this.indention || this.column > indent || (this.column == indent && !this.whitespace)) {
/* 1307 */       writeLineBreak(null);
/*      */     }
/*      */     
/* 1310 */     writeWhitespace(indent - this.column);
/*      */   }
/*      */   
/*      */   private void writeWhitespace(int length) throws IOException {
/* 1314 */     if (length <= 0) {
/*      */       return;
/*      */     }
/* 1317 */     this.whitespace = true;
/* 1318 */     char[] data = new char[length];
/* 1319 */     for (int i = 0; i < data.length; i++) {
/* 1320 */       data[i] = ' ';
/*      */     }
/* 1322 */     this.column += length;
/* 1323 */     this.stream.write(data);
/*      */   }
/*      */   
/*      */   private void writeLineBreak(String data) throws IOException {
/* 1327 */     this.whitespace = true;
/* 1328 */     this.indention = true;
/* 1329 */     this.column = 0;
/* 1330 */     if (data == null) {
/* 1331 */       this.stream.write(this.bestLineBreak);
/*      */     } else {
/* 1333 */       this.stream.write(data);
/*      */     } 
/*      */   }
/*      */   
/*      */   void writeVersionDirective(String versionText) throws IOException {
/* 1338 */     this.stream.write("%YAML ");
/* 1339 */     this.stream.write(versionText);
/* 1340 */     writeLineBreak(null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void writeTagDirective(String handleText, String prefixText) throws IOException {
/* 1346 */     this.stream.write("%TAG ");
/* 1347 */     this.stream.write(handleText);
/* 1348 */     this.stream.write(SPACE);
/* 1349 */     this.stream.write(prefixText);
/* 1350 */     writeLineBreak(null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSingleQuoted(String text, boolean split) throws IOException {
/* 1355 */     writeIndicator("'", true, false, false);
/* 1356 */     boolean spaces = false;
/* 1357 */     boolean breaks = false;
/* 1358 */     int start = 0, end = 0;
/*      */     
/* 1360 */     while (end <= text.length()) {
/* 1361 */       char ch = Character.MIN_VALUE;
/* 1362 */       if (end < text.length()) {
/* 1363 */         ch = text.charAt(end);
/*      */       }
/* 1365 */       if (spaces) {
/* 1366 */         if (ch == '\000' || ch != ' ') {
/* 1367 */           if (start + 1 == end && this.column > this.bestWidth && split && start != 0 && end != text
/* 1368 */             .length()) {
/* 1369 */             writeIndent();
/*      */           } else {
/* 1371 */             int len = end - start;
/* 1372 */             this.column += len;
/* 1373 */             this.stream.write(text, start, len);
/*      */           } 
/* 1375 */           start = end;
/*      */         } 
/* 1377 */       } else if (breaks) {
/* 1378 */         if (ch == '\000' || Constant.LINEBR.hasNo(ch)) {
/* 1379 */           if (text.charAt(start) == '\n') {
/* 1380 */             writeLineBreak(null);
/*      */           }
/* 1382 */           String data = text.substring(start, end);
/* 1383 */           for (char br : data.toCharArray()) {
/* 1384 */             if (br == '\n') {
/* 1385 */               writeLineBreak(null);
/*      */             } else {
/* 1387 */               writeLineBreak(String.valueOf(br));
/*      */             } 
/*      */           } 
/* 1390 */           writeIndent();
/* 1391 */           start = end;
/*      */         }
/*      */       
/* 1394 */       } else if (Constant.LINEBR.has(ch, "\000 '") && 
/* 1395 */         start < end) {
/* 1396 */         int len = end - start;
/* 1397 */         this.column += len;
/* 1398 */         this.stream.write(text, start, len);
/* 1399 */         start = end;
/*      */       } 
/*      */ 
/*      */       
/* 1403 */       if (ch == '\'') {
/* 1404 */         this.column += 2;
/* 1405 */         this.stream.write("''");
/* 1406 */         start = end + 1;
/*      */       } 
/* 1408 */       if (ch != '\000') {
/* 1409 */         spaces = (ch == ' ');
/* 1410 */         breaks = Constant.LINEBR.has(ch);
/*      */       } 
/* 1412 */       end++;
/*      */     } 
/* 1414 */     writeIndicator("'", false, false, false);
/*      */   }
/*      */   
/*      */   private void writeDoubleQuoted(String text, boolean split) throws IOException {
/* 1418 */     writeIndicator("\"", true, false, false);
/* 1419 */     int start = 0;
/* 1420 */     int end = 0;
/* 1421 */     while (end <= text.length()) {
/* 1422 */       Character ch = null;
/* 1423 */       if (end < text.length()) {
/* 1424 */         ch = Character.valueOf(text.charAt(end));
/*      */       }
/* 1426 */       if (ch == null || "\"\\  ﻿".indexOf(ch.charValue()) != -1 || ' ' > ch
/* 1427 */         .charValue() || ch.charValue() > '~') {
/* 1428 */         if (start < end) {
/* 1429 */           int len = end - start;
/* 1430 */           this.column += len;
/* 1431 */           this.stream.write(text, start, len);
/* 1432 */           start = end;
/*      */         } 
/* 1434 */         if (ch != null) {
/*      */           String data;
/*      */           
/* 1437 */           if (ESCAPE_REPLACEMENTS.containsKey(ch)) {
/* 1438 */             data = "\\" + (String)ESCAPE_REPLACEMENTS.get(ch);
/*      */           } else {
/*      */             int codePoint;
/*      */             
/* 1442 */             if (Character.isHighSurrogate(ch.charValue()) && end + 1 < text.length()) {
/* 1443 */               char ch2 = text.charAt(end + 1);
/* 1444 */               codePoint = Character.toCodePoint(ch.charValue(), ch2);
/*      */             } else {
/* 1446 */               codePoint = ch.charValue();
/*      */             } 
/*      */             
/* 1449 */             if (this.allowUnicode && StreamReader.isPrintable(codePoint)) {
/* 1450 */               data = String.valueOf(Character.toChars(codePoint));
/*      */               
/* 1452 */               if (Character.charCount(codePoint) == 2) {
/* 1453 */                 end++;
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 1458 */             else if (ch.charValue() <= 'ÿ') {
/* 1459 */               String s = "0" + Integer.toString(ch.charValue(), 16);
/* 1460 */               data = "\\x" + s.substring(s.length() - 2);
/* 1461 */             } else if (Character.charCount(codePoint) == 2) {
/* 1462 */               end++;
/* 1463 */               String s = "000" + Long.toHexString(codePoint);
/* 1464 */               data = "\\U" + s.substring(s.length() - 8);
/*      */             } else {
/* 1466 */               String s = "000" + Integer.toString(ch.charValue(), 16);
/* 1467 */               data = "\\u" + s.substring(s.length() - 4);
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1472 */           this.column += data.length();
/* 1473 */           this.stream.write(data);
/* 1474 */           start = end + 1;
/*      */         } 
/*      */       } 
/* 1477 */       if (0 < end && end < text.length() - 1 && (ch.charValue() == ' ' || start >= end) && this.column + end - start > this.bestWidth && split) {
/*      */         String data;
/*      */         
/* 1480 */         if (start >= end) {
/* 1481 */           data = "\\";
/*      */         } else {
/* 1483 */           data = text.substring(start, end) + "\\";
/*      */         } 
/* 1485 */         if (start < end) {
/* 1486 */           start = end;
/*      */         }
/* 1488 */         this.column += data.length();
/* 1489 */         this.stream.write(data);
/* 1490 */         writeIndent();
/* 1491 */         this.whitespace = false;
/* 1492 */         this.indention = false;
/* 1493 */         if (text.charAt(start) == ' ') {
/* 1494 */           data = "\\";
/* 1495 */           this.column += data.length();
/* 1496 */           this.stream.write(data);
/*      */         } 
/*      */       } 
/* 1499 */       end++;
/*      */     } 
/* 1501 */     writeIndicator("\"", false, false, false);
/*      */   }
/*      */   
/*      */   private boolean writeCommentLines(List<CommentLine> commentLines) throws IOException {
/* 1505 */     boolean wroteComment = false;
/* 1506 */     if (this.emitComments) {
/* 1507 */       int indentColumns = 0;
/* 1508 */       boolean firstComment = true;
/* 1509 */       for (CommentLine commentLine : commentLines) {
/* 1510 */         if (commentLine.getCommentType() != CommentType.BLANK_LINE) {
/* 1511 */           if (firstComment) {
/* 1512 */             firstComment = false;
/* 1513 */             writeIndicator("#", (commentLine.getCommentType() == CommentType.IN_LINE), false, false);
/* 1514 */             indentColumns = (this.column > 0) ? (this.column - 1) : 0;
/*      */           } else {
/* 1516 */             writeWhitespace(indentColumns);
/* 1517 */             writeIndicator("#", false, false, false);
/*      */           } 
/* 1519 */           this.stream.write(commentLine.getValue());
/* 1520 */           writeLineBreak(null);
/*      */         } else {
/* 1522 */           writeLineBreak(null);
/* 1523 */           writeIndent();
/*      */         } 
/* 1525 */         wroteComment = true;
/*      */       } 
/*      */     } 
/* 1528 */     return wroteComment;
/*      */   }
/*      */   
/*      */   private void writeBlockComment() throws IOException {
/* 1532 */     if (!this.blockCommentsCollector.isEmpty()) {
/* 1533 */       writeIndent();
/* 1534 */       writeCommentLines(this.blockCommentsCollector.consume());
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean writeInlineComments() throws IOException {
/* 1539 */     return writeCommentLines(this.inlineCommentsCollector.consume());
/*      */   }
/*      */   
/*      */   private String determineBlockHints(String text) {
/* 1543 */     StringBuilder hints = new StringBuilder();
/* 1544 */     if (Constant.LINEBR.has(text.charAt(0), " ")) {
/* 1545 */       hints.append(this.bestIndent);
/*      */     }
/* 1547 */     char ch1 = text.charAt(text.length() - 1);
/* 1548 */     if (Constant.LINEBR.hasNo(ch1)) {
/* 1549 */       hints.append("-");
/* 1550 */     } else if (text.length() == 1 || Constant.LINEBR.has(text.charAt(text.length() - 2))) {
/* 1551 */       hints.append("+");
/*      */     } 
/* 1553 */     return hints.toString();
/*      */   }
/*      */   
/*      */   void writeFolded(String text, boolean split) throws IOException {
/* 1557 */     String hints = determineBlockHints(text);
/* 1558 */     writeIndicator(">" + hints, true, false, false);
/* 1559 */     if (hints.length() > 0 && hints.charAt(hints.length() - 1) == '+') {
/* 1560 */       this.openEnded = true;
/*      */     }
/* 1562 */     if (!writeInlineComments()) {
/* 1563 */       writeLineBreak(null);
/*      */     }
/* 1565 */     boolean leadingSpace = true;
/* 1566 */     boolean spaces = false;
/* 1567 */     boolean breaks = true;
/* 1568 */     int start = 0, end = 0;
/* 1569 */     while (end <= text.length()) {
/* 1570 */       char ch = Character.MIN_VALUE;
/* 1571 */       if (end < text.length()) {
/* 1572 */         ch = text.charAt(end);
/*      */       }
/* 1574 */       if (breaks) {
/* 1575 */         if (ch == '\000' || Constant.LINEBR.hasNo(ch)) {
/* 1576 */           if (!leadingSpace && ch != '\000' && ch != ' ' && text.charAt(start) == '\n') {
/* 1577 */             writeLineBreak(null);
/*      */           }
/* 1579 */           leadingSpace = (ch == ' ');
/* 1580 */           String data = text.substring(start, end);
/* 1581 */           for (char br : data.toCharArray()) {
/* 1582 */             if (br == '\n') {
/* 1583 */               writeLineBreak(null);
/*      */             } else {
/* 1585 */               writeLineBreak(String.valueOf(br));
/*      */             } 
/*      */           } 
/* 1588 */           if (ch != '\000') {
/* 1589 */             writeIndent();
/*      */           }
/* 1591 */           start = end;
/*      */         } 
/* 1593 */       } else if (spaces) {
/* 1594 */         if (ch != ' ') {
/* 1595 */           if (start + 1 == end && this.column > this.bestWidth && split) {
/* 1596 */             writeIndent();
/*      */           } else {
/* 1598 */             int len = end - start;
/* 1599 */             this.column += len;
/* 1600 */             this.stream.write(text, start, len);
/*      */           } 
/* 1602 */           start = end;
/*      */         }
/*      */       
/* 1605 */       } else if (Constant.LINEBR.has(ch, "\000 ")) {
/* 1606 */         int len = end - start;
/* 1607 */         this.column += len;
/* 1608 */         this.stream.write(text, start, len);
/* 1609 */         if (ch == '\000') {
/* 1610 */           writeLineBreak(null);
/*      */         }
/* 1612 */         start = end;
/*      */       } 
/*      */       
/* 1615 */       if (ch != '\000') {
/* 1616 */         breaks = Constant.LINEBR.has(ch);
/* 1617 */         spaces = (ch == ' ');
/*      */       } 
/* 1619 */       end++;
/*      */     } 
/*      */   }
/*      */   
/*      */   void writeLiteral(String text) throws IOException {
/* 1624 */     String hints = determineBlockHints(text);
/* 1625 */     writeIndicator("|" + hints, true, false, false);
/* 1626 */     if (hints.length() > 0 && hints.charAt(hints.length() - 1) == '+') {
/* 1627 */       this.openEnded = true;
/*      */     }
/* 1629 */     if (!writeInlineComments()) {
/* 1630 */       writeLineBreak(null);
/*      */     }
/* 1632 */     boolean breaks = true;
/* 1633 */     int start = 0, end = 0;
/* 1634 */     while (end <= text.length()) {
/* 1635 */       char ch = Character.MIN_VALUE;
/* 1636 */       if (end < text.length()) {
/* 1637 */         ch = text.charAt(end);
/*      */       }
/* 1639 */       if (breaks) {
/* 1640 */         if (ch == '\000' || Constant.LINEBR.hasNo(ch)) {
/* 1641 */           String data = text.substring(start, end);
/* 1642 */           for (char br : data.toCharArray()) {
/* 1643 */             if (br == '\n') {
/* 1644 */               writeLineBreak(null);
/*      */             } else {
/* 1646 */               writeLineBreak(String.valueOf(br));
/*      */             } 
/*      */           } 
/* 1649 */           if (ch != '\000') {
/* 1650 */             writeIndent();
/*      */           }
/* 1652 */           start = end;
/*      */         }
/*      */       
/* 1655 */       } else if (ch == '\000' || Constant.LINEBR.has(ch)) {
/* 1656 */         this.stream.write(text, start, end - start);
/* 1657 */         if (ch == '\000') {
/* 1658 */           writeLineBreak(null);
/*      */         }
/* 1660 */         start = end;
/*      */       } 
/*      */       
/* 1663 */       if (ch != '\000') {
/* 1664 */         breaks = Constant.LINEBR.has(ch);
/*      */       }
/* 1666 */       end++;
/*      */     } 
/*      */   }
/*      */   
/*      */   void writePlain(String text, boolean split) throws IOException {
/* 1671 */     if (this.rootContext) {
/* 1672 */       this.openEnded = true;
/*      */     }
/* 1674 */     if (text.length() == 0) {
/*      */       return;
/*      */     }
/* 1677 */     if (!this.whitespace) {
/* 1678 */       this.column++;
/* 1679 */       this.stream.write(SPACE);
/*      */     } 
/* 1681 */     this.whitespace = false;
/* 1682 */     this.indention = false;
/* 1683 */     boolean spaces = false;
/* 1684 */     boolean breaks = false;
/* 1685 */     int start = 0, end = 0;
/* 1686 */     while (end <= text.length()) {
/* 1687 */       char ch = Character.MIN_VALUE;
/* 1688 */       if (end < text.length()) {
/* 1689 */         ch = text.charAt(end);
/*      */       }
/* 1691 */       if (spaces) {
/* 1692 */         if (ch != ' ') {
/* 1693 */           if (start + 1 == end && this.column > this.bestWidth && split) {
/* 1694 */             writeIndent();
/* 1695 */             this.whitespace = false;
/* 1696 */             this.indention = false;
/*      */           } else {
/* 1698 */             int len = end - start;
/* 1699 */             this.column += len;
/* 1700 */             this.stream.write(text, start, len);
/*      */           } 
/* 1702 */           start = end;
/*      */         } 
/* 1704 */       } else if (breaks) {
/* 1705 */         if (Constant.LINEBR.hasNo(ch)) {
/* 1706 */           if (text.charAt(start) == '\n') {
/* 1707 */             writeLineBreak(null);
/*      */           }
/* 1709 */           String data = text.substring(start, end);
/* 1710 */           for (char br : data.toCharArray()) {
/* 1711 */             if (br == '\n') {
/* 1712 */               writeLineBreak(null);
/*      */             } else {
/* 1714 */               writeLineBreak(String.valueOf(br));
/*      */             } 
/*      */           } 
/* 1717 */           writeIndent();
/* 1718 */           this.whitespace = false;
/* 1719 */           this.indention = false;
/* 1720 */           start = end;
/*      */         }
/*      */       
/* 1723 */       } else if (Constant.LINEBR.has(ch, "\000 ")) {
/* 1724 */         int len = end - start;
/* 1725 */         this.column += len;
/* 1726 */         this.stream.write(text, start, len);
/* 1727 */         start = end;
/*      */       } 
/*      */       
/* 1730 */       if (ch != '\000') {
/* 1731 */         spaces = (ch == ' ');
/* 1732 */         breaks = Constant.LINEBR.has(ch);
/*      */       } 
/* 1734 */       end++;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\emitter\Emitter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */