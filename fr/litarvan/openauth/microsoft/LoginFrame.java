/*    */ package fr.litarvan.openauth.microsoft;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javafx.application.Platform;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ import javafx.embed.swing.JFXPanel;
/*    */ import javafx.scene.Parent;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.scene.web.WebView;
/*    */ import javax.swing.JFrame;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoginFrame
/*    */   extends JFrame
/*    */ {
/*    */   private CompletableFuture<String> future;
/*    */   private boolean completed;
/*    */   
/*    */   public LoginFrame() {
/* 46 */     setTitle("Microsoft Authentication");
/* 47 */     setSize(750, 750);
/* 48 */     setLocationRelativeTo((Component)null);
/* 49 */     setDefaultCloseOperation(2);
/*    */     
/* 51 */     setContentPane((Container)new JFXPanel());
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<String> start(String url) {
/* 56 */     if (this.future != null) {
/* 57 */       return this.future;
/*    */     }
/*    */     
/* 60 */     this.future = new CompletableFuture<>();
/* 61 */     addWindowListener(new WindowAdapter()
/*    */         {
/*    */           public void windowClosing(WindowEvent e) {
/* 64 */             if (!LoginFrame.this.completed) {
/* 65 */               LoginFrame.this.future.complete(null);
/*    */             }
/*    */           }
/*    */         });
/* 69 */     Platform.runLater(() -> init(url));
/* 70 */     return this.future;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init(String url) {
/* 75 */     WebView webView = new WebView();
/* 76 */     JFXPanel content = (JFXPanel)getContentPane();
/*    */     
/* 78 */     content.setScene(new Scene((Parent)webView, getWidth(), getHeight()));
/*    */     
/* 80 */     webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
/*    */           if (newValue.contains("access_token")) {
/*    */             this.future.complete(newValue);
/*    */             this.completed = true;
/*    */             dispose();
/*    */           } 
/*    */         });
/* 87 */     webView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
/* 88 */     webView.getEngine().load(url);
/*    */     
/* 90 */     setVisible(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\fr\litarvan\openauth\microsoft\LoginFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */