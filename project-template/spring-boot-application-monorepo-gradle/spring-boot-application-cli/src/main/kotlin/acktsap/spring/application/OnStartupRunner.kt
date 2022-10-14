/*
 * Spring Batch Plus
 *
 * Copyright 2022-present NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package acktsap.spring.application

import acktsap.spring.application.module.JavaModel
import acktsap.spring.application.module.KotlinModel
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class OnStartupRunner : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val javaModel = JavaModel(3)
        val kotlinModel = KotlinModel(30)
        println("JavaModel value: ${javaModel.value}")
        println("KotlinModel value: ${kotlinModel.value}")
    }
}
