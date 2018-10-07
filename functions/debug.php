<?php

    function console_log($flag, $value) {
        echo "<script>console.log('" . $flag . ": " . $value . "' );</script>";
    }

    /**
     * Writes to the logs/log_(current_date).txt file.
     *
     * @param String $type the type of the log.
     * Posible Types:
     * 1) INFO
     * 2) DEBUG
     * 3) WARNING
     * 4) ERROR
     * 5) NOTICE
     * @param String $message The message we would like to see.
     * Every message contains the date and time when the event
     * occurred.
     *
     */
    function log_message($type = 'INFO', $message) {
        $filename = "logs_" . date('Y-m-d') . ".txt";
        $textToWrite = $type . " " . date('Y-m-d H:i:s') . " -> " . $message . PHP_EOL;
        file_put_contents(log_path() . $filename, $textToWrite, FILE_APPEND);
    }

    function print_pre($text) {
        echo "<pre>";
        print_r($text);
        echo "</pre>";
    }