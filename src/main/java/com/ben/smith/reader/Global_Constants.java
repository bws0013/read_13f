package com.ben.smith.reader;

/**
 * Created by bensmith on 11/13/17.
 *
 * Global constants that can be changed as per appropriate by a user
 */
class Global_Constants {

    // Your jdbc, in my case sqlite
    static final String jdbc_type = "jdbc:sqlite:";

    // The folder location of you database
    static final String db_location = "./database/";

    // This does nothing for now but may have some impact later
    static final boolean skip_ammended_filings = true;

    // Wait time on calls (milliseconds) to the sec web site
    // please keep this number at at least 500
    static final int wait_time = 500;

    // Output directory for created csv files
    static final String output_dir = "./output_for_csvs/";

    // This may be used later if we want to keep our filings stored somewhere
    // after we download and analyze them.
    static final boolean keep_downloaded_filings = false;

    // The maximum number of filings you are pulling
    // Approximately 4/year (we are skipping ammended ones for now)
//    static final int numDocs = 10;

    // This will probably be changed later as it does not fit in this method
    static int numDocs = 10;
    static void set_changeable_numDocs(int new_numDocs) {
        numDocs = new_numDocs;
    }

    // Starting directory for local files that will be read and processed.
    static final String start_path_for_local_files = System.getProperty("user.dir") + "/examples/local_filings/";
}
