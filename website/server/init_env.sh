#!/bin/sh

S=`pwd`

# Generate constants_local.py for flux app.
echo "1. Setup constants for MicrobesFlux"
echo '' > flux/constants_local.py
echo "kegg_database = \"${S}/kegg_database/\" " >> flux/constants_local.py
echo "baseurl = \"${S}/flux/\" " >> flux/constants_local.py

echo "2. Rewrite settings.py under microbesflux"
mkdir -p session
mkdir -p media

# Change Django settings.
cp microbesflux/settings_blueprint.py microbesflux/settings.py
platform=`uname`
if [[ $platform == 'Darwin' ]]; then
	sed -i "" "s%^MEDIA_ROOT.*\$%MEDIA_ROOT = \'${PWD}/media/\'%g " microbesflux/settings.py
	sed -i "" "s%^SESSION_FILE_PATH.*\$%SESSION_FILE_PATH = \'${PWD}/session/\'%g " microbesflux/settings.py
else 
	sed -i "s%^MEDIA_ROOT.*\$%MEDIA_ROOT = \'${PWD}/media/\'%g " microbesflux/settings.py
	sed -i "s%^SESSION_FILE_PATH.*\$%SESSION_FILE_PATH = \'${PWD}/session/\'%g " microbesflux/settings.py
fi
#SESSION_FILE_PATH
