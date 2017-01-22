/* =========================================================================================
 * Copyright © 2013-2016 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */

parallelExecution in Test in Global := false

lazy val kamon = (project in file("."))
  .settings(moduleName := "root")
  .settings(noPublishing: _*)
  .aggregate(core, autoweave, testkit, bench)


lazy val core = (project in file("kamon-core"))
  .settings(moduleName := "kamon-core")
  .settings(
        libraryDependencies ++=
          compileScope(akkaDependency("actor").value, hdrHistogram, slf4jApi) ++
          providedScope(aspectJ) ++
          optionalScope(logbackClassic) ++
          testScope(scalatest, akkaDependency("testkit").value, akkaDependency("slf4j").value, logbackClassic))



lazy val autoweave = (project in file("kamon-autoweave"))
  .dependsOn(core)
  .settings(moduleName := "kamon-autoweave")
  .settings(
        libraryDependencies ++=
          compileScope(aspectJ) ++
          testScope(scalatest, slf4jApi))


lazy val testkit = (project in file("kamon-testkit"))
  .dependsOn(core)
  .settings(moduleName := "kamon-testkit")
  .settings(
        libraryDependencies ++=
          compileScope(akkaDependency("actor").value, akkaDependency("testkit").value) ++
          providedScope(aspectJ) ++
          testScope(slf4jApi, slf4jnop))

lazy val bench = (project in file("kamon-bench"))
  .dependsOn(core)
  .settings(moduleName := "kamon-bench")
  .settings(noPublishing: _*)
  .enablePlugins(JmhPlugin)
