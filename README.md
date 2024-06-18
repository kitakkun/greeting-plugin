# Greeting Compiler Plugin (K2)

Kotlin Fest 2024
で発表した「[もっとKotlinを好きになる！K2時代のKotlin Compiler Plugin開発 by kitakkun](https://fortee.jp/kotlin-fest-2024/proposal/59aca818-d5d5-4eb4-a123-abae563a7f28)
」のサンプルプロジェクトです。

K2コンパイラで導入されたFIR拡張と、従来のIR拡張を組み合わせてソースプログラムの改変を行う方法を示しています。

> [!NOTE]
> ブランチによってプラグイン実装のカバー範囲が異なります。
> - [`main`](https://github.com/kitakkun/greeting-plugin/tree/main): 最小構成。CompilerPluginRegistrarとExtensionのみを実装。
> - [`kotlinc_option`](https://github.com/kitakkun/greeting-plugin/tree/kotlinc_option): コンパイラ引数対応。追加でCommandLineProcessorを実装。
> - [`gradle_dsl`](https://github.com/kitakkun/greeting-plugin/tree/gradle_dsl): Gradle DSL対応。追加でKotlinCompilerPluginSupportPluginを実装。検証の際は事前に`./gradlew publishToMavenLocal`を実行してください。

より発展的な活用事例が気になる方は [back-in-time-plugin](https://github.com/kitakkun/back-in-time-plugin) も合わせてご覧ください。

## スライド資料について

- [スライド資料](https://speakerdeck.com/kitakkun/kotlin-fest-2024-motutokotlinwohao-kininaru-k2shi-dai-nokotlin-compiler-pluginkai-fa)

## モジュール構成

プロジェクトは3つのモジュールで構成されています。

| モジュール                | 概要                     |
|----------------------|------------------------|
| greeting-annotations | `@Greetable`アノテーションの実装 |
| greeting-compiler    | コンパイラプラグインの実装          |
| test                 | コンパイラプラグインの検証          |

## 使い方

1. 改変対象のクラスに`@Greetable`アノテーションを付与します。

```kotlin
@Greetable
class A
```

2. 改変したクラスのインスタンスを生成し、関数を呼び出します。

```kotlin
val a = A()
a.greet("世界") // A「こんにちは、世界！」
```

## コンパイル時に起こっていること

```kotlin
@Greetable
class A
```

`@Greetable`を持つクラスをコンパイルすると、以下に示す`greet`関数の実装が自動的に追加されます。

```kotlin
@Greetable
class A {
    fun greet(name: String) {
        print("A「こんにちは、")
        print(name)
        println("！」")
    }
}
```

> [!TIP]
> より厳密に説明すると、次の順序でクラスの改変が行われます。
> 1. `FirDeclarationGenerationExtension`による`greet`関数宣言の生成
> ```kotlin
> @Greetable
> class A {
>     fun greet(name: String)
> }
> ```
> 2. `IrGenerationExtension`による`greet`関数の実装部分の生成
> ```kotlin
> @Greetable
> class A {
>     fun greet(name: String) {
>         print("A「こんにちは、")
>         print(name)
>         println("！」")
>     }
> }

