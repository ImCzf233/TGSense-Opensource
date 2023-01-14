package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004¸\u0006\u0000"},
    d2 = { "kotlin/concurrent/TimersKt$timerTask$1", "Ljava/util/TimerTask;", "run", "", "kotlin-stdlib"}
)
public final class AutoPlay$queueAutoPlay$$inlined$schedule$1 extends TimerTask {

    final AutoPlay this$0;
    final Function0 $runnable$inlined;

    public AutoPlay$queueAutoPlay$$inlined$schedule$1(AutoPlay autoplay, Function0 function0) {
        this.this$0 = autoplay;
        this.$runnable$inlined = function0;
    }

    public void run() {
        TimerTask $this$schedule = (TimerTask) this;
        boolean $i$a$-schedule-AutoPlay$queueAutoPlay$1 = false;

        if (this.this$0.getState()) {
            this.$runnable$inlined.invoke();
        }

    }
}
