spring-integration-pop3-sample
======================
spring-integration-pop3-sample  
Spring Integrationを使ってPOP3でメール受信を行うサンプルです。  
  
使い方(usage)
------
Import Eclipse < 3.7.2  
  
解説(description)
------
Runtime.getRuntime().addShutdownHook(new Thread() {public void run() {...}})を使って  
プロセス終了処理を実行しています。  
  
メール受信処理はHandlerを定義します。  
定義したHandlerはDirectChannel#subscribe(handler)で設定します。  
  
テスト用にメール送信クラスも含んでいます。  
  
動作環境(environment)
------------
Spring Integration 2.2.3  
Spring Framework 3.1.4  
JavaMail 1.4.7
  
依存ライブラリ(dependencies)
----------------
mail 1.4.7  
slf4j 1.7.5  
logback 1.0.11  
  
参考(Link)
----------------
  
