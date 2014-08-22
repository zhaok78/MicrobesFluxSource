#!/bin/sh

S=`pwd`

# Generate constants_local.py for flux app.
echo '' > flux/constants_local.py
echo "kegg_database = \"${S}/kegg_database/\" " >> flux/constants_local.py
echo "baseurl = \"${S}/flux/\" " >> flux/constants_local.py

# Change Django settings.
sed -i microbesflux/settings.py 


MEDIA_ROOT
SESSION_FILE_PATH
