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

package acktsap.ioc.dependency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class XmlDependencyInjection {

    private static class AnotherBean {
    }

    private static class YetAnotherBean {
    }

    static public class ExampleBean {

        private AnotherBean beanOne;

        @SuppressWarnings("FieldMayBeFinal")
        private YetAnotherBean beanTwo;

        private int i;

        public ExampleBean(YetAnotherBean beanTwo, int i) {
            this.beanTwo = beanTwo;
            this.i = i;
        }

        public void setBeanOne(AnotherBean beanOne) {
            this.beanOne = beanOne;
        }

        public void setIntegerProperty(int i) {
            this.i = i;
        }

        @Override
        public String toString() {
            return "ExampleBean{" +
                "beanOne=" + beanOne +
                ", beanTwo=" + beanTwo +
                ", i=" + i +
                '}';
        }
    }

    public static void main(String[] args) {
        String configLocation = XmlDependencyInjection.class.getPackageName() + "/xml-di.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        ExampleBean bean = applicationContext.getBean(ExampleBean.class);
        System.out.println(bean);
    }
}
