/* =========================================================================================
 * Copyright © 2013-2017 the kamon project <http://kamon.io/>
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

package kamon.testkit

import com.typesafe.config.ConfigFactory
import kamon.Kamon

trait Reconfigure {

  def enableFastMetricFlushing(): Unit = {
    applyConfig("kamon.metric.tick-interval = 1 millisecond")
  }

  def enableFastSpanFlushing(): Unit = {
    applyConfig("kamon.trace.tick-interval = 1 millisecond")
  }

  def sampleAlways(): Unit = {
    applyConfig("kamon.trace.sampler = always")
  }

  def sampleNever(): Unit = {
    applyConfig("kamon.trace.sampler = never")
  }

  def enableSpanMetricScoping(): Unit = {
    applyConfig("kamon.trace.span-metrics.scope-spans-to-parent = yes")
  }

  def disableSpanMetricScoping(): Unit = {
    applyConfig("kamon.trace.span-metrics.scope-spans-to-parent = no")
  }

  def applyConfig(configString: String): Unit = {
    Kamon.reconfigure(ConfigFactory.parseString(configString).withFallback(Kamon.config()))
  }

  def resetConfig(): Unit = {
    Kamon.reconfigure(ConfigFactory.load())
  }



}
