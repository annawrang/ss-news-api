#!/usr/bin/env bash

API_VERSION=1.0
FILE_NAME=ss-news-api-v${API_VERSION}

raml2html -i raml/${FILE_NAME}.raml -o html/${FILE_NAME}.html