package com.lts.queue;

import com.lts.core.cluster.Config;
import com.lts.core.extension.Adaptive;
import com.lts.core.extension.SPI;

/**
 * @author Robert HG (254963746@qq.com) on 5/19/15.
 */
@SPI("mysql")
public interface JobFeedbackQueueFactory {

    @Adaptive("job.queue")
    JobFeedbackQueue getQueue(Config config);
}
