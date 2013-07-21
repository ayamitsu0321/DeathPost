【MOD】DeathPost-1.6.2-v0.1.1

【概要】
プレイヤーが死んだらTwitterで死んだ回数と死因をつぶやくMODです。
アカウントの認証をしていなければ後述の【アカウントの認証の手順】をご参照ください
一度認証が成功していれば'K'キーでGUIは開きません

認証の保持をやめたければ"/.minecraft/mods/deathpost/access_token.txt"のファイルを削除してください。

universal仕様になり、マルチでも使用できるようになりました、たぶん。


【アカウントの認証の手順】
1.ゲーム内で'K'キーをおし、GUIを開く
2."Browse"ボタンをおして既定のブラウザから認証のページを開く
3.ツイートしたいアカウントでログイン後、アプリ連携をする
4.連携後に表示されるPINコードをゲームのGUIのテキストボックス内に入力する
5.GUIの"Done"ボタンをおす
6."Compeleted Authorization"か"認証済み"という文字が左上に表示されれば成功です。
7.試しに死んでみましょう☆

8.ダメだったら1からやり直し


Java1.6、ブラウザがGoogleChromeの環境では確認済みです。


【前提MOD&ライブラリ】
・FML(開発時は790)
・twitter4j(開発時は3.0.3) 
    
    Twitter4jについて
      配布サイト→(http://twitter4j.org/ja/index.html)
      ライセンス→Apache License 2.0(http://www.apache.org/licenses/LICENSE-2.0)


【導入収磨z
1.minecraft.jarからMETA-INFフォルダを削除
2.minecraft.jarにFMLを導入
4.twitter4j-core-3.0.3(zip内のディレクトリは"/lib/twitter4j-core-3.0.3")をmodsフォルダにいれる
5.このMODをcoremodsフォルダに導入
6.完了


【利用規約】
・このMODを使用して生じた損害については作者のayamitsuは一切責任を負いません
・そのまま二次配布はダメ
・改変は可、しかし内部のTwitterクライアント用のConsumerKeyとConsumerSecretはこのまま使用してはいけない
・改変後は配布自由
・商用利用禁止


author: ayamitsu（あやみつ）