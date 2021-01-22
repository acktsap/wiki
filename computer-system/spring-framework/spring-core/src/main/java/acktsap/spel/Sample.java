/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.spel;

import org.springframework.stereotype.Component;

@Component
public class Sample {

    int data = 200;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Sample [data=" + data + "]";
    }

}
