#!/bin/sh

S=`pwd`

# Generate constants_local.py for flux app.
echo "1. Setup constants for MicrobesFlux"
echo '' > flux/constants_local.py
echo "kegg_database = \"${S}/kegg_database/\" " >> flux/constants_local.py
echo "baseurl = \"${S}/flux/\" " >> flux/constants_local.py
cd flux
rm constants.py
ln -s constants_local.py constants.py
cd ..

echo "2. Rewrite settings.py under microbesflux"
mkdir -p session
mkdir -p media

# Change Django settings.
sed -i "s%^MEDIA_ROOT.*\$%MEDIA_ROOT = \'${PWD}/media/\'%g " microbesflux/settings.py
sed -i "s%^SESSION_FILE_PATH.*\$%SESSION_FILE_PATH = \'${PWD}/session/\'%g " microbesflux/settings.py
#SESSION_FILE_PATH
