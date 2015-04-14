package com.tifinnearme.priteshpatel.materialdrawer.services;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by pritesh.patel on 14-04-15.
 */
public class MyService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //L.t(this,"OnStartJob");
        jobFinished(jobParameters,false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
