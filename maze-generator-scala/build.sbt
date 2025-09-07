import org.scalajs.linker.interface.{ModuleSplitStyle, ModuleKind}

val scala3Version = "3.7.2"

lazy val root = project
    .in(file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      name := "maze-generator",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := scala3Version,

      // Tell Scala.js that this is an application with a main method
      scalaJSUseMainModuleInitializer := true,

      /* Configure Scala.js to emit modules in the optimal way to
       * connect to Vite's incremental reload.
       * - emit ECMAScript modules
       * - emit as many small modules as possible for classes in the "livechart" package
       * - emit as few (large) modules as possible for all other classes
       *   (in particular, for the standard library)
       */
      scalaJSLinkerConfig ~= {
          _.withModuleKind(ModuleKind.ESModule)
              .withModuleSplitStyle(
                ModuleSplitStyle.SmallModulesFor(List("JsCode"))
              )
      },
      libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,
      libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0",
      libraryDependencies += "io.indigoengine" %%% "indigo" % "0.17.0",
      libraryDependencies += "io.indigoengine" %%% "indigo-extras" % "0.17.0",
      libraryDependencies += "io.indigoengine" %%% "indigo-json-circe" % "0.17.0",
      libraryDependencies += "com.raquo" %%% "laminar" % "17.2.0"
    )
