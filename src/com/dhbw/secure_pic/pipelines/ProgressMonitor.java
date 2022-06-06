package com.dhbw.secure_pic.pipelines;


/**
 * Interface for monitoring the progress of a method called in doInTheBackground.
 *
 * @author Frederik Wolter
 */
public interface ProgressMonitor {
    // see https://stackoverflow.com/a/24946032/13777031

    /**
     * Update progress of task implementing this interface.
     *
     * @param progress progress [0-100]
     */
    void updateProgress(int progress);

}
