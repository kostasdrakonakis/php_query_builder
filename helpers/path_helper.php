<?php
    /**
     * Returns the path untill rootfolder/logs/
     */
    function log_path() {
        return server_url() . Config::get('folder/logs') . '/';
    }