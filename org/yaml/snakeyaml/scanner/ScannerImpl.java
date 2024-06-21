/*      */ package org.yaml.snakeyaml.scanner;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.charset.CharacterCodingException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Pattern;
/*      */ import org.yaml.snakeyaml.DumperOptions;
/*      */ import org.yaml.snakeyaml.LoaderOptions;
/*      */ import org.yaml.snakeyaml.comments.CommentType;
/*      */ import org.yaml.snakeyaml.error.Mark;
/*      */ import org.yaml.snakeyaml.error.YAMLException;
/*      */ import org.yaml.snakeyaml.reader.StreamReader;
/*      */ import org.yaml.snakeyaml.tokens.AliasToken;
/*      */ import org.yaml.snakeyaml.tokens.AnchorToken;
/*      */ import org.yaml.snakeyaml.tokens.BlockEndToken;
/*      */ import org.yaml.snakeyaml.tokens.BlockEntryToken;
/*      */ import org.yaml.snakeyaml.tokens.BlockMappingStartToken;
/*      */ import org.yaml.snakeyaml.tokens.BlockSequenceStartToken;
/*      */ import org.yaml.snakeyaml.tokens.CommentToken;
/*      */ import org.yaml.snakeyaml.tokens.DirectiveToken;
/*      */ import org.yaml.snakeyaml.tokens.DocumentEndToken;
/*      */ import org.yaml.snakeyaml.tokens.DocumentStartToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowEntryToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowMappingEndToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowMappingStartToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowSequenceEndToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowSequenceStartToken;
/*      */ import org.yaml.snakeyaml.tokens.KeyToken;
/*      */ import org.yaml.snakeyaml.tokens.ScalarToken;
/*      */ import org.yaml.snakeyaml.tokens.StreamEndToken;
/*      */ import org.yaml.snakeyaml.tokens.StreamStartToken;
/*      */ import org.yaml.snakeyaml.tokens.TagToken;
/*      */ import org.yaml.snakeyaml.tokens.TagTuple;
/*      */ import org.yaml.snakeyaml.tokens.Token;
/*      */ import org.yaml.snakeyaml.tokens.ValueToken;
/*      */ import org.yaml.snakeyaml.util.ArrayStack;
/*      */ import org.yaml.snakeyaml.util.UriEncoder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ScannerImpl
/*      */   implements Scanner
/*      */ {
/*   89 */   private static final Pattern NOT_HEXA = Pattern.compile("[^0-9A-Fa-f]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   public static final Map<Character, String> ESCAPE_REPLACEMENTS = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  114 */   public static final Map<Character, Integer> ESCAPE_CODES = new HashMap<>();
/*      */   private final StreamReader reader;
/*      */   
/*      */   static {
/*  118 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('0'), "\000");
/*      */     
/*  120 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('a'), "\007");
/*      */     
/*  122 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('b'), "\b");
/*      */     
/*  124 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('t'), "\t");
/*      */     
/*  126 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('n'), "\n");
/*      */     
/*  128 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('v'), "\013");
/*      */     
/*  130 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('f'), "\f");
/*      */     
/*  132 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('r'), "\r");
/*      */     
/*  134 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('e'), "\033");
/*      */     
/*  136 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(' '), " ");
/*      */     
/*  138 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('"'), "\"");
/*      */     
/*  140 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\\'), "\\");
/*      */     
/*  142 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('N'), "");
/*      */     
/*  144 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('_'), " ");
/*      */     
/*  146 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('L'), " ");
/*      */     
/*  148 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('P'), " ");
/*      */ 
/*      */     
/*  151 */     ESCAPE_CODES.put(Character.valueOf('x'), Integer.valueOf(2));
/*      */     
/*  153 */     ESCAPE_CODES.put(Character.valueOf('u'), Integer.valueOf(4));
/*      */     
/*  155 */     ESCAPE_CODES.put(Character.valueOf('U'), Integer.valueOf(8));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean done = false;
/*      */ 
/*      */   
/*  163 */   private int flowLevel = 0;
/*      */ 
/*      */   
/*      */   private final List<Token> tokens;
/*      */ 
/*      */   
/*      */   private Token lastToken;
/*      */ 
/*      */   
/*  172 */   private int tokensTaken = 0;
/*      */ 
/*      */   
/*  175 */   private int indent = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ArrayStack<Integer> indents;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean parseComments;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final LoaderOptions loaderOptions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean allowSimpleKey = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Map<Integer, SimpleKey> possibleSimpleKeys;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScannerImpl(StreamReader reader, LoaderOptions options) {
/*  220 */     if (options == null) {
/*  221 */       throw new NullPointerException("LoaderOptions must be provided.");
/*      */     }
/*  223 */     this.parseComments = options.isProcessComments();
/*  224 */     this.reader = reader;
/*  225 */     this.tokens = new ArrayList<>(100);
/*  226 */     this.indents = new ArrayStack(10);
/*      */     
/*  228 */     this.possibleSimpleKeys = new LinkedHashMap<>();
/*  229 */     this.loaderOptions = options;
/*  230 */     fetchStreamStart();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkToken(Token.ID... choices) {
/*  237 */     while (needMoreTokens()) {
/*  238 */       fetchMoreTokens();
/*      */     }
/*  240 */     if (!this.tokens.isEmpty()) {
/*  241 */       if (choices.length == 0) {
/*  242 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  246 */       Token.ID first = ((Token)this.tokens.get(0)).getTokenId();
/*  247 */       for (int i = 0; i < choices.length; i++) {
/*  248 */         if (first == choices[i]) {
/*  249 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  253 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Token peekToken() {
/*  260 */     while (needMoreTokens()) {
/*  261 */       fetchMoreTokens();
/*      */     }
/*  263 */     return this.tokens.get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Token getToken() {
/*  270 */     this.tokensTaken++;
/*  271 */     return this.tokens.remove(0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addToken(Token token) {
/*  277 */     this.lastToken = token;
/*  278 */     this.tokens.add(token);
/*      */   }
/*      */   
/*      */   private void addToken(int index, Token token) {
/*  282 */     if (index == this.tokens.size()) {
/*  283 */       this.lastToken = token;
/*      */     }
/*  285 */     this.tokens.add(index, token);
/*      */   }
/*      */   
/*      */   private void addAllTokens(List<Token> tokens) {
/*  289 */     this.lastToken = tokens.get(tokens.size() - 1);
/*  290 */     this.tokens.addAll(tokens);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needMoreTokens() {
/*  298 */     if (this.done) {
/*  299 */       return false;
/*      */     }
/*      */     
/*  302 */     if (this.tokens.isEmpty()) {
/*  303 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  307 */     stalePossibleSimpleKeys();
/*  308 */     return (nextPossibleSimpleKey() == this.tokensTaken);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchMoreTokens() {
/*  315 */     if (this.reader.getIndex() > this.loaderOptions.getCodePointLimit()) {
/*  316 */       throw new YAMLException("The incoming YAML document exceeds the limit: " + this.loaderOptions
/*  317 */           .getCodePointLimit() + " code points.");
/*      */     }
/*      */     
/*  320 */     scanToNextToken();
/*      */     
/*  322 */     stalePossibleSimpleKeys();
/*      */ 
/*      */     
/*  325 */     unwindIndent(this.reader.getColumn());
/*      */ 
/*      */     
/*  328 */     int c = this.reader.peek();
/*  329 */     switch (c) {
/*      */       
/*      */       case 0:
/*  332 */         fetchStreamEnd();
/*      */         return;
/*      */       
/*      */       case 37:
/*  336 */         if (checkDirective()) {
/*  337 */           fetchDirective();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 45:
/*  343 */         if (checkDocumentStart()) {
/*  344 */           fetchDocumentStart();
/*      */           return;
/*      */         } 
/*  347 */         if (checkBlockEntry()) {
/*  348 */           fetchBlockEntry();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 46:
/*  354 */         if (checkDocumentEnd()) {
/*  355 */           fetchDocumentEnd();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 91:
/*  362 */         fetchFlowSequenceStart();
/*      */         return;
/*      */       
/*      */       case 123:
/*  366 */         fetchFlowMappingStart();
/*      */         return;
/*      */       
/*      */       case 93:
/*  370 */         fetchFlowSequenceEnd();
/*      */         return;
/*      */       
/*      */       case 125:
/*  374 */         fetchFlowMappingEnd();
/*      */         return;
/*      */       
/*      */       case 44:
/*  378 */         fetchFlowEntry();
/*      */         return;
/*      */ 
/*      */       
/*      */       case 63:
/*  383 */         if (checkKey()) {
/*  384 */           fetchKey();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 58:
/*  390 */         if (checkValue()) {
/*  391 */           fetchValue();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 42:
/*  397 */         fetchAlias();
/*      */         return;
/*      */       
/*      */       case 38:
/*  401 */         fetchAnchor();
/*      */         return;
/*      */       
/*      */       case 33:
/*  405 */         fetchTag();
/*      */         return;
/*      */       
/*      */       case 124:
/*  409 */         if (this.flowLevel == 0) {
/*  410 */           fetchLiteral();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 62:
/*  416 */         if (this.flowLevel == 0) {
/*  417 */           fetchFolded();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 39:
/*  423 */         fetchSingle();
/*      */         return;
/*      */       
/*      */       case 34:
/*  427 */         fetchDouble();
/*      */         return;
/*      */     } 
/*      */     
/*  431 */     if (checkPlain()) {
/*  432 */       fetchPlain();
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  438 */     String chRepresentation = escapeChar(String.valueOf(Character.toChars(c)));
/*  439 */     if (c == 9) {
/*  440 */       chRepresentation = chRepresentation + "(TAB)";
/*      */     }
/*  442 */     String text = String.format("found character '%s' that cannot start any token. (Do not use %s for indentation)", new Object[] { chRepresentation, chRepresentation });
/*      */ 
/*      */     
/*  445 */     throw new ScannerException("while scanning for the next token", null, text, this.reader.getMark());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String escapeChar(String chRepresentation) {
/*  452 */     for (Character s : ESCAPE_REPLACEMENTS.keySet()) {
/*  453 */       String v = ESCAPE_REPLACEMENTS.get(s);
/*  454 */       if (v.equals(chRepresentation)) {
/*  455 */         return "\\" + s;
/*      */       }
/*      */     } 
/*  458 */     return chRepresentation;
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
/*      */   private int nextPossibleSimpleKey() {
/*  472 */     if (!this.possibleSimpleKeys.isEmpty()) {
/*  473 */       return ((SimpleKey)this.possibleSimpleKeys.values().iterator().next()).getTokenNumber();
/*      */     }
/*  475 */     return -1;
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
/*      */   private void stalePossibleSimpleKeys() {
/*  489 */     if (!this.possibleSimpleKeys.isEmpty()) {
/*  490 */       Iterator<SimpleKey> iterator = this.possibleSimpleKeys.values().iterator();
/*  491 */       while (iterator.hasNext()) {
/*  492 */         SimpleKey key = iterator.next();
/*  493 */         if (key.getLine() != this.reader.getLine() || this.reader.getIndex() - key.getIndex() > 1024) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  498 */           if (key.isRequired())
/*      */           {
/*      */             
/*  501 */             throw new ScannerException("while scanning a simple key", key.getMark(), "could not find expected ':'", this.reader
/*  502 */                 .getMark());
/*      */           }
/*  504 */           iterator.remove();
/*      */         } 
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
/*      */   
/*      */   private void savePossibleSimpleKey() {
/*  522 */     boolean required = (this.flowLevel == 0 && this.indent == this.reader.getColumn());
/*      */     
/*  524 */     if (this.allowSimpleKey || !required) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  534 */       if (this.allowSimpleKey) {
/*  535 */         removePossibleSimpleKey();
/*  536 */         int tokenNumber = this.tokensTaken + this.tokens.size();
/*      */         
/*  538 */         SimpleKey key = new SimpleKey(tokenNumber, required, this.reader.getIndex(), this.reader.getLine(), this.reader.getColumn(), this.reader.getMark());
/*  539 */         this.possibleSimpleKeys.put(Integer.valueOf(this.flowLevel), key);
/*      */       } 
/*      */       return;
/*      */     } 
/*      */     throw new YAMLException("A simple key is required only if it is the first token in the current line");
/*      */   }
/*      */   
/*      */   private void removePossibleSimpleKey() {
/*  547 */     SimpleKey key = this.possibleSimpleKeys.remove(Integer.valueOf(this.flowLevel));
/*  548 */     if (key != null && key.isRequired()) {
/*  549 */       throw new ScannerException("while scanning a simple key", key.getMark(), "could not find expected ':'", this.reader
/*  550 */           .getMark());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unwindIndent(int col) {
/*  578 */     if (this.flowLevel != 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  583 */     while (this.indent > col) {
/*  584 */       Mark mark = this.reader.getMark();
/*  585 */       this.indent = ((Integer)this.indents.pop()).intValue();
/*  586 */       addToken((Token)new BlockEndToken(mark, mark));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addIndent(int column) {
/*  594 */     if (this.indent < column) {
/*  595 */       this.indents.push(Integer.valueOf(this.indent));
/*  596 */       this.indent = column;
/*  597 */       return true;
/*      */     } 
/*  599 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchStreamStart() {
/*  609 */     Mark mark = this.reader.getMark();
/*      */ 
/*      */     
/*  612 */     StreamStartToken streamStartToken = new StreamStartToken(mark, mark);
/*  613 */     addToken((Token)streamStartToken);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchStreamEnd() {
/*  618 */     unwindIndent(-1);
/*      */ 
/*      */     
/*  621 */     removePossibleSimpleKey();
/*  622 */     this.allowSimpleKey = false;
/*  623 */     this.possibleSimpleKeys.clear();
/*      */ 
/*      */     
/*  626 */     Mark mark = this.reader.getMark();
/*      */ 
/*      */     
/*  629 */     StreamEndToken streamEndToken = new StreamEndToken(mark, mark);
/*  630 */     addToken((Token)streamEndToken);
/*      */ 
/*      */     
/*  633 */     this.done = true;
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
/*      */   private void fetchDirective() {
/*  645 */     unwindIndent(-1);
/*      */ 
/*      */     
/*  648 */     removePossibleSimpleKey();
/*  649 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  652 */     List<Token> tok = scanDirective();
/*  653 */     addAllTokens(tok);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchDocumentStart() {
/*  660 */     fetchDocumentIndicator(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchDocumentEnd() {
/*  667 */     fetchDocumentIndicator(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchDocumentIndicator(boolean isDocumentStart) {
/*      */     DocumentEndToken documentEndToken;
/*  676 */     unwindIndent(-1);
/*      */ 
/*      */ 
/*      */     
/*  680 */     removePossibleSimpleKey();
/*  681 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  684 */     Mark startMark = this.reader.getMark();
/*  685 */     this.reader.forward(3);
/*  686 */     Mark endMark = this.reader.getMark();
/*      */     
/*  688 */     if (isDocumentStart) {
/*  689 */       DocumentStartToken documentStartToken = new DocumentStartToken(startMark, endMark);
/*      */     } else {
/*  691 */       documentEndToken = new DocumentEndToken(startMark, endMark);
/*      */     } 
/*  693 */     addToken((Token)documentEndToken);
/*      */   }
/*      */   
/*      */   private void fetchFlowSequenceStart() {
/*  697 */     fetchFlowCollectionStart(false);
/*      */   }
/*      */   
/*      */   private void fetchFlowMappingStart() {
/*  701 */     fetchFlowCollectionStart(true);
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
/*      */   private void fetchFlowCollectionStart(boolean isMappingStart) {
/*      */     FlowSequenceStartToken flowSequenceStartToken;
/*  717 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  720 */     this.flowLevel++;
/*      */ 
/*      */     
/*  723 */     this.allowSimpleKey = true;
/*      */ 
/*      */     
/*  726 */     Mark startMark = this.reader.getMark();
/*  727 */     this.reader.forward(1);
/*  728 */     Mark endMark = this.reader.getMark();
/*      */     
/*  730 */     if (isMappingStart) {
/*  731 */       FlowMappingStartToken flowMappingStartToken = new FlowMappingStartToken(startMark, endMark);
/*      */     } else {
/*  733 */       flowSequenceStartToken = new FlowSequenceStartToken(startMark, endMark);
/*      */     } 
/*  735 */     addToken((Token)flowSequenceStartToken);
/*      */   }
/*      */   
/*      */   private void fetchFlowSequenceEnd() {
/*  739 */     fetchFlowCollectionEnd(false);
/*      */   }
/*      */   
/*      */   private void fetchFlowMappingEnd() {
/*  743 */     fetchFlowCollectionEnd(true);
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
/*      */   private void fetchFlowCollectionEnd(boolean isMappingEnd) {
/*      */     FlowSequenceEndToken flowSequenceEndToken;
/*  757 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  760 */     this.flowLevel--;
/*      */ 
/*      */     
/*  763 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  766 */     Mark startMark = this.reader.getMark();
/*  767 */     this.reader.forward();
/*  768 */     Mark endMark = this.reader.getMark();
/*      */     
/*  770 */     if (isMappingEnd) {
/*  771 */       FlowMappingEndToken flowMappingEndToken = new FlowMappingEndToken(startMark, endMark);
/*      */     } else {
/*  773 */       flowSequenceEndToken = new FlowSequenceEndToken(startMark, endMark);
/*      */     } 
/*  775 */     addToken((Token)flowSequenceEndToken);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchFlowEntry() {
/*  786 */     this.allowSimpleKey = true;
/*      */ 
/*      */     
/*  789 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  792 */     Mark startMark = this.reader.getMark();
/*  793 */     this.reader.forward();
/*  794 */     Mark endMark = this.reader.getMark();
/*  795 */     FlowEntryToken flowEntryToken = new FlowEntryToken(startMark, endMark);
/*  796 */     addToken((Token)flowEntryToken);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchBlockEntry() {
/*  806 */     if (this.flowLevel == 0) {
/*      */       
/*  808 */       if (!this.allowSimpleKey) {
/*  809 */         throw new ScannerException(null, null, "sequence entries are not allowed here", this.reader
/*  810 */             .getMark());
/*      */       }
/*      */ 
/*      */       
/*  814 */       if (addIndent(this.reader.getColumn())) {
/*  815 */         Mark mark = this.reader.getMark();
/*  816 */         addToken((Token)new BlockSequenceStartToken(mark, mark));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  823 */     this.allowSimpleKey = true;
/*      */ 
/*      */     
/*  826 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  829 */     Mark startMark = this.reader.getMark();
/*  830 */     this.reader.forward();
/*  831 */     Mark endMark = this.reader.getMark();
/*  832 */     BlockEntryToken blockEntryToken = new BlockEntryToken(startMark, endMark);
/*  833 */     addToken((Token)blockEntryToken);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchKey() {
/*  843 */     if (this.flowLevel == 0) {
/*      */       
/*  845 */       if (!this.allowSimpleKey) {
/*  846 */         throw new ScannerException(null, null, "mapping keys are not allowed here", this.reader
/*  847 */             .getMark());
/*      */       }
/*      */       
/*  850 */       if (addIndent(this.reader.getColumn())) {
/*  851 */         Mark mark = this.reader.getMark();
/*  852 */         addToken((Token)new BlockMappingStartToken(mark, mark));
/*      */       } 
/*      */     } 
/*      */     
/*  856 */     this.allowSimpleKey = (this.flowLevel == 0);
/*      */ 
/*      */     
/*  859 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  862 */     Mark startMark = this.reader.getMark();
/*  863 */     this.reader.forward();
/*  864 */     Mark endMark = this.reader.getMark();
/*  865 */     KeyToken keyToken = new KeyToken(startMark, endMark);
/*  866 */     addToken((Token)keyToken);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchValue() {
/*  876 */     SimpleKey key = this.possibleSimpleKeys.remove(Integer.valueOf(this.flowLevel));
/*  877 */     if (key != null) {
/*      */       
/*  879 */       addToken(key.getTokenNumber() - this.tokensTaken, (Token)new KeyToken(key.getMark(), key.getMark()));
/*      */ 
/*      */ 
/*      */       
/*  883 */       if (this.flowLevel == 0 && 
/*  884 */         addIndent(key.getColumn())) {
/*  885 */         addToken(key.getTokenNumber() - this.tokensTaken, (Token)new BlockMappingStartToken(key
/*  886 */               .getMark(), key.getMark()));
/*      */       }
/*      */ 
/*      */       
/*  890 */       this.allowSimpleKey = false;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  896 */       if (this.flowLevel == 0)
/*      */       {
/*      */ 
/*      */         
/*  900 */         if (!this.allowSimpleKey) {
/*  901 */           throw new ScannerException(null, null, "mapping values are not allowed here", this.reader
/*  902 */               .getMark());
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  909 */       if (this.flowLevel == 0 && 
/*  910 */         addIndent(this.reader.getColumn())) {
/*  911 */         Mark mark = this.reader.getMark();
/*  912 */         addToken((Token)new BlockMappingStartToken(mark, mark));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  917 */       this.allowSimpleKey = (this.flowLevel == 0);
/*      */ 
/*      */       
/*  920 */       removePossibleSimpleKey();
/*      */     } 
/*      */     
/*  923 */     Mark startMark = this.reader.getMark();
/*  924 */     this.reader.forward();
/*  925 */     Mark endMark = this.reader.getMark();
/*  926 */     ValueToken valueToken = new ValueToken(startMark, endMark);
/*  927 */     addToken((Token)valueToken);
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
/*      */   private void fetchAlias() {
/*  941 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  944 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  947 */     Token tok = scanAnchor(false);
/*  948 */     addToken(tok);
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
/*      */   private void fetchAnchor() {
/*  962 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  965 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  968 */     Token tok = scanAnchor(true);
/*  969 */     addToken(tok);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchTag() {
/*  979 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  982 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  985 */     Token tok = scanTag();
/*  986 */     addToken(tok);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchLiteral() {
/*  996 */     fetchBlockScalar('|');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchFolded() {
/* 1006 */     fetchBlockScalar('>');
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
/*      */   private void fetchBlockScalar(char style) {
/* 1018 */     this.allowSimpleKey = true;
/*      */ 
/*      */     
/* 1021 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/* 1024 */     List<Token> tok = scanBlockScalar(style);
/* 1025 */     addAllTokens(tok);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchSingle() {
/* 1032 */     fetchFlowScalar('\'');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchDouble() {
/* 1039 */     fetchFlowScalar('"');
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
/*      */   private void fetchFlowScalar(char style) {
/* 1051 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/* 1054 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/* 1057 */     Token tok = scanFlowScalar(style);
/* 1058 */     addToken(tok);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchPlain() {
/* 1066 */     savePossibleSimpleKey();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1071 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/* 1074 */     Token tok = scanPlain();
/* 1075 */     addToken(tok);
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
/*      */   private boolean checkDirective() {
/* 1089 */     return (this.reader.getColumn() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkDocumentStart() {
/* 1098 */     if (this.reader.getColumn() == 0) {
/* 1099 */       return ("---".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3)));
/*      */     }
/* 1101 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkDocumentEnd() {
/* 1110 */     if (this.reader.getColumn() == 0) {
/* 1111 */       return ("...".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3)));
/*      */     }
/* 1113 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkBlockEntry() {
/* 1121 */     return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkKey() {
/* 1129 */     if (this.flowLevel != 0) {
/* 1130 */       return true;
/*      */     }
/*      */     
/* 1133 */     return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkValue() {
/* 1142 */     if (this.flowLevel != 0) {
/* 1143 */       return true;
/*      */     }
/*      */     
/* 1146 */     return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
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
/*      */   private boolean checkPlain() {
/* 1170 */     int c = this.reader.peek();
/*      */ 
/*      */     
/* 1173 */     return (Constant.NULL_BL_T_LINEBR.hasNo(c, "-?:,[]{}#&*!|>'\"%@`") || (Constant.NULL_BL_T_LINEBR
/* 1174 */       .hasNo(this.reader.peek(1)) && (c == 45 || (this.flowLevel == 0 && "?:"
/* 1175 */       .indexOf(c) != -1))));
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
/*      */   private void scanToNextToken() {
/* 1204 */     if (this.reader.getIndex() == 0 && this.reader.peek() == 65279) {
/* 1205 */       this.reader.forward();
/*      */     }
/* 1207 */     boolean found = false;
/* 1208 */     int inlineStartColumn = -1;
/* 1209 */     while (!found) {
/* 1210 */       Mark startMark = this.reader.getMark();
/* 1211 */       int columnBeforeComment = this.reader.getColumn();
/* 1212 */       boolean commentSeen = false;
/* 1213 */       int ff = 0;
/*      */ 
/*      */       
/* 1216 */       while (this.reader.peek(ff) == 32) {
/* 1217 */         ff++;
/*      */       }
/* 1219 */       if (ff > 0) {
/* 1220 */         this.reader.forward(ff);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1226 */       if (this.reader.peek() == 35) {
/* 1227 */         CommentType type; commentSeen = true;
/*      */         
/* 1229 */         if (columnBeforeComment != 0 && (this.lastToken == null || this.lastToken
/* 1230 */           .getTokenId() != Token.ID.BlockEntry)) {
/* 1231 */           type = CommentType.IN_LINE;
/* 1232 */           inlineStartColumn = this.reader.getColumn();
/* 1233 */         } else if (inlineStartColumn == this.reader.getColumn()) {
/* 1234 */           type = CommentType.IN_LINE;
/*      */         } else {
/* 1236 */           inlineStartColumn = -1;
/* 1237 */           type = CommentType.BLOCK;
/*      */         } 
/* 1239 */         CommentToken token = scanComment(type);
/* 1240 */         if (this.parseComments) {
/* 1241 */           addToken((Token)token);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1246 */       String breaks = scanLineBreak();
/* 1247 */       if (breaks.length() != 0) {
/* 1248 */         if (this.parseComments && !commentSeen && 
/* 1249 */           columnBeforeComment == 0) {
/* 1250 */           Mark endMark = this.reader.getMark();
/* 1251 */           addToken((Token)new CommentToken(CommentType.BLANK_LINE, breaks, startMark, endMark));
/*      */         } 
/*      */         
/* 1254 */         if (this.flowLevel == 0)
/*      */         {
/*      */           
/* 1257 */           this.allowSimpleKey = true; } 
/*      */         continue;
/*      */       } 
/* 1260 */       found = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private CommentToken scanComment(CommentType type) {
/* 1267 */     Mark startMark = this.reader.getMark();
/* 1268 */     this.reader.forward();
/* 1269 */     int length = 0;
/* 1270 */     while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(length))) {
/* 1271 */       length++;
/*      */     }
/* 1273 */     String value = this.reader.prefixForward(length);
/* 1274 */     Mark endMark = this.reader.getMark();
/* 1275 */     return new CommentToken(type, value, startMark, endMark);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Token> scanDirective() {
/* 1281 */     Mark endMark, startMark = this.reader.getMark();
/*      */     
/* 1283 */     this.reader.forward();
/* 1284 */     String name = scanDirectiveName(startMark);
/* 1285 */     List<?> value = null;
/* 1286 */     if ("YAML".equals(name)) {
/* 1287 */       value = scanYamlDirectiveValue(startMark);
/* 1288 */       endMark = this.reader.getMark();
/* 1289 */     } else if ("TAG".equals(name)) {
/* 1290 */       value = scanTagDirectiveValue(startMark);
/* 1291 */       endMark = this.reader.getMark();
/*      */     } else {
/* 1293 */       endMark = this.reader.getMark();
/* 1294 */       int ff = 0;
/* 1295 */       while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(ff))) {
/* 1296 */         ff++;
/*      */       }
/* 1298 */       if (ff > 0) {
/* 1299 */         this.reader.forward(ff);
/*      */       }
/*      */     } 
/* 1302 */     CommentToken commentToken = scanDirectiveIgnoredLine(startMark);
/* 1303 */     DirectiveToken token = new DirectiveToken(name, value, startMark, endMark);
/* 1304 */     return makeTokenList(new Token[] { (Token)token, (Token)commentToken });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanDirectiveName(Mark startMark) {
/* 1314 */     int length = 0;
/*      */ 
/*      */ 
/*      */     
/* 1318 */     int c = this.reader.peek(length);
/* 1319 */     while (Constant.ALPHA.has(c)) {
/* 1320 */       length++;
/* 1321 */       c = this.reader.peek(length);
/*      */     } 
/*      */     
/* 1324 */     if (length == 0) {
/* 1325 */       String s = String.valueOf(Character.toChars(c));
/* 1326 */       throw new ScannerException("while scanning a directive", startMark, "expected alphabetic or numeric character, but found " + s + "(" + c + ")", this.reader
/*      */           
/* 1328 */           .getMark());
/*      */     } 
/* 1330 */     String value = this.reader.prefixForward(length);
/* 1331 */     c = this.reader.peek();
/* 1332 */     if (Constant.NULL_BL_LINEBR.hasNo(c)) {
/* 1333 */       String s = String.valueOf(Character.toChars(c));
/* 1334 */       throw new ScannerException("while scanning a directive", startMark, "expected alphabetic or numeric character, but found " + s + "(" + c + ")", this.reader
/*      */           
/* 1336 */           .getMark());
/*      */     } 
/* 1338 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   private List<Integer> scanYamlDirectiveValue(Mark startMark) {
/* 1343 */     while (this.reader.peek() == 32) {
/* 1344 */       this.reader.forward();
/*      */     }
/* 1346 */     Integer major = scanYamlDirectiveNumber(startMark);
/* 1347 */     int c = this.reader.peek();
/* 1348 */     if (c != 46) {
/* 1349 */       String s = String.valueOf(Character.toChars(c));
/* 1350 */       throw new ScannerException("while scanning a directive", startMark, "expected a digit or '.', but found " + s + "(" + c + ")", this.reader
/* 1351 */           .getMark());
/*      */     } 
/* 1353 */     this.reader.forward();
/* 1354 */     Integer minor = scanYamlDirectiveNumber(startMark);
/* 1355 */     c = this.reader.peek();
/* 1356 */     if (Constant.NULL_BL_LINEBR.hasNo(c)) {
/* 1357 */       String s = String.valueOf(Character.toChars(c));
/* 1358 */       throw new ScannerException("while scanning a directive", startMark, "expected a digit or ' ', but found " + s + "(" + c + ")", this.reader
/* 1359 */           .getMark());
/*      */     } 
/* 1361 */     List<Integer> result = new ArrayList<>(2);
/* 1362 */     result.add(major);
/* 1363 */     result.add(minor);
/* 1364 */     return result;
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
/*      */   private Integer scanYamlDirectiveNumber(Mark startMark) {
/* 1376 */     int c = this.reader.peek();
/* 1377 */     if (!Character.isDigit(c)) {
/* 1378 */       String s = String.valueOf(Character.toChars(c));
/* 1379 */       throw new ScannerException("while scanning a directive", startMark, "expected a digit, but found " + s + "(" + c + ")", this.reader
/* 1380 */           .getMark());
/*      */     } 
/* 1382 */     int length = 0;
/* 1383 */     while (Character.isDigit(this.reader.peek(length))) {
/* 1384 */       length++;
/*      */     }
/* 1386 */     Integer value = Integer.valueOf(Integer.parseInt(this.reader.prefixForward(length)));
/* 1387 */     return value;
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
/*      */   private List<String> scanTagDirectiveValue(Mark startMark) {
/* 1404 */     while (this.reader.peek() == 32) {
/* 1405 */       this.reader.forward();
/*      */     }
/* 1407 */     String handle = scanTagDirectiveHandle(startMark);
/* 1408 */     while (this.reader.peek() == 32) {
/* 1409 */       this.reader.forward();
/*      */     }
/* 1411 */     String prefix = scanTagDirectivePrefix(startMark);
/* 1412 */     List<String> result = new ArrayList<>(2);
/* 1413 */     result.add(handle);
/* 1414 */     result.add(prefix);
/* 1415 */     return result;
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
/*      */   private String scanTagDirectiveHandle(Mark startMark) {
/* 1427 */     String value = scanTagHandle("directive", startMark);
/* 1428 */     int c = this.reader.peek();
/* 1429 */     if (c != 32) {
/* 1430 */       String s = String.valueOf(Character.toChars(c));
/* 1431 */       throw new ScannerException("while scanning a directive", startMark, "expected ' ', but found " + s + "(" + c + ")", this.reader
/* 1432 */           .getMark());
/*      */     } 
/* 1434 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanTagDirectivePrefix(Mark startMark) {
/* 1444 */     String value = scanTagUri("directive", startMark);
/* 1445 */     int c = this.reader.peek();
/* 1446 */     if (Constant.NULL_BL_LINEBR.hasNo(c)) {
/* 1447 */       String s = String.valueOf(Character.toChars(c));
/* 1448 */       throw new ScannerException("while scanning a directive", startMark, "expected ' ', but found " + s + "(" + c + ")", this.reader
/* 1449 */           .getMark());
/*      */     } 
/* 1451 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   private CommentToken scanDirectiveIgnoredLine(Mark startMark) {
/* 1456 */     while (this.reader.peek() == 32) {
/* 1457 */       this.reader.forward();
/*      */     }
/* 1459 */     CommentToken commentToken = null;
/* 1460 */     if (this.reader.peek() == 35) {
/* 1461 */       CommentToken comment = scanComment(CommentType.IN_LINE);
/* 1462 */       if (this.parseComments) {
/* 1463 */         commentToken = comment;
/*      */       }
/*      */     } 
/* 1466 */     int c = this.reader.peek();
/* 1467 */     String lineBreak = scanLineBreak();
/* 1468 */     if (lineBreak.length() == 0 && c != 0) {
/* 1469 */       String s = String.valueOf(Character.toChars(c));
/* 1470 */       throw new ScannerException("while scanning a directive", startMark, "expected a comment or a line break, but found " + s + "(" + c + ")", this.reader
/* 1471 */           .getMark());
/*      */     } 
/* 1473 */     return commentToken;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Token scanAnchor(boolean isAnchor) {
/*      */     AliasToken aliasToken;
/* 1485 */     Mark startMark = this.reader.getMark();
/* 1486 */     int indicator = this.reader.peek();
/* 1487 */     String name = (indicator == 42) ? "alias" : "anchor";
/* 1488 */     this.reader.forward();
/* 1489 */     int length = 0;
/* 1490 */     int c = this.reader.peek(length);
/* 1491 */     while (Constant.NULL_BL_T_LINEBR.hasNo(c, ":,[]{}/.*&")) {
/* 1492 */       length++;
/* 1493 */       c = this.reader.peek(length);
/*      */     } 
/* 1495 */     if (length == 0) {
/* 1496 */       String s = String.valueOf(Character.toChars(c));
/* 1497 */       throw new ScannerException("while scanning an " + name, startMark, "unexpected character found " + s + "(" + c + ")", this.reader
/* 1498 */           .getMark());
/*      */     } 
/* 1500 */     String value = this.reader.prefixForward(length);
/* 1501 */     c = this.reader.peek();
/* 1502 */     if (Constant.NULL_BL_T_LINEBR.hasNo(c, "?:,]}%@`")) {
/* 1503 */       String s = String.valueOf(Character.toChars(c));
/* 1504 */       throw new ScannerException("while scanning an " + name, startMark, "unexpected character found " + s + "(" + c + ")", this.reader
/* 1505 */           .getMark());
/*      */     } 
/* 1507 */     Mark endMark = this.reader.getMark();
/*      */     
/* 1509 */     if (isAnchor) {
/* 1510 */       AnchorToken anchorToken = new AnchorToken(value, startMark, endMark);
/*      */     } else {
/* 1512 */       aliasToken = new AliasToken(value, startMark, endMark);
/*      */     } 
/* 1514 */     return (Token)aliasToken;
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
/*      */   private Token scanTag() {
/* 1548 */     Mark startMark = this.reader.getMark();
/*      */ 
/*      */     
/* 1551 */     int c = this.reader.peek(1);
/* 1552 */     String handle = null;
/* 1553 */     String suffix = null;
/*      */     
/* 1555 */     if (c == 60) {
/*      */ 
/*      */       
/* 1558 */       this.reader.forward(2);
/* 1559 */       suffix = scanTagUri("tag", startMark);
/* 1560 */       c = this.reader.peek();
/* 1561 */       if (c != 62) {
/*      */ 
/*      */         
/* 1564 */         String s = String.valueOf(Character.toChars(c));
/* 1565 */         throw new ScannerException("while scanning a tag", startMark, "expected '>', but found '" + s + "' (" + c + ")", this.reader
/* 1566 */             .getMark());
/*      */       } 
/* 1568 */       this.reader.forward();
/* 1569 */     } else if (Constant.NULL_BL_T_LINEBR.has(c)) {
/*      */ 
/*      */       
/* 1572 */       suffix = "!";
/* 1573 */       this.reader.forward();
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1579 */       int length = 1;
/* 1580 */       boolean useHandle = false;
/* 1581 */       while (Constant.NULL_BL_LINEBR.hasNo(c)) {
/* 1582 */         if (c == 33) {
/* 1583 */           useHandle = true;
/*      */           break;
/*      */         } 
/* 1586 */         length++;
/* 1587 */         c = this.reader.peek(length);
/*      */       } 
/*      */ 
/*      */       
/* 1591 */       if (useHandle) {
/* 1592 */         handle = scanTagHandle("tag", startMark);
/*      */       } else {
/* 1594 */         handle = "!";
/* 1595 */         this.reader.forward();
/*      */       } 
/* 1597 */       suffix = scanTagUri("tag", startMark);
/*      */     } 
/* 1599 */     c = this.reader.peek();
/*      */ 
/*      */     
/* 1602 */     if (Constant.NULL_BL_LINEBR.hasNo(c)) {
/* 1603 */       String s = String.valueOf(Character.toChars(c));
/* 1604 */       throw new ScannerException("while scanning a tag", startMark, "expected ' ', but found '" + s + "' (" + c + ")", this.reader
/* 1605 */           .getMark());
/*      */     } 
/* 1607 */     TagTuple value = new TagTuple(handle, suffix);
/* 1608 */     Mark endMark = this.reader.getMark();
/* 1609 */     return (Token)new TagToken(value, startMark, endMark);
/*      */   }
/*      */ 
/*      */   
/*      */   private List<Token> scanBlockScalar(char style) {
/*      */     String breaks;
/*      */     int indent;
/*      */     Mark mark1;
/* 1617 */     boolean folded = (style == '>');
/* 1618 */     StringBuilder chunks = new StringBuilder();
/* 1619 */     Mark startMark = this.reader.getMark();
/*      */     
/* 1621 */     this.reader.forward();
/* 1622 */     Chomping chompi = scanBlockScalarIndicators(startMark);
/* 1623 */     int increment = chompi.getIncrement();
/* 1624 */     CommentToken commentToken = scanBlockScalarIgnoredLine(startMark);
/*      */ 
/*      */     
/* 1627 */     int minIndent = this.indent + 1;
/* 1628 */     if (minIndent < 1) {
/* 1629 */       minIndent = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1635 */     if (increment == -1) {
/* 1636 */       Object[] brme = scanBlockScalarIndentation();
/* 1637 */       breaks = (String)brme[0];
/* 1638 */       int maxIndent = ((Integer)brme[1]).intValue();
/* 1639 */       mark1 = (Mark)brme[2];
/* 1640 */       indent = Math.max(minIndent, maxIndent);
/*      */     } else {
/* 1642 */       indent = minIndent + increment - 1;
/* 1643 */       Object[] brme = scanBlockScalarBreaks(indent);
/* 1644 */       breaks = (String)brme[0];
/* 1645 */       mark1 = (Mark)brme[1];
/*      */     } 
/*      */     
/* 1648 */     String lineBreak = "";
/*      */ 
/*      */     
/* 1651 */     while (this.reader.getColumn() == indent && this.reader.peek() != 0) {
/* 1652 */       chunks.append(breaks);
/* 1653 */       boolean leadingNonSpace = (" \t".indexOf(this.reader.peek()) == -1);
/* 1654 */       int length = 0;
/* 1655 */       while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(length))) {
/* 1656 */         length++;
/*      */       }
/* 1658 */       chunks.append(this.reader.prefixForward(length));
/* 1659 */       lineBreak = scanLineBreak();
/* 1660 */       Object[] brme = scanBlockScalarBreaks(indent);
/* 1661 */       breaks = (String)brme[0];
/* 1662 */       mark1 = (Mark)brme[1];
/* 1663 */       if (this.reader.getColumn() == indent && this.reader.peek() != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1668 */         if (folded && "\n".equals(lineBreak) && leadingNonSpace && " \t"
/* 1669 */           .indexOf(this.reader.peek()) == -1) {
/* 1670 */           if (breaks.length() == 0)
/* 1671 */             chunks.append(" "); 
/*      */           continue;
/*      */         } 
/* 1674 */         chunks.append(lineBreak);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1683 */     if (chompi.chompTailIsNotFalse()) {
/* 1684 */       chunks.append(lineBreak);
/*      */     }
/* 1686 */     if (chompi.chompTailIsTrue()) {
/* 1687 */       chunks.append(breaks);
/*      */     }
/*      */ 
/*      */     
/* 1691 */     ScalarToken scalarToken = new ScalarToken(chunks.toString(), false, startMark, mark1, DumperOptions.ScalarStyle.createStyle(Character.valueOf(style)));
/* 1692 */     return makeTokenList(new Token[] { (Token)commentToken, (Token)scalarToken });
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
/*      */   private Chomping scanBlockScalarIndicators(Mark startMark) {
/* 1712 */     Boolean chomping = null;
/* 1713 */     int increment = -1;
/* 1714 */     int c = this.reader.peek();
/* 1715 */     if (c == 45 || c == 43) {
/* 1716 */       if (c == 43) {
/* 1717 */         chomping = Boolean.TRUE;
/*      */       } else {
/* 1719 */         chomping = Boolean.FALSE;
/*      */       } 
/* 1721 */       this.reader.forward();
/* 1722 */       c = this.reader.peek();
/* 1723 */       if (Character.isDigit(c)) {
/* 1724 */         String s = String.valueOf(Character.toChars(c));
/* 1725 */         increment = Integer.parseInt(s);
/* 1726 */         if (increment == 0) {
/* 1727 */           throw new ScannerException("while scanning a block scalar", startMark, "expected indentation indicator in the range 1-9, but found 0", this.reader
/* 1728 */               .getMark());
/*      */         }
/* 1730 */         this.reader.forward();
/*      */       } 
/* 1732 */     } else if (Character.isDigit(c)) {
/* 1733 */       String s = String.valueOf(Character.toChars(c));
/* 1734 */       increment = Integer.parseInt(s);
/* 1735 */       if (increment == 0) {
/* 1736 */         throw new ScannerException("while scanning a block scalar", startMark, "expected indentation indicator in the range 1-9, but found 0", this.reader
/* 1737 */             .getMark());
/*      */       }
/* 1739 */       this.reader.forward();
/* 1740 */       c = this.reader.peek();
/* 1741 */       if (c == 45 || c == 43) {
/* 1742 */         if (c == 43) {
/* 1743 */           chomping = Boolean.TRUE;
/*      */         } else {
/* 1745 */           chomping = Boolean.FALSE;
/*      */         } 
/* 1747 */         this.reader.forward();
/*      */       } 
/*      */     } 
/* 1750 */     c = this.reader.peek();
/* 1751 */     if (Constant.NULL_BL_LINEBR.hasNo(c)) {
/* 1752 */       String s = String.valueOf(Character.toChars(c));
/* 1753 */       throw new ScannerException("while scanning a block scalar", startMark, "expected chomping or indentation indicators, but found " + s + "(" + c + ")", this.reader
/*      */           
/* 1755 */           .getMark());
/*      */     } 
/* 1757 */     return new Chomping(chomping, increment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CommentToken scanBlockScalarIgnoredLine(Mark startMark) {
/* 1768 */     while (this.reader.peek() == 32) {
/* 1769 */       this.reader.forward();
/*      */     }
/*      */ 
/*      */     
/* 1773 */     CommentToken commentToken = null;
/* 1774 */     if (this.reader.peek() == 35) {
/* 1775 */       commentToken = scanComment(CommentType.IN_LINE);
/*      */     }
/*      */ 
/*      */     
/* 1779 */     int c = this.reader.peek();
/* 1780 */     String lineBreak = scanLineBreak();
/* 1781 */     if (lineBreak.length() == 0 && c != 0) {
/* 1782 */       String s = String.valueOf(Character.toChars(c));
/* 1783 */       throw new ScannerException("while scanning a block scalar", startMark, "expected a comment or a line break, but found " + s + "(" + c + ")", this.reader
/* 1784 */           .getMark());
/*      */     } 
/* 1786 */     return commentToken;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object[] scanBlockScalarIndentation() {
/* 1797 */     StringBuilder chunks = new StringBuilder();
/* 1798 */     int maxIndent = 0;
/* 1799 */     Mark endMark = this.reader.getMark();
/*      */ 
/*      */ 
/*      */     
/* 1803 */     while (Constant.LINEBR.has(this.reader.peek(), " \r")) {
/* 1804 */       if (this.reader.peek() != 32) {
/*      */ 
/*      */         
/* 1807 */         chunks.append(scanLineBreak());
/* 1808 */         endMark = this.reader.getMark();
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1813 */       this.reader.forward();
/* 1814 */       if (this.reader.getColumn() > maxIndent) {
/* 1815 */         maxIndent = this.reader.getColumn();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1820 */     return new Object[] { chunks.toString(), Integer.valueOf(maxIndent), endMark };
/*      */   }
/*      */ 
/*      */   
/*      */   private Object[] scanBlockScalarBreaks(int indent) {
/* 1825 */     StringBuilder chunks = new StringBuilder();
/* 1826 */     Mark endMark = this.reader.getMark();
/* 1827 */     int col = this.reader.getColumn();
/*      */ 
/*      */     
/* 1830 */     while (col < indent && this.reader.peek() == 32) {
/* 1831 */       this.reader.forward();
/* 1832 */       col++;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1837 */     String lineBreak = null;
/* 1838 */     while ((lineBreak = scanLineBreak()).length() != 0) {
/* 1839 */       chunks.append(lineBreak);
/* 1840 */       endMark = this.reader.getMark();
/*      */ 
/*      */       
/* 1843 */       col = this.reader.getColumn();
/* 1844 */       while (col < indent && this.reader.peek() == 32) {
/* 1845 */         this.reader.forward();
/* 1846 */         col++;
/*      */       } 
/*      */     } 
/*      */     
/* 1850 */     return new Object[] { chunks.toString(), endMark };
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
/*      */   private Token scanFlowScalar(char style) {
/* 1872 */     boolean _double = (style == '"');
/* 1873 */     StringBuilder chunks = new StringBuilder();
/* 1874 */     Mark startMark = this.reader.getMark();
/* 1875 */     int quote = this.reader.peek();
/* 1876 */     this.reader.forward();
/* 1877 */     chunks.append(scanFlowScalarNonSpaces(_double, startMark));
/* 1878 */     while (this.reader.peek() != quote) {
/* 1879 */       chunks.append(scanFlowScalarSpaces(startMark));
/* 1880 */       chunks.append(scanFlowScalarNonSpaces(_double, startMark));
/*      */     } 
/* 1882 */     this.reader.forward();
/* 1883 */     Mark endMark = this.reader.getMark();
/* 1884 */     return (Token)new ScalarToken(chunks.toString(), false, startMark, endMark, 
/* 1885 */         DumperOptions.ScalarStyle.createStyle(Character.valueOf(style)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanFlowScalarNonSpaces(boolean doubleQuoted, Mark startMark) {
/* 1893 */     StringBuilder chunks = new StringBuilder();
/*      */ 
/*      */     
/*      */     while (true) {
/* 1897 */       int length = 0;
/* 1898 */       while (Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(length), "'\"\\")) {
/* 1899 */         length++;
/*      */       }
/* 1901 */       if (length != 0) {
/* 1902 */         chunks.append(this.reader.prefixForward(length));
/*      */       }
/*      */ 
/*      */       
/* 1906 */       int c = this.reader.peek();
/* 1907 */       if (!doubleQuoted && c == 39 && this.reader.peek(1) == 39) {
/* 1908 */         chunks.append("'");
/* 1909 */         this.reader.forward(2); continue;
/* 1910 */       }  if ((doubleQuoted && c == 39) || (!doubleQuoted && "\"\\".indexOf(c) != -1)) {
/* 1911 */         chunks.appendCodePoint(c);
/* 1912 */         this.reader.forward(); continue;
/* 1913 */       }  if (doubleQuoted && c == 92) {
/* 1914 */         this.reader.forward();
/* 1915 */         c = this.reader.peek();
/* 1916 */         if (!Character.isSupplementaryCodePoint(c) && ESCAPE_REPLACEMENTS
/* 1917 */           .containsKey(Character.valueOf((char)c))) {
/*      */ 
/*      */ 
/*      */           
/* 1921 */           chunks.append(ESCAPE_REPLACEMENTS.get(Character.valueOf((char)c)));
/* 1922 */           this.reader.forward(); continue;
/* 1923 */         }  if (!Character.isSupplementaryCodePoint(c) && ESCAPE_CODES
/* 1924 */           .containsKey(Character.valueOf((char)c))) {
/*      */ 
/*      */           
/* 1927 */           length = ((Integer)ESCAPE_CODES.get(Character.valueOf((char)c))).intValue();
/* 1928 */           this.reader.forward();
/* 1929 */           String hex = this.reader.prefix(length);
/* 1930 */           if (NOT_HEXA.matcher(hex).find()) {
/* 1931 */             throw new ScannerException("while scanning a double-quoted scalar", startMark, "expected escape sequence of " + length + " hexadecimal numbers, but found: " + hex, this.reader
/*      */                 
/* 1933 */                 .getMark());
/*      */           }
/* 1935 */           int decimal = Integer.parseInt(hex, 16);
/* 1936 */           String unicode = new String(Character.toChars(decimal));
/* 1937 */           chunks.append(unicode);
/* 1938 */           this.reader.forward(length); continue;
/* 1939 */         }  if (scanLineBreak().length() != 0) {
/* 1940 */           chunks.append(scanFlowScalarBreaks(startMark)); continue;
/*      */         } 
/* 1942 */         String s = String.valueOf(Character.toChars(c));
/* 1943 */         throw new ScannerException("while scanning a double-quoted scalar", startMark, "found unknown escape character " + s + "(" + c + ")", this.reader
/* 1944 */             .getMark());
/*      */       }  break;
/*      */     } 
/* 1947 */     return chunks.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanFlowScalarSpaces(Mark startMark) {
/* 1954 */     StringBuilder chunks = new StringBuilder();
/* 1955 */     int length = 0;
/*      */ 
/*      */     
/* 1958 */     while (" \t".indexOf(this.reader.peek(length)) != -1) {
/* 1959 */       length++;
/*      */     }
/* 1961 */     String whitespaces = this.reader.prefixForward(length);
/* 1962 */     int c = this.reader.peek();
/* 1963 */     if (c == 0)
/*      */     {
/* 1965 */       throw new ScannerException("while scanning a quoted scalar", startMark, "found unexpected end of stream", this.reader
/* 1966 */           .getMark());
/*      */     }
/*      */     
/* 1969 */     String lineBreak = scanLineBreak();
/* 1970 */     if (lineBreak.length() != 0) {
/* 1971 */       String breaks = scanFlowScalarBreaks(startMark);
/* 1972 */       if (!"\n".equals(lineBreak)) {
/* 1973 */         chunks.append(lineBreak);
/* 1974 */       } else if (breaks.length() == 0) {
/* 1975 */         chunks.append(" ");
/*      */       } 
/* 1977 */       chunks.append(breaks);
/*      */     } else {
/* 1979 */       chunks.append(whitespaces);
/*      */     } 
/* 1981 */     return chunks.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private String scanFlowScalarBreaks(Mark startMark) {
/* 1986 */     StringBuilder chunks = new StringBuilder();
/*      */ 
/*      */     
/*      */     while (true) {
/* 1990 */       String prefix = this.reader.prefix(3);
/* 1991 */       if (("---".equals(prefix) || "...".equals(prefix)) && Constant.NULL_BL_T_LINEBR
/* 1992 */         .has(this.reader.peek(3))) {
/* 1993 */         throw new ScannerException("while scanning a quoted scalar", startMark, "found unexpected document separator", this.reader
/* 1994 */             .getMark());
/*      */       }
/*      */       
/* 1997 */       while (" \t".indexOf(this.reader.peek()) != -1) {
/* 1998 */         this.reader.forward();
/*      */       }
/*      */ 
/*      */       
/* 2002 */       String lineBreak = scanLineBreak();
/* 2003 */       if (lineBreak.length() != 0) {
/* 2004 */         chunks.append(lineBreak); continue;
/*      */       }  break;
/* 2006 */     }  return chunks.toString();
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
/*      */   private Token scanPlain() {
/* 2023 */     StringBuilder chunks = new StringBuilder();
/* 2024 */     Mark startMark = this.reader.getMark();
/* 2025 */     Mark endMark = startMark;
/* 2026 */     int indent = this.indent + 1;
/* 2027 */     String spaces = "";
/*      */     
/*      */     do {
/* 2030 */       int length = 0;
/*      */       
/* 2032 */       if (this.reader.peek() == 35) {
/*      */         break;
/*      */       }
/*      */       while (true) {
/* 2036 */         int c = this.reader.peek(length);
/* 2037 */         if (Constant.NULL_BL_T_LINEBR.has(c) || (c == 58 && Constant.NULL_BL_T_LINEBR
/* 2038 */           .has(this.reader.peek(length + 1), 
/* 2039 */             (this.flowLevel != 0) ? ",[]{}" : "")) || (this.flowLevel != 0 && ",?[]{}"
/* 2040 */           .indexOf(c) != -1)) {
/*      */           break;
/*      */         }
/* 2043 */         length++;
/*      */       } 
/* 2045 */       if (length == 0) {
/*      */         break;
/*      */       }
/* 2048 */       this.allowSimpleKey = false;
/* 2049 */       chunks.append(spaces);
/* 2050 */       chunks.append(this.reader.prefixForward(length));
/* 2051 */       endMark = this.reader.getMark();
/* 2052 */       spaces = scanPlainSpaces();
/*      */     }
/* 2054 */     while (spaces.length() != 0 && this.reader.peek() != 35 && (this.flowLevel != 0 || this.reader
/* 2055 */       .getColumn() >= indent));
/*      */ 
/*      */ 
/*      */     
/* 2059 */     return (Token)new ScalarToken(chunks.toString(), startMark, endMark, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean atEndOfPlain() {
/* 2067 */     int wsLength = 0;
/* 2068 */     int wsColumn = this.reader.getColumn();
/*      */     
/*      */     int c;
/* 2071 */     while ((c = this.reader.peek(wsLength)) != 0 && Constant.NULL_BL_T_LINEBR.has(c)) {
/* 2072 */       wsLength++;
/* 2073 */       if (!Constant.LINEBR.has(c) && (c != 13 || this.reader.peek(wsLength + 1) != 10) && c != 65279) {
/*      */         
/* 2075 */         wsColumn++; continue;
/*      */       } 
/* 2077 */       wsColumn = 0;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2084 */     if (this.reader.peek(wsLength) == 35 || this.reader.peek(wsLength + 1) == 0 || (this.flowLevel == 0 && wsColumn < this.indent))
/*      */     {
/* 2086 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2091 */     if (this.flowLevel == 0) {
/*      */       
/* 2093 */       int extra = 1; while (true) { if ((c = 
/* 2094 */           this.reader.peek(wsLength + extra)) != 0 && !Constant.NULL_BL_T_LINEBR.has(c)) {
/* 2095 */           if (c == 58 && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(wsLength + extra + 1)))
/* 2096 */             return true;  extra++;
/*      */           continue;
/*      */         } 
/*      */         break; }
/*      */     
/*      */     } 
/* 2102 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanPlainSpaces() {
/* 2109 */     int length = 0;
/* 2110 */     while (this.reader.peek(length) == 32 || this.reader.peek(length) == 9) {
/* 2111 */       length++;
/*      */     }
/* 2113 */     String whitespaces = this.reader.prefixForward(length);
/* 2114 */     String lineBreak = scanLineBreak();
/* 2115 */     if (lineBreak.length() != 0) {
/* 2116 */       this.allowSimpleKey = true;
/* 2117 */       String prefix = this.reader.prefix(3);
/* 2118 */       if ("---".equals(prefix) || ("..."
/* 2119 */         .equals(prefix) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3)))) {
/* 2120 */         return "";
/*      */       }
/* 2122 */       if (this.parseComments && atEndOfPlain()) {
/* 2123 */         return "";
/*      */       }
/* 2125 */       StringBuilder breaks = new StringBuilder();
/*      */       while (true) {
/* 2127 */         while (this.reader.peek() == 32) {
/* 2128 */           this.reader.forward();
/*      */         }
/* 2130 */         String lb = scanLineBreak();
/* 2131 */         if (lb.length() != 0) {
/* 2132 */           breaks.append(lb);
/* 2133 */           prefix = this.reader.prefix(3);
/* 2134 */           if ("---".equals(prefix) || ("..."
/* 2135 */             .equals(prefix) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3)))) {
/* 2136 */             return "";
/*      */           }
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/* 2143 */       if (!"\n".equals(lineBreak))
/* 2144 */         return lineBreak + breaks; 
/* 2145 */       if (breaks.length() == 0) {
/* 2146 */         return " ";
/*      */       }
/* 2148 */       return breaks.toString();
/*      */     } 
/* 2150 */     return whitespaces;
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
/*      */   private String scanTagHandle(String name, Mark startMark) {
/* 2176 */     int c = this.reader.peek();
/* 2177 */     if (c != 33) {
/* 2178 */       String s = String.valueOf(Character.toChars(c));
/* 2179 */       throw new ScannerException("while scanning a " + name, startMark, "expected '!', but found " + s + "(" + c + ")", this.reader
/* 2180 */           .getMark());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2185 */     int length = 1;
/* 2186 */     c = this.reader.peek(length);
/* 2187 */     if (c != 32) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2192 */       while (Constant.ALPHA.has(c)) {
/* 2193 */         length++;
/* 2194 */         c = this.reader.peek(length);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2199 */       if (c != 33) {
/* 2200 */         this.reader.forward(length);
/* 2201 */         String s = String.valueOf(Character.toChars(c));
/* 2202 */         throw new ScannerException("while scanning a " + name, startMark, "expected '!', but found " + s + "(" + c + ")", this.reader
/* 2203 */             .getMark());
/*      */       } 
/* 2205 */       length++;
/*      */     } 
/* 2207 */     String value = this.reader.prefixForward(length);
/* 2208 */     return value;
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
/*      */   private String scanTagUri(String name, Mark startMark) {
/* 2228 */     StringBuilder chunks = new StringBuilder();
/*      */ 
/*      */ 
/*      */     
/* 2232 */     int length = 0;
/* 2233 */     int c = this.reader.peek(length);
/* 2234 */     while (Constant.URI_CHARS.has(c)) {
/* 2235 */       if (c == 37) {
/* 2236 */         chunks.append(this.reader.prefixForward(length));
/* 2237 */         length = 0;
/* 2238 */         chunks.append(scanUriEscapes(name, startMark));
/*      */       } else {
/* 2240 */         length++;
/*      */       } 
/* 2242 */       c = this.reader.peek(length);
/*      */     } 
/*      */ 
/*      */     
/* 2246 */     if (length != 0) {
/* 2247 */       chunks.append(this.reader.prefixForward(length));
/*      */     }
/* 2249 */     if (chunks.length() == 0) {
/*      */       
/* 2251 */       String s = String.valueOf(Character.toChars(c));
/* 2252 */       throw new ScannerException("while scanning a " + name, startMark, "expected URI, but found " + s + "(" + c + ")", this.reader
/* 2253 */           .getMark());
/*      */     } 
/* 2255 */     return chunks.toString();
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
/*      */   private String scanUriEscapes(String name, Mark startMark) {
/* 2272 */     int length = 1;
/* 2273 */     while (this.reader.peek(length * 3) == 37) {
/* 2274 */       length++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2280 */     Mark beginningMark = this.reader.getMark();
/* 2281 */     ByteBuffer buff = ByteBuffer.allocate(length);
/* 2282 */     while (this.reader.peek() == 37) {
/* 2283 */       this.reader.forward();
/*      */       try {
/* 2285 */         byte code = (byte)Integer.parseInt(this.reader.prefix(2), 16);
/* 2286 */         buff.put(code);
/* 2287 */       } catch (NumberFormatException nfe) {
/* 2288 */         int c1 = this.reader.peek();
/* 2289 */         String s1 = String.valueOf(Character.toChars(c1));
/* 2290 */         int c2 = this.reader.peek(1);
/* 2291 */         String s2 = String.valueOf(Character.toChars(c2));
/* 2292 */         throw new ScannerException("while scanning a " + name, startMark, "expected URI escape sequence of 2 hexadecimal numbers, but found " + s1 + "(" + c1 + ") and " + s2 + "(" + c2 + ")", this.reader
/*      */ 
/*      */             
/* 2295 */             .getMark());
/*      */       } 
/* 2297 */       this.reader.forward(2);
/*      */     } 
/* 2299 */     buff.flip();
/*      */     try {
/* 2301 */       return UriEncoder.decode(buff);
/* 2302 */     } catch (CharacterCodingException e) {
/* 2303 */       throw new ScannerException("while scanning a " + name, startMark, "expected URI in UTF-8: " + e
/* 2304 */           .getMessage(), beginningMark);
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
/*      */   private String scanLineBreak() {
/* 2320 */     int c = this.reader.peek();
/* 2321 */     if (c == 13 || c == 10 || c == 133) {
/* 2322 */       if (c == 13 && 10 == this.reader.peek(1)) {
/* 2323 */         this.reader.forward(2);
/*      */       } else {
/* 2325 */         this.reader.forward();
/*      */       } 
/* 2327 */       return "\n";
/* 2328 */     }  if (c == 8232 || c == 8233) {
/* 2329 */       this.reader.forward();
/* 2330 */       return String.valueOf(Character.toChars(c));
/*      */     } 
/* 2332 */     return "";
/*      */   }
/*      */   
/*      */   private List<Token> makeTokenList(Token... tokens) {
/* 2336 */     List<Token> tokenList = new ArrayList<>();
/* 2337 */     for (int ix = 0; ix < tokens.length; ix++) {
/* 2338 */       if (tokens[ix] != null)
/*      */       {
/*      */         
/* 2341 */         if (this.parseComments || !(tokens[ix] instanceof CommentToken))
/*      */         {
/*      */           
/* 2344 */           tokenList.add(tokens[ix]); }  } 
/*      */     } 
/* 2346 */     return tokenList;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Chomping
/*      */   {
/*      */     private final Boolean value;
/*      */     
/*      */     private final int increment;
/*      */ 
/*      */     
/*      */     public Chomping(Boolean value, int increment) {
/* 2358 */       this.value = value;
/* 2359 */       this.increment = increment;
/*      */     }
/*      */     
/*      */     public boolean chompTailIsNotFalse() {
/* 2363 */       return (this.value == null || this.value.booleanValue());
/*      */     }
/*      */     
/*      */     public boolean chompTailIsTrue() {
/* 2367 */       return (this.value != null && this.value.booleanValue());
/*      */     }
/*      */     
/*      */     public int getIncrement() {
/* 2371 */       return this.increment;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\scanner\ScannerImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */