#!/bin/sh

S=`pwd`

# Generate constants.py for flux app.
rm -f flux/constants.py
echo '' > flux/constants.py
echo "kegg_database = \"${S}/kegg_database/\" " >> flux/constants.py
echo "baseurl = \"${S}/flux/\" " >> flux/constants.py

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
