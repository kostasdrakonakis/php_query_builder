<?php

    /**
     * Returns the base URL
     * http://www.domain.com/rootfolder/
     */
    function base_url() {
        return Config::get('url/base_url');
    }

    /**
     * Returns the server root url
     * e.g. var/www/public_html/rootfolder/
     */
    function server_url() {
        return $_SERVER['DOCUMENT_ROOT'] . "/" . Config::get('folder/root') . "/";
    }