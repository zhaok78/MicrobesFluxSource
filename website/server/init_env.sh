#!/bin/sh

S=`pwd`

# Generate constants_local.py for flux app.
echo '' > flux/constants_local.py
echo "kegg_database = \"${S}/kegg_database/\" " >> flux/constants_local.py
echo "baseurl = \"${S}/flux/\" " >> flux/constants_local.py

mkdir -p session
mkdir -p media

# Change Django settings.
cp microbesflux/settings.py microbesflux/settings_blueprint.py
platform=`uname`
if [[ $platform == 'Darwin' ]]; then
	sed -i "" "s%^MEDIA_ROOT.*\$%MEDIA_ROOT = \'${PWD}/media/\'%g " microbesflux/settings.py
	sed -i "" "s%^SESSION_FILE_PATH.*\$%SESSION_FILE_PATH = \'${PWD}/session/\'%g " microbesflux/settings.py
else 
	sed -i "s%^MEDIA_ROOT.*\$%MEDIA_ROOT = \'${PWD}/media/\'%g " microbesflux/settings.py
	sed -i "s%^SESSION_FILE_PATH.*\$%SESSION_FILE_PATH = \'${PWD}/session/\'%g " microbesflux/settings.py
fi
#SESSION_FILE_PATH
