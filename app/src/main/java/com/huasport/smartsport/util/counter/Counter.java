package com.huasport.smartsport.util.counter;

/**
 * 计数器
 */
public class Counter {

    private int count = 0;
    private CounterListener counterListener;
    public Counter(CounterListener counterListener, int count) {
        this.count = count;
        this.counterListener = counterListener;
    }

    /**
     * count做减法操作
     */
    public void countDown(){

        if(count > 0){
            int newCount =(count-1);
            count = newCount;
            if (count == 0){
                counterListener.countEnd(true);
            }else {
                counterListener.countEnd(false);
            }
        }else{
            counterListener.countEnd(true);
        }
    }

    public void setCount(int count) {
        this.count = count;
    }
}
