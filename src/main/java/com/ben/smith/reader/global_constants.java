package com.ben.smith.reader;

/**
 * Created by bensmith on 11/13/17.
 *
 * Global constants that can be changed as per appropriate by a user
 */
class global_constants {

    // Your jdbc, in my case sqlite
    static final String jdbc_type = "jdbc:sqlite:";

    // The folder location of you database
    static final String db_location = "./database/";

    // The maximum number of filings you are pulling
    // Approximately 4/year (we are skipping ammended ones for now)
    static final int numDocs = 10;

    // This does nothing for now but may have some impact later
    static final boolean skip_ammended_filings = true;

    // Wait time on calls (milliseconds) to the sec web site
    // please keep this number at at least 500
    static final int wait_time = 500;

    // Output directory for created csv files
    static final String output_dir = "./output_for_csvs/";

    // THis may be used later if we want to keep our filings stored somewhere
    // after we download and analyze them.
    static final boolean keep_downloaded_filings = false;

}
